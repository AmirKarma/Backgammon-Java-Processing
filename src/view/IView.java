package view;

import controlP5.Button;


public interface IView {
    void drawGame(int[] stonesCollected, int[][] board, boolean[][] validStones, int i, String valueOf, boolean b,int[] stoneLose );

    void updateDice(int die, int die1,boolean isValidDice);

    Button getRollButton();

    Button getDice1();

    Button getDice2();

    void drawGG(String valueOf);

    Button getGiveUP();

    int getBorder();
}
