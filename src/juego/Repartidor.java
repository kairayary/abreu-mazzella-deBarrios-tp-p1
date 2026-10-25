package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Repartidor {
    // Atributos con visibilidad por defecto (como pide la cátedra)
    int x;
    int y;
    int ancho;
    int alto;
    int vidas;
    Image imagenRepartidor;

    public Repartidor(int xInicio, int yInicio) {
        this.x = xInicio;
        this.y = yInicio;
        this.ancho = 30;
        this.alto = 40;
        this.vidas = 3; 
        this.imagenRepartidor = Herramientas.cargarImagen("juego/deliveryBoy.png");
    }
    
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagenRepartidor, this.x, this.y, 0, 0.15);
    }
    
    // Métodos de movimiento directos
    public void moverArriba() { 
        this.y -= 2; 
    }
    
    public void moverAbajo() { 
        this.y += 2; 
    }
    
    public void moverIzquierda() { 
        this.x -= 2; 
    }
    
    public void moverDerecha() { 
        this.x += 2; 
    }
    
    public void imprimir() {
        System.out.println("Repartidor en X: " + this.x + " | Y: " + this.y + " | Vidas: " + this.vidas);
    }
}