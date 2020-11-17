/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;

public class main {
    
    public static void main(String[] args) {
        String j1 = "JUGADOR 1";
        String j2 = "JUGADOR 2";
        String j3 = "JUGADOR 3";
        String j4 = "JUGADOR 4";
        
        VistaTextual vista = new VistaTextual();
        CivitasJuego juego = new CivitasJuego(j1,j2);
        Dado.getInstance().setDebug(true);
        Controlador controlador = new Controlador (juego, vista);
        
        controlador.juega();
    }
    
}
