package org.tec.datosI.listasEnlazadas;


/*
 * Clase que permite la creación de una lista doble circular genérica con sus respectivos métodos principales
 * @author Fabricio Elizondo
 */

public class ListaDobleCircular <T> extends ListaGeneral<T>{
	
	private NodoLista<T> primero;
	
	public ListaDobleCircular()
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
	public void InsertarFinalLista(T valor, String Code)
	{
		if(this.getPrimero() == null)
		{
			NodoLista<T> primero = new NodoLista<T>(valor, Code);
			this.setPrimero(primero);
			this.getPrimero().setSiguiente(primero);
			this.getPrimero().setAnterior(primero);
		}
		
		else
		{
			NodoLista<T> actual = this.getPrimero();
			
			while(actual.getSiguiente() != this.getPrimero())
			{
				actual = actual.getSiguiente();
			}
			
			NodoLista<T> nuevo = new NodoLista<T>(valor, Code);
			actual.setSiguiente(nuevo);
			nuevo.setAnterior(actual);
			nuevo.setSiguiente(this.getPrimero());
			this.getPrimero().setAnterior(nuevo);
		}
	}
	
	@Override
	public void EliminarLista(T valor)
	{
		NodoLista<T> actual = this.getPrimero();
		NodoLista<T> anterior = this.getPrimero().getAnterior();
	
		if(this.ListaVacia())
		{
			System.out.println("Lista Doble Circular Vacía");
		}
		
		else if (this.BuscarLista(valor) == null)
		{
			//System.out.println("Elemento a eliminar no encontrado");
		}
		
		else
		{
			while (actual.getSiguiente() != this.getPrimero())
			{
				if(actual.getValor().equals(valor))
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
				this.primero.setAnterior(anterior);
			}
			
			else if(actual == this.getPrimero() && this.ObtenerTamañoLista() == 1) {
				
				this.setPrimero(null);
			}
			
			else
			{
				anterior.setSiguiente(actual.getSiguiente());
				actual.getSiguiente().setAnterior(anterior);
			}
			
		}
	}
	
	@Override
	public NodoLista<T> BuscarLista(T valor)
	{
		if(this.ListaVacia())
		{
			System.out.println("Lista Doble Circular Vacía");
			return null;
		}
		
		NodoLista<T> actual = this.getPrimero();
		
		while(actual.getSiguiente() != this.getPrimero())
		{
			if(actual.getValor().equals(valor))
			{
				//System.out.println("Elemento " + valor.toString() + " encontrado");
				return actual;
			}
			
			else
			{
				actual = actual.getSiguiente();
			}
		}
		
		if(actual.getValor().equals(valor))
		{
			//System.out.println("Elemento " + valor.toString() + " encontrado");
			return actual;
		}
		
		else
		{
			//System.out.println("Elemento " + valor.toString() + " no encontrado");
			return null;
		}
	}
	
	@Override
	public void ImprimirLista()
	{
		if(this.ListaVacia())
		{
			System.out.println("Lista Doble Circular Vacía");
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
	
	public static void main(String[] args) {
		
		ListaDobleCircular<Integer> lista1 = new ListaDobleCircular<Integer>();
		lista1.InsertarFinalLista(3, "");
		lista1.EliminarLista(3);
		lista1.ImprimirLista();
	}

}
