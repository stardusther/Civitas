=begin
Authors: Esther García Gallego
         Yesenia Glez Dávila
         Grupo B3
=end

module Civitas

class TituloPropiedad  
  
  attr_reader :precioCompra, :hipotecado, :hipotecaBase, :nombre, :numCasas, :numHoteles,
              :precioEdificar, :propietario
            
    
  @nombre
  
  @alquilerBase
  @hipotecaBase
  @factorRevalorizacion
  @precioCompra
  @precioEdificar
  
  @hipotecado
  
  @numCasas
  @numHoteles
  
  @propietario
  
  public # --------------------------------------------------
  
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
  end

  def actualizaPropietarioPorConversion (jugador)
    #@propietario = jugador.clone()
    @propietario = jugador
  end
  
  def cancelarHipoteca (jugador)
    result = false
    
    if @hipotecado && esEsteElPropietario(jugador)
      jugador.paga(getImporteCancelarHipoteca)
      @hipotecado = false
      result = true
    end
    
    result
  end
  
  def cantidadCasasHoteles() 
    @numCasas + @numHoteles
  end
  
  def derruirCasas (n, jugador)
    correcto = true
    
    if n>= @numCasas && esEsteElPropietario(jugador)
      @numCasas = @numCasas - n
    else
      correcto = false
    end
    
    correcto
  end
  
  def tramitarAlquiler (jugador)
    if tienePropietario && !esEsteElPropietario(jugador)
      jugador.pagaAlquiler(getPrecioAlquiler)
      @propietario.recibe(getPrecioAlquiler)
    end
  end
  
  def vender (jugador)
    vendido = false
    if tienePropietario && !@hipotecado
      @propietario.recibe(@precioCompra)
      @numCasas = 0
      @numHoteles = 0
      # Desvincular propietario del jugador
      @propietario = nil  # warning
      vendido = true
    end
    vendido
  end
  
  def comprar (jugador)
    result = false
    
    if !tienePropietario
      @propietario = jugador
      result = true
      jugador.paga(@precioCompra)
    end
    
    result
  end
  
  def construirHotel (jugador)
    result = false
    if esEsteElPropietario(jugador)
      jugador.paga(@precioEdificar)
      @numHoteles = @numHoteles + 1
      result = true
    end
    result
  end
  
  def construirCasa(jugador)
    result = false
    if esEsteElPropietario(jugador)
      jugador.paga(@precioEdificar)
      @numCasas = @numCasas + 1
      result = true
    end
    result
  end
  
  def hipotecar(jugador)
    salida = false
    if !@hipotecado && esEsteElPropietario(jugador)
      jugador.recibe(@hipotecaBase)
      @hipotecado = true
      salida = true
    end
    salida
  end
  
  def getImporteCancelarHipoteca
    @factorInteresesHipoteca * @hipotecaBase
  end
  
  def getPrecioAlquiler
    if @hipotecado || @encarcelado
      precio = 0
    else
      precio = @alquilerBase * (1+(@numCasas*0.5))+(@numHoteles*2.5)
    end
  end
  
  def getPrecioVenta
    @precioCompra + @precioEdificar*(@numCasas+(5*@numHoteles))*@factorRevalorizacion
  end
  
  def propietarioEncarcelado
    encarcelado = tienePropietario && @propietario.isEncarcelado
  end
  
  def toString
    str = "#{@nombre} \n " # ... 
  end
  
  def tienePropietario
    @propietario != nil
  end
  
  private # --------------------------------------------------------
  
  def esEsteElPropietario (jugador)
    @propietario == jugador
  end
  
  
end

end
