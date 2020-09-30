
package civitas;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 */
public class Tablero {
    private int numCasillaCarcel; // dónde se encuentra la cárcel
    private ArrayList<Casilla> casillas[20]; //array nombres casillas //memoria dinámica???
    private int porSalida; //veces que se pasa la salida
    private Boolean tieneJuez; //tablero tiene casilla juez


    Tablero(int indice){
      if(indice >= 1)
        numCasillaCarcel=indice;
      else
        numCasillaCarcel = 1;

      for(int i=0; i<20; i++) //inicializa el array a vacío
        casillas[i]="";

      porSalida=0;
      tieneJuez=false;
    }

    private Boolean correcto(){
      if((casillas.lenght > numCasillaCarcel) && tieneJuez)
        return true;
      else
        return false;
    }

    private Boolean correcto(int numCasilla){
      if(correcto() && (numCasilla < casillas.lenght - 1))
        return true;
      else
        return false;
    }

    int getCarcel (){
      return numCasillaCarcel;
    }

    int getPorSalida (){
      if( porSalida > 0 )
        porSalida--;
        return (porSalida+1); // probar
      else
        return porSalida;
    }

    void añadeCasilla (Casilla casilla){ //?????????????????????????????? me no entender
      if (casillas.lenght == numCasillaCarcel)
        casillas[casillas.lenght-1] = "Cárcel"; //CARCEL ES CASILLA TIPO DESCANSO
        casillas[...]= casilla;
      if (casillas.lenght == numCasillaCarcel)
        casillas[casillas.lenght-1] = "Cárcel"; //CARCEL ES CASILLA TIPO DESCANSO

    }

    void añadeJuez(){
      if (!tieneJuez)
        añadeCasilla();//añadir casilla tieneJuez?
        tieneJuez=true;
    }

    Casilla getCasilla (int numCasilla){ //devuelve la casilla de la posición numCasilla
      if(numCasilla>0 && correcto())
        return casillas[numCasilla];
      else
        return null; //?????????????????
    }

    int nuevaPosicion (int actual, int tirada){
      if(!correcto())
        return -1;
      else

    }

    int calcularTirada (int origen, int destino){
      int result=destino-origen;
      if(result<0)
        result=result+casillas.lenght;
        //actualizar porSalida???
      return result;
    }

}












//--------------------------------------------
public class Casilla {
  private String nombre;

  Casilla(String s){
    nombre = s;
  }

  String getNombre(){
    return nombre;
  }
}

//--------------------------------------------()
