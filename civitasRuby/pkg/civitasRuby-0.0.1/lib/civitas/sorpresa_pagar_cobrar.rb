#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class SorpresaPagarCobrar < Sorpresa
  
  def initialize (valor, texto)
    super("*Pagar/Cobrar*", nil, valor, texto, nil)
  end
  
  def aplicarAJugador (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor)
      end
    end
end

end
