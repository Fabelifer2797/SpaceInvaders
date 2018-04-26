package org.tec.datosI.juego;

import java.awt.Graphics;
import java.util.ArrayList;
import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.listasEnlazadas.ListaDobleCircular;
import org.tec.datosI.listasEnlazadas.ListaGeneral;
import org.tec.datosI.listasEnlazadas.NodoLista;
import org.tec.datosI.sound.Sound;

public class ClaseE extends HileraEnemigos {
	
	private int ID = 5;
	private ListaGeneral<Graficos> listaClaseE = new ListaDobleCircular<Graficos>();
	private ListaGeneral<Graficos> listaPosiciones = new ListaDobleCircular<Graficos>();
	private Graficos referenciaJefe;
	private int contadorVueltas = 1;
	private int contadorCiclos = 1;
	double[] horizontal = {0,-240,0,-160,0,-80,0,80,0,160,0,240};
	double[] vertical = {-160,0,-105,0,-50,0,50,0,105,0,160,0};
	double[] diagonal1 =  {-130,-240,-90,-160,-50,-80,50,80,90,160,130,240};
	double[] diagonal2 = {-130,240,-90,160,-50,80,50,-80,90,-160,130,-240};
	private Sound sonidoExplosion =  new Sound("/org/tec/datosI/sonidos/explosion.wav");
	

	@Override
	public void CrearHilera(Graficos Nave, String Alien, String Jefe, SpaceInvaders Juego) {
		
		int posicion = 3;
		listaClaseE.InsertarFinalLista(Nave, "N");
		
		for (int f = 0; f < 1; f++) 
		{
			for (int c = 0; c < 7; c++) 
			{
				if(c == posicion) {
					Graficos alien = new Alien(Juego,Jefe,70 + (c * 80),(150) + f * 30);
					alien.setJefe();
					listaClaseE.InsertarFinalLista(alien,"J");
					referenciaJefe = new Alien(Juego,Jefe,70 + (c * 80),(150) + f * 30);
					Juego.num_aliens++;
				}
				
				else {
					
					Graficos alien = new Alien(Juego,Alien,70 + (c * 80),(150) + f * 30);
					listaClaseE.InsertarFinalLista(alien,"A");
					listaPosiciones.InsertarFinalLista(alien, "A");
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
		referenciaJefe.mover(Valor);
		
	}

	@Override
	public void GenerarMovimientoFilas() {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		
		while(actual.getSiguiente() != this.getLista().getPrimero()){
			
			actual.getValor().avanzar_aliens();
			actual = actual.getSiguiente();
			
		}
		
		actual.getValor().avanzar_aliens();
		referenciaJefe.avanzar_aliens();
		
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
	public void DescontarEliminados(ArrayList<Graficos> listaEliminados, SpaceInvaders Juego) {
		
		NodoLista<Graficos> actual = this.getLista().getPrimero();
		int Contador = 0;
		
		while (actual != null && Contador < this.getLista().ObtenerTamañoLista()) {
			
			
			for(int i = 0; i < listaEliminados.size(); i++) {
				
				Graficos grafico = (Graficos) listaEliminados.get(i);
				
				if(grafico.getID() == actual.getValor().getID()) {
					
					Juego.coorXE = actual.getValor().columna;
					Juego.coorYE = actual.getValor().fila;
					Juego.Explosion = true;
					
					if(!sonidoExplosion.isPlaying()) {
					sonidoExplosion.play();
				}
					
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
				
				//grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
				
			}
			
			actual = actual.getSiguiente();
			
		}
		
		Graficos grafico = (Graficos) actual.getValor();
		
		if(grafico instanceof Alien) {
			
			grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
			
		}
		
	}

	@Override
	public void CorrerAlCentro(Graficos AlienEliminado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RestablecerValorDesCol() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void GenerarIntercambioJefe(SpaceInvaders Juego) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GenerarMovimientoReloj() {
		
		double filaJefe = referenciaJefe.fila;
		double columnaJefe = referenciaJefe.columna;
		
		if(this.contadorVueltas == 1) {
			
			if(this.contadorCiclos == 1) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.horizontal[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.horizontal[contadorColumna]);
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else if(this.contadorCiclos == 2) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.diagonal1[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.diagonal1[contadorColumna]);
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else if(this.contadorCiclos == 3) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.vertical[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.vertical[contadorColumna]);
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.diagonal2[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.diagonal2[contadorColumna]);
						actual = actual.getSiguiente();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos = 1;
				this.contadorVueltas++;
				
			}
			
			
			
			
		}
		
		else {
			
			if(this.contadorCiclos == 1) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero().getAnterior();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.horizontal[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.horizontal[contadorColumna]);
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else if(this.contadorCiclos == 2) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero().getAnterior();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.diagonal1[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.diagonal1[contadorColumna]);
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else if(this.contadorCiclos == 3) {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero().getAnterior();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.vertical[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.vertical[contadorColumna]);
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos++;
			}
			
			else {
				
				NodoLista<Graficos> actual = this.listaPosiciones.getPrimero().getAnterior();
				int contadorBucle = 0;
				int contadorFila = 0;
				int contadorColumna = 1;
				
				while(contadorBucle < this.listaPosiciones.ObtenerTamañoLista()) {
					
					NodoLista<Graficos> alien = this.getLista().BuscarLista(actual.getValor());
					
					if(alien == null) {
						
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
					else {
						alien.getValor().fila = filaJefe + (this.diagonal2[contadorFila]);
						alien.getValor().columna = columnaJefe + (this.diagonal2[contadorColumna]);
						actual = actual.getAnterior();
						contadorBucle++;
						contadorFila += 2;
						contadorColumna += 2;
					}
					
				}
				
				this.contadorCiclos = 1;
				this.contadorVueltas = 1;
				
			}
		}
		
		
		
		
	}

	@Override
	public Graficos getReferenciaJefe() {
		
		return referenciaJefe;
	}


}
