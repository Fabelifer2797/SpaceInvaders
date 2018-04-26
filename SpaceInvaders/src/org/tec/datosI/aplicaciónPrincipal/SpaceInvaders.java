package org.tec.datosI.aplicaciónPrincipal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.tec.datosI.control.Teclado;
import org.tec.datosI.graficos.BufferedImageLoader;
import org.tec.datosI.graficos.Graficos;
import org.tec.datosI.graficos.SpriteSheet;
import org.tec.datosI.juego.ClaseA;
import org.tec.datosI.juego.ClaseB;
import org.tec.datosI.juego.Misil;
import org.tec.datosI.juego.Nave_espacial;
import org.tec.datosI.listasEnlazadas.ListaGeneral;
import org.tec.datosI.listasEnlazadas.NodoLista;
import org.tec.datosI.sound.Sound;
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
	public int numeroHileraActual = 0;	
	public int numeroHileraSiguiente = 0;	
	Random aleatorio = new Random(System.currentTimeMillis());
	public int contadorCambio = 0;
	public int contadorCambio2 = 0;
	public boolean movimientoReloj = false;
	public boolean alcanzadoLimiteRelojC = false;
	public double cambioVelocidad = 0;
	public Graficos fondo;
	public BufferedImage spriteExplosion;
	public BufferedImage explosion;
	public SpriteSheet hojaSpritesE;
	public int  contadorSprites = 0;
	public int contadorExplosion = 1;
	public double coorXE;
	public double coorYE;
	public boolean Explosion = false;
	public Sound disparo = new Sound("/org/tec/datosI/sonidos/shoot.wav");
	public Sound beep = new Sound("/org/tec/datosI/sonidos/beep.wav");
	public Sound boop  = new Sound("/org/tec/datosI/sonidos/boop.wav");
	private boolean beepboop;
	private Font gameScreen = new Font("Arial", Font.PLAIN, 30);
	private Font informacion = new Font("Arial",Font.PLAIN,15);
	private int nivel = 1;
	public int puntaje = 0;
	private boolean titulo = true;
	private static final ImageIcon icono = new ImageIcon(SpaceInvaders.class.getResource("/org/tec/datosI/imagenes/Icono.png"));

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
		ventana.setIconImage(icono.getImage());
		requestFocus();
		Teclado teclado = new Teclado(this);
		addKeyListener(teclado);
		createBufferStrategy(2);
		buffer = getBufferStrategy();
		inicializarHileras();	
		establecerHileraActual();		
		añadir_Sprites(); 
		fondo = new Nave_espacial(this, "/org/tec/datosI/imagenes/Fondo.jpg", 0,0);
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteExplosion = loader.loadImage("/org/tec/datosI/imagenes/Explosion.png");
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		


	}	
	
	
	public void inicializarHileras() {
		lista_Hileras.clear();
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
		
		numeroHileraActual = numeroHileraSiguiente;
		numeroHileraSiguiente = aleatorio.nextInt(6);
		System.out.println(numeroHileraActual + "  ->  " + numeroHileraSiguiente);
		inicializarHileras();
		establecerHileraActual();
		hileraActual.EliminarLista();
		añadir_Sprites();
		flecha_izquierda_pulsada = false;
		flecha_derecha_pulsada = false;
		disparado = false;
		cambiarVelocidadxNivel();
	
	}
	
	public void cambiarVelocidadxNivel() {
		
		if(nivel == 1) {
			
			cambioVelocidad = 0;
		}
		cambioVelocidad += 40;
		
		if(this.hileraActual.getID() == 5) {
			
			this.hileraActual.getReferenciaJefe().desplazamiento_columna -= cambioVelocidad;
		}
		
		ListaGeneral<Graficos> listaActual = this.hileraActual.getLista();
		NodoLista<Graficos> actual = listaActual.getPrimero();
		int contador = 0;
		
		while (contador < listaActual.ObtenerTamañoLista()) {
			
			if(actual.getCode() == "A" || actual.getCode() == "J") {
				
				actual.getValor().desplazamiento_columna -= cambioVelocidad;
				contador++;
				actual = actual.getSiguiente();
			}
			
			else {
				
				contador++;
				actual = actual.getSiguiente();
			}
			
		}
		
	}
	
	public String establecerMensajeHilera (int Numero) {
		
		if(Numero == 0) {
			return "Clase Basic";
			
		}
		
		else if (Numero == 1) {
			return "Clase A";
			
		}
		
		else if (Numero == 2) {
			return "Clase B";
			
		}
		
		else if (Numero == 3) {
			return "Clase C";
			
		}
		
		else if (Numero == 4) {
			return "Clase D";
			
		}
		
		else {
			return "Clase E";
		}
		
		
	}

	public void añadir_Sprites() {
		
		num_aliens = 0;
		nave = new Nave_espacial(this,"/org/tec/datosI/imagenes/Nave2.png",350,490);
		hileraActual.CrearHilera(nave,"/org/tec/datosI/imagenes/AlienBasico.png", "/org/tec/datosI/imagenes/Jefe.png", this);
	}

	public void notificar_perdedor() 
	{
		mensaje = "GAME OVER";
		nivel = 1;
		puntaje = 0;
		tecla_no_pulsada = true;
		num_aliens = 8;
		inicializarHileras();	
		establecerHileraActual();		
		añadir_Sprites(); 
		
		if(disparo.isPlaying()) {
			disparo.stop();
		}
	}

	public void notificar_ganador() 
	{
		mensaje = "LEVEL COMPLETED";
		nivel++;
		puntaje += 1000;
		tecla_no_pulsada = true;
		num_aliens = 8;
		inicializarHileras();	
		establecerHileraActual();		
		añadir_Sprites(); 
		if(disparo.isPlaying()) {
			disparo.stop();
		}
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
			fondo.dibujar_graficos(G2D);
			G2D.setFont(informacion);
			G2D.setColor(Color.white);
			G2D.drawString("Hilera Actual: ", 10, 20);
			G2D.setColor(Color.red);
			G2D.drawString(this.establecerMensajeHilera(this.numeroHileraActual), 100, 20);
			G2D.setColor(Color.white);
			G2D.drawString("Hilera Siguiente: ", 200, 20);
			G2D.setColor(Color.red);
			G2D.drawString(this.establecerMensajeHilera(this.numeroHileraSiguiente), 320, 20);
			G2D.setColor(Color.white);
			G2D.drawString("Nivel Actual: ", 420, 20);
			G2D.setColor(Color.red);
			String nivelS = String.valueOf(nivel);
			G2D.drawString(nivelS, 510, 20);
			G2D.setColor(Color.white);
			G2D.drawString("Puntaje: ", 560, 20);
			G2D.setColor(Color.red);
			String puntajeS = String.valueOf(puntaje);
			G2D.drawString(puntajeS, 630, 20);
			
			if(titulo) {
				G2D.setColor(Color.red);
				G2D.setFont(gameScreen);
				int width = G2D.getFontMetrics().stringWidth("Space Invaders");
				G2D.drawString("Space Invaders",(800 / 2) - (width / 2),600 / 2);
			}
			
			
			if (!tecla_no_pulsada) 
			{
				titulo = false;
				hileraActual.GenerarMovimientoColumnas(valor);
			}
			
			
			hileraActual.DibujarHileras(G2D);
			
			hojaSpritesE = new SpriteSheet(spriteExplosion);
			
			if(Explosion) {
				
				if(contadorSprites == 2) {
					
					explosion = hojaSpritesE.grabImage(contadorExplosion, 1, 100, 100);
					G2D.drawImage(explosion,(int)this.coorXE,(int)this.coorYE,null);
					contadorSprites = 0;
					
					if(contadorExplosion == 4) {
						contadorExplosion = 1;
						Explosion = false;
					}
					else {
						contadorExplosion++;
					}
				}	
				
				contadorSprites++;
				
				
			}
			
			
			if(contadorCambio == 100) {
				
				hileraActual.GenerarIntercambioJefe(this);
				contadorCambio = 0;
				
			}
			contadorCambio++;
			
			if(contadorCambio2 == 7) {
				hileraActual.GenerarMovimientoReloj();
				contadorCambio2 = 0;
			}
			contadorCambio2++;
			hileraActual.RestablecerValorDesCol();
			hileraActual.VerificarColisiones();
			hileraActual.DescontarEliminados(lista_eliminados, this);
			lista_eliminados.clear();

			if (alcanzado_limite) 
			{	
				hileraActual.GenerarMovimientoFilas();
				alcanzado_limite = false;
				
				if (beepboop) {
					beepboop = false;
					boop.play();
				} else {
					beepboop = true;
					beep.play();
				}
			}

			if (tecla_no_pulsada) 
			{
				G2D.setColor(Color.red);
				G2D.setFont(gameScreen);
				int width = G2D.getFontMetrics().stringWidth(mensaje);
				G2D.drawString(mensaje,(800 / 2) - (width / 2),600 / 2);
				G2D.setColor(Color.white);
				G2D.drawString("Pulse una tecla para continuar",200,400);
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
				if(!disparo.isPlaying()) {
					disparo.play();
				}
				
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
