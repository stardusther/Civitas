=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative ./civitas/TipoCasilla.rb
class Casilla

  attr_reader :nombre, :tituloPropiedad

  @@carcel                #Atributo de instancia

  def self. constructorNombre (n)
    init()
    new(n, nil, 0, 0, nil)
  end

  def self. constructorTituloPropiedad (titulo)
    init()
    new(titulo.nombre, titulo, 0, 0, nil)
  end

  def self. constructorCantidadNombre (cantidad, n)
    init()
    new(n, nil, cantidad, 0, nil)
  end

  def self. constructorCarcelNombre (numCasillaCarcel, n)
    init()
    new(n, nil, 0, numCasillaCarcel, nil)
  end

  def self. constructorMazoNombre (n)
    init()
    new(n, nil, 0, 0, m)
  end

  def jugadorCorrecto(actual, todos)
  end

  def self. recibeJugador(actual, todos)
    #no entiendo qué hace este método
  end

  def toString
    str = ">> Casilla " + @nombre
    #Falta poner el resto de valores según el tipo de casilla (importe etc)
  end


  private #-------------------------------------------------------------------
  def initialize(n, titulo, cantidad, numCasillaCarcel, m)
    @nombre = n
    @importe = cantidad
    @@carcel = numCasillaCarcel
    @tituloPropiedad = titulo
    @tipo = Civitas::TipoCasilla::CALLE
    @mazo = m
  end

  def init
    @nombre = ""
    @importe = -1
    @@carcel = -1

    @tituloPropiedad = nil
    @sorpresa = nil
    @mazo = nil
  end

  def informe
    str = "El jugador " + todos[actual] + " ha caido en una casilla.\n" + toString()
    Diario.getInstance.ocurreEvento(str)
  end

  def recibeJugador_calle(actual, todos)
    if(jugadorCorrecto(actual, todos))
      informe(actual,todos)
      todos[actual].pagaAlquiler(tituloPropiedad.getPrecioAlquiler())
    end
  end

  def recibeJugador_impuesto(actual, todos)
    if (jugadorCorrecto(actual, todos))
      informe(actual, todos)
      todos[actual].pagaImpuesto(importe)
    end
  end

  def recibeJugador_juez(actual, todos)
    if(jugadorCorrecto(actual, todos))
      informe(actual, todos)
      todos[actual].encarcelar(carcel)
    end
  end

  def recibeJugador_sorpresa(actual, todos)
    if (jugadorCorrecto(actual, todos))
      informe(actual, todos)
      sorpresa.aplicarAJugador(actual, todos)
    end
  end
  
  def recibeJugador(iactual, todos)
    case @tipo
    when Civitas::TipoCasilla::CALLE
      recibeJugador_calle(iactual, todos)
    when Civitas::TipoCasilla::IMPUESTO
      recibeJugador_impuesto(iactual, todos)
    when Civitas::TipoCasilla::JUEZ
      recibeJugador_juez(iactual, todos)
    when Civitas::TipoCasilla::SORPRESA
      recibeJugador_sorpresa(iactual, todos)
    else
      informe(iactual, todos)
    end
    
  end
  
  
end
