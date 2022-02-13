package pacman_practica1_oficial;
import java.util.Scanner;
import java.util.Random;

public class PACMAN_PRACTICA1_OFICIAL {
    
    static Scanner menu = new Scanner(System.in); // "menu" es una variable

    public static void main(String[] args) {
        
        String historial = "No hay registros."; 
        String nombre="";
        int edad=0;
        int opcion=0;
        
        System.out.println("_____________________________");
        System.out.println("         ¡BIENVENIDO         ");
        System.out.println("             A               ");
        System.out.println("          PAN MAN!           ");
        System.out.println("_____________________________");
          
        boolean salir= false;
        while(!salir){
            
        System.out.println("");
        System.out.println("    ¿QUE DESEAS HACER HOY?   ");
        System.out.println("");
        System.out.println("==============================");
        System.out.println("°        1. JUGAR            °");
        System.out.println("°        2. HISTORIAL        °");
        System.out.println("°        3. SALIR            °");
        System.out.println("==============================");
        System.out.println("");
        System.out.println(" Elige el número de tu opción ");
        System.out.println("");
        opcion = menu.nextInt();
        
          switch(opcion){
           case 1: //El numero en los casos es lo que se pide al usuario
               System.out.println("");
               System.out.println("");
               System.out.println("Ingresa tu nombre de usuario");
               nombre = menu.next();
               System.out.println("");
               System.out.println("¿Cuantos años tienes?");
               edad = menu.nextInt();
               
               //-------------------MATRIZ----------------------
               
               System.out.println("");
               System.out.println("");
               System.out.println("Ingresa las dimensiones de tu tablero de juego");
               System.out.println("");
               System.out.println("------------------¡IMPORTANTE!-----------------");
               System.out.println("       Las dimensiones de tu tablero tienen  ");
               System.out.println("          que ser como minimo de 8x8         ");
               System.out.println("-----------------------------------------------");
               System.out.println("");
               System.out.println("¿De cuantas filas quieres tu tablero?");
               int filas = menu.nextInt() + 2;
               while (filas-2 < 8){
                   System.out.println("RECUERDA que tiene que ser mayor a 8");
                   System.out.println("¿De cuantas filas quieres tu tablero?");
                   filas = menu.nextInt() + 2;
               }
               System.out.println("");
               System.out.println("¿De cuantas columnas quieres tu tablero?");
               int columnas = menu.nextInt() + 2;  
               while (filas-2 < 8){
                   System.out.println("RECUERDA que tiene que ser mayor a 8");
                   System.out.println("¿De cuantas columnas quieres tu tablero?");
                   filas = menu.nextInt() + 2;
               }
               Game game = new Game(filas, columnas);
               game.run();
               
               String gameInfo = 
                       "\n*****************************\n" +
                       "Nombre: " + nombre + "\n" +
                       "Edad: " + edad + "\n" +
                       "Puntaje: " + game.score + "\n" +
                       "Movimientos: " + game.movements + "\n" +
                       "*****************************\n";
               
               if (historial == "No hay registros.") {
                   historial = gameInfo;
               } else {
                   historial += gameInfo;
               }
               break;
           case 2:
           System.out.println("______________________________");
           System.out.println("          TU RECORD          ");
           System.out.println("------------------------------");
           System.out.println("");
           if (historial == "No hay registros.") {
               System.out.println("\u001B[31m"+ historial +"\u001B[0m");
           } else {
              System.out.println(historial);
           }
           break; 
           
           case 3:
               salir=true;
                System.out.println(" ¡GRACIAS POR JUGAR!");
                System.out.println("    HASTA LUEGOOOO  ");
               break;
               
           default: 
               System.out.println("Su entrada no es valida");
               break;
          }
        }
    }
}
    
       
   
      
        
    
        
