#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  class Casilla

    attr_reader :nombre, :tituloPropiedad, :carcel
    
    def initialize(nombre)
      @nombre = nombre
      @tituloPropiedad = nil
      @importe = nil
      @Carcel = nil
      @mazo = nil
      @sorpresa = nil
      
      # Atributo extra para ahorrar la redefinicion
      # del metodo informe en todas las clases hijas
      @tipo = "DESCANSO"
    end
    
    # Metodos ---------------------------------------------------------------- #

    def jugadorCorrecto(actual, todos)
      actual < todos.length
    end
   
    def to_s
      @nombre
    end
    
    def recibeJugador(actual, todos)
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
      end
    end


    protected #--------------------------------------------------------------- #

    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de #{@tipo}"
      Diario.instance.ocurre_evento(str)
    end
  
  end
end
