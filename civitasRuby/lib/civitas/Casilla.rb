#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative "./TipoCasilla.rb"
require_relative "./Sorpresa.rb"

module Civitas
  class Casilla

    attr_reader :nombre, :tituloPropiedad, :carcel

    # Constructores ---------------------------------------------------------- #
    
    def self.newDescanso (n) 
      Casilla.new(n, nil, -1, -1, nil, nil)    
    end

    
    # Metodos ---------------------------------------------------------------- #

    def jugadorCorrecto(actual, todos)
      actual < todos.length
    end
   
    def to_s
      @nombre
    end


    protected #----------------------------------------------------------------- #
  
    def initialize(n, titulo, cantidad, numCasillaCarcel, m, sorp)
      @nombre = n
      @tituloPropiedad = titulo
      @importe = cantidad
      @Carcel = numCasillaCarcel
      @mazo = m
      @sorpresa = sorp
      # @tipo = tipo
      
    end

    def informe (actual, todos)
      str = "El jugador #{todos[actual].nombre} ha caido en una casilla de DESCANSO"
      Diario.instance.ocurre_evento(str)
    end
  
  end
end
