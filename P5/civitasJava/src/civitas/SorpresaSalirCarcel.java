
package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa {
    
    SorpresaSalirCarcel (MazoSorpresas mazo) {
        super ("* Salvoconducto *");
        this.mazo = mazo;
        texto = " ¡Obtienes salvoconducto!";
    }
    
    /** Se pregunta a todos los jugadores su alguien tiene la sorpresa para evitar 
     *  la carcel (salvoconducto), si nadie la tiene,la obtiene el jugador actual 
     *  y se llama al método salirDelMazo. */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto (actual, todos)) {

            // Se comprueba que el resto de jugadores no tiene el salvoconducto
            boolean tienen_salvoconducto = false;
            for (int i=0 ; i<todos.size() && !tienen_salvoconducto; i++)
                tienen_salvoconducto = todos.get(i).tieneSalvoconducto();

            if (!tienen_salvoconducto) {
                informe (actual, todos);
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo ();
            }
        }
    }
    
    /**  Si la sorpresa es SALIRCARCEL, inhabilita la carta en el mazo. */
    void salirDelMazo () {
        mazo.inhabilitarCartaEspecial(this);
    }

    /** Si la sorpresa es SALIRCARCEL, habilita la carta en el mazo. */
    void usada() {
        mazo.habilitarCartaEspecial(this);
    }
    
}
