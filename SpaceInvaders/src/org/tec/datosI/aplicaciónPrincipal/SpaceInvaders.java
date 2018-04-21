package org.tec.datosI.aplicaciónPrincipal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import org.tec.datosI.control.Teclado;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.juego.ClaseA;
import org.tec.datosI.juego.ClaseB;
import org.tec.datosI.juego.Misil;
import org.tec.datosI.juego.Nave_espacial;
import org.tec.datosI.juego.ClaseBasic;
import org.tec.datosI.juego.ClaseC;
import org.tec.datosI.juego.ClaseD;
import org.tec.datosI.juego.ClaseE;
import org.tec.datosI.juego.HileraEnemigos;

public class SpaceInvaders extends Canvas {

	
	private static final long serialVersionUID = 1L;
	public ArrayList<Graficos> lista_eliminados = new ArrayList<Graficos>();	
	public ArrayList<HileraEnemigos> lista_Hileras = new ArrayList<HileraEnemigos>();	
	public HileraEnemigos hileraActual;
	Graficos nave;
	BufferStrategy buffer;
	public double desplazamiento = 300;
	public long tiempo_ultimo_disparo = 0;
	public long intervalo_disparo = 250;
	public int num_aliens = 8;
	public String mensaje = "";
	public boolean juego_iniciado = true;
	public boolean tecla_no_pulsada = true;
	public boolean flecha_izquierda_pulsada = false;
	public boolean flecha_derecha_pulsada = false;
	public boolean disparado= false;
	public boolean alcanzado_limite = false;	
	public int numeroHileraActual = 4;	
	public int numeroHileraSiguiente = 0;	
	Random aleatorio = new Random(System.currentTimeMillis());



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
		Teclado teclado = new Teclado(this);
		addKeyListener(teclado);
		createBufferStrategy(2);
		buffer = getBufferStrategy();
		inicializarHileras();	
		establecerHileraActual();		
		añadir_Sprites();

	}	
	
	
	public void inicializarHileras() {
		
		HileraEnemigos claseBasic = new ClaseBasic();
		HileraEnemigos claseA = new ClaseA();
		HileraEnemigos claseB = new ClaseB();
		HileraEnemigos claseC = new ClaseC();
		HileraEnemigos claseD = new ClaseD();
		HileraEnemigos claseE = new ClaseE();
		lista_Hileras.add(claseBasic);
		lista_Hileras.add(claseA);
		lista_Hileras.add(claseB);
		lista_Hileras.add(claseC);
		lista_Hileras.add(claseD);
		lista_Hileras.add(claseE);
		
	}
	
	public void establecerHileraActual() {
		
		for(int i = 0; i < lista_Hileras.size(); i++) {
			
			if(numeroHileraActual == lista_Hileras.get(i).getID()) {
				
				hileraActual = (HileraEnemigos)lista_Hileras.get(i);
			}
			
		}
		
	}
	

	public void iniciar_juego() {
		
		//numeroHileraActual = aleatorio.nextInt(6);
		
		//aleatorio.setSeed(System.currentTimeMillis());
		
		//numeroHileraSiguiente = aleatorio.nextInt(6);
		
		//aleatorio.setSeed(System.currentTimeMillis());
		
		//System.out.println(numeroHileraActual + "  ->  " + numeroHileraSiguiente);
		inicializarHileras();
		establecerHileraActual();
		hileraActual.EliminarLista();
		añadir_Sprites();
		flecha_izquierda_pulsada = false;
		flecha_derecha_pulsada = false;
		disparado = false;
	
	}

	public void añadir_Sprites() {
		
		num_aliens = 0;
		nave = new Nave_espacial(this,"/org/tec/datosI/imagenes/Nave2.png",350,490);
		hileraActual.CrearHilera(nave,"/org/tec/datosI/imagenes/AlienBasico.png", "/org/tec/datosI/imagenes/Jefe.png", this);
	}

	public void notificar_perdedor() 
	{
		mensaje = "GAME OVER";
		tecla_no_pulsada = true;
	}

	public void notificar_ganador() 
	{
		mensaje = "LEVEL COMPLETED";
		tecla_no_pulsada = true;
	}

	public void añadir_misil() {

		if (System.currentTimeMillis() - tiempo_ultimo_disparo < intervalo_disparo) 
		{
			return;
		}

		tiempo_ultimo_disparo = System.currentTimeMillis();
		Misil misil = new Misil(this,"/org/tec/datosI/imagenes/misil.gif",((int)nave.columna) + 28,((int)nave.fila) - 10);
		hileraActual.AgregarMisil(misil);

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
				hileraActual.GenerarMovimientoColumnas(valor);
			}
			
			hileraActual.DibujarHileras(G2D);
			hileraActual.VerificarColisiones();
			hileraActual.DescontarEliminados(lista_eliminados);
			lista_eliminados.clear();

			if (alcanzado_limite) 
			{	
				hileraActual.GenerarMovimientoFilas();
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
				nave.desplazamiento_columna = - desplazamiento;

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
	
	public static void main(String args[]) {

		SpaceInvaders spaceInvaders = new SpaceInvaders();
		spaceInvaders.controlar_juego();
	}


}
