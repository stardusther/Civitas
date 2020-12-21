/**
 * @file CasillaImpuesto.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @brief Casilla de tipo juez. Subclase de Casilla.
 * @note Grupo B.3
*/

package civitas;
import java.util.ArrayList;

public class CasillaJuez extends Casilla {
    
    /** Constructor juez. */
    CasillaJuez (String nombre, int numCasillaCarcel) {
        super (nombre);
        carcel = numCasillaCarcel;
        tipoCasilla = "JUEZ";
    }
    
    /** Recibe al jugador en la casilla aplicando el proceso ç 
     *  correspondiente al juez. */
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
}
