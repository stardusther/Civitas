/**
 * @file CasillaImpuesto.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @brief Casilla de tipo impuesto. Subclase de Casilla.
 * @note Grupo B.3
*/

package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {
    
    /** Constructor impuesto. */
    CasillaImpuesto (String nombre, float cantidad) {
        super (nombre);
        importe = cantidad;  
        tipoCasilla = "IMPUESTO";
    }
    
    /** Recibe a un jugador en la casilla y le aplica el pago del impuesto. */
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
}
