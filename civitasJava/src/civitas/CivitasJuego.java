package civitas;
import java.util.ArrayList;

/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 */

public class CivitasJuego {

    private Tablero tablero;
    private MazoSorpresas mazo;
    private ArrayList<Jugador> jugadores;                       
    private EstadosJuego estado;
    private GestorEstados gestorEstados;

    private int indiceJugadorActual;

    static final int numJugadores = 2;                          
    static final int casillaCarcel = 3;                                      
    static final int numCasillas = 20;               

    /** Constructor. */
    public CivitasJuego (ArrayList<String> nombres){      
        jugadores = new ArrayList<> ();

        for (int i = 0; i < numJugadores; i++)  
            jugadores.add (new Jugador (nombres.get(i)));

        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza (numJugadores);

        tablero = new Tablero(casillaCarcel);   
        mazo = new MazoSorpresas (true);       // Lo inicializamos con el debug activado 
        
        inicializaMazoSorpresas (tablero);
        inicializaTablero (mazo);
    }

    /** Inicializa el tablero. */
    private void inicializaTablero (MazoSorpresas mazo){
        TituloPropiedad t1, t2, t3;

        // Calles
        int cont = 1;
        final int alquiler = 100, hipBase = 50, precioCompra = 150, precioEdif = 200;
        final float factRev = 2.0f;
        final String calle = "Calle ";    

        // Casilla impuesto
        final float cantidad_impuesto = 150f; 
        
        for (int i=1 ; i < numCasillas; i++)       // Carcel se añade automáticamente (en c3), y la salida ya está en 0
            switch (i) {
                case 2:     // Sorpresa 1
                    tablero.añadeCasilla (new CasillaSorpresa ("Sorpresa 1", mazo));
//                    tablero.añadeCasilla ( new Casilla (mazo, "Sorpresa 1"));
                    break;
                    
                case 4:     // Impuesto
                    tablero.añadeCasilla(new CasillaImpuesto ("Impuesto de " + cantidad_impuesto + ".", cantidad_impuesto));
                    break;
                    
                case 7:      // Sorpresa 2
                    tablero.añadeCasilla (new CasillaSorpresa ("Sorpresa 2", mazo));
                    break;
                    
                case 8:      // Juez
                    tablero.añadeJuez();
                    break;
                    
                case 10:     // Sorpresa 3
                    tablero.añadeCasilla (new CasillaSorpresa ("Sorpresa 3", mazo));
                    break;
                    
                case 12:     // Parking
                    tablero.añadeCasilla(new Casilla ("Parking"));
                    break;
                    
                case 3:      // Carcel (se añade automaticamente) --> si no se pone el case entra en default y crea calle
                    break;
                    
                default:    // Calles: casillas 1, 5, 6, 9, 11-19
                    tablero.añadeCasilla (new CasillaCalle
                           (new TituloPropiedad (calle + cont++, alquiler, factRev, hipBase, precioCompra, precioEdif)));
                    break;
            }
    }
    
    /** Inicializa el mazo. */
    private void inicializaMazoSorpresas (Tablero tablero){
        
        int valor = 200;  // <-- Para sorpresas PAGARCOBRAR, PORJUGADOR y PORCASAHOTEL
        int ir_casilla_juez = 8;  
        int ir_a_calle = 9;
        int ir_a_calle2 = 12;
        
        
        // Llevar a calle
        mazo.alMazo (new SorpresaIrCasilla (tablero, ir_a_calle, " ¡Vas a la calle de la casilla " + ir_a_calle +"!"));
        
        // Llevar a juez
        mazo.alMazo (new SorpresaIrCasilla (tablero, ir_casilla_juez, " Irás al juez..."));
        
        // Llevar a calle 2 
        mazo.alMazo (new SorpresaIrCasilla (tablero, ir_a_calle2, " ¡Vas a la calle de la casilla " + ir_a_calle2 +"!"));
        
        // Ir carcel
        mazo.alMazo (new SorpresaIrCarcel (tablero));
        
        // Salir carcel
        mazo.alMazo (new SorpresaSalirCarcel (mazo));
        
        // Por jugador, positiva (recibe) y negativa (paga)
        mazo.alMazo (new SorpresaPorJugador (valor, " ¡Recibes 200 civiMonedas de cada jugador!"));  
        mazo.alMazo (new SorpresaPorJugador (valor*-1, " Tienes que pagarle 200 civiMonedas a cada jugador :( ..."));  
        
        // Por casa hotel, positiva y negativa
        mazo.alMazo (new SorpresaPorCasaHotel (valor, " ¡Recibes 200 civiMonedas por cada casa y hotel que tengas!"));
        mazo.alMazo (new SorpresaPorCasaHotel (valor*-1, " Tienes que pagar 200 civiMonedas por cada casa y hotel que tengas :( ..." ));
        
        // Pagar cobrar, positiva y negativa
        mazo.alMazo (new SorpresaPagarCobrar (valor, " ¡Cobras 200 civiMonedas!" ));
        mazo.alMazo (new SorpresaPagarCobrar (valor*-1, " Tienes que pagar 200 civiMonedas :(..." ));
        
    }

