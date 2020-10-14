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


    /**
      * @brief Constructor de la clase trablero
      * @param indice de la casilla en la que se encuentra la cárcel
      */
    Tablero (int indice){
      if (indice >= 1)
        numCasillaCarcel = indice;
      else
        numCasillaCarcel = 1;

      casillas = new ArrayList<> ();
      casillas.add (new Casilla ("Salida"));

      porSalida=0;
      tieneJuez=false;
    }

    
    /**
    * @brief Comprueba si el tablero es correcto
    * @return @retval true si el tablero es correcto, @retval false si no lo es
    */
    private boolean correcto () {
        boolean correct = false;

        if( (casillas.size() > numCasillaCarcel) && tieneJuez)
            correct = true;

        return correct;
    }

    
    /** Comprueba si el tablero es correcto y si su parametro es un indice valido
    * @param numCasilla índice para acceder a elementos de casillas
    * @return @retval true si el tablero e indice son correctos, @retval false si no es el caso
    */
    private boolean correcto (int numCasilla) {
        boolean correct = false;

        if (correcto() && (numCasilla < casillas.size() - 1))
            correct = true;

        return correct;
    }

    
    /** Consultor del atributo numCasillaCarcel.
    * @return la casilla en la que se encuentra la cárcel
    */
    int getCarcel (){
      return numCasillaCarcel;
    }

    
    /** Consultor del atributo porSalida.
    * @return número de veces que se ha pasado por la casilla de salida
    */
    int getPorSalida (){
        int devuelve = porSalida;
        if (porSalida > 0)
            porSalida --;
        return devuelve;
    }

    
    /** Dada una casilla, la añade al tablero
    * @param casilla objeto de la clase casilla
    */
    void añadeCasilla (Casilla casilla){
        añadeCarcel ();
        casillas.add (casilla);
        añadeCarcel ();
    }

    
    /** Añade al tablero la casilla de cárcel
    * @note Método auxiliar propio para no repetir código
    */
    private void añadeCarcel () {
        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla ("Cárcel");
            casillas.add (carcel);
        }
    }

    
    /** Añade al tablero una casilla de juez y actualiza el atributo tieneJuez
    */
    void añadeJuez(){
      if (!tieneJuez) {
        Casilla casillaJuez = new Casilla ("Juez");
        añadeCasilla (casillaJuez);
        tieneJuez=true;
      }
    }

    
    /** Consultor con parámetro del array casillas
    * @param numCasilla índice de una casilla
    */
    Casilla getCasilla (int numCasilla){
      if (correcto(numCasilla))
        return casillas.get(numCasilla);
      else
        return null;
    }

    
    /** Calcula la nueva posicion en el tablero
    * @param actual casilla actual
    * @param tirada número de casillas avanzadas
    * @return @retval posicion La casilla final, @retval -1 si el tablero no es correcto
    */
    int nuevaPosicion (int actual, int tirada){
      int posicion = -1;

      if (correcto()) {
          posicion = (actual + tirada) % casillas.size();

          if (posicion != actual + tirada)
              porSalida ++;
      }

      return posicion;
    }

    
    /** Calcula lo que debería salir en el dado para ir desde el origen al destino
    * @param origen Posición inicial
    * @param destino Posición final
    * @return devuelve la tirada necesaria para ir de origen a destino.
    */
    int calcularTirada (int origen, int destino){
      int result = destino-origen;

      if (result<0)
        result += casillas.size();
      return result;
    }
}
