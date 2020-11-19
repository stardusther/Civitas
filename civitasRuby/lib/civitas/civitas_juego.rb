=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative ./civitas/jugador.rb # Incluye Dado
require_relative ./civitas/Tablero.rb
require_relative ./civitas/MazoSorpresas.rb
require_relative ./civitas/TituloPropiedad.rb


module Civitas
  class CivitasJuego
    
    @@NumJugadores = 2  # Todo lo que empiece con mayúsculas se considera constante
    @@CasillaCarcel = 3 # Preguntar de todas formas
    @@NumCasillas = 11
    
    def initialize(nombres)
      jugadores = []
      
      for i in 0..@@NumJugadores
        jugadores.insert(i,jugador.new(nombres[i])) # Crea el jugador
      end
      
      @gestorEstados = GestorEstados.new()
      @estado = @gestorEstados.estadoInicial()
      
      @indiceJugadorActual = Dado.instance().quienEmpieza(NumJugadores)
      
      inicializaMazoSorpresas(@tablero)
      inicializaTablero(@mazo)
      
    end
    
    def inicializaTablero(mazo)
      @tablero = Tablero.new(@@CasillaCarcel)
      
      # Creamos calles
      i = 1
      alquiler = 100
      hipotecaBase = 50
      precioCompra = 120
      precioEdificar = 200
      factorRev = 1.2
      nombre = "Calle "

      t1 = TituloPropiedad.new(nombre + i, alquiler*i, factorRev, hipotecaBase*i, precioCompra*i, precioEdificar*i++)
      t2 = TituloPropiedad.new(nombre + i, alquiler*i, factorRev, hipotecaBase*i, precioCompra*i, precioEdificar*i++)
      t3 = TituloPropiedad.new(nombre + i, alquiler*i, factorRev, hipotecaBase*i, precioCompra*i, precioEdificar*i++)
      
      
    end
    
  end
end
