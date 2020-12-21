#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class TituloPropiedadSostenible < TituloPropiedad
  
  
  def initialize (nombre, ab, fr, hb, pc, pe, porcentaje)
    super(nombre, ab, fr, hb, pc, pe)
    @porcentajeInversion = porcentaje*10
  end
  
  #def hacermeSostenible(jugador)
    
  #end
  
  def to_s
    str = super
    str = str + "\n >> Soy sostenible << \n"
  end
  
  protected
  
  def getPrecioVenta
    precio = super
    precio = precio + (precio*(@porcentajeInversion/100))
  end
  
end
end