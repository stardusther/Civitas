/**
 * @file JugadorEspeculador.java
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * @note Grupo B.3
 * @class JugadorEspeculador
 * @brief Representa a un tipo de jugador especial
*/


package civitas;
public class JugadorEspeculador extends Jugador {

    private final int FactorEspeculador = 2;
    private float fianza;

    JugadorEspeculador(Jugador otro, float _fianza) {   
        super(otro);
        fianza =_fianza;
        
        for(int i = 0; i < getPropiedades().size(); i++)
            getPropiedades().get(i).ActualizaPropietarioPorConversion(this);
    }

    @Override
    protected boolean debeSerEncarcelado() {
        boolean carcel = false;

        if (!isEncarcelado()) {
            
            if (!tieneSalvoconducto())      // 1. Si no tiene salvoconducto
                
                if (puedoGastar(fianza)){   // 1.2. Puede pagar fianza
                    paga(fianza);
                    Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre()
                            + " se ha librado de la cárcel pagando la fianza");
                    
                } else carcel = true;       // 1.3. Si no puede pagar fianza, se encarcela
                    
                
            else {                          // 2. Si tiene salvoconducto, lo pierde y no es encarcelado
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre()
                        + " se ha librado de la cárcel por tener un salvoconducto");
            }
        }

        return carcel;
    }

    @Override
    boolean pagaImpuesto(float cantidad) {
        if (isEncarcelado()) 
            return false;
        else 
            return paga(cantidad / FactorEspeculador);
    }
   
    @Override 
    public String toString(){
        String s = super.toString() + " Especulador";
        return s;
    }
    
    @Override
    protected int getCasasMax(){
        return super.getCasasMax()*FactorEspeculador;
    }
    
    @Override
    protected int getHotelesMax () {
      return super.getHotelesMax()*FactorEspeculador;
    }
}