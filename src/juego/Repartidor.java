package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Repartidor {
    int x;
    int y;
    int ancho;
    int alto;
    int vidas;
    int pedidosEntregados;
    String estadoPedido;
    Image imagen;
    
    // Variables para registrar la última dirección de movimiento
    int dirX = 1; 
    int dirY = 0;

    public Repartidor(int xInicio, int yInicio) {
        this.x = xInicio;
        this.y = yInicio;
        this.ancho = 30;
        this.alto = 40;
        this.vidas = 3; 
        this.pedidosEntregados = 0;
        this.estadoPedido = "Ninguno";   
        this.imagen = Herramientas.cargarImagen("img/deliveryBoy.png");
    }
    
    public int getVidas() {
    	return this.vidas;
    }
    
    public int getPedidosEntregados() { 
    	return this.pedidosEntregados;
    }
    
    public String getEstadoPedido() {
    	return this.estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
    
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
    
    public void moverArriba() {
        this.dirX = 0;
        this.dirY = -1;
        this.y -= 2;
    }

    public void moverAbajo() {
        this.dirX = 0;
        this.dirY = 1;
        this.y += 2;
    }

    public void moverIzquierda() {
        this.dirX = -1;
        this.dirY = 0;
        this.x -= 2;
    }

    public void moverDerecha() {
        this.dirX = 1;
        this.dirY = 0;
        this.x += 2;
    }
}