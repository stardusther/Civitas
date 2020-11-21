=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative "./TipoCasilla.rb"
module Civitas
  class Casilla

    attr_reader :nombre, :tituloPropiedad

    #@@carcel                #Atributo de instancia

    def self. newDescanso (n) 
      Casilla.new(n, nil, -1, -1, nil, nil)    #n, titulo, cantidad, numCasillaCarcel, m, sorp
      @tipo = Civitas::TipoCasilla::DESCANSO
    end

    def self. newCalle (titulo)
      new(titulo.nombre, titulo, -1, -1, nil, nil)
      @tipo = Civitas::TipoCasilla::CALLE
    end

    def self. newImpuesto (cantidad, n)
      new(n, nil, cantidad, -1, nil, nil)
      @tipo = Civitas::TipoCasilla::IMPUESTO
    end

    def self. newJuez (numCasillaCarcel, n)
      new(n, nil, -1, numCasillaCarcel, nil, nil)
      @tipo = Civitas::TipoCasilla::JUEZ
    end

    def self. newSorpresa (mazo, n)
      new(n, nil, -1, -1, mazo, nil)
    end

    def jugadorCorrecto(actual, todos)
      actual < todos.length
    end

    def self. recibeJugador(actual, todos)
      case @tipo
      
      when Civitas::TipoCasilla::CALLE
        recibeJugador_calle(actual, todos)
      
      when Civitas::TipoCasilla::IMPUESTO
        recibeJugador_impuesto(actual, todos)
      
      when Civitas::TipoCasilla::JUEZ
        recibeJugador_juez(actual, todos)
      
      when Civitas::TipoCasilla::SORPRESA
        recibeJugador_sorpresa(actual, todos)
      
      else
        informe(actual, todos)
      end
    
    end

    def toString
      str = @nombre + " (" + @tipo + ")"
    end


    private #------------------------------------------------------------------- #
  
    def initialize(n, titulo, cantidad, numCasillaCarcel, m, sorp)
      @nombre = n
      @tituloPropiedad = titulo
      @importe = cantidad
      @@carcel = numCasillaCarcel
      @mazo = m
      @sorpresa = sorp
    end

  
    def informe
      str = "El jugador " + todos[actual] + " ha caido en la casilla " + toString()
      Diario.getInstance.ocurreEvento(str)
    end

  
    def recibeJugador_calle(actual, todos)
      if(jugadorCorrecto(actual, todos))
        informe(actual,todos)
        jugador = todos[actual]
      
        if (!@tituloPropiedad.tienePropietario)
          jugador.puedeComprarCasilla
        else
          @tituloPropiedad.tramitarAlquiler(jugador)
        end
      
      end
    end

  
    def recibeJugador_impuesto(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].pagaImpuesto(importe)
      end
    end

  
    def recibeJugador_juez(actual, todos)
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(carcel)
      end
    end
  

    def recibeJugador_sorpresa(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        sorpresa.aplicarAJugador(actual, todos)
      end
    end

  
  end
end
