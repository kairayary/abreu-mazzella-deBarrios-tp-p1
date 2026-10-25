package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Negocio {
    int x;
    int y;
    int ancho;
    int alto;
    Image imagenNegocio;

    //Constructor
    public Negocio(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = 10;
        this.alto = 10;
        this.imagenNegocio = Herramientas.cargarImagen("juego/negocio.png");
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(this.imagenNegocio, this.x, this.y, 0, 0.04);
    }
}