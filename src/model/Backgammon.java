/**
 * The Backgammon class is a model for the game of Backgammon. It contains the logic for the game and holds the state of the game.
 */
package model;

/**
 * <p>The class contains the following private fields:
 * <ul>
 * <li>PlayersStones - an array of integers representing the number of stones for each player</li>
 * <li>doubleRoll - a boolean value representing whether the current roll is a double roll or not</li>
 * <li>board - a 2D integer array representing the state of the board</li>
 * <li>attacked_Stones - an array of integers representing the number of stones that are attacked for each player</li>
 * <li>player - an array of integers representing the player, with the first index being player 1 and the second index being player 2</li>
 * <li>currentPlayers - an integer representing the current player</li>
 * <li>dice - an array of integers representing the values of the two dice</li>
 * <li>validStones - a 2D boolean array representing the valid stones that can be moved for each player</li>
 * <li>stonesCollected - an array of integers representing the number of stones that have been collected for each player</li>
 * <li>tempX, tempY - integers representing the temporary x and y coordinates of a stone</li>
 * <li>moveCount - an integer representing the number of moves that have been made</li>
 * </ul>
 */
public class Backgammon {
    private int[] PlayersStones;
    private boolean doubleRoll = false;
    private int[][] board;
    private int[] attacked_Stones = {0, 0};
    private int[] player = new int[]{0, 1};
    private int currentPlayers;
    private int[] dice = new int[2];
    private boolean[][] validStones = new boolean[2][12];
    private int[] stonesCollected = new int[]{0, 0};
    private int tempX, tempY;
    private int moveCount;
    private boolean unvalidDice = false;

    public boolean isUnvalidDice() {
        return unvalidDice;
    }

    /**
     * Constructor for the Backgammon class.
     * Initializes the game by calling the init() method.
     */
    public Backgammon() {
        init();
    }

    /**
     * Initializes the board and other variables for the game. This method is called when creating a new instance of the Backgammon class.
     * It creates a two-dimensional board array which represents the state of the board, where the first index represents the player and the second index represents the points of the board.
     * Each point can have a negative number of stones for player 1, a positive number of stones for player 2, or zero when the point is empty.
     * It also initializes the players' stones and the current player.
     */
    private void init() {
        board = new int[][]{{2, 0, 0, 0, 0, -5, 0, -3, 0, 0, 0, 5},
                {-5, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, -2}};
        /*board = new int[][]{{-5, -5, -5, 2, 2, 2, 0, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, 0}};*/
       /* board = new int[][]{{-5, -5, 0, 0, 0, -3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, -2, 5, 5, 5}};*/
      /* board = new int[][]{{-5, -5, -5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5}};*/

        PlayersStones = new int[]{15, 15};
        currentPlayers = player[0];
    }

