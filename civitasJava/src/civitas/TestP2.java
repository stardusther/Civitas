
package civitas;

public class TestP2 {

    
    public static void main(String[] args) {
        //test1();
        //test2 ();
        //test3();
    }
    
    // Test casillas + sorpresas + tablero +  mazo
    static void test1 () {
        
        // Ya no válido por la creación de subclases.
//        // Creamos mazo
//        MazoSorpresas mazo = new MazoSorpresas();
//        int numCarcel = 5;
//        
//        // Tablero sin juez
//        Tablero tablero = new Tablero (numCarcel);
//        System.out.println(tablero.toString() + "\n");
//        
//        // Creamos casillas
//        Casilla c1 = new Casilla ("Descanso");
//        Casilla c2 = new Casilla (200f, "Impuesto");
//        Casilla c3 = new Casilla (5, "Juez");
//        Casilla c4 = new Casilla (mazo, "Sorpresa");
//        
//        // Añadimos juez y casillas (no tiene sentido añadir casilla c3) (ok)
//        tablero.añadeJuez();
//        tablero.añadeCasilla(c1);
//        tablero.añadeCasilla(c2);
//        tablero.añadeCasilla(c4);
//        tablero.añadeCasilla(c1);
//        System.out.println(tablero.toString() + "\n");
//        
//        // Creamos sorpresas (ok)
//        Sorpresa evita_carcel = new Sorpresa (TipoSorpresa.SALIRCARCEL, mazo);
//        Sorpresa ir_a_casilla = new Sorpresa (TipoSorpresa.IRCASILLA, tablero, 1, " Ir a casilla");
//        Sorpresa por_jugador = new Sorpresa (TipoSorpresa.PORJUGADOR, 10, "Por jugador");
//        Sorpresa por_casa_hotel = new Sorpresa (TipoSorpresa.PORCASAHOTEL, 20, "Por casa hotel");
//        Sorpresa pagar_cobrar = new Sorpresa (TipoSorpresa.PAGARCOBRAR, 30, "Por casa hotel");
//        Sorpresa ir_carcel = new Sorpresa (TipoSorpresa.IRCARCEL, tablero);
//        
//        // Probamos sorpresas (ok)
//        System.out.println(evita_carcel.toString());  System.out.println(ir_a_casilla.toString());
//        System.out.println(por_jugador.toString());   System.out.println(por_casa_hotel.toString());
//        System.out.println(pagar_cobrar.toString());  System.out.println(ir_carcel.toString());
//    
//        // Añadimos sorpresas (ok)
//        mazo.alMazo(evita_carcel);  mazo.alMazo(ir_a_casilla);
//        mazo.alMazo(por_jugador);   mazo.alMazo(por_casa_hotel);
//        mazo.alMazo(pagar_cobrar);  System.out.println("\n" + mazo.toString());
//        
//        // Habilitar/deshabilitar cartas (ok)
//        evita_carcel.salirDelMazo();  
//        System.out.println("\n" + mazo.toString() + "\n");
//        evita_carcel.usada();         ir_a_casilla.salirDelMazo();
//        System.out.println(mazo.toString() + "\n");
//        
//        // Probamos a sacar sorpresas de mazo (no inhabilitar), 10 veces (ok)
//        for (int i=0 ; i<10 ; i++) 
//            System.out.println(mazo.siguiente().toString());
//        
//        // Comprobamos diario
//        String salida = "tmp";
//        while (salida != "") {
//            salida = Diario.getInstance().leerEvento();
//            System.out.println("\n" + salida);
//        }
    }
    
    // Test jugador (asumiendo que clases de test1 están correctas)
    static void test2 () {
        
        //Creamos 4 jugadores;
        Jugador j1 = new Jugador ("J1");
        Jugador j2 = new Jugador ("J2");
        
        // Damos a un jugador salvoconducto y comprobamos debeSerEncarcelado()
        MazoSorpresas mazo = new MazoSorpresas();
        Sorpresa s = new Sorpresa (TipoSorpresa.SALIRCARCEL, mazo);
        j1.obtenerSalvoconducto(s);
        if (j1.debeSerEncarcelado())
            System.out.println("Jugador 1 debe ser encarcelado");
        if (j2.debeSerEncarcelado())
            System.out.println("Jugador 2 debe ser encarcelado");
        
        // Encarcelamos a j2
        j2.encarcelar(5);
        
        System.out.println(j1.toString() + "\n" + j2.toString());
        
        String diario = "tmp";
        while (diario != "") {
            diario = Diario.getInstance().leerEvento();
            System.out.println("\n" + diario);
        }
    }
    
    // Test titulo propiedad (sumiendo clases de test1 y test2 correctas)
    static void test3 () {
        
        TituloPropiedad tit = new TituloPropiedad ("Calle Mogán", 1, 2, 3, 4, 5);
        Jugador j1 = new Jugador ("J1");
        Jugador j2 = new Jugador ("J2");
        
        // Añadimos propietario (ok)
        tit.ActualizaPropietarioPorConversion(j1);
        System.out.println(tit.toString());
        
        // Alquiler (ok)
        tit.tramitarAlquiler(j2);
        System.out.println(j1.toString() + "\n" + j2.toString());
        
        // Vendemos (ok)
        tit.vender(j1);
        System.out.println(tit.toString());
        System.out.println(j1.toString());
        
        // Metodos de construir para las siguientes practicas sooo....
    }
    
    // Test 4 CivitasJuego (asumiendo clases de test anteriores correctas)
    static void test4() {
        
    }
    
}
