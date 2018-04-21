package org.tec.datosI.listasEnlazadas;


/*
 * Clase que permite la creación de una lista circular simple genérica con sus respectivos métodos principales
 * @author Fabricio Elizondo
 */


public class ListaCircular<T> extends ListaGeneral<T> {
	
	private NodoLista<T> primero;
	
	public ListaCircular()
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
	public void InsertarFinalLista(T Valor,String Code)
	{
		if (this.getPrimero() == null)
		{	
			NodoLista<T> nuevo = new NodoLista<T>(Valor,Code);
			this.setPrimero(nuevo);
			nuevo.setSiguiente(nuevo);
		}
		
		else
		{
			NodoLista<T> actual = this.getPrimero();
			
			while (actual.getSiguiente() != this.getPrimero())
			{
				actual = actual.getSiguiente();
			}
			
			NodoLista<T> nuevo = new NodoLista<T>(Valor, Code);
			actual.setSiguiente(nuevo);
			nuevo.setSiguiente(this.getPrimero());
		}
	}
	
	@Override
	public void EliminarLista(T Valor)
	{
		NodoLista<T> actual = this.getPrimero();
		NodoLista<T> anterior = this.getPrimero();
		
		while(anterior.getSiguiente() != this.getPrimero())
		{
			anterior = anterior.getSiguiente();
		}
		
		if(actual == null)
		{
			System.out.println("Lista Circular Vacía");
		}
		
		else if (this.BuscarLista(Valor) == null)
		{
			System.out.println("Elemento a eliminar no encontrado");
		}
		
		else
		{
			while (actual.getSiguiente() != this.getPrimero())
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
			
			if(actual == this.getPrimero() && this.ObtenerTamañoLista() != 1)
			{
				this.setPrimero(actual.getSiguiente());
				anterior.setSiguiente(this.getPrimero());
			}
			else if(actual == this.getPrimero() && this.ObtenerTamañoLista() == 1) {
				
				this.setPrimero(null);
			}
			
			else
			{
				anterior.setSiguiente(actual.getSiguiente());
				actual.setSiguiente(null);
			}
			
		}
		
	}
	
	@Override
	public NodoLista<T> BuscarLista(T Valor) {
		
		if(this.ListaVacia())
		{
			System.out.println("Lista Doble Circular Vacía");
			return null;
		}
		
		NodoLista<T> actual = this.getPrimero();
		
		while(actual.getSiguiente() != this.getPrimero())
		{
			if( actual.getValor().equals(Valor))
			{
				//System.out.println("Elemento " + Valor.toString() + " encontrado");
				return actual;
			}
			
			else
			{
				actual = actual.getSiguiente();
			}
		}
		
		if(actual.getValor().equals(Valor))
		{
			//System.out.println("Elemento " + Valor.toString() + " encontrado");
			return actual;
		}
		
		else
		{
			//System.out.println("Elemento " + Valor.toString() + " no encontrado");
			return null;
		}
	}
	
	@Override
	public void ImprimirLista()
	{	
		if(this.ListaVacia())
		{
			System.out.println("Lista Circular Vacía");
			return;
		}
		
		NodoLista<T> actual = this.getPrimero();
		
		
		while (actual.getSiguiente() != this.getPrimero())
		{
			System.out.println(actual.getValor().toString());
			actual = actual.getSiguiente();
		}
		
		System.out.println(actual.getValor().toString());
			
	}

	@Override
	public int ObtenerTamañoLista() {
		
		if(this.ListaVacia())
		{
			return 0;
		}
		
		int Contador = 0;
		NodoLista<T> actual = this.getPrimero();
		
		
		while (actual.getSiguiente() != this.getPrimero())
		{
			Contador++;
			actual = actual.getSiguiente();
		}
		
		Contador++;
		//System.out.println(Contador);
		return Contador;
		
	}
	
//	public static void main(String[] args) {
//		
//		ListaCircular<Integer> lista1 = new ListaCircular<Integer>();
//		lista1.InsertarFinalLista(4, "");
//		lista1.EliminarLista(4);
//		lista1.ImprimirLista();
//	}

}
