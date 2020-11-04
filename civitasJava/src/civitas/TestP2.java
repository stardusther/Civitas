/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author yese
 */
public class TestP2 {

    
    public static void main(String[] args) {
        test1();
    }
    
    // Test casillas + sorpresas + tablero +  mazo
    static void test1 () {
        
        // Creamos mazo
        MazoSorpresas mazo = new MazoSorpresas();
        int numCarcel = 5;
        
        // Tablero sin juez
        Tablero tablero = new Tablero (numCarcel);
        System.out.println(tablero.toString() + "\n");
        
        // Creamos casillas
        Casilla c1 = new Casilla ("Descanso");
        Casilla c2 = new Casilla (200f, "Impuesto");
        Casilla c3 = new Casilla (5, "Juez");
        Casilla c4 = new Casilla (mazo, "Sorpresa");
        
        // Añadimos juez y casillas (no tiene sentido añadir casilla c3) (ok)
        tablero.añadeJuez();
        tablero.añadeCasilla(c1);
        tablero.añadeCasilla(c2);
        tablero.añadeCasilla(c4);
        tablero.añadeCasilla(c1);
        System.out.println(tablero.toString() + "\n");
        
        // Creamos sorpresas (ok)
        Sorpresa evita_carcel = new Sorpresa (TipoSorpresa.SALIRCARCEL, mazo);
        Sorpresa ir_a_casilla = new Sorpresa (TipoSorpresa.IRCASILLA, tablero, 1, " Ir a casilla");
        Sorpresa por_jugador = new Sorpresa (TipoSorpresa.PORJUGADOR, 10, "Por jugador");
        Sorpresa por_casa_hotel = new Sorpresa (TipoSorpresa.PORCASAHOTEL, 20, "Por casa hotel");
        Sorpresa pagar_cobrar = new Sorpresa (TipoSorpresa.PAGARCOBRAR, 30, "Por casa hotel");
        Sorpresa ir_carcel = new Sorpresa (TipoSorpresa.IRCARCEL, tablero);
        
        // Probamos sorpresas (ok)
        System.out.println(evita_carcel.toString());  System.out.println(ir_a_casilla.toString());
        System.out.println(por_jugador.toString());   System.out.println(por_casa_hotel.toString());
        System.out.println(pagar_cobrar.toString());  System.out.println(ir_carcel.toString());
    
        // Añadimos sorpresas (ok)
        mazo.alMazo(evita_carcel);  mazo.alMazo(ir_a_casilla);
        mazo.alMazo(por_jugador);   mazo.alMazo(por_casa_hotel);
        mazo.alMazo(pagar_cobrar);
        System.out.println("\n" + mazo.toString());
        
        // Habilitar/deshabilitar cartas (ok)
        evita_carcel.salirDelMazo();  System.out.println("\n" + mazo.toString() + "\n");
        evita_carcel.usada();         ir_a_casilla.salirDelMazo();
        System.out.println(mazo.toString() + "\n");
        
        // Probamos a sacar sorpresas de mazo (no inhabilitar), 10 veces
        Sorpresa nueva;
        for (int i=0 ; i<10 ; i++) {
            nueva = mazo.siguiente(); System.out.println(nueva.toString());
        }
        
        // Comprobamos diario
        String salida = "tmp";
        while (salida != "") {
            salida = Diario.getInstance().leerEvento();
            System.out.println(salida);
        }
        
    }
    
}
