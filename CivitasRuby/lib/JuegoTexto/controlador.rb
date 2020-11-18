=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative ./Civitas/Diario #E: no estoy segura de si esto va así
module Juego_texto
  class Controlador
    
    def initialize(_juego, _vista)
        @juego = _juego
        @vista = _vista
    end
    
    def juega
      final = false
      
      @vista.setCivitasJuego
    end
    
  end
end

