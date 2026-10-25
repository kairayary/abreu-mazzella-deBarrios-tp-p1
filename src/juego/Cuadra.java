package juego;

import entorno.Entorno;

public class Cuadra {
    Casa[] casas;
    Negocio negocio;

    // Constructor que inicializa las casas y el negocio de esta cuadra (mínimo 3 casas y 1 negocio)
    public Cuadra(int xBase, int yBase) {
        this.casas = new Casa[3];
        // Distribuimos las 3 casas dentro del espacio de la cuadra
        this.casas[0] = new Casa(xBase - 30, yBase - 20);
        this.casas[1] = new Casa(xBase + 30, yBase - 20);
        this.casas[2] = new Casa(xBase, yBase + 20);
        
        // El negocio de esta cuadra
        this.negocio = new Negocio(xBase, yBase);
    }
    public boolean hayColision(int xRepartidor, int yRepartidor, int anchoRepartidor, int altoRepartidor) {
        // Verificar colisión con el negocio de la cuadra
        if (this.negocio.colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
            return true;
        }
        
        // Verificar colisión con cada una de las casas de la cuadra
        for (int i = 0; i < this.casas.length; i++) {
            if (this.casas[i].colisionaCon(xRepartidor, yRepartidor, anchoRepartidor, altoRepartidor)) {
                return true;
            }
        }
        
        return false;
    }

    public void dibujar(Entorno entorno) {
        // Dibujamos el negocio
        this.negocio.dibujar(entorno);
        // Dibujamos las casas de la cuadra
        for (int i = 0; i < this.casas.length; i++) {
            this.casas[i].dibujar(entorno);
        }
    }
}