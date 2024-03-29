/**
 * @author Yesenia González Dávila
 * @author Esther García Gallego
 * Grupo B.3
 * @file OperacionInmobiliaria.java
 */
package civitas;


public class OperacionInmobiliaria {
    private int numPropiedad;
    GestionesInmobiliarias gestion;
    
    public OperacionInmobiliaria (int ip, GestionesInmobiliarias gest) {
        numPropiedad = ip;
        gestion = gest;
    }
    
    public GestionesInmobiliarias getGestion() {
        return gestion;
    }
    
    public int getNumPropiedad(){
        return numPropiedad;
    }
    
    /*public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){                // E: Dos veces lo mismo
        gestion = gest;
        numPropiedad = ip;
    }*/     
}
