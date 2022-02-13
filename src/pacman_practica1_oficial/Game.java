package pacman_practica1_oficial;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Isaa
 */

public class Game { //Clase game, genera el tablero, posiciona los personasjes y les da moviiento
    //Atributos publicos, tablero de juego e items a posicionar
    public String gameBoard[][] = {{" "}}; 
    public String items[] = {"#", "$", "@"};
    //Atributos que guardan el numero de filas y columnas
    public int rows = 0;
    public int columns = 0;
    //Atributos que guardan el puntaje y movimiento
    public int score = 10;
    public int movements = 0;
    //Atributos que guardan las coordenadas del jugador
    public int playerRow = 0; 
    public int playerColumn = 0; 
   //Colores del tablero
    public String defColor = "\u001B[0m";
    public String playerColor = "\u001B[33m";
    public String badItemColor = "\u001B[31m";
    public String goodItemColor = "\u001B[37m";
    public String superItemColor = "\u001B[36m";
    public String wallColor = "\u001B[34m";
    
    //Metodo constructor que asiga no. de filas y columnas
    public Game(int rowsY, int columnsX){
        rows = rowsY;
        columns = columnsX;
    }
    //Metodo que genera el tablero
    public String[][] generateBoard() {
        //filasTunel=Fila donde inicia el tunel
        int filasTunel = rows/2;
        if (rows < 13){
            filasTunel = rows/3;
        } 
               
        String[][] matrix = new String [rows][columns];
        //Recorrer y asignar strigs a las coordenadas
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                if ((i == 0) || (i == rows-1)){
                    matrix[i][j] = "*";
                } else if (((i>=filasTunel) && (i<=filasTunel+2)) && ((j==0) || (j==columns-1))){
                    matrix[i][j] = " ";
                } else if ((j == 0) || (j==columns-1)){
                    matrix[i][j] = "*";
                } else {
                    matrix[i][j] = " ";
                } 
            }
        }
        //
        Random random = new Random();
        //ciclo para se ejecuta 5 veces, 3=paredes verticales y 2=paredes horizontales
        for (int loop=0; loop<5; loop++){
            //fila y columna aleatoria
            int rowRandom = random.nextInt(rows-3 + 1) + 1;
            int columnRandom = random.nextInt(columns-3 + 1) + 1;
            int range[] = {columnRandom, rowRandom};//coordenadas
            matrix[range[1]][range[0]] = "*";//se inserta punto random
            if (loop<=2){//paredes verticales
                int amountAddWall = (rows-2)-rowRandom; 
                for (int r=1; r<=amountAddWall; r++){
                    matrix[range[1]+r][range[0]] = "*";
                    if (r==5){
                        break;
                    }
                }
            } else {//paredes horizontales
                int amountAddWall = (columns-2)-columnRandom; 
                for (int c=1; c<=amountAddWall; c++){
                    matrix[range[1]][range[0]+c] = "*";
                    if (c==5){
                        break;
                    }
                }
            }
        }
        
        
        return matrix;
    }
    //función para validar la posición y si está libre insertar el simbolo
    public void validateCoordAndRandomAAddSymbol(String[][] array, int rowY, int columnX, String symbol) {
        Random random = new Random();
        String coordSymbol = array[rowY][columnX];//obtengo el símbolo que está en la posición

        if (coordSymbol == " "){
            array[rowY][columnX] = symbol;
        } else {
            while (
                    (coordSymbol != " ") || 
                    (coordSymbol == symbol)
                ){
                    //obtener nueva posición aleatoria
                    rowY = random.nextInt(array.length-1); 
                    columnX = random.nextInt(array[0].length-1);
                    coordSymbol = array[rowY][rowY];
            }
            array[rowY][columnX] = symbol;
        }
    }
    //Posicionar a pacman y a los items en una posición vacía
    public void initBoard(){
        Random random = new Random();
        Game game = new Game(rows, columns);//filas y columnas del contructor
        String board[][] = game.generateBoard();
        //coordenada aleatoria para el jugador 
        playerRow = random.nextInt(board.length-1); 
        playerColumn = random.nextInt(board[0].length-1);
        game.playerRow = playerRow;
        game.playerColumn = playerColumn;
        game.validateCoordAndRandomAAddSymbol(board, playerRow, playerColumn, "V");
        for (int item=0; item<items.length; item++) {
            for (int nitem=0; nitem<=2; nitem++){
                //coordenada aletoria para los items
                int row = random.nextInt(board.length-1); 
                int column = random.nextInt(board[0].length-1); 
                game.validateCoordAndRandomAAddSymbol(board, row, column, items[item]);
            }
        }
        gameBoard = board;
    }
    //interacción con paredes
    public int[] collisions(String[][] array, int rowY, int columnX){
        Game game = new Game(rows, columns);
        String posSymbol = "";
        try{//para evitar problemas con el tunel
            posSymbol = array[rowY][columnX];
        } catch (ArrayIndexOutOfBoundsException exc) {
            ;
        }
        
        if(posSymbol == "*"){
            int ret[] = {1, 0};
            return ret;
        } else if(posSymbol == "#"){
            game.regenerateItem(array, "#");
            int ret[] = {0, -10};
            return ret;
        } else if(posSymbol == "$"){
            game.regenerateItem(array, "$");
            int ret[] = {0, 15};
            return ret;
        } else if(posSymbol == "@"){
            game.regenerateItem(array, "@");
            int ret[] = {0, 10};
            return ret;
        } else {
            int ret[] = {0, 0};
            return ret;
        }
    }
    //regenerar los intems en una nueva posición aleatoria
    public void regenerateItem(String[][] array, String symbol){
        Game game = new Game(rows, columns);
        Random random = new Random();
        //coordenada aleatoria
        int row = random.nextInt(array.length-1); 
        int column = random.nextInt(array[0].length-1);
        game.validateCoordAndRandomAAddSymbol(array, row, column, symbol);
    }
    //colorear los simbolos y las paredes
    public String colorBoard(String item) {
        switch (item) {
            case "*":
                return wallColor;
            case "#":
                return badItemColor;
            case "@":
                return goodItemColor;
            case "$":
                return superItemColor;
            case "V":
                return playerColor;
        }
        return defColor;
    }
    //mostrar tablero
    public void showBoard(String[][] array) {
        for (int x=0; x < array.length; x++) {
            for (int y=0; y < array[x].length; y++) {
                if (y == array[x].length-1) {
                    System.out.println(colorBoard(array[x][y]) + array[x][y] + defColor);
                } else {
                    System.out.print(colorBoard(array[x][y]) + array[x][y] + defColor);
                }
            }
        }
    }
    //Decide si pacman se mueve por la colisión 
    public void moveTo(char direction){
        Game game = new Game(rows, columns);
        if(direction == 'W'){
            gameBoard[playerRow][playerColumn] = " ";
            int colsns[] = game.collisions(gameBoard, playerRow-1, playerColumn);
            if (colsns[0] == 0){
                score += colsns[1];
                playerRow--;
                movements++;
            }
        } else if (direction == 'S') {
            gameBoard[playerRow][playerColumn] = " ";
            int colsns[] = game.collisions(gameBoard, playerRow+1, playerColumn);
            if (colsns[0] == 0){
                score += colsns[1];
                playerRow++;
                movements++;
            }
        } else if (direction == 'D'){
            gameBoard[playerRow][playerColumn] = " ";
            int colsns[] = game.collisions(gameBoard, playerRow, playerColumn+1);
            if (colsns[0] == 0){
                score += colsns[1];
                playerColumn++;
                movements++;
            }
        } else if (direction == 'A') {
            gameBoard[playerRow][playerColumn] = " ";
            int colsns[] = game.collisions(gameBoard, playerRow, playerColumn-1);
            if (colsns[0] == 0){
                score += colsns[1];
                playerColumn--;
                movements++;
            }
        }
    }

    public void run() {
        Game game = new Game(rows, columns);
        Scanner input = new Scanner(System.in);

        game.initBoard();
        char command = '0';

        while (
                (command != 'M') &&
                (game.score > 0) &&
                (game.score < 100)
            ){
            try {
                game.gameBoard[game.playerRow][game.playerColumn] = "V";
            } catch (ArrayIndexOutOfBoundsException exc){
                int row = game.playerRow;
                int column = game.playerColumn;
                String array[][] = game.gameBoard;

                if (column > array[0].length-1){
                    game.playerColumn = 0;
                    array[row][game.playerColumn] = "V";
                    array[row][array.length-1] = " ";
                } else if (column <= 0){
                    game.playerColumn = array[0].length-1;
                    array[row][game.playerColumn] = "V";
                    array[row][0] = " ";
                }
            }
            score = game.score;
            movements = game.movements;
            
            System.out.println("Score: " + game.score);
            System.out.println("Movements: " + game.movements);
            
            game.showBoard(game.gameBoard);
            
            System.out.print(">>>");
            command = input.next().charAt(0);

            game.moveTo(command);
            if (command == 'M') {
                break;
            }
        }
        score = game.score;
    }
}