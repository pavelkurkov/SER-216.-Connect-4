package ui;
import java.util.Scanner;
import core.Connect4;
import core.Connect4ComputerPlayer;
import java.util.InputMismatchException;

/** 
 * Description: This class contains methods that are used to interact with a user.
 *
 * @author Pavel Kurkov
 * @version 2.0. November 2, 2019
 */
public class Connect4TextConsole {
    /** A variable that contains the game board.*/
    private Connect4 board;
    
    /** Scanner used to accept inputs from a user */
    Scanner in = new Scanner(System.in);
    
    /** Object of a class that generates computer moves */
    private Connect4ComputerPlayer comp;
    
    /**
     * Default constructor, creates a new Connect4 object.
     */
    public Connect4TextConsole(){
        this.board = new Connect4(); 
        this.comp = new Connect4ComputerPlayer(this.board);
    }
    
    /**
     * Prints the game board to the screen
     */
    public void displayBoard(){
        System.out.println(board.toString());
    }
    
    /**
     * Starts a new game where PlayerX goes first. Calls makeMove() method.
     * Allows choice to play against computer or another player
     */
    public void newGame()throws InputMismatchException{
        String token;              
        token = "X";
        displayBoard();
        System.out.println("Begin Game. Enter 'P' if you want to play against another player; Enter 'C' if you want to play against computer.");         
        String choice = in.nextLine();
        if (choice.equals("p") || choice.equals("P")){
            makeMove(token, false);
        } else if (choice.equals("c") || choice.equals("C")){
            System.out.println("Start game against computer.");
            makeMove(token, true);
        } else {
            System.out.println("Invalid choice. Please try again!");
            newGame();
        }       
    }
   
    /**
     * This method asks user to pick a column, and then check the validity of the users choice.
     * It places a new piece on the board if there is an available space, checks for a winner
     * and checks for a Draw. When the move is done it changes piece and calls itself.
     * Supports a game against computer opponent.
     * @param token Represents a piece that will be put on the board during this move
     * @param playVsComp Parameter that enables game against computer
     */
    public void makeMove(String token, boolean playVsComp) throws InputMismatchException {        
        int choice;
        System.out.println("Player" + token + " - your turn. Please select a column number 1-7");
        if (!in.hasNextInt()) {
            String input = (String) in.next();
            System.out.println(input + " is not a valid number.");
            makeMove(token, playVsComp);
        }
        choice = in.nextInt();
        if (choice < 1 || choice > 7){
            System.out.println("Invalid choice, you should choose a number between 1 and 7. Try again!");
            makeMove(token, playVsComp);
        } else {
            if (!board.getPiece(5, choice).equals(" |")){
                System.out.println("This column is full, try choosing another one");
                makeMove(token, playVsComp);
            } else { 
                board.addPiece(choice, token);
                displayBoard();
                if (board.checkForWinner()){
                    System.out.println("Player " + token + " won the Game!");
                    System.exit(0);
                }
                if (board.checkForDraw()){
                    System.out.println("It is a draw!");
                    System.exit(0);
                }
                if (token.equals("X")){
                    token = "O";                    
                } else {  
                    token = "X";
                }
                if (playVsComp){
                    int moved = comp.computerMove(token);
                    System.out.println("Computer put an O into column number " + moved);
                    displayBoard();
                    if (board.checkForWinner()){
                        System.out.println("Player " + token + " won the Game!");
                        System.exit(0);
                    }
                    if (board.checkForDraw()){
                        System.out.println("It is a draw!");
                        System.exit(0);
                    }
                    token = "X";
                } 
                makeMove(token, playVsComp);               
            }
        }        
    }
         
    
   /**
    * Entry point for the program
    * @param args The command line arguments
    */
    public static void main(String[] args) {        
        Connect4TextConsole board = new Connect4TextConsole();
        board.newGame();
    }
}
