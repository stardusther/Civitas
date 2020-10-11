package civitas;
import java.util.ArrayList;


/**
 * @file Diario.java
 * @class Diario
 * @brief Mantiene constancia de los eventos ocurridos en el juego
 * @note Clase ya implementada.
 */
public class Diario {
  static final private Diario instance = new Diario();

  private ArrayList<String> eventos;
  
  /** Metodo de calse para obtener la instancia
   * @note Para acceder a la única instancia del diario: Diario.getInstance()
   */
  static public Diario getInstance() {
    return instance;
  }

  private Diario () {
    eventos = new ArrayList<>();
  }

  /** Añade un evento al diario
   * @param e nombre del evento a añadir
   */
  void ocurreEvento (String e) {
    eventos.add (e);
  }

  public boolean eventosPendientes () {
    return !eventos.isEmpty();
  }

  public String leerEvento () {
    String salida = "";
    if (!eventos.isEmpty()) {
      salida = eventos.remove(0);
    }
    return salida;
  }
}
