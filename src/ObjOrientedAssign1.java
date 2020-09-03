/*
ObjOrientedAssign1.java
Sat Arora
Class Gameword is a extension tool to the Game Scrabble that comes with 5 methods:
Reverse --> Returns a reversed string of the letters you have
Anagram --> Checks if the word passed in can be rewritten as the word that is given to the object
Point value --> Returns the point value of a word on a Scrabble board, given its direction and position of first character
Permutations --> Returns an arrayList with all possible arrangements of the word
toString --> Returns the contents*/
import java.util.*;
public class ObjOrientedAssign1 {
    public static void main(String[] args){
        GameWord keys=new GameWord("KEYS"); //making new words
        GameWord syke=new GameWord("SYKE");
        System.out.println(keys.reverse()); //using all features
        System.out.println(keys.anagram("SYKE"));
        System.out.println(keys.anagram(syke));
        System.out.println(keys.anagram("RING"));
        System.out.println(keys.permutations());
        System.out.println(keys.pointValue(0,6,GameWord.DOWN));
        System.out.println(keys.toString());
    }
}
class GameWord {
    private String contents; //string field with the characters the object will have
    public static final int DOWN = 2; //holds the constant value for down to check if the direction passed in is down
    public static final int RIGHT = 1; //holds the constant value for right to check if the direction passed in is right

    //constructor
    public GameWord(String contents) {
        this.contents = contents.toUpperCase();
    }
    //method reverse that will return the contents string reversed
    public String reverse() {
        String returned = ""; //empty string that will hold the reverse string of contents
        int size = contents.length(); //getting length of contents in order to loop through each character
        for (int i = size - 1; i >= 0; i--) { //looping through each index in descending order, then adding the character onto the string
            returned += contents.charAt(i);
        }
        return returned; //returns the reversed string
    }
    //returns a point value given the starting position on the board and the direction
    public int pointValue (int x, int y, int direction) {
        /*
        variable name corresponds to the number of points per letter
        are variables that are worth the same number of points are held in a string for using a .contains method
        to check how many points each letter is worth
        */
        final int[] pts = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; //array that has the point values for each character in the alphabet
        final int word_mult[][] = {
                {3,1,1,1,1,1,1,3,1,1,1,1,1,1,3},
                {1,2,1,1,1,1,1,1,1,1,1,1,1,2,1},
                {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
                {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                {1,1,1,1,2,1,1,1,1,1,2,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {3,1,1,1,1,1,1,2,1,1,1,1,1,1,3},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,2,1,1,1,1,1,2,1,1,1,1},
                {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                {1,1,2,1,1,1,1,1,1,1,1,1,2,1,1},
                {1,2,1,1,1,1,1,1,1,1,1,1,1,2,1},
                {3,1,1,1,1,1,1,3,1,1,1,1,1,1,3}
        }; //2d array with spots of word multiplication
        final int char_mult[][] = {
                {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                {1,1,1,1,1,3,1,1,1,3,1,1,1,1,1},
                {1,1,1,1,1,1,2,1,2,1,1,1,1,1,1},
                {2,1,1,1,1,1,1,2,1,1,1,1,1,1,2},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
                {1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
                {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
                {1,1,2,1,1,1,2,1,2,1,1,1,2,1,1},
                {1,3,1,1,1,3,1,1,1,3,1,1,1,3,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {2,1,1,1,1,1,1,2,1,1,1,1,1,1,2},
                {1,1,1,1,1,1,2,1,2,1,1,1,1,1,1},
                {1,1,1,1,1,3,1,1,1,3,1,1,1,1,1},
                {1,1,1,2,1,1,1,1,1,1,1,2,1,1,1},
        }; //2d array with spots of character multiplication

        int total = 0; //will hold the total points from the characters and the direction
        int multiply = 1; //holds the multiplier for the word from all of the intersections between the character and word multiplier spots

        for (char c : contents.toCharArray()) { //looping through each character
            total += char_mult[y][x] * pts[(int)(c-'A')];
            multiply *= word_mult[y][x]; //word multiplier being
            if (direction == DOWN) y++; //increments the y variable so the word moves down
            else if (direction == RIGHT) x++; //increments x variable so the word moves right
        }
        return total * multiply; //returns total score from the characters multiplied by the word score
    }

    //method that returns all permutations of the current contents in an arraylist
    public ArrayList<String> permutations() {
        ArrayList<String> perms = new ArrayList<String>(); //NEW ARRAYLIST that will hold all complete permutations of the word
        return permutations("", contents, perms); //calls the overloaded method below
    }
    //overloaded method taking in the characters used so far, what characters are remaining, and the full arraylist holding all words that work
    public ArrayList<String> permutations(String sofar, String left, ArrayList<String> perms) {
        if (left.equals("")) perms.add(sofar); //this means that there are no characters remaining, so the string sofar has all of the characters used in contents, and it will be added the arrayList
        else {
            for (int i = 0; i < left.length(); i++) { //looping through each index of the left string
                String next = left.substring(0,i) + left.substring(i+1); //making a new string that has all the contents except the character at index i
                ArrayList<String> new_perms = new ArrayList<String>(); //making new arrayList for branching downwards
                //going through the branch for getting closer to having left being empty
                for (String s : permutations(sofar + left.charAt(i),next,new_perms)) {
                    perms.add(s); //completing the perms array from all possibilities in the tree below the current permutation
                }
            }
        }
        return perms; //returning the arrayList so it could either be the final returned arrayList or the arrayList that will be brought up the recursive tree
    }
    //method checking if the current contents can be written as otherWord, a string that is passed through
    public boolean anagram(String otherWord) {
        //converting to character arrays for sorting, and then actually sorting them
        char[] contLets = contents.toCharArray();
        char[] otherLets = otherWord.toCharArray();
        Arrays.sort(contLets);
        Arrays.sort(otherLets);
        return Arrays.equals(contLets,otherLets); //returns true if the arrays are the same, meaning contents and otherWord consisted of the same characters
    }
    //another anagram that does the same as the previous one, but takes in another GameWord object
    public boolean anagram(GameWord other) {
        return anagram(other.toString());
    }
    //toString method returns the contents
    @Override
    public String toString() {
        return contents;
    }
}
