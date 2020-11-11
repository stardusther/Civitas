/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 * @file Controlador.java
 */
package civitas;
import java.util.ArrayList;

public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego _juego, VistaTextual _vista){
        juego = _juego;
        vista = _vista;
    }
    
    void juega() {
        Boolean end = false;
        OperacionesJuego operacion = OperacionesJuego.AVANZAR;  // No sé a qué se debería inicializar
        Respuestas respuesta;

        vista.setCivitasJuego(juego);       // Muestra el estado del juego actualizado 

        while (!end) {
            vista.actualizarVista();
            vista.pausa();
            vista.mostrarSiguienteOperacion(operacion);

            if (operacion != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }

            if (juego.finalDelJuego()) {
                end = true;
                ArrayList<Jugador> rank = new ArrayList(juego.ranking());
                
                for(int i = 0; i < rank.size(); i++)
                    rank.get(i).toString();
            }

            switch (operacion) {
                case COMPRAR:
                    respuesta = vista.comprar();
                    if (respuesta == Respuestas.SI) {
                        juego.comprar();    // Hay que implementar este método
                    }
                    juego.siguientePasoCompletado(operacion);
                    break;
                case GESTIONAR:
                    int gest;
                    int ip;
                    
                    vista.gestionar();
                    gest = vista.getGestion();
                    ip = vista.getPropiedad();
                    
                    OperacionInmobiliaria operacionInm = new OperacionInmobiliaria (GestionesInmobiliarias(gest),ip); //E: npi
                    switch(operacionInm.getGestion()){
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
            }
            
            
        }
    }
}
