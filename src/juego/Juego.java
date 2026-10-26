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
	
	// --- Variables para el HUD visual (de Natalia) ---
	private int enemigosEliminados = 0;
	private Image iconoTiempo;
    private Image iconoVidas;
    private Image iconoCajaPedido;
    private Image iconoEnemigos;
    private Image iconoPedidoActual;
	
	// --- Variables para la Dinámica de Pedidos y Temporizador (de Kaira) ---
	private int estadoPedido = 0; // 0 = Esperando (ir al negocio), 1 = En camino (ir a la casa)
	private Negocio negocioDestinoActual = null;
	private Casa casaDestinoActual = null;
	private int tiempoRestante = 0;        
	private final int TIEMPO_MAXIMO = 1500; // Ticks límites para completar el pedido
	private int pedidosEntregados = 0;
	private int pedidosPerdidos = 0;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Inicializar el repartidor
		this.repartidor = new Repartidor(200, 300); 
		
		// Cargar iconos para el HUD
		this.iconoTiempo = Herramientas.cargarImagen("img/tiempo.png");
	    this.iconoVidas = Herramientas.cargarImagen("img/vidas.png");
	    this.iconoCajaPedido = Herramientas.cargarImagen("img/cajaPedido.png");
	    this.iconoEnemigos = Herramientas.cargarImagen("img/enemigos.png");
	    this.iconoPedidoActual = Herramientas.cargarImagen("img/pedidoActual.png");
		
		// Inicializacion de la grilla de 4x4 cuadras
		this.cuadras = new Cuadra[4][4];
		
		int offsetX = 130; // Margen inicial horizontal
		int offsetY = 120; // Margen inicial vertical 
		int espacioX = 170; // Separación entre columnas de cuadras
		int espacioY = 110; // Separación entre filas de cuadras

		for (int f = 0; f < 4; f++) {
			for (int c = 0; c < 4; c++) {
				int xPos = offsetX + (c * espacioX);
				int yPos = offsetY + (f * espacioY);
				this.cuadras[f][c] = new Cuadra(xPos, yPos);
			}
		}
		
		// Inicialización de enemigos
		this.enemigos = new Enemigo[4];
		this.enemigos[0] = new Enemigo(100, 100, "HORIZONTAL");
		this.enemigos[1] = new Enemigo(300, 200, "VERTICAL");
		this.enemigos[2] = new Enemigo(500, 100, "HORIZONTAL");
		this.enemigos[3] = new Enemigo(200, 400, "VERTICAL");

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
		// 1. Color de fondo del juego
	    this.entorno.colorFondo(Color.DARK_GRAY);

	    // --- 2. DIBUJAR ENCABEZADO / HUD SUPERIOR (Diseño Natalia) ---
	    this.entorno.dibujarRectangulo(400.0, 25.0, 800.0, 50.0, 0.0, Color.BLACK);
	    Color colorCuadro = new Color(55, 70, 50);
	    
	    int segundosRestantes = this.tiempoRestante / 60; // Conversión aproximada de ticks a segundos

	    // A. TIEMPO
	    this.entorno.dibujarRectangulo(82.0, 25.0, 145.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoTiempo, 30.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Tiempo: " + segundosRestantes + "s", 50, 30);

	    // B. VIDAS
	    this.entorno.dibujarRectangulo(230.0, 25.0, 130.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoVidas, 185.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Vidas: " + this.repartidor.vidas, 205, 30);

	    // C. PEDIDOS ENTREGADOS
	    this.entorno.dibujarRectangulo(395.0, 25.0, 175.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoCajaPedido, 325.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Entregados: " + this.pedidosEntregados, 345, 30);

	    // D. ENEMIGOS ELIMINADOS
	    this.entorno.dibujarRectangulo(570.0, 25.0, 155.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoEnemigos, 515.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Eliminados: " + this.enemigosEliminados, 535, 30);

	    // E. ESTADO DEL PEDIDO
	    String textoEstado = (this.estadoPedido == 0) ? "Esperando" : "En camino";
	    this.entorno.dibujarRectangulo(722.0, 25.0, 135.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoPedidoActual, 675.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Pedido: " + textoEstado, 695, 30);

		// 3. Dibuja la grilla de la ciudad (Cuadras, Casas y Negocios)
		for (int f = 0; f < 4; f++) {
			for (int c = 0; c < 4; c++) {
				this.cuadras[f][c].dibujar(this.entorno);
			}
		}

		// Dimensiones de la caja de colisión del repartidor
		int anchoRepartidor = 30;
		int altoRepartidor = 40;

		// --- Movimiento arriba (W) ---
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
			
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverAbajo();
			}
		}

		// --- Movimiento izquierda (A) ---
		if (this.entorno.estaPresionada('a') || this.entorno.estaPresionada('A')) {
			int proximoX = this.repartidor.x - 2;
			boolean colisiona = false;
			
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
			
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(proximoX, this.repartidor.y, anchoRepartidor, altoRepartidor)) {
						colisiona = true;
					}
				}
			}
			
			if (!colisiona) {
				this.repartidor.moverDerecha();
			}
		}
		
		// --- Movimiento y dibujo de los enemigos ---
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				this.enemigos[i].mover();
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

		// --- Dinámica de Pedidos: Activación con la tecla 'E' (Reemplazo del Clic) ---
				if (this.entorno.estaPresionada('e') || this.entorno.estaPresionada('E')) {
					// Estado 0: El repartidor debe estar sobre el negocio y presionar 'E' para tomarlo
					if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
						if (this.negocioDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
							this.estadoPedido = 1; // Pasamos a estado "En camino"
							this.tiempoRestante = TIEMPO_MAXIMO; // Reiniciamos el reloj para la entrega
							System.out.println("¡Pedido recogido! En camino a la casa de destino.");
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