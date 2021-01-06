
package GUI;

import java.util.ArrayList;
import civitas.CivitasJuego;

public class TestP5 {
    
    public static void main(String[] args) {
        
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(true);
        CapturaNombres captura = new CapturaNombres(vista, true);
        
        ArrayList<String> nombres = new ArrayList();
        
        nombres = captura.getNombres();
        
        CivitasJuego juego = new CivitasJuego(nombres);
        
        Controlador controlador = new Controlador(juego, vista);
        
        vista.setCivitasJuego(juego);
        
        vista.actualizarVista();
    }
    
}
