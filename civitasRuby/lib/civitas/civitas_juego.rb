#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'jugador.rb'
require_relative 'gestor_estados.rb'
require_relative 'Dado'
require_relative 'Tablero'
require_relative 'MazoSorpresas'
require_relative 'TituloPropiedad'


module Civitas
  class CivitasJuego
    
    @@NumJugadores = 2  # Todo lo que empiece con mayúsculas se considera constante
    @@CasillaCarcel = 3 # Preguntar de todas formas
    @@NumCasillas = 7
    
    def initialize(nombres)
      @jugadores = []
      
      for i in 0..@@NumJugadores
        @jugadores.insert(i,Civitas::Jugador::new(nombres[i])) # Crea el jugador
      end
      
      @gestorEstados = Civitas::Gestor_estados.new()
      @estado = @gestorEstados.estado_inicial()
      
      @indiceJugadorActual = Dado.instance().quienEmpieza(@@NumJugadores)
      
      @mazo = MazoSorpresas.new(true)
      @tablero = Tablero.new(@@CasillaCarcel)
      inicializaMazoSorpresas(@tablero)
      inicializaTablero(@mazo)
      
    end
           
    def getJugadorActual
      jugador = @jugadores[@indiceJugadorActual]
    end
    
    def getCasillaActual
      jugador = getJugadorActual()
      numCasilla = jugador.numCasillaActual
      @tablero.getCasilla(numCasilla)
    end
    
    def siguientePaso
      jugadorActual = @jugadores.at(@indiceJugadorActual)
      operacion = @gestorEstados.operaciones_permitidas(jugadorActual, @estado)
      
      if operacion == Civitas::OperacionesJuego::PASAR_TURNO
        pasarTurno()
        siguientePasoCompletado(operacion)
      elsif operacion == Civitas::OperacionesJuego::AVANZAR
        avanzaJugador()
        siguientePasoCompletado(operacion)
      end
      
      operacion
    end
    
    def siguientePasoCompletado(operacion)
      @estado = @gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual], @estado, operacion)
    end
    
    def construirCasa(ip)
      getJugadorActual().construirCasa(ip)
    end
    
    def construirHotel(ip)
      getJugadorActual().construirHotel(ip)
    end
    
    def vender(ip)
      getJugadorActual().vender(ip)
    end
    
    def comprar
      jugadorActual = @jugadores.at(@indiceJugadorActual)
      casilla =@tablero.getCasilla(jugadorActual.getNumCasillaActual())
      titulo = casilla.getTituloPropiedad()
      jugadorActual.comprar(titulo)
    end
    
    def hipotecar(ip)
      getJugadorActual().hipotecar(ip)
    end
    
    def cancelarHipoteca(ip)
      getJugadorActual().cancelarHipoteca(ip)
    end
    
    def salirCarcelPagando
      getJugadorActual().salirCarcelPagando()
    end
    
    def salirCarcelTirando
      getJugadorActual().salirCarcelTirando()
    end
    
    def finalDelJuego
      finaljuego = false
      
      for i in 0..NumJugadores and !finaljuego
        finaljuego = @jugadores.at(i).enBancarrota()
      end
      
      finaljuego
    end
    
    def compareTo(jugador)
      @saldo <=> otro.get_saldo
    end
    
    def ranking # El ranking debe ser público para poder acceder a él 
      playersrank = []
      
      jugadores_aux = @jugadores  # no sé cómo copiar un array
      
      for i in 0..NumJugadores
        max = jugadores_aux.at(0)
        pos = 0
        
        for j in 1..jugadores_aux.size
          if max.compareTo(jugadores_aux.at(j) < 0)
            max = jugadores_aux.at(j)
            pos = j
          end
        end
          
        playersrank.push(max)
        jugadores_aux.delete_at(pos)
        
      end
      
      return playersrank # Se puede poner return pa que quede más claro, no es un error
    end
    
    private # -------------------------------------------------
    
    def avanzaJugador()
      
      # Declaramos al jugador actual y su posicion
      jugadorActual = getJugadorActual()
      posicionActual = jugadorActual.numCasillaActual
      
      # Calculamos su nueva posicion tirando el dado  
      tirada = Dado.instance.tirar()
      posicionNueva = @tablero.nuevaPosicion(posicionActual, tirada)
      
      # Declaramos la casilla en la que está la nueva posición  
      casilla = @tablero.getCasilla(posicionNueva)
      
      # Comprobamos si ha pasado por salida para que el jugador reciba el dinero en tal caso
      contabilizarPasosPorSalida(jugadorActual)
      
      # Movemos al jugador a la nueva posición
      jugadorActual.moverACasilla(posicionNueva)
      
      # Actualizamos la casilla y volvemos a comprobar si ha pasado por salida
      
      casilla.recibeJugador(@indiceJugadorActual, @jugadores)
      contabilizarPasosPorSalida(jugadorActual)
    end
    
    def contabilizarPasosPorSalida(jugadorActual)
      while @tablero.getPorSalida > 0
        jugadorActual.pasarPorSalida
      end
    end
    
    def inicializaTablero(mazo)
      #@tablero = Tablero.new(@@CasillaCarcel)
      
      # Creamos calles
      i = 1
      alquiler = 100
      hipotecaBase = 50
      precioCompra = 150
      precioEdificar = 200
      factorRev = 1.2
      
      n1 = "Calle 1"
      n2 = "Calle 2"
     
      t1 = TituloPropiedad.new(n1, alquiler*i, factorRev, hipotecaBase*i, precioCompra*i, precioEdificar*i)
      i = i+1
      t2 = TituloPropiedad.new(n2, alquiler*i, factorRev, hipotecaBase*i, precioCompra*i, precioEdificar*i)
      
      # Casillas
      c1 = Civitas::Casilla.newCalle(t1)
      c2 = Civitas::Casilla.newCalle(t2)
      
      s1 = Casilla.newSorpresa(@mazo, "Sorpresa 1")
      
      cantidad_impuesto = 50
      impuesto = Casilla.newImpuesto(cantidad_impuesto, "Impuesto (50€)")
      
      for i in 1..@@NumCasillas
        case i
        
        when 1
          @tablero.añadeCasilla(c1)
        when 2
          @tablero.añadeCasilla(s1)
        when 4
          @tablero.añadeCasilla(impuesto)
        when 5
          @tablero.añadeCasilla(c2)
        when 6
          @tablero.añadeJuez
        end
        
      end
      
    end
    
    def inicializaMazoSorpresas(tablero)
      valor = 100
      ir_a_casilla = 6
      num_sorpresas = 6
      
      irCarcel = Sorpresa.newIrCarcel(TipoSorpresa::IRCARCEL, tablero)
      
      @mazo.alMazo(Sorpresa.newIrCarcel(TipoSorpresa::IRCARCEL, tablero))
      @mazo.alMazo(Sorpresa.newIrCasilla(TipoSorpresa::IRCASILLA, tablero, ir_a_casilla, " Ir a casilla 6 (JUEZ)"))
      @mazo.alMazo(Sorpresa.newEvitaCarcel(TipoSorpresa::SALIRCARCEL, @mazo))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORJUGADOR, valor, " POR JUGADOR"))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORCASAHOTEL, valor, " POR CASA HOTEL"))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PAGARCOBRAR, valor, " PAGARCOBRAR"))
    end
    
    def pasarTurno
      indiceJugadorActual = (indiceJugadorActual+1) % @@NumJugadores
    end
    
  end
end
