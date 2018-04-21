package org.tec.datosI.listasEnlazadas;


public class NodoLista <T> {
	
	private NodoLista<T> siguiente;
	private NodoLista<T> anterior;
	private T valor;
	private String code;

	public NodoLista(T valor, String code) {
		this.valor = valor;
		this.code = code;
		this.siguiente = null;
		this.anterior = null;
	}
	
	public NodoLista(T valor, String code, NodoLista<T> siguiente, NodoLista<T> anterior) {
		this.valor = valor;
		this.code = code;
		this.siguiente = siguiente;
		this.anterior = anterior;
		
	}
	
	public NodoLista<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(NodoLista<T> siguiente) {
		this.siguiente = siguiente;
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}
	
	public NodoLista<T> getAnterior() {
		return anterior;
	}

	public void setAnterior(NodoLista<T> anterior) {
		this.anterior = anterior;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
