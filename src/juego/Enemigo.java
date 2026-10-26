package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Enemigo {
    int x;
    int y;
    String direccion; // "HORIZONTAL" o "VERTICAL"
    int velocidad;
    Image imagen;

    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2;
        this.imagen = Herramientas.cargarImagen("img/enemigo.png");
    }

    public void mover() {
        if (this.direccion.equals("VERTICAL")) {
            this.y += this.velocidad;
        } else {
            this.x += this.velocidad;
        }
    }

    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
}