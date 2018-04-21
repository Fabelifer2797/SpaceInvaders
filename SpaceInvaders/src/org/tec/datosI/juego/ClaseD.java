package org.tec.datosI.juego;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import org.tec.datosI.aplicaci�nPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.listasEnlazadas.ListaCircular;
import org.tec.datosI.listasEnlazadas.ListaGeneral;
import org.tec.datosI.listasEnlazadas.NodoLista;

public class ClaseD extends HileraEnemigos{
	
	private int ID = 4;
	private ListaGeneral<Graficos> listaClaseD = new ListaCircular<Graficos>();
	private Random posicionJefe = new Random(System.currentTimeMillis());
	private Random resistenciasAlien = new Random(System.currentTimeMillis());
	private AlgoritmosDeOrdenamiento algoSort = new AlgoritmosDeOrdenamiento();
	
	public int[] crearArregloResistencias() {
		
		int[] ArregloResistencias = new int[7];
		
		for(int i = 0; i < 7; i++) {
			
			int resistencia = 1 +  resistenciasAlien.nextInt(10);
			ArregloResistencias[i] = resistencia;
		}
		
		//algoSort.MostrarArreglo(ArregloResistencias);
		return ArregloResistencias;
		
		
	}
	
	@Override
	public void CrearHilera(Graficos Nave, String Alien, String Jefe, SpaceInvaders Juego) {
		
		int posicion = posicionJefe.nextInt(8);
		listaClaseD.InsertarFinalLista(Nave, "N");
		int[] ArregloResistenciasOrdenado = algoSort.BubbleSort(this.crearArregloResistencias());
		int Contador = 6;
		
		for (int f = 0; f < 1; f++) 
		{
			for (int c = 0; c < 8; c++) 
			{
				
				if(c == posicion) {
					Graficos alien = new Alien(Juego,Jefe,70 + (c * 80),(50) + f * 30);
					alien.setJefe();
					listaClaseD.InsertarFinalLista(alien,"J");
					Juego.num_aliens++;
				}
				
				else {
					
					Graficos alien = new Alien(Juego,Alien,70 + (c * 80),(50) + f * 30);
					alien.setResistenciaAlien(ArregloResistenciasOrdenado[Contador]);
					listaClaseD.InsertarFinalLista(alien,"A");
					Juego.num_aliens++;
					Contador-- ;
					
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
		
		return listaClaseD;
	}

	@Override
	public void AgregarMisil(Misil misil) {
		
		listaClaseD.InsertarFinalLista(misil,"M");
		
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

		while(actual1 != null && Contador1 < this.getLista().ObtenerTama�oLista()) {

			NodoLista<Graficos> actual2 = this.getLista().getPrimero();
			int Contador2 = 0;

			while(actual2 != null && Contador2 < this.getLista().ObtenerTama�oLista()) {

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
		
		while (actual != null && Contador < this.getLista().ObtenerTama�oLista()) {
			
			
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