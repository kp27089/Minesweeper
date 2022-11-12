package minesweeper;

public enum GameState {
    NONE,
    NUMBER,
    MINE,
    EMPTY,
    MARKED,
    WON,
    LOSS;

    public static String message(GameState stat){
        if (stat == GameState.WON){
            return "Congratulations! You found all the mines!";
        } else if(stat == GameState.NUMBER){
            return "There is a number here!";
        }else if(stat==GameState.LOSS){
            return "You stepped on a mine and failed!";
        }
        return null;
    }

}
