
package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa {
    
    SorpresaIrCarcel (Tablero tab) {
        super("*Ir carcel*");
        tablero = tab;
        texto = " Vas a la carcel... ";
    }
    
    /** Se encarcela la jugador.  */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
}
