package civitas;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 */

/**
 * @class Casilla
 * @brief Crea las casillas del tablero.
 * @warning Not fully implemented. Clase temporal
*/
public class Casilla {

    private String nombre;

    /**Constructor de la clase casilla.
    */
    Casilla (String n) {
        nombre = n;
    }

    /** Consultor del atributo getNombre.
    */
    String getNombre () {
        return nombre;
    }
}
