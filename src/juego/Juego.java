package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	
	private Entorno entorno;
	private Cuadra[][] cuadras;
	private Repartidor repartidor;
	private Enemigo[] enemigos; // REQUERIMIENTO: Arreglo de objetos obligatorio
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Inicializar lo que haga falta para el juego
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
