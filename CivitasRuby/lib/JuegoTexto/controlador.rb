=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative ./civitas/Diario.rb 
require_relative ./civitas/civitas_juego.rb
require_relative ./civitas/Operaciones_juego.rb
require_relative ./civitas/respuestas.rb
require_relative ./civitas/jugador.rb
require_relative ./civitas/Casilla.rb
require_relative ./civitas/operacion_inmobiliaria.rb
require_relative ./civitas/gestiones_inmobiliarias.rb
require_relative ./civitas/salidas_carcel

module Juego_texto
  class Controlador
    
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
          
        if operacion != OperacionesJuego.pasar_turno
          @vista.mostrarEventos
        end
          
        if @juego.finalDelJuego
          final = true
          rank = Array.new(@juego.ranking.size) # Creamos el array

          rank = @juego.ranking         # Copiamos el ranking del juego ?????

          for i in 0..(num_jugadores-1) # Mostramos los jugadores en orden
            rank[i].toString
          end
            
        else
          case operacion                                    #switch
          when Civitas::OperacionesJuego::COMPRAR
            respuesta = @vista.comprar
                  
            if respuesta == Civitas::Respuestas::SI
              @juego.comprar
            end
                  
            @juego.siguientePasoCompletado(operacion)
                  
          when Civitas::GestionesInmobiliarias::GESTIONAR   #Civitas::   ?????????
            @vista.gestionar
            gest = #GestionesInmobiliarias.values()[@vista.gestion]; ????????
            ip = @vista.propiedad
                  
            operacionInm = OperacionInmobiliaria.new(ip,gest)
                  
            case operacionInm.gestion
            when Civitas::GestionesInmobiliarias::VENDER    #me lo asocia con el método vender de juego en vez de con el enum
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
                  
          when Civitas::GestionesInmobiliarias::SALIR_CARCEL
              
            salida = @vista.salirCarcel
            if salida == Civitas::SalidasCarcel::PAGANDO
              @juego.salirCarcelPagando
            else
              juego.salirCarcelTirando
            end
                  
            @juego.siguientePasoCompletado(operacion)
            
          end
        
        end
      end
    
    end
  end
end

