package juego;

import entorno.Entorno;
import java.awt.Color;

public class Cuadra {
    Casa[] casas;
    Negocio negocio;
    int xBase;
    int yBase;

    public Cuadra(int xBase, int yBase) {
        this.xBase = xBase;
        this.yBase = yBase;
        this.casas = new Casa[3];
        
<<<<<<< Updated upstream
        // Distribuimos las 3 casas alrededor del centro de la cuadra
=======
        // Posicionamiento correcto de las 3 casas de la cuadra
>>>>>>> Stashed changes
        this.casas[0] = new Casa(xBase - 30, yBase - 20);
        this.casas[1] = new Casa(xBase + 30, yBase - 20);
        this.casas[2] = new Casa(xBase, yBase + 20);
        
        // Ubicamos el negocio en el centro de la cuadra
        this.negocio = new Negocio(xBase, yBase);
    }

    public boolean hayColision(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes
        if (this.negocio.colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
            return true;
        }
        
        for (int i = 0; i < this.casas.length; i++) {
            if (this.casas[i].colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
                return true;
            }
        }
        
        return false;
    }

    public void dibujar(Entorno entorno) {
<<<<<<< Updated upstream
        // Dibujamos el rectángulo de fondo para representar la manzana gris
        entorno.dibujarRectangulo((double)this.xBase, (double)this.yBase, 90.0, 65.0, 0.0, Color.GRAY);

        // Dibujamos el negocio sobre el fondo de la cuadra
=======
>>>>>>> Stashed changes
        this.negocio.dibujar(entorno);
        for (int i = 0; i < this.casas.length; i++) {
            this.casas[i].dibujar(entorno);
        }
    }
}