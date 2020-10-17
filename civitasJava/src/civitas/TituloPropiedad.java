/**
 * @file TituloPropiedad.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
*/

package civitas;
public class TituloPropiedad {

    private String nombre;

    private float alquilerBase;
    private float hipotecaBase;
    private float factorRevalorizacion;
    private float factorInteresesHipoteca;
    private float precioCompra;
    private float precioEdificar;

    private boolean hipotecado;

    private int numCasas;
    private int numHoteles;

    private Jugador propietario;
    private static String no_propietario = "NO_PROPIETARIO";

    /**
     * Constructor de la clase.
     * @warning Propietario por defecto = "NO_PROPIETARIO" ???
     * @post titulo no hipotecado, sin casas ni hoteles
     */
    TituloPropiedad (String _nombre, float ab, float fr, float hb,
                     float pc, float pe) {
        nombre = _nombre;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;

        hipotecado = false;
        propietario = new Jugador (no_propietario); 
        numCasas = 0;
        numHoteles = 0;
        factorInteresesHipoteca = 1.1f ;
    }

    void ActualizaPropietarioPorConversion (Jugador jugador) {
        propietario = jugador;
    }

    /**
     * @warning Pendiente
     * @param jugador
     * @return
     */
    boolean cancelarHipoteca (Jugador jugador) {
        hipotecado = false;
        return hipotecado;
    }

    int cantidadCasasHoteles() {
        return (numCasas + numHoteles);
    }

    /**
     * @warning Siguiente practica 
     * @param jugador
     */
    boolean comprar (Jugador jugador) {
        return true;
    }

    /**
     * @warning Siguiente practica 
     * @param jugador
     */
    boolean construirCasa (Jugador jugador) {
        return true;
    }

    /**
     * @warning Siguiente practica 
     * @param jugador
     */
    boolean construirHotel (Jugador jugador) {
        return true;
    }
    
    /**
     * @brief Si el jugador es el propietario y n es menor al num. de casas, 
     * se decrementa el contador de casas en n unidades
     * @param n numero a decrementar del contador
     * @param jugador jugador al que aplicar el decremento de casas
     * @return @retval true si seha realizado la operacion @retval false si no ha sido el caso
     */
    boolean derruirCasas (int n, Jugador jugador) {
        boolean correcto = true;
        
        if (n >= numCasas && jugador == propietario)        //Y: Me queda mirar si jugador == otroJUgador se puede hacer (provisional jeje)
            numCasas -= n;
        
        else
            correcto = false;
        
        return correcto;
    }

    boolean esEsteElPropietario (Jugador jugador) {
        return (jugador == propietario);                    //Y: Idem ^ 
    }

    boolean getHipotecado () {
        return hipotecado;
    }

    float getImporteCancelarHipoteca () {
        return (factorInteresesHipoteca * hipotecaBase);
    }

    float getImporteHipoteca () {
        return hipotecaBase;
    }

    String getNombre() {
        return nombre;
    }

    int getNumCasas() {
        return numCasas;
    }

    int getNumHoteles() {
        return numHoteles;
    }

    float getPrecioAlquiler() {
        float precio;
        //devuelve el precio del alquiler calculado según las reglas del
        //juego. Si el título se encuentra hipotecado o si el propietario está encarcelado (ver
        //propietarioEncarcelado()) el precio del alquiler será cero.

        if (hipotecado || propietarioEncarcelado())
            precio = 0f;
        else
            precio = 1f;
            //Calcular precio (1 para que no de error de compilacion de momento)

        return precio;
    }

    float getPrecioCompra () {
        return precioCompra;
    }

    float getPrecioEdificar () {
        return precioEdificar;
    }

    float getPrecioVenta () {
        return (precioCompra + precioEdificar * (numCasas+numHoteles) * factorRevalorizacion);
    }

    Jugador getPropietario () {
        return propietario;
    }

    /**
     * @warning Siguiente practica 
     * @param jugador
     */
    boolean hipotecar (Jugador jugador) {
        return true;
    }

    boolean propietarioEncarcelado () {
        boolean encarcelado = false;
        if (propietario.getNombre() != no_propietario && propietario.isEncarcelado())
            encarcelado = true;
        
        return encarcelado;
    }

    boolean tienePropietario() {
        //no se como implementar esto (dar nombre al propietario por defecto ??)
        return true;
    }

    @Override
    public String toString() {
        String estado = " Estado: \n"
                        + " >> Nombre: " + nombre + "\n"
                        + " >> Alquiler base: " + alquilerBase + "\n"
                        + " >> Hipoteca base: " + hipotecaBase + "\n"
                        + " >> Factor de revalorizacion: " + factorRevalorizacion + "\n"
                        + " >> Factor intereses hipoteca: " + factorInteresesHipoteca + "\n"
                        +  ">> Factor intereses hipoteca: " + factorInteresesHipoteca + "\n"
                        + " >> Precio compra: " + precioCompra + "\n"
                        + " >> Precio edificar: " + precioEdificar + "\n"
                        + " >> Hipotecado: " + hipotecado + "\n"
                        + " >> Nº de casas: " + numCasas + "\n"
                        + " >> Nº de hoteles: " + numHoteles + "\n" ;

        return estado;
    }

    void tramitarAlquiler (Jugador jugador) {
        if (propietario.getNombre() != no_propietario && jugador != propietario) {
            jugador.pagaAlquiler(alquilerBase);
            propietario.recibe(alquilerBase);
        }
    }

    boolean vender (Jugador jugador) {
        boolean vendido = false;
        if (jugador == propietario && !hipotecado) {
            propietario.recibe(precioCompra);
            numCasas = 0;
            numHoteles = 0;
            //desvincular propietario no c
            vendido = true;
        }
        
        return vendido;
    }

}
