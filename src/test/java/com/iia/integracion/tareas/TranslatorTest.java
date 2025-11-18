package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.Document;

import java.io.StringReader;
import java.util.Collections;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;

public class TranslatorTest {

    // --- XMLs de Entrada y Salida ---
    private static final String XML_ORIGEN
            = "<ProductoOrdenado id=\"1\"><sku>P001</sku><descripcion>Tornillo M8 de alta resistencia</descripcion>"
            + "<qty>5</qty><unitPrice>1.50</unitPrice></ProductoOrdenado>";

    private static final String XML_SOLUCION
            = "<LineaFactura><ID_Item>1</ID_Item><Referencia>P001</Referencia>"
            + "<Detalle>Tornillo M8 de alta resistencia</Detalle><CantidadFacturada>5</CantidadFacturada>"
            + "<PrecioTotal>7.5</PrecioTotal></LineaFactura>";

    private static final String RUTA_XSLT = "src\\test\\java\\com\\iia\\integracion\\tareas\\traductor_a_formato_final.xslt";

    // --- Metadatos de prueba (Todos UUIDs) ---
    private static final UUID ID_MENSAJE_ENTRADA = UUID.randomUUID();
    private static final UUID ID_CORRELATOR = UUID.randomUUID();

    @Test
    void testEjecutaTraduccionYPropagaMetadatosFragmento() throws Exception {
        // 1. Preparar Slots
        Slot entrada = new Slot();
        Slot salida = new Slot();

        // 2. Crear MensajeFragmento de entrada
        Document docOrigen = convertStringToDocument(XML_ORIGEN);

        Mensaje msgEntrada = new Mensaje(docOrigen);

        // Establecer los metadatos (UUIDs)
        msgEntrada.setId(ID_MENSAJE_ENTRADA);
        msgEntrada.setIdCorrelator(ID_CORRELATOR);
        msgEntrada.setIdFragment((long) 1); // Usando UUID

        entrada.escribirSlot(msgEntrada);

        // 3. Inicializar el Translator
        Translator translator = new Translator(
                Collections.singletonList(entrada),
                Collections.singletonList(salida),
                RUTA_XSLT
        );

        // 4. Ejecutar la Tarea
        translator.ejecuta();

        // 5. Verificar el resultado
        Mensaje msgSalida = salida.leerSlot();
        assertNotNull(msgSalida, "El Slot de salida no debería estar vacío.");

        // --- Aserciones del Cuerpo XML ---
        // Normalizamos la salida del XML para una comparación limpia
        String xmlResultadoLimpio = normalizeXml(msgSalida.toString());
        String xmlSolucionLimpia = normalizeXml(XML_SOLUCION);

        System.out.println("entrada: " + msgEntrada);
        System.out.println("salida: " + msgSalida);

        assertTrue(xmlResultadoLimpio.contains(xmlSolucionLimpia),
                "El cuerpo XML traducido no coincide con el esperado.\nResultado: " + xmlResultadoLimpio);

        // --- Aserciones de la Propagación de Metadatos y Tipo ---
        // El ID del mensaje de salida debe ser NUEVO (generado por el constructor del Mensaje)
        assertEquals(ID_MENSAJE_ENTRADA, msgSalida.getId(),
                "El ID del Mensaje de salida debe ser nuevo.");

        // Los IDs Correlator y Fragmento deben ser propagados
        assertEquals(ID_CORRELATOR, msgSalida.getIdCorrelator(),
                "El ID Correlator no fue propagado correctamente.");

        assertEquals((long) 1, msgSalida.getIdFragment(),
                "El ID del Fragmento no fue propagado correctamente (UUID).");
    }

    // --- Métodos Auxiliares ---
    // Convierte un String XML a un Documento DOM
    private Document convertStringToDocument(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlString));
        return builder.parse(is);
    }

    // Normaliza el XML para una comparación robusta, AISLANDO solo el contenido XML
    private String normalizeXml(String xml) {
        // 1. Eliminar la declaración XML y la indentación/saltos de línea del cuerpo
        // 2. Extraer solo el contenido XML encerrado en 'cuerpo='

        // Patrón para extraer el contenido entre 'cuerpo=' y la siguiente llave '}'
        int inicioCuerpo = xml.indexOf("cuerpo=");
        int finCuerpo = xml.lastIndexOf('}');

        String cuerpoStr = xml;

        if (inicioCuerpo != -1 && finCuerpo != -1 && finCuerpo > inicioCuerpo + 7) {
            // Extrae el String que va después de "cuerpo=" y antes del final de la clase/fragmento
            cuerpoStr = xml.substring(inicioCuerpo + 7, finCuerpo);
            // Si es un MensajeFragmento, tiene un 'idFragment=' al final que también hay que cortar
            int corteFinal = cuerpoStr.lastIndexOf(", idFragment=");
            if (corteFinal != -1) {
                cuerpoStr = cuerpoStr.substring(0, corteFinal);
            }
        }

        // Limpieza final de espacios y saltos de línea dentro del XML
        return cuerpoStr.replaceAll("\\s+", "").trim();
    }
}
