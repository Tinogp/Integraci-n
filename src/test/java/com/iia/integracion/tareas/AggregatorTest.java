/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.iia.integracion.tareas;

/**
 *
 * @author Enrique
 */
public class AggregatorTest {
    /*
     * //private Slot entrada;
     * //private Slot salida;
     * //private Aggregator aggregator;
     * 
     * //private static final String fragmento1 =
     * "<drink><name>cafe</name><type>hot</type></drink>";
     * //private static final String fragmento2 =
     * "<drink><name>coca-cola</name><type>cold</type></drink>";
     * //private static final String fragmento3 =
     * "<drink><name>chocolate</name><type>hot</type></drink>";
     * //private static final String fragmento4 =
     * "<drink><name>agua</name><type>cold</type></drink>";
     * 
     * private static final String raiz = "cafe_order";
     * 
     * @BeforeEach
     * public void setUp() {
     * entrada = new Slot();
     * salida = new Slot();
     * 
     * //aggregator = new Aggregator(List.of(entrada), List.of(salida), raiz);
     * }
     * 
     * @Test
     * public void testAggregator() {
     * 
     * Crear mensajes y fragmentos
     * Mensaje msgOriginal = new Mensaje(null); //sin cuerpo solo para id
     * Mensaje msgOriginal2 = new Mensaje(null); //sin cuerpo solo para id
     * 
     * Mensaje msgFrag1 = new Mensaje(crearDocumentoDesdeString(fragmento1),
     * msgOriginal.getId());
     * msgFrag1.setIdFragment(UUID.randomUUID());
     * msgFrag1.setTamano(2);
     * Mensaje msgFrag2 = new Mensaje(crearDocumentoDesdeString(fragmento2),
     * msgOriginal.getId());
     * msgFrag2.setIdFragment(UUID.randomUUID());
     * msgFrag2.setTamano(2);
     * 
     * Mensaje msgFrag3 = new Mensaje(crearDocumentoDesdeString(fragmento3),
     * msgOriginal2.getId());
     * msgFrag3.setIdFragment(UUID.randomUUID());
     * msgFrag3.setTamano(2);
     * Mensaje msgFrag4 = new Mensaje(crearDocumentoDesdeString(fragmento4),
     * msgOriginal2.getId());
     * msgFrag4.setIdFragment(UUID.randomUUID());
     * msgFrag4.setTamano(2);
     * 
     * //Enviar Fragmentos
     * entrada.escribirSlot(msgFrag1);
     * aggregator.ejecuta();
     * entrada.escribirSlot(msgFrag3);
     * aggregator.ejecuta();
     * entrada.escribirSlot(msgFrag2);
     * aggregator.ejecuta();
     * 
     * //Comprobaciones
     * assertEquals(1, salida.numMensajes());
     * Mensaje msgFinal = salida.leerSlot();
     * 
     * System.out.println("\n--- Mensaje Final ---");
     * System.out.println(msgFinal.toString());
     * System.out.println("------------------------------\n");
     * }
     * 
     * private Document crearDocumentoDesdeString(String xmlString) {
     * try {
     * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
     * DocumentBuilder builder = factory.newDocumentBuilder();
     * InputSource is = new InputSource(new StringReader(xmlString));
     * return (Document) builder.parse(is);
     * } catch (ParserConfigurationException | SAXException | IOException e) {
     * throw new RuntimeException("Error al parsear XML de prueba", e);
     * }
     * }
     */

}
