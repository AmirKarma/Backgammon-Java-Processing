package test;

import org.junit.*;

import static org.junit.Assert.*;

import model.Backgammon;

public class BackgammonTest {
    Backgammon game;

    @Before
    public void setUp() {
        game = new Backgammon();
    }

    @Test
    public void testShowWhere() {
        Backgammon game = new Backgammon();
        game.showWhere(5);

        boolean[][] expectedValidStones = new boolean[][]{{false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, true, false, false, false, false}};

        assertTrue(game.getValidStones()[0][5] == expectedValidStones[0][5]);
        assertTrue(game.getValidStones()[1][7] == expectedValidStones[1][7]);
    }

    @Test
    public void testValidStones() {
        Backgammon game = new Backgammon();
        game.validStones(4);
        boolean[][] expected = new boolean[][]{{false, false, false, false, false, true, false, true, false, false, false, false},
                {true, false, false, false, false, false, false, false, false, false, false, true}};
        assertArrayEquals(expected, game.getValidStones());
    }

    @Test
    public void testMoveStones() {

        //Test trying to move a stone to an invalid ending position
        game.moveStones(1, 11, 5);
        assertEquals(-2, game.getBoard()[1][11]);

        // Test moving a stone from a valid starting position
        game.moveStones(1, 11, 6);
        assertEquals(-1, game.getBoard()[1][11]);

        // Test trying to move a stone from an invalid starting position
        game.moveStones(0, 1, 4);
        assertEquals(0, game.getBoard()[0][1]);

    }

    @Test
    public void testIsAttacked() {
        // Test if player is attacked
        game.getAttacked_Stones()[0] = 1;
        assertTrue(game.isattacked());
        // Test if player is not attacked
        game.getAttacked_Stones()[0] = 0;
        assertFalse(game.isattacked());
    }

    @Test
    public void testRollDice() {
        Backgammon game = new Backgammon();
        int[] dice = game.rollDice();
        assertTrue(dice[0] >= 1 && dice[0] <= 6);
        assertTrue(dice[1] >= 1 && dice[1] <= 6);
    }

    @Test
    public void testAreStonesInArea_withStonesInArea() {
        Backgammon game = new Backgammon();
        int[][] board = new int[][]{{-5, -5, -5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5}};
        assertTrue(game.areStonesInArea(board));
    }

    @Test
    public void testAreStonesInArea_withNoStonesInArea() {
        Backgammon game = new Backgammon();
        int[][] board = new int[][]{{-5, -5, -4, 2, 2, 2, -1, 0, 0, 0, 0, 5},
                {0, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, 0}};
        assertFalse(game.areStonesInArea(board));
    }

    @Test
    public void testGameOver() {
        Backgammon game = new Backgammon();
        int[] stonesCollected =new int[2];
        stonesCollected =new int[] {15, 4}; // set the stones collected to the maximum

        boolean result = game.gameOver(stonesCollected);
        assertTrue(result); // the game should be over since all stones have been collected

        stonesCollected = new int[]{0, 0}; // set the stones collected to zero

        result = game.gameOver(stonesCollected);
        assertFalse(result); // the game should not be over since no stones have been collected*/
    }
}