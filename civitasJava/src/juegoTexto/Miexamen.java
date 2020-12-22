/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;

/**
 *
 * @author Esther
 */
public class Miexamen {
    public static void main(String[] args) {
        ArrayList <String> jugadores = new ArrayList();
        
        jugadores.add("*Paris*");
        jugadores.add("*Ciudad del Vaticano*");
        
        VistaTextual vista = new VistaTextual();
        CivitasJuego juego = new CivitasJuego(jugadores);
        Dado.getInstance().setDebug(true);
        Controlador controlador = new Controlador (juego, vista);
        
        controlador.juega ();
    }
}
