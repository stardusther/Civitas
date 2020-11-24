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
    
    @@NumJugadores = 2  
    @@CasillaCarcel = 3
    @@NumCasillas = 20
    
    # Añadimos consultor de numero de jugadores para poder consultarlo en el controlador
    # Consultor de propiedades para acceder a el en vista textual.
    attr_reader :NumJugadores, :propiedades
    
    def initialize(nombres)
      @jugadores = []
      
      for i in 0..@@NumJugadores
        @jugadores.insert(i,Civitas::Jugador::new(nombres[i])) # Crea el jugador
      end
      
      @gestorEstados = Civitas::Gestor_estados.new()
      @estado = @gestorEstados.estado_inicial()
      
      #Dado.instance.set
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
      jugadorActual = @jugadores[@indiceJugadorActual]
      operacion = @gestorEstados.operaciones_permitidas(jugadorActual, @estado)
      
      if operacion == Civitas::Operaciones_juego::PASAR_TURNO
        pasarTurno()
        siguientePasoCompletado(operacion)
      elsif operacion == Civitas::Operaciones_juego::AVANZAR
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
      casilla =@tablero.getCasilla(jugadorActual.numCasillaActual)
      titulo = casilla.tituloPropiedad()
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
      final_juego = false
      i=0
      
      while i<@@NumJugadores && !final_juego
        final_juego = @jugadores.at(i).enBancarrota()
        i = i+1
      end
      
      final_juego
    end
    
    def compareTo(jugador)
      @saldo <=> otro.get_saldo
    end
    
    def ranking # El ranking debe ser público para poder acceder a él desde el controlador
      playersrank = []
      
      jugadores_aux = @jugadores.clone
      
      for i in 0..@@NumJugadores
        max = jugadores_aux.at(0)
        pos = 0
        
        for j in 1..jugadores_aux.size-1
          if max.compare_to(jugadores_aux[j]) < 0
            max = jugadores_aux.at(j)
            pos = j
          end
        end
          
        playersrank.push(max)
        jugadores_aux.delete_at(pos)
        
      end
      
      playersrank  
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
      
      # Calles
      alquiler = 100
      hipBase = 50
      precioCompra = 150
      precioEdif = 200
      factorRev = 1.2
      cont = 1   # Contador para el nombre de las calles
      
      cantidad_impuesto = 150
      
      # Tablero como en civitasJava
      for i in 1..@@NumCasillas-1 
        case i
          
        when 2 # Sorpresa 1
          @tablero.añadeCasilla(Casilla.newSorpresa(@mazo, "Sorpresa 1"))
          
        when 4 # Impuesto
          @tablero.añadeCasilla(Casilla.newImpuesto( cantidad_impuesto, "Impuesto #{cantidad_impuesto}"))
        
        when 7 # Sorpresa 2
          @tablero.añadeCasilla(Casilla.newSorpresa(@mazo, "Sorpresa 2"))
          
        when 8 # Juez
          @tablero.añadeJuez
          
        when 10 # Sorpresa 3
          @tablero.añadeCasilla(Casilla.newSorpresa(@mazo, "Sorpresa 3"))
          
        when 12 # Parking
          @tablero.añadeCasilla(Casilla.newDescanso("Parking"))
          
        when 3 # Carcel en 3, se añade automaticamente (si no lo ponemos se ejecuta añadir calle en else)
          
        else
          @tablero.añadeCasilla(Casilla.newCalle(TituloPropiedad.new("Calle #{cont}", alquiler, factorRev, hipBase, precioCompra, precioEdif)))
          
        end
        
      end
      
      
    end
    
    def inicializaMazoSorpresas(tablero)
      valor = 200
      ir_casilla_juez = 8
      ir_a_calle = 9
      ir_a_calle2 = 12
      
      # Ir calle 1
      @mazo.alMazo(Sorpresa.newIrCasilla(TipoSorpresa::IRCASILLA, tablero, ir_a_calle, " ¡Vas a la calle de la casilla #{ir_a_calle}!"))
      
      #Juez
      @mazo.alMazo(Sorpresa.newIrCasilla(TipoSorpresa::IRCASILLA, tablero, ir_casilla_juez,  "¡Vas a la calle de la casilla #{ir_casilla_juez}!"))
      
      # Ir calle 2
      @mazo.alMazo(Sorpresa.newIrCasilla(TipoSorpresa::IRCASILLA, tablero, ir_a_calle, " ¡Vas a la calle de la casilla #{ir_a_calle}!"))
      
      # Salir carcel
      @mazo.alMazo(Sorpresa.newEvitaCarcel(TipoSorpresa::SALIRCARCEL, @mazo))
      
      # Ir carcel
      @mazo.alMazo(Sorpresa.newIrCarcel(TipoSorpresa::IRCARCEL, tablero))
      
      # Por jugador, positiva (rebibe) y negativa (paga)
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORJUGADOR, valor, " ¡Recibes 200 civiMonedas de cada jugador!"))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORJUGADOR, valor*(-1), " Tienes que pagarle 200 civiMonedas a cada jugador ..."))
      
      # Por casa hotel positiva y negativa
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORCASAHOTEL, valor, " ¡Recibes 200 civiMonedas por cada casa y hotel que tengas!"))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PORCASAHOTEL, valor*(-1), " Tienes que pagar 200 civiMonedas por cada casa y hotel que tengas ..."))
    
      # Pagar cobrar positiva y negativa
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PAGARCOBRAR, valor, " ¡Cobras 200 civiMonedas!"))
      @mazo.alMazo(Sorpresa.newOtras(TipoSorpresa::PAGARCOBRAR, valor*(-1), " Tienes que pagar 200 civiMonedas ..."))
      
    end
    
    def pasarTurno
      @indiceJugadorActual = (@indiceJugadorActual+1) % @@NumJugadores
    end
    
  end
end
