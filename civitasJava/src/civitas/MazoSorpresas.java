/**
 * @file MazoSopresas.java
 * @class MazoSorpresas
 * @brief Además de almacenar las cartas, las instancias de esta clase velan por que el mazo
 * se mantenga consistente a lo largo del juego y para que se produzcan las operaciones de barajado
 * cuando se han usado todas las cartas.
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 */

package civitas;
import java.util.ArrayList;
import java.util.Collections;       
import java.util.List;

public class MazoSorpresas {

     private ArrayList<Sorpresa> sorpresas;               //Almacena cartas de sorpresa
     private Boolean barajada;                            //Determina si el mazo de cartas ha sido barajado
     private int usadas;                                  //Número de cartas del mazo usadas
     private Boolean debug;                               //Activa o desactiva el modo debug
     private ArrayList<Sorpresa> cartasEspeciales;        //Almacena SALIRCARCEL mientras está fuera del MazoSorpresas
     private Sorpresa ultimaSorpresa;                     //Almacena la última carta de sorpresa que ha salido

    /** Inicializa los atributos de la clase MazoSorpresas,
      *  deja barajada a false y usadas a 0.
      */
     private void init(){
       sorpresas = new ArrayList<> ();
       cartasEspeciales = new ArrayList<> ();
       barajada = false;
       ultimaSorpresa = null;
       usadas = 0;
     }

    /** Constructor sin parámetros de la clase MazoSopresas.
     * @post Llama a init() e inicializa debug a false.
     */
     MazoSorpresas(){
       init();
       debug = false;
     }

    /** Constructor con parámetros de la clase MazoSopresas.
     * @param _debug Booleano que determina el valor de debug
     * @post Llama a init() y si se activa el modo debug añade el evento al Diario.
     */
     MazoSorpresas(Boolean _debug){
       debug = _debug;
       init();
       if(debug)
          Diario.getInstance().ocurreEvento ("Modo debug activo");
     }

    /** Añade al mazo de sorpresas una carta. */
     void alMazo(Sorpresa s){
      if (!barajada)
        sorpresas.add(s);
     }

   /** Descarta la sorpresa actual y pasa a la siguiente.
    *  @return ultimaSorpresa La última carta de sorpresa usada
    */
    Sorpresa siguiente (){
        if ( (!barajada || usadas == sorpresas.size()) && !debug){  // Si no está barajada o se han usado ya todas -->
            Collections.shuffle (sorpresas);                        // baraja el mazo 
            usadas = 0;
            barajada = true;
        }

        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);                    // Eliminamos carte de la primera posicion y 
        sorpresas.add(ultimaSorpresa);          // la añadimos a la última

        return ultimaSorpresa;
    }

   /** Inhabilita la carta pasada como parámetro. */
    void inhabilitarCartaEspecial (Sorpresa sorpresa){
        if (sorpresas.contains(sorpresa)){               // Si está en el mazo:
            cartasEspeciales.add(sorpresa);                // se añade a cartasEspeciales
            sorpresas.remove(sorpresa);                    // se elimina del mazo

          Diario.getInstance().ocurreEvento ("Se ha inhabilitado una carta especial");
        }
    }

   /** Habilita la carta pasada como parámetro. */
    void habilitarCartaEspecial (Sorpresa sorpresa){
        if (cartasEspeciales.contains(sorpresa)){        // Si está en cartasEspeciales:
            sorpresas.add(sorpresa);                       // Se añade al mazo de sorpresas
            cartasEspeciales.remove(sorpresa);             // Se elimina de cartasEspeciales

          Diario.getInstance().ocurreEvento ("Se ha habilitado una carta especial");
        }
    }
    
    @Override
    public String toString () {
        String s = " >> Mazo: contiene " + sorpresas.size() + " sorpresas. " +
                   "Se han usado " + usadas + " cartas del mazo. " ;
        
        if (barajada)
            s += "Está barajada. ";
        if (debug)
            s += "Debug on. ";
        
        for (int i=0 ; i<sorpresas.size() ; i++)
            s += "\n  " + sorpresas.get(i).toString();
        
        return s;
    }
}
