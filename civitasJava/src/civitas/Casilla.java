/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 */

package civitas;

/**
 * @class Casilla
 * @brief Crea las casillas del tablero
 * @warning Not fully implemented. Clase temporal
*/
public class Casilla {

    private String nombre;

    /*
    * @brief Constructor de la clase casilla
    */
    Casilla (String n) {
        nombre = n;
    }

    /*
    * @brief Consultor del atributo getNombre
    */
    String getNombre () {
        return nombre;
    }
}
