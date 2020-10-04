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
        boolean correct = false;

        if( (casillas.size() > numCasillaCarcel) && tieneJuez)
            correct = true;

        return correct;
    }

    private boolean correcto (int numCasilla) {
        boolean correct = false;

        if (correcto() && (numCasilla < casillas.size() - 1))
            correct = true;

        return correct;
    }

    int getCarcel (){
      return numCasillaCarcel;
    }

    int getPorSalida (){
        int devuelve = porSalida;
        if (porSalida > 0)
            porSalida --;
        return devuelve;
    }

    void añadeCasilla (Casilla casilla){
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
      if (correcto(numCasilla))
        // return casillas[numCasilla]; esto me da error ???? E: no tendrá operador de indexación
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
    }

    int calcularTirada (int origen, int destino){
      int result = destino-origen;

      if (result<0)
        result += casillas.size();
      return result;
    }

}
