package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
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
    
    private final XPathExpression xpath;

    /**
     * 
     * @param entradas lista de slots de entrada (1)
     * @param salidas lista de slots de salida (2), 
     *                primera salida para bebidas caliente y segunda salida para frías
     * @param xPathExpression Evalua a true cuando la bebida es caliente (/drink/type = 'hot')
     */
    public Distributor(List<Slot> entradas, List<Slot> salidas, String xPathExpression) {
        super(entradas, salidas, xPathExpression);
        
        //Comprobar numeros de slots y Xpath
        if(entradas == null || entradas.size() != 1){
            throw new IllegalArgumentException("Distributor debe tener 1 slot de entrada");
        }
        
        if(salidas == null || salidas.size() < 2){
            throw new IllegalArgumentException("Distributor debe tener 2 slots de salida");
        }  

        if (xPathExpression == null || xPathExpression.isBlank()) {
            throw new IllegalArgumentException("Distributor necesita una expresion XPath");
        }
        
        try{
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath  = factory.newXPath();
            this.xpath = xpath.compile(xPathExpression);            
        }catch (XPathExpressionException e) {            
            throw new IllegalArgumentException("Expresion XPath inválida: " + xPathExpression, e);
        }     
    }        

    @Override
    public void ejecuta() {
        
        Mensaje mensaje = this.entradas.getFirst().leerSlot();
        
        if(mensaje != null){                
            
            try{
                
                Document cuerpo = mensaje.getCuerpo();
                Double resultadoDouble = (Double) this.xpath.evaluate(cuerpo, XPathConstants.NUMBER);
                
                int indiceSalida = resultadoDouble.intValue();
                
                if(indiceSalida >= 0 && indiceSalida < this.salidas.size()){
                    
                    Slot salida = this.salidas.get(indiceSalida);
                    
                    salida.escribirSlot(mensaje);                                
                }else {
                
                    Logger.getLogger(Distributor.class.getName()).log(
                        Level.WARNING, 
                        "El índice de salida {0} está fuera de rango para {1} slots de salida.", 
                        new Object[]{indiceSalida, this.salidas.size()}
                    );
            }
                
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, "Error al evaluar la expresión XPath.", ex);
            }                                    
        }
        
        
        
    }
    
}