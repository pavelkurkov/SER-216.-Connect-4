package core;
/** 
 * Description: This class contains the backend logic for the simple, console based 
 * Connect 4 game.
 *
 * @author Pavel Kurkov
 * @version 2.0. November 2, 2019
 */
public class Connect4 {
    
    /** This variable represents a board for the Connect 4 Game */
    protected String board [][];
    
    /**
     * Default constructor, generates an empty board.
     */
    public Connect4(){
        board = new String [6][8];
        for (int i = 0; i < 6; i++ ){
            for (int j = 0; j < 8; j++){
                board [i][j] = " |";
            }
        }
        
    }
    
    /**
     * This method adds a new piece to the board.
     * @param column Column where new piece is added.
     * @param token Represents a piece that is added to the board.
     */
    public void addPiece(int column, String token) throws ArrayIndexOutOfBoundsException {
        for (int i = 0; i < 6; i++){
            if (board[i][column] == " |"){
                board[i][column] = token + "|";
                return;
            }
        }        
    }
    
    /**
     * Returns a piece from a specified position on the board.
     * @param row Row of the specified piece
     * @param column Column of the specified piece
     * @return Value of the piece, i.e "X"
     */
    public String getPiece  (int row, int column)throws ArrayIndexOutOfBoundsException{
        return board[row][column];
    }
    
    /**
     * Method to check if there is a winner in the Game
     * @return Returns true if there is a winner or false if there is not.
     */
    public boolean checkForWinner(){
        return  checkRows() || checkColumns() || checkDiagonals();        
    }
    
    /**
     * Checks if there is a draw in the game.
     * @return Returns true if there is no more moves and no one won. 
     */
    public boolean checkForDraw(){
        for (int i = 0; i < 6; i++ ){
            for (int j = 1; j < 7; j++){
                if(board[i][j].equals(" |")){
                    return false;
                }
            }           
        }
        return true;
    }
    
    /**
     * Checks if any player connected 4 pieces in a column
     * @return Returns true if there sis 4 pieces in a vertical direction.
     */
    private boolean checkColumns(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
               if (board[i][j].equals(board[i+1][j])){
                   if (board[i][j].equals(board[i+2][j])){
                       if (board[i][j].equals(board[i+3][j])){
                           if (board[i][j].equals("X|") || board[i][j].equals("O|")){
                               return true;
                           }
                       }
                   }
               }
            
            }
        }
        return false;
    }
       
    /**
     * Checks if any player connected 4 pieces in a row
     * @return Returns true if there sis 4 pieces in a horizontal direction.
     */
    private boolean checkRows(){
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 4; j++){
               if (board[i][j].equals(board[i][j+1])){
                   if (board[i][j].equals(board[i][j+2])){
                       if (board[i][j].equals(board[i][j+3])){
                           if (board[i][j].equals("X|") || board[i][j].equals("O|")){
                               return true;
                           }
                       }
                   }
               }
            
            }
        }
        return false;
    }
    
    /**
     * Checks if any player connected 4 pieces in a diagonal
     * @return Returns true if there sis 4 pieces in a diagonal direction.
     */
    private boolean checkDiagonals(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                if (board[i][j].equals(board[i+1][j+1])){
                    if (board[i][j].equals(board[i+2][j+2])){
                        if (board[i][j].equals(board[i+3][j+3])){
                            if (board[i][j].equals("X|") || board[i][j].equals("O|")){
                                return true;
                            }
                        }
                    }
                }                         
            }
        }               
        for (int i = 0; i < 3; i++){
            for (int j = 3; j < 8; j++){
                if (board[i][j].equals(board[i+1][j-1])){
                    if (board[i][j].equals(board[i+2][j-2])){
                        if (board[i][j].equals(board[i+3][j-3])){
                            if (board[i][j].equals("X|") || board[i][j].equals("O|")){
                                return true;
                            }
                        }
                    }
                }                         
            }
        }
        return false;
    }
    
    /**
     * Returns a String representation of the game board
     * @return A String that represents a current state of the game board 
     */
    public String toString(){
        String toPrint = "";
        for (int i = 5; i >=0; i--){
            for (int j= 0; j < 8; j++){
                toPrint+= board[i][j];
            }
            toPrint+="\n";
        }
        return toPrint;
    }
     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connect4 board = new Connect4();    
    }   
}
