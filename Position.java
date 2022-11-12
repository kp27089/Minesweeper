package minesweeper;

public class Position {

    private int x, y;

    public Position(int[] cords){
        this.x = cords[0];
        this.y = cords[1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
}
