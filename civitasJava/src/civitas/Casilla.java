package civitas;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @class Casilla
 * @brief Crea las casillas del tablero.
 * @warning Not fully implemented. Clase temporal
 * @note Grupo B.3
*/
public class Casilla {

    private String nombre;

    Casilla (String n) {
        nombre = n;
    }

    String getNombre () {
        return nombre;
    }
}
