package juego;

import entorno.Entorno;
import java.awt.Color;

public class Cuadra {
    Casa[] casas;
    Negocio negocio;
    int xBase;
    int yBase;

 // Constructor que inicializa las casas y el negocio como bloque
    public Cuadra(int xBase, int yBase) {
        this.xBase = xBase;
        this.yBase = yBase;
        
        this.casas = new Casa[3];
        
        this.casas[0] = new Casa(xBase - 20, yBase - 15);
        this.casas[1] = new Casa(xBase + 20, yBase - 15);
        this.casas[2] = new Casa(xBase - 20, yBase + 15);
        
        this.negocio = new Negocio(xBase + 20, yBase + 15);
    }

    public boolean hayColision(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {

        // Colisión con el rectángulo de fondo de la cuadra (manzana completa)
        int anchoManzana = 90;
        int altoManzana = 65;
        
        int left1 = xRepartidor - anchoRepartidor / 2;
        int right1 = xRepartidor + anchoRepartidor / 2;
        int top1 = yRepartidor - altoRepartidor / 2;
        int bottom1 = yRepartidor + altoRepartidor / 2;

        int left2 = this.xBase - anchoManzana / 2;
        int right2 = this.xBase + anchoManzana / 2;
        int top2 = this.yBase - altoManzana / 2;
        int bottom2 = this.yBase + altoManzana / 2;

        if (left1 < right2 && right1 > left2 && top1 < bottom2 && bottom1 > top2) {
            return true;
        }
        
        // Verificar colisión con el negocio de la cuadra
        if (this.negocio.colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
            return true;
        }
        
        // Verificar colisión con las casas
        for (int i = 0; i < this.casas.length; i++) {
            if (this.casas[i].colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
                return true;
            }
        }
        
        return false;
    }

    public void dibujar(Entorno entorno) {
        // Dibujamos el rectángulo de fondo para representar la manzana
        entorno.dibujarRectangulo((double)this.xBase, (double)this.yBase, 90.0, 65.0, 0.0, Color.GRAY);

        // Dibujamos el negocio sobre el fondo de la cuadra
        this.negocio.dibujar(entorno);
        
        // Dibujamos las casas
        for (int i = 0; i < this.casas.length; i++) {
            this.casas[i].dibujar(entorno);
        }
    }
}