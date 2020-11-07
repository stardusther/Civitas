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
    private ArrayList<Jugador> jugadores;                       //E: Jugador[] jugadores = new Jugador [4];
    private EstadosJuego estado;
    private GestorEstados gestorEstados;

    private int indiceJugadorActual;

    static final int numJugadores = 4;                          
    static final int casillaCarcel = 5;                         
    static final int numCasillas = 20;ç

    /** Constructor. */
    public CivitasJuego (String j1, String j2, String j3, String j4){       

        jugadores = new ArrayList<> ();

        jugadores.add (new Jugador (j1));
        jugadores.add (new Jugador (j2));
        jugadores.add (new Jugador (j3));
        jugadores.add (new Jugador (j4));

        estado = gestorEstados.estadoInicial();

        indiceJugadorActual = Dado.getInstance().quienEmpieza (numJugadores);

        inicializaTablero(mazo);
        inicializaMazoSorpresas(tablero);
    }

    private void inicializaTablero (MazoSorpresas mazo){
      tablero = new Tablero(casillaCarcel);   
      TituloPropiedad t1, t2, t3;
      
      // Creamos calles
      int i = 0;
      final int alquiler = 100, hipotecaBase = 50, precioCompra = 120, precioEdificar = 200;
      final float factorRev = 1.2f;
      final String nombre = "Calle ";
      t1 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      t2 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      t3 = new TituloPropiedad (nombre + i, alquiler*i, factorRev, 
                               hipotecaBase*i, precioCompra*i, precioEdificar*i++);
      
      Casilla c1 = new Casilla (t1);
      Casilla c2 = new Casilla (t2);
      Casilla c3 = new Casilla (t3);
      
      Casilla descanso = new Casilla ("Descanso");
      
      Casilla s1 = new Casilla (mazo, "Sorpresa 1");
      Casilla s2 = new Casilla (mazo, "Sorpresa 2");
      Casilla s3 = new Casilla (mazo, "Sorpresa 3");
      
      Casilla juez = (casillaCarcel, "Juez");
      
      final int cantidad_impuesto = 50;
      Casilla impuesto = new Casilla (cantidad_impuesto, "Impuesto");

      for (int i = 1; i < numCasillas-1; i++)       // -1 porque carcel se añade automáticamente
          switch (i) {
              default:
                  tablero.añadeCasilla (calle); 
              case 7:
                  tablero.añadeCasilla (sorpresa1);
                  break;
              case 10:
                  tablero.añadeCasilla(descanso);
                  break;
              case 13:
                  tablero.añadeCasilla (sorpresa2);
              case 15:
                  tablero.añadeJuez();
                  break;
              case 18:
                  tablero.añadeCasilla(sorpresa3);
                  break;
          }
    }

    public Jugador getJugadorActual(){
      return jugadores.get(indiceJugadorActual);
    }

    public Casilla getCasillaActual(){   
      return (tablero.getCasilla (getJugadorActual().getNumCasillaActual()));
    }
    
    private void inicializaMazoSorpresas (Tablero tablero){
        
    }

    private void contabilizarPasosPorSalida(Jugador jugadorActual){
      while (tablero.getPorSalida() > 0)
        jugadorActual.pasaPorSalida();
    }

    private void pasarTurno(){
      if (indiceJugadorActual < 4)                                              // Los índices de los jugadores empiezan en 0 (no?)
        indiceJugadorActual++;
      else
        indiceJugadorActual = 0;
    }

    public void siguientePasoCompletado (OperacionesJuego operacion){
      estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
    }

    public boolean construirCasa (int ip){
       //getJugadorActual().construirCasa(ip);   Y:
       return getJugadorActual().construirCasa(ip);
    }

    public boolean construirHotel (int ip){
      //getJugadorActual().construirHotel(ip);   Y:
      return getJugadorActual().construirHotel(ip);
    }

    public boolean vender (int ip){
      //getJugadorActual().vender(ip);           Y:
      return getJugadorActual().vender(ip);
    }

    public boolean hipotecar (int ip){
      //getJugadorActual().hipotecar(ip);
      return getJugadorActual().hipotecar(ip);
    }

    public boolean cancelarHipoteca (int ip){
      //getJugadorActual().cancelarHipoteca(ip);
      return getJugadorActual().cancelarHipoteca(ip);
    }

    public boolean salirCarcelPagando (int ip){
      //getJugadorActual().salirCarcelPagando();
      return getJugadorActual().salirCarcelPagando();
    }

    public boolean salirCarcelTirando (int ip){
      //getJugadorActual().salirCarcelTirando();
      return getJugadorActual().salirCarcelTirando();
    }

    public boolean finalDelJuego(){
      boolean finaljuego = false;

      for (int i = 0; i < 4 && !finaljuego; i++)
        if (jugadores.get(i).enBancarrota())
          finaljuego = true;
      return finaljuego;
    }

    @Override
    public int compareTo(Jugador j){
      return Float.compare(getSaldo(), j.getSaldo());
    }

    private Jugador ranking(){                                        //E: rehacer con el compareTo          
      ArrayList <Jugador> jugadorescopia = new ArrayList<Jugador> (jugadores);
      ArrayList <Jugador> playersrank = new ArrayList<Jugador> ();
      Jugador max;

      for (int i = 0; i < 4; i++){
        max = jugadorescopia.get(i);
        for(int j = 0 ; j < jugadorescopia.size() ; j++)
            if(max < jugadorescopia.get(j).getSaldo())
               max = jugadorescopia.get(j);
        playersrank.add(max);
        jugadorescopia.remove(max);
      }
    }

    public String infoJugadorTexto(){
      String s;
      for (int i = 0; i < 4; i++)
        s += jugadores.get(i).toString();

      return s;
    }

}
