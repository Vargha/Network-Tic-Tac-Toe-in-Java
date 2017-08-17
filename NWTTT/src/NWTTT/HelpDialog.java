package NWTTT;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Toolkit;

public class HelpDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HelpDialog dialog = new HelpDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public HelpDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("files/icon.png"));
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JTextArea txtrTictactoeIsA = new JTextArea();
		txtrTictactoeIsA.setFont(new Font("Calibri", Font.PLAIN, 15));
		txtrTictactoeIsA.setWrapStyleWord(true);
		txtrTictactoeIsA.setEditable(false);
		txtrTictactoeIsA.setLineWrap(true);
		txtrTictactoeIsA.setText("Tic-tac-toe is a fun game that you can play, with your friends, or against computer. Tic Tac Toe is a zero-sum game, which means that if both players are playing their best, that neither player will win. However, if you learn how to play tic-tac-toe and master some simple strategies, then you'll be able to not only play, but to win the majority of the time. Specifically, 4x4 and 5x5 grids of this game, leaves a bigger room for players, to try and win each other.\n\nHow to Play:\r\nChoose the grid size: You can change the number of grids from its default 3x3, to 4x4, or 5x5, from the Setting option, on the File tab.\r\nIf you are playing against computer, you and computer take turns after each other, and put your symbols on each block. Similarly, if you are playing through the network, you and your friend will take turns.\r\nNote: You can choose yours and your opponent\u2019s symbol from the right-side settings.\r\nThe player who can put 3 of his/her/its symbols in a horizontal, vertical, or diagonal after each other, will win the game.\r\nOn the right-side of the board you can see the final results of the played games.");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtrTictactoeIsA)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtrTictactoeIsA, GroupLayout.PREFERRED_SIZE, 251, Short.MAX_VALUE)
		);
		contentPanel.setLayout(gl_contentPanel);
	}

}
