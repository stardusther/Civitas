package civitas;

/**
 * @file Jugador.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/
import java.util.Collection;    //Para hacer el isEmpty()

public class Jugador implements Comparable<Jugador>{

    protected int CasasMax = 4;                         //Número de casas máximo que se pudede tener
    protected int CasasPorHotel = 4;                    //
    protected Boolean encarcelado;                      //
    protected int HotelesMax = 4;                       //
    protected float PasoPorSalida = 1000;               //
    protected float PrecioLibertad = 200;               //

    private String nombre;                              //
    private int numCasillaActual;                       //
    private Boolean puedeComprar;                       //
    private float saldo;                                //
    private float SaldoInicial = 7500;                  //
    private Sorpresa salvoconducto;
    private ArrayList<TituloPropiedad> propiedades;

    boolean cancelarHipoteca(int ip){ //SIGUIENTE PRÁCTICA

    }

    int cantidadCasasHoteles(){
      return CasasPorHotel;
    }

    public int compareTo (Jugador otro){

    }

    boolean comprar (TituloPropiedad titulo){ //SIGUIENTE PRÁCTICA

    }

    boolean construirCasa (int ip){ //SIGUIENTE PRÁCTICA

    }

    boolean construirHotel (int ip){  //SIGUIENTE PRÁCTICA

    }

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

    boolean enBancarrota (){
      boolean cero = false;

      if (getSaldo >= 0)
        cero = true;

      return cero;
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

    private boolean existeLaPropiedad (int ip){
      boolean existe = false;

      if(propiedades.contains(ip))  //E: esto está mal porque las propiedades no tienen una ip asociada
        existe = true;
      return existe;
    }

    private int getCasasMax (){
      return CasasMax;
    }

    int getCasasPorHotel (){
      return CasasPorHotel;
    }

    private int getHotelesMax ()}{
      return HotelesMax;
    }

    protected String getNombre (){
      return nombre;
    }

    int getNumCasillaActual (){
      return numCasillaActual;
    }

    private float getPrecioLibertad (){
      return PrecioLibertad;
    }

    private float getPremioPasoSalida (){
      return PasoPorSalida;
    }

    protected TituloPropiedad getPropiedades(){
      return propiedades;           //E:  return propiedades[]  ?????????
    }

    boolean getPuedeComprar(){
      return puedeComprar;
    }

    protected float getSaldo(){
      return saldo;
    }

    boolean hipotecar (int ip){ //PRÁCTICA SIGUIENTE

    }

    public boolean isEncarcelado(){
      return encarcelado
    }

    Jugador (String nombre){  //IMPLEMENTAR

    }

    protected Jugador (Jugador otro){ //IMPLEMENTAR

    }

    boolean modificarSaldo (float cantidad){
      saldo += cantidad;
      Diario.getInstance().ocurreEvento ("Se ha incrementado el saldo");

      return true;
    }

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

    boolean obtenerSalvoconducto (Sorpresa s){
      boolean obtiene = true;

      if (isEncarcelado())
        obtiene = false;
      else
        salvoconducto = s;

      return obtiene;
    }

    boolean paga (float cantidad){
      return modificarSaldo(cantidad * -1);
    }

    boolean pagaAlquiler (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return paga(cantidad);
    }

    boolean pagaImpuesto (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return paga(cantidad);
    }

    boolean pasaPorSalida (){
      modificarSaldo(getPremioPasoSalida());
    }

    private void perderSalvoconducto(){
      salvoconducto.usada();  //E: npi de cuál es este método ni cómo se usada
      salvoconducto = null;
    }

    boolean puedeComprarCasilla(){
      if(isEncarcelado())
        puedeComprar = false;
      else
        puedeComprar = true;

      return getPuedeComprar();
    }

    private boolean puedeSalirCarcelPagando(){
      boolean salir = false;

      if(getSaldo() >= getPrecioLibertad())
        salir = true;

        return salir;
    }

    private boolean puedoEdificarCasa (TituloPropiedad propiedad){  //IMPLEMENTAR

    }

    private boolean puedoEdificarHotel (TituloPropiedad propiedad){ //IMPLEMENTAR

    }

    private boolean puedoGastar (float precio){
      boolean puedo = true;

      if (isEncarcelado())
        puedo = false;
      else if (getSaldo() < precio)
        puedo = false;

      return puedo;
    }

    boolean recibe (float cantidad){
      if (isEncarcelado())
        return false;
      else
        return modificarSaldo(cantidad);
    }

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

    boolean salirCarcelTirando (){
      if(Dado.getInstance().salgoDeLaCarcel()){
        encarcelado = false;
        Diario.getInstance().ocurreEvento("El jugador ha tirado y ha salido de la cárcel");
      }

        return isEncarcelado();
    }

    boolean tieneAlgoQueGestionar (){
      boolean tiene = true;

      if (propiedades.isEmpty())
        tiene = false

      return tiene;
    }

    boolean tieneSalvoconducto (){
      boolean tiene = true;

      if (salvoconducto == null)
        tiene = false;

      return tiene;
    }

    @Override
    public String toString(){ //IMPLEMENTAR

    }

    boolean vender (int ip){
      boolean puedo = true;

      if(isEncarcelado())
        puedo = false;
      else if (existeLaPropiedad(ip)){
        propiedades[ip].vendida()
      }

    }

}
