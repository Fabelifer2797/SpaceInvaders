package org.tec.datosI.aplicaciónPrincipal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.juego.Alien;
import org.tec.datosI.juego.Misil;
import org.tec.datosI.juego.Nave_espacial;

public class SpaceInvaders extends Canvas {

	
	private static final long serialVersionUID = 1L;
	
	public ArrayList lista_imagenes= new ArrayList();
	
	public ArrayList lista_eliminados = new ArrayList();
	
	Graficos nave;
	
	BufferStrategy buffer;
	
	public double desplazamiento = 300;
	
	public long tiempo_ultimo_disparo = 0;
	
	public long intervalo_disparo = 500;
	
	public int num_aliens;
	
	public String mensaje = "";
        
    public boolean juego_iniciado = true;
	
	public boolean tecla_no_pulsada = true;
	
	public boolean flecha_izquierda_pulsada = false;
	
	public boolean flecha_derecha_pulsada = false;
	
	public boolean disparado= false;
	
	public boolean alcanzado_limite = false;
	
	
	
	public SpaceInvaders() {
		
		JFrame ventana = new JFrame();
		
		ventana.getContentPane().setPreferredSize(new Dimension(800,600));
		
		ventana.setTitle("Space Invaders");
		
		ventana.getContentPane().add(this);
                
		ventana.setResizable(false);
                
		ventana.setVisible(true);
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ventana.pack();
		
		ventana.setLocationRelativeTo(null);
		
		requestFocus();
		
		addKeyListener(new Control_tecla_pulsada());

		createBufferStrategy(2);
                
		buffer = getBufferStrategy();
		
		añadir_imagenes();
		
	}
	
void iniciar_juego() {
		
		lista_imagenes.clear();
                
		añadir_imagenes();
		
		flecha_izquierda_pulsada = false;
                
		flecha_derecha_pulsada = false;
                
		disparado= false;
	}
	
	void añadir_imagenes() {
		
		nave = new Nave_espacial(this,"/org/tec/datosI/imagenes/nave.gif",370,550);
		
                
		lista_imagenes.add(nave);
	
		
		num_aliens = 0;
                
		for (int f=0; f<1; f++) 
                {
			for (int c=0;c<12;c++) 
                        {
				Graficos alien = new Alien(this,"/org/tec/datosI/imagenes/alien.gif",100+(c*50),(50)+f*30);
                                
				lista_imagenes.add(alien);
                                
				num_aliens++;
			}
		}
	}
	
	public void notificar_perdedor() 
        {
		mensaje = "HAS PERDIDO INSIGNIFICANTE TERRÃ�COLA";
                
		tecla_no_pulsada = true;
	}
	
	public void notificar_ganador() 
        {
		mensaje = "HAS GANADO, PERO VOLVEREMOS...";
                
		tecla_no_pulsada = true;
	}
	
	public void descontar_eliminados() {
		
		num_aliens--;
		
		if (num_aliens == 0) 
                {
		  notificar_ganador();
		}
		
		for (int i=0;i<lista_imagenes.size();i++) 
                {
			Graficos grafico= (Graficos) lista_imagenes.get(i);
			
			if (grafico instanceof Alien) {
				
				grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
			}
		}
	}
	
	public void añadir_misil() {
		
		if (System.currentTimeMillis() - tiempo_ultimo_disparo < intervalo_disparo) 
                {
		 return;
		}
		
		tiempo_ultimo_disparo = System.currentTimeMillis();
                
		Misil misil = new Misil(this,"/org/tec/datosI/imagenes/misil.gif",((int)nave.columna) +10,((int)nave.fila)-30);
                
		lista_imagenes.add(misil);
                
                }
	
public void controlar_juego() 
        {
		long ultimo_tiempo = System.currentTimeMillis();

		
		while (juego_iniciado) {
			
			long valor = System.currentTimeMillis() - ultimo_tiempo;
                        
			ultimo_tiempo = System.currentTimeMillis();
			
			
			Graphics2D G2D = (Graphics2D) buffer.getDrawGraphics();
			
			 
		     G2D.setColor(Color.black);
		        
                        
             G2D.fillRect(0,0,800,600);
			
                        
			if (!tecla_no_pulsada) 
                        {
				for (int i=0;i<lista_imagenes.size();i++) 
                                {
					Graficos grafico= (Graficos) lista_imagenes.get(i);
					
					grafico.mover(valor);
				}
			}
			
		       for (int i=0;i<lista_imagenes.size();i++) 
                        {
				Graficos grafico= (Graficos) lista_imagenes.get(i);
				
				
				grafico.dibujar_graficos(G2D);
				
			}
			
		       for (int k=0; k<lista_imagenes.size(); k++) 
                        {
				for (int s=k+1; s<lista_imagenes.size(); s++) 
                                {
					Graficos grafico1 = (Graficos) lista_imagenes.get(k);
                                        
					Graficos grafico2 = (Graficos) lista_imagenes.get(s);
					
					if (grafico1.choca(grafico2)) 
                                        {
						grafico1.colisiona_con(grafico2);
                                                
						grafico2.colisiona_con(grafico1);
					}
				}
			}
			
			lista_imagenes.removeAll(lista_eliminados);
                        
			lista_eliminados.clear();

			if (alcanzado_limite) 
                        {
				for (int i=0;i<lista_imagenes.size();i++) 
                                {
				   Graficos grafico = (Graficos) lista_imagenes.get(i);
                                        
				   grafico.avanzar_aliens();
				}
				
				alcanzado_limite = false;
			}
			
			if (tecla_no_pulsada) 
                        {
				G2D.setColor(Color.white);
                                
				G2D.drawString(mensaje,(800-G2D.getFontMetrics().stringWidth(mensaje))/2,250);
                                
				G2D.drawString("Pulse una tecla para continuar",300,400);
			}
			
			G2D.dispose();
                        
			buffer.show();
			
			nave.desplazamiento_columna = 0;
			
			if (flecha_izquierda_pulsada)  
                        {
			  nave.desplazamiento_columna = -desplazamiento;
                                
			} else if (flecha_derecha_pulsada){
			                                    nave.desplazamiento_columna = desplazamiento;
			                                  }
			
			if(disparado) 
                        {
		          añadir_misil();
			}
			
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	
	class Control_tecla_pulsada extends KeyAdapter {
		
		int pulsaciones = 1;
		
		public void keyPressed(KeyEvent e) {
			
			if (tecla_no_pulsada) 
                        {
			  return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_A) 
                        {
			  flecha_izquierda_pulsada = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) 
                        {
			  flecha_derecha_pulsada = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                        {
			  disparado= true;
			}
		} 
		
		public void keyReleased(KeyEvent e) {
			
			if (tecla_no_pulsada) 
                        {
			  return;
			}
		        if (e.getKeyCode() == KeyEvent.VK_A) 
                        {
			  flecha_izquierda_pulsada = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) 
                        {
			   flecha_derecha_pulsada = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                        {
		          disparado= false;
			}
		}

		public void keyTyped(KeyEvent e) {
			
			if (tecla_no_pulsada) 
                        {
				if (pulsaciones == 1) 
                                {
				   tecla_no_pulsada = false;
                                   
				   iniciar_juego();
                                   
				   pulsaciones = 0;
                                   
				} else {
					pulsaciones++;
				       }
			}
			
	}
 }
	
	
	public static void main(String args[]) {
		
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.controlar_juego();
	}
	

}
