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
    
    public boolean colisionaCon(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
        int diferenciaX = this.x - xRepartidor;
        int umbralX = (this.ancho + anchoRepartidor) / 2;
        boolean colisionX = (diferenciaX > -umbralX) && (diferenciaX < umbralX);

        int diferenciaY = this.y - yRepartidor;
        int umbralY = (this.alto + altoRepartidor) / 2;
        boolean colisionY = (diferenciaY > -umbralY) && (diferenciaY < umbralY);

        return colisionX && colisionY;
    }
    
    public boolean estaDentro(int px, int py) {
        boolean dentroX = px >= (this.x - this.ancho / 2) && px <= (this.x + this.ancho / 2);
        boolean dentroY = py >= (this.y - this.alto / 2) && py <= (this.y + this.alto / 2);
        return dentroX && dentroY;
    }
  
}
