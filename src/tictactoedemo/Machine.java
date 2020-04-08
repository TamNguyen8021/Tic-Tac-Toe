/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoedemo;

import java.util.ArrayList;
import java.util.List;
import static tictactoedemo.Cell.cell;

/**
 *
 * @author Tam
 */
public class Machine {

    static List<Position> availablePoints;
    static List<PointsAndScores> rootsChildrenScore = new ArrayList<>();

    public Machine() {

    }

    private static int changeInScore(int xToken, int oToken) {
        int change;
        if (xToken == 5) {
            change = 5;
        } else if ((xToken == 4 && oToken == 0) || (xToken == 4 && oToken == 1)) {
            change = 5;
        } else if ((xToken == 3 && oToken == 0) || (xToken == 3 && oToken == 1)) {
            change = 3;
        } else if ((xToken == 2 && oToken == 0) || (xToken == 2 && oToken == 1)) {
            change = 2;
        } else if ((xToken == 1 && oToken == 0) || (xToken == 1 && oToken == 1)) {
            change = 1;
        } else if (oToken == 5) {
            change = -5;
        } else if ((oToken == 4 && xToken == 0) || (oToken == 4 && xToken == 1)) {
            change = -5;
        } else if ((oToken == 3 && xToken == 0) || (oToken == 3 && xToken == 1)) {
            change = -3;
        } else if ((oToken == 2 && xToken == 0) || (oToken == 2 && xToken == 1)) {
            change = -2;
        } else if ((oToken == 1 && xToken == 0) || (oToken == 1 && xToken == 1)) {
            change = -1;
        } else {
            change = 0;
        }
        return change;
    }

    public static int evaluateBoard() {
        int score = 0;
        int blank;
        int X;
        int O;

        //Check all rows
        for (int i = 0; i < 20; i++) {
            blank = 0;
            X = 0;
            O = 0;
            for (int j = 0; j < 20; j++) {
                if (cell[i][j].getToken() == ' ') {
                    blank++;
                } else if (cell[i][j].getToken() == 'X') {
                    X++;
                } else {
                    O++;
                }
            }
            score += changeInScore(X, O);
        }

        //Check all columns
        for (int j = 0; j < 20; j++) {
            blank = 0;
            X = 0;
            O = 0;
            for (int i = 0; i < 20; i++) {
                if (cell[i][j].getToken() == ' ') {
                    blank++;
                } else if (cell[i][j].getToken() == 'X') {
                    X++;
                } else {
                    O++;
                }
            }
            score += changeInScore(X, O);
        }

        //Check diagonals, from top left
        for (int i = 0; i < cell.length - 4; i++) {
            blank = 0;
            X = 0;
            O = 0;
            for (int j = 0; j < cell[i].length - 4; j++) {
                if (cell[i][j].getToken() == ' ') {
                    blank++;
                } else if (cell[i][j].getToken() == 'X') {
                    X++;
                } else {
                    O++;
                }
            }
            score += changeInScore(X, O);
        }

        //Check diagonals, from top right
        for (int i = 0; i < cell.length - 4; i++) {
            blank = 0;
            X = 0;
            O = 0;
            for (int j = 4; j < cell[i].length; j++) {
                if (cell[i][j].getToken() == ' ') {
                    blank++;
                } else if (cell[i][j].getToken() == 'X') {
                    X++;
                } else {
                    O++;
                }
            }
            score += changeInScore(X, O);
        }
        return score;
    }

