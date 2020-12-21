#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia Glez Dávila
         Grupo B3
=end

module Civitas

class TituloPropiedad  
  
  attr_reader :precioCompra, :hipotecado, :hipotecaBase, :nombre, :numCasas, :numHoteles,
              :precioEdificar, :propietario
            
  
  public
  
  def getImporteHacermeSostenible
    importe = getPrecioVenta*(calcularPorcentajeInversionPorSostenibilidad/100)
  end

  protected
  def calcularPorcentajeInversionPorSostenibilidad
    if @numCasas == 0 && @numHoteles == 0
      porcentaje = 5
    else
      porcentaje = 2
    end
    
    porcentaje
  end
  
  public
  
  def hacermeSostenible (jugador)
    
    # 1
    propiedadSostenible = nil
    
    # 2
    esElPropietario = esEsteElPropietario(jugador)
    
    # 3 y 4
    if esElPropietario
      percent = calcularPorcentajeInversionPorSostenibilidad
      importe = getImporteHacermeSostenible
      jugador.paga(importe)
      nuevoNombre = "#{@nombre} >> Sostenible <<"
      propiedadSostenible = TituloPropiedadSostenible.new(nuevoNombre, @alquilerBase, @hipotecaBase, @factorRevalorizacion, @precioCompra, @precioEdificar, percent)
    end
    
    propiedadSostenible
  end
  
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
      @importeHipoteca = 0
    end
    
    result
  end
  
  def cantidadCasasHoteles() 
    @numCasas + @numHoteles
  end
  
  def derruirCasas (n, jugador)
    correcto = true
    
    if n<= @numCasas && esEsteElPropietario(jugador)
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
      @propietario = nil  
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
  
  # Cálculo según las reglas (pag 3 pdf CivitasElJuego.pdf)
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
  
  # Cálculo según las reglas (pag 3 pdf CivitasElJuego.pdf)
  # Si se ha hipotecado, importeHipoteca != nil, si no se da el
  # caso, el importe a pagar 0 porque no se ha hipotecado
  def getImporteCancelarHipoteca
      @factorInteresesHipoteca * @importeHipoteca  
  end
  
  def getPrecioAlquiler
    if @hipotecado || @encarcelado
      precio = 0
    else
      precio = @alquilerBase * (1+(@numCasas*0.5))+(@numHoteles*2.5)
    end
  end
  
  protected
  def getPrecioVenta
    @precioCompra + @precioEdificar*(@numCasas+(5*@numHoteles))*@factorRevalorizacion
  end
  
  public
  def propietarioEncarcelado
    encarcelado = tienePropietario && @propietario.isEncarcelado
  end
  
  def to_s
    tab = "      × "
    str = "#{@nombre}
        × Alquiler: #{@alquilerBase}
        × Hipoteca base: #{@hipotecaBase}
        × Factor de revalorizacion: #{@factorRevalorizacion}
        × Factor intereses hipoteca: #{@factorInteresesHipoteca}
        × Precio compra: #{@precioCompra}
        × Precio edificar: #{@precioEdificar}
        × Num. casas: #{@numCasas}
        × Num. hoteles: #{@numHoteles}
        × Hipotecado: #{@hipotecado}"
    
    if @hipotecado
      str = str + "    × Importe de la hipoteca: #{@importeHipoteca}"
    end
    
    if (@propietario)
      str = str + "      >> Propietario: #{@propietario.nombre}\n"
    else
      str = str + " >> Titulo vacío. \n"
    end
    
    str
  end
  
  def tienePropietario
    @propietario != nil
  end
  
  private # ------------------------------------------------------------------ #
  
  def esEsteElPropietario (jugador)
    @propietario == jugador
  end
  
  
end

end
