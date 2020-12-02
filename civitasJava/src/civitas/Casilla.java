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
    
    /** Constructor descanso. */
    Casilla (String nombre) {
        init();
        this.nombre = nombre;
    }

    /** Recibe a un jugador en la casilla. */
    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        informe (iactual, todos);
    }

    // ---------------------------------------------------------------------- //
    // --------------------------- Consultores ------------------------------ //
    // ---------------------------------------------------------------------- //

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

    // ---------------------------------------------------------------------- //
    // -------------------- Met. privados/protegidos ------------------------ //
    // ---------------------------------------------------------------------- //

    protected void informe (int actual, ArrayList<Jugador> todos) {
        String str = "El jugador " + todos.get(actual).getNombre() + " cae en casilla DESCANSO";
        Diario.getInstance().ocurreEvento(str);
    }


    /** Este metodo hace una inicializacion de todos los atributos asumiendo que
     *  no se proporciona al constructor un valor para estos. */
    protected void init () {
        mazo = null;
        //sorpresa = null;
        tituloPropiedad = null;
        importe = -1f;
        carcel = -1;
        nombre = "";
    }
}
