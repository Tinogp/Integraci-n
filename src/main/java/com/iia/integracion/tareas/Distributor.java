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
        
        if(salidas == null || salidas.size() != 2){
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
            
            Slot salidaCaliente = this.salidas.get(0);
            Slot salidaFria = this.salidas.get(1);
            
            try{
                
                Document cuerpo = mensaje.getCuerpo();
                boolean resultado = (Boolean) this.xpath.evaluate(cuerpo, XPathConstants.BOOLEAN);
                
                if(resultado){ //bebida caliente                    
                    salidaCaliente.escribirSlot(mensaje);
                }else{ //bebida fria
                    salidaFria.escribirSlot(mensaje);
                }
                
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Distributor.class.getName()).log(Level.SEVERE, null, ex);
            }                                    
        }
        
        
        
    }
    
}