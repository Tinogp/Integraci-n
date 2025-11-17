package com.iia.integracion.model.conector;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoSalida;
import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author tinog
 */
public class ConectorCamarero extends Conector {
    
    public ConectorCamarero(Puerto puerto, String Polling) {
        super(puerto, Polling);
    }
    
    @Override
    public void ejecuta() {
        Mensaje msg = null;
        if (puerto instanceof PuertoSalida) {
            msg = ((PuertoSalida) puerto).ejecutaLectura();
        } else {
            System.err.println("Error en el tipo del puerto entrada en conector camarero...");
        }

        ////// CAMBIOS //////
        
        if(msg != null && msg.getCuerpo() != null){

            File directorio = new File(Polling);
            if(!directorio.exists()){
                directorio.mkdirs(); // Crear el directorio si no existe
            }

            //Archivo con el nombre del ID del mensaje
            String nombreArchivo = Polling + "/comanda_" + msg.getId().toString() + ".xml";
            
            File archivoSalida = new File(directorio, nombreArchivo);

            exportarDocumento(msg.getCuerpo(), archivoSalida.getAbsolutePath());
        }                
    }
    
    public void exportarDocumento(Document documento, String rutaArchivo) {
        try {
            // 1. Obtener la Factoría de Transformadores
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // Opcional: Configurar la indentación para que el XML sea legible
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // 2. Definir la Fuente (el Document DOM)
            DOMSource source = new DOMSource(documento);

            // 3. Definir el Resultado (el archivo de destino)
            File archivoSalida = new File(rutaArchivo);
            StreamResult result = new StreamResult(archivoSalida);

            // 4. Realizar la Transformación (escritura al archivo)
            transformer.transform(source, result);
            
            System.out.println("Documento exportado exitosamente a: " + rutaArchivo);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al exportar el documento XML.");
        }
    }
    
}
