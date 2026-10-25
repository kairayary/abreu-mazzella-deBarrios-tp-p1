package juego;

import entorno.Entorno;
import java.awt.Color;

public class Negocio {
    int x;
    int y;
    int ancho;
    int alto;

    public Negocio(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = 45;
        this.alto = 35;
    }

    public void dibujar(Entorno entorno) {
        // Rectángulo distintivo para el negocio
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
    }
}