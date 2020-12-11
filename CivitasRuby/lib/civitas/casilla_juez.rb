# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    
    def initialize(numCasillaCarcel, nombre)
      super(nombre, nil, -1, numCasillaCarcel, nil, nil)
    end
    
    # Override del método recibeJugador
    def recibeJugador(actual, todos)
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(@Carcel)
      end
    end
    
    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en la casilla JUEZ"
      Diario.instance.ocurre_evento(str)
    end
    
  end
end
