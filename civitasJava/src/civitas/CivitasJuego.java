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
    
    // 'Numeros magicos' jjejejej
    static final int numJugadores = 4;                          //Y: atributo de clase para indicar el número de jugadores
    static final int casillaCarcel = 5;                         //Y: atributo de clase para indicar la casilla de carcel
    static final int numCasillas = 20;

    public CivitasJuego (String j1, String j2, String j3, String j4){           //E: Jugador (String... nombre) Paso de parámetros variable

//      jugadores.add(Jugador.new(j1));
//      jugadores.add(Jugador.new(j2));
//      jugadores.add(Jugador.new(j3));
//      jugadores.add(Jugador.new(j4));

        //Y: inicializar vector
        jugadores = new ArrayList<> ();
        
        //Y: añadir jugadores
        jugadores.add (new Jugador (j1));
        jugadores.add (new Jugador (j2));
        jugadores.add (new Jugador (j3));
        jugadores.add (new Jugador (j4));

        estado = gestorEstados.estadoInicial();
        
        //Y: he cambiado el 4 por un atributo de clase constante 
        indiceJugadorActual = Dado.getInstance().quienEmpieza (numJugadores);

        // "Crear el mazo de sorpresas, llamar al método de inicialización del tablero y del mazo" E: tengo que redefinir el mazo?
        tablero.inicializaTablero(mazo);
        mazo.inicializaMazoSorpresas(tablero);
    }

    private void inicializaTablero (MazoSorpresas mazo){
      tablero = new Tablero(casillaCarcel);                                 //Se añade automáticamente la casilla de salida en la posición 0

      for (int i = 1; i < numCasillas; i++)
          switch (i) {
              default:
                  tablero.añadeCasilla (calle);
              case 5:
                  tablero.añadeCasilla (carcel);
                  break;
              case 7:
                  tablero.añadeCasilla (sorpresa_mazo1);
                  break;
              case 10:
                  tablero.añadeCasilla(descaso);
                  break;
              case 13:
                  tablero.añadeCasilla (sorpresa_mazo2);
              case 15:
                  tablero.añadeJuez();
                  break;
              case 18:
                  tablero.añadeCasilla(sorpresa_mazo3);
                  break;
          }
    }

    public Jugador getJugadorActual(){
      return jugadores.get(indiceJugadorActual);
    }

    public Casilla getCasillaActual(){                                          // E: no lo entiendo bien
      //return getJugadorActual().getNumCasillaActual();                          // Esto no devuelve un objeto del tipo casilla
      
      //Y: acceder al tablero:
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

    private Jugador ranking(){                                                  //E: not sure its ok
      ArrayList <Jugador> jugadorescopia = new ArrayList<Jugador> (jugadores);
      ArrayList <Jugador> playersrank = new ArrayList<Jugador> ();
      Jugador max;                                                              // E: max = new Jugador(); ??????????

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
