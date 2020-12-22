#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

module Civitas
  module GestionesInmobiliarias
    VENDER = :vender
    HIPOTECAR = :hipotecar
    CANCELAR_HIPOTECA = :cancelar_hipoteca
    CONSTRUIR_CASA = :construir_casa
    CONSTRUIR_HOTEL = :construir_hotel
    TERMINAR = :terminar
    EXAMEN = :examen
    
    Lista_Gestiones = [GestionesInmobiliarias::VENDER, 
                       GestionesInmobiliarias::HIPOTECAR, 
                       GestionesInmobiliarias::CANCELAR_HIPOTECA, 
                       GestionesInmobiliarias::CONSTRUIR_CASA, 
                       GestionesInmobiliarias::CONSTRUIR_HOTEL, 
                       GestionesInmobiliarias::TERMINAR,
                       GestionesInmobiliarias::EXAMEN]

  end
end