    public static List<Position> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (cell[i][j].getChildren().toString().equals("[]")) {
                    availablePoints.add(new Position(i, j));
                }
            }
        }
        return availablePoints;
    }

    public static void placeAMove(Position pos, char player) {
        cell[pos.x][pos.y].setToken(player);
    }

    public static Position returnBestMove() {
        int MIN = 100000;
        int best = -1;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (cell[i][j].getToken() == ' ') {
                    cell[i][j].getChildren().clear();
                }
            }
        }
        for (int i = 0; i < rootsChildrenScore.size(); i++) {
            if (MIN > rootsChildrenScore.get(i).score) {
                MIN = rootsChildrenScore.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScore.get(best).pos;
    }

    public static boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (isWon('X') || isWon('O') || getAvailableStates().isEmpty());
    }

    public static boolean isWon(char token) {
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length - 4; j++) {
                if (cell[i][j].getToken() == token
                        && cell[i][j].getToken() == cell[i][j + 1].getToken()
                        && cell[i][j].getToken() == cell[i][j + 2].getToken()
                        && cell[i][j].getToken() == cell[i][j + 3].getToken()
                        && cell[i][j].getToken() == cell[i][j + 4].getToken()) {
                    return true;
                }
            }
        }
        for (int j = 0; j < cell[0].length; j++) {
            for (int i = 0; i < cell.length - 4; i++) {
                if (cell[i][j].getToken() == token
                        && cell[i][j].getToken() == cell[i + 1][j].getToken()
                        && cell[i][j].getToken() == cell[i + 2][j].getToken()
                        && cell[i][j].getToken() == cell[i + 3][j].getToken()
                        && cell[i][j].getToken() == cell[i + 4][j].getToken()) {
                    return true;
                }
            }
        }

        //Check for win diagonally, from top left
        for (int i = 0; i < cell.length - 4; i++) {
            for (int j = 0; j < cell[i].length - 4; j++) {
                if (cell[i][j].getToken() == token
                        && cell[i][j].getToken() == cell[i + 1][j + 1].getToken()
                        && cell[i][j].getToken() == cell[i + 2][j + 2].getToken()
                        && cell[i][j].getToken() == cell[i + 3][j + 3].getToken()
                        && cell[i][j].getToken() == cell[i + 4][j + 4].getToken()) {
                    return true;
                }
            }
        }

        //Check for win diagonally, from top right
        for (int i = 0; i < cell.length - 4; i++) {
            for (int j = 4; j < cell[i].length; j++) {
                if (cell[i][j].getToken() == token
                        && cell[i][j].getToken() == cell[i + 1][j - 1].getToken()
                        && cell[i][j].getToken() == cell[i + 2][j - 2].getToken()
                        && cell[i][j].getToken() == cell[i + 3][j - 3].getToken()
                        && cell[i][j].getToken() == cell[i + 4][j - 4].getToken()) {
                    return true;
                }
            }
        }
        return false;
    }

    static int uptoDepth = -1;

    public static int alphaBetaMinimax(int alpha, int beta, int depth, char turn) {

        if (beta <= alpha) {
            if (turn == 'X') {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (depth == uptoDepth || isGameOver()) {
            return evaluateBoard();
        }

        if (getAvailableStates().isEmpty()) {
            return 0;
        }

        if (depth == 0) {
            rootsChildrenScore.clear();
        }

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < getAvailableStates().size(); i++) {
            Position pos = getAvailableStates().get(i);

            int currentScore = 0;

            if (turn == 'X') {
                placeAMove(pos, 'X');
                currentScore = alphaBetaMinimax(alpha, beta, depth + 1, 'O');

                maxValue = Math.max(maxValue, currentScore);

                //Set alpha
                alpha = Math.max(currentScore, alpha);
            } else if (turn == 'O') {
                placeAMove(pos, 'O');
                currentScore = alphaBetaMinimax(alpha, beta, depth + 1, 'X');

                minValue = Math.min(minValue, currentScore);

                //Set beta
                beta = Math.min(currentScore, beta);
                rootsChildrenScore.add(new PointsAndScores(currentScore, pos));
            }
            //reset board
            cell[pos.x][pos.y].setToken(' ');

            //If a pruning has been done, don't evaluate the rest of the sibling states
            if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) {
                break;
            }
        }
        return turn == 'X' ? maxValue : minValue;
    }
}
