#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'jugador.rb'
require_relative 'gestor_estados.rb'
require_relative 'dado'
require_relative 'tablero'
require_relative 'mazo_sorpresas'
require_relative 'titulo_propiedad'

require_relative 'casilla_calle'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'casilla_sorpresa'

require_relative 'titulo_examen'

module Civitas
  class CivitasJuego
    
    @@NumJugadores = 2  
    @@CasillaCarcel = 4
    @@NumCasillas = 20
    
    # Añadimos consultor de numero de jugadores para poder consultarlo en el controlador
    # Consultor de propiedades para acceder a el en vista textual.
    attr_reader :NumJugadores, :propiedades
    
    ##
    # Examen
    def Examen
      for i in 0..@jugadores.length-2
        puts " ---> Jugador #{@jugadores[i].nombre}"
        for j in 0..@jugadores[i].propiedades.length
          puts @jugadores[i].propiedades[j].to_s
        end
      end
    end
    
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
    
    def compare_to(jugador)
      @saldo <=> jugador.get_saldo
    end
    
    def ranking # El ranking debe ser público para poder acceder a él desde el controlador
      playersrank = []
      jugadores_aux = @jugadores.clone
      
      for i in 0..@@NumJugadores-1
        #puts "leng #{jugadores_aux.length}"
        max = jugadores_aux[0]
        pos = 0
        
        for j in 1..jugadores_aux.length-2     # jugadores_aux mide 3 en lugar de 2 (verificar con el put de arriba)
          if max.compare_to(jugadores_aux[j]) < 0
            max = jugadores_aux[j].clone
            pos = j
          end
          
        end
        
        playersrank.push(max)
        jugadores_aux.delete_at(pos)
        
      end
      
      playersrank  
    end
    
    private # ---------------------------------------------------------------- #
    
    def avanzaJugador()
      
      # Declaramos al jugador actual y su posicion
      jugadorActual = getJugadorActual
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
        jugadorActual.pasaPorSalida
      end
    end
    
    def inicializaTablero(mazo)
      
      # Calles
      alquiler = 200
      hipBase = 100
      precioCompra = 250
      precioEdif = 300
      factorRev = 1.2 
      cont = 1   # Contador para el nombre de las calles
      
      cantidad_impuesto = 150
      
      # (Tablero para examen)
      for i in 1..@@NumCasillas-1 
        case i
          when 1 # Calle1
            t = TituloExamen.new("Calle #{cont}", alquiler, factorRev, hipBase, precioCompra, precioEdif) #para ver si me lo pilla
            @tablero.añadeCasilla(CasillaCalle.new(t))
            cont = cont+1
          
          when 2 # Sorpresa 1
            @tablero.añadeCasilla(CasillaSorpresa.new(mazo, "Sorpresa 1"))

          when 3 # Calle 2
            titulo = TituloPropiedad.new("Calle #{cont}", alquiler, factorRev, hipBase, precioCompra, precioEdif) #para ver si me lo pilla
            @tablero.añadeCasilla(CasillaCalle.new(titulo))
            cont = cont+1

          when 4  # Carcel (no hacer nada, se añade automaticamente) 
            
          when 5 # Impuesto
            @tablero.añadeCasilla(CasillaImpuesto.new( cantidad_impuesto, "Impuesto #{cantidad_impuesto}"))

          when 7 # Sorpresa 2
            @tablero.añadeCasilla(CasillaSorpresa.new(mazo, "Sorpresa 2"))

          when 8 # Juez
            @tablero.añadeJuez

          when 10 # Sorpresa 3
            @tablero.añadeCasilla(CasillaSorpresa.new(mazo, "Sorpresa 3"))

          when 12 # Parking
            @tablero.añadeCasilla(Casilla.new("Parking"))

          else # Calles: 6, 9, 11, 13-19
            titulo = TituloPropiedad.new("Calle #{cont}", alquiler, factorRev, hipBase, precioCompra, precioEdif) #para ver si me lo pilla
            @tablero.añadeCasilla(CasillaCalle.new(titulo))
            cont = cont+1
          
        end
        
      end
      
      
    end
    
    def inicializaMazoSorpresas(tablero)
      valor = 200
      ir_casilla_juez = 8
      ir_a_calle = 9
      ir_a_calle2 = 12
      
      
      #Sorpresa especulador
      @mazo.alMazo( SorpresaEspeculador.new(200))
      
      # Ir calle 1
      @mazo.alMazo( SorpresaIrCasilla.new( tablero, ir_a_calle, " ¡Vas a la calle de la casilla #{ir_a_calle}!") )
      
      # Ir carcel
      @mazo.alMazo( SorpresaIrCarcel.new(tablero))
     
      #Juez
      @mazo.alMazo( SorpresaIrCasilla.new( tablero, ir_casilla_juez,  "Vas al juez de la casilla #{ir_casilla_juez}...") )
      
      # Ir calle 2
      @mazo.alMazo( SorpresaIrCasilla.new( tablero, ir_a_calle2, " ¡Vas a la calle de la casilla #{ir_a_calle2}!") )
      
      # Salir carcel
      @mazo.alMazo( SorpresaSalirCarcel.new(@mazo))
      
      # Ir carcel
      @mazo.alMazo( SorpresaIrCarcel.new(tablero))
      
      # Por jugador, positiva (recibe) y negativa (paga)
      @mazo.alMazo( SorpresaPorJugador.new( valor, " ¡Recibes 200 civiMonedas de cada jugador!"))
      @mazo.alMazo( SorpresaPorJugador.new( valor*(-1), " Tienes que pagarle 200 civiMonedas a cada jugador ..."))
      
      # Por casa hotel positiva y negativa
      @mazo.alMazo( SorpresaPorCasaHotel.new( valor, " ¡Recibes 200 civiMonedas por cada casa y hotel que tengas!"))
      @mazo.alMazo( SorpresaPorCasaHotel.new( valor*(-1), " Tienes que pagar 200 civiMonedas por cada casa y hotel que tengas ..."))
    
      # Pagar cobrar positiva y negativa
      @mazo.alMazo( SorpresaPagarCobrar.new( valor, " ¡Cobras 200 civiMonedas!"))
      @mazo.alMazo( SorpresaPagarCobrar.new( valor*(-1), " Tienes que pagar 200 civiMonedas ..."))
      
    end
    
    def pasarTurno
      @indiceJugadorActual = (@indiceJugadorActual+1) % @@NumJugadores
    end
    
  end
end
