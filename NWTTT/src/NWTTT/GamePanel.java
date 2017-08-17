package NWTTT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;


public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static char board[][] = new char [5][5];						// array of blocks/grids
	static int row, col, opRow, opCol;									// place of last move
	public static char turn = 'P';
	public static int gridNum = 3;										// Number of grids would be 3 by default
	static int gameStatus = 0;											// Game is not active unless gameStatus is 1
	public static int diff =1;
	public int bg = 5;													// the number for the default background
	static int vWin, hWin, fDiagWin, bDiagWin;							// 1 if there is a win 0 if not
	static int vTopR, vBotR, vCol;										// info to draw  vertical win line
	static int hLeftC, hRightC, hRow;									// info to draw horizontal win line
	static int fLeftC, fBotR, fRightC, fTopR;							// info for forward diagonal win line
	static int bLeftC, bBotR, bRightC, bTopR;							// info for back diagonal win line
	
	public void paint(Graphics g){
		super.paint(g);
		BufferedImage img = null;
		try {
			String bgAddress = "files/bg" + bg + ".png";
			img = ImageIO.read(new File(bgAddress));
		} catch (Exception e) { e.printStackTrace(); }
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));						// Setting Thickness for lines
        g.setColor(Color.WHITE);
		for(int i=1; i<(gridNum); i++)
		{
			g.drawLine(i*(getWidth()/(gridNum)), 5, i*(getWidth()/(gridNum)), getHeight()-5);	//vertical lines
			g.drawLine(5, i*(getHeight()/(gridNum)), getWidth()-5, i*(getHeight()/(gridNum)));  //horizontal lines
		}
		
		//\\ Drawing Symbols //\\-------------------------------------------------------------------- //\\
		int i, j, blockX, blockY;
		int gridW = (getWidth()/(gridNum)) - 20;
		int gridH = (getHeight()/(gridNum)) - 20;
		for (i=0; i<gridNum; i++){
			for (j=0; j<gridNum; j++){
				blockX = ((j*getWidth())/gridNum) + 10;
				blockY = ((i*getHeight())/gridNum) + 10;
				if (board[i][j] != 'A'){
					if (board[i][j] == 'P'){
						g.setColor(Color.BLUE);						// if block belongs to the Player
						if (GameFrame.playerSymbol == 'X'){
							g.drawLine(blockX, blockY, blockX+gridW, blockY+gridH);				// (x1, y1, x2, y2)
		        			g.drawLine(blockX+gridW, blockY, blockX, blockY+gridH);				// (x2, y1, x1, y2)
						}
						if (GameFrame.playerSymbol == 'O'){
							g.drawOval(blockX, blockY, gridW, gridH);		// (x, y, width, height)
						}
						if(GameFrame.playerSymbol == 'R'){
							g.drawRect(blockX, blockY, gridW, gridH);
						}
						if(GameFrame.playerSymbol == 'T'){
							g.drawLine(blockX+((gridW)/2), blockY, blockX, blockY+(gridH));
							g.drawLine(blockX, blockY+gridH, blockX+gridW, blockY+(gridH));
							g.drawLine(blockX+((gridW)/2), blockY,blockX+gridW, blockY+(gridH));
						}
					}
					else if (board[i][j] == 'O'){
						g.setColor(Color.RED);						// if block belongs to the Opponent
						if (GameFrame.oppSymbol == 'X'){
							g.drawLine(blockX, blockY, blockX+gridW, blockY+gridH);				// (x1, y1, x2, y2)
		        			g.drawLine(blockX+gridW, blockY, blockX, blockY+gridH);				// (x2, y1, x1, y2)
						}
						if (GameFrame.oppSymbol == 'O'){
							g.drawOval(blockX, blockY, gridW, gridH);		// (x, y, width, height)
						}
						if(GameFrame.oppSymbol == 'R'){
							g.drawRect(blockX, blockY, gridW, gridH);
						}
						if(GameFrame.oppSymbol == 'T'){
							g.drawLine(blockX+((gridW)/2), blockY, blockX, blockY+(gridH));
							g.drawLine(blockX, blockY+gridH, blockX+gridW, blockY+(gridH));
							g.drawLine(blockX+((gridW)/2), blockY,blockX+gridW, blockY+(gridH));
						}
						
					}
				}
			}
		}
		//\\ Draw win lines //\\
		g.setColor(Color.BLACK);						// if block belongs to the Player
		if(vWin==1){
			g.drawLine( ((getWidth()/gridNum-1)*vCol)+((getWidth()/gridNum-1)/2),(getHeight()/gridNum)*vTopR ,((getWidth()/gridNum-1)*vCol)+((getWidth()/gridNum-1)/2) , (getHeight()/gridNum)*(vBotR+1));
		}
		if(hWin==1){
			g.drawLine( (getWidth()/gridNum)*hLeftC, ((getHeight()/gridNum)*hRow)+((getHeight()/gridNum)/2), (getWidth()/gridNum)*(hRightC+1), ((getHeight()/gridNum)*hRow)+((getHeight()/gridNum)/2));
		}
		if(fDiagWin==1){
			g.drawLine( (getWidth()/gridNum)*fLeftC, (getHeight()/gridNum)*(fBotR+1), (getWidth()/gridNum)*(fRightC+1), (getHeight()/gridNum)*(fTopR));
		}
		if(bDiagWin==1){
			g.drawLine((getWidth()/gridNum)*bLeftC, (getHeight()/gridNum)*bTopR, (getWidth()/gridNum)*(bRightC+1), (getHeight()/gridNum)*(bBotR+1));
		}
	}

	public GamePanel() {
		for (int i=0; i<5; i++)
		{
			for (int j=0; j<5; j++)
			{
				board[i][j] = 'A';
			}
		}
		vWin=0;							// set vertical win to 0
		hWin=0;							// set horizontal win
		fDiagWin=0;						// set forward diag win
		bDiagWin=0;						// set back diag win
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (gameStatus == 0)		return;				// only store the move if the game is going on
				if (turn == 'O')			return;				// don't store the move, if it is Opponent's turn
				row = convertToRow(e.getY());					// get the valid row based on Y position
				col = convertToCol(e.getX());					// get the valid column based on X position
				if(isVal(row,col)==false)	return;				// square is occupied, do nothing
				board[row][col]=turn;							// store move
				//if (checkTie() == 'T')		return;				// check tie after players move
				if (GameFrame.human.isSelected() == true)		// if opponent is Computer, send the move (row, column)
				{
					GameFrame.sendMsg("M" + row + col);
				}
				if(checkWin(row, col)){
					GameFrame.playerWon.setText((Integer.parseInt(GameFrame.playerWon.getText())+1)+"");
					repaint();
					return;
				}
				
				repaint();
				turn='O'; 										// Switch turn, after each move
			
				if (GameFrame.cpu.isSelected() == true)		// if opponent is Computer
				{
					compMove();								// Register Computer's Move
					if(checkWin(opRow, opCol)){
						GameFrame.opponentWon.setText((Integer.parseInt(GameFrame.opponentWon.getText())+1)+"");
					}
					turn='P'; 								// Switch turn, after each move
				}
				repaint();
				if (checkTie() == 'T')		return;				// check tie after opponent's move
			}
		});
	}
	
	//\\ Convert the Y value to col //\\
	int convertToCol(int myX){
		if(myX%(this.getWidth()/gridNum) < gridNum){
			return ((myX/(this.getWidth()/gridNum))-1);
		}
		return (myX/(this.getWidth()/gridNum));
	}
	//\\ Convert the Y value to row //\\
	int convertToRow(int myY){
		if(myY%(this.getHeight()/gridNum) < gridNum){
			return ((myY/(this.getHeight()/gridNum))-1);
		}
		return (myY/(this.getHeight()/gridNum));
	}
	
	//check if the square clicked on is available
	boolean isVal(int row, int col){
		if(board[row][col] != 'A')
			return false;			//square is unavailable return false
		else
			return true;			//square is available
	}
	//\\ Start method sets everything ready for a new game //\\
	public void setGame(){
		int i, j;
		for (i=0; i<5; i++)
		{
			for (j=0; j<5; j++)
			{
				board[i][j] = 'A';
			}
		}
		vWin=0;
		hWin=0;
		fDiagWin=0;
		bDiagWin=0;
		gameStatus = 1;				// the game is started, and action listener can start working
		repaint();
	}
	public void  diffChange(int difficulty)
	{
		diff = difficulty;
	}
	
	//\\ Store Received Human Move from the Network//\\
	public static void humanMove(int tempRow, int tempCol){
		board[tempRow][tempCol] = 'O';		// register the move
		if (checkWin(tempRow,tempCol) == true)
		{
			GameFrame.opponentWon.setText((Integer.parseInt(GameFrame.opponentWon.getText())+1)+"");
		}
		turn = 'P';
	}
	
	//\\ Store Computer Move //\\
	public void compMove(){
		if(diff <= 33){				//difficulty level on easiest
			firstAv();				//search for first available square	
		}
		else if(diff <= 66){			//difficulty on med
			randAv();				//choose a random and available block 
		}
		else if(diff > 66){			//difficulty hard
			hardDiff();
		}
		
	}
	private void firstAv(){
		int i, j;
		for (i=0; i<5; i++)
		{
			for (j=0; j<5; j++)
			{
				if(board[i][j] == 'A'){
					board[i][j]='O';
					opRow=i;
					opCol=j;
					return;
				}
			}
		}
		
	}
	private void randAv(){
		int i, j;
		i= (int) (Math.random()*(gridNum));
		j= (int) (Math.random()*(gridNum));
		while(board[i][j] != 'A'){
			i= (int) (Math.random()*(gridNum));
			j= (int) (Math.random()*(gridNum));
		}
		board[i][j]='O';
		opRow=i;
		opCol=j;
		return;
	}
	
	private void hardDiff(){
		if(smartMove('O'))			// search for winning computer move
			return;
		if(smartMove('P'))			// search for a player winning square to block
			return;
		randAv();					// there were not any desirable moves, make random move
	}
	
	private boolean smartMove(char symb){
		for(int i=0; i<gridNum; i++){				// loop through rows 
			for(int j=0; j<gridNum; j++){			//loop through columns
				if(board[i][j]=='A'){				// square is open
					if(winSearch(i,j,symb)){		// method searches if the open square is a potential win
						board[i][j]='O';			// square is either a possible win for comp or player, either way place comp move there
						opRow=i;					// move info for checkwin/winline
						opCol=j;					// move info for checkwin/winline
						return true;				// found desirable move
					}
				}
			}
		}
		return false;								// did not find desirable move
	}
	
	private boolean winSearch(int r, int c, char symb){
		if(vSearch(r,c,symb))		// search for a possible move by checking counting symbols in the vertical
			return true;			
		if(hSearch(r,c,symb))		// search for a possible move by checking counting symbols in the horizontal
			return true;
		if(fdSearch(r,c,symb))		// search for a possible move by checking counting symbols in the forward diagonal
			return true;
		if(bdSearch(r,c,symb))		// search for a possible move by checking counting symbols in the backward diagonal
			return true;
		return false;
	}
	
	private boolean vSearch(int r, int c, char symb){
		int moveVal=0;						// hold the value of the possible move
		int tempR;							// hold different values of row for moving vertically
		if(r!=0){							// check square is not at the top of the grid to prevent going out of bounds
			tempR=r-1;						// start checking squares by moving up the grid 1 place
			while(board[tempR][c]==symb){	// consecutive symbols keep moving up grid 
				moveVal++;					// add 1 to the value of the possible move
				if(tempR==0)				// check if at top of grid so we don't go out of bounds
					break;
				tempR--;					// move up 1 row on grid
			}
		}
		if(r!=gridNum-1){					// check square is not at bottom of grid to prevent going out of bounds
			tempR=r+1;						// start checking squares by moving down the grid 1 place
			while(board[tempR][c]==symb){	// consecutive symbols keep moving down grid
				moveVal++;					// add 1 to the value of the possible move
				if(tempR==gridNum-1)		// check if at the bottom of grid so array doesn't go out of bounds
					break;
				tempR++;					// move down 1 row on the grid
			}
		}
		if(moveVal>1)						// if move value is 2 or greater then it is a desirable move
			return true;					
		return false;						// move was not of value/desirable
	}
	
	private boolean hSearch(int r, int c, char symb){
		int moveVal=0;						// hold the value of the possible move
		int tempC;							// hold different values of column for moving horizontally
		if(c!=0){							// check square is not at the left end of the grid to prevent going out of bounds
			tempC=c-1;						// start checking squares by moving left on the grid 1 place
			while(board[r][tempC]==symb){	// consecutive symbols keep moving left on grid 
				moveVal++;					// add 1 to the value of the possible move
				if(tempC==0)				// check if at the left end of grid so we don't go out of bounds
					break;
				tempC--;					// move left 1 column on grid
			}
		}
		if(c!=gridNum-1){					// check square is not at right end of grid to prevent going out of bounds
			tempC=c+1;						// start checking squares by moving right on the grid 1 place
			while(board[r][tempC]==symb){	// consecutive symbols keep moving right on grid
				moveVal++;					// add 1 to the value of the possible move
				if(tempC==gridNum-1)		// check if at the right end of grid so array doesn't go out of bounds
					break;
				tempC++;					// move right 1 column on the grid
			}
		}
		if(moveVal>1)						// if move value is 2 or greater then it is a desirable move
			return true;					
		return false;						// move was not of value/desirable
	}
	
	private boolean fdSearch(int r, int c, char symb){
		int moveVal=0;								// hold the value of the possible move
		int tempC, tempR;							// hold different values of column and row for moving diagonally
		if(r!=0 && c!=gridNum-1){					// check square is not at top or right end of grid, to avoid out bounds
			tempC=c+1;								// start checking squares by moving right 1 and up 1
			tempR=r-1;
			while(board[tempR][tempC]==symb){		// consecutive symbols keep moving up and right on grid 
				moveVal++;							// add 1 to the value of the possible move
				if(tempR==0 || tempC==gridNum-1)	// check if at the top or right end of grid so we don't go out of bounds
					break;
				tempC++;							// move right 1 and up 1 on grid
				tempR--;							
			}
		}
		if(r!=gridNum-1 && c!=0){					// check square is not at bottom or left end of grid to prevent going out of bounds
			tempC=c-1;								// start checking squares by moving down and left on the grid 
			tempR=r+1;
			while(board[tempR][tempC]==symb){		// consecutive symbols keep moving down and left on grid
				moveVal++;							// add 1 to the value of the possible move
				if(tempR==gridNum-1 || tempC==0)	// check if at the bottom or left end of grid so array doesn't go out of bounds
					break;
				tempC--;							// move left and down on the grid
				tempR++;
			}
		}
		if(moveVal>1)								// if move value is 2 or greater then it is a desirable move
			return true;					
		return false;								// move was not of value/desirable
	}
	
	private boolean bdSearch(int r, int c, char symb){
		int moveVal=0;								// hold the value of the possible move
		int tempC, tempR;							// hold different values of column and row for moving diagonally
		if(r!=0 && c!=0){							// check square is not at top or left end of grid, to avoid out bounds
			tempC=c-1;								// start checking squares by moving left 1 and up 1
			tempR=r-1;
			while(board[tempR][tempC]==symb){		// consecutive symbols keep moving up and left on grid 
				moveVal++;							// add 1 to the value of the possible move
				if(tempR==0 || tempC==0)			// check if at the top or left end of grid so we don't go out of bounds
					break;
				tempC--;							// move left 1 and up 1 on grid
				tempR--;							
			}
		}
		if(r!=gridNum-1 && c!=gridNum-1){				// check square is not at bottom or right end of grid to prevent going out of bounds
			tempC=c+1;									// start checking squares by moving down and right on the grid 
			tempR=r+1;
			while(board[tempR][tempC]==symb){			// consecutive symbols keep moving down and right on grid
				moveVal++;								// add 1 to the value of the possible move
				if(tempR==gridNum-1 || tempC==gridNum-1)// check if at the bottom or right end of grid so array doesn't go out of bounds
					break;
				tempC--;							// move right and down on the grid
				tempR++;
			}
		}
		if(moveVal>1)								// if move value is 2 or greater then it is a desirable move
			return true;					
		return false;								// move was not of value/desirable
	}
	
	public static boolean checkWin(int row, int col){
		int winsum=0;
		if(checkVert(row, col))				// check if there is a vertical win
			winsum=winsum+1;
		if(checkHorizontal(row, col))		// check for a horizontal win
			winsum=winsum+1;
		if(checkForwardDiag(row, col))		// check for a forward diagonal win
			winsum=winsum+1;
		if(checkBackDiag(row, col))			// check for a backward diagonal win
			winsum=winsum+1;
		if(winsum == 0)								// there were no winning lines
			return false;
		else
			return true;							// there was win line(s)
	}
	
	static boolean checkVert(int row, int col){
		
		int sum=0;									// sum the number of consecutive symbols
		while( board[row][col]== turn ){ 			// go to the top grid position with matching symbol 
			vTopR=row;								// set top row position
			if(row==0)								// if row is at zero break out of while
				break;
			row--;
		}
		if(board[row][col] != turn)					// compensate for previous while loop iteration 
			row++;
		while( board[row][col]==turn ){				// travel down the column summing the consecutive symbols
			vBotR=row;								// save position of the bottom most symbol
			sum=sum+1;
			if(row==gridNum-1)						// condition so that array does not go out of bounds
				break;
			row++;
		}
		if(sum>=3){									// If the is a Vertical Win
			vWin=1;
			vCol=col;
			gameStatus = 0;
			return true;
		}
		return false;
	}
	
	private static boolean checkHorizontal(int row, int col){
		int sum=0;									// sum of consecutive symbols
		while(board[row][col]==turn){				// go to farthest left of grid with same symbol 
			hLeftC=col;								// set far left point 
			if(col==0)
				break;
			col--;
		}
		if(board[row][col] != turn)					// compensate for previous while loop iteration
			col++;
		while(board[row][col]==turn){				// travel right across grid, summing consecutive symbols
			hRightC=col;
			sum=sum+1;
			if(col==gridNum-1)						// keep array from going out of bounds
				break;
			col++;
		}
		if(sum>=3){
			hWin=1;									// there is a horizontal win
			hRow=row;
			gameStatus=0;
			return true;
		}
		return false;
	}
	
	private static boolean checkForwardDiag(int row, int col){
		int sum=0;
		while(board[row][col]==turn){
			fLeftC=col;
			fBotR=row;
			if(row==gridNum-1)
				break;
			if(col==0)
				break;
			col--;
			row++;
		}
		if(board[row][col]!=turn){
			row--;
			if (row==-1)		row=0;
			col++;
			if(col==gridNum)	col=gridNum-1;
		}
		while(board[row][col]==turn){
			fRightC=col;
			fTopR=row;
			sum=sum+1;
			if(col==gridNum-1)
				break;
			if(row==0)
				break;
			col++;
			row--;
		}
		if(sum>=3){
			fDiagWin=1;
			gameStatus=0;
			return true;
		}
		return false;
	}
	
	private static boolean checkBackDiag(int row, int col){
		int sum=0;
		while(board[row][col]==turn){
			bLeftC=col;
			bTopR=row;
			if(row==0)
				break;
			if(col==0)
				break;
			col--;
			row--;
		}
		if(board[row][col]!=turn){
			row++;
			if(row==gridNum)	row=gridNum-1;
			col++;
			if(col==gridNum)	col=gridNum-1;
		}
		while(board[row][col]==turn){
			bRightC=col;
			bBotR=row;
			sum=sum+1;
			if(col==gridNum-1)
				break;
			if(row==gridNum-1)
				break;
			col++;
			row++;
		}
		if(sum>=3){
			bDiagWin=1;
			gameStatus=0;
			return true;
		}
		return false;
	}
	private char checkTie(){
		for (int i=0; i<gridNum; i++){
			for (int j=0; j<gridNum; j++){
				if (board[i][j] == 'A')				// if there is still an available block
					return 'N';						// return Not a Tie
			}
		}
		gameStatus = 0;								// End of the game, Stop the game
		GameFrame.tied.setText((Integer.parseInt(GameFrame.tied.getText())+1)+"");
		repaint();
		return 'T';									//  if no available block is left, return Tie 
	}
	public void changeBacouground()
	{
		if (bg == 6) bg = 1;
		else bg++;
		repaint();
		return;
	}
	
}