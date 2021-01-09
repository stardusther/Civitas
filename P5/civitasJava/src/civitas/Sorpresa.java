package civitas;
import java.util.ArrayList;

/**
 * @file Sorpresa.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/

abstract class Sorpresa{
    
    // Cambiamos visibilidades a protected:
    protected String texto;
    protected int valor;

    protected Tablero tablero;
    protected MazoSorpresas mazo;
    
    protected String tipo;  // Para no redefinir el metodo informe en todas las subclases

    /** Constructor para sorpresa. */
    Sorpresa (String tipo) {
        this.tipo = tipo;
        init();
    }

    /** Llama a aplicarJugador<tipo_de_sorpresa> en funcion del tipo de atributo sorpresa que se trate.
     * @note Todos los métodos aplicarJugador<...> revisan si en jugador indicado es correcto.
     * En caso contrario no se lleva a cabo ninguna acción.
     * @warnign Mejor quitar todas las comprobacines de los metodos privado y ponerla 
     * solo en el uncio que se llama? = menos líneas de cógido  (pendiente) 
     */
    abstract void aplicarAJugador (int actual, ArrayList<Jugador> todos) ;
    
    /** Informa al diario de que se está aplicando una sorpresa a un jugador (se indica su nombre). */
    protected void informe (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos))
            Diario.getInstance().ocurreEvento("¡Sorpresa!" + texto + " Se aplica " + tipo + " al jugador " + todos.get(actual).getNombre());
    }

    /** Comprueba si el primer parámetro es un indice valido para acceder al array de Jugadores. */
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos) {
        boolean correcto = false;

        if (actual >= 0 && actual < todos.size() )
            correcto = true;

        return correcto;
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
        String str = " >> Sorpresa: " + tipo + ". Valor: " + valor + ". ";
        if (texto != "")
            str += texto + ". " ;
        if (tablero != null)
            str += "Tablero asociado. ";
        if (mazo != null)
            str += "Mazo asociado. ";
        return str;
    }

}