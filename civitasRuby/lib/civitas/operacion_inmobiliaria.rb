#encoding:utf-8

module Civitas
  class OperacionInmobiliaria
    
    def initialize(ip,gest)
      @numPropiedad = ip
      @gestion = gest
    end
    
    attr_reader :gestion, :numPropiedad
  end
end
