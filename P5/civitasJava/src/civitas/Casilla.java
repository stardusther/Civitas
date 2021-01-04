package civitas;
import java.util.ArrayList;

/**
 * @file Casilla.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @class Casilla
 * @brief Crea las casillas del tablero.
 * @note Grupo B.3
*/

public class Casilla {

    /** Cambiamos las visibilidades a protected para que 
        la subclase pueda acceder a ellas. */
    protected String nombre;                      // Nombre casilla
    protected int carcel;                         // Juez
    protected float importe;                      // Impuesto
    protected Sorpresa sorpresa;                  // Sopresa
    protected MazoSorpresas mazo;                 // Mazo si la casilla es una sorpresa
    protected TituloPropiedad tituloPropiedad;    // Calle
    
    protected String tipoCasilla;                 // String para indicar el tipo de casilla
                                                  // (ahorrará redefinir el informe en todas las 
                                                  // subclases)
    
    /** Constructor descanso. */
    Casilla (String nombre) {
        mazo = null;
        tituloPropiedad = null;
        importe = -1f;
        carcel = -1;
        sorpresa = null;
        tipoCasilla = "DESCANSO";
        
        this.nombre = nombre;
    }

    /** Recibe a un jugador en la casilla. */
    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        informe (iactual, todos);
    }

    public String getNombre () {
        return nombre;
    }

    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }
    
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos) {
        return (actual < todos.size());
    }

    @Override
    public String toString() {
        String str = nombre ;
        return str;
    }

    protected void informe (int actual, ArrayList<Jugador> todos) {
        String str = "El jugador " + todos.get(actual).getNombre() + " cae en casilla " + tipoCasilla;
        Diario.getInstance().ocurreEvento(str);
    }
}