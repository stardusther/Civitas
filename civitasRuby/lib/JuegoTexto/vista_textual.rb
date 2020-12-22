#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

require_relative '../civitas/Operaciones_juego.rb'
require_relative '../civitas/respuestas.rb'
require 'io/console'

module Civitas

  class Vista_textual
    
    @@Separador = "========================================"
    
    attr_reader :iGestion, :iPropiedad
    
    public # -------------------------------------------------------------------

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "\nPulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end


    def menu(titulo,lista)
      tab = "   "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+" ➤ "+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n⮕ Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end
    
    
    def comprar  
      respuestas = []
      respuestas.push("Si")
      respuestas.push("No")
      opcion = menu("¿Comprar calle?", respuestas);
      return Civitas::Respuestas::Lista_respuestas[opcion]
    end

    def gestionar
      gestiones = []
      gestiones.push("Vender")
      gestiones.push("Hipotecar")
      gestiones.push("Cancelar hipoteca")
      gestiones.push("Constuir casa")
      gestiones.push("Construir hotel")
      gestiones.push("Terminar")
      gestiones.push(" >> EXAMEN <<")
      
      @iGestion = menu("\n Indique operación inmobiliaria:", gestiones)
      
      if (iGestion != 5)
        if iGestion != 6 # Examen
          
          propiedades = []
          for i in 0..@juegoModel.getJugadorActual.propiedades.length-1
            propiedades.push(@juegoModel.getJugadorActual.propiedades[i].to_s)
          end
          @iPropiedad = menu("\n Indique propiedad a la que desea aplicar la gestión:", propiedades)
        end
      end
      
    end

    def mostrarSiguienteOperacion(operacion)
      puts "\n × Siguiente: #{operacion}"
    end

    def mostrarEventos
      evento = Civitas::Diario.instance.leer_evento
      if evento <=> ""
        puts "\n ---------- "
        puts " | Diario | "
        puts " ----------"
        while evento <=> ""
          puts evento
          evento = Civitas::Diario.instance.leer_evento
        end
      end
      puts
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      puts "\n#{@@Separador}"
      puts("#{@juegoModel.getJugadorActual.to_s} >> #{@juegoModel.getCasillaActual.to_s}");
      puts @@Separador
    end
    
    def salirCarcel
      salir_carcel = []
      salir_carcel.push("Pagando")
      salir_carcel.push("Tirando")
      opcion = menu(" ⮕ Elige la forma para intentar salir de la carcel", salir_carcel);
    
      return Civitas::SalidasCarcel::Lista_salidas[opcion]
    end

    
  end

end