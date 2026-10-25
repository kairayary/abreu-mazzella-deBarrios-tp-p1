package juego;

import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

public class Enemigo {
    // Variables de instancia
    int x;
    int y;
    String direccion; // Puede ser "HORIZONTAL" o "VERTICAL"
    int velocidad;
    Image imagenEnemigo;

    // Constructor para inicializar al enemigo
    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2; // Velocidad inicial de desplazamiento
        this.imagenEnemigo = Herramientas.cargarImagen("juego/enemigo.png");
    }

 // Método para dibujar al enemigo en la pantalla
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagenEnemigo, this.x, this.y, 0, 0.15);
    }
    
    // Método para mover al enemigo en línea recta
    public void mover() {
        if (this.direccion.equals("VERTICAL")) {
            this.y += this.velocidad;
        } else {
            this.x += this.velocidad;
        }
    }
}