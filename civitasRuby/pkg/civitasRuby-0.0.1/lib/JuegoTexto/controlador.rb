#encoding:utf-8
=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative "../civitas/civitas_juego.rb"
require_relative "./vista_textual"
require_relative '../civitas/Casilla'
require_relative '../civitas/operacion_inmobiliaria'
require_relative '../civitas/gestiones_inmobiliarias'
require_relative '../civitas/salidas_carcel'

module Civitas
  class Controlador
    
    public # -----------------------------------------------------------
    
    def initialize(_juego, _vista)
      @juego = _juego
      @vista = _vista
    end
    
    def juega
      final = false
      
      puts "\n"
      @vista.setCivitasJuego(@juego)
      
      while (!final)
        @vista.actualizarVista
        @vista.pausa

        operacion = @juego.siguientePaso
        @vista.mostrarSiguienteOperacion(operacion)
          
        if operacion != Civitas::Operaciones_juego::PASAR_TURNO
          @vista.mostrarEventos
        end
          
        if @juego.finalDelJuego
          final = true
          #rank = Array.new(@juego.ranking.size) # Creamos el array

          rank = @juego.ranking         

          puts "\n ---------------------------- "
          puts   "     RANKING DE JUGADORES    "
          puts   " ----------------------------\n\n"
          
          for i in 0..1 # Mostramos los jugadores en orden
            puts rank[i].to_s
          end
          
          puts "\n ----- FIN DEL JUEGO -----\n"
          
        else
          case operacion                                    
            
          when Civitas::Operaciones_juego::COMPRAR
            respuesta = @vista.comprar
            if respuesta == Civitas::Respuestas::SI
              @juego.comprar
            end
            @juego.siguientePasoCompletado(operacion)
                  
          when Civitas::Operaciones_juego::GESTIONAR   
            @vista.gestionar
            
            #lista = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL, GestionesInmobiliarias::TERMINAR]
            gest = Civitas::GestionesInmobiliarias::Lista_Gestiones[@vista.iGestion]
            #gest = lista[@vista.iGestion]
            ip = @vista.iPropiedad
                  
            operacionInm = OperacionInmobiliaria.new(ip,gest)
                  
            case operacionInm.gestion
            when Civitas::GestionesInmobiliarias::VENDER
              @juego.vender(ip)
              
            when Civitas::GestionesInmobiliarias::HIPOTECAR
              @juego.hipotecar(ip)
              
            when Civitas::GestionesInmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelarHipoteca(ip)
              
            when Civitas::GestionesInmobiliarias::CONSTRUIR_CASA
              @juego.construirCasa(ip)
              
            when Civitas::GestionesInmobiliarias::CONSTRUIR_HOTEL
              @juego.construirHotel(ip)
              
            when Civitas::GestionesInmobiliarias::TERMINAR
              @juego.siguientePasoCompletado(operacion)
            end
                  
          when Civitas::Operaciones_juego::SALIR_CARCEL
            salida = @vista.salirCarcel
            
            if salida == Civitas::SalidasCarcel::PAGANDO
              @juego.salirCarcelPagando
            else
              @juego.salirCarcelTirando 
            end
                  
            @juego.siguientePasoCompletado(operacion)
            
          end
        
        end
      end
    
    end
  end
end

