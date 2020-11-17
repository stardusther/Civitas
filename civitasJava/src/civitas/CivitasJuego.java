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
    static final int casillaCarcel = 3;             /** @warning provisional */                         
    static final int numCasillas = 11;              /** @warning provisional */

    /** Constructor. */
    public CivitasJuego (String [] nombres){       

        jugadores = new ArrayList<> ();

        for (int i = 0; i < numJugadores; i++)      // Como sólo puede haber cuatro jugadores, sólo se cogen los primeros cuatro nombres
            jugadores.add (new Jugador (nombres[i]));
        

        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza (numJugadores);

        inicializaMazoSorpresas (tablero);
        inicializaTablero (mazo);
    }

    /** Inicializa el tablero. */
    private void inicializaTablero (MazoSorpresas mazo){
      tablero = new Tablero(casillaCarcel);   
      TituloPropiedad t1, t2, t3;
      
      // Creamos calles
      int i = 1;
      final int alquiler = 100, hipotecaBase = 50, precioCompra = 120, precioEdificar = 200;
      final float factorRev = 1.2f;
      final String nombre = "Calle ";
      t1 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      t2 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      t3 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      
      // Casillas
      Casilla c1 = new Casilla (t1);                        // Casillas calle
      Casilla c2 = new Casilla (t2);
      Casilla c3 = new Casilla (t3);
      
      Casilla descanso = new Casilla ("Descanso");          // Casilla descanso
      
      Casilla s1 = new Casilla (mazo, "Sorpresa 1");        // Casillas sorpresa
      Casilla s2 = new Casilla (mazo, "Sorpresa 2");
      Casilla s3 = new Casilla (mazo, "Sorpresa 3");
      
      final int cantidad_impuesto = 50;
      Casilla impuesto = new Casilla (cantidad_impuesto, "Impuesto");   // Casilla impuesto

      for ( i=1 ; i < numCasillas-1; i++)       // -1 (la carcel se añade automáticamente)
          switch (i) {
              case 1:
                  tablero.añadeCasilla (c1);
                  break;
              case 2:
                  tablero.añadeCasilla (s1);
                  break;
              case 4:
                  tablero.añadeCasilla (impuesto);
                  break;
              case 5:
                  tablero.añadeCasilla (c2);
                  break;
              case 6:
                  tablero.añadeCasilla (s2);
                  break;
              case 7:
                  tablero.añadeJuez();
                  break;
              case 8:
                  tablero.añadeCasilla(s3);
                  break;
              case 9:
                  tablero.añadeCasilla(c3);
                  break;
              case 10:
                  tablero.añadeCasilla(descanso);
          }
    }
    
    /** Inicializa el mazo. */
    private void inicializaMazoSorpresas (Tablero tablero){
        
        mazo = new MazoSorpresas (true);   // Lo inicializamos con el debug activado para esta practica.
        
        // Creamos una sorpresa de cada tipo
        
        int valor = 100;
        int ir_a_casiila = 7;
        int num_sorpresas = 6;
        
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCARCEL, tablero));
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, ir_a_casiila, " Ir a casilla 7 (JUEZ)"));
        mazo.alMazo (new Sorpresa (TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORJUGADOR, valor, " POR JUGADOR"));
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORCASAHOTEL, valor, " POR CASA HOTEL"));
        mazo.alMazo (new Sorpresa (TipoSorpresa.PAGARCOBRAR, valor, " PAGARCOBRAR"));
        
    }

    /** Avanza jugador. */
    private void avanzaJugador() {
        
        // Declaramos al jugador actual y su posicion
        Jugador jugadorActual = getJugadorActual();
        int posicionActual = jugadorActual.getNumCasillaActual();
        jugadorActual.moverACasilla(posicionActual);
        
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
