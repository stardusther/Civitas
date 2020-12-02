#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class SorpresaIrCasilla < Sorpresa
  
  def initialize (tab, valor, texto)
    super("*Ir casilla*", tab, -1, texto, nil)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)

      casilla = todos[actual].numCasillaActual
      tirada = @tablero.calcularTirada(casilla, @valor)
      nuevaPos = @tablero.nuevaPosicion(casilla, tirada)

      todos[actual].moverACasilla(nuevaPos)

      @tablero.getCasilla(@valor).recibeJugador(actual, todos)
    end
  end
  
end  
  
end
