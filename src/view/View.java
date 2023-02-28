package view;


import controlP5.*;
import controller.BackgammonController;
import controller.IBackgammonController;
import processing.core.PApplet;
import processing.core.PFont;


import java.io.IOException;

import static controlP5.ControlP5Constants.ACTION_RELEASE;

/**
 * The View class is responsible for creating the user interface and displaying the game state to the user.
 * It implements the IView interface, which defines the methods that the view classes must implement.
 * The class uses the ControlP5 library for creating the UI.
 * The class creates buttons for Roll, Dice1, Dice2, GiveUP and also it's handling the event of these buttons.
 * It also contains main method to run the game.
 */
public class View extends PApplet implements IView {

    private ControlP5 cp5;

    private IBackgammonController controller;
    private PFont font;
    private Button rollButton, dice1, dice2, giveUP;

    private int cellSize = 50;

    private int stoneSize = 50;
    private int border = 600;

    /**
     * The method getRollButton returns the roll button.
     *
     * @return roll button.
     */
    public Button getRollButton() {
        return rollButton;
    }

    /**
     * The method getDice1 returns the dice1 button.
     *
     * @return dice1 button.
     */
    public Button getDice1() {
        return dice1;
    }

    /**
     * The method getDice2 returns the dice2 button.
     *
     * @return dice2 button.
     */

    public Button getDice2() {
        return dice2;
    }

    /**
     * The method getGiveUP returns the giveUP button.
     *
     * @return giveUP button.
     */

    public Button getGiveUP() {
        return giveUP;
    }

    /**
     * Returns the value of the border.
     *
     * @return an int representing the value of the border.
     */
    public int getBorder() {
        return border;
    }

    /**
     * The main method which is used to run the game.
     * It calls the main method of the PApplet class and passes the View class as an argument.
     * This will launch the game window where you can play the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(View.class);
    }

    /**
     * Constructor for View class, sets the size of the game window to 1000x600 pixels.
     */
    public View() {
        setSize(1000, 600);

    }

    @Override
    public void setup() {
        this.controller = new BackgammonController(this);

        cp5 = new ControlP5(this);
        font = createFont("Arial", 32);
        cp5.setFont(font);

        // Roll Button
        rollButton = cp5.addButton("Roll");
        rollButton.setPosition(810, 220);
        rollButton.setSize(150, 50);
        rollButton.setVisible(true);
        rollButton.setLabel("Roll");

        //Give Up Button
        giveUP = cp5.addButton("GiveUP");
        giveUP.setPosition(810, 540);
        giveUP.setColorBackground(color(255, 0, 0));
        giveUP.setSize(150, 50);
        giveUP.setVisible(true);
        giveUP.setLabel("Give UP");

        //dice 1
        dice1 = cp5.addButton("dice1");
        dice1.setPosition(820, 280);
        dice1.setColorBackground(color(173, 255, 47));
        dice1.setSize(50, 50);
        dice1.setVisible(false);


        //dice 2
        dice2 = cp5.addButton("dice2");
        dice2.setPosition(885, 280);
        dice2.setColorBackground(color(173, 255, 47));
        dice2.setSize(50, 50);
        dice2.setVisible(false);


        dice1.addListenerFor(ACTION_RELEASE, new CallbackListener() {
            @Override
            public void controlEvent(CallbackEvent callbackEvent) {
                try {
                    controller.dice1Pressed();
                } catch (Exception e) {
                }
            }
        });
        dice2.addListenerFor(ACTION_RELEASE, new CallbackListener() {
            @Override
            public void controlEvent(CallbackEvent callbackEvent) {
                try {
                    controller.dice2Pressed();
                } catch (Exception e) {
                }
            }
        });
        rollButton.addListenerFor(ACTION_RELEASE, new CallbackListener() {
            @Override
            public void controlEvent(CallbackEvent callbackEvent) {
                try {
                    controller.rollPressed();

                } catch (Exception e) {
                }
            }

        });

        giveUP.addListenerFor(ACTION_RELEASE, new CallbackListener() {
            @Override
            public void controlEvent(CallbackEvent callbackEvent) {
                try {
                    controller.giveUppressed();

                } catch (Exception e) {
                }
            }

        });
    }

