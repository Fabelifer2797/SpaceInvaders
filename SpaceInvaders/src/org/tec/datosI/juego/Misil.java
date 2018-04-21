package org.tec.datosI.juego;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;

public class Misil extends Graficos {

	public double desplazamiento = -300;
	public SpaceInvaders juego;
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
			if(grafico.getJefe() && grafico.getResistenciaJefe() == 1 && juego.hileraActual.getID() != 3
					&& juego.hileraActual.getID() != 4 && juego.hileraActual.getID() != 5) {
			
				juego.notificar_ganador();
				
			}
			
			else if(grafico.getJefe() && grafico.getResistenciaJefe() == 1 && (juego.hileraActual.getID() == 3 
					|| juego.hileraActual.getID() == 4 || juego.hileraActual.getID() == 5)) {
				
				juego.lista_eliminados.add(grafico);
				juego.hileraActual.getLista().EliminarLista(this);
				juego.hileraActual.DescontarNumeroAliens(juego);
				misil_disparado = true;
				
			}
			
			else if(grafico.getJefe() && grafico.getResistenciaJefe() > 0) {
				
				grafico.descontarResistenciaJefe();
				juego.hileraActual.getLista().EliminarLista(this);
				misil_disparado = true;
				
			}
			
			else {
				
				if(juego.hileraActual.getID() == 4) {
					
					if(grafico.getResistenciaAlien() == 1) {
						
						juego.lista_eliminados.add(grafico);
						juego.hileraActual.getLista().EliminarLista(this);
						juego.hileraActual.DescontarNumeroAliens(juego);
						misil_disparado = true;
						
					}
					
					else {
						
						grafico.descontarResistenciaAlien();
						juego.hileraActual.getLista().EliminarLista(this);
						misil_disparado = true;
					}
					
				}
				
				else {
					
					juego.lista_eliminados.add(grafico);
					juego.hileraActual.getLista().EliminarLista(this);
					juego.hileraActual.DescontarNumeroAliens(juego);
					misil_disparado = true;
				}
				

				
			}

		}
	}

	@Override
	public boolean getJefe() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getResistenciaJefe() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void descontarResistenciaJefe() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setJefe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResistenciaAlien(int ResistenciaAlien) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getResistenciaAlien() {
		// TODO Auto-generated method stub
		return 0;
		
	}

	@Override
	public void descontarResistenciaAlien() {
		// TODO Auto-generated method stub
		
	}
}