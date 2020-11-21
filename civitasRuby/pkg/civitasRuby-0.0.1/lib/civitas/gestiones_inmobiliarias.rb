=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  module GestionesInmobiliarias
    attr_accessor :lista_Gestiones
    
    VENDER = :vender
    HIPOTECAR = :hipotecar
    CANCELAR_HIPOTECA = :cancelar_hipoteca
    CONSTRUIR_CASA = :construir_casa
    CONSTRUIR_HOTEL = :construir_hotel
    TERMINAR = :terminar
    
    #lista = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL, GestionesInmobiliarias::TERMINAR]
    
    lista_Gestiones = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL, GestionesInmobiliarias::TERMINAR]
  
    #enum status: {vender: 0, HIPOTECAR:1,CANCELAR_HIPOTECA:2,CONSTRUIR_CASA:3,CONSTRUIR_HOTEL:4,TERMINAR:5} 
  end
end

