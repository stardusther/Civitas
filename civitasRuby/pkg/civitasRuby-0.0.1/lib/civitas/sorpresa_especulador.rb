#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'jugador_especulador'

module Civitas

class SorpresaEspeculador < Sorpresa
  
  def initialize (fianza)
    super("*Especulador*", nil, fianza, "Ahora eres un jugador especulador.", nil)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      jugadorEspeculador = JugadorEspeculador.nuevoEspeculador(todos[actual], @valor)
      todos[actual] = jugadorEspeculador
    end
  end
  
end

end