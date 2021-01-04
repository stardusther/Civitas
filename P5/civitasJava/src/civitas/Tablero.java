/**
 * @file Tablero.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/

package civitas;
import java.util.ArrayList;                     // import the ArrayList class

/**
 * @class Tablero 
 * @brief Representa el tablero de juego imponiendo las restricciones existentes
 */

public class Tablero {

    private int numCasillaCarcel;                 // Dónde se encuentra la cárcel
    private ArrayList<Casilla> casillas ;         // Contenedor casillas
    private int porSalida;                        // Veces que se pasa por salida
    private boolean tieneJuez;                    // Determina si el tablero tiene juez


    /** Constructor de la clase trablero. */
    Tablero (int indice){
      if (indice >= 1)
        numCasillaCarcel = indice;
      else
        numCasillaCarcel = 1;

      casillas = new ArrayList<> ();
      casillas.add (new Casilla ("Salida"));        // La casilla es de tipo descanso

      porSalida=0;
      tieneJuez=false;
    }
    
   /** Comprueba si el tablero es correcto. */
    private boolean correcto () {
        boolean correct = false;

        if( (casillas.size() > numCasillaCarcel) && tieneJuez)
            correct = true;

        return correct;
    }
    
    /** Comprueba si el tablero es correcto y si su parametro es un indice valido. */
    private boolean correcto (int numCasilla) {
        boolean correct = false;

        if (correcto() && numCasilla >= 0 && numCasilla<casillas.size())
            correct = true;

        return correct;
    }
    
    /** Consultor del atributo numCasillaCarcel. */
    int getCarcel (){
      return numCasillaCarcel;
    }
    
    /** Consultor del atributo porSalida. */
    int getPorSalida (){
        int devuelve = porSalida;
        if (porSalida > 0)
            porSalida --;
        return devuelve;
    }
    
    /** Dada una casilla, la añade al tablero. */
    void añadeCasilla (Casilla casilla){
        añadeCarcel ();
        casillas.add (casilla);
        añadeCarcel ();
    }
    
    /** Añade al tablero la casilla de cárcel. */
    private void añadeCarcel () {
        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla ("Cárcel");
            casillas.add (carcel);
        }
    }
    
    /** Añade casilla de juez y actualiza el atributo tieneJuez. */
    void añadeJuez(){
      if (!tieneJuez) {
        CasillaJuez casillaJuez = new CasillaJuez ("Juez", numCasillaCarcel);
        añadeCasilla (casillaJuez);
        tieneJuez=true;
      }
    }
    
    /** Consultor con parámetro del array casillas. */
    Casilla getCasilla (int numCasilla){
      if (correcto(numCasilla))
        return casillas.get(numCasilla);
      else
        return null;
    }
    
    /** Calcula la nueva posicion en el tablero. 
    * @return @retval posicion La casilla final, @retval -1 si el tablero no es correcto */
    int nuevaPosicion (int actual, int tirada){
      int posicion = -1;

      if (correcto()) {
          posicion = (actual + tirada) % casillas.size();

          if (posicion != actual + tirada)
              porSalida ++;
      }

      return posicion;
    }
    
    /** Calcula lo que debería salir en el dado para ir desde el origen al destino. 
    * @return devuelve la tirada necesaria para ir de origen a destino. */
    int calcularTirada (int origen, int destino){
      int result = destino-origen;

      if (result<0)
        result += casillas.size();
      return result;
    }
    
    @Override
    public String toString() {
        String str = " >> Tablero: num. casilla carcel: " + numCasillaCarcel + 
                     ". Se ha pasado por salida " + porSalida + " veces. " + 
                     "\n Tiene " + casillas.size() + " casillas. ";
        if (tieneJuez)
            str += "Tiene juez. ";
        
        for (int i = 0 ; i<casillas.size() ; i++)
            str += "\n  " + casillas.get(i).toString();
        
        return str;
    }
}