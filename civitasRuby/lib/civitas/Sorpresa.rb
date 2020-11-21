#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative './TipoSorpresa.rb'

module Civitas
  class Sorpresa

    attr_reader :valor
    
    
    def initialize (tipo, tab, val, txt, m)
      @tipo = tipo
      @tablero = tab
      @valor = val
      @texto = txt
      @mazo = m
    end
    
    # Constructores
    def self.newIrCarcel (tipo, tab)
      objeto = Sorpresa.new(tipo, tab, -1, "", nil)
    end

    def self.newIrCasilla (tipo, tab, val, txt)
      Sorpresa.new(tipo, tab, val, txt, nil)
    end

    def self.newEvitaCarcel (tipo, m)
      Sorpresa.new(tipo, nil, -1,"", m)
    end

    def self.newOtras (tipo, val, txt)
      Sorpresa.new(tipo, nil, val, txt,nil)
    end

    # Metodos
    def jugadorCorrecto (actual, todos)
      correcto = false

      if actual < todos.length && actual>0
        correcto = true
      end
    end
    
    def aplicarAJugador (actual, todos)
      if jugadorCorrecto(actual, todos)
        
        
        case @tipo
          
        when Civitas::TipoSorpresa::IRCARCEL
          aplicarAJugador_irCarcel(actual, todos)
          
        when Civitas::TipoSorpresa::IRCASILLA
          aplicarAJugador_irACasilla(actual, todos)
          
        when Civitas::TipoSorpresa::PAGARCOBRAR
          aplicarAJugador_pagarCobrar(actual, todos)
          
        when Civitas::TipoSorpresa::PORCASAHOTEL
          aplicarAJugador_porCasaHotel(actual, todos)
          
        when Civitas::TipoSorpresa::PORJUGADOR
          aplicarAJugador_porJugador(actual, todos)
          
        when Civitas::TipoSorpresa::SALIRCARCEL
          aplicarAJugador_salirCarcel(actual, todos)
          
        end
      end
    end
    

    def toString
      str = " Sorpresa: #{@texto}. >> Valor: #{@valor}"
    end

    
    private # ---------------------------------------------------------------- #

    def init()
      @valor = -1
      @texto = ""
      @mazo = nil
      @tablero = nil
    end
    
    def informe (actual, todos)
      if jugadorCorrecto(actual, todos)
        Diario.instance.ocurre_evento("\n ¡Sorpresa! #{@texto} Se aplica #{@sorpresa} al jugador #{todos[actual].nombre}\n")
      end
    end

    def aplicarAJugador_irCarcel (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end

    def aplicarAJugador_irACasilla(actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)

        casilla = todos[actual].numCasillaActual
        tirada = @tablero.calcularTirada(casilla, @valor)
        nuevaPos = @tablero.nuevaPosicion(casilla, tirada)

        todos[actual].moverACasilla(nuevaPos)
      end
    end

    def aplicarAJugador_porJugador (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        _sorpresa = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, @valor * -1, "")

        i = 0
        while i < todos.lenght
          if i != actual
            todos[i].paga(_sorpresa.valor)
            i += 1
          end

          _sorpresaActual = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, @valor * (todos.lenght - 1), "")  # ?????
          todos[actual].recibe(_sorpresaActual.valor)

        end
      end
    end

    def aplicarAJugador_pagarCobrar (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor)
      end
    end

    def aplicarAJugador_salirCarcel (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)

        tienen_salvoconducto = false
        i = 0
        while i < todos.length && !tienen_salvoconducto
          tienen_salvoconducto = todos[i].tieneSalvoconducto
          i += 1
        end

        if !tienen_salvoconducto
          todos[actual].obtenerSalvoconducto(this)
          salirDelMazo()
        end

      end
    end

    def aplicarAJugador_porCasaHotel (actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].modificarSaldo(@valor * todos[actual].cantidadCasasHoteles)
      end
    end

    def self. salirDelMazo
      if sorpresa == TipoSorpresa::SALIRCARCEL
        mazo.inhabilitarCartaEspecial(this)
      end
    end

    def self. usada
      if sorpresa == TipoSorpresa::SALIRCARCEL
        mazo.habilitarCartaEspecial(this)
      end
    end

  end
end