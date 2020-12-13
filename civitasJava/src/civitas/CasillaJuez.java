/**
 * @file CasillaImpuesto.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @brief Casilla de tipo calle. Subclase de Casilla.
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
    
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }
}
