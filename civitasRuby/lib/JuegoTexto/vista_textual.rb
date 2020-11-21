#encoding:utf-8

=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

#encoding:utf-8
require_relative '../civitas/Operaciones_juego.rb'
require_relative '../civitas/respuestas.rb'
require 'io/console'

module Civitas

  class Vista_textual
    
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
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end
    
    
    def comprar  # Me lo he inventado, hay que probar :3
      opcion = menu("¿Comprar calle?", ("Si" "No"));
      return Civitas::Respuestas[opcion]
    end

    def gestionar
      @iGestion = menu(" Indique número de operación inmobiliaria: (0..5))", ("Vender"
                      "Hipotecar" "Cancelar hipoteca" "Constuir casa" "Construir hotel" "Terminar"))
      if (iGestion != 5)
        @iPropiedad = menu(" Indique propiedad a la que desea aplicar la gestión:)",
                          ("0" "1" "2"))
      end
    end

    def mostrarSiguienteOperacion(operacion)
      puts "\n *** Siguiente operacion: #{operacion}"
    end

    def mostrarEventos
      evento = Civitas::Diario.instance.leer_evento
      while evento != ""
        puts evento
        evento = Civitas::Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      puts("#{@juegoModel.getJugadorActual.to_s} \n >> Casilla #{@juegoModel.getCasillaActual.to_s}");
    end

    
  end

end