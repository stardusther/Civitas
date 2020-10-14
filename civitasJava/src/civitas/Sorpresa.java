package civitas;

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
     */
    Sorpresa (TipoSorpresa sor, Tablero tab) {
        init();
    }
    
    /**
     * @brief Constructor para sorpresa que envia al jugador a otra casilla
     */
    Sorpresa (TipoSorpresa sor, Tablero tab, int valor) {
        init();
    }    
    
    /**
     * @brief Constructor para sorpresa que evita la carcel
     */
    Sorpresa (TipoSorpresa sor, MazoSorpresas _mazo) {
       init(); 
    }    
    
    /**
     * @brief Constructor para el resto de sorpresas
     */
    Sorpresa (TipoSorpresa sor) {
        init();
    }
    
    private void init() {
        valor = -1;
        tablero = null;
        mazo = null;
    }
    
    void aplicarAJugador (int actual, Jugador todos[]) {
        
    }
    
    void aplicarAJugador_irACasilla (int actual, Jugador todos[]) {
        
    }
    
    void aplicarAJugador_pagarCobrar (int actual, Jugador todos[]) {
        
    }
    
    void aplicarAJugador_porCasaHotel (int actual, Jugador todos[]) {
        
    }
    
    void aplicarAJugador_porJugador (int actual, Jugador todos[]) {
        
    }
    
    void aplicarAJugador_salirCarcel (int actual, Jugador todos[]) {
        
    }
    
    void informe (int actual, Jugador todos[]) {
        
    }
    
    boolean jugadorCOrrecto (int actual, Jugador todos[]) {
        return true;
    }
    
    void salirDelMazo () {
        
    }
    

    
}
