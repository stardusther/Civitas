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
    nuevoEspeculador(jugador, fianza)
  end
  
  
  def nuevoEspeculador (jugador, fianza) 
    @CasasMax = 8
    @HotelesMax = 8
    
    @saldo = jugador.saldo
    @encarcelado = jugador.isEncarcelado
    @puedeComprar = jugador.puedeComprar
    @numCasillaActual = jugador.numCasillaActual
    
    @propiedades = jugador.propiedades
    
    @propiedades.each do |propiedad| 
      propiedad.actualizaPropietarioPorConversion(self)
    end
  end
  
  def to_s
     str = " >> Jugador #{@nombre}. #{@saldo} €. Propiedades: #{@propiedades.length}. Edificaciones #{cantidadCasasHoteles}. "
    str = str + "\n    Casilla actual: #{@numCasillaActual}."
    str = str + "\n    Especulador"
    
    if @puedeComprar
      str = str + " Puede comprar."
    end
    if @encarcelado
      str = str + " Encarcelado."
    end
    if !@salvoconducto.nil?
      str = str + " Tiene salvoconducto."
    end
    
    puts str
  end
  
end

end
