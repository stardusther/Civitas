
package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa {
    
    SorpresaPorCasaHotel (int valor, String texto) {
        super ("*Por casa/hotel*");
        this.texto = texto;
        this.valor = valor;
    }
    
    
    /** Se modifica el saldo del jugador actual con el valor de la sorpresa
     *  multiplicado por el núm de casas y hoteles del jugador. */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            System.out.println(valor * todos.get(actual).cantidadCasasHoteles());
            todos.get(actual).modificarSaldo (valor*todos.get(actual).cantidadCasasHoteles());
        }
    }
    
}
