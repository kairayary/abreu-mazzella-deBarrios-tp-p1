package juego;

import entorno.Entorno;
import java.awt.Color;

public class Casa {
    int x;
    int y;
    int ancho;
    int alto;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = 40;
        this.alto = 30;
    }

    public void dibujar(Entorno entorno) {
        // Por ahora dibujamos un rectángulo representativo (luego se puede cambiar por imagen)
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.BLUE);
    }
}
