package org.tec.datosI.graficos;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Configuracion_imagen {
	
    public static Configuracion_imagen configuracion = new Configuracion_imagen();
	
	public HashMap hashmap= new HashMap();
	
	public Dibujo_imagen transparentar_imagen(String grafico) {
		
		if (hashmap.get(grafico) != null) {
             
			return (Dibujo_imagen) hashmap.get(grafico);
		}
		
		BufferedImage imagen_buffer = null;
		
		try {
			URL url = this.getClass().getResource(grafico);
			
			if (url == null) {
                     
			  System.err.println("No se encontro el archivo: " + grafico);
			}
			
			imagen_buffer = ImageIO.read(url);
                     
                 } catch (IOException e){
			                    System.err.println("No se pudo cargar la imagen: " + grafico);
		                           }
		
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
             
		Image imagen = gc.createCompatibleImage(imagen_buffer.getWidth(),imagen_buffer.getHeight(),Transparency.BITMASK);
		
		imagen.getGraphics().drawImage(imagen_buffer,0,0,null);
		
		Dibujo_imagen dibujo = new Dibujo_imagen(imagen);
             
		hashmap.put(grafico,dibujo);
		
		return dibujo;
	}
	
}
