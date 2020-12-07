
package civitas;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa {
    
    SorpresaPorJugador (int valor, String texto) {
        super ("*Por jugador*");
        this.texto = texto;
        this.valor = valor;
    }
    
    /** Todos los jugadores dan/reciben dinero del jugador actual. */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);

            for (int i=0 ; i<todos.size() ; i++)
                if (i != actual)
                    todos.get(i).paga(valor);     
            
            todos.get(actual).recibe (valor);
        }
    }
    
}
