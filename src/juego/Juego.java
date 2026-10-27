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
	private Proyectil proyectil; 
	
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
	private final int TIEMPO_MAXIMO = 1500; 
	private int pedidosEntregados = 0;
	private int pedidosPerdidos = 0;
	private Image iconoFlechaRoja;
	private Image iconoFlechaVerde;

	Juego()
	{
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Posicionar al repartidor en el margen inferior izquierdo (Requerimiento 1)[cite: 4]
		this.repartidor = new Repartidor(60, 540); 
		this.proyectil = null;
		
		this.iconoTiempo = Herramientas.cargarImagen("img/tiempo.png");
	    this.iconoVidas = Herramientas.cargarImagen("img/vidas.png");
	    this.iconoCajaPedido = Herramientas.cargarImagen("img/cajaPedido.png");
	    this.iconoEnemigos = Herramientas.cargarImagen("img/enemigos.png");
	    this.iconoPedidoActual = Herramientas.cargarImagen("img/pedidoActual.png");
	    this.iconoFlechaRoja = Herramientas.cargarImagen("img/flechaRoja.png");
	    this.iconoFlechaVerde = Herramientas.cargarImagen("img/flechaVerde.png");
		
		this.cuadras = new Cuadra[4][4];
		int offsetX = 130; 
		int offsetY = 120; 
		int espacioX = 170; 
		int espacioY = 110; 

		for (int f = 0; f < 4; f++) {
			for (int c = 0; c < 4; c++) {
				int xPos = offsetX + (c * espacioX);
				int yPos = offsetY + (f * espacioY);
				this.cuadras[f][c] = new Cuadra(xPos, yPos);
			}
		}
		
		this.enemigos = new Enemigo[4];
		this.enemigos[0] = new Enemigo(215, 205, "HORIZONTAL"); 
		this.enemigos[1] = new Enemigo(385, 205, "VERTICAL");   
		this.enemigos[2] = new Enemigo(215, 315, "HORIZONTAL"); 
		this.enemigos[3] = new Enemigo(555, 315, "VERTICAL");   

		generarNuevoPedido();
		this.entorno.iniciar();
	}

	private void generarNuevoPedido() {
		int fNeg = (int) (Math.random() * 4);
		int cNeg = (int) (Math.random() * 4);
		this.negocioDestinoActual = this.cuadras[fNeg][cNeg].negocio;

		int fCas = (int) (Math.random() * 4);
		int cCas = (int) (Math.random() * 4);
		int kCas = (int) (Math.random() * 3);
		this.casaDestinoActual = this.cuadras[fCas][cCas].casas[kCas];

		this.estadoPedido = 0; 
		this.tiempoRestante = TIEMPO_MAXIMO;
		System.out.println("¡Nuevo pedido generado! Recógelo en el negocio marcado.");
	}

	public void tick()
	{
	    this.entorno.colorFondo(Color.DARK_GRAY);

	    // HUD
	    this.entorno.dibujarRectangulo(400.0, 25.0, 800.0, 50.0, 0.0, Color.BLACK);
	    Color colorCuadro = new Color(55, 70, 50);

	    // TIEMPO
	    this.entorno.dibujarRectangulo(75.0, 25.0, 135.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoTiempo, 33.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Tiempo: " + tiempoRestante + "s", 51, 30);

	    // VIDAS
	    this.entorno.dibujarRectangulo(213.0, 25.0, 105.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoVidas, 175.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Vidas: " + this.repartidor.getVidas(), 195, 30);

	    // PEDIDOS ENTREGADOS
	    this.entorno.dibujarRectangulo(355.0, 25.0, 150.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoCajaPedido, 298.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Entregados: " + this.repartidor.getPedidosEntregados(), 318, 30);

	    // ENEMIGOS ELIMINADOS
	    this.entorno.dibujarRectangulo(520.0, 25.0, 150.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoEnemigos, 465.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Eliminados: " + this.enemigosEliminados, 485, 30);
	    
	    // ESTADO DEL PEDIDO
	    this.entorno.dibujarRectangulo(700.0, 25.0, 180.0, 36.0, 0.0, colorCuadro);
	    this.entorno.dibujarImagen(this.iconoPedidoActual, 628.0, 25.0, 0.0, 0.3);
	    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
	    this.entorno.escribirTexto("Pedido: " + this.repartidor.getEstadoPedido(), 648, 30);
	    // Dibuja la grilla
	    for (int f = 0; f < 4; f++) {
	        for (int c = 0; c < 4; c++) {
	            this.cuadras[f][c].dibujar(this.entorno);
	        }
	    }
	    
	    // Flechas indicadoras
	    if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
	        this.entorno.dibujarImagen(this.iconoFlechaRoja, this.negocioDestinoActual.x, this.negocioDestinoActual.y - 35, 0, 0.25);
	    } else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
	        this.entorno.dibujarImagen(this.iconoFlechaVerde, this.casaDestinoActual.x, this.casaDestinoActual.y - 35, 0, 0.25);
	    }

		int anchoRepartidor = 30;
		int altoRepartidor = 40;

		// Movimiento W / Flecha Arriba
		if (this.entorno.estaPresionada('w') || this.entorno.estaPresionada('W') || this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA)) {
			int proximoY = this.repartidor.y - 2;
			boolean colisiona = false;
			if (proximoY - (altoRepartidor / 2) < 50) colisiona = true;
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) colisiona = true;
				}
			}
			if (!colisiona) this.repartidor.moverArriba();
		}
				
		// Movimiento S / Flecha Abajo
		if (this.entorno.estaPresionada('s') || this.entorno.estaPresionada('S') || this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
			int proximoY = this.repartidor.y + 2;
			boolean colisiona = false;
			if (proximoY + (altoRepartidor / 2) > this.entorno.alto()) colisiona = true;
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(this.repartidor.x, proximoY, anchoRepartidor, altoRepartidor)) colisiona = true;
				}
			}
			if (!colisiona) this.repartidor.moverAbajo();
		}

		// Movimiento A / Flecha Izquierda
		if (this.entorno.estaPresionada('a') || this.entorno.estaPresionada('A') || this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			int proximoX = this.repartidor.x - 2;
			boolean colisiona = false;
			if (proximoX - (anchoRepartidor / 2) < 0) colisiona = true;
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(proximoX, this.repartidor.y, anchoRepartidor, altoRepartidor)) colisiona = true;
				}
			}
			if (!colisiona) this.repartidor.moverIzquierda();
		}

		// Movimiento D / Flecha Derecha
		if (this.entorno.estaPresionada('d') || this.entorno.estaPresionada('D') || this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			int proximoX = this.repartidor.x + 2;
			boolean colisiona = false;
			if (proximoX + (anchoRepartidor / 2) > this.entorno.ancho()) colisiona = true;
			for (int f = 0; f < 4; f++) {
				for (int c = 0; c < 4; c++) {
					if (this.cuadras[f][c].hayColision(proximoX, this.repartidor.y, anchoRepartidor, altoRepartidor)) colisiona = true;
				}
			}
			if (!colisiona) this.repartidor.moverDerecha();
		}
		
		// --- REQUERIMIENTO 7: Disparar con el botón izquierdo del mouse (Botón 1) o Espacio ---
		if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO) || this.entorno.sePresionoBoton(1)) {
			if (this.proyectil == null) {
				this.proyectil = new Proyectil(this.repartidor.x, this.repartidor.y, this.repartidor.dirX, this.repartidor.dirY);
			}
		}

		// --- Actualizar y verificar colisiones del Proyectil ---
		if (this.proyectil != null) {
			this.proyectil.mover();
			this.proyectil.dibujar(this.entorno);

			// Verificar si sale de los límites de la pantalla
			if (this.proyectil.x < 0 || this.proyectil.x > this.entorno.ancho() || 
				this.proyectil.y < 50 || this.proyectil.y > this.entorno.alto()) {
				this.proyectil = null;
			} else {
				// Verificar colisión con enemigos
				for (int i = 0; i < this.enemigos.length; i++) {
					if (this.enemigos[i] != null && this.proyectil != null) {
						if (this.proyectil.colisionaCon(this.enemigos[i])) {
							this.enemigos[i] = null; // Elimina al enemigo
							this.proyectil = null;   // Elimina el proyectil
							this.enemigosEliminados++;
							System.out.println("¡Enemigo eliminado con disparos!");
						}
					}
				}
			}
		}

		// Movimiento y dibujo de enemigos
		for (int i = 0; i < this.enemigos.length; i++) {
	        if (this.enemigos[i] != null) { 
	            this.enemigos[i].mover(this.entorno.ancho(), this.entorno.alto(), this.cuadras);
	            this.enemigos[i].dibujar(this.entorno);
	        }
	    }
		
		// --- COLISIONES CON ENEMIGOS (Requerimiento 5)[cite: 5] ---
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				if (this.enemigos[i].colisionaCon(this.repartidor.x, this.repartidor.y, anchoRepartidor, altoRepartidor)) {
					// Descuenta una vida
					this.repartidor.vidas--;
					System.out.println("¡Chocaste con un enemigo! Vidas restantes: " + this.repartidor.vidas);
					
					// Si llevaba pedido en estado "En camino", se pierde y vuelve a estado 0
					if (this.estadoPedido == 1) {
						this.estadoPedido = 0;
						this.pedidosPerdidos++;
						System.out.println("¡Pedido perdido por colisión con enemigo!");
					}
					
					// Reubicar al repartidor al margen inferior izquierdo
					this.repartidor.x = 70;
					this.repartidor.y = 530;
					
					if (this.repartidor.vidas <= 0) {
						System.out.println("¡JUEGO PERDIDO! Te has quedado sin vidas.");
					}
				}
			}
		}

		this.repartidor.dibujar(this.entorno);
		
		// Temporizador de entrega
		if (this.tiempoRestante > 0) {
			this.tiempoRestante--;
			if (this.tiempoRestante <= 0) {
				this.pedidosPerdidos++;
				System.out.println("¡Tiempo agotado! Pedido perdido.");
				generarNuevoPedido(); 
			}
		}

		// Dinámica de Pedidos (Clic Derecho - Botón 3)
		if (this.entorno.sePresionoBoton(3)) {
			if (this.estadoPedido == 0 && this.negocioDestinoActual != null) {
				if (this.negocioDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.estadoPedido = 1; 
					this.tiempoRestante = TIEMPO_MAXIMO; 
					System.out.println("¡Pedido recogido! En camino a la casa de destino.");
				}
			} 
			else if (this.estadoPedido == 1 && this.casaDestinoActual != null) {
				if (this.casaDestinoActual.colisionaCon(repartidor.x, repartidor.y, repartidor.ancho, repartidor.alto)) {
					this.pedidosEntregados++;
					System.out.println("¡Pedido entregado con éxito!");
					generarNuevoPedido(); 
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