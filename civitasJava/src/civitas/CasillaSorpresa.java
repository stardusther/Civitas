
package civitas;

import java.util.ArrayList;


public class CasillaSorpresa extends Casilla {
    
    /** Constructor sorpresa. */
    CasillaSorpresa (String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.mazo = mazo;
    }
    
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            sorpresa = mazo.siguiente();
            informe (actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
    
    @Override
    protected void informe (int actual, ArrayList<Jugador> todos) {
        String str = "El jugador " + todos.get(actual).getNombre() + " cae en casilla SORPRESA. ";
        Diario.getInstance().ocurreEvento(str);
    }
}
