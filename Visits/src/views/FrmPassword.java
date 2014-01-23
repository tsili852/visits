package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.EtchedBorder;

import utilities.HelpMe;
import utilities.SqlConnector;

import java.util.Arrays;

public class FrmPassword extends JDialog {
	private static final long serialVersionUID = -2126409026641211216L;
	private JPasswordField txtNewPassword;
	private JLabel lblOldPasswod;
	private JPasswordField txtRetypePassword;
	private JPasswordField txtOldPassword;
	private JLabel lblPasswords;
	private HelpMe.PasswordState state;
	private String userName;

	public FrmPassword(HelpMe.PasswordState state, JFrame parent, String userName) {
	    super(parent, true);
	    if (parent != null) {
	      Dimension parentSize = parent.getSize(); 
	      Point p = parent.getLocation(); 
	      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }
		setType(Type.UTILITY);
		setTitle("Password Management");
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 296, 188);
		getContentPane().setLayout(null);
		createCompontentsAndEvents();
		
		if (state == HelpMe.PasswordState.NEW){
			lblOldPasswod.setVisible(false);
			txtOldPassword.setVisible(false);
		} else {
			lblOldPasswod.setVisible(true);
			txtOldPassword.setVisible(true);
		}
		this.userName = userName;
		this.state = state;
	}
	public FrmPassword() {
		setType(Type.UTILITY);
		setTitle("Password Management");
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 296, 188);
		getContentPane().setLayout(null);
		
		createCompontentsAndEvents();
	}
	
	public void createCompontentsAndEvents() {
		lblOldPasswod = new JLabel("Old Password");
		lblOldPasswod.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOldPasswod.setBounds(10, 11, 85, 20);
		getContentPane().add(lblOldPasswod);
		lblOldPasswod.getBackground();
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setActionCommand("OK");
		btnCancel.setBounds(210, 131, 67, 23);
		getContentPane().add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == HelpMe.PasswordState.NEW) {
					if (txtNewPassword.getPassword().length < 1 || txtRetypePassword.getPassword().length < 1) {
						lblPasswords.setText(" Error : Fill both password fields");
						lblPasswords.setBackground(Color.red);
					} else if (!(Arrays.equals(txtNewPassword.getPassword(), txtRetypePassword.getPassword()))) {
						lblPasswords.setText(" Error : The two password do not match");
						lblPasswords.setBackground(Color.red);
					} else {
						String passwordToString = new String(txtRetypePassword.getPassword());
						SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
						int updateStatus;
					
						sqlConnector.connectToDatabase();
						String sqlStatement = "INSERT INTO Users VALUES ('" + userName + "','" + passwordToString + "')";
						updateStatus = sqlConnector.executeUpdateQuery(sqlStatement);
						if (updateStatus > 0) {
							HelpMe.passwordUpdateStatus = 1;
						} else
							HelpMe.passwordUpdateStatus = 0;
						
						if (sqlConnector.isConnectionOpen()) 
							sqlConnector.closeConnection();
						
						dispose();
					}
				}
			}
		});
		btnOk.setActionCommand("OK");
		btnOk.setBounds(10, 131, 47, 23);
		getContentPane().add(btnOk);
		
		lblPasswords = new JLabel("");
		lblPasswords.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPasswords.setBounds(10, 104, 267, 16);
		getContentPane().add(lblPasswords);
		
		txtNewPassword = new JPasswordField();
		txtNewPassword.setBounds(134, 42, 143, 20);
		getContentPane().add(txtNewPassword);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewPassword.setBounds(10, 42, 85, 20);
		getContentPane().add(lblNewPassword);
		
		JLabel lblRetypePassword = new JLabel("Retype Password");
		lblRetypePassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRetypePassword.setBounds(10, 73, 106, 20);
		getContentPane().add(lblRetypePassword);
		
		txtRetypePassword = new JPasswordField();
		txtRetypePassword.setBounds(134, 73, 143, 20);
		getContentPane().add(txtRetypePassword);
		
		txtOldPassword = new JPasswordField();
		txtOldPassword.setBounds(134, 12, 143, 20);
		getContentPane().add(txtOldPassword);

	}
}// End of Class FrmPassword
