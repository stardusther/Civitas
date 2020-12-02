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
     * @note El valor no es aplicable en esta carta (valor=-1)
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab) {
        init();
        sorpresa = tipo;
        tablero = tab;
        texto =  " Vas a la carcel...";
    }

    /** Constructor para sorpresa que envia al jugador a otra casilla.
     * @note El valor corresponderá al núm de la casilla donde se debe trasladar al jugador.
     */
    Sorpresa (TipoSorpresa tipo, Tablero tab, int valor, String texto) {
        init();
        sorpresa = tipo;
        tablero = tab;
        this.texto = texto;
        this.valor = valor;
    }

    /** Constructor para sorpresa que evita la carcel.
     * @note valor no aplicable
     */
    Sorpresa (TipoSorpresa tipo, MazoSorpresas mazo) {
       init();
       sorpresa = tipo;
       this.mazo = mazo;
       texto = "Tienes un salvoconducto.";
       
    }

    /** Constructor para el resto de sorpresas (PORJUGADOR, PORCASAHOTEL, PAGARCOBRAR). */
    Sorpresa (TipoSorpresa tipo, int valor, String texto) {
        init();
        sorpresa = tipo;
        this.texto = texto;
        this.valor = valor;
    }


    /** Llama a aplicarJugador<tipo_de_sorpresa> en funcion del tipo de atributo sorpresa que se trate.
     * @note Todos los métodos aplicarJugador<...> revisan si en jugador indicado es correcto.
     * En caso contrario no se lleva a cabo ninguna acción.
     * @warnign Mejor quitar todas las comprobacines de los metodos privado y ponerla 
     * solo en el uncio que se llama? = menos líneas de cógido  (pendiente) 
     */
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {
            switch (sorpresa){
                case IRCARCEL:
                    aplicarAJugador_irCarcel (actual, todos);
                    break;
                case IRCASILLA:
                    aplicarAJugador_irACasilla (actual, todos);
                    break;
                case PAGARCOBRAR:
                    aplicarAJugador_pagarCobrar (actual, todos);
                    break;
                case PORCASAHOTEL:
                    aplicarAJugador_porCasaHotel (actual, todos);
                    break;
                case PORJUGADOR:
                    aplicarAJugador_porJugador (actual, todos);
                    break;
                case SALIRCARCEL:
                    aplicarAJugador_salirCarcel (actual, todos);
                case CONVERSION:
                    aplicarAJugador_conversion (actual, todos);
            }
        }
    }


    /** Mueve al jugador a la casilla indicada por el atributo valor.
     * @note Es necesario usar los metodos calcularTirada y nuevaPosicion para que quede registrado
     * un posible paso por salida como consecuencia del salto a la nueva casilla.
     */
    private void aplicarAJugador_irACasilla (int actual, ArrayList<Jugador> todos) {

        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);

            int casilla = todos.get(actual).getNumCasillaActual();      // 1. Obtenemos casilla actual del jugador
            int tirada = tablero.calcularTirada(casilla, valor);        // 2. Se calcula la tirada
            int nuevaPos = tablero.nuevaPosicion(casilla, tirada);      // 3. Se obtiene la nueva pos. del jugador

            todos.get(actual).moverACasilla(nuevaPos);                  // 4. Se mueve al jugador a esa nueva posicion

            tablero.getCasilla(valor).recibeJugador (actual, todos);    // 5. Se indica a la casilla que está en la posicion
                                                                        // del valor de la sorpresa que reciba al jugador
        }
    }

    private void aplicarAJugador_conversion (int actual, ArrayList<Jugador> todos) {
         if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            //JugadorEspeculador 
         }
     }
    /** Se encarcela la jugador.  */
    private void aplicarAJugador_irCarcel (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }


    /** Se modifica el saldo del jugador actual con el valor de la sorpresa. */
    private void aplicarAJugador_pagarCobrar (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }


    /** Se modifica el saldo del jugador actual con el valor de la sorpresa
     *  multiplicado por el núm de casas y hoteles del jugador. */
    private void aplicarAJugador_porCasaHotel (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);
            System.out.println(valor * todos.get(actual).cantidadCasasHoteles());
            todos.get(actual).modificarSaldo (valor*todos.get(actual).cantidadCasasHoteles());
        }
    }

    /** Todos los jugadores dan dinero al jugador actual. */
    private void aplicarAJugador_porJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);

            for (int i=0 ; i<todos.size() ; i++)
                if (i != actual)
                    todos.get(i).paga(valor);     
            
            todos.get(actual).recibe (valor);
        }
    }


    /** Se pregunta a todos los jugadores su alguien tiene la sorpresa para evitar 
     *  la carcel (salvoconducto), si nadie la tiene,la obtiene el jugador actual 
     *  y se llama al método salirDelMazo. */
    private void aplicarAJugador_salirCarcel (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {

            // Se comprueba que el resto de jugadores no tiene el salvoconducto
            boolean tienen_salvoconducto = false;
            for (int i=0 ; i<todos.size() && !tienen_salvoconducto; i++)
                tienen_salvoconducto = todos.get(i).tieneSalvoconducto();

            if (!tienen_salvoconducto) {
                informe (actual, todos);
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo ();
            }
        }
    }

    /** Informa al diario de que se está aplicando una sorpresa a un jugador (se indica su nombre). */
    private void informe (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos))
            Diario.getInstance().ocurreEvento("\n¡Sorpresa!" + texto + " Se aplica " + sorpresa + " al jugador " + todos.get(actual).getNombre() + "\n");
    }

    /** Comprueba si el primer parámetro es un indice valido para acceder al array de Jugadores. */
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos) {
        boolean correcto = false;

        if (actual >= 0 && actual < todos.size() )
            correcto = true;

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
        String str = " >> Sorpresa: " + sorpresa + ". Valor: " + valor + ". ";
        if (texto != "")
            str += texto + ". " ;
        if (tablero != null)
            str += "Tablero asociado. ";
        if (mazo != null)
            str += "Mazo asociado. ";
        return str;
    }

}