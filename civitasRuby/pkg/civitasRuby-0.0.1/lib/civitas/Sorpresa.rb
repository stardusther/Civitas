=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end


class Sorpresa

  attr_reader :valor

  # Constructores
  def self. constructorTab (tipo, tab)
    init()
    new(tipo, tab, -1, "", nil)
  end

  def self. constructorTabValTxt (tipo, tab, val, txt)
    init()
    new(tipo, tab, val, txt, nil)
  end

  def self. constructorMazo (tipo, m)
    init()
    new(tipo, nil, -1, "", m)
  end

  def self. constructorValTxt (tipo, val, txt)
    init()
    new(tipo, nil, val, txt, nil)
  end

  # Metodos
  def jugadorCorrecto (actual, todos)
    correcto = true

    if actual < todos.lenght
      correcto = false
    end
  end

  def toString
    str = " Sorpresa: #{@texto}. >> Valor: #{@valor}"
  end

  # -------------------------------------------------------------------------- #
  # ------------------------------ Privados ---------------------------------- #
  # -------------------------------------------------------------------------- #
  private 

  def initialize (tipo, tab, val, txt, m)

    @sorpresa = tipo
    @tablero = tab
    @valor = valor
    @texto = txt
    @mazo = m
  end

  def init
    @valor = -1
    @texto = ""
    @mazo = nil
    @tablero = nil
  end

  def informe (actual, todos)
    if jugadorCorrecto(actual, todos)
      Diario.instance.ocurreEvento("Se aplica sorpresa al jugador " + todos[actual].getNombre)
    end
  end

  def self. aplicarJugador (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
    end
  end

  def aplicarAJugador_irCarcel (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@tablero.getCarcel)
    end
  end

  def aplicarAJugador_irACasilla
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)

      casilla = todos[actual].getNumCasillaActual
      tirada = @tablero.calcularTirada(casilla, valor)
      nuevaPos = @tablero.nuevaPosicion(casilla, tirada)

      todos[actual].moverACasilla(nuevaPos)
      #falta indicar a la casilla que está en la posición el valor de la sorpresa que reciba al jugador
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
  end #es posible que sobre un end
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
