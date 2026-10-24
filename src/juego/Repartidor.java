package juego;

import entorno.Entorno;
import java.awt.Color;
import java.awt.Image;
import entorno.Herramientas;

public class Repartidor {
	// Variables de instancia
    int x;
    int y;
    int vidas;
    Image imagen;

    // Constructor 
    public Repartidor(int xInicio, int yInicio, Entorno entorno) {
        this.x = xInicio;
        this.y = yInicio;
        this.vidas = 3; 
        this.imagen = Herramientas.cargarImagen("img/repartidor_adelante.jpg");
    }
    
 // Método para dibujar la imagen del repartidor en la pantalla
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0);
    }
    
 // Método de instancia para mover al repartidor según la tecla presionada
    public void moverArriba() { this.y -= 2; }
    public void moverAbajo() { this.y += 2; }
    public void moverIzquierda() { this.x -= 2; }
    public void moverDerecha() { this.x += 2; }
    
    
    // Método de instancia para ver su estado
    public void imprimir() {
        System.out.println("Repartidor en X: " + this.x + " | Y: " + this.y + " | Vidas: " + this.vidas);
}
    }
