#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class SorpresaPorCasaHotel < Sorpresa
  
  def initialize (valor, texto)
    super("*Por casas y hoteles*", nil, valor, texto, nil)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].modificarSaldo(@valor * todos[actual].cantidadCasasHoteles)
    end
  end
end

end
