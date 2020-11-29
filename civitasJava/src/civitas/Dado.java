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

    private Random random;                              
    private int ultimoResultado;
    private boolean debug;

    private static Dado instance = new Dado ();
    private static final int SalidaCarcel = 5;

    /** Método de clase para obtener la instancia. */
    static public Dado getInstance () {
        return instance;
    }

    /** Constructor de la clase. */
    Dado () {
        debug = false;
        random = new Random ();                        
        ultimoResultado = -1;                           
    }

    /** Genera un numero aleatorio entre 1 y 6 si el modo debug está desactivado,
     *  o 1 si está activado. */
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
        
        //if (ultimoResultado >= SalidaCarcel)
        if (tirar() >= SalidaCarcel)     
          salgo = true;

        return salgo;
    }

    /** Genera un numero aleatorio entre 0 y n-1 para decidir quien empieza. */
    int quienEmpieza (int n) {
        return random.nextInt(n);                       //Num aleatorio entre 0 y n-1
    }

    /** Activa o desactiva el modo debug. */
    public void setDebug (boolean d) {
        if (d != debug) {                               // Si la configuracion no cambia no se añade el evento a diario
            debug = d;
            String modo = "Modo debug inhabilitado en dado";

            if (debug)
                modo = "Modo debug activo en dado";

            Diario.getInstance().ocurreEvento(modo);
        }
    }

    /** Consultor del atributo ultimoResultado. */
    int getUltimoResultado () {
        return ultimoResultado;
    }
}
