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
    
    public boolean colisionaCon(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
        // Cálculo basado en los centros (coordenadas x, y de la librería Entorno)
        int left1 = xRepartidor - anchoRepartidor / 2;
        int right1 = xRepartidor + anchoRepartidor / 2;
        int top1 = yRepartidor - altoRepartidor / 2;
        int bottom1 = yRepartidor + altoRepartidor / 2;

        int left2 = this.x - this.ancho / 2;
        int right2 = this.x + this.ancho / 2;
        int top2 = this.y - this.alto / 2;
        int bottom2 = this.y + this.alto / 2;

        return left1 < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2;
    }
}