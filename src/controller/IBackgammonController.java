package controller;

import java.io.IOException;

public interface IBackgammonController {
    void nextFrame() throws IOException;

    void userInput(int mouseX, int mouseY);

    void rollPressed();

    void dice1Pressed();

    void dice2Pressed();

    void giveUppressed();
       /* void rollDice();
        void selectDice(int diceNumber);
        void selectCell(int x, int y);
        void moveSelectedStones();*/
    }

