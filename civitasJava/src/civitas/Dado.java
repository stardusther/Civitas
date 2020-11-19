package civitas;
import java.util.Random;


/**
 * @file Dado.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 * @class Dado
 * @brief Se encarga de todas las decisiones del juego relacionadas con el azar. 
 * @note Sigue el patrón singleton
 */

public class Dado {

    private Random random;                              // Se utilizara para la generación de nº aleatorios
    private int ultimoResultado;
    private boolean debug;

    private static Dado instance = new Dado ();
    private static final int SalidaCarcel = 5;

    /** Método de clase para obtener la instancia.
     * @note Para acceder a ella: Dado.getInstance()
     */
    static public Dado getInstance () {
        return instance;
    }

    /** Constructor de la clase. 
     * @post Deja debug a false e inicializa random
     * @warning Y: ultimoResultado a -1 o tirada valida?
     */
    Dado () {
        debug = false;
        random = new Random ();                        
        ultimoResultado = -1;                           // Y: llamar a tirar() para darle un valor válido ??
    }

    /** Genera un numero aleatorio entre 1 y 6 si el modo debug está desactivado,
     *  o 1 si está activado.
     * @return ultimoResultado
     */
    int tirar () {
        if (debug)
            ultimoResultado = 1;
        else
            ultimoResultado = random.nextInt(6) + 1;    // Num aleatorio entre 0 y 5, más 1

        return ultimoResultado;
    }

    /** Se tira el dado y se determina si el jugador puede salir de la carcel con el valor obtenido
     * @return @retval true si el numero obtenido le permite salir, @retval false si no es el caso
     */
    boolean salgoDeLaCarcel() {
        boolean salgo = false;

        //if (tirar() >= SalidaCarcel)                    //Mayor o igual a 5
        // Y: Se que es lo que pone en el pdf pero no le veo sentido para el juego ahhhhhh 
        // (y de paso es mas facil la tarea 3)
        if (ultimoResultado >= SalidaCarcel)
          salgo = true;

        return salgo;
    }

    /** Genera un numero aleatorio entre 0 y n-1 para decidir quien empieza.
     * @param n numero de jugadores
     * @return índice del jugador que empieza
     */
    int quienEmpieza (int n) {
        return random.nextInt(n);                       //Num aleatorio entre 0 y n-1
    }

    /** Activa o desactiva el modo debug
     */
    public void setDebug (boolean d) {
        if (d != debug) {                               // Si la configuracion no cambia no se añade el evento a diario
            debug = d;
            String modo = "Modo debug inhabilitado en dado";

            if (debug)
                modo = "Modo debug activo en dado";

            Diario.getInstance().ocurreEvento(modo);
        }
    }

    /** Consultor del atributo ultimoResultado
     * @return ultimoResultado
     */
    int getUltimoResultado () {
        return ultimoResultado;
    }
}
