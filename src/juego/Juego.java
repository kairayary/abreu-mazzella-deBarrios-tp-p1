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
	private Enemigo[] enemigos; // REQUERIMIENTO: Arreglo de objetos obligatorio
	// --- variables para el HUD:
	private int tiempoRestantePedido = 20; //Tiempo limite para entregar cada pedido. Regresiva de 20 a 0.
	private int contadorTicksPedido = 0;  //Controla los segundos del pedido.
	private int enemigosEliminados = 0;
	private Image iconoTiempo;
    private Image iconoVidas;
    private Image iconoCajaPedido;
    private Image iconoEnemigos;
    private Image iconoPedidoActual;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		this.repartidor = new Repartidor(200, 300); 
		
		// Cargar iconos para el HUD (ajusta los nombres de archivo según los que tengas)
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
		
		this.enemigos = new Enemigo[4]; // Tamaño fijo de al menos 4 enemigos
		this.enemigos[0] = new Enemigo(100, 100, "HORIZONTAL");
		this.enemigos[1] = new Enemigo(300, 200, "VERTICAL");
		this.enemigos[2] = new Enemigo(500, 100, "HORIZONTAL");
		this.enemigos[3] = new Enemigo(200, 400, "VERTICAL");

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
- 	Durante el juego, el método tick() será ejecutado en cada instante y 
- 	por lo tanto es el método más importante de esta clase. Aquí se debe 
- 	actualizar el estado interno del juego para simular el paso del tiempo 
- 	(ver el enunciado del TP para mayor detalle).
	 */
	
	public void tick()
	{
		// 1. Limpiar pantalla / Color de fondo del juego
	    this.entorno.colorFondo(Color.DARK_GRAY);

	    // --- 2. CONTROL DEL TIEMPO DEL PEDIDO ---
	    // Si el repartidor tiene un pedido activo ("En camino")
	    if (this.repartidor.getEstadoPedido().equals("en camino")) {
	        
	        this.contadorTicksPedido++;
	        if (this.contadorTicksPedido >= 60) { // 60 ticks = 1 segundo aprox.
	            if (this.tiempoRestantePedido > 0) {
	                this.tiempoRestantePedido--;
	            } else {
	                // ¡Se acabó el tiempo! Pierde el pedido y vuelve a estar libre
	                this.repartidor.setEstadoPedido("pyo en breveserdido"); 
	                this.tiempoRestantePedido = 20;
	            }
	            this.contadorTicksPedido = 0;
	        }
	    } else {
	        // Si no tiene pedido activo, el cronómetro se reinicia y se queda en 20
	        this.tiempoRestantePedido = 20;
	        this.contadorTicksPedido = 0;
	    }

	    // 3. Dibujar Encabezado / HUD Superior con Marcos individuales
	    // A. Fondo general de la barra superior (negro)
	    this.entorno.dibujarRectangulo(400.0, 25.0, 800.0, 50.0, 0.0, Color.BLACK);
	    
	    // Color de fondo para los marcos individuales (un tono más claro para que contraste bien)
	    Color colorCuadro = new Color(55, 70, 50);
	    	    
	     int minutos = this.tiempoRestantePedido / 60;
	     int segundos = this.tiempoRestantePedido % 60;
	     String tiempoStr = (minutos < 10 ? "0" : "") + minutos + ":" + (segundos < 10 ? "0" : "") + segundos;

	     	// --- 1. TIEMPO ---
		    this.entorno.dibujarRectangulo(82.0, 25.0, 145.0, 36.0, 0.0, colorCuadro);
		    this.entorno.dibujarImagen(this.iconoTiempo, 30.0, 25.0, 0.0, 0.3);
		    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
		    this.entorno.escribirTexto("Tiempo: " + tiempoRestantePedido + "s", 50, 30);

		    // --- 2. VIDAS ---
		    this.entorno.dibujarRectangulo(230.0, 25.0, 130.0, 36.0, 0.0, colorCuadro);
		    this.entorno.dibujarImagen(this.iconoVidas, 185.0, 25.0, 0.0, 0.3);
		    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
		    this.entorno.escribirTexto("Vidas: " + this.repartidor.getVidas(), 205, 30);

		    // --- 3. PEDIDOS ENTREGADOS ---
		    this.entorno.dibujarRectangulo(395.0, 25.0, 175.0, 36.0, 0.0, colorCuadro);
		    this.entorno.dibujarImagen(this.iconoCajaPedido, 325.0, 25.0, 0.0, 0.3);
		    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
		    this.entorno.escribirTexto("Entregados: " + this.repartidor.getPedidosEntregados(), 345, 30);

		    // --- 4. ENEMIGOS ELIMINADOS ---
		    this.entorno.dibujarRectangulo(570.0, 25.0, 155.0, 36.0, 0.0, colorCuadro);
		    this.entorno.dibujarImagen(this.iconoEnemigos, 515.0, 25.0, 0.0, 0.3);
		    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
		    this.entorno.escribirTexto("Eliminados: " + this.enemigosEliminados, 535, 30);

		    // --- 5. ESTADO DEL PEDIDO ---
		    this.entorno.dibujarRectangulo(722.0, 25.0, 135.0, 36.0, 0.0, colorCuadro);
		    this.entorno.dibujarImagen(this.iconoPedidoActual, 675.0, 25.0, 0.0, 0.3);
		    this.entorno.cambiarFont("Arial", 13, Color.WHITE);
		    this.entorno.escribirTexto("Pedido: " + this.repartidor.getEstadoPedido(), 695, 30);
	    
		    // Dibuja la grilla de la ciudad (Cuadras, Casas y Negocios)
		    for (int f = 0; f < 4; f++) {
		    	for (int c = 0; c < 4; c++) {
		    		this.cuadras[f][c].dibujar(this.entorno);
		    	}
		    }

		    // Definimos las dimensiones de la caja de colisión del repartidor
				int anchoRepartidor = 30;
				int altoRepartidor = 40;

				// --- MOVIMIENTO ARRIBA (W) ---
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

				// --- MOVIMIENTO ABAJO (S) ---
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

				// --- MOVIMIENTO IZQUIERDA (A) ---
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

				// --- MOVIMIENTO DERECHA (D) ---
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
		
	//  Movimiento y dibujo de los enemigos
			for (int i = 0; i < this.enemigos.length; i++) {
				if (this.enemigos[i] != null) { // Verificamos que no sea null
					this.enemigos[i].mover();
					this.enemigos[i].dibujar(this.entorno);
				}
			} 
		
		// Dibujamos al repartidor una sola vez por fotograma
		this.repartidor.dibujar(this.entorno);
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
