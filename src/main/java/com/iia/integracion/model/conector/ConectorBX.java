package com.iia.integracion.model.conector;

import com.iia.integracion.model.ConexionMySQL;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoSolicitud;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

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

    public ConectorBX(Puerto puerto, String URL) {
        super(puerto, URL);
        conexion = ConexionMySQL.getConexion(URL);
        if (!(puerto instanceof PuertoSolicitud)) {
            System.err.println("Error en la creacion del conectorBX, el puerto no es del tipo solicitud");
            System.exit(0);
        }
    }

    @Override
    public void ejecuta() {
        PuertoSolicitud ps = (PuertoSolicitud) puerto;
        Mensaje msg = ps.ejecutaLectura();

        // Obtenemos la query del mensaje entrante
        String query = msg.getCuerpo().getElementsByTagName("sql").item(0).getTextContent();

        Document doc;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.newDocument();

            // Elemento raíz que agrupa todos los resultados
            Element rootElement = doc.createElement("sqlResponseSet");
            doc.appendChild(rootElement);

            try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
                try (ResultSet rs = pstmt.executeQuery()) {

                    // 1. OBTENER METADATOS (Información sobre las columnas)
                    ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                    int numeroDeColumnas = metaData.getColumnCount();

                    // 2. Iterar por cada FILA de datos
                    while (rs.next()) {
                        // Creamos un elemento genérico para la fila, ej: <resultado> o <row>
                        Element rowElement = doc.createElement("resultado");
                        rootElement.appendChild(rowElement);

                        // 3. Iterar por cada COLUMNA de esa fila (dinámicamente)
                        for (int i = 1; i <= numeroDeColumnas; i++) {

                            // Obtenemos el nombre de la columna para usarlo como etiqueta XML
                            // getColumnLabel es mejor que getColumnName porque respeta los alias (AS
                            // nombre)
                            String nombreColumna = metaData.getColumnLabel(i);

                            // Obtenemos el valor sin importar el tipo (Object)
                            Object valor = rs.getObject(i);

                            // Creamos la etiqueta XML con el nombre de la columna
                            // Cuidado: Los nombres de columnas no deben tener espacios para ser XML válido
                            Element colElement = doc.createElement(nombreColumna);

                            // Si el valor no es null, lo convertimos a String
                            if (valor != null) {
                                colElement.setTextContent(valor.toString());
                            } else {
                                colElement.setTextContent(""); // O manejar nulos como prefieras
                            }

                            rowElement.appendChild(colElement);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Aquí podrías agregar al XML un nodo de <error>
            }

            // Enviamos el XML construido
            msg.setCuerpo(doc);
            ps.ejecutaEscritura(msg);

        } catch (ParserConfigurationException ex) {
            System.getLogger(ConectorBX.class.getName()).log(System.Logger.Level.ERROR, "Error XML", ex);
        }
    }
}
