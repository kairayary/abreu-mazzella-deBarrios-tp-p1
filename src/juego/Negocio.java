package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Negocio {
    int x;
    int y;
    int ancho;
    int alto;
    Image imagen;

    public Negocio(int x, int y) {
        this.x = x;
        this.y = y;
        this.ancho = 45;
        this.alto = 35;
        this.imagen = Herramientas.cargarImagen("img/negocio.png");
    }

    public void dibujar(Entorno entorno) {
    	entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
    
    public boolean colisionaCon(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
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