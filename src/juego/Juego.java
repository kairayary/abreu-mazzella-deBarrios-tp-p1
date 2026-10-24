package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Repartidor repartidor;
	private Enemigo[] enemigos; // REQUERIMIENTO: Arreglo de objetos obligatorio
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - UNGS", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		this.repartidor = new Repartidor(400, 500); // Ubicado en la parte inferior
		
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
		// Procesamiento de un instante de tiempo
		// Movimiento de los enemigos, recorremos el arreglo de enemigos para moverlos en cada fotograma
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) { // Verificamos que no sea null
				this.enemigos[i].mover();
				this.enemigos[i].dibujar(this.entorno);
			}
	
	        //  Movimiento del Repartidor con W, A, S, D
	        if (this.entorno.estaPresionada('w') || this.entorno.estaPresionada('W')) {
	            this.repartidor.moverArriba();
	        }
	        if (this.entorno.estaPresionada('s') || this.entorno.estaPresionada('S')) {
	            this.repartidor.moverAbajo();
	        }
	        if (this.entorno.estaPresionada('a') || this.entorno.estaPresionada('A')) {
	            this.repartidor.moverIzquierda();
	        }
	        if (this.entorno.estaPresionada('d') || this.entorno.estaPresionada('D')) {
	            this.repartidor.moverDerecha();
	        }
	        
	        this.repartidor.dibujar(this.entorno);
	        
		}
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
