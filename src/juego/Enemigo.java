package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Enemigo {
    // Variables de instancia
    int x;
    int y;
    String direccion; // Puede ser "HORIZONTAL" o "VERTICAL"
    int velocidad;
    int sentido = 1;  //1 para avanzar, -1 para el rebote con los margenes
    int ancho = 30;
    int alto = 40;
    Image imagen;

    // Constructor para inicializar al enemigo
    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2; // Velocidad inicial de desplazamiento
        this.imagen = Herramientas.cargarImagen("img/enemigo.png");
    }

    public void mover(int anchoEntorno, int altoEntorno, Cuadra[][] cuadras) {
        if (this.direccion.equals("VERTICAL")) {
            int proximoY = this.y + (this.velocidad * this.sentido);
            boolean colisiona = false;
            
            // Límites de pantalla (HUD arriba de 50px y piso abajo)
            if (proximoY - (this.alto / 2) < 50 || proximoY + (this.alto / 2) > altoEntorno) {
                colisiona = true;
            }
            
            // Colisión con las cuadras
            for (int f = 0; f < 4; f++) {
                for (int c = 0; c < 4; c++) {
                    if (cuadras[f][c].hayColision(this.x, proximoY, this.ancho, this.alto)) {
                        colisiona = true;
                    }
                }
            }
            
            if (colisiona) {
            	// Al chocar en vertical, cambia a horizontal
            	this.direccion = "HORIZONTAL";
                this.sentido = (Math.random() < 0.5) ? 1 : -1;
            } else {
                this.y = proximoY;
            }
            
        } else { // HORIZONTAL
            int proximoX = this.x + (this.velocidad * this.sentido);
            boolean colisiona = false;
            
            // Límites de pantalla izquierdo y derecho
            if (proximoX - (this.ancho / 2) < 0 || proximoX + (this.ancho / 2) > anchoEntorno) {
                colisiona = true;
            }
            
            // Colisión con las cuadras
            for (int f = 0; f < 4; f++) {
                for (int c = 0; c < 4; c++) {
                    if (cuadras[f][c].hayColision(proximoX, this.y, this.ancho, this.alto)) {
                        colisiona = true;
                    }
                }
            }
            
            if (colisiona) {
            	// Al chocar en horizontal, cambia a vertical
                this.direccion = "VERTICAL";
                this.sentido = (Math.random() < 0.5) ? 1 : -1;
            } else {
                this.x = proximoX;
            }
        }
    }
    
    //Método para dibujar al enemigo en la pantalla
    public void dibujar(Entorno entorno){
    	entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
}