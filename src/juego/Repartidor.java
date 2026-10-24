package juego;

public class Repartidor {
	// Variables de instancia
    int x;
    int y;
    int vidas;

    // Constructor 
    public Repartidor(int xInicio, int yInicio) {
        this.x = xInicio;
        this.y = yInicio;
        this.vidas = 3; // Comienza con 3 vidas según los requerimientos
    }
 // Método de instancia para mover al repartidor según la tecla presionada
    public void moverArriba() { this.y -= 2; }
    public void moverAbajo() { this.y += 2; }
    public void moverIzquierda() { this.x -= 2; }
    public void moverDerecha() { this.x += 2; }
    
    // Método de instancia para ver su estado
    public void imprimir() {
        System.out.println("Repartidor en X: " + this.x + " | Y: " + this.y + " | Vidas: " + this.vidas);
}
    }
