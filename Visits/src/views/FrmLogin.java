package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EtchedBorder;

import utilities.HelpMe;
import utilities.SqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FrmLogin extends JDialog {

	private static final long serialVersionUID = 7080169746534853431L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JLabel lblStatus;
	private JButton cancelButton;
	private String lastUser;

	public FrmLogin(JFrame parent, String title, String lUser) {
		    super(parent, title, true);
		    if (parent != null) {
		      Dimension parentSize = parent.getSize(); 
		      Point p = parent.getLocation(); 
		      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		    }
		    lastUser = lUser;
		    setResizable(false);
		    createComponentsAndEvents();
	}
	
	public FrmLogin() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setModal(true);
		createComponentsAndEvents();
	}
	
	private void createComponentsAndEvents(){
		setBounds(100, 100, 245, 153);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name ");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUserName.setBounds(10, 11, 67, 20);
		contentPanel.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(10, 42, 67, 20);
		contentPanel.add(lblPassword);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(87, 12, 143, 20);
		txtUserName.setText(lastUser);
		contentPanel.add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(87, 43, 143, 20);
		contentPanel.add(txtPassword);
		
		lblStatus = new JLabel("");
		lblStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblStatus.setBounds(10, 69, 220, 16);
		contentPanel.add(lblStatus);
		
		okButton = new JButton("OK");
		okButton.setBounds(10, 96, 47, 23);
		contentPanel.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtUserName.getText().length() <= 0) {
					lblStatus.setText(" Error : User ID field should not be blank");
					lblStatus.setBackground(Color.red);
				} else {
					SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
					ResultSet rs;
					sqlConnector.connectToDatabase();
					rs = sqlConnector.executeResultSetQuery("Select Password from Users Where UserId = '" + txtUserName.getText()+ "'");
					try {
						if(rs.next()){
							if (String.valueOf(txtPassword.getPassword()).trim().toUpperCase().equals(rs.getString("Password").trim().toUpperCase())) {
								lblStatus.setText(" Succesfully loged in ");
								lblStatus.setBackground(Color.green);
								if(!(txtUserName.getText().equals(HelpMe.lastUser))) {
									HelpMe.lastUser = txtUserName.getText();
								}
								HelpMe.userName = HelpMe.lastUser;
								dispose();
							} else {
								lblStatus.setText(" Error : Wrong Password ");
								lblStatus.setBackground(Color.red);
								txtPassword.setText("");
							}
						} else {
							lblStatus.setText(" Error : User ID not found ");
							lblStatus.setBackground(Color.red);
						}
					} catch(SQLException e){
						e.printStackTrace();
					}
					if(sqlConnector.isConnectionOpen())
						sqlConnector.closeConnection();
				}
				HelpMe.updateProperty("LAST_USER", txtUserName.getText());
			}
		});

		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to quit ?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		cancelButton.setActionCommand("OK");
		cancelButton.setBounds(163, 96, 67, 23);
		contentPanel.add(cancelButton);
	}
}
