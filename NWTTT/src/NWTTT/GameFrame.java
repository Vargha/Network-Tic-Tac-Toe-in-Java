package NWTTT;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextArea chatLog;
	static JButton sendBtn;
	public static JRadioButton cpu, human;
	public static char playerSymbol = 'X';
	public static char oppSymbol = 'O';
	JComboBox<?> yourSymbol, opponentSymbol;
	public static JRadioButton playerFirst, opponentFirst;				// First move radio button
	JPanel panel;
	private final ButtonGroup Opponent_buttonGroup = new ButtonGroup();
	private final ButtonGroup FirstMove_buttonGroup = new ButtonGroup();
	public static char opponent = 'C';									//default opponent to computer
	public static char firstMove = 'P';									//default first move to player
	char waitFlag = 'V';												// Don't wait unless it's other player's turn
	public static JLabel difLabel, tied;
	public static JSlider difLev;										// difficulty level bar
	public static HelpDialog helpDialog;								// Help Frame
	public static NetworkSetup nwSetup;									// Network Setup Frame
	public static SettingFrame settingFrame;							// Setting Frame
	public static JTextField chatBox;
	JScrollPane scrollPane;												// scroll pane will contain the chat log in it
	public static JLabel playerWon, opponentWon;
	public static String plyName="You", oppName="Opponent";				// Player name, opponent name for chat messages
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//\\ Game Frame //\\__________________________________________________________________________//\\
	public GameFrame() {
		
		//\\ MenuBar //\\	----------------------------------------------------------------------//\\
		JMenuBar menuBar = new JMenuBar();							// create a JMenuBar
		setJMenuBar(menuBar);										// set the JMenuBar
		JMenu system = new JMenu ("System");						// initialize system
		menuBar.add(system);										// add system to the menu
		JMenuItem quit = new JMenuItem("Quit");						// Create a new Jmenu item "Quit"

		quit.addMouseListener(new MouseAdapter() {					// When quit gets clicked
			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.exit(0);										// Quit the game				
			}
		});
		
		
		JMenuItem changeBG = new JMenuItem("Change Background");	// Create a new Jmenu item "Change Background"
		changeBG.addMouseListener(new MouseAdapter() {				// When quit gets clicked
			@Override
			public void mouseReleased(MouseEvent arg0) {
				((GamePanel) panel).changeBacouground();				
			}
		});
		JMenuItem Settings = new JMenuItem("Settings");				// Create a new Jmenu item "Change Background"
		Settings.addMouseListener(new MouseAdapter() {				// When quit gets clicked
			@Override
			public void mouseReleased(MouseEvent arg0) {
				settingFrame = new SettingFrame();
				settingFrame.setVisible(true);				
			}
		});
		JMenuItem Help = new JMenuItem("Help");						// Create a new Jmenu item "Change Background"
		Help.addMouseListener(new MouseAdapter() {					// When quit gets clicked
			@Override
			public void mouseReleased(MouseEvent arg0) {
				helpDialog = new HelpDialog();
				helpDialog.setVisible(true);				
			}
		});
		system.add (changeBG);
		system.add (Settings);
		system.add (Help);
		system.add (quit);										// add quit to the system_menu
		//\\ MenuBar Ends //\\	----------------------------------------------------------------------//\\
		//\\ Set Icon //\\		----------------------------------------------------------------------//\\
		setIconImage(Toolkit.getDefaultToolkit().getImage("files/icon.png"));
		//\\ Set Icon Ends //\\	----------------------------------------------------------------------//\\
		
		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//\\ Game Panel //\\	----------------------------------------------------------------------//\\
		panel = new GamePanel();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				repaint();
			}
		});
		panel.setBorder(new LineBorder(new Color(176, 196, 222), 2, true));
		JLabel label = new JLabel("Choose Opponent:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
	
		cpu = new JRadioButton("CPU");
		cpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setComputer();
			}
			//\\ Set game to play against Computer //\\
			private void setComputer() {
				chatLog.setVisible(false);					// Disable chat capability
				chatBox.setVisible(false);					// set disable until player choose to play against human
				sendBtn.setVisible(false);					// Disable chat capability
				difLev.setVisible(true);					// Disable chat capability
				difLabel.setVisible(true);					// Disable chat capability
				opponent='C';								// Change opponent to computer
			}
		});
		Opponent_buttonGroup.add(cpu);
		cpu.setFont(new Font("Tahoma", Font.BOLD, 12));
		cpu.setSelected(true);
		
		human = new JRadioButton("Human");
		human.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nwSetup = new NetworkSetup();				// Open Network Settings Frame 
				nwSetup.setVisible(true);
				setHuman();
			}
			
			//\\ Set game to play against Human //\\
			private void setHuman() {
				chatLog.setVisible(true);					// Enable chat capability
				chatBox.setVisible(true);					// set disable until player choose to play against human
				sendBtn.setVisible(true);					// Enable chat capability
				difLev.setVisible(false);					// Disable chat capability
				difLabel.setVisible(false);					// Disable chat capability
				opponent='H';								// Change opponent to Human
			}
		});
		Opponent_buttonGroup.add(human);
		human.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_1 = new JLabel("Game Results:");
		label_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		JLabel label_2 = new JLabel("Player Won:");
		label_2.setForeground(Color.DARK_GRAY);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		playerWon = new JLabel("0");
		playerWon.setForeground(Color.DARK_GRAY);
		playerWon.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		opponentWon = new JLabel("0");
		opponentWon.setForeground(Color.DARK_GRAY);
		opponentWon.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_5 = new JLabel("Opponent Won:");
		label_5.setForeground(Color.DARK_GRAY);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_6 = new JLabel("Tied:");
		label_6.setForeground(Color.DARK_GRAY);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tied = new JLabel("0");
		tied.setForeground(Color.DARK_GRAY);
		tied.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_8 = new JLabel("First Move:");
		label_8.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		playerFirst = new JRadioButton("Player");
		playerFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstMove='P';
				if(human.isSelected()==true)
					sendMsg("CO");
			}
		});
		FirstMove_buttonGroup.add(playerFirst);
		playerFirst.setSelected(true);
		playerFirst.setForeground(Color.BLACK);
		playerFirst.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		opponentFirst = new JRadioButton("Opponent");
		opponentFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstMove='O';
				if(human.isSelected()==true)
					sendMsg("CP");
			}
		});
		FirstMove_buttonGroup.add(opponentFirst);
		opponentFirst.setForeground(Color.BLACK);
		opponentFirst.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_9 = new JLabel("Your symbol:");
		label_9.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		JLabel label_10 = new JLabel("Opponent's symbol:");
		label_10.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		difLabel = new JLabel("Difficulty Level:");
		difLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		difLev = new JSlider();
		difLev.setPaintTicks(true);
		difLev.setPaintLabels(false);
		difLev.setLabelTable(difLev.createStandardLabels(25));
		
		
		JButton startBtn = new JButton("Let's Start a New Game");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		startBtn.addMouseListener(new MouseAdapter() {					// When click on start button, set the game
			@Override
			public void mouseReleased(MouseEvent arg0) {
				setSymbols();
				((GamePanel) panel).setGame();							// Clear board[][] array, and set the game status
				yourSymbol.setEnabled(false);							// disable changes during the game
				opponentSymbol.setEnabled(false);						// disable changes during the game
				playerFirst.setEnabled(false);							// disable changes during the game
				opponentFirst.setEnabled(false);						// disable changes during the game
				cpu.setEnabled(false);									// disable changes during the game
				human.setEnabled(false);								// disable changes during the game
				((GamePanel) panel).diffChange(difLev.getValue());		// set difficulty level
				if(firstMove=='O')
				{
					if (opponent == 'H')
						while (waitFlag == 'W')							// Wait until receive a signal after other player's move 
						{}
					((GamePanel) panel).compMove();
				}
				if (opponent == 'H')
					sendMsg("CS");
			}
		});
		startBtn.setForeground(Color.WHITE);
		startBtn.setFocusable(false);
		startBtn.setBackground(new Color(0, 128, 0));
		
		JButton quiteBtn = new JButton("Quit");							// When click on quit button
		quiteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.exit(0);
			}
		});
		quiteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		quiteBtn.setForeground(Color.WHITE);
		quiteBtn.setFocusable(false);
		quiteBtn.setBackground(Color.RED);
		
		sendBtn = new JButton("Send \u21B5");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = "  "+plyName+":  " + chatBox.getText();			// get the current value of textSend field
				sendMsg("T"+oppName+": " + message.substring(8, message.length()));
				chatBox.setText("");												// clean the box for the next message
				chatLog.setText(message + "\n" + chatLog.getText());				// renew Server's chat log
			}
		});
		sendBtn.setVisible(false);													// set disable until player choose to play against human
		chatLog = new JTextArea();
		chatLog.setLineWrap(true);
		chatLog.setEditable(false);
		chatBox = new JTextField();
		chatBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER)								// if the pressed key is ENTER
				{
					String message = "  "+plyName+":  " + chatBox.getText();					// get the current value of textSend field
					sendMsg("T"+oppName+": " + message.substring(8, message.length()));
					chatBox.setText("");											// clean the box for the next message
					chatLog.setText(message + "\n" + chatLog.getText());			// renew Server's chat log
				}
			}
		});
		chatLog.setVisible(false);													// set disable until player choose to play against human
		chatLog.setColumns(10);
		chatBox.setVisible(false);
		chatLog.setBackground(UIManager.getColor("TextPane.disabledBackground"));
		chatLog.setForeground(UIManager.getColor("TextPane.caretForeground"));
		chatLog.setEditable(false);
		chatLog.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		