    /**
     * The moveStones method is responsible for moving a stone from one point on the board to another point.
     * It takes in three parameters: x and y coordinates representing the source and destination point respectively, and dices representing the value of the dice roll.
     * This method checks if the move is valid by calling helper methods such as isattacked(), isValidToPut(), isValidToMove(), areStonesInArea(), and isValidToPop().
     * If the move is valid, it updates the board, attacked_stones, and stonesCollected accordingly.
     * The method also updates the current player and the move count.
     *
     * @param x     an integer representing the x coordinate of the source point
     * @param y     an integer representing the y coordinate of the destination point
     * @param dices an integer representing the value of the dice roll
     */
    public void moveStones(int x, int y, int dices) {
        if (isattacked()) {
            if (isValidToPut(x, dices, currentPlayers)) {
                if (currentPlayers == 1) {
                    attacked_Stones[1]--;
                    if (board[x][y] == -1) {
                        board[x][y]++;
                        attacked_Stones[0]++;
                    }
                    board[x][y]++;
                } else if (currentPlayers == 0) {
                    attacked_Stones[0]--;
                    if (board[x][y] == 1) {
                        board[x][y]--;
                        attacked_Stones[1]++;
                    }
                    board[x][y]--;
                }
            }
        } else if (isValidToMove(x, y, dices, currentPlayers)) {
            if (currentPlayers == 1) {
                board[x][y] -= 1;
                if (board[tempX][tempY] == -1) {
                    attacked_Stones[0] += 1;
                    board[tempX][tempY] = 0;
                }
                (board[tempX][tempY])++;
            } else if (currentPlayers == 0) {
                board[x][y] += 1;
                if (board[tempX][tempY] == 1) {
                    attacked_Stones[1] += 1;
                    board[tempX][tempY] = 0;
                }
                (board[tempX][tempY])--;
            }
        } else if (areStonesInArea(board)) {
            if (isValidToPop(x, y, dices)) {
                board[x][y] = currentPlayers == 1 ? --board[x][y] : ++board[x][y];
                stonesCollected[currentPlayers]++;
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                validStones[i][j] = false;
            }
        }
        int index = 2;
        if (doubleRoll) {
            index = 4;
            if (moveCount >= 2) {
                dice[0] = 0;
                doubleRoll = isSame(dice);
            }
        }
        moveCount += 1;

        if (moveCount >= index) {
            if (currentPlayers == 0) currentPlayers = 1;
            else currentPlayers = 0;
            moveCount = 0;
        }
    }

