package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	private Entorno entorno;
	private Cuadra[][] cuadras;
	private Repartidor repartidor;
	private Enemigo[] enemigos; 
	
	// Variables para la Dinámica de Pedidos (Requerimiento 2)
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

	// Método auxiliar para generar un pedido aleatorio (Requerimiento 2)
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
		// 1. Dibuja la grilla de la ciudad (Cuadras, Casas y Negocios)
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

		// --- Dinámica de Pedidos: Detección de Clic Derecho (Requerimiento 2) ---
		if (this.entorno.sePresionoBotonDerecho()) {
			// Estado 0: El repartidor debe estar sobre el negocio y hacer clic derecho para tomarlo
			if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
				if (this.negocioDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.estadoPedido = 1; // Pasamos a estado "En camino"
					this.tiempoRestante = TIEMPO_MAXIMO; // Reiniciamos el reloj para la entrega
					System.out.println("¡Pedido recogido! En camino a la casa de destino.");
				}
			} 
			// Estado 1: El repartidor debe estar sobre la casa correcta y hacer clic derecho para entregar
			else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
				if (this.casaDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.pedidosEntregados++;
					System.out.println("¡Pedido entregado con éxito! Total entregados: " + this.pedidosEntregados);
					generarNuevoPedido(); // Se genera el siguiente pedido
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