//================================================== Fix the Scrolling  Problem ======================================================= //
//================================================== Fix the Scrolling  Problem ======================================================= //
		
		//scrollPane = new JScrollPane (chatLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setBounds(15, 350, 304, 85);
		contentPane.setLayout(null);
		
		String[] symbolString = { "X", "O", "Rectangle", "Triangle" };
		yourSymbol = new JComboBox<Object>(symbolString);
		opponentSymbol = new JComboBox<Object>(symbolString);
		yourSymbol.setSelectedIndex(0);
		opponentSymbol.setSelectedIndex(1);
		
		chatBox.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(startBtn, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(quiteBtn, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
									.addGap(4))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(panel, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(18)
										.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addGap(34)
										.addComponent(playerWon, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(18)
										.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(opponentWon, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(18)
										.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(tied, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addComponent(cpu, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(human, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addComponent(difLabel, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(18)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(difLev, 0, 0, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(playerFirst, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(opponentFirst, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))))
								.addComponent(opponentSymbol, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
								.addComponent(yourSymbol, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(chatLog, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(chatBox, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(2))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cpu)
								.addComponent(human))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
								.addComponent(playerWon, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
								.addComponent(opponentWon, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
								.addComponent(tied, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(playerFirst)
								.addComponent(opponentFirst))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(yourSymbol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(opponentSymbol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(difLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(difLev, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(startBtn)
								.addComponent(quiteBtn))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chatLog, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(chatBox)
						.addComponent(sendBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		//\\ Game Panel Ends //\\----------------------------------------------------------------------//\\
	}
	private void setSymbols()
	{
		if		(yourSymbol.getSelectedIndex() == 0)			playerSymbol='X';
		else if	(yourSymbol.getSelectedIndex() == 1)			playerSymbol='O';
		else if	(yourSymbol.getSelectedIndex() == 2)			playerSymbol='R';
		else if	(yourSymbol.getSelectedIndex() == 3)			playerSymbol='T';
		
		if		(opponentSymbol.getSelectedIndex() == 0)			oppSymbol='X';
		else if	(opponentSymbol.getSelectedIndex() == 1)			oppSymbol='O';
		else if	(opponentSymbol.getSelectedIndex() == 2)			oppSymbol='R';
		else if	(opponentSymbol.getSelectedIndex() == 3)			oppSymbol='T';
	}
	
	
	
	
	
	public static int myPort = 1201;									// Communication Port
	public static BufferedWriter bw;
	//\\ EVERYTHING IS HERE //\\//  All Network talks happen through this method here
	@SuppressWarnings("resource")
	public static void nwTalk(){
		if (NetworkSetup.rdbtnServer.isSelected() == true)				// it this device is the Server
		{
			try {
				ServerSocket ss=new ServerSocket(1201);
				{
					Socket s=ss.accept();
					new NWThreads(s).start();							// putting the request on thread
					bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				}
			} catch (Exception e) {	e.printStackTrace();	}
		}
		else															// if this device is the Client
		{
			try {
				Socket s = new Socket (NetworkSetup.serverIP, 1201);
				new NWThreads(s).start();
				bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			} catch (Exception e) {			e.printStackTrace();		}
		}
		
	}
	
	public static void sendMsg(String msg)
	{
		try {
			// making difference between Text-Messages(start with T), Commands(start with C), and Moves(start with M)
			bw.write(msg + "\n");
			bw.flush();
		} catch (IOException e) {	e.printStackTrace();	}
	}
}