    /**
     * This method is used to check whether the move is valid or not. It takes the current position x and y, the value of the dice and the current player as input and returns a boolean value indicating if the move is valid or not.
     * If the current player is 1, it checks if the stone at the current position is negative, which means it is not the player's stone, if so it returns false.
     * If the current player is 0, it checks if the stone at the current position is positive, which means it is not the player's stone, if so it returns false.
     * It then calculates the new position on the board after the move, and checks if the new position is within the board boundaries and the new position on the board is empty or not based on the current player.
     * If the move is valid, it returns true, otherwise it returns false.
     *
     * @param x             the current x position of the stone
     * @param y             the current y position of the stone
     * @param dice          the value of the dice
     * @param currentPlayer the current player
     * @return boolean indicating whether the move is valid or not.
     */
    private boolean isValidToMove(int x, int y, int dice, int currentPlayer) {
        if (currentPlayer == 1) {
            tempX = 0;
            if (board[x][y] < 0) {
                return false;
            } else if (x == 0) {
                int y_temp = y + dice;
                tempY = y + dice;
                if (y_temp >= board[0].length) {
                    tempX = 1;

                    tempY = y_temp - board[0].length;
                    if (board[1][tempY] >= -1) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (board[0][y_temp] >= -1) {
                    return true;
                }
            } else if (x == 1) {
                tempX = 1;
                int y_temp = y + dice;
                tempY = y + dice;
                if (y_temp >= board[1].length) {
                    return false;
                } else if (board[x][y_temp] >= -1) {
                    return true;
                }
            }
        } else if (currentPlayer == 0) {
            tempX = 1;
            if (board[x][y] > 0) {
                return false;
            } else if (x == 1) {
                int y_temp = y - dice;
                tempY = y - dice;

                if (y_temp < 0) {

                    tempY = y_temp + board[0].length;
                    tempX = 0;
                    if (board[0][tempY] <= 1) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (board[1][y_temp] <= 1) {
                    return true;
                }
            } else if (x == 0) {
                tempX = 0;
                int y_temp = y - dice;
                tempY = y - dice;
                if (y_temp < 0) {
                    return false;
                } else if (board[x][y_temp] <= 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A private method that checks if it is valid to put a stone on a certain field based on the current player and the value of the dice roll.
     *
     * @param x              the x-coordinate of the field on the board
     * @param dice           the value of the dice roll
     * @param currentPlayers the current player
     * @return true if it is valid to put a stone on the field, false otherwise
     */
    private boolean isValidToPut(int x, int dice, int currentPlayers) {
        if (currentPlayers == 0) {
            if (board[x][board[0].length - dice] <= 1) {
                return true;
            }
        } else if (currentPlayers == 1) {
            if (board[x][dice - 1] >= -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * The rollDice method is used to simulate the rolling of the dice in a game of Backgammon.
     * <p>
     * It generates random integers between 1 and 6, assigns them to the dice array, and checks if they are the same.
     * <p>
     * The doubleRoll field is set to true if the dice have the same value, and false otherwise.
     *
     * @return an array of integers representing the values of the two dice.
     */
    public int[] rollDice() {
        dice[0] = (int) (Math.random() * 6) + 1;
        dice[1] = (int) (Math.random() * 6) + 1;
        doubleRoll = isSame(dice);
        if (isattacked()) {
            if (currentPlayers == 0) {
                if (!isValidToPut(1, dice[0], currentPlayers) && !isValidToPut(1, dice[1], currentPlayers)) {
                    this.unvalidDice = true;
                    currentPlayers = 1;
                } else if (!isValidToPut(1, dice[0], currentPlayers) || !isValidToPut(1, dice[1], currentPlayers)) {
                    rollDice();
                }
            } else if (currentPlayers == 1) {
                if (!isValidToPut(0, dice[0], currentPlayers) && !isValidToPut(0, dice[1], currentPlayers)) {
                    this.unvalidDice = true;
                    currentPlayers = 0;
                } else if (!isValidToPut(0, dice[0], currentPlayers) || !isValidToPut(0, dice[1], currentPlayers)) {
                    rollDice();
                }
            }
        }
        return dice;
    }

    /**
     * A private helper method to check if the two dice have the same value
     *
     * @param dice an array of integers representing the values of the two dice
     * @return a boolean value indicating whether the two dice have the same value or not
     */
    private boolean isSame(int[] dice) {
        return dice[0] == dice[1];
    }

    /**
     * The isDoubleRoll method is used to check if the current roll of the dice is a double roll or not.
     *
     * @return A boolean value indicating whether the current roll is a double roll (true) or not (false).
     */
    public boolean isDoubleRoll() {
        return doubleRoll;
    }

    /**
     * Returns the current state of the Backgammon board as a 2D integer array.
     * The first dimension of the array represents the side, with the first index being downside and the second index being upside.
     * The second dimension of the array represents the points on the board, where a positive number of stones represents player 1's stones, a negative number of stones represents player 2's stones, and zero represents an empty point.
     *
     * @return the current state of the board as a 2D integer array
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Returns the number of attacked stones for each player.
     * The first element in the array represents the number of attacked stones for player 1,
     * and the second element represents the number of attacked stones for player 2.
     *
     * @return int[] the number of attacked stones for each player
     */
    public int[] getAttacked_Stones() {
        return attacked_Stones;
    }

    /**
     * Returns the current player.
     *
     * @return an integer representing the current player.
     */
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * Returns a 2D boolean array representing the valid stones that can be moved for each player.
     *
     * @return a 2D boolean array representing the valid stones that can be moved for each player
     */
    public boolean[][] getValidStones() {
        return validStones;
    }

    /**
     * Returns an array of integers representing the number of stones that have been collected for each player.
     *
     * @return an array of integers representing the number of stones that have been collected for each player.
     */
    public int[] getStonesCollected() {
        return stonesCollected;
    }

    /**
     * validStones(int dice) is a method that sets the valid stones that can be moved for the current player based on the value of the dice roll.
     * It checks whether each stone on the board is valid to move, valid to pop, or not valid to move at all.
     * It loops through the board and check if the board field is not empty, if it is valid to move by passing the current point, the dice roll and the current player,
     * or if it is valid to pop by checking if there are stones in the inner area and if the current player can pop from that point.
     * If the point is not valid it sets the validStones field to false.
     *
     * @param dice an integer representing the value of the dice roll.
     */
    public void validStones(int dice) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] != 0) {
                    if (isValidToMove(i, j, dice, currentPlayers)) {
                        validStones[i][j] = true;
                    } else if (areStonesInArea(board)) {
                        if (currentPlayers == 0) {
                            if (isValidToPop(i, j, dice) && board[i][j] <= -1) {
                                validStones[i][j] = true;
                            }
                        } else if (currentPlayers == 1) {
                            if (isValidToPop(i, j, dice) && board[i][j] >= 1) {
                                validStones[i][j] = true;
                            }
                        }
                    }
                } else {
                    validStones[i][j] = false;
                }
            }
        }
    }

    /**
     * This method checks whether the current player has any attacked stones on the board.
     *
     * @return a boolean value indicating whether the current player has any attacked stones or not.
     */
    public boolean isattacked() {
        return attacked_Stones[currentPlayers] != 0;
    }

    /**
     * showWhere method displays the valid fields on the board where a stone can be put.
     *
     * @param dice the value of the rolled dice
     *             It checks if the current player can put a stone on the board and mark it as valid.
     *             The move is valid if the player is player 0 and the point on the board which is dice-1 is empty or player 1 and point at the board which is board[0].length - dice is empty.
     */
    public void showWhere(int dice) {
        if (currentPlayers == 0) {
            if (isValidToPut(1, dice, currentPlayers)) {
                validStones[1][board[0].length - dice] = true;
            }
        }
        if (currentPlayers == 1) {
            if (isValidToPut(0, dice, currentPlayers)) {
                validStones[0][dice - 1] = true;
            }
        }
    }

    /**
     * Private helper method that checks if it is valid to pop a stone from a certain field on the board based on the current player and the value of the dice.
     *
     * @param x    The x-coordinate of the field on the board where the stone is to be popped.
     * @param y    The y-coordinate of the field on the board where the stone is to be popped.
     * @param dice The value of the dice being used to determine the validity of the move.
     * @return A boolean value indicating if it is valid to pop a stone from the specified field on the board.
     */

    private boolean isValidToPop(int x, int y, int dice) {

        if (currentPlayers == 0) {
            if (y == (dice - 1) && board[x][y] < 0) {
                return true;
            } else if (y <= (dice - 1) && board[x][dice - 1] >= 0) {
                for (int j = 5; j > y; j--) {
                    if (board[x][j] < 0) {
                        return false;
                    }
                }
                return true;
            }
        } else if (currentPlayers == 1) {
            if (y == (board[0].length - dice) && board[x][y] > 0) {
                return true;
            } else if (y >= (board[0].length - dice) && board[x][board[0].length - dice] <= 0) {
                for (int j = 6; j > y; j++) {
                    if (board[x][j] > 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether there are any stones in the inner area of the board.
     * The inner area is defined as the player's home territory, which is the first 6 points for player 1 and the last 6 points for player 2.
     * If there are any stones in the inner area for the current player, the method will return ture. Otherwise, it will return false.
     *
     * @param board the current state of the board
     * @return boolean indicating whether there are any stones in the inner area.
     */
    public boolean areStonesInArea(int[][] board) {
        int tempj = 0;

        if (isattacked()) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            if (currentPlayers == 0) {
                if (i == 0) tempj = 6;
                for (int j = tempj; j < board[0].length; j++) {
                    if (board[i][j] <= -1) {
                        return false;
                    }
                }
            } else if (currentPlayers == 1) {
                tempj = board[0].length - 1;
                if (i == 1) tempj = 5;
                for (int j = tempj; j >= 0; j--) {
                    if (board[i][j] >= 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Determines if the game is over by checking if the current player has collected 15 stones.
     *
     * @param stonesCollected the array of integers representing the number of stones that have been collected for each player
     * @return true if the current player has collected 15 stones, false otherwise.
     */
    public boolean gameOver(int[] stonesCollected) {

        return stonesCollected[currentPlayers] == 15;
    }
}
