package org.tec.datosI.juego;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.listasEnlazadas.ListaDobleCircular;
import org.tec.datosI.listasEnlazadas.ListaGeneral;
import org.tec.datosI.listasEnlazadas.NodoLista;

public class ClaseE extends HileraEnemigos {
	
	private int ID = 5;
	private ListaGeneral<Graficos> listaClaseE = new ListaDobleCircular<Graficos>();
	private Random posicionJefe = new Random(System.currentTimeMillis());

	@Override
	public void CrearHilera(Graficos Nave, String Alien, String Jefe, SpaceInvaders Juego) {
		
		int posicion = posicionJefe.nextInt(8);
		listaClaseE.InsertarFinalLista(Nave, "N");
		
		for (int f = 0; f < 1; f++) 
		{
			for (int c = 0; c < 8; c++) 
			{
				if(c == posicion) {
					Graficos alien = new Alien(Juego,Jefe,70 + (c * 80),(50) + f * 30);
					alien.setJefe();
					listaClaseE.InsertarFinalLista(alien,"J");
					Juego.num_aliens++;
				}
				
				else {
					
					Graficos alien = new Alien(Juego,Alien,70 + (c * 80),(50) + f * 30);
					listaClaseE.InsertarFinalLista(alien,"A");
					Juego.num_aliens++;
					
				}


			}
		}
		
	}

	@Override
	public void GenerarMovimientoColumnas(long Valor) {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		
		while(actual.getSiguiente() != this.getLista().getPrimero()){
			
			actual.getValor().mover(Valor);
			actual = actual.getSiguiente();
			
		}
		
		actual.getValor().mover(Valor);
		
	}

	@Override
	public void GenerarMovimientoFilas() {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		
		while(actual.getSiguiente() != this.getLista().getPrimero()){
			
			actual.getValor().avanzar_aliens();
			actual = actual.getSiguiente();
			
		}
		
		actual.getValor().avanzar_aliens();
		
	}

	@Override
	public int getID() {
		
		return ID;
	}

	@Override
	public ListaGeneral<Graficos> getLista() {
		
		return listaClaseE;
	}

	@Override
	public void AgregarMisil(Misil misil) {
		
		listaClaseE.InsertarFinalLista(misil,"M");
		
	}

	@Override
	public void DibujarHileras(Graphics G2D) {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		
		while(actual.getSiguiente() != this.getLista().getPrimero()){
			
			actual.getValor().dibujar_graficos(G2D);
			actual = actual.getSiguiente();
			
		}
		
		actual.getValor().dibujar_graficos(G2D);
		
	}

	@Override
	public void VerificarColisiones() {
		
		NodoLista<Graficos> actual1 = this.getLista().getPrimero();
		int Contador1 = 0;

		while(actual1 != null && Contador1 < this.getLista().ObtenerTamañoLista()) {

			NodoLista<Graficos> actual2 = this.getLista().getPrimero();
			int Contador2 = 0;

			while(actual2 != null && Contador2 < this.getLista().ObtenerTamañoLista()) {

				Graficos grafico1 = (Graficos) actual1.getValor();
				Graficos grafico2 = (Graficos) actual2.getValor();

				if(grafico1.choca(grafico2)) {

					grafico1.colisiona_con(grafico2);
					grafico2.colisiona_con(grafico1);

				}

				actual2 = actual2.getSiguiente();
				Contador2++;

			}

			actual1 = actual1.getSiguiente();
			Contador1++;

		}
		
	}

	@Override
	public void DescontarEliminados(ArrayList<Graficos> listaEliminados) {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		int Contador = 0;
		
		while (actual != null && Contador < this.getLista().ObtenerTamañoLista()) {
			
			
			for(int i = 0; i < listaEliminados.size(); i++) {
				
				Graficos grafico = (Graficos) listaEliminados.get(i);
				
				if(grafico.getID() == actual.getValor().getID()) {
					
					this.getLista().EliminarLista(grafico);
					
				}
				
			}
			
			actual = actual.getSiguiente();
			Contador++;
		}
		
	}

	@Override
	public void EliminarLista() {
		
		this.getLista().setPrimero(null);
		
	}

	@Override
	public void DescontarNumeroAliens(SpaceInvaders Juego) {
		
		Juego.num_aliens--;

		if (Juego.num_aliens == 0) 
		{
			Juego.notificar_ganador();
		}
		
		ListaGeneral<Graficos> listaHileras;
		listaHileras = this.getLista();
		NodoLista<Graficos> actual = listaHileras.getPrimero();
		
		while(actual.getSiguiente() != this.getLista().getPrimero()) {
			
			Graficos grafico = (Graficos) actual.getValor();
			
			if(grafico instanceof Alien) {
				
				grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
				
			}
			
			actual = actual.getSiguiente();
			
		}
		
		Graficos grafico = (Graficos) actual.getValor();
		
		if(grafico instanceof Alien) {
			
			grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
			
		}
		
	}

}
