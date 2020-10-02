package civitas;
import java.util.Random;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 */

public class Dado {
    
    private Random random;                              // Se utilizara para la generación de nº aleatorios
    private int ultimoResultado;    
    private boolean debug;
    
    private static Dado instance = new Dado ();
    private static final int SalidaCarcel = 5;
    
    static public Dado getInstance () {
        return instance;
    }
    
    Dado () {
        debug = false;
        random = new Random ();                         // es como una semilla?? Si no no le encuentro el sentido a inicializarlo
        ultimoResultado = -1;                           // llamar a tirar() para darle un valor válido ??????????
    }
    
    int tirar () {
        if (debug)
            ultimoResultado = 1;
        else
            ultimoResultado = random.nextInt(6) + 1;    // Num aleatorio entre 0 y 5, más 1
            
        return ultimoResultado;
    }
    
    boolean salgoDeLaCarcel() {
        boolean salgo = false;
        
        if (tirar() == SalidaCarcel)
            salgo = true;
        
        return salgo;
    }
    
    int quienEmpieza (int n) {
        return random.nextInt(n);                       //Num aleatorio entre 0 y n-1
    }
    
    void setDebug (boolean d) {
        if (d != debug) {                               // Si la configuracion no cambia no se añade el evento a diario
            debug = d;
            String modo = "Debug off (dado)";
            
            if (debug = true)
                modo = "Debug on (dado)";
                 
            Diario.getInstance().ocurreEvento(modo);     
        }
    }
    
    int getUltimoResultado () {
        return ultimoResultado;
    }
}
