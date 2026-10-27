package juego;

import entorno.Entorno;
import java.awt.Color;

public class Proyectil {
    int x;
    int y;
    int dirX;
    int dirY;
    int velocidad;

    public Proyectil(int x, int y, int dirX, int dirY) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.velocidad = 6; // Velocidad del proyectil
    }

    public void mover() {
        this.x += this.dirX * this.velocidad;
        this.y += this.dirY * this.velocidad;
    }

    public void dibujar(Entorno entorno) {
        // Dibujamos el proyectil como un pequeño rectángulo amarillo brillante
        entorno.dibujarRectangulo(this.x, this.y, 8, 8, 0, Color.YELLOW);
    }

    public boolean colisionaCon(Enemigo enemigo) {
        if (enemigo == null) return false;
        
        int anchoProyectil = 8;
        int altoProyectil = 8;
        int anchoEnemigo = 30;
        int altoEnemigo = 40;

        int diferenciaX = enemigo.x - this.x;
        int umbralX = (anchoEnemigo + anchoProyectil) / 2;
        boolean colisionX = (diferenciaX > -umbralX) && (diferenciaX < umbralX);

        int diferenciaY = enemigo.y - this.y;
        int umbralY = (altoEnemigo + altoProyectil) / 2;
        boolean colisionY = (diferenciaY > -umbralY) && (diferenciaY < umbralY);

        return colisionX && colisionY;
    }
}