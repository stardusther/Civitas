#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  class CasillaImpuesto < Casilla
    
    def initialize(cantidad, nombre)
      super(nombre)
      @tituloPropiedad = nil
      @importe = cantidad
      @Carcel = -1
      @mazo = nil
      @sorpresa = nil
    end
    
    # Override 
    def recibeJugador(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        todos[actual].pagaImpuesto(@importe)
      end
    end
    
  end
end
