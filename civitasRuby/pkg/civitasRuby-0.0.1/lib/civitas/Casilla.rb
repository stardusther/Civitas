=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative "./TipoCasilla.rb"
module Civitas
  class Casilla

    attr_reader :nombre, :tituloPropiedad

    #@@carcel                # Atributo de instancia
    
    def to_s
      str = "tipo #{@tipo} nombre #{@nombre} valor #{@valor}"
    end

    def self. newDescanso (n) 
      Casilla.new(n, nil, -1, -1, nil, nil, Civitas::TipoCasilla::DESCANSO)    #n, titulo, cantidad, numCasillaCarcel, m, sorp
      #@tipo = Civitas::TipoCasilla::DESCANSO
    end

    def self. newCalle (titulo)
      new(titulo.nombre, titulo, -1, -1, nil, nil, Civitas::TipoCasilla::CALLE)
      #@tipo = Civitas::TipoCasilla::CALLE
    end

    def self. newImpuesto (cantidad, n)
      new(n, nil, cantidad, -1, nil, nil, Civitas::TipoCasilla::IMPUESTO)
      #@tipo = Civitas::TipoCasilla::IMPUESTO
    end

    def self. newJuez (numCasillaCarcel, n)
      new(n, nil, -1, numCasillaCarcel, nil, nil, Civitas::TipoCasilla::JUEZ)
      #@tipo = Civitas::TipoCasilla::JUEZ
    end

    def self. newSorpresa (mazo, n)
      new(n, nil, -1, -1, mazo, nil, Civitas::TipoCasilla::SORPRESA)
    end

    def jugadorCorrecto(actual, todos)
      actual < todos.length
    end

    def recibeJugador(actual, todos)
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

    def to_s
      str = "#{@nombre}. Tipo: #{@tipo}. Valor: #{@valor}"
    end


    private #------------------------------------------------------------------- #
  
    def initialize(n, titulo, cantidad, numCasillaCarcel, m, sorp, tipo)
      @nombre = n
      @tituloPropiedad = titulo
      @importe = cantidad
      @@carcel = numCasillaCarcel
      @mazo = m
      @sorpresa = sorp
      @tipo = tipo
    end

  
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en la casilla #{to_s}"
      Diario.instance.ocurre_evento(str)
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
        @sorpresa.aplicarAJugador(actual, todos)
      end
    end

  
  end
end
