package NWTTT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NWThreads extends Thread{
	private Socket socket;
	int tempRow, tempCol;
	String temp;
	
	//\\ Constructor //\\
	NWThreads(Socket s){
		socket = s;													// getting socket, and using in this class
	}


	public void run(){
		if (NetworkSetup.rdbtnServer.isSelected() == true)
			GameFrame.chatLog.setText(GameFrame.chatLog.getText() + "Got a new connection request!\n");	// Showing the server is up
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (null != (temp=br.readLine()))
			{
				if (temp.charAt(0) == 'T')							// if it is a Text message
					GameFrame.chatLog.setText(temp.substring(1, temp.length())+  "\n" + GameFrame.chatLog.getText());					// renew Server's chat log
				else if (temp.charAt(0) == 'M')						// if it is a Move
				{
					tempRow = Character.getNumericValue(temp.charAt(1));
					tempCol = Character.getNumericValue(temp.charAt(2));
					GamePanel.humanMove(tempRow, tempCol);
				}
				else if (temp.charAt(0) == 'C')						// if it is a Command
				{
					if (temp.charAt(1) == 'P')
					{
						GameFrame.playerFirst.setSelected(true);
						GameFrame.firstMove = 'P';
					}
					else if (temp.charAt(1) == 'O')
					{
						GameFrame.opponentFirst.setSelected(true);
						GameFrame.firstMove = 'O';
					}
					else if (temp.charAt(1) == 'S')					// Start the game as soon as opponent starts
					{
						if (GamePanel.gameStatus!=1)		this.setGame();
					}
					else if (temp.charAt(1) == 'G')					// Start the game as soon as opponent starts
					{
						GamePanel.gridNum = temp.charAt(2)-48;

					}
				}
			}
			socket.close();
			GameFrame.chatLog.setText(GameFrame.chatLog.getText() + " . .. ... Disconnected!\n____________________________________________________________\n");	// Showing whenever disconnected
		} catch (IOException e) {			e.printStackTrace();		}
	}


	private void setGame(){
		int i, j;
		for (i=0; i<5; i++)
		{
			for (j=0; j<5; j++)
			{
				GamePanel.board[i][j] = 'A';
			}
		}
		GamePanel.vWin=0;
		GamePanel.hWin=0;
		GamePanel.fDiagWin=0;
		GamePanel.bDiagWin=0;
		GamePanel.gameStatus = 1;				// the game is started, and action listener can start working
	}

}
