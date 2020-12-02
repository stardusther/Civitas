#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class SorpresaIrCarcel < Sorpresa
  
  def initialize (tab)
    super("*Ir carcel*", tab, -1, "", nil)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@tablero.numCasillaCarcel)
    end
  end
  
end

end