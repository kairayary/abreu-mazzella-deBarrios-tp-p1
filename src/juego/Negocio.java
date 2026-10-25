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