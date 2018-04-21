package org.tec.datosI.juego;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;

public class Nave_espacial extends Graficos {

	public SpaceInvaders juego;


	public Nave_espacial(SpaceInvaders juego,String imagen,int x,int y) 
	{
		super(imagen,x,y);

		this.juego = juego;
	}


	public void mover(long valor) {

		if ((desplazamiento_columna < 0) && (columna< 10)) 
		{
			return;
		}

		if ((desplazamiento_columna > 0) && (columna> 720)) 
		{
			return;
		}

		super.mover(valor);
	}


	public void colisiona_con(Graficos grafico) {

		if (grafico instanceof Alien) 
		{
			juego.notificar_perdedor();
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
	public int getResistenciaAlien() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setResistenciaAlien(int ResistenciaAlien) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void descontarResistenciaAlien() {
		// TODO Auto-generated method stub
		
	}
}