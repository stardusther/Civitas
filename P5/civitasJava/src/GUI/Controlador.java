/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego Grupo B.3
 * @file Controlador.java
 */
package GUI;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.Jugador;
import civitas.Casilla;
import civitas.OperacionInmobiliaria;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;

import juegoTexto.VistaTextual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.util.ArrayList;

public class Controlador {

    private CivitasJuego juego;
    private CivitasView vista;

    Controlador(CivitasJuego _juego, CivitasView _vista) {
        juego = _juego;
        vista = _vista;
    }

    
    void juega() {
        boolean end = false;
        OperacionesJuego operacion;
        Respuestas respuesta;

        vista.setCivitasJuego(juego);
        
        System.out.println("\n");

        while (!end) {
            vista.actualizarVista();
            //vista.pausa();

            operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);

            if (operacion != OperacionesJuego.PASAR_TURNO) 
                vista.mostrarEventos();

            if (juego.finalDelJuego()) {
                
                end = true;
                ArrayList<Jugador> rank = new ArrayList(juego.ranking());

                System.out.println("\n ----------------------------- ");
                System.out.println("     RANKING DE JUGADORES      ");
                System.out.println(" ----------------------------- ");
                for (int i = 0; i < rank.size(); i++) 
                    System.out.println( i+1 + ". " + rank.get(i).toString() + "\n");
                System.out.println("\n ----- FIN DEL JUEGO ----- \n\n");
                
                
            } else {
                switch (operacion) {

                    case COMPRAR:
                        respuesta = vista.comprar();

                        if (respuesta == Respuestas.SI) {
                            juego.comprar();
                        }
                        juego.siguientePasoCompletado(operacion);
                        break;

                    case GESTIONAR:
                        vista.gestionar();
                        GestionesInmobiliarias gest = GestionesInmobiliarias.values()[vista.getGestionElegida()];
                        int ip = vista.getPropiedadElegida();

                        OperacionInmobiliaria operacionInm = new OperacionInmobiliaria(ip, gest);

                        switch (operacionInm.getGestion()) {
                            case VENDER:
                                juego.vender(ip);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(ip);
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(ip);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(ip);
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(ip);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(operacion);
                                break;
                        }
                        break;

                    case SALIR_CARCEL:

                        SalidasCarcel salida = vista.salirCarcel();
                        if (salida == SalidasCarcel.PAGANDO) {
                            juego.salirCarcelPagando();
                        } else {
                            juego.salirCarcelTirando();
                        }

                        juego.siguientePasoCompletado(operacion);
                        break;
                        
                }
            }

        }
    }
    
}