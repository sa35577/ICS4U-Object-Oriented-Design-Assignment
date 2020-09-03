/*
ObjOrientedAssign2.java
Sat Arora
Class CheckersBoard allows for 3 methods:
Move --> Takes in a current (x1,y1), and checks if the piece can move to (x2,y2) in one move
Display --> Prints an ASCII version of the board
Count --> Returns the number of pieces of the specified colour on the board
 */
import java.util.*;
public class ObjOrientedAssign2 {
    public static void main(String[] args){
        CheckersBoard board1=new CheckersBoard(); //making new default board
        board1.display(); //displaying board
        System.out.println(board1.count(CheckersBoard.BLACK)); //number of black pieces
        System.out.println(board1.move(7,5,6,4)); //set of moves
        board1.display();
        System.out.println(board1.move(6,2,7,5));
        board1.display();
        System.out.println(board1.move(6,4,5,3));
        board1.display();
        System.out.println(board1.move(6,6,7,5));
        board1.display();
        System.out.println(board1.move(6,2,6,6));
        board1.display();
        System.out.println(board1.count(CheckersBoard.BLACK));
    }
}
class CheckersBoard {
    private int board[][] = {
            {2,0,2,0,2,0,2,0},
            {0,2,0,2,0,2,0,2},
            {2,0,2,0,2,0,2,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
    }; //checker board
    public static final int BLACK = 1; //constant BLACK to avoid magic number 1
    public static final int RED = 2; //constant RED to avoid magic number 2
    //function move takes starting point (x1,y1) and end point (x2,y2) and checks if it can get there in one move
    public boolean move (int x1, int y1, int x2, int y2) {
        int x_diff = Math.abs(x2 - x1), y_diff = Math.abs(y2 - y1); //setting x diff and y diff to be the absolute values of the differences in destination versus current spot for future comparison
        int piece = board[y1][x1];
        if (board[y2][x2] > 0) return false; //checking if there is a piece at (x2,y2), and you cant move to (x2,y2) if there is a piece there
        if (board[y1][x1] == 0) return false; //checking if there is actually a piece at (x1,y1), and will return false if there is no piece to start off with at (x1,y1)
		/*
		The logic with the next 2 lines is that the x and y coordinates can only be changed by one (no capture), or else the change must be even
		(positive or negative). Therefore, if the change in x or y is odd, but not 1, then the move is invalid.
		*/
        if (x_diff > 1 && Math.abs(y2 - y1) % 2 == 1) return false;
        if (y_diff > 1 && Math.abs(x2 - x1) % 2 == 1) return false;
        if (y2 - y1 == 0) return false; //if the y-value has not changed there is no way a piece can move to (x2,y2), since we restrict kings in this program
        if (x_diff == 1 && y_diff == 1) { //checks if the spot is 1 up/down and 1 left/right
            if ((piece == RED && y2 > y1) || (piece == BLACK && y2 < y1)) { //checks if the piece would move in the correct direction
                board[y2][x2] = piece; //setting the destination place to have the piece
                board[y1][x1] = 0; //setting the original place to have no piece
                return true; //this is a valid move, so it returns true
            }
            else return false; //if none of the two are true, the piece has to move in the opposite direction, which is not possible
        }
        int[][] check_board = new int[board.length][board.length]; //a check board which will have the original contents of board
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++) {
                check_board[i][j] = board[i][j]; //setting every value in check_board to be what it is in board
            }
        }
        ArrayList<Integer> startx = new ArrayList<Integer>(), starty = new ArrayList<Integer>(); //2 new arraylists that are used to track which coordinates are skipped over (need to have them empty if the move passes)
        //they also include the starting point as that needs to be emptied as well
        startx.add(new Integer(x1)); //adding the x coordinate of the original point on
        starty.add(new Integer(y1)); //adding the y coordinate of the original point on
        move(x1,y1,x2,y2,startx,starty,piece); //calling the overloaded move
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != check_board[i][j]) { //looping through each element checking if the board has changed
                    board[y2][x2] = piece; //setting the board at the destination coordinates to be the piece
                    return true; //the board has changed, meaning the move was valid (therefore returns true)
                }
            }
        }
        return false; //this executes if the whole board was the same before and after, making it an invalid move
    }
    //overloaded move that takes in starting point (x1,y1), end point (x2,y2), 2 arraylists for (x,y) coordinates that will have their board position set to zero if thte move is valid, and the current piece
    public void move (int x1, int y1, int x2, int y2, ArrayList<Integer> empty_x, ArrayList<Integer> empty_y, int piece) {
        if (x1 == x2 && y1 == y2) { //checks if we reached the destination
            for (int i = 0; i < empty_x.size(); i++) { //loops through the length of the arraylist of values we can empty
                board[empty_y.get(i)][empty_x.get(i)] = 0; //setting the value at each point from the 2 given arraylists to 0
            }
        }
        ArrayList<Integer> new_empty_x = empty_x, new_empty_y = empty_y; //making 2 new arraylists that will be used for the recursion
        if (piece == BLACK) {
            if (y1 - 2 >= 0 && x1 - 2 >= 0 && y1 > y2) { //checking if moving 2 up and 2 left will still be on the board, and if the destination is still above the current point
                if (board[y1 - 2][x1 - 2] == 0 && board[y1 - 1][x1 - 1] == RED) { //checking if we have an enemy piece to jump over and if the square we land on is empty
                    new_empty_x.add(new Integer(x1 - 1)); //adding the value of x coordinate one left to the arraylist for deletions
                    new_empty_y.add(new Integer(y1 - 1)); //adding the value of y coordinate one up to the arraylist for deletions
                    move(x1 - 2, y1 - 2, x2, y2, new_empty_x, new_empty_y, piece); //recursion via calling move from the new spot
                }
            }
            if (y1 - 2 >= 0 && x1 + 2 < board.length && y1 > y2) { //checking if moving 2 up and 2 right will still be on the board, and if the destination is still above the current point
                if (board[y1-2][x1+2] == 0 && board[y1-1][x1+1] == RED ) { //checking if we have an enemy piece to jump over and if the square we land on is empty
                    new_empty_x.add(new Integer(x1 + 1)); //adding the value of x coordinate one right to the arraylist for deletions
                    new_empty_y.add(new Integer(y1 - 1)); //adding the value of y coordinate one up to the arraylist for deletions
                    move(x1 + 2, y1 - 2, x2, y2, new_empty_x, new_empty_y, piece); //recursion via calling move from the new spot
                }
            }
        }
        if (piece == RED) {
            if (y1 + 2 < board.length && x1 - 2 >= 0 && y1 < y2) { //checking if moving 2 down and 2 left will still be on the board, and if the destination is still below the current point
                if (board[y1+2][x1-2] == 0 && board[y1+1][x1-1] == BLACK){ //checking if we have an enemy piece to jump over and if the square we land on is empty
                    new_empty_x.add(new Integer(x1 - 1)); //adding the value of x coordinate one left to the arraylist for deletions
                    new_empty_y.add(new Integer(y1 + 1)); //adding the value of y coordinate one down to the arraylist for deletions
                    move(x1 - 2, y1 + 2, x2, y2, new_empty_x, new_empty_y, piece); //recursion via calling move from the new spot
                }
            }
            if (y1 + 2 < board.length && x1 + 2 < board.length && y1 < y2) { //checking if moving 2 down and 2 right will still be on the board, and if the destination is still below the current point
                if (board[y1+2][x1+2] == 0 && board[y1+1][x1+1] == BLACK) { //checking if we have an enemy piece to jump over and if the square we land on is empty
                    new_empty_x.add(new Integer(x1 + 1)); //adding the value of x coordinate one right to the arraylist for deletions
                    new_empty_y.add(new Integer(y1 + 1)); //adding the value of y coordinate one down to the arraylist for deletions
                    move(x1 + 2, y1 + 2, x2, y2, new_empty_x, new_empty_y, piece); //recursion via calling move from the new spot
                }
            }
        }
    }
    //display function used to print ASCII version of the board
    public void display() {
        String div = "+---+---+---+---+---+---+---+---+"; //div string is used as a divider
        System.out.println(div); //prints the top of the board, the divider
        for (int i = 0; i < board.length; i++) { //for every row
            System.out.print("|"); //printing a | for the LHS of the board
            for (int j = 0; j < board.length; j++) { //looping through each spot
                if (board[i][j] == 0) System.out.print("   |"); //printing 3 spaces and a | for no piece
                else if (board[i][j] == BLACK) System.out.print(" X |"); //printing an X surrounded by spaces on BS, followed by a | for the black piece
                else System.out.print(" O |"); //this is for a red space, similar to black except with an O
            }
            System.out.println(); //going down a line
            System.out.println(div); //printing the row divider
        }
    }
    //count function returning the number of occurences of the specified color
    public int count(int colour) {
        int tot = 0; //total variable
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == colour) tot++; //looping through each index and seeing if the colour is the same as the one we need to count, and increments if it is
            }
        return tot; //returning the number of instances of the color
    }
}