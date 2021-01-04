/**
 * @file CasillaImpuesto.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @brief Casilla de tipo sorpresa. Subclase de Casilla.
 * @note Grupo B.3
*/

package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {
    
    /** Constructor sorpresa. */
    CasillaSorpresa (String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.mazo = mazo;
        tipoCasilla = "SORPRESA";
    }
    
    /** Recibe al jugador en la casilla, aplicando la sorpresa
     * correspondiente segun el mazo. */
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            sorpresa = mazo.siguiente();
            informe (actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
