#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative "sorpresa"

module Civitas
  class CasillaSorpresa < Casilla
    
    def initialize(mazo, nombre)
      super(nombre)
      @tituloPropiedad = nil
      @importe = -1
      @Carcel = -1
      @mazo = mazo
      @sorpresa = nil
      
      @tipo = "SORPRESA"
    end
    
    # Override
    def recibeJugador(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
        @sorpresa = @mazo.siguiente
        @sorpresa.aplicarAJugador(actual, todos)
      end
    end
    
  end
end
