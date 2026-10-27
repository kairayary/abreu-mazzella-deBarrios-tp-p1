package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Enemigo {
    int x;
    int y;
    String direccion; // "HORIZONTAL" o "VERTICAL"
    int velocidad;
    int sentido = 1;  
    int ancho = 30;   
    int alto = 40;
    Image imagen;

    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2;
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
                // Al chocar en vertical, cambia a HORIZONTAL (simula doblar en la esquina)
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
                // Al chocar en horizontal, cambia a VERTICAL (simula doblar en la esquina)
                this.direccion = "VERTICAL";
                this.sentido = (Math.random() < 0.5) ? 1 : -1;
            } else {
                this.x = proximoX;
            }
        }
    }  
    
	 // COLISION CON EL REPARTIDOR
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
    
    
    public void dibujar(Entorno entorno){
        entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
}