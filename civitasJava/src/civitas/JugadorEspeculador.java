/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;


/**
 *
 * @author Esther
 */
public class JugadorEspeculador extends Jugador {

    private final int FactorEspeculador = 2;
    private float fianza;

    JugadorEspeculador(JugadorEspeculador otro, float _fianza) {   // Constructor de copia
        super(otro);
        fianza =_fianza;
        
        for(int i = 0; i < propiedades.size(); i++)
            propiedades.get(i).ActualizaPropietarioPorConversion(otro);
    }

    @Override
    protected boolean debeSerEncarcelado() {
        boolean carcel = false;

        if (!isEncarcelado()) {
            if (!tieneSalvoconducto() && puedoGastar(fianza)) {
                paga(fianza);
                Diario.getInstance().ocurreEvento("El jugador especulador " + nombre
                        + " se ha librado de la cárcel pagando la fianza");
            } else if (!tieneSalvoconducto() && !puedoGastar(PrecioLibertad)) {
                carcel = true;
            } else {
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador especulador " + nombre
                        + " se ha librado de la cárcel por tener un salvoconducto");
            }
        }

        return carcel;
    }

    @Override
    boolean pagaImpuesto(float cantidad) {
        if (isEncarcelado()) {
            return false;
        } else {
            return paga(cantidad / FactorEspeculador);
        }
    }
   
    @Override 
    public String toString(){
        String s;

      s = "\n >> Jugador especulador " + getNombre() + ". Saldo " + getSaldo() + "€. Propiedades: " + propiedades.size() +". Edificaciones:" + cantidadCasasHoteles();

      s += "\n Casilla actual: " + numCasillaActual;
      if (puedeComprar)
          s+= " Puede comprar.";

      if (encarcelado)
          s+= " Está encarcelado.";

      if ( salvoconducto != null)
          s+= " Tiene salvoconducto";

      return s;
    }
    
    @Override
    protected int getCasasMax(){
        return CasasMax*FactorEspeculador;
    }
    
    @Override
    protected int getHotelesMax () {
      return HotelesMax*FactorEspeculador;
    }
}
