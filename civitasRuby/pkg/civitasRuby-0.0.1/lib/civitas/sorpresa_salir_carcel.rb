#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class SorpresaSalirCarcel < Sorpresa
  
  def initialize(mazo)
    super("*Salvoconducto*", nil, -1, "", mazo)
  end
  
  def aplicarAJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)

      tienen_salvoconducto = false
      i = 0
      while i < todos.length && !tienen_salvoconducto
        tienen_salvoconducto = todos[i].tieneSalvoconducto
        i += 1
      end

      if !tienen_salvoconducto
        todos[actual].obtenerSalvoconducto(self)
        salirDelMazo
      end

    end
  end
  
  private # ------------------------------------------------------------------ #
  
  def salirDelMazo
        @mazo.inhabilitarCartaEspecial(self)
    end

  def usada
      @mazo.habilitarCartaEspecial(self)
  end
  
  
end

end
