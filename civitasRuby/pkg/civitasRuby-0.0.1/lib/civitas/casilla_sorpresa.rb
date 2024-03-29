# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "./Sorpresa.rb"

module Civitas
  class CasillaSorpresa < Casilla
    
    def initialize(mazo, nombre)
      super(nombre)
      @tituloPropiedad = nil
      @importe = -1
      @Carcel = -1
      @mazo = mazo
      @sorpresa = nil
    end
    
    def recibeJugador(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        @sorpresa = @mazo.siguiente
        @sorpresa.aplicarAJugador(actual, todos)
      end
    end
    
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de SORPRESA"
      Diario.instance.ocurre_evento(str)
    end
    
  end
end
