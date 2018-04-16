package org.tec.datosI.juego;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;

public class Misil extends Graficos {
	
	 double desplazamiento = -300;
	
	 SpaceInvaders juego;
	
	 boolean misil_disparado = false;
	
	public Misil(SpaceInvaders juego,String imagen,int x,int y) 
       {
		super(imagen,x,y);
		
		this.juego = juego;
		
		desplazamiento_fila = desplazamiento;
	}

	public void mover(long valor) {
		
		super.mover(valor);
		
		}
	
	
	public void colisiona_con(Graficos grafico) {
		
		if (misil_disparado) 
               {
		  return;
		}
		
		if (grafico instanceof Alien) 
               {
		   juego.lista_eliminados.add(grafico);
			
		   juego.descontar_eliminados();
                       
		   misil_disparado = true;
		}
	}
}