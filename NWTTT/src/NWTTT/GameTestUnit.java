package NWTTT;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTestUnit {

	// Test if the game can detect the first available block
		public void checkWin() {
			GamePanel gp = new GamePanel();
			gp.board[0][0] = gp.board[0][1] = gp.board[0][2] = 'P';
			assertEquals(true, gp.checkVert(0,1));
		}
		
	// Test if the game blocks non valid moves
	@Test
	public void val() {
		GamePanel gp = new GamePanel();
		gp.board[0][0] = 'P';
		assertEquals(false, gp.isVal(0,0));
	}

	// Test if the game makes all blocks available at the beginning of the game
	@Test
	public void startBlocks() {
		GamePanel gp = new GamePanel();
		gp.setGame();
		int myFlag = 1;
		int i, j;
		for (i=0; i<5; i++)
		{
			for (j=0; j<5; j++)
			{
				if (gp.board[i][j] != 'A')
					myFlag = 0;
			}
		}
		assertEquals(1, myFlag);
	}

	//-------------------------------------- Interface Test --------------------------------------//

	// Test if the difficulty level affects compMove() method - Easy
	@Test
	public void difficultyTest() {
		GamePanel gp = new GamePanel();
		gp.setGame();
		gp.diff = 1;					// set to easy
		gp.compMove();
		assertEquals(false, gp.isVal(0,0));
	}
	
	// Test if the difficulty level affects compMove() method - Hard (Smart)
		@Test
		public void difficultyTestTwo() {
			GamePanel gp = new GamePanel();
			gp.setGame();
			gp.diff = 100;					// set to hard
			gp.board[0][0] = gp.board[0][2] = 'P';
			gp.compMove();
			assertEquals(false, gp.isVal(0,1));			// computer should block the middle block
		}
}
