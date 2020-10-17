/**
 * @file MazoSopresas.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 */

package civitas;
import java.util.ArrayList;
import java.util.Collections;       //Para hacer el shuffle

/**
 * @class MazoSorpresas
 * @brief Además de almacenar las cartas, las instancias de esta clase velan por que el mazo
 * se mantenga consistente a lo largo del juego y para que se produzcan las operaciones de barajado
 * cuando se han usado todas las cartas.
*/
public class MazoSorpresas {

     private ArrayList<Sorpresa> sorpresas;               //Almacena cartas de sorpresa
     private Boolean barajada;                            //Determina si el mazo de cartas ha sido barajado
     private int usadas;                                  //Número de cartas del mazo usadas
     private Boolean debug;                               //Activa o desactiva el modo debug
     private ArrayList<Sorpresa> cartasEspeciales;        //Almacena SALIRCARCEL mientras está fuera del MazoSorpresas
     private Sorpresa ultimaSorpresa;                     //Almacena la última carta de sorpresa que ha salido

     /**
      * @brief Inicializa los atributos de la clase MazoSorpresas
      * @post inicializa los vectores sorpresas y cartasEspeciales, deja barajada a false y usadas a 0
      */
     private void init(){
       sorpresas = new ArrayList<> ();
       cartasEspeciales = new ArrayList<Sorpresa> ();
       barajada = false;
       usadas = 0;
     }

     /**
     * @brief Constructor sin parámetros de la clase MazoSopresas
     * @post Llama a init() e inicializa debug a false.
     */
     MazoSorpresas(){
       init();
       debug = false;
     }

     /**
     * @brief Constructor con parámetros de la clase MazoSopresas
     * @param _debug Booleano que determina el valor de debug
     * @post Llama a init() y si se activa el modo debug añade el evento al Diario.
     */
     MazoSorpresas(Boolean _debug){
       debug = _debug;
       init();

       if(debug)
          Diario.getInstance().ocurreEvento ("Modo debug activo");
     }

     /**
     * @brief Añade al mazo de sorpresas una carta
     * @param s Una carta de sorpresa
     */
     void alMazo(Sorpresa s){
      if (!barajada)
        sorpresas.add(s);
     }

    /**
    * @brief Descarta la sorpresa actual y pasa a la siguiente
    * @return ultimaSorpresa La última carta de sorpresa usada
    */
    Sorpresa siguiente (){
        if ( (!barajada || usadas == sorpresas.size()) && !debug){
            Collections.shuffle (sorpresas);               //Baraja el mazo de sorpresas
            usadas = 0;
            barajada = true;
        }

        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);

        return ultimaSorpresa;
    }

    /**
    * @brief Inhabilita la carta pasada como parámetro
    * @param sorpresa Carta de sorpresa
    * @warning E: comprobar si es una carta especial?
    */
    void inhabilitarCartaEspecial (Sorpresa sorpresa){
        if (cartasEspeciales.contains(sorpresa)){               // Si está en el mazo:
            cartasEspeciales.add(sorpresa);                        // Se añade a cartasEspeciales
            sorpresas.remove(sorpresa);                            // Se elimina del mazo

          Diario.getInstance().ocurreEvento ("Se ha inhabilitado una carta especial");
        }
    }

    /**
    * @brief Habilita la carta pasada como parámetro
    * @param sorpresa Carta de sorpresa
    */
    void habilitarCartaEspecial (Sorpresa sorpresa){
        if (cartasEspeciales.contains(sorpresa)){                // Si está en cartas cartasEspeciales:
            sorpresas.add(sorpresa);                                // Se añade al mazo de sorpresas
            cartasEspeciales.remove(sorpresa);                      // Se elimina de cartasEspeciales

          Diario.getInstance().ocurreEvento ("Se ha habilitado una carta especial");
        }
    }
}
