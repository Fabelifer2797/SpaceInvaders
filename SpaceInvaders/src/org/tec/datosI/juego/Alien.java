package org.tec.datosI.juego;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;

public class Alien extends Graficos {
	
	 SpaceInvaders juego;
	
       public Alien(SpaceInvaders juego,String imagen,int columna,int fila) 
       {
		super(imagen,columna,fila);
		
		this.juego = juego;
               
		desplazamiento_columna = -75;
	}

	
	public void mover(long valor) {
		
		if ((desplazamiento_columna < 0) && (columna < 10)) 
               {
		  juego.alcanzado_limite = true;;
		}
		
		if ((desplazamiento_columna > 0) && (columna > 750)) 
               {
	          juego.alcanzado_limite = true;;
		}
		
		super.mover(valor);
	}
	
	
	public void avanzar_aliens() 
       {
		desplazamiento_columna = -desplazamiento_columna;
               
		fila += 15;
		
		if (fila > 570) 
               {
		  juego.notificar_perdedor();
		}
	}
	
	
	public void colisiona_con(Graficos grafico) {
		
	}
}
