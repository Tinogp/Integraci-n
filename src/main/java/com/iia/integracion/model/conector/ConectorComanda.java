package com.iia.integracion.model.conector;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ConectorComanda extends Conector {

    /**
     * 
     * @param puerto puerto de entrada
     * @param directorioPolling directorio donde se encuentran las distintas comandas
     */
    public ConectorComanda(Puerto puerto, String directorioPolling) {
        super(puerto, directorioPolling);
    }

    @Override
    public void ejecuta() {
        File directory = new File(directorioPolling);
        File[] files = directory.listFiles();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.getLogger(ConectorComanda.class.getName()).log(System.Logger.Level.ERROR, (String) "Error al ejecutar ConectorComanda", ex);
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
            puerto.ejecuta(new Mensaje(document));
        }

    }

}
