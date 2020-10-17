package civitas;

/**
 * @file Jugador.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/
import java.util.Collection;    //Para hacer el isEmpty()
import java.util.ArrayList; 

public class Jugador implements Comparable<Jugador>{

    protected static int CasasMax = 4;                  // Número de casas máximo que se puede edificar por casilla
    protected static int CasasPorHotel = 4;             // Número de casas que se deben tener para poder intercambiarse por un hotel
    protected boolean encarcelado;                      // Determina si el jugador está encarcelado o no
    protected int HotelesMax = 4;                       // Número de hoteles máximo que se puede edificar por casilla
    protected static float PasoPorSalida = 1000;        // Precio a cobrar por pasar por la casilla de salida
    protected static float PrecioLibertad = 200;        // Precio a pagar por salir de la cárcel

    private String nombre;                              // Nombre del Jugador
    private int numCasillaActual;                       // Número de la casilla en la que se encuentra el Jugador
    private Boolean puedeComprar;                       // Determina si el jugador está en condiciones de comprar una propiedad
    private float saldo;                                // Saldo del jugador
    private static float SaldoInicial = 7500;           // Saldo con el que comienzan todos los jugadores
    private Sorpresa salvoconducto;                     // Almacena el salvoconducto para salir de la cárcel
    private ArrayList<TituloPropiedad> propiedades;     // Conjunto de propiedades del jugador

    /** Constructor básico de la clase Jugador.
     * @param nombre El nombre del nuevo Jugador
     */
    Jugador (String _nombre){
      nombre = _nombre;
      saldo = SaldoInicial;
      puedeComprar = true;
      encarcelado = false;
      numCasillaActual = 0;

      propiedades = new ArrayList<TituloPropiedad> ();
    }

    /** Constructor de copia de la clase Jugador.
     * @param otro El otro jugador que queremos copiar
     */
    protected Jugador (Jugador otro){
      nombre = otro.getNombre();
      saldo = otro.getSaldo();
      encarcelado = otro.isEncarcelado();
      puedeComprar = otro.getPuedeComprar();
      numCasillaActual = otro.getNumCasillaActual();

      propiedades = new ArrayList<TituloPropiedad> (otro.getPropiedades()); //E: (ArrayList) otro.getPropiedades().clone()
    }

    /** Consultor privado del atributo CasasMax.
     * @return CasasMax Número máximo de casas que se puede edificar por casilla
     */
    private int getCasasMax (){
      return CasasMax;
    }

    /** Consultor del atributo CasasPorHotel.
     * @return CasasPorHotel Número de casas que se deben tener para poder intercambiarse por un hotel
     */
    int getCasasPorHotel (){
      return CasasPorHotel;
    }

    /** Consultor privado del atributo HotelesMax.
     * @return HotelesMax Número máximo de hoteles que se puede edificar por casilla
     */
    private int getHotelesMax () {
      return HotelesMax;
    }

    /** Consultor protegido del atributo nombre.
     * @return nombre El nombre del Jugador
     */
    protected String getNombre (){
      return nombre;
    }

    /** Consultor del atributo numCasillaActual.
     * @return numCasillaActual Número de la casilla en la que se encuentra el Jugador
     */
    int getNumCasillaActual (){
      return numCasillaActual;
    }

    /** Consultor privado del atributo PrecioLibertad.
     * @return PrecioLibertad Precio a pagar por salir de la cárcel
     */
    private float getPrecioLibertad (){
      return PrecioLibertad;
    }

    /** Consultor privado del atributo PasoPorSalida.
     * @return PasoPorSalida Precio a cobrar por pasar por la casilla de salida
     */
    private float getPremioPasoSalida (){
      return PasoPorSalida;
    }

    /** Consultor protegido del atributo propiedades.
     * @return propiedades Array que contiene el conjunto de propiedades del jugador
     */
    protected ArrayList <TituloPropiedad> getPropiedades(){
      return propiedades;           //Y: era el tipo que devolvia, ahora si se puede devolver un array :)
    }

    /** Conmuta la cantidad total de edificaciones en las propiedades del jugador.
     * @return casasHoteles Número total de edificaciones que existen en el array propiedades
     * @warning no sé si está correcto Y: po yo creo que si
     */
    int cantidadCasasHoteles(){ 
      TituloPropiedad propiedad;
      int casasHoteles = 0;

      for (int i=0; i < propiedades.size(); i++){
        propiedad = propiedades.get(i);
        casasHoteles += propiedad.getNumCasas() + propiedad.getNumHoteles();
      }

      return casasHoteles;
    }

    /** Consultor del atributo puedeComprar.
     * @return @retval true si el jugador puede comprar una propiedad o @retval false en caso contrario
     */
    boolean getPuedeComprar(){
      return puedeComprar;
    }

    /** Consultor protegido del atributo saldo.
     * @return saldo Cantidad de dinero del jugador
     */
    protected float getSaldo(){
      return saldo;
    }

    /** Consultor público del atributo encarcelado.
     * @return @retval true si el jugador está encarcelado o @retval false en caso contrario
     */
    public boolean isEncarcelado(){
      return encarcelado;
    }

    /** Determina si un jugador debe ser encarcelado y, en caso afirmativo, se comunica en el diario.
     * @return @retval true si el jugador debe ser encarcelado o @retval false en caso contrario
     */
    protected boolean debeSerEncarcelado (){
      boolean carcel = false;

      if (!isEncarcelado())
        if (!tieneSalvoconducto())
          carcel = true;
        else {
          perderSalvoconducto();
          Diario.getInstance().ocurreEvento("El jugador se ha librado de la cárcel por tener un salvoconducto");
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
        Diario.getInstance().ocurreEvento("El jugador ha sido encarcelado");
      }

      return isEncarcelado();
    }

    /** Guarda la referencia al parámetro en el atributo salvovconducto si el jugador no está en la cárcel.
     * @return @retval true si el jugador ha obtenido el salvoconducto y @retval false si no es el caso
     */
    boolean obtenerSalvoconducto (Sorpresa s){
      boolean obtiene = true;

      if (isEncarcelado())
        obtiene = false;
      else
        salvoconducto = s;

      return obtiene;
    }

    /** Elimina la referencia al salvoconducto porque ha sido usado.
     *
     */
    private void perderSalvoconducto(){
      salvoconducto.usada();
      salvoconducto = null;
    }

    /** Determina si el jugador tiene salvoconducto.
     * @return @retval true si el jugador tiene salvoconducto y @retval false si no es el caso
     */
    boolean tieneSalvoconducto (){
      boolean tiene = true;

      if (salvoconducto == null)
        tiene = false;

      return tiene;
    }

    /** Determina si el jugador puede comprar una propiedad.
     * @return @retval true si el jugador puede comprar la casilla y @retval false si no es el caso
     */
    boolean puedeComprarCasilla(){
      if(isEncarcelado())
        puedeComprar = false;
      else
        puedeComprar = true;

      return getPuedeComprar();
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
      Diario.getInstance().ocurreEvento ("Se ha incrementado el saldo");

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
      else{
        numCasillaActual = numCasilla;
        puedeComprar = false;
        Diario.getInstance().ocurreEvento ("Se ha movido al jugador");
      }

      return mover;
    }

    /** Determina si el jugador tiene el saldo suficiente para pagar.
     * @param precio Dinero a pagar
     * @return @retval true si se lo puede permitir o @false en caso contrario
     */
    private boolean puedoGastar (float precio){
      boolean puedo = true;

      if (isEncarcelado())
        puedo = false;
      else if (getSaldo() < precio)
        puedo = false;

      return puedo;
    }

    /** Vende una propiedad de un jugador.
     * @param ip Número de casilla de la propiedad que se va a vender
     * @return @retval true si se ha realizado la acción con éxito o @false en caso contrario
     */
    boolean vender (int ip){
      boolean puedo = false;

      if(!isEncarcelado() && existeLaPropiedad(propiedades.get(ip)))                              // Si no está encarcelado y existe la propiedad
        if (propiedades[ip].vender(*this)){                                                       // Si se ha podido vender la propiedad
          propiedades.remove(ip);
          Diario.getInstance().ocurreEvento("Se ha vendido la propiedad de la casilla " + ip);
          puedo = true;
        }                                                                                         // En cualquier otro caso se devuelve false

        return puedo;
    }

    /** Determina si el jugador tiene propiedades.
     * @return @retval true si el jugador posee propiedades o @false en caso contrario
     */
    boolean tieneAlgoQueGestionar (){
      boolean tiene = true;

      if (propiedades.isEmpty())
        tiene = false;

      return tiene;
    }

    /** Determina si el jugador puede salir de la cárcel pagando.
     * @return @retval true si el jugador puede pagar el precio por su libertad o @false en caso contrario
     */
    private boolean puedeSalirCarcelPagando(){
      boolean salir = false;

      if(getSaldo() >= getPrecioLibertad())
        salir = true;

        return salir;
    }

    /** Libera al jugador de la cárcel tras pagar.
     * @return @retval true si el jugador ha sido liberado o @false en caso contrario
     */
    boolean salirCarcelPagando (){
      boolean salir = false;

      if(isEncarcelado() && puedeSalirCarcelPagando()){
        paga(getPrecioLibertad());
        encarcelado = false;

        Diario.getInstance().ocurreEvento("El jugador ha pagado para salir de la cárcel");
        salir = true;
      }

      return salir;
    }

    /** Libera al jugador de la cárcel tras tirar.
     * @return @retval true si el jugador ha sido liberado o @false en caso contrario
     */
    boolean salirCarcelTirando (){
      if(Dado.getInstance().salgoDeLaCarcel()){
        encarcelado = false;
        Diario.getInstance().ocurreEvento("El jugador ha tirado y ha salido de la cárcel");
      }

        return isEncarcelado();
    }

    /** Modifica el saldo por haber pasado por la casilla de salida.
     * @return @retval true si se ha realizado la acción con éxito o @false en caso contrario
     */
    boolean pasaPorSalida (){
      modificarSaldo(getPremioPasoSalida());
      //return modificarSaldo(...) ??
    }

    /**
     * @warning por implementar
     */
    boolean cancelarHipoteca(int ip){ //SIGUIENTE PRÁCTICA------------------------------------------
        return true;    //(para que deje compilar :D )
    }

    /**
     * @warning por implementar
     */
    boolean comprar (TituloPropiedad titulo){ //SIGUIENTE PRÁCTICA
        return true;    //compilar
    }

    /**
     * @warning por implementar
     */
    boolean construirCasa (int ip){ //SIGUIENTE PRÁCTICA
        return true;    //compilar
    }

    /**
     * @warning por implementar
     */
    boolean construirHotel (int ip){  //SIGUIENTE PRÁCTICA
        return true;    //compilar
    }

    /**
     * @warning por implementar
     */
    boolean hipotecar (int ip){ //SIGUIENTE PRÁCTICA
        return true;    //compilar
    }

    /** Determina si el jugador está en bancarrota.
     * @return @retval true si se el saldo del jugador ha llegado a 0 o inferior o @false en caso contrario
     */
    boolean enBancarrota (){
      boolean cero = false;

      if (getSaldo() >= 0)
        cero = true;

      return cero;
    }

    /** Determina si una propiedad dada existe.
     * @param ip Número de la casilla en la que se encuentra la propiedad
     * @return @retval true si se la propiedad existe o @false en caso contrario
     */
    private boolean existeLaPropiedad (int ip){
      boolean existe = false;

      if(propiedades.contains(propiedades.get(ip)))  //E: esto está mal. No entiendo el método
        existe = true;
      return existe;
    }

    /** Determina si el jugador puede edificar una casa en una determinada propiedad
     * @param propiedad La propiedad en la que queremos edificar la casa
     */
    private boolean puedoEdificarCasa (TituloPropiedad propiedad){
      boolean puede = false;

      if (propiedades.contains("propiedad") && propiedad.getNumCasas() < getCasasMax() && getPuedeComprar() && puedoGastar(propiedad.getPrecioEdificar()))  // Si el jugador posee la propiedad, el número de casas edificadas es menor a 4,
                                                                                                                                                            // el jugador puede comprar y puede gastar lo que cuesta la ediificación en esa casilla
        puede = true;

        return puede;
    }

    /** Determina si el jugador puede edificar un hotel en una determinada propiedad
     * @param propiedad La propiedad en la que queremos edificar el hotel
     */
    private boolean puedoEdificarHotel (TituloPropiedad propiedad){ //IMPLEMENTAR
      boolean puede = false;

      if (propiedades.contains("propiedad") && propiedad.getNumHoteles() < getHotelesMax() && (propiedad.getNumCasas == getCasasPorHotel) && getPuedeComprar() && puedoGastar(propiedad.getPrecioEdificar()))  // Si el jugador posee la propiedad, el número de hoteles edificados es menor a 4,
                                                                                                                                                                                                              // hay 4 casas edificadas, el jugador puede comprar y puede gastar lo que cuesta la ediificación en esa casilla
        puede = true;

        return puede;
    }

    /** Imprime por pantalla las características del jugador
     * @warning por implementar
     */
    @Override
    public String toString(){
      String s;

      s = "\nEl jugador de nombre " + getNombre() + " tiene un saldo de " + getSaldo() + "€, tiene " + propiedades.size() +" propiedades y un total de " + cantidadCasasHoteles() + " edificaciones";

      return s;
    }

    /** Compara los saldos de dos Jugadores.
     * @param otro El jugador con el que se compara
     * @return diferencia Diferencia entre ambos saldos
     * @note Si es positivo, el saldo de J1 es superior al saldo del otro jugador y negativo en caso contrario.
     */
    public int compareTo (Jugador otro){
      return getSaldo() - otro.getSaldo();
    }

}
