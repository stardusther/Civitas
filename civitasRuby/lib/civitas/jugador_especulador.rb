#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class JugadorEspeculador < Jugador
  
  @@FactorEspeculador = 2 # Atributo de clase
    
  
  def initialize (jugador, fianza)
    super(jugador.nombre)
    @fianza = fianza
    nuevoEspeculador(jugador)
  end
  
  
  def nuevoEspeculador (jugador) 
    @saldo = jugador.saldo
    @encarcelado = jugador.isEncarcelado
    @puedeComprar = jugador.puedeComprar
    @numCasillaActual = jugador.numCasillaActual
    
    @propiedades = jugador.propiedades
    
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
  
  def casasMax
    @CasasMax*@@FactorEspeculador
  end
  
  def hotelesMax
    @HotelesMax*@@FactorEspeculador
  end
  
  protected # ---------------------------------------------------------------- #
  
   def debeSerEncarcelado ()
    carcel = false
    
      if !isEncarcelado()
        
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
