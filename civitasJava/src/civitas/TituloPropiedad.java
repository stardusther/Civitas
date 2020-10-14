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
        factorInteresesHipoteca = (float) 1.1 ;
    }
    
    void ActualizaPropietarioPorConversion (Jugador jugador) {
        
    }
    
    boolean cancelarHipoteca (Jugador jugador) {
        
    }
    
    int cantidadCasasHoteles() {
        
    }
    
    boolean comprar (Jugador jugador) {
        
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
        
    }
    
    float getImporteCancelarHipoteca () {
        
    }
    
    float getImporteHipoteca () {
        
    }
    
    String getNombre() {
        
    }
    
    int getNumCasas() {
        
    }
    
    int getNumHoteles() {
        
    }
    
    float getPrecioAlquiler() {
        
    }
    
    float getPrecioCompra () {
        
    }
    
    float getPrecioEdificar () {
        
    }
    
    float getPrecioVenta () {
        
    }
    
    Jugador getPropietario () {
        
    }
    
    boolean hipotecar (Jugador jugador) {
        
    }
    
    boolean propietarioEncarcelado () {
        
    }
    
    boolean tienePropietario() {
        
    }
    
    TituloPropiedad (String nombre, float ab, float fr, float hb, float pc, float pe) {
        
    }
    
    String toString() {
        
    }
    
    void tramitarAlquiler (Jugador jugador) {
        
    }
    
    boolean vender (Jugador jugador) {
        
    }
    
}


