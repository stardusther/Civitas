# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

class Jugador
  
  @@CasasMax = 4
  @@CasasPorHotel = 4
  @@HotelesMax = 4
  @@PasoPorSalida = 1000
  @@PrecioLibertad = 200
  @@SaldoInicial = 7500
  
  attr_reader :CasasMax, :CasasPorHotel, :HotelesMax , :PrecioLibertad, 
              :PasoPorSalida ,  :SaldoInicial 
     
  attr_accessor :encarcelado, :nombre , :numCasillaActual, :puedeComprar,
                :propiedades , :saldo , :salvoconducto 

  
  def initialize (nombre)
    @nombre = nombre
    @saldo = @@SaldoInicial
    @puedeComprar = true
    @encarcelado = false
    @numCasillaActual = 0
    @salvoconducto = nil
    
    @propiedades = []
  end
  
  def self. new_por_copia (otro)
    @nombre = otro.getNombre()
    @saldo = otro.getSaldo()
    @encarcelado = otro.isEncarcelado()
    @puedeComprar = otro.getPuedeComprar()
    @numCasillaActual = otro.getNumCasillaActual()
    
    @propiedades = otro.getPropiedades()
  end
  
  def compare_to(otro)
    # si a < b --> -1 || si a = b --> 0 || si a > b --> 1 || si no son comparables --> nil
    @saldo<=>otro.saldo
  end
  
  def debeSerEncarcelado ()
    carcel = false
    
      if !isEncarcelado()
        if !tieneSalvoConducto
          carcel = true
        else
          perderSalvoConducto()
          #Diario.instance.ocurreEvento ("El jugador se ha librado de la cárcel por tener un salvoconducto")
          Diario.instance.ocurreEvento ("E")
        end
      end
      
    carcel
  end
  
  def encarcelar (numCasillaCarcel)
    if debeSerEncarcelado()
      moverACasilla (numCasillaCarcel)
      @encarcelado = true
      #Diario.instance.ocurreEvento ("El jugador ha sido encarcelado")
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
    if isEncarcelado()
      fasel
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
    saldo += cantidad
    #Diario.instance.ocurreEvento ("Se ha modificado el saldo")
    true
  end
  
  
  def moverACasilla (numCasilla)
    if (isEncarcelado())
      mover = false
    else
      @numCasillaActual = numCasilla
      @puedeComprar = false
      #Diario.instance.ocurreEvento ("Se ha movido al jugador #{@nombre} a la casilla #{@numCasilla}")
      mover = true
    end
  end
  
  
  def vender (ip)
    
    if !isEncarcelado() and existeLaPropiedad(ip)
      
      if (@propiedades.get(ip).vender(this))
        @propiedades.delete_at(ip)
        #Diario.instance().ocurreEvento ("Se ha vendido la propiedad de la casilla #{ip}")
        true;
      else
        false
      end
      
    end
    
  end
  
  
  def puedeSalirCarcelPagando()
    getSaldo() >= getPrecioLibertad()
  end
  
  
  def salirCarcelPagando()
    if (isEncarcelado() and puedeSalirCarcelPagando())
      paga(getPrecioLibertad)
      @encarcelado = false
      
      #Diario.instance().ocurreEvento("El jugador ha pagado para salir de la cárcel");
      true
    else
      false
    end
  end
  
  
  def salirCarcelTirando()
    if (Dado.instance.salgoDeLaCarcel())
      @encarcelado = false
      #Diario.instance().ocurreEvento("El jugador ha tirado y ha salido de la cárcel");
    end
    @encarcelado
  end
  
  
  def pasaPorSalida()
    modificarSaldo (getPremioPasoPorSalida())
  end
  
  
  def tieneAlgoQueGestionar()
    !@propiedades.emtpy?  #diferencia entre empty y any, revisar
  end
  
  
  def getPremioPasoSalida()
    @@PasoPorSalida       # realmente no necesario?
  end
  
  
  def cantidadCasasHoteles()
    casasHoteles=0
    
    for i in 0..(@propiedades.length-1)
      casasHoteles = casasHoteles + @propiedades[i].cantidadCasasHoteles()
    end
    
    casasHoteles    # no hace falta??? (la ultima operaciona ha sido sumar a casasHoteles en el bucle
  end
  
  
  def isEncarcelado()
    @encarcelado
  end
  
  
  def tieneSalvoconducto()
    salvoconducto != nil
  end
  
  
  def puedeComprarCasilla()
    if !isEncarcelado
      @puedeComprar = false
    else
      @puedeComprar = true
    end
  end
  
  
  def enBancarrota()
    if (getSaldo()>=0)
      true
    else
      false
    end
  end
  
  
  def existeLaPropiedad (ip)
    #existe = @propiedades.include?(@propiedades[ip])
  end
  
  
  def puedoEdificarCasa (propiedad)
    puedo = @propiedades.contains (propiedad) && (propiedad.numCasas<@@CasasMax) && @puedeComprar && puedeGastar(propiedad.precioEdificar)
  end
  
  
  def puedoEdificarHotel (propiedad)
    puedo = @propiedades.contains(propiedad) and propiedad.numHoteles()<@@HotelesMax and propiedad.numCasas == @@CasasPorHotel and @puedeComprar and puedoGastar(propiedad.precioEdificar)
  end

  
  def to_s()
    puts "Jugador llamado #{@nombre}. >> Saldo: #{@saldo}. >> Num. de propiedades: #{@propiedades.length}. 
          >> Total casas y hoteles: #{cantidadCasasHoteles()}. >> Casilla actual: #{@numCasillaActual}.
          >> Puede comprar: #{@puedeComprar}. >> Encarcelado: #{@encarcelado}. >> Salvoconducto: #{@salvoconducto}"
  end
  
  # -------------------------------------------------------------------------- #
  # ------------------------------ Privados ---------------------------------- #
  # -------------------------------------------------------------------------- #
  
  
  private
    def perderSalvoconducto()
      @salvoconducto.usada()
      @salvoconducto = nil
    end
    
    def puedoGastar (precio)
      !isEncarcelado and getSaldo() >= precio
    end
    
    def puedeSalirCarcelPagando()
      getSaldo() >= getPrecioLibertad()
    end
  
end

jugador = Jugador.new ("Pepe")
puts jugador