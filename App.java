package minesweeper;

import java.util.Scanner;

public class App {

    Scanner scanner = new Scanner(System.in);
    private int mines;
    Board board;
    private String cordinates[] = new String[3];
    private int cords[] = new int[2];
    GameState status = GameState.EMPTY;


    public void run() {

        System.out.print("How many mines do you want on the field?\n");
        mines = scanner.nextInt();
        System.out.println();
        board = new Board(mines);
        board.fieldFillX();
        boolean firstRun = true;


        board.printAround();
        board.fieldPrint();

        do{
            System.out.println("\nSet unset mines marks or claim a cell as free: ");
            try{
                cordinates =        scanner.nextLine().split(" ");
            }catch(ArrayIndexOutOfBoundsException e){

            }

            if(!(cordinates[0].equals(""))&&!(cordinates[1].equals(""))){

                cords[0] = Integer.parseInt(cordinates[1])-1;
                cords[1] = Integer.parseInt(cordinates[0])-1;

                while(board.firstCheck(cords) && firstRun){

                    board.fieldFillX();
                    board.printAround();
                    if(board.stateOfCell(cords)!=GameState.MINE&& board.stateOfCell(cords)!=GameState.NUMBER){
                        firstRun = false;
                    }
                }
                firstRun = false;
                status = board.stateOfCell(cords);

                if(status==GameState.MINE && cordinates[2].equals("free")){
                    status=GameState.LOSS;
                    board.fieldPrint3();
                    break;
                }


                else if(status!=GameState.NUMBER) {
                    board.fillCords(cords, status, cordinates[2]);
                    // board.fieldPrint2();
                   // board.minesweeperCheck();
                    board.fieldPrint();
                    //System.out.println();
                    //board.fieldPrint2();
                    status = board.isWon();


                } else if(status==GameState.NUMBER&&cordinates[2].equals("free")){
                    board.numberToField(cords);

                    board.fieldPrint();
                    status = GameState.EMPTY;
                }
                else{

                    System.out.println(GameState.message(status));
                    board.fieldPrint();
                    status = GameState.EMPTY;
                }
            }

        }while(status != GameState.WON);
        System.out.println(GameState.message(status));

    }
}
