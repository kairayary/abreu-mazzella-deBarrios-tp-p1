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
        this.vidas = 5; 
        this.pedidosEntregados = 0;
        this.estadoPedido = "Ninguno";   // Estado inicial del pedido
        this.imagen = Herramientas.cargarImagen("img/deliveryBoy.png");
    }
    
    //que hace el repartidor cuando pierde una vida: 
    public void perderVida() {
        this.vidas--; // Resta una vida
        this.x = 60; // Lo reubica a la posición segura X
        this.y = 540; // Lo reubica a la posición segura Y
    }
    
    // Métodos Getters y Setters requeridos para el HUD y la lógica
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
    
    // Métodos de movimiento con limite de pantalla y hud
    public void moverArriba() { 
        if (this.y - (this.alto / 2) > 50) { // Respeta el HUD superior (50 píxeles)
            this.y -= 2; 
        }
    }
    
    public void moverAbajo(int altoEntorno) { 
        if (this.y + (this.alto / 2) < altoEntorno) { // Respeta el borde inferior
            this.y += 2; 
        }
    }
    
    public void moverIzquierda() { 
        if (this.x - (this.ancho / 2) > 0) { // Respeta el borde izquierdo
            this.x -= 2; 
        }
    }
    
    public void moverDerecha(int anchoEntorno) { 
        if (this.x + (this.ancho / 2) < anchoEntorno) { // Respeta el borde derecho
            this.x += 2; 
        }
    }
}