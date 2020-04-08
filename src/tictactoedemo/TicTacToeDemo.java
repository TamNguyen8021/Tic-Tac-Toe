/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoedemo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Tam
 */
public class TicTacToeDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu");
        Text intro = new Text("TIC TAC TOE");
        intro.getStyleClass().add("title");
        Button bt2players = new Button("Multiplayer");
        bt2players.getStyleClass().add("button");
        Button btMachine = new Button("Single Player");
        btMachine.getStyleClass().add("button");
        Button exit = new Button("Quit Game");
        exit.getStyleClass().add("button");
        exit.setOnMouseClicked(e -> Platform.exit());
        vBox.getChildren().addAll(intro, btMachine, bt2players, exit);
        pane.setCenter(vBox);

        Scene scene = new Scene(pane, 1300, 990);
        scene.getStylesheets().add(
                getResource("tictactoe-menu-style.css")
        );
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);        

        bt2players.setOnMouseClicked(e -> {
            BorderPane board = drawBoard(bt2players, primaryStage, scene);

            Scene playScene = new Scene(board, 1300, 1000);
            playScene.getStylesheets().add(
                    getResource("tictactoe-playmode-style.css")
            );
            primaryStage.setScene(playScene);
        });
        btMachine.setOnMouseClicked(e -> {
            BorderPane board = drawBoard(btMachine, primaryStage, scene);
            
            Scene playScene = new Scene(board, 1300, 1000);
            playScene.getStylesheets().add(
                    getResource("tictactoe-playmode-style.css")
            );
            primaryStage.setScene(playScene);
            
            
        });
    }

    private String getResource(String resourceName) {
        return getClass().getResource(resourceName).toExternalForm();
    }
    
    BorderPane drawBoard(Button bt, Stage stage, Scene scene) {
        //Pane to hold cell
        GridPane cellPane = new GridPane();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cellPane.add(Cell.cell[i][j] = new Cell(bt), j, i);
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(cellPane);

        GridPane gameBoard = new GridPane();
        gameBoard.getStyleClass().add("gameBoard");
        Text score = new Text("SCORE");
        score.getStyleClass().add("score");
        Text xPlayer = new Text("X player");
        xPlayer.getStyleClass().add("label");
        Text oPlayer = new Text("O player");
        oPlayer.getStyleClass().add("label");
        Text xPlayerScore = new Text();
        xPlayerScore.setText("" + Cell.xWon);
        xPlayerScore.getStyleClass().add("textfield");
        Text oPlayerScore = new Text();
        oPlayerScore.setText("" + Cell.oWon);
        oPlayerScore.getStyleClass().add("textfield");
        Pane player1 = new Pane();
        player1.getStyleClass().add("player");
        Pane player2 = new Pane();
        player2.getStyleClass().add("player");
        Button titleScreen = new Button("Main Menu");
        titleScreen.getStyleClass().add("titleScreen");
        titleScreen.setOnMouseClicked(e -> stage.setScene(scene));
        gameBoard.add(score, 0, 0);
        gameBoard.add(player1, 0, 2);
        gameBoard.add(xPlayer, 0, 3);
        gameBoard.add(xPlayerScore, 1, 3);
        gameBoard.add(player2, 0, 5);
        gameBoard.add(oPlayer, 0, 6);
        gameBoard.add(oPlayerScore, 1, 6);
        gameBoard.add(titleScreen, 0, 7, 2, 1);
        borderPane.setRight(gameBoard);
        return borderPane;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
