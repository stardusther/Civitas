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
    static final int numCasillas = 19;               

    /** Constructor. */
    public CivitasJuego (ArrayList<String> nombres){      
        jugadores = new ArrayList<> ();

        for (int i = 0; i < numJugadores; i++)  
            jugadores.add (new Jugador (nombres.get(i)));

        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza (numJugadores);

        tablero = new Tablero(casillaCarcel);   
        mazo = new MazoSorpresas (true);       // Lo inicializamos con el debug activado para esta practica.
        
        inicializaMazoSorpresas (tablero);
        inicializaTablero (mazo);
    }

    /** Inicializa el tablero. */
    private void inicializaTablero (MazoSorpresas mazo){
        TituloPropiedad t1, t2, t3;

        // Calles
        int i = 1;
        final int alquiler = 100, hipBase = 50, precioCompra = 150, precioEdif = 200;
        final float factRev = 2.0f;
        final String calle = "Calle ";    

        // Casilla impuesto
        final float cantidad_impuesto = 50f; 

        for ( i=1 ; i < numCasillas; i++)       // la carcel se añade automáticamente y la salida ya está en 0
            switch (i) {
                default:    // Casillas 1, 5, 6, 9, 11-19
                    tablero.añadeCasilla (new Casilla (new TituloPropiedad (calle + i++, alquiler, factRev, hipBase, precioCompra, precioEdif)));
                    break;
                case 2:     // Sorpresa 1
                    tablero.añadeCasilla (new Casilla (mazo, "Sorpresa 1"));
                    break;
                case 4:     // Impuesto
                    tablero.añadeCasilla(new Casilla (cantidad_impuesto, "Impuesto de ." + cantidad_impuesto));
                    break;
                case 7:      // Sorpresa 2
                    tablero.añadeCasilla (new Casilla (mazo, "Sorpresa 2"));
                    break;
                case 8:      // Juez
                    tablero.añadeJuez();
                    break;
                case 10:
                    tablero.añadeCasilla (new Casilla (mazo, "Sorpresa 3"));
                    break;
                case 12:     // Parking
                    tablero.añadeCasilla(new Casilla ("Parking"));
                    break;
            }
    }
    
    /** Inicializa el mazo. */
    private void inicializaMazoSorpresas (Tablero tablero){
        
        int valor = 100;  // <-- Para sorpresas PAGARCOBRAR y PORJUGADOR
        int ir_casilla_juez = 8;  
        int ir_a_calle2 = 5;
        int ir_salida = 0;
        
        // Llevar a salida
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, ir_salida, " Te ayudamos a ser tu propio jefe con este incentivo de 1000 civiMonedas."));
        
        // Llevar a juez
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, ir_casilla_juez, " Felicidades, es navidad."));
        
        // Llevar a calle 2 (MercaCivitas)
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, ir_a_calle2, " Te llevamos al MercaCivitas para que puedas comprar tus creditos favoritos."));
        
        // Ir carcel
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCARCEL, tablero));
        
        // Salir carcel
        mazo.alMazo (new Sorpresa (TipoSorpresa.SALIRCARCEL, mazo));
        
        // Por jugador, positiva (recibe) y negativa (paga)
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORJUGADOR, valor, " ¡Recibes 100 civiMonedas de cada jugador!"));  
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORJUGADOR, valor*-1, " Tienes que pagarle 100 civiMonedas a cada jugador :( ..."));  
        
        // Por casa hotel, positiva y negativa
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORCASAHOTEL, valor, " ¡Recibes 100 civiMonedas por cada casa y hotel que tengas!"));
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORCASAHOTEL, valor*-1, " Tienes que pagar por cada casa y hotel que tengas :( ..." ));
        
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

//        for (int i = 0; i < numJugadores; i++) {
//            max = jugadores.get(i);
//            for (int j = i + 1; j < numJugadores; j++) 
//                if (max.compareTo(jugadores.get(j)) < 0) 
//                    max = jugadores.get(j);
//            playersrank.add(max);
//        }
        
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