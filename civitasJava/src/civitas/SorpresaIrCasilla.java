/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class SorpresaIrCasilla extends Sorpresa {
    
    /** Constructor, el valor corresponde a la casilla
     *  a la que se envía al jugador. */
    SorpresaIrCasilla (Tablero tab, int valor, String texto) {
        super("* Ir casilla *");
        tablero = tab;
        this.valor = valor;
        this.texto = texto;
    }
    
    
    /** Mueve al jugador a la casilla indicada por el atributo valor.
     * @note Es necesario usar los metodos calcularTirada y nuevaPosicion 
     * para que quede registrado un posible paso por salida como 
     * consecuencia del salto a la nueva casilla.
     */
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe (actual, todos);

            int casilla = todos.get(actual).getNumCasillaActual();      // 1. Obtenemos casilla actual del jugador
            int tirada = tablero.calcularTirada(casilla, valor);        // 2. Se calcula la tirada
            int nuevaPos = tablero.nuevaPosicion(casilla, tirada);      // 3. Se obtiene la nueva pos. del jugador

            todos.get(actual).moverACasilla(nuevaPos);                  // 4. Se mueve al jugador a esa nueva posicion

            tablero.getCasilla(valor).recibeJugador (actual, todos);    // 5. Se indica a la casilla que está en la posicion
                                                                        // del valor de la sorpresa que reciba al jugador
        }
    }
    
}
