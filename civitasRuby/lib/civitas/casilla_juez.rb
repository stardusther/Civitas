#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  class CasillaJuez < Casilla
    
    def initialize(numCasillaCarcel, nombre)
      super(nombre)
      @tituloPropiedad = nil
      @importe = -1
      @Carcel = numCasillaCarcel
      @mazo = nil
      @sorpresa = nil
    end
    
    # Override 
    def recibeJugador(actual, todos)
      if(jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].encarcelar(@Carcel)
      end
    end
    
  end
end
