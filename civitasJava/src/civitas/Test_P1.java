//package civitas;
//import java.util.ArrayList;
//
//public class Test_P1 {
//    
//    public static void main () {
//        //p2_casilla_test();
//    }
//    
//    void tarea1 () {
//        int n_jug = 4;
//        double []indices_jug = new double [n_jug];
//        
//        for (int i=0; i<n_jug ; i++)
//            indices_jug[i] = 0;
//        
//        // Tarea 1
//        System.out.println("\t --- TAREA 1 ---");
//        int nveces = 100;
//        int indice;
//        for (int i=0 ; i<nveces ; i++) {
//            indice = Dado.getInstance().quienEmpieza(n_jug);
//            indices_jug[indice] ++;
//        }
//        
//        double prob1 = indices_jug[0] / nveces,
//               prob2 = indices_jug[1] / nveces,
//               prob3 = indices_jug[2] / nveces,
//               prob4 = indices_jug[3] / nveces;
//        
//        System.out.println(" >> Probabilidad jugador 1: " + prob1);
//        System.out.println(" >> Probabilidad jugador 2: " + prob2);
//        System.out.println(" >> Probabilidad jugador 3: " + prob3);
//        System.out.println(" >> Probabilidad jugador 4: " + prob4);
//        System.out.println("\t --- FIN TAREA 1 ---\n");
//    }
//    
//    void tarea2() {
//        
//        System.out.println("\t --- TAREA 2 ---");
//        
//        // 20 tiradas con modo debug desactivado
//        System.out.println("Modo debug ON");
//        int ntiradas = 20;
//        int []tiradas = new int [10];
//        System.out.print(" >> Tiradas: ");
//        for (int i=0 ; i<ntiradas ; i++)
//            System.out.print(Dado.getInstance().tirar() + " ");
//        
//        // Modo debug activado
//        Dado.getInstance().setDebug(true);
//        
//        System.out.println("\n\n Modo debug OFF");
//        System.out.print(" >> Tiradas: ");
//        for (int i=0 ; i<ntiradas ; i++)
//            System.out.print(Dado.getInstance().tirar() + " ");
//        
//        System.out.println("\n\t --- FIN TAREA 2 ---\n");
//    }
//    
//    void tarea3() {
//        
//        System.out.println("\t --- TAREA 3 ---");
//        
//        int ntiradas = 5;
//        
//        // Desactivamos el modo debug
//        Dado.getInstance().setDebug(false);
//        
//        for (int i=0 ; i<ntiradas ; i++) {
//            System.out.println(" >> Tirada: " + Dado.getInstance().tirar());
//            System.out.println("\t ultimoResultado: " + Dado.getInstance().getUltimoResultado());
//            System.out.print("\t salgoDeLaCarcel: ");
//            
//            if (Dado.getInstance().salgoDeLaCarcel())
//                System.out.println("true");
//            else
//                System.out.println("false");
//            
//        }
//        
//        System.out.println("\t --- FIN TAREA 3 ---\n");
//    }
//    
//    void tarea4() {
//        System.out.println("\t --- TAREA 4 ---");
//        // Tipo casilla
//        int ncasillas = 5;
//        System.out.print(" >> Tipo casilla: ");
//        System.out.print(TipoCasilla.JUEZ + ", " + TipoCasilla.CALLE);
//        
//        //Tipo sorpresa
//        System.out.print("\n >> Tipo sorpresa: ");
//        System.out.print(TipoSorpresa.IRCARCEL+ ", " + TipoSorpresa.PAGARCOBRAR);
//        
//        // Estados juego
//        System.out.print("\n >> Estados juego: ");
//        System.out.print(EstadosJuego.DESPUES_AVANZAR + ", " + EstadosJuego.DESPUES_GESTIONAR);
//        
//        // Operaciones juego
//        System.out.print("\n >> Operaciones juego: ");
//        System.out.print(OperacionesJuego.AVANZAR + ", " + OperacionesJuego.PASAR_TURNO);
//        
//        System.out.println("\n\t --- FIN TAREA 4 ---\n");
//    }
//    
//    void tarea5y6() {
//        System.out.println("\n\t --- TAREA 5 y 6  ---");
//        MazoSorpresas mazo = new MazoSorpresas();
//        
//        System.out.println("Como cuyons añado sorpresas al mazo si la clase sorpresa esta vacía jaja salu2");
//        System.out.println("\t --- FIN TAREA 5 y 6 ---\n");
//    }
//    
//    void tarea7() {
//        System.out.println("\n\t --- TAREA 7 ---");
//        //Suponemos casilla carcel en 5 
//        int indice_carcel = 5;
//        Tablero tablero = new Tablero (indice_carcel);
//        
//        // Añadimos varias casillas
//        int ncasillas = 15;
//        
//        ArrayList<Casilla> casillas = new ArrayList<> ();
//        casillas.add(0, new Casilla ("JUEZ"));
//        casillas.add(1, new Casilla ("IMPUESTO"));
//        casillas.add(2, new Casilla ("DESCANSO"));
//        casillas.add(3, new Casilla ("SORPRESA"));
//        casillas.add(4, new Casilla ("CALLE"));
//        
//        System.out.println("Creando tablero 1... (usar depurador para verificar los vavlores de sus casillas)");
//        for (int i = 0 ; i<ncasillas ; i++)
//            tablero.añadeCasilla (casillas.get(i%5));
//        
//        
//        // Poner carcel en posicion mayor a tamaño de tablero
//        System.out.println("Creando tablero 2... (usar depurador para verificar el valor del indice de carcel)");
//        Tablero tablero2 = new Tablero (40);
//        
//        
//        // Falta dado
//        
//        System.out.println("\t --- FIN TAREA 7 ---\n");
//    }
//    
//}
