# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

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
      
        if (!@tituloPropiedad.tienePropietario)
          jugador.puedeComprarCasilla
        else
          @tituloPropiedad.tramitarAlquiler(jugador)
        end
      
      end
    end
    
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de tipo CALLE"
      Diario.instance.ocurre_evento(str)
    end
    
  end
end
