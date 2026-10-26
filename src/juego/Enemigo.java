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

    // Método para mover al enemigo en línea recta y hacerlo rebotar en los limites
    public void mover(int anchoEntorno, int altoEntorno) {
        if (this.direccion.equals("VERTICAL")) {
            this.y += this.velocidad * this.sentido;
         //Rebote vertical (respeta el HUD arriba de 50px y el piso abajo)
            if (this.y - (this.alto / 2) < 50 || this.y + (this.alto / 2) > altoEntorno) {
                this.sentido *= -1; 
            }            
        } else {
            this.x += this.velocidad * this.sentido;
     
            //Rebote horizontal (izq y der)
            if (this.x - (this.ancho / 2) < 0 || this.x + (this.ancho / 2) > anchoEntorno) {
                this.sentido *= -1; 
            }
        }
    }
            
    //Método para dibujar al enemigo en la pantalla
    public void dibujar(Entorno entorno){
    	entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.25);
    }
}