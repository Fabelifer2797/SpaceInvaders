package org.tec.datosI.listasEnlazadas;


/*
 * Clase que permite la creación de una lista doble genérica con sus respectivos métodos principales
 * @author Fabricio Elizondo
 */

public class ListaDoble <T> extends ListaGeneral<T>{
	
	private NodoLista<T> primero;
	
	public ListaDoble()
	{
		this.primero = null;
	}
	
	@Override
	public boolean ListaVacia()
	{
		return this.primero == null;
	}
	
	@Override
	public NodoLista<T> getPrimero() {
		return primero;
	}
	
	@Override
	public void setPrimero(NodoLista<T> primero) {
		
		this.primero = primero;
	}
	
	@Override
	public void InsertarFinalLista(T Valor, String Code)
	{
		if(this.getPrimero() == null)
		{
			NodoLista<T> nuevo = new NodoLista<T>(Valor, Code);
			this.setPrimero(nuevo);
		}
		else
		{
			NodoLista<T> actual = this.getPrimero();
			
			while(actual.getSiguiente() != null)
			{
				actual = actual.getSiguiente();
			}
			
			NodoLista<T> nuevo = new NodoLista<T>(Valor, Code);
			actual.setSiguiente(nuevo);
			nuevo.setAnterior(actual);
		}
	}
	
	@Override
	public void EliminarLista(T Valor)
	{
		
		if(this.ListaVacia())
		{
			System.out.println("Lista Doble Vacía");
		}
		
		else
		{
			NodoLista<T> actual = this.getPrimero();
			NodoLista<T> anterior = null;
			
			while (actual != null)
			{
				if(actual.getValor().equals(Valor))
				{
					break;
					
				}
				else
				{
					anterior = actual;
					actual = actual.getSiguiente();
				}
			}
			
			if(actual == null)
			{
				System.out.println("Elemento a eliminar no encontrado");
			}
			else if(actual == this.getPrimero())
			{
				this.setPrimero(actual.getSiguiente());
				actual.setSiguiente(null);
				this.getPrimero().setAnterior(null);
			}
			else if(actual.getSiguiente() == null)
			{
				anterior.setSiguiente(null);
				actual.setAnterior(null);
			}
			
			else
			{
				anterior.setSiguiente(actual.getSiguiente());
				actual.getSiguiente().setAnterior(anterior);
				actual.setSiguiente(null);
				actual.setAnterior(null);
				
			}
			
		}
	}
	
	@Override
	public NodoLista<T> BuscarLista(T Valor)
	{
		NodoLista<T> actual = this.getPrimero();
		
		while(actual != null)
		{
			if(actual.getValor().equals(Valor))
			{
				System.out.println("Elemento " + Valor.toString() + " encontrado");
				return actual;
			}
			
			else
			{
				actual = actual.getSiguiente();
			}
		}
		
		System.out.println("Elemento " + Valor.toString() + " no encontrado");
		return null;
	}
	
	@Override
	public void ImprimirLista() {
		
		NodoLista<T> actual = this.getPrimero();
		
		while (actual != null) {
			
			System.out.println(actual.getValor().toString());
			actual = actual.getSiguiente();
		}		
	}

	@Override
	public int ObtenerTamañoLista() {
		
		NodoLista<T> actual = this.getPrimero();
		int Contador = 0;
		
		while (actual != null) {
			
			Contador++;
			actual = actual.getSiguiente();
		}
		
		return Contador;
		
	}
}