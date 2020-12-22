#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative "../civitas/civitas_juego.rb"
require_relative "./vista_textual"
require_relative '../civitas/casilla'
require_relative '../civitas/operacion_inmobiliaria'
require_relative '../civitas/gestiones_inmobiliarias'
require_relative '../civitas/salidas_carcel'

module Civitas
  class Controlador
    
    def initialize(_juego, _vista)
      @juego = _juego
      @vista = _vista
    end
    
    def juega
      final = false
      
      puts "\n ~~~~~~~~ CIVITAS ~~~~~~~~"
      puts "   INICIO DE LA PARTIDA   "
      puts " ~~~~~~~~~~~~~~~~~~~~~~~~~"
      
      @vista.setCivitasJuego(@juego)
      
      while (!final)
        @vista.actualizarVista
        @vista.pausa

        operacion = @juego.siguientePaso
        @vista.mostrarSiguienteOperacion(operacion)
          
        if operacion != Civitas::Operaciones_juego::PASAR_TURNO
          @vista.mostrarEventos
        end
          
        # Final del juego --> mostrar ranking
        if @juego.finalDelJuego
          final = true

          rank = @juego.ranking         

          puts "\n ~~~~~~~~ CIVITAS ~~~~~~~~"
            puts "   RANKING DE JUGADORES   "
            puts " ~~~~~~~~~~~~~~~~~~~~~~~~~"
          
          for i in 0..1
            puts rank[i].to_s
          end
          
          puts "\n ~~~~~~~~ CIVITAS ~~~~~~~~"
            puts "     FIN DE LA PARTIDA    "
            puts " ~~~~~~~~~~~~~~~~~~~~~~~~~"
          
        else
          
          # Operacion en la partida (comprar, gestionar etc)
          case operacion      
            
            # Comprar
            when Civitas::Operaciones_juego::COMPRAR
              respuesta = @vista.comprar
              if respuesta == Civitas::Respuestas::SI
                @juego.comprar
              end
              @juego.siguientePasoCompletado(operacion)

            # Gestionar
            when Civitas::Operaciones_juego::GESTIONAR   
              @vista.gestionar

              gest = Civitas::GestionesInmobiliarias::Lista_Gestiones[@vista.iGestion]
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
                  
                when Civitas::GestionesInmobiliarias::EXAMEN
                  @juego.Examen
                  
              end
                  
          # Salir de la carcel
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

