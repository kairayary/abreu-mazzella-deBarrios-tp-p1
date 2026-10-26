package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class Enemigo {
    // Variables de instancia
    int x;
    int y;
    String direccion; // Puede ser "HORIZONTAL" o "VERTICAL"
    int velocidad;
    Image imagen;

    // Constructor para inicializar al enemigo
    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2; // Velocidad inicial de desplazamiento
        this.imagen = Herramientas.cargarImagen("img/enemigo.png");
    }

    // Método para mover al enemigo en línea recta
    public void mover() {
        if (this.direccion.equals("VERTICAL")) {
            this.y += this.velocidad;
        } else {
            this.x += this.velocidad;
        }
    }
    
    // Método para dibujar al enemigo en la pantalla
    public void dibujar(Entorno entorno){
    	entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
}