package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

/**
 * Implementa el patrón Message Translator. Utiliza un archivo XSLT, cuya ruta
 * se especifica en xpathExpression, para transformar el cuerpo XML del Mensaje
 * de entrada.
 *
 * * @author tinog
 */
public class Translator extends Tarea {

    private String xpathExpression;

    public Translator(List<Slot> entradas, List<Slot> salidas, String xsltFilePath) {
        super(entradas, salidas);
        this.xpathExpression = xsltFilePath;
    }

    @Override
    public void run() {

        if (entradas.isEmpty() || salidas.isEmpty() || xpathExpression == null) {
            Logger.getLogger(Translator.class.getName()).log(Level.SEVERE,
                    "Configuración incompleta en translator: \n\tSlot de entrada/salida vacío o ruta XSLT no especificada.");
            return;
        }

        while(true) {           
            Mensaje msg = entradas.getFirst().leerSlot();

            if(msg == null) {
                break;
            }

            try { 
                Document docEntrada = msg.getCuerpo();

                TransformerFactory factory = TransformerFactory.newInstance();

                StreamSource xslt = new StreamSource(new File(xpathExpression));

                Transformer transformer = factory.newTransformer(xslt);

                DOMSource xmlSource = new DOMSource(docEntrada);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                transformer.transform(xmlSource, result);

                Document docSalida = convertStringToDocument(writer.toString());

                Mensaje msgSalida;
                msgSalida = new Mensaje(docSalida);
                msgSalida.setIdFragment(msg.getIdFragment());
                msgSalida.setId(msg.getId());
                msgSalida.setIdCorrelator(msg.getIdCorrelator());

                salidas.getFirst().escribirSlot(msgSalida);

            } catch (javax.xml.transform.TransformerException ex) {
                Logger.getLogger(Translator.class.getName()).log(Level.SEVERE,
                        "Error durante la transformación XSLT o el archivo XSLT es inválido.", ex);
            } catch (Exception ex) {
                Logger.getLogger(Translator.class.getName()).log(Level.SEVERE,
                        "Error al leer el Slot o convertir el resultado.", ex);
            }
        
        } 
    }

    private Document convertStringToDocument(String xmlString) throws Exception {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
        org.xml.sax.InputSource is = new org.xml.sax.InputSource(new java.io.StringReader(xmlString));
        return builder.parse(is);
    }   
}
