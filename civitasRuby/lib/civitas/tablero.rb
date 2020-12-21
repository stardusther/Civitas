#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'casilla'

module Civitas
  class Tablero

    attr_reader :numCasillaCarcel

    def initialize (indice)
      if (indice >=1)
        @numCasillaCarcel = indice
      else
        @numCasillaCarcel = 1;
      end

      @casillas = []                           
      @casillas.push(Casilla.new("Salida"))     # Añadimos la salida al inicio (posicion 0)

      @porSalida = 0
      @tieneJuez = false
    end

    ##
    # Consultor que devuelve el numero de veces que el jugador ha pasado por salida.
    # Se decrementa en una unidad el nº de pasos por salida a cada uso del consultor.
    def getPorSalida ()
      devuelve = @porSalida

      if @porSalida > 0
        @porSalida = @porSalida - 1
      end

      devuelve
    end

    ##
    # Añade la casilla dada al tablero. Comprueba si es necesario
    # añadir la carcel antes o despues de la misma.
    def añadeCasilla (casilla)
      añadeCarcel()
      @casillas.push(casilla)
      añadeCarcel()
    end

    ##
    # Añade una casilla de tipo juez si el aun no está en el tablero.
    def añadeJuez ()
      if !@tieneJuez
        casillaJuez = CasillaJuez.new(@numCasillaCarcel, "Juez")
        añadeCasilla (casillaJuez)
        @tieneJuez = true
      end
    end

    ##
    # Consultor de la casilla en un indice dado del tablero.
    def getCasilla (numCasilla)
      correcto(numCasilla) ? @casillas[numCasilla] : nil
    end

    ##
    # Calcula la posicion en la que queda el jugador tras tirar el dado. Si
    # el tablero no es correcto, la tirada toma valor nulo.
    def nuevaPosicion (actual, tirada)
      posicion = nil

      if correcto_tablero()
        posicion = (actual + tirada) % @casillas.length

        if posicion != (actual + tirada)
          @porSalida += 1
        end
      end

      posicion
    end

    ##
    # Calcula la tirada necesaria para llegar desde el
    # origen al destino dados.
    def calcularTirada (origen, destino)
      (result = destino - origen) > 0 ? result : result + @casillas.length
    end
    
    def to_s
      str = ""
      
      for i in 0..@casillas.length-1
        str = str + "#{@casillas[i].to_s} | "
      end
      
      str
    end

    private # ---------------------------------------------------------------- #
    
    ##
    # Consultor para verificar si el tablero es correcto
    def correcto_tablero()
      @casillas.length > @numCasillaCarcel && @tieneJuez
    end

    ##
    # Consultor para verificar que el tablero es correcto y el 
    # indice pasado es valido
    def correcto(numCasilla)
      correcto_tablero && (numCasilla<@casillas.length)
    end

    ##
    # Comprueba si es necesario añadir la carcel, y si 
    # lo lleva a cabo en caso afirmativo
    def añadeCarcel ()
      if @casillas.length == @numCasillaCarcel
        carcel = Casilla.new("Cárcel")
        @casillas.push(carcel)
      end
    end

  end
end
