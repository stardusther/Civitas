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

       if(debug){
          //Diario diario = diario.getInstance();                 Y: Esto da error de compilacion :(
          // diario.ocurreEvento("Modo Debug Activo");    
          
          Diario.getInstance().ocurreEvento ("Modo debug activo");
          //diario.leerEvento();      E: not sure :/        Y: creo que no hace falta
       }
     }

    void alMazo(Sorpresa s){
      if (!barajada)
        sorpresas.add(s);
    }

    Sorpresa siguiente (){
      if ( (!barajada || usadas == sorpresas.size()) && !debug){
        Collections.shuffle (sorpresas);                   //Baraja el mazo de sorpresas
        usadas = 0;
        barajada = true;
      }

      usadas++;
      ultimaSorpresa = sorpresas.get(0);
      sorpresas.remove(0);
      
      //Creo que falta esto   (...se quita la primera carta, se añade al final de la misma,...)
      sorpresas.add(ultimaSorpresa);

      return ultimaSorpresa;
    }

    void inhabilitarCartaEspecial (Sorpresa sorpresa){ //E: habrá que comprobar si es una cartaEspecial ????
      Boolean found = false;

      for (int i=0; i < sorpresas.size() && !found; i++)
          
        if (sorpresa == sorpresas.get(i)){                // Si está en el mazo:
          cartasEspeciales.add(sorpresa);                     // Se añade a cartasEspeciales
          sorpresas.remove(i);                                // Se elimina del mazo
          
          //Diario diario = diario.getInstance();    
          //diario.ocurreEvento("Se ha inhabilitado una carta especial");
          //diario.leerEvento();
          Diario.getInstance().ocurreEvento ("Se ha inhabilitado una carta especial");
          
          found = true;
        }
    }

    void habilitarCartaEspecial (Sorpresa sorpresa){
      Boolean found = false;

      for (int i=0; i < cartasEspeciales.size() && !found; i++)
          
        if(sorpresa == cartasEspeciales.get(i)){          // Si está en cartas cartasEspeciales
            //Y: alMazo es un metodo de MazoSorpresas no Sorpresas jeje   (sorpresas.alMazo(sorpresa); )                         
            sorpresas.add(sorpresa);                            // Se añade al mazo de sorpresas
            cartasEspeciales.remove(i);                         // Se elimina de cartasEspeciales
          
            //diario.ocurreEvento("Se ha habilitado una carta especial");
            Diario.getInstance().ocurreEvento ("Se ha habilitado una carta especial");
        
            found = true;
        }
    }

}
