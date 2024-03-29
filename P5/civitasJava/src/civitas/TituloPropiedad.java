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
    
    // Añadimos el siguiente atributo para saber el importe de la 
    // hipoteca otorgado al jugador cuando la pidió (para usar en getImporteCancelarHipoteca)
    private float importeHipoteca;

    
    /** Constructor de la clase.
     * @note titulo no hipotecado, sin propietario, casas ni hoteles. */
    TituloPropiedad (String nombre, float ab, float fr, float hb, float pc, float pe) {
        this.nombre = nombre;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;

        hipotecado = false;
        propietario = null;
        numCasas = 0;
        numHoteles = 0;
        factorInteresesHipoteca = 1.1f ;
        
        importeHipoteca = 0f;
    }
    
    /** Actualiza el propietario. */
    void ActualizaPropietarioPorConversion (Jugador jugador) {
        propietario = jugador;
    }
    
    /** Si la propiedad está hipotecada y el jugador es el propietario, se 
     * cancela la hipoteca. */
    boolean cancelarHipoteca (Jugador jugador) {
        boolean result = false;
        
        if (hipotecado && esEsteElPropietario(jugador)) {
                jugador.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                importeHipoteca=0;
                result = true;
            }
        
        return result;
    }
    
    /** Devuelve la suma del num de casas y hoteles construidos. */
    int cantidadCasasHoteles() {
        return (numCasas + numHoteles);
    }
    
    /** Si el jugador es el propietario y n es menor al num de casas, 
     *  se decrementa el contador de casas en n unidades. 
     * @return @retval true si se ha realizado la operacion @retval false si no ha sido el caso
     */
    boolean derruirCasas (int n, Jugador jugador) {
        boolean correcto = true;
        
        if (n <= numCasas && esEsteElPropietario(jugador)) 
            numCasas -= n;
        else
            correcto = false;
        
        return correcto;
    }
    
    /** Comprueba si el jugador pasado como parámetro es el propietario del título. */
    private boolean esEsteElPropietario (Jugador jugador) {
        return (jugador == propietario); 
    }
    
    /** Si el titulo tiene propietario y no es el jugador pasado como parametro, 
     * este paga el alguiler correspondiente, y el propietario recibe ese mismo importe. */
    void tramitarAlquiler (Jugador jugador) {
        if (tienePropietario() && !esEsteElPropietario (jugador)) {
            jugador.pagaAlquiler (getPrecioAlquiler());
            propietario.recibe (getPrecioAlquiler());
        }
    }

    /** Si el jugador pasado como parámetro es el propietario y este no está hipotecado,
     *  entonces se da al propietario el precio de venta, se desvincula al propietario de la 
     *  propiedad y se eliminan las casas y hoteles.
     *  @warning desvincular propietario 
     */
    boolean vender (Jugador jugador) {
        boolean vendido = false;
        if (esEsteElPropietario(jugador) && !hipotecado) {
            propietario.recibe(precioCompra);
            numCasas = 0;
            numHoteles = 0;
            propietario = null;
            vendido = true;
        }
        return vendido;
    }
    
    boolean comprar (Jugador jugador) {
        boolean result = false;
        
        if (!tienePropietario()) {
            propietario = jugador;
            result = true;
            jugador.paga(getPrecioCompra());
        }
        
        return result;
    }
    
    
    boolean construirHotel (Jugador jugador) {
        boolean result = false;
        if (esEsteElPropietario(jugador)) {
            jugador.paga(precioEdificar);
            numHoteles += 1;
            result = true;
        }
        return result;
    }
    
    boolean construirCasa (Jugador jugador) {
        boolean result = false;
        if (esEsteElPropietario(jugador)) {
            jugador.paga(precioEdificar);
            numCasas += 1;
            result = true;
        }
        return result;
    }
    
    
    boolean hipotecar (Jugador jugador) {
        boolean salida = false;
        if (!hipotecado && esEsteElPropietario(jugador)) {
            importeHipoteca = getImporteHipoteca();  
            jugador.recibe(importeHipoteca);
            hipotecado = true;
            salida = true;
        }
        
        return salida;
    }
    
    
    // --------------------------- Consultores ------------------------------ //
    
    public boolean getHipotecado () {
        return hipotecado;
    }

    // COMENTARIO: Reglas del juego: se paga según la hipoteca recibida en su momento.
    // Si al pedirla tenemos x edificaciones, y cuando queremos cancelarla ese numero
    // cambia (por vender o construir más), ya no recibiríamos el importe adecuado. 
    // Por ello usamos var aux importeHipoteca
    float getImporteCancelarHipoteca () {
        //return (factorInteresesHipoteca * hipotecaBase);
        return (factorInteresesHipoteca * importeHipoteca);
    }

    float getImporteHipoteca () {
        //return hipotecaBase;
        // COMENTARIO: Cálculo según las reglas (pag 3 pdf CivitasElJuego.pdf)
        return (hipotecaBase * (1+(numCasas*0.5f)+(numHoteles*2.5f)));  
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public int getNumHoteles() {
        return numHoteles;
    }

    float getPrecioAlquiler() {
        float precio;

        if (hipotecado || propietarioEncarcelado())
            precio = 0f;
        else
            precio = (float) (alquilerBase * (1 + ( (float)numCasas*0.5) + ( (float)numHoteles*2.5)));

        return precio;
    }

    public float getAlquilerBase() {
        return alquilerBase;
    }

    public float getHipotecaBase() {
        return hipotecaBase;
    }
     
    
    public float getPrecioCompra () {
        return precioCompra;
    }
    

    public float getPrecioEdificar () {
        return precioEdificar;
    }

    float getPrecioVenta () {
        return (precioCompra + precioEdificar * (numCasas+(5*numHoteles)) * factorRevalorizacion);
    }

    public Jugador getPropietario () {
        return propietario;
    }
    
    boolean tienePropietario() {
        return (propietario != null);
    }
    
    private boolean propietarioEncarcelado () {
        return (tienePropietario() && propietario.isEncarcelado());
    }
    
    @Override
    public String toString() {
        String tab = "      × ";
        String estado =  nombre + "\n" +
                        tab + "Alquiler base: " + alquilerBase + "\n" +
                        tab + "Hipoteca base: " + hipotecaBase + "\n" + 
                        tab + "Factor de revalorizacion: " + factorRevalorizacion + "\n" + 
                        tab + "Factor intereses hipoteca: " + factorInteresesHipoteca + "\n" + 
                        tab + "Precio compra: " + precioCompra + "\n" +
                        tab + "Precio edificar: " + precioEdificar + "\n" +
                        tab + "Nº de casas: " + numCasas + "\n" +
                        tab + "Nº de hoteles: " + numHoteles + "\n" +
                        tab + "Hipotecado: " + hipotecado + "\n" ;
                        
        if (hipotecado)
            estado += tab + "Importe de la hipoteca: " + importeHipoteca + "\n";
        
        if (propietario != null)
            estado += tab + "Propietario: " + propietario.getNombre() + "\n";
        else
            estado += tab + "No tiene propietario. \n";
        return estado;
    }
}