    /** Avanza jugador. */
    private void avanzaJugador() {
        
        // Declaramos al jugador actual y su posicion
        Jugador jugadorActual = getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        
        // Calculamos su nueva posicion tirando el dado
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion (posicionActual, tirada);
        
        // Declaramos la casilla en la que está la nueva posición
        Casilla casilla = tablero.getCasilla(posicionNueva);
        
        // Comprobamos si ha pasado por salida para que el jugador reciba el dinero en tal caso
        contabilizarPasosPorSalida (jugadorActual);
        
        // Movemos al jugador a la nueva posición
        jugadorActual.moverACasilla(posicionNueva);
        
        // Actualizamos la casilla y volvemos a comprobar si ha pasado por salida
        casilla.recibeJugador (indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida (jugadorActual);
    } 
    
    public Jugador getJugadorActual(){
      return jugadores.get(indiceJugadorActual);
    }

    public Casilla getCasillaActual(){   
      return (tablero.getCasilla (getJugadorActual().getNumCasillaActual()));
    }

    private void contabilizarPasosPorSalida(Jugador jugadorActual){
      while (tablero.getPorSalida() > 0)
        jugadorActual.pasaPorSalida();
    }

    private void pasarTurno(){
      indiceJugadorActual = (++indiceJugadorActual) % numJugadores;
    }
    
    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas (jugadorActual, estado);
        
        if (operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if (operacion == OperacionesJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }

    public void siguientePasoCompletado (OperacionesJuego operacion){
      estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
    }

    public boolean construirCasa (int ip){
       return getJugadorActual().construirCasa(ip);
    }

    public boolean construirHotel (int ip){
      return getJugadorActual().construirHotel(ip);
    }

    public boolean vender (int ip){
      return getJugadorActual().vender(ip);
    }
    
    public boolean comprar () {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        Casilla casilla = tablero.getCasilla (jugadorActual.getNumCasillaActual());
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        return (jugadorActual.comprar(titulo));
    }
    
    public boolean hipotecar (int ip){
      return getJugadorActual().hipotecar(ip);
    }

    public boolean cancelarHipoteca (int ip){
      return getJugadorActual().cancelarHipoteca(ip);
    }

    public boolean salirCarcelPagando (){
      return getJugadorActual().salirCarcelPagando();
    }

    public boolean salirCarcelTirando (){
      return getJugadorActual().salirCarcelTirando();
    }

    public boolean finalDelJuego(){
      boolean finaljuego = false;

      for (int i = 0; i < numJugadores && !finaljuego; i++)
          finaljuego = jugadores.get(i).enBancarrota();
      
      return finaljuego;
    }

    //@Override
    public int compareTo(Jugador j){
      return Float.compare(getJugadorActual().getSaldo(), j.getSaldo());
    }

    public ArrayList<Jugador> ranking() {
        Jugador max;
        ArrayList<Jugador> playersrank = new ArrayList();
        
        ArrayList<Jugador> jugadores_aux = (ArrayList)jugadores.clone();
        
        for (int i = 0; i < numJugadores; i++) {
            max = jugadores_aux.get (0);
            for (int j = 1; j < jugadores_aux.size(); j++) 
                if (max.compareTo(jugadores_aux.get(j)) < 0) 
                    max = jugadores_aux.get(j);
            playersrank.add(max);
            jugadores_aux.remove(max);
        }    
        
        return playersrank;
    }

}