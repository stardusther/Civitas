=begin
Authors: Esther García Gallego
         Yesenia Glez Dávila
         Grupo B3
=end

require 'singleton'
module Civitas
  class Dado
    include Singleton

                                  #En ruby no se necesita la instancia
    @@SalidaCarcel                #Los atributos son privados por defecto

    attr_reader :ultimoResultado  #Consultor de ultimoResultado

    def initialize                #Constructor
      @Random
      @ultimoResultado
      @debug = false
    end

    def tirar
      if debug
        @ultimoResultado = 1
      else
        @ultimoResultado = rand(6) + 1  #Roll a 6 sided die, rand(6) returns a number from 0 to 5 inclusive
    end

    def salgoDeLaCarcel
      salgo = false                     #variable local

      if (tirar >= @SalidaCarcel)       #E: esto devolverá la variable salgo o no pq es local?
        salgo = true
      else
        salgo = false
      end

    end

    def quienEmpieza (n)
      Random.new.rand(0..(n-1))         #E: no dé di hay que incluir algún paquete
    end

    def setDebug (d)
      if d != @debug
        @debug = d
        modo = "Debug off (dado)"

        if @debug
          modo = "Debug on (dado)"
        end

        Diario.instance.ocurreEvento(modo)

      end
    end

  end
