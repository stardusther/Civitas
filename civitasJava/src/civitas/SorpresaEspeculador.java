
package civitas;

import java.util.ArrayList;


public class SorpresaEspeculador extends Sorpresa {
    
    private float fianza;
    
    SorpresaEspeculador (float fianza){
        super ("*Especulador*");
        this.fianza = fianza;
    }
    
    /** Se cambia al jugador por un jugador especulador.  */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            Jugador especulador = new JugadorEspeculador (todos.get(actual), fianza);
            todos.set(actual, especulador);
        }
    }
    
}
