#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas

class TituloExamen < TituloPropiedad
  
  def initialize 
    
  end
  
  def initialize (nombre, ab, fr, hb, pc, pe)
    super(nombre, ab, fr, hb, pc, pe)
  end
  
  def construirCasa(jugador)
    result = super(jugador)
    if result # SUmar otra vez el dinero que ha costado al sañdo
      jugador.paga(@precioEdificar)
    end
    result
  end
  
  
end

end
