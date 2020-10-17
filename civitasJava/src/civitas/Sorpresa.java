package civitas;
import java.util.ArrayList;  

/**
 * @file Sorpresa.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/

public class Sorpresa{
    private String texto;
    private int valor;

    private Tablero tablero;
    private TipoSorpresa sorpresa;
    private MazoSorpresas mazo;

    /**
     * @bief Constructor para sorpresa que envia a la carcel
     * @pre TipoSorpresa de tipo IRCARCEL
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab) {
        init();
        sorpresa = tipo;
        tablero = tab;
    }

    /**
     * @brief Constructor para sorpresa que envia al jugador a otra casilla
     * @pre TipoSorpresa de tipo IRACASILLA
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab, int valor) {
        init();
        sorpresa = tipo;
    }

    /**
     * @brief Constructor para sorpresa que evita la carcel
     * @pre TipoSorpresa de tipo SALIRCARCEL
     */
    Sorpresa (TipoSorpresa tipo, MazoSorpresas _mazo) {
       init();
       sorpresa = tipo;
    }

    /**
     * @brief Constructor para el resto de sorpresas
     */
    Sorpresa (TipoSorpresa tipo) {
        init();
        sorpresa = tipo;
    }


    void aplicarAJugador (int actual, Jugador todos[]) {

    }

    /**
     * @warning metodo recibeJugador?? SIN TERMINAR
     * @param actual
     * @param todos 
     */
    void aplicarAJugador_irACasilla (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            
            int casilla = todos[actual].getNumCasillaActual();
            int tirada = tablero.calcularTirada(casilla, valor);
            int nuevaPos = tablero.nuevaPosicion(casilla, tirada);
            
            todos[actual].moverACasilla(nuevaPos);
        }
    }
    
    void aplicarAJugador_irCarcel (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].encarcelar(tablero.getCarcel());
        }
    }

    void aplicarAJugador_pagarCobrar (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].modificarSaldo(valor);
        }
    }

    void aplicarAJugador_porCasaHotel (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].modificarSaldo (valor * todos[actual].cantidadCasasHoteles());
        }
    }

    /**
     * @warning valor de la sorpresa??
     * @param actual
     * @param todos 
     */
    void aplicarAJugador_porJugador (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            Sorpresa _sorpresa = new Sorpresa( TipoSorpresa.PAGARCOBRAR );
            _sorpresa.valor *= -1;
            
            for (int i=0 ; i<todos.length ; i++)
                if (i != actual)
                    todos[i].paga(_sorpresa.valor);     // ????
            
            Sorpresa _sorpresaActual = new Sorpresa (TipoSorpresa.PAGARCOBRAR);
            _sorpresaActual.valor *= todos.length -1;
            todos[actual].recibe(_sorpresaActual.valor);
        }
    }

    void aplicarAJugador_salirCarcel (int actual, Jugador todos[]) {

    }

    void informe (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos))
            Diario.getInstance().ocurreEvento("Se aplica sorpresa al jugador " + todos[actual].getNombre());
    }

    boolean jugadorCorrecto (int actual, Jugador todos[]) {
        boolean correcto = true;
        
        if (actual < todos.length ) 
            correcto = false;
        
        return correcto;
    }

    void salirDelMazo () {

    }

    private void init() {
        valor = -1;
        tablero = null;
        mazo = null;
    }

}
