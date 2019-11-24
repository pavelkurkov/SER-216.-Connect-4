
package ui;
import ui.Connect4TextConsole;
import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
/** 
 * Description: This class contains methods that implement graphic User interface.
 *
 * @author Pavel Kurkov
 * @version 1.0. November 12, 2019
 */
public class Connect4GUI extends Application{
    private static Connect4 board = new Connect4();
    private String symbol = "X";
    private Connect4ComputerPlayer compPlayer = new Connect4ComputerPlayer(board);
  
    /**
     * Method that constructs the primary stage for the program
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        BorderPane mainPane = new BorderPane();
        HBox buttonPane = new HBox(15);
        VBox textAndButtons = new VBox(10);        
        Text message = new Text("Do you want to use graphic interface or console?");
        textAndButtons.getChildren().add(message);
        textAndButtons.getChildren().add(buttonPane);
        textAndButtons.setAlignment(Pos.CENTER);
        buttonPane.setAlignment(Pos.CENTER);
        mainPane.setCenter(textAndButtons);
        Button guiButton = new Button("Graphic interface");
        Button consoleButton = new Button("Console");
        consoleButton.setOnAction(actionEvent -> launchConsole(primaryStage));
        guiButton.setOnAction(actionEvent -> playerOrComp(primaryStage));
        buttonPane.getChildren().add(guiButton);
        buttonPane.getChildren().add(consoleButton);
        Scene scene = new Scene(mainPane, 300, 100);
        primaryStage.setTitle("Connect 4 Game"); 
        primaryStage.setScene(scene);
        primaryStage.setTitle("GUI or Console?");
        primaryStage.show();        
    }
    
    /**
     * Displays choice of the opponent
     * @param stage
     */
    public void playerOrComp(Stage stage){
        stage.close();
        Stage questionStage = new Stage();
        VBox mainPane =  new VBox(5);
        Text text = new Text ("Do you want to play against Computer or Another Player?");
        Button comp = new Button("Computer");
        comp.setOnAction(actionEvent->guiGame(questionStage, true));
        Button player = new Button("Player");
        player.setOnAction(actionEvent->guiGame(questionStage,  false));
        HBox buttonPane = new HBox(5);
        buttonPane.getChildren().addAll(player, comp);
        buttonPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().addAll(text, buttonPane);
        mainPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainPane, 350, 100);
        questionStage.setScene(scene);
        questionStage.setTitle("Choose your opponent!");
        questionStage.show();
    }
    
    /**
     * Main stage for the game, displays game board and a field for users choices
     * @param stage
     * @param compOpponent true when play against computer was chosen
     */
    public void guiGame(Stage stage, boolean compOpponent){
        stage.close();
        Stage newStage = new Stage();
        VBox mainPane = new VBox(5);
        Scene scene = new Scene(mainPane, 450, 250);
        Text textBoard = new Text(board.toString());
        textBoard.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 20));
        mainPane.getChildren().add(textBoard);
        mainPane.setAlignment(Pos.CENTER);
        Text message = new Text("New Game! Player" + symbol + " please select a column!");
        mainPane.getChildren().add(message);
        TextField field = new TextField();
        field.setOnAction(actionEvent -> makeMove(newStage, symbol, compOpponent, field, field.getText(), message, textBoard));
        HBox textPane = new HBox(5);
        textPane.getChildren().add(field);
        textPane.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(textPane);
        newStage.setScene(scene);
        newStage.setTitle("Connect4");
        newStage.show();         
    }
    
    /**
     * Changes token from "X" to "O" and vice versa.
     * @param token Token to be changed
     * @return New value of token
     */
    public String changeToken(String token){
       if (token.equals("X")){
           symbol = "O";           
           return symbol;                    
        } else { 
           symbol = "X";
           return symbol;
        }
   }
    
    /**
     * Method that uses game logic to make a move on the game board
     * @param stage Game stage
     * @param symbol Current token
     * @param compOpponent True when player plays against computer
     * @param field Text field for users input
     * @param stringInput User's input
     * @param message Space to display a message to the user
     * @param textBoard Displays game board
     */
    public void makeMove(Stage stage, String symbol, boolean compOpponent, TextField field, String stringInput, Text message, Text textBoard){
        String note;
        if (!stringInput.matches("\\d+")){
            message.setText(stringInput + " is not a valid choice, pleasy try again!");
            field.setText("");
        }
        if (stringInput.matches("\\d+")){
            int input = Integer.parseInt(stringInput);
            if (input > 7 || input < 1){
                message.setText("Please choose number from 1 to 7!");
                field.setText("");
            } else {                
                if (!board.getPiece(5, input).equals(" |")){
                    message.setText("This column is full, try choosing another one. ");
                    field.setText("");                    
                } else { 
                    board.addPiece(input, symbol);
                    textBoard.setText(board.toString());   
                    field.setText("");
                    message.setText("");
                    if (board.checkForWinner()){
                        note = "Player " + symbol + " won the Game!";
                        symbol = changeToken(symbol);
                        endGame(stage, textBoard, note);
                    }
                    if (board.checkForDraw()){
                        note = "It is a draw!";
                        symbol = changeToken(symbol);
                        endGame(stage, textBoard, note);
                    }
                    symbol = changeToken(symbol);
                    if (compOpponent){
                        int moved = compPlayer.computerMove(symbol);
                        message.setText("Computer put an O into column number " + moved + ". ");
                        textBoard.setText(board.toString());
                        if (board.checkForWinner()){
                            note = "Player " + symbol + " won the Game!";                            
                            endGame(stage, textBoard, note);
                        }
                        if (board.checkForDraw()){
                            note = "It is a draw!";                            
                            endGame(stage, textBoard, note);
                        }
                        symbol = changeToken(symbol);
                    }
                    message.setText(message.getText() + "Player" + symbol + " please select a column");                    
                }                
            }                        
        }
    } 

    /**
     * Gives a choice of the new game
     * @param stage
     * @param textBoard
     * @param note
     */
    public void endGame(Stage stage, Text textBoard, String note){
        stage.close();
        board = new Connect4();
        symbol = "X";
        textBoard.setText(board.toString());
        Stage buttonStage = new Stage();
        VBox pane = new VBox(10);
        pane.setAlignment(Pos.CENTER);
        Text message = new Text(note);
        pane.getChildren().add(message);
        Text text = new Text("Do you want to start a new Game?");
        pane.getChildren().add(text);
        HBox buttonPane = new HBox(10);
        buttonPane.setAlignment(Pos.CENTER);
        pane.getChildren().add(buttonPane);
         Button yes = new Button("Yes!");
        buttonPane.getChildren().add(yes);    
        yes.setOnAction(actionEvent -> playerOrComp(buttonStage));
        Button no = new Button("No!");
        no.setOnAction(actionEvent -> System.exit(0));
        buttonPane.getChildren().add(no);
        Scene scene = new Scene(pane, 300, 80);
        buttonStage.setTitle("Game Over!"); 
        buttonStage.setScene(scene); 
        buttonStage.show(); 
    }

    /**
     * Starts console version of the game
     * @param primaryStage
     */
    public void launchConsole(Stage primaryStage){
        primaryStage.close();
        Connect4TextConsole board = new Connect4TextConsole();
        board.newGame();
    }
       
    /**
     * Entry point to the program
     * @param args
     */
    public static void main(String[] args) {
    launch(args);
  }
    }