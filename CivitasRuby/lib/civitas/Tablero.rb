=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

class Tablero

  attr_reader :numCasillaCarcel

  def initialize (indice)
    if (indice >=1)
      @numCasillaCarcel = indice
    else
      @numCasillaCarcel = 1;
    end

    @casillas = []                            # Creamos vector de casillas
    @casillas.push(Casilla.new("Salida"))     # Se añade la salida al inicio

    @porSalida = 0
    @tieneJuez = false
  end

  def getPorSalida ()
    devuelve = @porSalida

    if @porSalida > 0
      @porSalida -= 1
    end

    devuelve
  end

  def añadeCasilla (casilla)
    añadeCarcel()
    @casillas.push(casilla)
    añadeCarcel()
  end

  def añadeJuez ()
    if !@tieneJuez
      casillaJuez = Casilla.new("Juez")
      añadeCasilla (casillaJuez)
      @tieneJuez = true
    end
  end

  def getCasilla (numCasilla)
    if (correcto(numCasilla))
      return @casillas[numCasilla]
    else
      return nil
    end
  end

  def nuevaPosicion (actual, tirada)
    posicion = -1

    if (correcto())
      posicion = (actual + tirada) % casillas.length

      if (posicion != actual + tirada)
        @porSalida += 1
      end
    end

    posicion
  end

  def calcularTirada (origen, destino)
    result = destino - origen

    if result<0
      result += casillas.length
    end
  end

  private
  def correcto()
    if @casillas.length > @numCasillaCarcel && @tieneJuez
      correct = true
    else
      correct = false
    end
  end

  def correcto(numCasilla)
    if (correct && (numCasilla<@casillas.length))
      correct = true
    else
      correct = false
    end
  end

  def añadeCarcel ()
    if @casillas.length == @numCasillaCarcel
      carcel = Casilla.new("Cárcel")
      casillas.push(carcel)
    end
  end

end
