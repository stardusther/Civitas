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
    
    //Casilla cImpuesto = new Casilla();
    
    /** Constructor impuesto. */
    CasillaImpuesto (String nombre, float cantidad) {
        super (nombre);
        //cImpuesto.nombre = nombre;     // Nombre de la casilla
        importe = cantidad;  // Impuesto a aplicar
    }
    
    /** Recibe a un jugador en la casilla y le aplica el pago del impuesto. */
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }
    
    @Override
    protected void informe (int actual, ArrayList<Jugador> todos) {
        String str = "El jugador " + todos.get(actual).getNombre() + " cae en casilla IMPUESTO. ";
        Diario.getInstance().ocurreEvento(str);
    }
    
}
