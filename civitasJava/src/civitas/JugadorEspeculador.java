/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;


/**
 * @author Yesenia Gonzáles Dávila
 * @author Esther García Gallego
 */
public class JugadorEspeculador extends Jugador {

    private final int FactorEspeculador = 2;
    private float fianza;

    JugadorEspeculador(JugadorEspeculador otro, float _fianza) {   // Constructor de copia
        super(otro);
        fianza =_fianza;
        
        for(int i = 0; i < getPropiedades().size(); i++)
            getPropiedades().get(i).ActualizaPropietarioPorConversion(otro);
    }

    @Override
    protected boolean debeSerEncarcelado() {
        boolean carcel = false;

        if (!isEncarcelado()) {
            if (!tieneSalvoconducto() && puedoGastar(fianza)) {
                paga(fianza);
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre()
                        + " se ha librado de la cárcel pagando la fianza");
            } else if (!tieneSalvoconducto() && !puedoGastar(getPrecioLibertad())) {
                carcel = true;
            } else {
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre()
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

      s = "\n >> Jugador especulador " + getNombre() + ". Saldo " + getSaldo() + "€. Propiedades: " + getPropiedades().size() +". Edificaciones:" + cantidadCasasHoteles();

      s += "\n Casilla actual: " + getNumCasillaActual();
      if (getPuedeComprar())
          s+= " Puede comprar.";

      if (isEncarcelado())
          s+= " Está encarcelado.";

      if (tieneSalvoconducto())
          s+= " Tiene salvoconducto";

      return s;
    }
    
    @Override
    protected int getCasasMax(){
        return getCasasMax()*FactorEspeculador;
    }
    
    @Override
    protected int getHotelesMax () {
      return getHotelesMax()*FactorEspeculador;
    }
}