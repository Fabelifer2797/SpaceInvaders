package org.tec.datosI.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.tec.datosI.aplicaciónPrincipal.SpaceInvaders;

public class Teclado extends KeyAdapter {
	
	private int pulsaciones = 1;
	private SpaceInvaders juego;
	
	
	public Teclado(SpaceInvaders Juego) {
		
		this.juego = Juego;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (this.juego.tecla_no_pulsada) 
		{
			return;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) 
		{
			this.juego.flecha_izquierda_pulsada = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) 
		{
			this.juego.flecha_derecha_pulsada = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
		{
			this.juego.disparado= true;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (this.juego.tecla_no_pulsada) 
		{
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) 
		{
			this.juego.flecha_izquierda_pulsada = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) 
		{
			this.juego.flecha_derecha_pulsada = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
		{
			this.juego.disparado= false;
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		

		if (this.juego.tecla_no_pulsada) 
		{
			if (pulsaciones == 1) 
			{
				this.juego.tecla_no_pulsada = false;

				this.juego.iniciar_juego();

				pulsaciones = 0;

			} else {
				pulsaciones++;
			}
		}

	}
		
		
}
