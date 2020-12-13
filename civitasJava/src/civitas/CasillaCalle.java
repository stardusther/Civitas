/**
 * @file CasillaImpuesto.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @brief Casilla de tipo calle. Subclase de Casilla.
 * @note Grupo B.3
*/

package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {
    
    /** Constructor calle. */
    CasillaCalle (TituloPropiedad titulo) {
        super (titulo.getNombre());         // Nombre de la casilla
        tituloPropiedad = titulo;           // Impuesto a aplicar
        tipoCasilla = "CALLE";
    }
    
    /** Recibe a un jugador en la casilla, gestionando la operación 
     * correspondiente (comprar o alquiler según el propietario). */
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            Jugador jugador = todos.get(actual);

            if (!tituloPropiedad.tienePropietario()) 
                jugador.puedeComprarCasilla();
            else if (!tituloPropiedad.getHipotecado())
                tituloPropiedad.tramitarAlquiler(jugador);
        }
    }
}
