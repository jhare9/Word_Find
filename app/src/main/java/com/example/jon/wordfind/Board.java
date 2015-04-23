package com.example.jon.wordfind;

import java.util.Random;

/**
 * Created by jon on 3/17/2015.
 * class to fill the word find board with words at random positions
 *
 *
 */
public class Board {
    private boolean[][] check;
    private char[][] board;

    // public constructor to create the game board
    public Board(int row,int col){
        this.check = new boolean[row][col];
        this.board = new char[row][col];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                this.board[i][j] = ' ';
                this.check[i][j] = false;
            }
        }
    }

    // fill the board with a word horizontally
    public boolean horizontal(int row, int col,String word){

        int diff = board.length - col;

        int c1 = col;
        int c2 = col;

        if (word.length() > diff)
            return false;

        for (int i = 0; i < word.length(); i++) {
            if (check[row][c1] && board[row][c1] != word.charAt(i))
                return false;

            c1++;
        }

        for (int i = 0; i < word.length(); i++) {

            board[row][c2] = word.charAt(i);
            check[row][c2] = true;
            c2++;
        }

        return true;
    }
    // fill the board with a word vertically
    public boolean vertical(int row, int col,String word) {
        int dif = board.length - row;

        int r_1 = row;
        int r_2 = row;

        if (word.length() > dif)
            return false;

        for (int i = 0; i < word.length(); i++) {
            if (check[r_1][col] && board[r_1][col] != word.charAt(i))
                return false;

            r_1++;

        }

        for (int i = 0; i < word.length(); i++) {
            board[row][col] = word.charAt(i);
            check[row][col] = true;
            row++;
        }

        return true;
    }

    // fill the board with a down word dia word
    public boolean rightDowndiagnol(int row, int col,String word) {
        int r1 = row;
        int r2 = row;
        int c1 = col;
        int c2 = col;

        int widthDiff = board.length - row;
        int heightDiff = board.length - col;
        if (word.length() > widthDiff || word.length() > heightDiff)
            return false;

        for (int i = 0; i < word.length(); i++) {
            if (check[r1][c1] && board[r1][c1] != word.charAt(i))
                return false;

            r1++;
            c1++;
        }

        for(int i = 0; i < word.length(); i++){
            board[r2][c2] = word.charAt(i);
            check[r2][c2] = true;
            r2++;
            c2++;
        }

        return true;
    }

    //fill the board with a word right up dia.
    public boolean rightupDiagnol(int row, int col, String word){
        int r1 = row;
        int r2 = row;
        int c1 = col;
        int c2 = col;

        int Diff = (board.length - col) - (board.length -row);

        if (word.length() > Diff)
            return false;

        for (int i = 0; i < word.length(); i++) {
            if (check[r1][c1] && board[r1][c1] != word.charAt(i))
                return false;

            r1--;
            c1++;
        }

        for(int i = 0; i < word.length(); i++){
            board[r2][c2] = word.charAt(i);
            check[r2][c2] = true;
            r2--;
            c2++;
        }
        return true;
    }
    // reverse the word
    public String wordReverse(String word){
         return word = new StringBuffer(word).reverse().toString();
    }

    // fill in the rest of the board with random letters 
    public void fill_board(){
        char[] abc = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        Random ran = new Random();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(!check[i][j])
                    board[i][j] = abc[ran.nextInt(26)];
            }
        }
    }

    public String returnLetter(int row,int col){
         return Character.toString(board[row][col]);
    }

    public String[][] getBoard(int row, int col){

        String[][] newBoard = new String[col][col];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                newBoard[i][j] = Character.toString(board[i][j]);
            }
        }
        return newBoard;
    }

}
