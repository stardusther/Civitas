#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_salir_carcel'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_especulador'

module Civitas
  class MazoSorpresas

    # wat porque el constructor en privado ??
    private # -----------------------------------------------------------
    # Constructor
    def initialize (debug=false)
      init()
      @debug = debug

      if (@debug)
        Diario.instance.ocurre_evento ("Modo debug activo en el mazo");
      end

    end

    public # -----------------------------------------------------------
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
      @ultimaSorpresa = @sorpresas[0]
      @sorpresas.delete_at(0)
      @sorpresas.push (@ultimaSorpresa)

      @ultimaSorpresa
    end

    def inhabilitarCartaEspecial (sorpresa)
      found = false
      i = 0

      while i < @sorpresas.lenght && !found

        if sorpresa == @sorpresas[i]
          @cartasEspeciales.push(sorpresa)
          @sorpresas.delete_at(i)

          Diario.instance.ocurreEvento ("Se ha inhabilitado una carta especial");

          found = true
        end

        i = i + 1
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