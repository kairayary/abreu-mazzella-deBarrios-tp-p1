package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Repartidor {
	//Variables
    int x;
    int y;
    int vidas;
    Image imagenRepartidor;

    //Constructor
    public Repartidor(int xInicio, int yInicio) {
        this.x = xInicio;
        this.y = yInicio;
        this.vidas = 3; 
        this.imagenRepartidor = Herramientas.cargarImagen("juego/deliveryBoy.png");
    }
    
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagenRepartidor, this.x, this.y, 0, 0.15);
    }
    
    // Métodos de movimiento con restricciones de pantalla (800x600)
   
    public void moverArriba() { 
        if (this.y > 70) {
            this.y -= 2; 
        }
    }
    
    public void moverAbajo() { 
        if (this.y < 550) { 
            this.y += 2; 
        }
    }
    
    public void moverIzquierda() { 
        if (this.x > 30) { 
            this.x -= 2; 
        }
    }
    
    public void moverDerecha() { 
        if (this.x < 770) { 
            this.x += 2; 
        }
    }
    
    public void imprimir() {
        System.out.println("Repartidor en X: " + this.x + " | Y: " + this.y + " | Vidas: " + this.vidas);
    }
}