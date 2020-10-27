package civitas;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @class Casilla
 * @brief Crea las casillas del tablero.
 * @warning Not fully implemented. Clase temporal
 * @note Grupo B.3
*/
public class Casilla {

    private TipoCasilla tipo;                       // Tipo de casilla
    private String nombre;

    private int carcel;                             // Juez
    private float importe;                          // Impuesto
    private Sorpresa sorpresa;                      // Sopresa
    private MazoSorpresas mazo;                     // Mazo si la casilla es una sorpresa
    private TituloPropiedad tituloPropiedad;        // Calle


    /** Constructor casilla descanso. */
    Casilla (String nombre) {
        init();
        this.nombre = nombre;
        tipo = TipoCasilla.DESCANSO;
    }

    /** Constructor casilla calle. */
    Casilla (TituloPropiedad titulo) {
        init();
        nombre = titulo.getNombre();
        tituloPropiedad = titulo;
    }

    /** Constructor casilla impuesto. */
    Casilla (float cantidad, String nombre) {
        init();
        this.nombre = nombre;
        importe = cantidad;
        tipo = TipoCasilla.CALLE;
    }

    /** Constructor casilla juez. */
    Casilla (int numCasillaCarcel, String nombre) {
        init();
        this.nombre = nombre;
        carcel = numCasillaCarcel;
        tipo = TipoCasilla.JUEZ;
    }

    /** Constructor casilla sorpresa. */
    Casilla (MazoSorpresas mazo, String nombre) {
        init();
        this.nombre = nombre;
        this.mazo = mazo;
        tipo = TipoCasilla.SORPRESA;
    }


    public boolean jugadorCorrecto (int actual, Jugador todos[]) {
        return (actual < todos.length);
    }


    /** @warning Siguiente práctica */
    void recibeJugador (int iactual, Jugador todos[]) {

    }


    // ------------- Consultores -------------- //


    public String getNombre () {
        return nombre;
    }


    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }


    @Override
    public String toString() {
        String str = " >> Casilla " + nombre;
        //Falta poner el resto de valores según el tipo de casilla (importe etc)
       return str;
    }


    // ------------- Met. privados -------------- //


    private void informe (int actual, Jugador todos[]) {
        String str = "El jugador " + todos[actual] + " ha caido en una casilla.\n" + toString();
        Diario.getInstance().ocurreEvento(str);
    }


    /** @warning Siguiente práctica */
    private void recibeJugador_calle (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos[actual].pagaAlquiler(tituloPropiedad.getPrecioAlquiler());    // Y: creo? E: sí
        }
    }


    private void recibeJugador_impuesto (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos[actual].pagaImpuesto(importe);
        }
    }


    private void recibeJugador_juez (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            todos[actual].encarcelar(carcel);
        }
    }


    /** @warning Siguiente práctica */
    private void recibeJugador_sorpresa (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos)) {
            informe (actual, todos);
            sorpresa.aplicarAJugador(actual, todos);
        }
    }


    /** Este metodo hace una inicializacion de todos los atributos asuminedo que no se proporciona
     *  al constructor un valor para ese atributo. */
    private void init () {
        mazo = null;
        sorpresa = null;
        tituloPropiedad = null;

        importe = -1f;
        carcel = -1;

        nombre = "";
    }
}
