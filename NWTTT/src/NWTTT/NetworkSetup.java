package NWTTT;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;

public class NetworkSetup extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup nwButtonGroup = new ButtonGroup();
	private JTextField ipField;
	String myIP;
	public static String serverIP;												// Server's IP
	JLabel ipLabel;														// Note at the left of IP Address field
	public static JRadioButton rdbtnServer;
	JRadioButton rdbtnClient;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkSetup frame = new NetworkSetup();
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
	public NetworkSetup() {
		setTitle("Network Setup");
		setResizable(false);
		//\\ Set Icon //\\		----------------------------------------------------------------------//\\
		setIconImage(Toolkit.getDefaultToolkit().getImage("files/icon.png"));
		setDefaultCloseOperation(getDefaultCloseOperation());
		setBounds(100, 100, 300, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSetThisComputer = new JLabel("Set this computer to:");
		lblSetThisComputer.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		rdbtnServer = new JRadioButton("Server");
		rdbtnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					// Selecting Server on Server
				ipField.setEditable(false);
				try {
					myIP = Inet4Address.getLocalHost().getHostAddress();	// Read the IP Address
				} catch (Exception e1) {					e1.printStackTrace();				}
				ipField.setText(myIP);										// Display the IP Address
				ipLabel.setText("Your IP:");
			}
		});
		rdbtnServer.setSelected(true);
		rdbtnServer.setFont(new Font("Calibri", Font.PLAIN, 14));
		nwButtonGroup.add(rdbtnServer);
		
		rdbtnClient = new JRadioButton("Client");
		rdbtnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipField.setEditable(true);
				ipField.setText("");										// Display the IP Address
				ipLabel.setText("Enter Server IP:");
			}
		});
		rdbtnClient.setFont(new Font("Calibri", Font.PLAIN, 14));
		nwButtonGroup.add(rdbtnClient);
		
		ipField = new JTextField();
		ipField.setEditable(false);
		ipField.setFont(new Font("Calibri", Font.PLAIN, 14));
		ipField.setColumns(10);
		try {
			myIP = Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception e1) {					e1.printStackTrace();				}
		ipField.setText(myIP);
		
		ipLabel = new JLabel("Your IP:");
		ipLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameFrame.nwTalk();
				if (rdbtnClient.isSelected()==true)
					GameFrame.opponentFirst.setSelected(true);
				GameFrame.nwSetup.dispose();							// Close the Frame
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GameFrame.chatLog.setVisible(false);					// Disable chat capability
				GameFrame.chatBox.setVisible(false);					// set disable until player choose to play against human
				GameFrame.sendBtn.setVisible(false);					// Disable chat capability
				GameFrame.difLev.setVisible(true);						// Disable chat capability
				GameFrame.difLabel.setVisible(true);					// Disable chat capability
				GameFrame.opponent='C';									// Change opponent to computer
				GameFrame.cpu.setSelected(true);						// Change it back to CPU 
				GameFrame.nwSetup.dispose();							// Close the Frame
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSetThisComputer)
						.addComponent(ipLabel)
						.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(ipField, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(rdbtnServer)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnClient)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSetThisComputer)
						.addComponent(rdbtnClient)
						.addComponent(rdbtnServer))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ipLabel))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
