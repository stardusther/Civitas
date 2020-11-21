=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative "./Sorpresa.rb"

module Civitas
  class MazoSorpresas

    # Constructor
    def initialize (debug=false)
      init()
      @debug = debug

      if (@debug)
        Diario.instance.ocurre_evento ("Modo debug activo");
      end

    end

    def alMazo (s)
      if (!@barajada)
        @sorpresas.push (s)
      end
    end

    def siguiente()
      if ( (!@barajada || @usadas == @sorpresas.length) && !@debug)
        @sorpresas.shuffle            # Baraja el mazo de sorpresas
        @usadas = 0
        @barajada = true
      end

      @usadas += 1
      @ultimaSorpresa = @sorpresa[0]
      @sorpresa.delete_at(0)
      @sorpresa.push (@ultimaSorpresa)

      @ultimaSorpresa
    end

    def inhabilitarCartaEspecial (sorpresa)
      found = false
      i = 0

      # No tengo claro si se puede hacer con for en ruby o como :(
      while i < @sorpresas.lenght && !found

        if sorpresa == @sorpresas[i]
          @cartasEspeciales.push(sorpresa)
          @sorpresas.delete_at(i)

          Diario.instance.ocurreEvento ("Se ha inhabilitado una carta especial");

          found = true
        end

        # JA no existe el i++ o ++i en Ruby :_D E: lloremos
        i += 1
      end

    end

    def habilitarCartaEspecial (sorpresa)
      found = false
      i = 0

      while i < @cartasEspeciales.length && !found

        if sorpresa == @cartasEspeciales[i]
          @sorpresas.push(sorpresa)
          @cartasEspeciales.del_at(i)
          Diario.instance.ocurreEvento ("Se ha habilitado una carta especial")
          found = true
        end

        i += 1

      end

    end

    # Inicializa los atributos
    private
    def init()
      @sorpresas = []
      @cartasEspeciales = []
      @barajada = false
      @usadas = 0
    end

  end
end