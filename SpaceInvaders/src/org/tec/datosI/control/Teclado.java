package org.tec.datosI.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {
	
	private final static int numeroTeclas = 120;
	private final boolean[] teclas = new boolean[numeroTeclas];
	
	public boolean izquierda;
	public boolean derecha;
	
	public void actualizar() {
		
		izquierda = teclas[KeyEvent.VK_A];
		derecha = teclas[KeyEvent.VK_D];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		teclas[e.getKeyCode()] = true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		teclas[e.getKeyCode()] = false;
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
