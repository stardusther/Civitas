
package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa {
    
    SorpresaPagarCobrar(int valor, String texto) {
        super ("*Pagar/cobrar*");
        this.texto = texto;
        this.valor = valor;
    }
    
    /** Se modifica el saldo del jugador actual con el valor de la sorpresa. */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
}
