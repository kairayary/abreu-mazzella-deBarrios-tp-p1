package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	private Entorno entorno;
	private Cuadra[][] cuadras;
	private Repartidor repartidor;
	private Enemigo[] enemigos; 
	
	// Variables para el HUD visual  
	private int enemigosEliminados = 0;
	private Image iconoTiempo;
    private Image iconoVidas;
    private Image iconoCajaPedido;
    private Image iconoEnemigos;
    private Image iconoPedidoActual;
	

	// Variables para la Dinámica de Pedidos y Temporizador 
	private int estadoPedido = 0; // 0 = Esperando (ir al negocio), 1 = En camino (ir a la casa)
	private Negocio negocioDestinoActual = null;
	private Casa casaDestinoActual = null;
	private int tiempoRestante = 0;        
	private final int TIEMPO_MAXIMO = 1500; // Ticks límites para completar el pedido
	private int pedidosEntregados = 0;
	private int pedidosPerdidos = 0;
	private Image iconoFlechaRoja;
	private Image iconoFlechaVerde;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Inicializar el repartidor
		this.repartidor = new Repartidor(200, 300); 
		
		// Cargar iconos para el HUD y las flechas indicadoras
		this.iconoTiempo = Herramientas.cargarImagen("img/tiempo.png");
	    this.iconoVidas = Herramientas.cargarImagen("img/vidas.png");
	    this.iconoCajaPedido = Herramientas.cargarImagen("img/cajaPedido.png");
	    this.iconoEnemigos = Herramientas.cargarImagen("img/enemigos.png");
	    this.iconoPedidoActual = Herramientas.cargarImagen("img/pedidoActual.png");
	    this.iconoFlechaRoja = Herramientas.cargarImagen("img/flechaRoja.png");
	    this.iconoFlechaVerde = Herramientas.cargarImagen("img/flechaVerde.png");
		
		// Inicializacion de la grilla de 4x4 cuadras
		this.cuadras = new Cuadra[4][4];
		
		int offsetX = 130; // Margen inicial horizontal
		int offsetY = 150; // Margen inicial vertical 
		int espacioX = 170; // Separación entre columnas de cuadras
		int espacioY = 170; // Separación entre filas de cuadras

		for (int f = 0; f < 4; f++) {
			for (int c = 0; c < 4; c++) {
				int xPos = offsetX + (c * espacioX);
				int yPos = offsetY + (f * espacioY);
				this.cuadras[f][c] = new Cuadra(xPos, yPos);
			}
		}
		
		// Inicialización de enemigos
		this.enemigos = new Enemigo[4];
		this.enemigos[0] = new Enemigo(240, 195, "HORIZONTAL"); // Calle libre horizontal
		this.enemigos[1] = new Enemigo(420, 195, "VERTICAL");   // Calle libre vertical
		this.enemigos[2] = new Enemigo(240, 325, "HORIZONTAL"); // Calle libre horizontal
		this.enemigos[3] = new Enemigo(600, 325, "VERTICAL");   // Calle libre vertical

		// Generar el primer pedido al iniciar el juego
		generarNuevoPedido();

		// Inicia el juego!
		this.entorno.iniciar();
	}

	// Método auxiliar para generar un pedido aleatorio
	private void generarNuevoPedido() {
		int fNeg = (int) (Math.random() * 4);
		int cNeg = (int) (Math.random() * 4);
		this.negocioDestinoActual = this.cuadras[fNeg][cNeg].negocio;

		int fCas = (int) (Math.random() * 4);
		int cCas = (int) (Math.random() * 4);
		int kCas = (int) (Math.random() * 3);
		this.casaDestinoActual = this.cuadras[fCas][cCas].casas[kCas];

		this.estadoPedido = 0; // Estado inicial: Esperando que el repartidor vaya al negocio
		this.tiempoRestante = TIEMPO_MAXIMO;
		System.out.println("¡Nuevo pedido generado! Recógelo en el negocio marcado.");
	}

	public void tick()
	{
	    this.entorno.colorFondo(Color.DARK_GRAY);

	    // DIBUJAR ENCABEZADO / HUD SUPERIOR 
	    this.entorno.dibujarRectangulo(400.0, 25.0, 800.0, 50.0, 0.0, Color.BLACK);
	    Color colorCuadro = new Color(55, 70, 50);
	    
	    int segundosRestantes = this.tiempoRestante / 60; // Conversión aproximada de ticks a segundos

	    // TIEMPO
	    this.entorno.dibujarRectangulo(82.0, 25.0, 145.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoTiempo, 30.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Tiempo: " + segundosRestantes + "s", 50, 30);

	    // VIDAS
	    this.entorno.dibujarRectangulo(230.0, 25.0, 130.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoVidas, 185.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Vidas: " + this.repartidor.vidas, 205, 30);

	    // PEDIDOS ENTREGADOS
	    this.entorno.dibujarRectangulo(395.0, 25.0, 175.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoCajaPedido, 325.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Entregados: " + this.pedidosEntregados, 345, 30);

	    // ENEMIGOS ELIMINADOS
	    this.entorno.dibujarRectangulo(570.0, 25.0, 155.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoEnemigos, 515.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Eliminados: " + this.enemigosEliminados, 535, 30);

	    // ESTADO DEL PEDIDO
	    String textoEstado = (this.estadoPedido == 0) ? "Esperando" : "En camino";
	    this.entorno.dibujarRectangulo(722.0, 25.0, 135.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoPedidoActual, 675.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Pedido: " + textoEstado, 695, 30);

		// Dibuja la grilla de la ciudad (Cuadras, Casas y Negocios)
	    for (int f = 0; f < 4; f++) {
	        for (int c = 0; c < 4; c++) {
	            this.cuadras[f][c].dibujar(this.entorno);
	        }
	    }
	    
	    // DIBUJAR FLECHA INDICADORA SEGÚN EL ESTADO DEL PEDIDO 
	    if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
	        this.entorno.dibujarImagen(this.iconoFlechaRoja, this.negocioDestinoActual.x, this.negocioDestinoActual.y - 35, 0, 0.25);
	    } else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
	    	
	    }
	 //  DIBUJAR FLECHA INDICADORA SEGÚN EL ESTADO DEL PEDIDO 
	    if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
	        // Estado 0 (Esperando): Flecha roja apuntando al negocio (escala 0.25 para que se vea bien)
	        this.entorno.dibujarImagen(this.iconoFlechaRoja, this.negocioDestinoActual.x, this.negocioDestinoActual.y - 35, 0, 0.25);
	    } else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
	        // Estado 1 (En camino): Flecha verde apuntando a la casa de destino

	        this.entorno.dibujarImagen(this.iconoFlechaVerde, this.casaDestinoActual.x, this.casaDestinoActual.y - 35, 0, 0.25);
	    }

		// Dimensiones de la caja de colisión del repartidor
		int anchoRepartidor = 30;
		int altoRepartidor = 40;


		// --- Movimiento arriba (W) ---

		if (this.entorno.estaPresionada('w') || this.entorno.estaPresionada('W')) {
			int proximoY = this.repartidor.y - 2;
			boolean colisiona = false;
			
			// Límite superior de la pantalla (HUD)
			if (proximoY - (altoRepartidor / 2) < 50) {
				colisiona = true;
			}
			
			// Colisión con las cuadras
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverArriba();
			}
		}

				if (this.entorno.estaPresionada('w') || this.entorno.estaPresionada('W')) {
					int proximoY = this.repartidor.y - 2;
					boolean colisiona = false;
					
					for (int f = 0; f < 4; f++) {
						for (int c = 0; c < 4; c++) {
							if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) {
								colisiona = true;
							}
						}
					}
					
					if (!colisiona) {
						this.repartidor.moverArriba();
					}
				}
				
		// --- Movimiento abajo (S) ---
		if (this.entorno.estaPresionada('s') || this.entorno.estaPresionada('S')) {
			int proximoY = this.repartidor.y + 2;
			boolean colisiona = false;
			
			// Límite inferior de la pantalla
			if (proximoY + (altoRepartidor / 2) > this.entorno.alto()) {
				colisiona = true;
			}
			
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverAbajo(this.entorno.alto());
			}
		}

		// --- Movimiento izquierda (A) ---
		
		if (this.entorno.estaPresionada('a') || this.entorno.estaPresionada('A')) {
			int proximoX = this.repartidor.x - 2;
			boolean colisiona = false;
			
			// Límite izquierdo de la pantalla
			if (proximoX - (anchoRepartidor / 2) < 0) {
				colisiona = true;
			}
			
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(proximoX, this.repartidor.y, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverIzquierda();
			}
		}

		// --- Movimiento derecha (D) ---
		if (this.entorno.estaPresionada('d') || this.entorno.estaPresionada('D')) {
			int proximoX = this.repartidor.x + 2;
			boolean colisiona = false;
			
			// Límite derecho de la pantalla
			if (proximoX + (anchoRepartidor / 2) > this.entorno.ancho()) {
			    colisiona = true;
			}
			
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(proximoX, this.repartidor.y, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverDerecha(this.entorno.ancho());
			}
		}
		
		// --- Movimiento y dibujo de los enemigos ---
		for (int i = 0; i < this.enemigos.length; i++) {
	        if (this.enemigos[i] != null) { 
	            this.enemigos[i].mover(this.entorno.ancho(), this.entorno.alto(), this.cuadras);
	            this.enemigos[i].dibujar(this.entorno);
	        }
	    }
		
		// --- Dibujamos al repartidor ---
		this.repartidor.dibujar(this.entorno);
		
		// --- Control del Temporizador de Entrega ---
		if (this.tiempoRestante > 0) {
			this.tiempoRestante--;
			if (this.tiempoRestante <= 0) {
				this.pedidosPerdidos++;
				System.out.println("¡Tiempo agotado! Pedido perdido.");
				generarNuevoPedido(); // Se genera un nuevo pedido automáticamente
			}
		}

		// --- Dinámica de Pedidos: Activación con la tecla 'E' ---
		if (this.entorno.estaPresionada('e') || this.entorno.estaPresionada('E')) {
			// Estado 0: Tomar pedido en el negocio
			if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
				if (this.negocioDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.estadoPedido = 1; // Pasamos a "En camino"
					this.tiempoRestante = TIEMPO_MAXIMO; 
					System.out.println("¡Pedido recogido! En camino a la casa de destino.");
				}
			} 
			// Estado 1: Entregar pedido en la casa destino
			else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
				if (this.casaDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.pedidosEntregados++;
					System.out.println("¡Pedido entregado con éxito!");
					generarNuevoPedido(); // Nuevo ciclo de pedido
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}