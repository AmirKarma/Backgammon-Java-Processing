/**
 * The controller package contains the BackgammonController class, which serves as the bridge between the model and the view.
 * It takes user inputs, processes them and updates the view accordingly.
 * The package also contains the IBackgammonController interface, which defines the methods that the controller class should have.
 * The Backgammon class in the model package is used to perform all the logic of the game.
 * The IView and View classes in the view package are used to display the game's graphics and handle the user inputs
 */
package controller;

import model.Backgammon;
import view.IView;
import view.View;

import java.awt.*;

/**
 * BackgammonController class is responsible for handling the game logic and communication between the game model and view.
 * <p>
 * It implements the IBackgammonController interface.
 *
 * @author Amir Ebrahimi
 * @version 1.00
 */
public class BackgammonController implements IBackgammonController {
    private Backgammon game;
    private IView view;
    private GameState state;
    private int dice[];
    private int tempDice;
    private int currentPlayer;

    /**
     * Constructor for BackgammonController class.
     * Initializes the view and game model.
     *
     * @param view The view for the game.
     */
    public BackgammonController(View view) {
        this.view = view;
        this.game = new Backgammon();
        this.state = GameState.GAME;
        this.currentPlayer = game.getCurrentPlayers();
    }

    /**
     * nextFrame function updates the view according to the current game state.
     */
    public void nextFrame() {
        switch (state) {
            case GAME -> view.drawGame(game.getStonesCollected(), game.getBoard(), game.getValidStones(), 50,
                    String.valueOf(game.getCurrentPlayers() + 1), view.getDice1().isVisible() || view.getDice2().isVisible(), game.getAttacked_Stones());
            case GAME_OVER -> gameOver(currentPlayer);
        }
    }

    /**
     * userInput function handles the user input when the user selects a game piece to move.
     *
     * @param x The x-coordinate of Mouse.
     * @param y The y-coordinate of Mouse.
     */
    public void userInput(int x, int y) {
        switch (state) {
            case GAME -> {
                stonesMove(x, y, 50);
            }
        }
    }

    /**
     * rollPressed function is called when the roll button is pressed by the user.
     * It rolls the dice, updates the view with the dice values, and enables the dice buttons.
     */
    public void rollPressed() {
        try {
            //if(rollPressedCount ==1){
            //     System.out.println("You have already rolled the dice, please select your dice");
            if (!view.getDice1().isVisible() && !view.getDice2().isVisible()) {
                dice = game.rollDice();
                if (game.isUnvalidDice()) {
                    view.updateDice(dice[0], dice[1], false);
                } else view.updateDice(dice[0], dice[1], true);
            }
        } catch (Exception e) {
        }
    }

    /**
     * dice1Pressed function is called when the dice1 button is pressed by the user.
     * It shows the valid fields, change the color of dice 1 and locks the dice2.
     */
    public void dice1Pressed() {
        try {
            view.getDice1().setColorBackground(new Color(255, 0, 0).getRGB());
            if (game.isattacked()) {
                game.showWhere(dice[0]);
            } else if (game.areStonesInArea(game.getBoard())) {
                game.validStones(dice[0]);
            } else {
                game.validStones(dice[0]);
            }
            tempDice = dice[0];
            view.getDice2().setLock(true);
        } catch (Exception e) {
        }
    }

    /**
     * dice2Pressed function is called when the dice2 button is pressed by the user.
     * It shows the valid fields, change the color of dice 2 and locks the dice1.
     */
    public void dice2Pressed() {
        try {
            view.getDice2().setColorBackground(new Color(255, 0, 0).getRGB());
            if (game.isattacked()) {
                game.showWhere(dice[1]);
            } else if (game.areStonesInArea(game.getBoard())) {
                game.validStones(dice[1]);
            } else {
                game.validStones(dice[1]);
            }
            tempDice = dice[1];
            view.getDice1().setLock(true);
        } catch (Exception e) {
        }
    }

    /**
     * giveUppressed function is called when the giveUp button is pressed by the user.
     * It checks the current player if it is 0 and will change it to 1 and if it is not 0 it will be changed to 1,
     * change the GameState to Game_Over
     */
    public void giveUppressed() {
        try {
            if (game.getCurrentPlayers() == 0) {
                currentPlayer = 1;
            } else currentPlayer = 0;
            state = GameState.GAME_OVER;

        } catch (Exception e) {
        }
    }

    /**
     * gameOver is a private method that is called when the game state is "game over", it hides all buttons and
     * call drawGG() from the view instance.
     *
     * @param currentPlayer an int number containing the player number.
     */
    private void gameOver(int currentPlayer) {
        view.getRollButton().setVisible(false);
        view.getGiveUP().setVisible(false);
        view.getDice1().setVisible(false);
        view.getDice2().setVisible(false);
        view.drawGG(String.valueOf(currentPlayer + 1));
    }

    /**
     * The method is used to move the stones on the board.
     *
     * @param x:         x coordinate of the point where user clicked.
     * @param y:         y coordinate of the point where user clicked.
     * @param stoneSize: size of the stone on the board.
     */
    private void stonesMove(int x, int y, int stoneSize) {
        new Thread(() -> {
            if (x > view.getBorder()) {
                return;
            }
            int row = (y / (stoneSize * 5)) == 0 ? 1 : 0;
            int col = x / stoneSize;
            if (row == 0) {
                col = (game.getBoard()[row].length - 1) - col;
            }
            if (!game.getValidStones()[row][col]) {
                return;
            }
            game.moveStones(row, col, tempDice);
            diceButtonsChange();
            if (game.gameOver(game.getStonesCollected())) state = GameState.GAME_OVER;
        }).start();
    }

    /**
     * This method is responsible for changing the appearance of the dice buttons after a move has been made.
     * It checks if the color of the dice button is set to red, indicating that it was selected for the move.
     * If it is red, the color is changed back to green, and the visibility of the button is changed based on whether
     * the roll was a double roll or not. If it was a double roll, the button remains visible, otherwise, it becomes hidden
     * and the other dice button's lock state is set to false.
     */
    private void diceButtonsChange() {
        if (view.getDice1().getColor().getBackground() == new Color(255, 0, 0).getRGB()) {
            view.getDice1().setColorBackground(new Color(173, 255, 47).getRGB());
            if (game.isDoubleRoll()) {
                view.getDice1().setVisible(true);
            } else {
                view.getDice1().setVisible(false);
                view.getDice2().setLock(false);
            }
        }
        if (view.getDice2().getColor().getBackground() == new Color(255, 0, 0).getRGB()) {
            view.getDice2().setColorBackground(new Color(173, 255, 47).getRGB());
            if (game.isDoubleRoll()) {
                view.getDice2().setVisible(true);
            } else {
                view.getDice2().setVisible(false);
                view.getDice1().setLock(false);
            }
        }
    }

}
