package com.iia.integracion.model.conector;

import com.iia.integracion.model.ConexionMySQL;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoSolicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author tinog
 */
public class ConectorBX extends Conector {

    Connection conexion;

    public ConectorBX(Puerto puerto, String directorioPolling) {
        super(puerto, directorioPolling);
        conexion = ConexionMySQL.getConexion();
        if (!(puerto instanceof PuertoSolicitud)) {
            System.err.println("Error en la creacion del conectorBX, el puerto no es del tipo solicitud");
            System.exit(0);
        }
    }

    @Override
    public void ejecuta() {
        PuertoSolicitud ps = (PuertoSolicitud) puerto;
        Document doc;
        Mensaje msg = ps.ejecutaLectura();
        String query = msg.getCuerpo().getElementsByTagName("sql").item(0).getTextContent();
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    Boolean existencia = rs.getBoolean("existencia");
                    System.out.println("ID: " + id + ", Nombre: " + nombre + ", existencia: " + existencia);
                    doc = crearDocumentoSQLResponse(id.toString(), nombre, existencia.toString());
                    msg.setCuerpo(doc);
                    ps.ejecutaEscritura(msg);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Document crearDocumentoSQLResponse(String idContenido, String nombreContenido, String listoContenido) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.getLogger(ConectorBX.class.getName()).log(System.Logger.Level.ERROR, (String) "Error en la creacion de un documento en una respuesta sql de BX...", ex);
        }

        Document document = builder.newDocument();

        // Etiqueta principal: <sqlResponse>
        Element raiz = document.createElement("sqlResponse");
        document.appendChild(raiz);

        // Sub-etiqueta: <id>
        Element idElemento = document.createElement("id");
        idElemento.setTextContent(idContenido);
        raiz.appendChild(idElemento);

        // Sub-etiqueta: <nombre>
        Element nombreElemento = document.createElement("nombre");
        nombreElemento.setTextContent(nombreContenido);
        raiz.appendChild(nombreElemento);

        // Sub-etiqueta: <listo>
        Element listoElemento = document.createElement("listo");
        listoElemento.setTextContent(listoContenido);
        raiz.appendChild(listoElemento);

        return document;
    }

}
