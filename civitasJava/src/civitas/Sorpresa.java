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

    /** Constructor para sorpresa que envia a la carcel.
     * @pre TipoSorpresa de tipo IRCARCEL
     * @note El valor no es aplicable en esta carta (valor=-1)
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab) {
        init();
        sorpresa = tipo;
        tablero = tab;
    }

    /** Constructor para sorpresa que envia al jugador a otra casilla.
     * @pre TipoSorpresa de tipo IRACASILLA
     * @note El valor corresponderá al núm de la casilla donde se debe trasladar al jugador.
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab, int valor, String texto) {
        init();
        sorpresa = tipo;
        this.valor = valor;
    }

    /** Constructor para sorpresa que evita la carcel.
     * @pre TipoSorpresa de tipo SALIRCARCEL
     * @note valor no aplicable
     */
    Sorpresa (TipoSorpresa tipo, MazoSorpresas mazo) {
       init();
       sorpresa = tipo;
       this.mazo = mazo;
    }

    /** Constructor para el resto de sorpresas (PORJUGADOR, PORCASAHOTEL, PAGARCOBRAR). */
    Sorpresa (TipoSorpresa tipo, int valor, String texto) {
        init();
        sorpresa = tipo;
        this.valor = valor;
    }


    /** Llama a aplicarJugador<tipo_de_sorpresa> en funcion del tipo de atributo sorpresa que se trate. 
     * @note Todos los métodos aplicarJugador<...> revisans si en jugador indicado es correcto. 
     *       En caso contrario no se lleva a cabo ninguna acción.*/
    void aplicarAJugador (int actual, Jugador todos[]) {

    }

    
    /** Mueve al jugador a la casilla indicada por el atributo valor.
     * @warning metodo recibeJugador sin terminar !!
     * @note Es necesario usar los metodos calcularTirada y nuevaPosicion para que quede registrado
     * un posible paso por salida como consecuencia del salto a la nueva casilla.
     */
    private void aplicarAJugador_irACasilla (int actual, Jugador todos[]) {
        
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            
            int casilla = todos[actual].getNumCasillaActual();          // 1. Obtenemos casilla actual del jugador
            int tirada = tablero.calcularTirada(casilla, valor);        // 2. Se calcula la tirada
            int nuevaPos = tablero.nuevaPosicion(casilla, tirada);      // 3. Se obtiene la nueva pos. del jugador
            
            todos[actual].moverACasilla(nuevaPos);                      // 4. Se mueve al jugador a esa nueva posicion
            
                                                                        // 5. Se indica a la casilla que está en la posicion 
                                                                        // del valor de la sorpresa que reciba al jugador
        }
    }
    
    
    /** Se encarcela la jugador. */
    private void aplicarAJugador_irCarcel (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].encarcelar(tablero.getCarcel());
        }
    }

    
    /** Se modifica el saldo del jugador actual con el valor de la sorpresa. */
    private void aplicarAJugador_pagarCobrar (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].modificarSaldo(valor);
        }
    }

    
    /** Se modifica el saldo del jugador actual con el valor de la sorpresa 
     *  multiplicado por el núm de casas y hoteles del jugador. */
    private void aplicarAJugador_porCasaHotel (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos[actual].modificarSaldo (valor * todos[actual].cantidadCasasHoteles());
        }
    }

    /** Todos los jugadores dan dinero al jugador actual. 
     * @warning valor de sorpresas !! */
    private void aplicarAJugador_porJugador (int actual, Jugador todos[]) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            Sorpresa _sorpresa = new Sorpresa (TipoSorpresa.PAGARCOBRAR, valor * -1, "");
            
            for (int i=0 ; i<todos.length ; i++)
                if (i != actual)
                    todos[i].paga(_sorpresa.valor);     // ????
            
            Sorpresa _sorpresaActual = new Sorpresa (TipoSorpresa.PAGARCOBRAR, valor * todos.length, "");
            _sorpresaActual.valor *= todos.length -1;
            todos[actual].recibe (_sorpresaActual.valor);
        }
    }

    
    /** Se pregunta a todos los jugadores su alguien tiene la sorpresa para evitar la carcel (salvoconducto),
     *  si nadie la tiene,la obtiene el jugador actual y se llama al método salirDelMazo. */
    private void aplicarAJugador_salirCarcel (int actual, Jugador todos[]) {
        
        if (jugadorCorrecto (actual, todos)) {
            
            boolean tienen_salvoconducto = false;
            for (int i=0 ; i<todos.length && !tienen_salvoconducto; i++) 
                tienen_salvoconducto = todos[i].tieneSalvoconducto();
            
            if (!tienen_salvoconducto) {
                todos[actual].obtenerSalvoconducto(this);
                salirDelMazo ();
            }
        }
    }

    
    /** Informa al diario de que se está aplicando una sorpresa a un jugador (se indica su nombre). */
    private void informe (int actual, Jugador todos[]) {
        if (jugadorCorrecto (actual, todos))
            Diario.getInstance().ocurreEvento("Se aplica sorpresa al jugador " + todos[actual].getNombre());
    }

    
    /** Comprueba si el primer parámetro es un indice valido para acceder al array de Jugadores. */
    public boolean jugadorCorrecto (int actual, Jugador todos[]) {
        boolean correcto = true;
        
        if (actual < todos.length ) 
            correcto = false;
        
        return correcto;
    }

    
    /**  Si la sorpresa es SALIRCARCEL, inhabilita la carta en el mazo. */
    void salirDelMazo () {
        if (sorpresa == TipoSorpresa.SALIRCARCEL) 
            mazo.inhabilitarCartaEspecial(this);
    }
    
    
    /** Si la sorpresa es SALIRCARCEL, habilita la carta en el mazo. */
    void usada() {
        if (sorpresa == TipoSorpresa.SALIRCARCEL) 
            mazo.habilitarCartaEspecial(this);
    }

    
    /** Fija valor a -1, hace nulas las referencias al mazo y el tablero, 
     *  y deja el texto como cadena vacía. */
    private void init() {
        valor = -1;
        tablero = null;
        mazo = null;
        texto = "";
    }
    
    @Override
    public String toString () {
        String str = " >> Sorpresa: " + texto + ". >> Valor: " + valor;
        return str;
    }

}
