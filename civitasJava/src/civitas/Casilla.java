package civitas;
import java.util.ArrayList;

/**
 * @file Casilla.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @class Casilla
 * @brief Crea las casillas del tablero.
 * @warning Not fully implemented. 
 * @note Grupo B.3
*/

public class Casilla {

    private TipoCasilla tipo;                       // Tipo de casilla
    private String nombre;                          // Nombre casilla

    private int carcel;                             // Juez
    private float importe;                          // Impuesto
    private Sorpresa sorpresa;                      // Sopresa
    private MazoSorpresas mazo;                     // Mazo si la casilla es una sorpresa
    private TituloPropiedad tituloPropiedad;        // Calle


    /** Constructor descanso. */
    Casilla (String nombre) {
        init();
        this.nombre = nombre;
        tipo = TipoCasilla.DESCANSO;
    }

    /** Constructor calle. */
    Casilla (TituloPropiedad titulo) {
        init();
        nombre = titulo.getNombre();
        tituloPropiedad = titulo;
        tipo = TipoCasilla.CALLE;
    }

    /** Constructor impuesto. */
    Casilla (float cantidad, String nombre) {
        init();
        this.nombre = nombre;
        importe = cantidad;
        tipo = TipoCasilla.IMPUESTO;
    }

    /** Constructor juez. */
    Casilla (int numCasillaCarcel, String nombre) {
        init();
        this.nombre = nombre;
        carcel = numCasillaCarcel;
        tipo = TipoCasilla.JUEZ;
    }

    /** Constructor sorpresa. */
    Casilla (MazoSorpresas mazo, String nombre) {
        init();
        this.nombre = nombre;
        this.mazo = mazo;
        
        //System.out.println(mazo.toString() + "\n\n");
        //System.out.println(this.mazo.toString());
        
        tipo = TipoCasilla.SORPRESA;
    }

    /** Recibe a un jugador en la casilla. */
    void recibeJugador (int iactual, ArrayList<Jugador> todos) {
        
        switch (tipo){
            case CALLE:
                recibeJugador_calle(iactual, todos);
                break;
            case IMPUESTO:
                recibeJugador_impuesto (iactual, todos);
                break;
            case JUEZ:
                recibeJugador_juez (iactual, todos);
                break;
            case SORPRESA:
                recibeJugador_sorpresa (iactual, todos);
                break;
            default:
                informe (iactual, todos);           // Y: por qué?
        }
        
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
    // -------------------------- Met. privados ----------------------------- //
    // ---------------------------------------------------------------------- //


    private void informe (int actual, ArrayList<Jugador> todos) {
        String str = "El jugador " + todos.get(actual).getNombre() + " cae en casilla " + tipo;
        Diario.getInstance().ocurreEvento(str);
    }
    
    private void recibeJugador_calle (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            Jugador jugador = todos.get(actual);
            
            if (!tituloPropiedad.tienePropietario()) 
                jugador.puedeComprarCasilla();
            else 
                tituloPropiedad.tramitarAlquiler(jugador);
        }
    }

    private void recibeJugador_impuesto (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    private void recibeJugador_juez (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos.get(actual).encarcelar(carcel);
        }
    }

    private void recibeJugador_sorpresa (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            sorpresa = new Sorpresa (TipoSorpresa.IRCARCEL, mazo);
            sorpresa = mazo.siguiente();
            informe (actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }


    /** Este metodo hace una inicializacion de todos los atributos asumiendo que
     *  no se proporciona al constructor un valor para estos atributos. */
    private void init () {
        mazo = null;
        //sorpresa = null;
        tituloPropiedad = null;
        importe = -1f;
        carcel = -1;
        nombre = "";
    }
}
