#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class TituloPropiedadSocial < TituloPropiedad
  
  @factorInteresesHipoteca = 1.05
  
  def initialize(nombre, ab, fr, hb, pc, pe, mc, mh, propietario)
    super(nombre, ab, fr, hb, pc, pe)
    
    @maxCasasSociales = mc
    @maxHotelesSociales = mh
    @propietario = propietario
  end
  
  def hacermeSocial (jugador)
    
  end
  
  def to_s
    str = super
    str = str + " >> Social << \n"
  end
  
  
  # -----------------------------------------------
  
  def self.factorInteresesHipoteca
    @factorInteresesHipoteca
  end
  
  def factorInteresesHipoteca
    TituloPropiedadSocial.factorInteresesHipoteca
  end
  
end

end
