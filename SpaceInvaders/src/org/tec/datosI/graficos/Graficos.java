package org.tec.datosI.graficos;

import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class Graficos {
	
	public Dibujo_imagen dibujo_imagen;
    
    public double columna;
	
	public double fila;
	
	public double desplazamiento_columna;
	
	public double desplazamiento_fila;
	
	public Rectangle grafico1 = new Rectangle();
	
	public Rectangle grafico2 = new Rectangle();
	
       public Graficos(String imagen,int columna,int fila) 
       {
		this.dibujo_imagen = Configuracion_imagen.configuracion.transparentar_imagen(imagen);
                
		this.columna = columna;
                
		this.fila = fila;
	}
	
	public void mover(long valor) 
        {
	  columna += (valor * desplazamiento_columna) / 1000;
          
	  fila += (valor * desplazamiento_fila) / 1000;
	}
	
	public void dibujar_graficos (Graphics grafico) 
        {
	  dibujo_imagen.dibujar(grafico,(int)columna,(int) fila);
	}
	
	public void avanzar_aliens() {}
	
	public boolean choca(Graficos imagen) 
        {
          grafico1.setBounds((int)columna,(int)fila,dibujo_imagen.getWidth(),dibujo_imagen.getHeight());
                
          grafico2.setBounds((int)imagen.columna,(int)imagen.fila,imagen.dibujo_imagen.getWidth(),imagen.dibujo_imagen.getHeight());

	  return grafico1.intersects(grafico2);
	}
	
	public abstract void colisiona_con(Graficos imagen);
}