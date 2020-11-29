#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative "Diario.rb"

require 'singleton'

module Civitas
  class Dado
    include Singleton
                                  #En ruby no se necesita la instancia
    @@SalidaCarcel = 5               # Los atributos son privados por defecto

    attr_reader :ultimoResultado  #Consultor de ultimoResultado

    def initialize                #Constructor
      @Random
      @ultimoResultado
      @debug = false
    end

    def tirar
      if @debug
        @ultimoResultado = 1
      else
        @ultimoResultado = rand(6) + 1  #Roll a 6 sided die, rand(6) returns a number from 0 to 5 inclusive
      end
    end

    def salgoDeLaCarcel
      salgo = false                     #variable local

      if (tirar >= @@SalidaCarcel)       #E: esto devolverá la variable salgo o no pq es local?
        salgo = true
      else
        salgo = false
      end

    end

    def quienEmpieza (n)
      rand(n)         #E: no dé di hay que incluir algún paquete
    end

    def setDebug (d)
      if d != @debug
        @debug = d
        modo = "Debug desactivado en el dado"
      end

        if @debug
          modo = "Debug activo en el dado"
        end

    Diario.instance.ocurre_evento(modo)
  end
end
end
