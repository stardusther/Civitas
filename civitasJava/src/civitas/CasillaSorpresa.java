
package civitas;

import java.util.ArrayList;


public class CasillaSorpresa extends Casilla {
    
    /** Constructor sorpresa. */
    CasillaSorpresa (String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.mazo = mazo;
        tipoCasilla = "SORPRESA";
    }
    
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            sorpresa = mazo.siguiente();
            informe (actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
