#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  
class SorpresaPorJugador < Sorpresa
  
  def initialize (valor, texto)
    super("*Por jugador*", nil, valor, texto, nil)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)

      i = 0
      while i < todos.length
        if i != actual
          todos[i].paga(@valor)
        end
        i += 1
      end
      todos[actual].recibe(@valor)
    end
  end
  
end

end