    /**
     * This method is called by the Processing library on every frame update. It calls the nextFrame() method of the controller, which updates the game state and redraws the game elements on the screen.
     */
    @Override
    public void draw() {
        try {
            controller.nextFrame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method mouseClicked() is an overridden method from the PApplet class. It is called when a mouse button is pressed and then released within the sketch window.
     * In this specific implementation, it calls the userInput() method of the controller, passing it the x and y coordinates of the mouse click event.
     * This method is used to handle user input and interact with the game state.
     */
    public void mouseClicked() {
        controller.userInput(mouseX, mouseY);
    }

    /**
     * updateDice is a method that updates the values of the two dice buttons in the game's GUI.
     * It takes in two integers as parameters, which are the values of the two dice.
     * The method sets the label of the dice1 button to the value of dicen1 and makes the button visible.
     * Similarly, the method sets the label of the dice2 button to the value of dicen2 and makes the button visible.
     * This method allows the game to display the values of the rolled dice on the GUI.
     *
     * @param dicen1 the value of the first dice
     * @param dicen2 the value of the second dice
     */
    public void updateDice(int dicen1, int dicen2, boolean isValidDice) {
        if (isValidDice) {
            dice1.setLabel(String.valueOf(dicen1));
            dice1.setVisible(true);

            dice2.setLabel(String.valueOf(dicen2));
            dice2.setVisible(true);

        } else {
            dice1.setVisible(false);
            dice2.setVisible(false);

        }
    }

    /**
     * This method is responsible for drawing the game state on the screen. It takes in several parameters that represent the current state of the game, including the number of stones collected by each player, the current board state, the valid fields on the board, the size of the stones, the current player, the visibility of the dices, and the number of stones lost by each player.
     * It calls several other methods to draw different elements of the game state on the screen, such as the game information, the triangles on the board, and the stones on the board.
     *
     * @param stonesCollected An array containing the number of stones collected by each player. The first element represents player 1, and the second element represents player 2.
     * @param board           A 2D array representing the current state of the board. Positive values represent player 1's stones, and negative values represent player 2's stones.
     * @param validFields     A 2D boolean array representing the valid fields on the board.
     * @param stoneSize       The size of the stones on the board.
     * @param currentPlayer   A string representing the current player.
     * @param dicesVisible    A boolean indicating whether the dices are visible on the screen.
     * @param stone_lose      An array containing the number of stones lost by each player. The first element represents player 1, and the second element represents player 2.
     */
    public void drawGame(int[] stonesCollected, int[][] board, boolean[][] validFields, int stoneSize,
                         String currentPlayer, boolean dicesVisible, int[] stone_lose) {
        background(255, 255, 153);
        drawGameInfo(stonesCollected, currentPlayer, dicesVisible, stone_lose);
        drawTriangles(board, validFields);
        drawStones(board, stoneSize);

    }

    /**
     * This method is for drawing the information about the game on the game board.
     * It displays the number of stones collected by each player, the current player's turn, and the number of stones lost by each player.
     * It also displays a message asking the player to choose one of the dices if the dice are visible.
     *
     * @param stonesCollected An array that holds the number of stones collected by each player.
     * @param currentPlayer   A string that represents the current player's turn.
     * @param dicesVisible    A boolean that indicates if the dices are visible or not.
     * @param stoneLose       An array that holds the number of stones lost by each player.
     */
    private void drawGameInfo(int[] stonesCollected, String currentPlayer, boolean dicesVisible, int[] stoneLose) {
        strokeWeight(2);
        stroke(0);
        fill(0);
        rect(600, 0, 20, 600);
        rect(700, 0, 20, 600);
        stroke(153, 204, 255);
        strokeWeight(5);
        ellipse(660, 40, (float) (stoneSize * 1.5), (float) (stoneSize * 1.5));
        fill(255
        );
        ellipse(660, 560, (float) (stoneSize * 1.5), (float) (stoneSize * 1.5));
        fill(255);
        textSize(20);
        textAlign(CENTER);
        text(String.valueOf(stonesCollected[1]), 660, 45);
        fill(0);
        textSize(30);
        text(String.valueOf(stonesCollected[0]), 660, 565);
        textSize(20);
        text("Player: It's player " + currentPlayer + " turn!", 880, 50);
        textSize(20);
        text("Player 1 Stones lose: " + stoneLose[0], 870, 100);
        text("Player 2 Stones lose: " + stoneLose[1], 870, 125);
        if (dicesVisible) {
            text("Please choose \n one of the dice", 880, 360);
        }
        strokeWeight(2);
        stroke(0);
    }

    /**
     * The method drawTriangles is used for drawing the triangles on the game board.
     * The triangles represent the spaces on the board where the player can move their stones.
     *
     * @param board      The current state of the game board.
     * @param validField A 2D boolean array that represents the valid spaces on the board where the player can move their stones.
     */
    private void drawTriangles(int[][] board, boolean[][] validField) {
        int index, triangleX1, triangleX2, triangleX3, hight;
        for (int i = 0; i < board.length; i++) {
            for (int j = 1; j <= board[i].length; j++) {
                triangleX1 = (stoneSize * (j - 1));
                triangleX2 = triangleX1 + (stoneSize / 2);
                triangleX3 = triangleX2 + (stoneSize / 2);
                index = j - 1;
                hight = 0 + (stoneSize * 5);
                if (i == 1) {
                    index = board[i].length - j;
                    hight = height - (stoneSize * 5);
                }
                if (j == 7) {

                    fill(0);
                    rect(triangleX1, 0, 1, height);
                }
                if (validField[1 - i][index]) {
                    fill(255, 223, 0);
                    triangle(triangleX1, height * i, triangleX2, hight, triangleX3, height * i);
                } else {
                    if (j % 2 == 0) {
                        fill(255, 179, 102);
                    } else fill(102, 51, 0);
                    triangle(triangleX1, height * i, triangleX2, hight, triangleX3, height * i);
                }
            }

        }

    }

    /**
     * This method is used to draw the stones on the game board. It takes in two parameters, the state of the board represented as a 2D array and the size of each stone.
     * The method loops through the rows and columns of the board array, and for each cell that contains a stone, it draws an ellipse of the specified size on the corresponding location on the screen.
     * The fill color of the ellipse is determined by the value of the cell in the board array. Positive values represent one player's stones and will be drawn in black, while negative values represent the other player's stones and will be drawn in white.
     */
    private void drawStones(int[][] board, int stoneSize) {
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] > 0) {
                fill(0, 0, 0);
            } else if (board[0][i] < 0) {
                fill(255, 255, 255);
            }
            for (int j = 0; j < Math.abs(board[0][i]); j++) {
                ellipse((border - stoneSize / 2) - i * cellSize, (height - stoneSize / 2) - j * stoneSize, stoneSize, stoneSize);

            }
            if (board[1][i] < 0) {
                fill(255, 255, 255);
            } else if (board[1][i] > 0) {
                fill(0, 0, 0);
            }
            for (int j = 0; j < Math.abs(board[1][i]); j++) {
                ellipse((0 + stoneSize / 2) + i * cellSize, (0 + stoneSize / 2) + j * stoneSize, stoneSize, stoneSize);
            }
        }
    }

    /**
     * This method is used to draw the Game over message on the screen when the game is over.
     * It takes in the player name as a parameter and displays it on the screen along with the message "Game Over!"
     * The method sets the background color to black and text color to white.
     * It also sets the text size to 100 and aligns it to the center of the screen.
     *
     * @param playerName The name of the player who won the game.
     */
    public void drawGG(String playerName) {
        background(0);
        fill(255);
        textSize(100);
        textAlign(CENTER);
        text("\n" + "Game Over!\n" + "Player " + playerName + " won this game!", width / 2, height / 4);
    }
}

