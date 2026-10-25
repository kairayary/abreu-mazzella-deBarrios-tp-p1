package juego;

import entorno.Entorno;

public class Cuadra {
    Casa[] casas;
    Negocio negocio;

    public Cuadra(int xBase, int yBase) {
        this.casas = new Casa[3];
        // Distribuimos las 3 casas dentro del espacio de la cuadra con sus signos correctos
        this.casas[0] = new Casa(xBase - 30, yBase - 20);
        this.casas[1] = new Casa(xBase + 30, yBase - 20);
        this.casas[2] = new Casa(xBase, yBase + 20);
        
        // El negocio de esta cuadra
        this.negocio = new Negocio(xBase, yBase);
    }

    public void dibujar(Entorno entorno) {
        this.negocio.dibujar(entorno);
        for (int i = 0; i < this.casas.length; i++) {
            this.casas[i].dibujar(entorno);
        }
    }
    
    public boolean hayColision(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
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
}