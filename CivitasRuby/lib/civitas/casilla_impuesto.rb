# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaImpuesto < Casilla
    
    def initialize(nombre, cantidad)
      super(nombre)
      @importe = cantidad
    end
    
    def recibeJugador(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].pagaImpuesto(@importe)
      end
    end
    
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de IMPUESTO"
      Diario.instance.ocurre_evento(str)
    end
    
  end
end
