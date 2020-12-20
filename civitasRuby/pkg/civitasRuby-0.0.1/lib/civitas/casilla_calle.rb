#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative './TituloPropiedad.rb'


module Civitas
  class CasillaCalle < Casilla
    
    def initialize(titulo)
      super(titulo.nombre)
      @tituloPropiedad = titulo
      @importe = -1
      @Carcel = -1
      @mazo = nil
      @sorpresa = nil
    end
    
    def recibeJugador(actual, todos)
      
      if(jugadorCorrecto(actual, todos))
        informe(actual,todos)
        jugador = todos[actual]
      
        if !@tituloPropiedad.tienePropietario
          jugador.puedeComprarCasilla
        elsif !@tituloPropiedad.hipotecado
          @tituloPropiedad.tramitarAlquiler(jugador)
        else
            Diario.instance.ocurre_evento("El jugador #{jugador.nombre} se libra de pagar el alquiler porque la calle está hipotecada.")
        end
      
      end
    end
    
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de tipo CALLE"
      Diario.instance.ocurre_evento(str)
    end
    
  end
end
