#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'titulo_propiedad.rb'


module Civitas
  class CasillaCalle < Casilla
    
    def initialize(titulo)
      super(titulo.nombre)
      @tituloPropiedad = titulo
      @importe = -1
      @Carcel = -1
      @mazo = nil
      @sorpresa = nil
     
      @tipo = "CALLE"
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
    
  end
end
