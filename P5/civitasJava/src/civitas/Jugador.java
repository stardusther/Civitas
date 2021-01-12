package civitas;

import GUI.Dado;

/**
 * @file Jugador.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 * @class Jugador
 * @brief Representa a cada uno de los jugadores del juego.
*/
import java.util.Collection;    
import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{

    protected static int CasasMax = 4;                  // Número de casas máximo que se puede edificar por casilla
    protected static int CasasPorHotel = 4;             // Número de casas que se deben tener para poder intercambiarse por un hotel
    protected boolean encarcelado;                      // Determina si el jugador está encarcelado o no
    protected static int HotelesMax = 4;                // Número de hoteles máximo que se puede edificar por casilla
    protected static float PasoPorSalida = 1000;        // Precio a cobrar por pasar por la casilla de salida
    private static float PrecioLibertad = 200;          // Precio a pagar por salir de la cárcel

    private String nombre;                              // Nombre del Jugador
    private int numCasillaActual;                       // Número de la casilla en la que se encuentra el Jugador
    private Boolean puedeComprar;                       // Determina si el jugador está en condiciones de comprar una propiedad
    private float saldo;                                // Saldo del jugador
    private static float SaldoInicial = 7500;           // Saldo con el que comienzan todos los jugadores
    private SorpresaSalirCarcel salvoconducto;          // Almacena el salvoconducto para salir de la cárcel
    private ArrayList<TituloPropiedad> propiedades;     // Conjunto de propiedades del jugador
    
    
    /** Constructor básico de la clase Jugador. */
    Jugador (String _nombre){
      nombre = _nombre;
      saldo = SaldoInicial;
      puedeComprar = true;
      encarcelado = false;
      numCasillaActual = 0;
      salvoconducto = null;

      propiedades = new ArrayList<TituloPropiedad> ();
    }


    /** Constructor de copia de la clase Jugador. */
    protected Jugador (Jugador otro){
      nombre = otro.getNombre();
      saldo = otro.getSaldo();
      encarcelado = otro.isEncarcelado();
      puedeComprar = otro.getPuedeComprar();
      numCasillaActual = otro.getNumCasillaActual();

      propiedades = new ArrayList<TituloPropiedad> (otro.getPropiedades()); //E: (ArrayList) otro.getPropiedades().clone()
    }


    /** Determina si un jugador debe ser encarcelado y, en caso afirmativo, se comunica en el diario. */
    protected boolean debeSerEncarcelado (){
      boolean carcel = false;

      if (!isEncarcelado())
        if (!tieneSalvoconducto())
          carcel = true;
        else {
          perderSalvoconducto();
          Diario.getInstance().ocurreEvento("El jugador " + nombre +
                        " se ha librado de la cárcel por tener un salvoconducto");
        }

      return carcel;
    }


    /** Encarcela al jugador que llame a la función si es necesario e informa al diario.
     * @return @retval true si el jugador ha sido encarcelado y @retval false si no es el caso
     */
    boolean encarcelar (int numCasillaCarcel){
      if (debeSerEncarcelado()){
        moverACasilla(numCasillaCarcel);
        encarcelado = true;
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " ha sido encarcelado");
      }
      return isEncarcelado();
    }


    /** Guarda la referencia al parámetro en el atributo salvoconducto si el jugador no está en la cárcel.
     * @return @retval true si el jugador ha obtenido el salvoconducto y @retval false si no es el caso
     */
    boolean obtenerSalvoconducto (SorpresaSalirCarcel s){
      boolean obtiene = !isEncarcelado();
      if (obtiene)
          salvoconducto = s;
      return obtiene;

    }


    /** Elimina la referencia al salvoconducto porque ha sido usado. */
    protected void perderSalvoconducto(){
      salvoconducto.usada();
      salvoconducto = null;
    }


    /** Realiza la acción de pagar en el juego.
     * @param cantidad Dinero a pagar
     * @return @retval true si el jugador ha pagado y @retval false si no es el caso
     */
    boolean paga (float cantidad){
      return modificarSaldo(cantidad * -1);
    }


    /** Realiza la acción de pagar por sacar una sopresa que te obliga a pagar un impuesto.
     * @param cantidad Dinero a pagar
     * @return @retval true si el jugador ha pagado y @retval false si no es el caso
     */
    boolean pagaImpuesto (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return paga(cantidad);
    }


    /** Realiza la acción de pagar por caer en una propiedad de otro jugador.
     * @param cantidad Dinero a pagar
     * @return @retval true si el jugador ha pagado y @retval false si no es el caso
     */
    boolean pagaAlquiler (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return paga(cantidad);
    }


    /** Incrementa el saldo en el juego.
     * @param cantidad Dinero a pagar
     * @return @retval true si el jugador ha recibido el dinero y @retval false si no es el caso
     */
    boolean recibe (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return modificarSaldo(cantidad);
    }


    /** Modifica el saldo en el juego.
     * @param cantidad Dinero que incrementa/disminuye el saldo
     * @return @retval true siempre
     */
    boolean modificarSaldo (float cantidad){
      saldo += cantidad;
      Diario.getInstance().ocurreEvento ("Se ha modificado el saldo de " + nombre + " (" + 
                                          cantidad + "€, de " + (saldo-cantidad) + "€ a " + saldo + "€)");
      return true;
    }


    /** Modifica la posición de un jugador.
     * @param numCasilla Casilla a la que se va a mover al jugador
     * @return @retval true si se ha realizado la acción con éxito o @false en caso contrario
     */
    boolean moverACasilla (int numCasilla){
      boolean mover = true;

      if (isEncarcelado())
        mover = false;
      else {
        numCasillaActual = numCasilla;
        puedeComprar = false;
        Diario.getInstance().ocurreEvento ("Se ha movido al jugador " + nombre + " a la casilla " + numCasilla);
      }

      return mover;
    }


    /** Determina si el jugador tiene el saldo suficiente para pagar.
     * @return @retval true si se lo puede permitir o @false en caso contrario */
    protected boolean puedoGastar (float precio){
      return ( !isEncarcelado() && getSaldo() >= precio);
    }


    /** Vende una propiedad de un jugador.
     * @param ip Número de casilla de la propiedad que se va a vender
     * @return @retval true si se ha realizado la acción con éxito o @false en caso contrario. */
    boolean vender (int ip){
      boolean puedo = false;

      if (!isEncarcelado() && existeLaPropiedad(ip))                            // Si no está encarcelado y existe la propiedad
        if (propiedades.get(ip).vender(this)) {                                 // Si se ha podido vender la propiedad
          propiedades.remove(ip);
          Diario.getInstance().ocurreEvento("Se ha vendido la propiedad de la casilla " + ip);
          puedo = true;
        }                                                                       // En cualquier otro caso se devuelve false

        return puedo;
    }


    /** Determina si el jugador tiene propiedades. */
    boolean tieneAlgoQueGestionar (){
        return ( !propiedades.isEmpty() );
    }


    /** Determina si el jugador puede salir de la cárcel pagando.
     * @return @retval true si el jugador puede pagar el precio por su libertad o @false en caso contrario
     */
    private boolean puedeSalirCarcelPagando(){
        return (getSaldo() >= getPrecioLibertad());
    }


    /** Libera al jugador de la cárcel tras pagar.
     * @return @retval true si el jugador ha sido liberado o @false en caso contrario
     */
    boolean salirCarcelPagando (){
      boolean salir = false;

      if(isEncarcelado() && puedeSalirCarcelPagando()){
        paga(getPrecioLibertad());
        encarcelado = false;

        Diario.getInstance().ocurreEvento("El jugador " + nombre + " ha pagado para salir de la cárcel");
        salir = true;
      }

      return salir;
    }


    /** Libera al jugador de la cárcel tras tirar (si se cumplen las condiciones). */
    boolean salirCarcelTirando (){
      if(Dado.getInstance().salgoDeLaCarcel()){
        encarcelado = false;
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " ha tirado y ha salido de la cárcel");
      }

        return isEncarcelado();
    }


    /** Modifica el saldo por haber pasado por la casilla de salida.
     * @return @retval true si se ha realizado la acción con éxito o @false en caso contrario
     */
    boolean pasaPorSalida (){
      return modificarSaldo(getPremioPasoSalida());
    }



    // ---------------------------- Consultores ----------------------------- //


    /** Consultor protegido del atributo CasasMax. */
    protected int getCasasMax (){
      return CasasMax;
    }


    /** Consultor del atributo CasasPorHotel.
     * @return CasasPorHotel Número de casas que se deben tener para poder intercambiarse por un hotel
     */
    int getCasasPorHotel (){
      return CasasPorHotel;
    }


    /** Consultor protegido del atributo HotelesMax. */
    protected int getHotelesMax () {
      return HotelesMax;
    }


    /** Consultor protegido del atributo nombre. (public para clase JugadorPanel) */
    public String getNombre (){
      return nombre;
    }


    /** Consultor del atributo numCasillaActual.  */
    protected int getNumCasillaActual (){
      return numCasillaActual;
    }


    /** Consultor privado del atributo PrecioLibertad.  */
    protected float getPrecioLibertad (){
      return PrecioLibertad;
    }


    /** Consultor privado del atributo PasoPorSalida.  */
    private float getPremioPasoSalida (){
      return PasoPorSalida;
    }


    /** Consultor protegido del atributo propiedades.
     * @return propiedades Array que contiene el conjunto de propiedades del jugador
     */
    //protected ArrayList <TituloPropiedad> getPropiedades(){
    // Cambiamos visibilidad método para acceder a las propiedades desde VistaTextual
    // y poder mostrar el menu correctamente (para gestionar)
    public ArrayList <TituloPropiedad> getPropiedades(){
      return propiedades;           
    }


    /** Conmuta la cantidad total de edificaciones en las propiedades del jugador. */
    int cantidadCasasHoteles(){
      int casasHoteles = 0;

      for (int i=0; i < propiedades.size(); i++)
        casasHoteles += propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles();

      return casasHoteles;
    }


    /** Consultor del atributo puedeComprar. */
    protected boolean getPuedeComprar(){
      return puedeComprar;
    }


    /** Consultor protegido del atributo saldo. (public para JugadorPanel)  */
    public float getSaldo(){
      return saldo;
    }


    /** Consultor público del atributo encarcelado. */
    public boolean isEncarcelado(){
      return encarcelado;
    }

    /** Determina si el jugador tiene salvoconducto. */
    boolean tieneSalvoconducto (){
      return (salvoconducto != null);
    }

    /** Determina si el jugador puede comprar una propiedad. */
    boolean puedeComprarCasilla(){
      if(isEncarcelado())
        puedeComprar = false;
      else
        puedeComprar = true;

      return getPuedeComprar();
    }

    /** Cancela la hipoteca de un jugador si: no está encarcelado, la propiedad
     *  está hipotecada y puede pagarla. */
    boolean cancelarHipoteca(int ip){
        boolean result = false;

        if (!encarcelado && existeLaPropiedad(ip)) {  // opt 1 y 2 (equivalente a los 2 if)

            TituloPropiedad propiedad = propiedades.get(ip);            // 3
            float cantidad = propiedad.getImporteCancelarHipoteca();    // 4
            boolean puedoGastar = puedoGastar(cantidad);                // 5

            if (puedoGastar && propiedad.cancelarHipoteca(this)) {      // 6 y 7 (comprobar que el and funciona
                result = true;                                          // bien, si no tendría que ir en dos if)
                Diario.getInstance().ocurreEvento("El jugador " + nombre +
                            " cancela la hipoteca de la propiedad " + ip);
            } // else result = false; --> no hace falta, se declara false por defecto
        }
        return result;
    }

    /** Compra una propiedad si el jugador no está encarcelado, tiene permitido
     *  comprar, tiene el saldo suficiente, la propiedad se puede comprar.  */
    boolean comprar (TituloPropiedad titulo){
        boolean result = false;

        if (!encarcelado && puedeComprar) {
            float precio = titulo.getPrecioCompra();

            if (puedoGastar(precio)) {
                if (titulo.comprar(this)) {
                    result = true;
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre +
                                     " compra la propiedad " + titulo.getNombre());
                                     //" compra la propiedad " + titulo.toString());
                }
                puedeComprar = false;
            }
        }
        return result;
    }

    /** El jugador construye una casa si es posible. */
    boolean construirCasa (int ip){
        boolean result = false;
        boolean puedoEdificarCasa = false;
        
        if (!encarcelado && existeLaPropiedad (ip) && puedoEdificarCasa(propiedades.get(ip)) )
            result = propiedades.get(ip).construirCasa(this);
        
        return result;
    }

    /** Determina si el jugador puede construir el hotel y lo hace si es posible. */
    boolean construirHotel (int ip){
        boolean result = false;

        if (!encarcelado && existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = propiedades.get(ip);

            if (puedoEdificarHotel (propiedad)) {
                result = propiedad.construirHotel(this);
                int casasPorHotel = getCasasPorHotel();
                propiedad.derruirCasas(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador " + nombre +
                        "construye hotel en la propiedad " + ip);
            }
        }

        return result;
    }

    /** Hipoteca al jugador si es posible. */
    boolean hipotecar (int ip){
        boolean result = false;
        if (!encarcelado && existeLaPropiedad(ip)) {
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        return result;
    }

    /** Determina si el jugador está en bancarrota (saldo menor a 0). */
    boolean enBancarrota (){
      boolean cero = false;

      if (getSaldo() <= 0)
        cero = true;

      return cero;
    }

    /** Determina si una propiedad dada existe.
     * @param ip Número de la casilla en la que se encuentra la propiedad */
    private boolean existeLaPropiedad (int ip){
      boolean existe = false;
      if(propiedades.contains(propiedades.get(ip)))  //E: esto está mal. No entiendo el método
        existe = true;
      return existe;
    }

    /** Determina si el jugador puede edificar una casa en una determinada propiedad. */
    private boolean puedoEdificarCasa (TituloPropiedad propiedad){
        return (puedoGastar (propiedad.getPrecioEdificar()) && 
                propiedad.getNumCasas()<getCasasMax());
    }

    /** Determina si el jugador puede edificar un hotel en una determinada propiedad.
     */
    private boolean puedoEdificarHotel (TituloPropiedad propiedad){ //IMPLEMENTAR
        boolean puedoEdificarHotel = false;
        float precio = propiedad.getPrecioEdificar();

        if (puedoGastar(precio) && propiedad.getNumHoteles()<getHotelesMax() &&
            propiedad.getNumCasas()>=getCasasPorHotel())
                puedoEdificarHotel = true;

      return puedoEdificarHotel;

      // return ( puedoGastar(precio) && propiedad.getNumHoteles()<getHotelesMax() &&
      //          propiedad.getNumCasas()>=getCasasPorHotel() )

    }

    /** Imprime por pantalla las características del jugador. */
    @Override
    public String toString(){
      String s;

      s = " >> Jugador " + getNombre() + ". Saldo " + getSaldo() + "€. Propiedades: " + propiedades.size() +". Edificaciones:" + cantidadCasasHoteles();
      //s = "\n >> Jugador " + getNombre() + ". Saldo " + getSaldo() + "€. Propiedades: " + propiedades.size() +". Edificaciones:" + cantidadCasasHoteles();

      s += "\n    Casilla actual: " + numCasillaActual + ".";
      if (puedeComprar)
          s+= " Puede comprar.";

      if (encarcelado)
          s+= " Está encarcelado.";

      if ( salvoconducto != null)
          s+= " Tiene salvoconducto";

      return s;
    }

    /** Compara los saldos de dos Jugadores.
     * @return diferencia Diferencia entre ambos saldos
     * @note Si es positivo, el saldo de J1 es superior al saldo del otro jugador y negativo en caso contrario.
     */
    public int compareTo (Jugador otro){
      return (int)(getSaldo() - otro.getSaldo()) ;
    }
    
    
    
}