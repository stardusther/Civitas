#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class JugadorEspeculador < Jugador
  
  attr_reader :nombre, :saldo
  
  @@FactorEspeculador = 2 # Atributo de clase
    
  @SaldoInicial = 7500
  @CasasMax = 8
  @HotelesMax = 8
  
  def initialize (fianza)
    @fianza = fianza
  end
  
  def self.nuevoEspeculador (jugador, fianza) 
    nuevo = JugadorEspeculador.new(fianza)
    nuevo.copia(jugador)
    nuevo.actualizaPropiedades
    nuevo
  end
  
  def actualizaPropiedades 
    @propiedades.each do |propiedad| 
      propiedad.actualizaPropietarioPorConversion(self)
    end
  end
  
  def pagaImpuesto(cantidad)
    if (isEncarcelado)
      false
    else
      paga(cantidad/@@FactorEspeculador)
    end
  end
  
  def to_s
     str = " >> Jugador #{@nombre}. #{@saldo} €. Propiedades: #{@propiedades.length}. Edificaciones #{cantidadCasasHoteles}. "
    str = str + "\n    Casilla actual: #{@numCasillaActual}."
    str = str + "\n    Especulador."
    
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
  end
  
  private # ------------------------------------------------------------------ #
  
  def self.casasMax
    @CasasMax
  end
    
  def self.hotelesMax
    @HotelesMax
  end
  
  def casasMax
    JugadorEspeculador.casasMax
  end
  
  def hotelesMax
    JugadorEspeculador.hotelesMax
  end
  
  protected # ---------------------------------------------------------------- #
  
   def debeSerEncarcelado 
    carcel = false
    
      if !isEncarcelado
        
        if !tieneSalvoconducto       # 1. Si no tiene salvoconducto
          
          if puedoGastar(@fianza)    #     1.2 Puede pagar fianza y no es encarcelado
            paga(@fianza)
            Diario.instance.ocurre_evento ("El jugador especulador #{@nombre} se ha librado de la carcel pagando la fianza (#{@fianza}€)")
          else                       #     1.3. Se encarcela
            carcel = true
          end
          
         else                        # 2. Pierde el salvoconducto y no es encarcelado
          perderSalvoconducto()
          Diario.instance.ocurre_evento ("El jugador #{@nombre} se ha librado de la cárcel por tener un salvoconducto")
        end
      end
      
    carcel
  end
  
end

end
