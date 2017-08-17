package NWTTT;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingFrame extends JFrame {
	private static final long serialVersionUID = 1556159348926215096L;
	private JPanel contentPane;
	private final ButtonGroup gridBtnGrp = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingFrame frame = new SettingFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("files/icon.png"));		// Set icon
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rbtnThree = new JRadioButton("3");
		rbtnThree.setSelected(true);
		gridBtnGrp.add(rbtnThree);
		rbtnThree.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rbtnThree.setBounds(131, 5, 40, 23);
		contentPane.add(rbtnThree);
		
		JRadioButton rbtnFour = new JRadioButton("4");
		gridBtnGrp.add(rbtnFour);
		rbtnFour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rbtnFour.setBounds(131, 31, 40, 23);
		contentPane.add(rbtnFour);
		
		JRadioButton rbtnFive = new JRadioButton("5");
		gridBtnGrp.add(rbtnFive);
		rbtnFive.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rbtnFive.setBounds(131, 57, 40, 23);
		contentPane.add(rbtnFive);
		
		JLabel lblNumberOfGrids = new JLabel("Number of grids:");
		lblNumberOfGrids.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfGrids.setBounds(10, 5, 115, 23);
		contentPane.add(lblNumberOfGrids);
		
		JButton OkBtn = new JButton("OK");
		OkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rbtnThree.isSelected() == true)		GamePanel.gridNum = 3;
				if(rbtnFour.isSelected() == true)		GamePanel.gridNum = 4;
				if(rbtnFive.isSelected() == true)		GamePanel.gridNum = 5;
				
				if (GameFrame.human.isSelected() == true){
					GameFrame.sendMsg("CG" + GamePanel.gridNum);			// Send the gridSize over the network
				}
				GameFrame.settingFrame.dispose();							// Close the Frame
			}
		});
		OkBtn.setBounds(10, 87, 80, 23);
		contentPane.add(OkBtn);
		
		JButton CancelBtn = new JButton("Cancel");
		CancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameFrame.settingFrame.dispose();							// Close the Frame
			}
		});
		CancelBtn.setBounds(103, 87, 77, 23);
		contentPane.add(CancelBtn);
	}
}
