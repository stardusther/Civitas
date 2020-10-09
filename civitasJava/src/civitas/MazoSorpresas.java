package civitas;
import java.util.ArrayList;
import java.util.Collections;                //Para hacer el shuffle

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 */

public class MazoSorpresas {
     private ArrayList<Sorpresa> sorpresas;               //Almacena cartas de sorpresa
     private Boolean barajada;                            //Determina si el mazo de cartas ha sido barajado
     private int usadas;                                  //Número de cartas del mazo usadas
     private Boolean debug;                               //Activa o desactiva el modo debug
     private ArrayList<Sorpresa> cartasEspeciales;        //Almacena SALIRCARCEL mientras está fuera del MazoSorpresas
     Sorpresa ultimaSorpresa;                             //Almacena la última carta de sorpresa que ha salido

     private void init(){
       sorpresas = new ArrayList<> ();
       cartasEspeciales = new ArrayList<> ();
       barajada = false;
       usadas = 0;
     }

     MazoSorpresas(){
       init();
       debug = false;
     }

     MazoSorpresas(Boolean _debug){
       debug = _debug;
       init();

       if(debug)
          Diario.getInstance().ocurreEvento ("Modo debug activo");

     }

    void alMazo(Sorpresa s){
      if (!barajada)
        sorpresas.add(s);
    }

    Sorpresa siguiente (){
      if ( (!barajada || usadas == sorpresas.size()) && !debug){
        Collections.shuffle (sorpresas);                               //Baraja el mazo de sorpresas
        usadas = 0;
        barajada = true;
      }

      usadas++;
      ultimaSorpresa = sorpresas.get(0);
      sorpresas.remove(0);
      sorpresas.add(ultimaSorpresa);

      return ultimaSorpresa;
    }

    void inhabilitarCartaEspecial (Sorpresa sorpresa){        //E: habrá que comprobar si es una cartaEspecial ???
        if(cartasEspeciales.contains(sorpresa)){              // Si está en el mazo:
          cartasEspeciales.add(sorpresa);                     // Se añade a cartasEspeciales
          sorpresas.remove(i);                                // Se elimina del mazo

          Diario.getInstance().ocurreEvento ("Se ha inhabilitado una carta especial");
        }
    }

    void habilitarCartaEspecial (Sorpresa sorpresa){
      if(cartasEspeciales.contains(sorpresa)){                // Si está en cartas cartasEspeciales
          sorpresas.add(sorpresa);                            // Se añade al mazo de sorpresas
          cartasEspeciales.remove(i);                         // Se elimina de cartasEspeciales

          Diario.getInstance().ocurreEvento ("Se ha habilitado una carta especial");
    }

}
