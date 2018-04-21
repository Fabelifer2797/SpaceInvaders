package org.tec.datosI.graficos;

import java.awt.Graphics;
import java.awt.Image;

public class Dibujo_imagen {
	
	public Image imagen;
	
	public Dibujo_imagen(Image imagen) 
        {
	  this.imagen = imagen;
	}
	
	
	public int getWidth() 
        {
	  return imagen.getWidth(null);
	}

	
	public int getHeight() 
        {
	  return imagen.getHeight(null);
	}
	
	
	public void dibujar(Graphics grafico,int x,int y) 
        {
		grafico.drawImage(imagen,x,y,null);
	}
}
