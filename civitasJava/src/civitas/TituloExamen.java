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
public class TituloExamen extends TituloPropiedad{
    
    TituloExamen(String nombre, float ab, float fr, float hb, float pc, float pe) {
        super(nombre, ab, fr, hb, pc, pe);
    }
    
    @Override
    protected boolean construirCasa(Jugador jugador){
        boolean ret = super.construirCasa(jugador);
        jugador.paga(precioEdificar);
        
        return ret;
    }
}
