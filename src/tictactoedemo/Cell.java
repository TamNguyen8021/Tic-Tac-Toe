/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoedemo;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author Tam
 */
public class Cell extends Pane {

    public static char whoseTurn = 'X';

    //Create and initialize cell
    public static Cell cell[][] = new Cell[20][20];
    //Token used for this cell    
    public char token = ' ';
    
    public static int xWon = 0;
    public static int oWon = 0;

    public Cell() {
    }

    public Cell(Button bt) {
        this.getStyleClass().add("cell");
        if (bt.getText().equalsIgnoreCase("Single Player")) {
            this.setOnMouseClicked(e -> handleMouseClickSinglePlayer());
        } else {
            whoseTurn = 'X';
            this.setOnMouseClicked(e -> handleMouseClickMultiplayer());
        }
    }

    public char getToken() {
        return token;
    }

    public void setToken(char c) {
        token = c;

        if (token == 'X') {
            Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
            line1.getStyleClass().add("xToken");
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
            line2.getStyleClass().add("xToken");
            line2.startYProperty().bind(this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));

            getChildren().addAll(line1, line2);
        } else if (token == 'O') {
            Circle circle = new Circle(this.getWidth() / 3);
            circle.getStyleClass().add("oToken");
            circle.centerXProperty().bind(this.widthProperty().divide(2));
            circle.centerYProperty().bind(this.heightProperty().divide(2));
            circle.radiusProperty().bind(this.widthProperty().divide(3));
            getChildren().add(circle);
        }
    }

    public static void resetBoard() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cell[i][j].setToken(' ');
                cell[i][j].getChildren().clear();
            }
        }
    }

    public void handleMouseClickSinglePlayer() {
        //If cell is empty and game is not over
        if (token == ' ' && whoseTurn != ' ') {
            setToken(whoseTurn);    //Set token in the cell   
            Machine.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 'O');
            Machine.placeAMove(Machine.returnBestMove(), 'O');
        } else if (token != ' ') {
            return;
        }
        //Check game status
        if (Machine.isWon('X')) {
            xWon += 1;
            whoseTurn = 'X';    //Game is over
            resetBoard();
        } else if(Machine.isWon('O')) {
            oWon += 1;
            whoseTurn = 'X';
            resetBoard();
        }
        else if (Machine.isGameOver()) {
            whoseTurn = 'X';    //Game is over
            resetBoard();
        }
    }

    public void handleMouseClickMultiplayer() {
        //If cell is empty and game is not over
        if (token == ' ' && whoseTurn != ' ') {
            setToken(whoseTurn);    //Set token in the cell
        } else if (token != ' ') {
            return;
        }
        //Check game status
        if (Machine.isWon('X')) {
            xWon += 1;
            whoseTurn = 'X';    //Game is over
            resetBoard();
        } else if(Machine.isWon('O')) {
            oWon += 1;
            whoseTurn = 'X';
            resetBoard();
        }
        else if (Machine.isGameOver()) {
            whoseTurn = 'X';    //Game is over
            resetBoard();
        } else {
            //Change the turn
            if (whoseTurn == 'X') {
                whoseTurn = 'O';
            } else {
                whoseTurn = 'X';
            }
        }
    }
}
