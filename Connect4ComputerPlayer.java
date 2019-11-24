package core;
import java.lang.Math;

/** 
 * Description: This class contains method that allows player to play vs computer.
 *
 * @author Pavel Kurkov
 * @version 1.0. November 2, 2019
 */
public class Connect4ComputerPlayer {
    /** Object of a  Connect4 class. Contains a game board */
    protected Connect4 board;
    
    /**
     * Constructor for the Connect4ComputerPlayer class. Initiates board variable
     * @param board Represents a game board passed from the Connect4 object
     */
    public Connect4ComputerPlayer (Connect4 board){
        this.board = board;
    }    
    
    /** 
     * This method allows computer to choose random columns to play against a human opponent
     * @param token Represents a piece played by the computer, i.e "O" 
     */
    public int computerMove(String token){
        int randomColumn = (int)(Math.random()*10);
        while (randomColumn < 1 || randomColumn > 7 || !board.getPiece(5, randomColumn).equals(" |")){
            randomColumn = (int)(Math.random()*10);
        }
        board.addPiece(randomColumn, token);   
        return randomColumn;
    }
}
