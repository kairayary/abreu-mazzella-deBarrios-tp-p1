package juego;

public class Enemigo {
	// Variables de instancia
    int x;
    int y;
    String direccion; // Puede ser "HORIZONTAL" o "VERTICAL"
    int velocidad;

    // Constructor  para inicializar al enemigo
    public Enemigo(int xInicio, int yInicio, String direccionMovimiento) {
        this.x = xInicio;
        this.y = yInicio;
        this.direccion = direccionMovimiento;
        this.velocidad = 2; // Velocidad inicial de desplazamiento
    }

    // Método para mover al enemigo en línea recta
    public void mover() {
        if (this.direccion.equals("VERTICAL")) {
            this.y += this.velocidad;
        } else {
            this.x += this.velocidad;
        }
    }
}
