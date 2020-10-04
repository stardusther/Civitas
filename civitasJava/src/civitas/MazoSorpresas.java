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

     MazoSorpresas(Boolean _debug){
       debug = _debug;
       init();

       if(debug){
          Diario diario = diario.getInstance();
          diario.ocurreEvento("Modo Debug Activo");
          //diario.leerEvento();      E: not sure :/
       }
     }

     MazoSorpresas(){
       init();
       debug = false;
     }

    void alMazo(Sorpresa s){
      if(!barajada){
        sorpresas.add(s);
      }
    }

    Sorpresa siguiente (){
      if((!barajada || usadas == sorpresas.size()) && !debug){
        Collections.shuffle(sorpresas);                   //baraja el mazo de sorpresas
        usadas = 0;
        barajadas = true;
      }

      usadas++;
      ultimaSorpresa = sorpresas.get(0);
      sorpresas.remove(0);

      return ultimaSorpresa;
    }

    void inhabilitarCartaEspecial (Sorpresa sorpresa){ //E: habrá que comprobar si es una cartaEspecial ????
      Boolean found = false;

      for (int i=0; i < sorpresas.size() && !found; i++)
        if(sorpresa == sorpresas.get(i)){                 //Si está en el mazo
          cartasEspeciales.add(sorpresa);              //Se añade a cartasEspeciales
          sorpresas.remove(i);                            //E: Supongo que existirá un método para borrar una carta
          Diario diario = diario.getInstance();           //E: Not sure
          diario.ocurreEvento("Se ha inhabilitado una carta especial");
          //diario.leerEvento();
          found = true;
        }
    }

    void habilitarCartaEspecial (Sorpresa sorpresa){
      Boolean found = false;

      for (int i=0; i < cartasEspeciales.size() && !found; i++)
        if(sorpresa == cartasEspeciales.get(i)){    //Si está en cartas cartasEspeciales
          sorpresas.alMazo(sorpresa);               //Se añade al mazo de sorpresas
          cartasEspeciales.remove(i);               //Se elimina de cartasEspeciales
          diario.ocurreEvento("Se ha habilitado una carta especial");
          //diario.leerEvento();
          found = true;
        }
    }

   }
