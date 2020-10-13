class TestP1
  def initialize
  end

  def main
    num_jug = 4
    indices_jug = []
    # dado = Dado.new
#    Dado.instance

   # casilla = Casilla.new("Carcel")

    # Inicializo el vector
    ind = 0
    while ind < 4
      indices_jug[ind] = 0
      ind += 1
    end

    # Tarea 1
    puts "\t >> Tarea 1: ------------------------------------\n"

    i = 0
    nveces = 100
    while i < nveces
      indice = Dado.instance.quienEmpieza(num_jug)
      indices_jug[indice] += 1
      i += 1
    end

    prob1 = indices_jug[0] / nveces;
    prob2 = indices_jug[1] / nveces;
    prob3 = indices_jug[2] / nveces;
    prob4 = indices_jug[3] / nveces;

    puts "Jugador 1: " + "#{prob1}" + "\nJugador 2: " + "#{prob2}" + "\nJugador 3: " + "#{prob3}" + "\nJugador 4: " + "#{prob4}"

    # Tarea 2
    puts "\t >> Tarea 2: ------------------------------------\n"

    Dado.instance.setDebug(true)  #Activamos el modo Debug
    j = 0

    while j < 10
      tirada = Dado.tirar()
      puts "\nLa tirada número " + "#{j}"+ "tiene como resultado " +"#{tirada}"
      j += 1
    end

    Dado.instance.setDebug(false) #Desactivamos el modo Debug

    j = 0

    while j < 10
      tirada = Dado.tirar()
      puts "\nLa tirada número " + "#{j}"+ "tiene como resultado " +"#{tirada}"
      j += 1
    end

    # Tarea 3
    puts "\t >> Tarea 3: ------------------------------------\n"

    if Dado.salgoDeLaCarcel() then
      puts "\n Sale de la cárcel."
    else
      puts "\n No sale de la cárcel. Resultado:" + "#{Dado.getultimoResultado()}"

    # Tarea 4
    puts "\t >> Tarea 4: ------------------------------------\n"



    # Tarea 5
    puts "\t >> Tarea 5: ------------------------------------\n"

    mazo = MazoSorpresas.new
    mazo.alMazo(TipoSorpresa::PAGARCOBRAR)
    mazo.alMazo(TipoSorpresa::PAGARCOBRAR)
    mazo.

    # Tarea 6
    puts "\t >> Tarea 6: ------------------------------------\n"

  end
end

test = TestP1.new
test.main
