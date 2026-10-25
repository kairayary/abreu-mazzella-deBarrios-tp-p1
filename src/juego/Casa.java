package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Casa {
    int x;
    int y;
    int ancho;
    int alto;
    Image imagenCasa;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = 30;
        this.alto = 30;
        this.imagenCasa = Herramientas.cargarImagen("juego/casa.png");
    }

    public void dibujar(Entorno entorno) {
    	 entorno.dibujarImagen(this.imagenCasa, this.x, this.y, 0, 0.08);
    }
}
