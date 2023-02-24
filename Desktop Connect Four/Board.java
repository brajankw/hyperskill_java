package four;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private static HashMap<String, ArrayList<Cell>> cells;

    private static ArrayList<Cell> movesX;
    private static ArrayList<Cell> movesO;

    private final static int ROWS = 6;
    private final static int COLS = 7;

    private final static String EMPTY_FIELD = " ";

    private final static String X_MOVE = "X";

    private final static String O_MOVE = "O";

    private final static String GREEN_COLOR = "green";

    private final static String RED_COLOR = "red";
    private static boolean turn = true;
    private static boolean win = false;


    public Board(HashMap<String, ArrayList<Cell>> board) {
        cells = board;
        movesX = new ArrayList<>();
        movesO = new ArrayList<>();
    }

    public String toString() {
        return cells.toString();
    }

    public static void move(String field) {
        ArrayList<Cell> column = cells.get(String.valueOf(field.charAt(6)));
        for (Cell cell : column) {
            if (cell.getText().equals(EMPTY_FIELD) && !win) {
                if (turn) {
                    cell.setText(X_MOVE);
                    movesX.add(cell);
                } else {
                    cell.setText(O_MOVE);
                    movesO.add(cell);
                }
                Board.winCheck();
                turn = !turn;
                return;
            }
        }
    }

    public static void reset() {
        for (Cell cell : movesX) {
            cell.setText(EMPTY_FIELD);
            cell.paintCell(GREEN_COLOR);
        }
        for (Cell cell : movesO) {
            cell.setText(EMPTY_FIELD);
            cell.paintCell(GREEN_COLOR);
        }
        movesX.clear();
        movesO.clear();
        win = false;
        turn = true;
    }

    private static void winCheck() {
        if (turn) {
            if (rowCheck(movesX) || colCheck(movesX) || diagonalCheck()) {
                win = true;
            }
        } else {
            if (rowCheck(movesO) || colCheck(movesO) || diagonalCheck()) {
                win = true;
            }
        }
    }


    private static boolean rowCheck(ArrayList<Cell> moves) {
        for (Cell cell : moves) {
            if (cell.getName().charAt(6) == 'D') {
                int row = Integer.parseInt(String.valueOf(cell.getName().charAt(7))); //index
                ArrayList<Cell> winCells = new ArrayList<>();

                for (String key : cells.keySet()) {
                    Cell checkingCell = cells.get(key).get(row - 1);
                    if (cellCompare(cell, winCells, checkingCell)) return true;
                }
            }
        }

        return false;
    }

    private static boolean cellCompare(Cell cell, ArrayList<Cell> winCells, Cell checkingCell) {
        if (checkingCell.getText().equals(cell.getText())) {
            winCells.add(checkingCell);
            if (winCells.size() == 4) {
                for (Cell winningCell : winCells) {
                    winningCell.paintCell(RED_COLOR);
                }
                return true;
            }
        } else {
            winCells.clear();
        }
        return false;
    }


    private static boolean colCheck(ArrayList<Cell> moves) {
        for (Cell cell : moves) {
            if (cell.getName().charAt(7) == '4') {
                String key = String.valueOf(cell.getName().charAt(6)); //index
                ArrayList<Cell> winCells = new ArrayList<>();

                for (Cell checkingCell : cells.get(key)) {
                    if (cellCompare(cell, winCells, checkingCell)) return true;
                }
            }
        }

        return false;
    }

    private static boolean diagonalCheck() {
        for (int col = 0; col < COLS - 3; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                String token = cells.get(getColumnKey(col)).get(row).getText();
                if (!token.equals(EMPTY_FIELD)
                        && token.equals(cells.get(getColumnKey(col + 1)).get(row + 1).getText())
                        && token.equals(cells.get(getColumnKey(col + 2)).get(row + 2).getText())
                        && token.equals(cells.get(getColumnKey(col + 3)).get(row + 3).getText())) {
                    for (int i = 0; i < 4; i++) {
                        cells.get(getColumnKey(col + i)).get(row + i).paintCell(RED_COLOR);
                    }
                    return true;
                }
            }
        }
        for (int col = 0; col < COLS - 3; col++) {
            for (int row = 3; row < ROWS; row++) {
                String token = cells.get(getColumnKey(col)).get(row).getText();
                if (!token.equals(EMPTY_FIELD)
                        && token.equals(cells.get(getColumnKey(col + 1)).get(row - 1).getText())
                        && token.equals(cells.get(getColumnKey(col + 2)).get(row - 2).getText())
                        && token.equals(cells.get(getColumnKey(col + 3)).get(row - 3).getText())) {
                    for (int i = 0; i < 4; i++) {
                        cells.get(getColumnKey(col + i)).get(row - i).paintCell(RED_COLOR);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private static String getColumnKey(int col) {return (String) cells.keySet().toArray()[col];}

}
