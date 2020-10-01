package civitas;
import java.util.ArrayList; // import the ArrayList class

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 */

public class Tablero {
    
    private int numCasillaCarcel;                 // Dónde se encuentra la cárcel
    private ArrayList<Casilla> casillas ;         // Contenedor casillas 
    private int porSalida;                        // Veces que se pasa por salida
    private boolean tieneJuez;                    // Determina si el tablero tiene juez
    
    Tablero (int indice){
      if (indice >= 1)
        numCasillaCarcel = indice;
      else
        numCasillaCarcel = 1;
      
      casillas = new ArrayList<> ();
      
      Casilla casillaSalida = new Casilla ("Salida");   // i think so? :_D
      casillas.add (casillaSalida);

      //for (int i=0 ; i < nCasillas ; i++)       // Inicializa el array a vacío
      //casillas[i]="";

      porSalida=0;
      tieneJuez=false;
    }

    private boolean correcto () {
        // Y: Lo pongo en comment por el tema de intentar poner siempre un solo return :)
        //if((casillas.lenght > numCasillaCarcel) && tieneJuez)
        //  return true;
        //else
        //  return false;
        
        boolean correct = false;
        
        if( (casillas.size() > numCasillaCarcel) && tieneJuez)
            correct = true;
         
        return correct;
    }

    private boolean correcto (int numCasilla) {
       
      //if (correcto() && (numCasilla < casillas.lenght - 1))
        //return true;
      //else
        //return false;
        
        boolean correct = false;
        
        if (correcto() && (numCasilla < casillas.size() - 1))
            correct = true;
        
        return correct;
    }

    int getCarcel (){
      return numCasillaCarcel;
    }

    int getPorSalida (){
        
      //if (porSalida > 0)
      //  porSalida--;
      //  return (porSalida+1); // probar
      //else
      //  return porSalida;
        
      // Y: Mas corto:
        int devuelve = porSalida;
        if (porSalida > 0)
            porSalida --;
        return devuelve;
    }

    void añadeCasilla (Casilla casilla){ 
      // Y: 
      // Casilla es una clase no un array de strings, se tiene que cambiar esto creo 
//        if (casillas.size() == numCasillaCarcel)
//        casillas[casillas.size()-1] = "Cárcel"; //CARCEL ES CASILLA TIPO DESCANSO
//        casillas[...]= casilla;
//      if (casillas.size() == numCasillaCarcel)
//        casillas[casillas.lenght-1] = "Cárcel"; //CARCEL ES CASILLA TIPO DESCANSO

        // Y: otra opcion para hacer esto??
        añadeCarcel ();
        casillas.add (casilla);
        añadeCarcel ();
    }
    
    //Metodo auxiliar privado para no repetir código ????? (arriba se tendria que poner 2 veces)
    private void añadeCarcel () {
        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla ("Cárcel");
            casillas.add (carcel);
        }
    }

    void añadeJuez(){
      if (!tieneJuez) {
        Casilla casillaJuez = new Casilla ("Juez"); 
        añadeCasilla (casillaJuez);
        tieneJuez=true;
      }
    }

    Casilla getCasilla (int numCasilla){
      //if (numCasilla>0 && correcto())
      
      //Y:
      if (correcto(numCasilla))
        // return casillas[numCasilla]; esto me da error ????
        return casillas.get(numCasilla);
      
      else
        return null; 
    }

    int nuevaPosicion (int actual, int tirada){ 
      int posicion = -1;
      
      if (correcto()) {
          posicion = (actual + tirada) % casillas.size();
          
          if (posicion != actual + tirada)         
              porSalida ++;
      }
      
      return posicion;
      
      //if (!correcto())
      //  return -1;
      //else 
    }

    int calcularTirada (int origen, int destino){
      int result = destino-origen;

      if (result<0)
        result += casillas.size();
        //actualizar porSalida??? 
        //Y: no haria falta creo, se supone que el metodo solo calcula la tirada, no mueve las fichas (?)
      
      return result;
    }

}
