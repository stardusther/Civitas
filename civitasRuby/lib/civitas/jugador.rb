#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative "Dado.rb" 
require_relative "Diario.rb"
require_relative "Sorpresa.rb"

module Civitas

class Jugador
  
  @@CasasPorHotel = 4
  @@PasoPorSalida = 1000
  @@PrecioLibertad = 200
  
  @SaldoInicial = 7500
  @CasasMax = 4
  @HotelesMax = 4
  
  attr_reader :numCasillaActual, :PrecioLibertad, :PasoPorSalida , 
              :encarcelado, :nombre, :puedeComprar, :propiedades, :saldo, 
              :salvoconducto, :PasoPorSalida, :numCasillaActual

  
  # Examen
  
  def hacerPropiedadSocial (ip)
    
    propiedadSocial = nil
    
   
    if !isEncarcelado && existeLaPropiedad(ip)
      
      # 2.1
      propiedad = @propiedades[ip]
      
      # 2.2
      propiedadSocial = @propiedades[ip].hacermeSocial(self)
      
      puts " =============== "
      puts propiedadSocial.inspect
      puts " =============== "
      
      if propiedadSocial
        
        # 2.3
        @propiedades[ip] = propiedadSocial
        
        # 2.4
        Diario.instance.ocurre_evento(" El jugador #{@nombre} tiene ahora la propiedad social #{propiedadSocial.nombre}. ")
        
      end
      
    end
    
    propiedadSocial
    
  end
  
  
  # Revisar para quitarlo (creo que no se usa, revisar)
  def CasasPorHotel
    @@CasasPorHotel
  end
  
  def initialize (nombre)
    @SaldoInicial = 7500 # Arriba la iniacializacion no funcaaAaAAaA
    
    @nombre = nombre
    @saldo = @SaldoInicial
    @puedeComprar = true
    @encarcelado = false
    @numCasillaActual = 0
    @salvoconducto = nil
    
    @propiedades = []
  end
  
  # Probar
  def self.new_por_copia (otro)
    jugador = Jugador.new(otro.nombre)
    jugador.copia(otro)
    jugador
  end
  
  def copia(otro)
    @nombre = otro.nombre
    @saldo = otro.saldo
    @encarcelado = otro.isEncarcelado
    @puedeComprar = otro.puedeComprar
    @numCasillaActual = otro.numCasillaActual
    @propiedades = otro.propiedades
  end
  
  def compare_to(otro)
    # si a < b --> -1 || si a = b --> 0 || si a > b --> 1 || si no son comparables --> nil
    @saldo<=>otro.saldo
  end
  
  
  def encarcelar (numCasillaCarcel)
    if debeSerEncarcelado
      moverACasilla (numCasillaCarcel)
      @encarcelado = true
      Diario.instance.ocurre_evento ("El jugador #{@nombre} ha sido encarcelado")
    end
    @encarcelado
  end
  
  def obtenerSalvoconducto (s)
    obtiene = !isEncarcelado()
    if obtiene
        salvoconducto = s
    end
    obtiene
  end
  
  
  def paga (cantidad) 
    modificarSaldo (cantidad * -1)
  end
  
  
  def pagaImpuesto (cantidad)
    if isEncarcelado()
      false
    else
      paga(cantidad)
    end
  end
  
  
  def pagaAlquiler (cantidad)
    if isEncarcelado
      false
    else
      paga (cantidad)
    end
  end
  
  
  def recibe (cantidad)
    if isEncarcelado()
      false
    else
      modificarSaldo (cantidad)  
    end
  end
  
  
  def modificarSaldo (cantidad)
    saldo_anterior = @saldo
    @saldo = @saldo + cantidad
    Diario.instance.ocurre_evento ("Se ha modificado el saldo de #{@nombre} (#{cantidad}, de #{saldo_anterior} a #{@saldo})")
    true
  end
  
  
  def moverACasilla (numCasilla)
    if (isEncarcelado())
      mover = false
    else
      @numCasillaActual = numCasilla
      @puedeComprar = false
      Diario.instance.ocurre_evento ("Se ha movido al jugador #{@nombre} a la casilla #{numCasilla}")
      mover = true
    end
  end
  
  
  def vender (ip)
    if !isEncarcelado() and existeLaPropiedad(ip)
      
      if (@propiedades[ip].vender(self))
        @propiedades.delete_at(ip)
        Diario.instance.ocurre_evento ("Se ha vendido la propiedad de la casilla #{ip}")
        true;
      else
        false
      end
      
    end
  end
  
  
  def puedeSalirCarcelPagando()
    puede = @saldo >= @@PrecioLibertad
  end
  
  
  def salirCarcelPagando()
    salir = false
    if (isEncarcelado() and puedeSalirCarcelPagando())
      paga(@@PrecioLibertad)
      @encarcelado = false
      
      Diario.instance.ocurre_evento("El jugador #{@nombre} ha pagado para salir de la cárcel");
      salir = true
    end
    salir
  end
  
  
  def salirCarcelTirando()
    if (Dado.instance.salgoDeLaCarcel())
      @encarcelado = false
      Diario.instance.ocurre_evento("El jugador #{@nombre} ha tirado y ha salido de la cárcel");
    end
    @encarcelado
  end
  
  
  def pasaPorSalida()
    modificarSaldo (@@PasoPorSalida)
  end
  
  
  def tieneAlgoQueGestionar()
    !@propiedades.empty?  
  end
  
  
  def cantidadCasasHoteles()
    casasHoteles=0
    
    for i in 0..(@propiedades.length-1)
      casasHoteles = casasHoteles + @propiedades[i].cantidadCasasHoteles()
    end
    
    casasHoteles    
  end
  
  
  def isEncarcelado()
    @encarcelado
  end
  
  
  def tieneSalvoconducto()
    !@salconducto.nil?
  end
  
  
  def puedeComprarCasilla()
    if isEncarcelado
      @puedeComprar = false
    else
      @puedeComprar = true
    end
  end
  
  
  def cancelarHipoteca (ip)
    result = false
    if !@encarcelado && existeLaPropiedad(ip)
      propiedad = @propiedades[ip]
      cantidad = propiedad.getImporteCancelarHipoteca
      puedoGastar = puedoGastar(cantidad)
      
      if (puedoGastar && propiedad.cancelarHipoteca(self))
        result = true
        Diario.instance.ocurre_evento("El jugador #{@nombre} cancela la hipoteca de la propiedad #{ip}")
      end
    end
    result
  end
  
  
  def comprar (titulo)
    result = false
    
    if !@encarcelado && @puedeComprar
      precio = titulo.precioCompra
      
      if (puedoGastar(precio))
        if (titulo.comprar(self))
          result = true
          @propiedades.push(titulo)
          Diario.instance.ocurre_evento("El jugador #{@nombre} compra la propiedad #{titulo.to_s}")
        end
        @puedeComprar = true
      end
    end
    result
  end
  
  
  def construirCasa (ip)
    result = false
    puedoEdificarCasa = false
    if !@encarcelado && existeLaPropiedad(ip) && puedoEdificarCasa(@propiedades[ip])
      result = @propiedades[ip].construirCasa(self)
    end
    result
  end
  
  
  def construirHotel (ip)
    result = false
    
    if !@encarcelado && existeLaPropiedad(ip)
      propiedad = @propiedades[ip]
      if puedoEdificarHotel (propiedad)
        result = propiedad.construirHotel(self)
        casasPorHotel = @@CasasPorHotel
        propiedad.derruirCasas(casasPorHotel, self)
        Diario.instance.ocurre_evento("El jugador #{@nombre} construye hotel en la propiedad #{ip}")
      end
    end
    
    result
  end
  
  
  def hipotecar (ip)
    result = false
    if !@encarcelado && existeLaPropiedad(ip)
      propiedad = @propiedades[ip]
      result = propiedad.hipotecar(self)
    end
    result
  end
  
  
  def enBancarrota()
    en_bancarrota = @saldo<0
  end
  
  
  def existeLaPropiedad (ip)
    existe = @propiedades.include?(@propiedades[ip])
  end
  
  
  def puedoEdificarCasa (propiedad)
    puedo = puedoGastar(propiedad.precioEdificar) && propiedad.numCasas<casasMax
  end
  
  
  def puedoEdificarHotel (propiedad)
    puedo = false
    precio = propiedad.precioEdificar
    
    if puedoGastar(precio) && propiedad.numHoteles<hotelesMax &&
       propiedad.numCasas>=@@CasasPorHotel
     puedo = true
    end
    
    puedo
  end

  
  def to_s()
    
    str = " >> Jugador #{@nombre}. #{@saldo} €. Propiedades: #{@propiedades.length}. Edificaciones #{cantidadCasasHoteles}. "
    str = str + "\n    Casilla actual: #{@numCasillaActual}."
    
    if @puedeComprar
      str = str + " Puede comprar."
    end
    if @encarcelado
      str = str + " Encarcelado."
    end
    if !@salvoconducto.nil?
      str = str + " Tiene salvoconducto."
    end
    
    str = str + "\n"
    
    str
    #puts str
  end
  
  
  private # ------------------------------------------------------------------ #
  
    def perderSalvoconducto()
      @salvoconducto.usada()
      @salvoconducto = nil
    end
    
    def puedoGastar (precio)
      !isEncarcelado and @saldo >= precio
    end
    
    def puedeSalirCarcelPagando()
      @saldo >= @@PrecioLibertad
    end
    
    def self.casasMax
      @CasasMax
    end
    
    def self.hotelesMax
      @HotelesMax
    end
    
    def casasMax
      Jugador.casasMax
    end

    def hotelesMax
      Jugador.hotelesMax
    end
    
  protected # ---------------------------------------------------------------- #
  
  def debeSerEncarcelado ()
    carcel = false
    
      if !isEncarcelado()
        if !tieneSalvoconducto
          carcel = true
        else
          perderSalvoconducto()
          Diario.instance.ocurre_evento ("El jugador #{@nombre} se ha librado de la cárcel por tener un salvoconducto")
        end
      end
      
    carcel
  end
  
end
end