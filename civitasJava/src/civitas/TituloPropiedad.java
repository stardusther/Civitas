/**
 * @file MazoSopresas.java
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
    
    // Valor 'nulo' por defecto?
    private Jugador propietario;
    
    // Todos los titulos de propiedad comienzan existiendo sin propietario, casas ni hoteles y sin hipotecar
    TituloPropiedad (String _nombre, float base_alq, float factor_rev, float base_hip,
                     float precio_compra, float precio_edificar) {
        nombre = _nombre;
        alquilerBase = base_alq;
        factorRevalorizacion = factor_rev;
        hipotecaBase = base_hip;
        precioCompra = precio_compra;
        precioEdificar = precio_edificar;
        
        hipotecado = false;
        numCasas = 0;
        numHoteles = 0;
        factorInteresesHipoteca = 1.1f ;
    }
    
    void ActualizaPropietarioPorConversion (Jugador jugador) {
        
    }
    
    /**
     * @warning Pendiente
     * @param jugador
     * @return 
     */
    boolean cancelarHipoteca (Jugador jugador) {
        return true;
    }
    
    int cantidadCasasHoteles() {
        return (numCasas + numHoteles);
    }
    
    /**
     * @warning Pendiente
     * @param jugador
     * @return 
     */
    boolean comprar (Jugador jugador) {
        return true;
    }
    
    boolean construirCasa (Jugador jugador) {
        
    }
    
    boolean construirHotel (Jugador jugador) {
        
    }
    
    boolean derruirCasas (int n, Jugador jugador) {
        
    }
    
    boolean esEsteElPropietario (Jugador jugador) {
        
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
        
    }
    
    float getPrecioEdificar () {
        
    }
    
    float getPrecioVenta () {
        return (precioCompra + precioEdificar * (numCasas+numHoteles) * factorRevalorizacion);
    }
    
    Jugador getPropietario () {
        return propietario;
    }
    
    boolean hipotecar (Jugador jugador) {
        
    }
    
    boolean propietarioEncarcelado () {
        
    }
    
    boolean tienePropietario() {
        
    }
    
    TituloPropiedad (String nombre, float ab, float fr, float hb, float pc, float pe) {
        
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
        
    }
    
    boolean vender (Jugador jugador) {
        
    }
    
}


