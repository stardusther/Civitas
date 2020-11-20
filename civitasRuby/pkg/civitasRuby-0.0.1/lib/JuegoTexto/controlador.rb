=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end
require_relative 'CivitasJuego'
require_relative 'OperacionesJuego'
require_relative 'Respuestas'
require_relative 'Jugador'  #incluye Dado y diario
require_relative 'Casilla'
require_relative 'OperacionInmobiliaria'
require_relative 'GestionesInmobiliarias'
require_relative 'SalidasCarcel'

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
                  
          when Civitas::OperacionesJuego::GESTIONAR   
            @vista.gestionar
            gest = GestionesInmobiliarias::lista_Gestiones[@vista.gestion]
            ip = @vista.iPropiedad
                  
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
                  
          when Civitas::OperacionesJuego::SALIR_CARCEL
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

