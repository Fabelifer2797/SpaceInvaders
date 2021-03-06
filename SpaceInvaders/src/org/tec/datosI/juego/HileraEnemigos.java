package org.tec.datosI.juego;

import java.awt.Graphics;
import java.util.ArrayList;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.listasEnlazadas.ListaGeneral;


public abstract class HileraEnemigos {
	
	public abstract void CrearHilera(Graficos Nave, String Alien, String Jefe, SpaceInvaders Juego);
	public abstract void GenerarMovimientoColumnas(long Valor);
	public abstract void GenerarMovimientoFilas();
	public abstract int getID();
	public abstract ListaGeneral<Graficos> getLista();
	public abstract void AgregarMisil(Misil misil);
	public abstract void DibujarHileras(Graphics G2D);
	public abstract void VerificarColisiones();
	public abstract void DescontarEliminados(ArrayList<Graficos> listaEliminados, SpaceInvaders Juego);
	public abstract void EliminarLista();
	public abstract void DescontarNumeroAliens(SpaceInvaders Juego);
	public abstract void CorrerAlCentro(Graficos AlienEliminado);
	public abstract void RestablecerValorDesCol();
	public abstract void GenerarIntercambioJefe(SpaceInvaders Juego);
	public abstract void GenerarMovimientoReloj();
	public abstract Graficos getReferenciaJefe();

}
