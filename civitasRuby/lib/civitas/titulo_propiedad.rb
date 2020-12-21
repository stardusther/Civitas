#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia Glez Dávila
         Grupo B3
=end

module Civitas

class TituloPropiedad  
  
  attr_reader :hipotecado, :nombre, :propietario,
              :precioCompra, :precioEdificar,
              :numCasas, :numHoteles
  
  def initialize (nombre, ab, fr, hb, pc, pe)
    @nombre = nombre
    @alquilerBase = ab
    @hipotecaBase = hb
    @factorRevalorizacion = fr
    @precioCompra = pc
    @precioEdificar = pe
    
    @hipotecado = false
    @propietario = nil
    @numCasas = 0
    @numHoteles = 0
    @factorInteresesHipoteca = 1.1
    @importeHipoteca = 0
  end

  ## 
  # Actualiza al propietario actual por el jugador dado
  def actualizaPropietarioPorConversion (jugador)
    #@propietario = jugador.clone()
    @propietario = jugador
  end
  
  ##
  # Cancela la hipoteca al jugador si este es el propietario del
  # titulo y este está hipotecado. 
  def cancelarHipoteca (jugador)
    result = false
    
    if @hipotecado && esEsteElPropietario(jugador)
      jugador.paga(getImporteCancelarHipoteca)
      @hipotecado = false
      result = true
      @importeHipoteca = 0
    end
    
    result
  end
  
  ##
  # Consultor del numero total de edificaciones del titulo
  def cantidadCasasHoteles() 
    @numCasas + @numHoteles
  end
  
  ## 
  # Elimina n casas del titulo, si el jugador dado es
  # el propietario y hay al menos tantas casas como
  # el parametro n dado.
  def derruirCasas (n, jugador)
    correcto = true
    
    if n<= @numCasas && esEsteElPropietario(jugador)
      @numCasas = @numCasas - n
    else
      correcto = false
    end
    
    correcto
  end
  
  ##
  # Si el titulo tiene un propietario distinto al jugador 
  # pasado por parametro, este paga el alquiler
  def tramitarAlquiler (jugador)
    if tienePropietario && !esEsteElPropietario(jugador)
      jugador.pagaAlquiler(getPrecioAlquiler)
      @propietario.recibe(getPrecioAlquiler)
    end
  end
  
  ##
  # Vende el titulo, que pasa a no tener propietario
  # solo si el jugador dado es el propietario y no está hipotecado
  def vender (jugador)
    vendido = false
    
    if esEsteElPropietario(jugador) && !hipotecado
      @propietario.recibe(getPrecioVenta)
      
      @numCasas = 0
      @numHoteles = 0
      @propietario = nil  
      
      vendido = true
    end
    
    vendido
  end
  
  ##
  # Si el titulo no tiene propietario, el jugador compra el titulo
  def comprar (jugador)
    result = false
    
    if !tienePropietario
      @propietario = jugador
      result = true
      jugador.paga(@precioCompra)
    end
    
    result
  end
  
  ##
  # Añade un hotel a las edificaciones del titulo,
  # si el jugador dado es el propietario
  def construirHotel (jugador)
    result = false
    if esEsteElPropietario(jugador)
      jugador.paga(@precioEdificar)
      @numHoteles = @numHoteles + 1
      result = true
    end
    result
  end
  
  ##
  # Añade una casa a las edificaciones del titulo,
  # si el jugador dado es el propietario
  def construirCasa(jugador)
    result = false
    if esEsteElPropietario(jugador)
      jugador.paga(@precioEdificar)
      @numCasas = @numCasas + 1
      result = true
    end
    result
  end
  
  ##
  # Hipoteca la propiedad. El importe es calculado según 
  # las reglas (pag 3 pdf CivitasElJuego.pdf)
  def hipotecar(jugador)
    salida = false
    hipoteca = @hipotecaBase * (1+@numCasas*0.5)+(@numHoteles*2.5)
    if !@hipotecado && esEsteElPropietario(jugador)
      jugador.recibe(hipoteca)
      @importeHipoteca = hipoteca
      @hipotecado = true
      salida = true
    end
    salida
  end
  
  ##
  # Cálculo según las reglas (pag 3 pdf CivitasElJuego.pdf)
  # Si se ha hipotecado, el importe a pagar es 0 
  def getImporteCancelarHipoteca
      @factorInteresesHipoteca * @importeHipoteca  
  end
  
  ##
  # Consultor del precio del alquiler del titulo
  # Si el propitario esta encarcelado o el tit. hipotecado,
  # no se aplica (paga 0)
  def getPrecioAlquiler
    if @hipotecado || @encarcelado
      precio = 0
    else
      precio = @alquilerBase * (1+(@numCasas*0.5))+(@numHoteles*2.5)
    end
  end
  
  ##
  # Consultor que devuelve el precio de venta del titulo.
  # Calculo según las reglas del juego dadas.
  def getPrecioVenta
    @precioCompra + @precioEdificar*(@numCasas+(5*@numHoteles))*@factorRevalorizacion
  end
  
  ##
  # Consultor que devuelve si el propitario del titulo esta 
  # encarcelado. Si no tiene propietario devuelve false.
  def propietarioEncarcelado
    encarcelado = tienePropietario && @propietario.isEncarcelado
  end
  
  def to_s
    tab = "        × "
    str = "#{@nombre}
        × Alquiler: #{@alquilerBase}
        × Hipoteca base: #{@hipotecaBase}
        × Factor de revalorizacion: #{@factorRevalorizacion}
        × Factor intereses hipoteca: #{@factorInteresesHipoteca}
        × Precio compra: #{@precioCompra}
        × Precio edificar: #{@precioEdificar}
        × Num. casas: #{@numCasas}
        × Num. hoteles: #{@numHoteles}
        × Hipotecado: #{@hipotecado}\n"
    
    if @hipotecado
      str = str + "#{tab}Importe de la hipoteca: #{@importeHipoteca}\n"
    end
    
    if (@propietario)
      str = str + "#{tab}Propietario: #{@propietario.nombre}\n"
    else
      str = str + "#{tab}Titulo vacío.\n"
    end
    
    str
  end
  
  ##
  # Consultor que devuelve true si el titulo tiene propietario
  def tienePropietario
    @propietario != nil
  end
  
  private # ------------------------------------------------------------------ #
  
  ##
  # Consultor que devuelve si el jugador dado es el propietario del titulo
  def esEsteElPropietario (jugador)
    @propietario == jugador
  end
  
end

end
