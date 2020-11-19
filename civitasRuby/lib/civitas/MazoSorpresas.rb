=begin
Authors: Esther García Gallego
         Yesenia González Dávila
         Grupo B3
=end

class MazoSorpresas

  # Constructor
  def initialize (debug=false)
    init()
    @debug = debug

    if (@debug)
      Diario.instance.ocurreEvento ("Modo debug activo en el mazo");
    end

  end

  def alMazo (s)
    if (!@barajada)
      @sorpresas.push (s)
    end
  end

  def siguiente()
    if ( (!@barajada || @usadas == @sorpresas.length) && !@debug)
      @sorpresas.shuffle            # Baraja el mazo de sorpresas
      @usadas = 0
      @barajada = true
    end

    @usadas += 1
    @ultimaSorpresa = @sorpresas[0]
    @sorpresas.push(@ultimaSorpresa)
    @sorpresas.delete_at(0)
    @ultimaSorpresa = @sorpresas[@sorpresas.length-1]

    @ultimaSorpresa
  end

  def inhabilitarCartaEspecial (sorpresa)
    found = false
    i = 0
    
    if @sorpresas.include?(sorpresa)
      @cartasEspeciales.push(sorpresa)
      @sorpresas.remove(sorpresa)
      Diario.instance.ocurreEvento ("Se ha inhabilitado una carta especial")
    end

  end

  def habilitarCartaEspecial (sorpresa)
    found = false
    i = 0
    
    if @cartasEspeciales.include? (sorpresa)
      @sorpresas.push(sorpresa)
      @cartasEspeciales.delete(sorpresa)
      Diario.instance.ocurreEvento ("Se ha habilitado una carta especial")
    end

  end
  
  def toString()
    str = " >> Mazo: " + @sorpresas.length + " sorpresas. Se han usado " + @usadas 
    if (@barajada)
      str = str + " Esta barajada."
    end
    if (@debug)
      str = str + " Debug on"
    end
    
    @sorpresas.each do |sorpresa|
      str = str + "\n" + sorpresa.toString
    end
    
  end

  # Inicializa los atributos
  private
  def init()
    @sorpresas = []
    @cartasEspeciales = []
    @barajada = false
    @usadas = 0
  end

end
