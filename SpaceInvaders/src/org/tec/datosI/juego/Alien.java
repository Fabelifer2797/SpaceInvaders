package org.tec.datosI.juego;

import java.util.Random;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;

public class Alien extends Graficos {

	public SpaceInvaders juego;
	public boolean esJefe;
	Random numeroAleatorio = new Random(System.currentTimeMillis());
	public int resistenciaJefe;
	public int resistenciaAlien;

	public Alien(SpaceInvaders juego,String imagen,int columna,int fila) 
	{
		super(imagen,columna,fila);
		this.juego = juego;
		//Cambiar velocidad del juego
		desplazamiento_columna = -200;
		resistenciaJefe = 2 + numeroAleatorio.nextInt(4);
		esJefe = false;
		resistenciaAlien = 0;
	}
	
	@Override
	public void setJefe() {
		
		this.esJefe = true;
		//System.out.println(resistenciaJefe);
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

		if (fila >= 470) 
		{
			juego.notificar_perdedor();
		}
	}


	public void colisiona_con(Graficos grafico) {

	}

	@Override
	public boolean getJefe() {
		return this.esJefe;
	}

	@Override
	public int getResistenciaJefe() {
		
		return this.resistenciaJefe;
	}

	@Override
	public void descontarResistenciaJefe() {
		
		this.resistenciaJefe--;
		//System.out.println(resistenciaJefe);
		
	}

	@Override
	public void setResistenciaAlien(int ResistenciaAlien) {
		
		this.resistenciaAlien = ResistenciaAlien;
		
	}

	@Override
	public int getResistenciaAlien() {
		
		return this.resistenciaAlien;
		
	}

	@Override
	public void descontarResistenciaAlien() {
		
		this.resistenciaAlien--;
		
	}
}
