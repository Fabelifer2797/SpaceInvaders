package org.tec.datosI.listasEnlazadas;

public abstract class ListaGeneral<T> {
	
	public abstract boolean ListaVacia();
	public abstract NodoLista<T> getPrimero();
	public abstract void setPrimero(NodoLista<T> primero);
	public abstract void InsertarFinalLista(T Valor, String Code);
	public abstract void EliminarLista(T Valor);
	public abstract NodoLista<T> BuscarLista(T Valor);
	public abstract void ImprimirLista();
	public abstract int ObtenerTamañoLista();
	
}
