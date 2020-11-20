=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

class Sorpresa
  
  attr_reader :valor
  
  #Atributos: tipo, valor, tablero, texto(nombre), mazo

  # Constructores
  def self. newIrCarcel (tipo, tab)
    init()
    new(tipo, tab, -1, "", nil)
  end

  def self. newIrCasilla (tipo, tab, valor, txt)
    init()
    new(tipo, tab, valor, txt, nil)
  end

  def self. newEvitaCarcel (tipo, m)
    init()
    new(tipo, nil, -1, "", m)
  end

  def self. newSorpresas (tipo, val, txt)  
    init()
    new(tipo, nil, val, txt, nil)
  end
  
  
  def jugadorCorrecto (actual, todos)
    correcto = false
    if actual >= 0 && actual < todos.lenght
      correcto = true
    end
    correcto
  end
  
  
  def aplicarAJugador(actual, todos)
    if jugadorCorrecto(actual, todos)
      
      case @sorpresa
        
      when TipoSorpresa::IRCARCEL
        aplicarAJugador_irCarcel(actual, todos)
        
      when TipoSorpresa::IRCASILLA
        aplicarAJugador_irCarcel(actual, todos)
        
      when TipoSorpresa::PAGARCOBRAR
        aplicarAJugador_pagarCobrar(actual, todos)
        
      when TipoSorpresa::PORCASAHOTEL
        aplicarAJugador_porCasaHotel(actual, todos)
        
      when TipoSorpresa::PORJUGADOR
        aplicarAJugador_porJugador(actual, todos)
        
      when TipoSorpresa::SALIRCARCEL
        aplicarAJugador_salirCarcel(actual, todos)
      end
      
    end
  end
  
  
  def salirDelMazo
    if sorpresa == TipoSorpresa::SALIRCARCEL
      mazo.inhabilitarCartaEspecial(self)
    end
  end

  
  def usada
    if sorpresa == TipoSorpresa::SALIRCARCEL
      mazo.habilitarCartaEspecial(self)
    end
  end
  

  def toString
    str = " Sorpresa: #{@texto}. >> Valor: #{@valor}"
  end
  

  # ------------------------------ Privados ---------------------------------- #
  
  
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
  
  
  def aplicarAJugador_irACasilla(actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)

      casilla = todos[actual].getNumCasillaActual
      tirada = @tablero.calcularTirada(casilla, @valor)
      nuevaPos = @tablero.nuevaPosicion(casilla, tirada)

      todos[actual].moverACasilla(nuevaPos)
      
      @tablero.getCasilla(@valor).recibeJugador(actual,todos)
    end
  end
  

  def aplicarAJugador_irCarcel (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@tablero.getCarcel)
    end
  end

  
  def aplicarAJugador_pagarCobrar (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].modificarSaldo(@valor)
    end
  end
  
  
  def aplicarAJugador_porCasaHotel (actual, todos)
    if jugadorCorrecto(actual, todos)
      informe(actual, todos)
      todos[actual].modificarSaldo(@valor * todos[actual].cantidadCasasHoteles)
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
      end

      _sorpresaActual = Sorpresa.new(TipoSorpresa::PAGARCOBRAR, @valor * (todos.lenght - 1), "")  # ?????
      todos[actual].recibe(_sorpresaActual.valor)

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
        todos[actual].obtenerSalvoconducto(self)
        salirDelMazo
      end

    end
  end

end
