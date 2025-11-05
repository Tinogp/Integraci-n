package com.iia.integracion.model.conector;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ConectorComanda extends Conector {

    /**
     *
     * @param puerto puerto de entrada
     * @param directorioPolling directorio donde se encuentran las distintas
     * comandas
     */
    public ConectorComanda(Puerto puerto, String directorioPolling) {
        super(puerto, directorioPolling);
    }

    @Override
    public void ejecuta() {
        File directory = new File(Polling);
        File[] files = directory.listFiles();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.getLogger(ConectorComanda.class.getName()).log(System.Logger.Level.ERROR, (String) "Error al crear una factoria de document en ejecutar ConectorComanda", ex);
        }
        Document document = null;
        for (File file : files) {
            try {
                document = builder.parse(file);
            } catch (SAXException ex) {
                System.getLogger(ConectorComanda.class.getName()).log(System.Logger.Level.ERROR, (String) "Error al parsear un fichero a documento en ConectorComanda", ex);
            } catch (IOException ex) {
                System.getLogger(ConectorComanda.class.getName()).log(System.Logger.Level.ERROR, (String) "Error de entrada salida al parsear un fichero a documento en ConectorComanda", ex);
            }
            document.getDocumentElement().normalize();
            Mensaje msg = new Mensaje(document);
            UUID i = UUID.fromString(document.getElementsByTagName("order_id").item(0).getTextContent());
            msg.setId(i);
            if (puerto instanceof PuertoEntrada) {
                ((PuertoEntrada) puerto).ejecutaEscritura(msg);
            } else {
                System.err.println("Error en el tipo del puerto entrada...");
            }
        }

    }

}
