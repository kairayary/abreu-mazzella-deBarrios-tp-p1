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
    int pedidosEntregados;
    String estadoPedido;
    Image imagen;

    public Repartidor(int xInicio, int yInicio) {
        this.x = xInicio;
        this.y = yInicio;
        this.ancho = 30;
        this.alto = 40;
        this.vidas = 3; 
        this.pedidosEntregados = 0;
        this.estadoPedido = "Ninguno";   //Estado inicial del pedido
        this.imagen = Herramientas.cargarImagen("img/deliveryBoy.png");
    }
    
    //Requerido para el HUD
    public int getVidas() {
    	return this.vidas;
    }
    
    public int getPedidosEntregados() { 
    	return this.pedidosEntregados;
    }
    
    public String getEstadoPedido() {
    	return this.estadoPedido;
    }
    
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
    
    public void setEstadoPedido(String nuevoEstado) {
    	this.estadoPedido = nuevoEstado;
    }
    
    //Métodos de movimiento directos con limite de pantalla y HUD
    
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