package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
/**
 * 
 * @author Quique
 */
public class Distributor extends Tarea{
    
    private final List<XPathExpression> xpaths;

    /**
     * 
     * @param entradas lista de slots de entrada (1)
     * @param salidas lista de slots de salida (2), 
     *                primera salida para bebidas caliente y segunda salida para frías
     * @param xPathExpression Evalua a true cuando la bebida es caliente (/drink/type = 'hot')
     */
    public Distributor(List<Slot> entradas, List<Slot> salidas, List<String> xPathExpressions) {
        super(entradas, salidas, "Distributor");
        
        //Comprobar numeros de slots y Xpath
        if(entradas == null || entradas.size() != 1){
            throw new IllegalArgumentException("Distributor debe tener 1 slot de entrada");
        }
        
        if(salidas == null || salidas.size() < 2){
            throw new IllegalArgumentException("Distributor debe tener al menos 2 slots de salida");
        }  

        if (xPathExpressions == null || xPathExpressions.size() != salidas.size()) {
            throw new IllegalArgumentException("El número de expresiones XPath debe coincidir con el número de slots de salida.");
        }

        this.xpaths = new ArrayList<>();
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        for (String expresion : xPathExpressions) {
            try {               
                this.xpaths.add(xpath.compile(expresion));
            } catch (XPathExpressionException e) {
                throw new IllegalArgumentException("Expresión XPath inválida: " + expresion, e);
            }
        }                
    }        

    @Override
    public void ejecuta() {
        
        Mensaje mensaje = this.entradas.getFirst().leerSlot();
        
        if(mensaje != null){                
            
            try{
                
                Document cuerpo = mensaje.getCuerpo();
                boolean enviado = false;
                
                for (int i = 0; i < xpaths.size(); i++) {
                    XPathExpression expresion = xpaths.get(i);
                    Boolean resultado = (Boolean) expresion.evaluate(cuerpo, XPathConstants.BOOLEAN);
                    
                    if (resultado) {
                        this.salidas.get(i).escribirSlot(mensaje);
                        enviado = true;
                        break;
                    }
                }

                if(!enviado){
                    Logger.getLogger(Distributor.class.getName()).log(Level.WARNING, "Ninguna expresión XPath coincidió para el mensaje: {0}", mensaje);
                }
                
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, "Error al evaluar la expresión XPath.", ex);
            }                                    
        }
        
        
        
    }
    
}