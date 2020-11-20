=begin
Authors: Esther García Gallego
         Yesenia Glez Dávila
         Grupo B3
=end

class TituloPropiedad  
  
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

end
