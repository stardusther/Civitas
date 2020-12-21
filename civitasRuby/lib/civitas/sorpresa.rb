#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  class Sorpresa

    attr_reader :valor
    
    def initialize (tipo, tab, val, txt, m)
      @tipo = tipo     # string para indicar el tipo de sorpresa y usarlo en el metodo 'informe'.
      @tablero = tab
      @valor = val
      @texto = txt
      @mazo = m
    end

    def jugadorCorrecto (actual, todos)
      correcto = false
      if actual < todos.length && actual>=0
        correcto = true
      end
      correcto
    end
    
    def aplicarAJugador (actual, todos)
      if jugadorCorrecto(actual, todos)
        Diario.instance.ocurre_evento("\n ¡...! Se aplica sorpresa indefinida. No ocurrirá nada... \n\n")
        
      end
    end
    

    def toString
      str = " Sorpresa: #{@texto}. >> Valor: #{@valor}"
    end

    
    private # ---------------------------------------------------------------- #
    
    def informe (actual, todos)
      if jugadorCorrecto(actual, todos)
        Diario.instance.ocurre_evento("\n ¡Sorpresa! #{@texto} Se aplica #{@tipo} al jugador #{todos[actual].nombre}\n\n")
      end
    end

  end
end