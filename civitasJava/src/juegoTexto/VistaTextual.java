package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  /** Constructor. */
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  /** Muestra estado del juego. */
  //void mostrarEstado(String estado) {
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
           
  /** Pausa el juego. */
  void pausa() {
    System.out.print ("\nPulsa una tecla");
    in.nextLine();
  }

  /** Lee un entero. */
  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  /** Muestra menú. */
  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  /** Preguna forma de salir de la carcel. */
  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
                        new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    int opcion = menu ("¿Comprar calle?",
                        new ArrayList<> (Arrays.asList("Si","No")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
    iGestion = menu ("\nIndique número de operación inmobiliaria: (0..5))",
                       new ArrayList<> (Arrays.asList("Vender","Hipotecar", "Cancelar hipoteca", 
                      "Constuir casa", "Construir hotel", "Terminar")));
    // Si gestion = terminar, no tiene sentido presentar menu
    if (iGestion != 5)
        iPropiedad =  menu (" Indique propiedad a la que desea aplicar la gestión:)",
                       new ArrayList<> (Arrays.asList("Calle 1","Calle 2")));  // Hay 2 calles --> max 2 propiedades
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    
  /** Muestra la siguiente operacion.  */
  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      System.out.println("\n *** Siguiente operacion: " + operacion + "\n");
  }

  /** Muestra los eventos del diario. */
  void mostrarEventos() {
      String evento = Diario.getInstance().leerEvento();
      while (evento != "") {
          System.out.println(evento);
          evento = Diario.getInstance().leerEvento();
      }
      //System.out.println("\n");
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
    }
  
  public void actualizarVista(){
      System.out.println(juegoModel.getJugadorActual().toString() + "\n >> " + 
              juegoModel.getCasillaActual().toString());
  } 
}
    
