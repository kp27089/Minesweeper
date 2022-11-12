package minesweeper;

import java.nio.file.LinkPermission;
import java.util.*;

public class Board {
    private Random random = new Random();
    private static String[][] field;
    private static String[][] minesAround;
    private static final int fieldSize = 9;
    private static int input;
    private int numOfMines = 0;


    public Board(int input) {
        field = new String[fieldSize][fieldSize];
        minesAround = new String[fieldSize][fieldSize];
        this.input = input;
        fieldFill();


    }

    public boolean firstCheck(int[] firstCords) {
        if (isNumeric(minesAround[firstCords[0]][firstCords[1]])) {

            return true;
        }


        return false;
    }

    private void fieldFill() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = ".";
            }
        }
    }

    public void fieldPrint() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j].equals("X")) {
                    System.out.print(".");
                } else {
                    System.out.print(field[i][j]);
                }


            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void fieldPrint3() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {

                System.out.print(field[i][j]);


            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }


    public GameState stateOfCell(int[] chkVar) {
        if (field[chkVar[0]][chkVar[1]].equals(".") && isNumeric(minesAround[chkVar[0]][chkVar[1]])) {
            return GameState.NONE;
        } else if (field[chkVar[0]][chkVar[1]].equals("*") && isNumeric(minesAround[chkVar[0]][chkVar[1]])) {
            return GameState.MARKED;
        } else if (isNumeric(minesAround[chkVar[0]][chkVar[1]])) {
            return GameState.NUMBER;
        } else if (minesAround[chkVar[0]][chkVar[1]].equals("X") && !field[chkVar[0]][chkVar[1]].equals("*")) {
            //
            return GameState.MINE;
        } else if (field[chkVar[0]][chkVar[1]].equals("*")) {


            return GameState.MARKED;
        }
        return GameState.NONE;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }


    public void fillCords(int[] cords, GameState check, String mode) {
        if (mode.equals("mine")) {
            switch (check) {
                case NONE:
                    if (!(field[cords[0]][cords[1]] == "/")) {
                        field[cords[0]][cords[1]] = "*";
                    }

                    break;
                case MARKED:
                    field[cords[0]][cords[1]] = ".";
                    if (minesAround[cords[0]][cords[1]] == "X") {
                        numOfMines--;
                    }

                    break;
                case NUMBER:
                    field[cords[0]][cords[1]] = minesAround[cords[0]][cords[1]];
                    break;
                case MINE:
                    if (field[cords[0]][cords[1]].equals("*")) {
                        numOfMines--;
                        break;
                    } else {
                        field[cords[0]][cords[1]] = "*";
                        numOfMines++;
                    }


                    break;

            }
        } else if (mode.equals("free") && (isNumeric(minesAround[cords[0]][cords[1]]))) {
            field[cords[0]][cords[1]] = minesAround[cords[0]][cords[1]];

        } else if (mode.equals("free")) {
            fillFree(cords);
        }

    }

    private boolean boundsChecking(int x, int y) {
        if (x < 0 || y < 0 || x > field.length - 1 || y > field[0].length - 1) {
            return false;
        }
        return true;
    }

    private boolean isValidAndColorNumber(int x, int y) {
        if (x < 0 || y < 0 || x > field.length - 1 || y > field[0].length - 1) {
            return false;
        }
        if (field[x][y].equals("X")) {
            return false;
        }
        else if (isNumeric(minesAround[x][y])) {

            field[x][y] = minesAround[x][y];
            return false;
        } else if (field[x][y].equals("/")) {


            return false;
        }


        return true;
    }

    public void fillSlash(Position p) {
        int x1 = p.getX();
        int y1 = p.getY();
        field[x1][y1] = "/";
    }

    private void fillFree(int[] cords) {
        Deque<Position> deq = new ArrayDeque<>();
        ArrayList<Position> position = new ArrayList<>();

        position.add(new Position(cords));

        int counter = 0;
        int x = cords[0];
        int y = cords[1];
        deq.push(position.get(counter));
        Position first;
        while (!deq.isEmpty()) {
            int x1;
            int y1;
            first = deq.peek();
            x1 = first.getX();
            y1 = first.getY();
            fillSlash(first);

            deq.removeFirst();

            if (isValidAndColorNumber(x1 + 1, y1)) {
                deq.push(new Position(x1 + 1, y1));
            }
            if (isValidAndColorNumber(x1 - 1, y1)) {
                deq.push(new Position(x1 - 1, y1));
            }
            if (isValidAndColorNumber(x1, y1 + 1)) {
                deq.push(new Position(x1, y1 + 1));
            }
            if (isValidAndColorNumber(x1, y1 - 1)) {
                deq.push(new Position(x1, y1 - 1));
            }
            if (isValidAndColorNumber(x1 + 1, y1+1)) {
                deq.push(new Position(x1 + 1, y1+1));
            }
            if (isValidAndColorNumber(x1 + 1, y1-1)) {
                deq.push(new Position(x1 + 1, y1-1));
            }
            if (isValidAndColorNumber(x1-1, y1 + 1)) {
                deq.push(new Position(x1-1, y1 + 1));
            }
            if (isValidAndColorNumber(x1-1, y1 - 1)) {
                deq.push(new Position(x1-1, y1 - 1));
            }
        }

    }


    public GameState isWon() {
        if (numOfMines == input) {
            return GameState.WON;

        }
        return GameState.EMPTY;
    }


    public void fieldPrint2() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {
                if (minesAround[i][j].equals("X")) {
                    System.out.print(".");
                } else {
                    System.out.print(minesAround[i][j]);
                }


            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public void fieldPrintEmpty() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < fieldSize; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSize; j++) {

                System.out.print(".");


            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }


    public void fieldFillX() {
        fieldFill();
        int counter = 0;
        int randomX;
        int randomY;

        while (counter != input) {
            randomX = (random.nextInt(9));
            randomY = (random.nextInt(9));
            if (field[randomX][randomY].equals(".")) {
                field[randomX][randomY] = "X";
                counter++;
            }
        }
    }

    public void printAround() {

        for (int i = 0; i < field.length; i++) {

            System.arraycopy(field[i], 0, minesAround[i], 0, field[i].length);
        }

        for (int i = 0; i < minesAround.length; i++) {
            for (int j = 0; j < minesAround[i].length; j++) {
                if (minesAround[i][j].equals("X")) {
                    try {
                        if (minesAround[i - 1][j - 1].equals(".")) {
                            minesAround[i - 1][j - 1] = "1";
                        } else if (!minesAround[i - 1][j - 1].equals("X")) {
                            minesAround[i - 1][j - 1] = String.valueOf(Integer.parseInt(minesAround[i - 1][j - 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i][j - 1].equals(".")) {
                            minesAround[i][j - 1] = "1";
                        } else if (!minesAround[i][j - 1].equals("X")) {
                            minesAround[i][j - 1] = String.valueOf(Integer.parseInt(minesAround[i][j - 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i + 1][j - 1].equals(".")) {
                            minesAround[i + 1][j - 1] = "1";
                        } else if (!minesAround[i + 1][j - 1].equals("X")) {
                            minesAround[i + 1][j - 1] = String.valueOf(Integer.parseInt(minesAround[i + 1][j - 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i + 1][j].equals(".")) {
                            minesAround[i + 1][j] = "1";
                        } else if (!minesAround[i + 1][j].equals("X")) {
                            minesAround[i + 1][j] = String.valueOf(Integer.parseInt(minesAround[i + 1][j]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i - 1][j].equals(".")) {
                            minesAround[i - 1][j] = "1";
                        } else if (!minesAround[i - 1][j].equals("X")) {
                            minesAround[i - 1][j] = String.valueOf(Integer.parseInt(minesAround[i - 1][j]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i - 1][j + 1].equals(".")) {
                            minesAround[i - 1][j + 1] = "1";
                        } else if (!minesAround[i - 1][j + 1].equals("X")) {
                            minesAround[i - 1][j + 1] = String.valueOf(Integer.parseInt(minesAround[i - 1][j + 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i][j + 1].equals(".")) {
                            minesAround[i][j + 1] = "1";
                        } else if (!minesAround[i][j + 1].equals("X")) {
                            minesAround[i][j + 1] = String.valueOf(Integer.parseInt(minesAround[i][j + 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    try {
                        if (minesAround[i + 1][j + 1].equals(".")) {
                            minesAround[i + 1][j + 1] = "1";
                        } else if (!minesAround[i + 1][j + 1].equals("X")) {
                            minesAround[i + 1][j + 1] = String.valueOf(Integer.parseInt(minesAround[i + 1][j + 1]) + 1);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }


                }
            }
        }
    }

    public void numberToField(int[] x) {
        field[x[0]][x[1]] = minesAround[x[0]][x[1]];

    }

}
