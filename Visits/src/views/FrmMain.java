package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import utilities.*;
import javax.swing.JSeparator;

public class FrmMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panelAdd;
	private JPanel panelSearch;
	private JLabel lblCompany;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JLabel lblDescription;
	private JLabel lblDate;
	private JButton btnSave;
	private JComboBox<String> cmbCompany;
	private JComboBox<String> cmbSearchCompany;

	public String[] banks = {"NBG","Piraeus","Emporiki","RBS","Ethniki"};
	private JButton btnClear;
	private JDateChooser dateChooser;
	private JLabel lblEmployee;
	private static JTextField txtEmployee;
	private JLabel lblState;
	private JComboBox<String> cmbState;
	private JButton btnExit;
	private JLabel lblHours;
	private JSpinner spnHour;
	private JLabel lblStatus;
	private Color defaultBackColor;
	private JDateChooser dateChooserFrom;
	private JComboBox<String> cmbSearchState;
	private JTextField txtSearchEmployee;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_3;
	private JButton btnSearchExit;
	private JButton btnSearch;
	private JDateChooser dateChooserTo;
	private JButton btnSearchClear;
	private JLabel lblSearchStatus;
	private ResultSetTable rsJT, rsJTStates, rsJTCompanies, rsJTUsers;
	private JScrollPane tableScrollPane, statesScrollPane, companiesScrollPane;
	private JButton btnDelete;
	private String tempText;
	private JPanel panelProperties;
	private JTextField txtStatesNewState;
	private JButton btnRefreshStates;
	private JButton btnAddNewState;
	private JButton btnStatesExit;
	private JPanel panelStates;
	private JPanel panelCompanies;
	private JLabel lblCompanies;
	private JTextField txtCompaniesName;
	private JLabel lblName;
	private JLabel lblAddress;
	private JTextField txtCompaniesAddress;
	private JLabel lblManager;
	private JTextField txtCompaniesManager;
	private JButton btnAddNewCompany;
	private JButton btnRefreshCompanies;
	private JButton btnDeleteCompany;
	private JButton btnDeleteState;
	private JLabel lblPropertiesStatesStatus;
	private JLabel lblPropertiesCompStatus;
	private JTextField txtUsersNewUser;
	private JButton btnAddNewUser;
	private JButton btnRefreshUsers;
	private JButton btnDeleteUser;
	private JScrollPane usersScrollPane;
	private JLabel lblPropertiesUsersStatus;
	static FrmMain frame;
	private JPanel panelUsers;
	private JTextField txtDbName;
	private JButton btnUpdate;
	private JTextPane txtDetailedDescription;
	private JButton btnSearchUpdate;
	private JPanel panelUpdate;
	private JLabel lblUpdateId;
	private JComboBox<String> cmbUpdateCompany;
	private JComboBox<String> cmbUpdateState;
	private JTextField txtUpdateEmployee;
	private JSpinner spnUpdateHours;
	private JDateChooser updateDtChooser;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpMe.refreshProperties();
					
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					
					frame = new FrmMain();
					frame.setResizable(false);
					frame.setVisible(true);

					final FrmLogin loginScreen = new FrmLogin(frame,"Log In", HelpMe.lastUser);
					loginScreen.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					loginScreen.setVisible(true);

					txtEmployee.setText(HelpMe.userName);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmMain.class.getResource("/art/MapIcon.png")));
		initComponents();
		createEvents();
	}

	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 541);
		setTitle("Visits");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 944, 481);
		tabbedPane.setBackground(SystemColor.window);
		contentPane.add(tabbedPane);

		panelAdd = new JPanel();
//		tabbedPane.addTab("New Visit",null, panelAdd, null);
		tabbedPane.addTab("New Visit", new ImageIcon(FrmMain.class.getResource("/art/NewVisitIcon.png")), panelAdd, null);
		panelAdd.setLayout(null);

		lblCompany = new JLabel("Company");
		lblCompany.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCompany.setBounds(49, 11, 55, 19);
		panelAdd.add(lblCompany);
		
		lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDate.setBounds(78, 41, 26, 19);
		panelAdd.add(lblDate);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 101, 589, 331);
		panelAdd.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		lblDescription = new JLabel("Description");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setColumnHeaderView(lblDescription);
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		cmbCompany = new JComboBox<String>();
		cmbCompany.setBounds(114, 11, 145, 20);
		cmbState = new JComboBox<String>();
		cmbState.setBounds(355, 42, 145, 19);
		
		// Connect to database and retrieve all company names
		SqlConnector sqlConn = new SqlConnector(HelpMe.databaseName);
		ResultSet rs;
		sqlConn.connectToDatabase(); // Open the connection
		rs = sqlConn.executeResultSetQuery("Select * from Companies");
		try {
			while(rs.next()){
				cmbCompany.addItem(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rs = sqlConn.executeResultSetQuery("Select * from States");
		try {
			while(rs.next()){
				cmbState.addItem(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		sqlConn.closeConnection(); // Close the connection
		
		panelAdd.add(cmbCompany);
		panelAdd.add(cmbState);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(114, 41, 145, 20);
		dateChooser.setDate(new Date());
		panelAdd.add(dateChooser);
		
		lblEmployee = new JLabel("Employee");
		lblEmployee.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmployee.setBounds(290, 11, 55, 19);
		panelAdd.add(lblEmployee);
		
		txtEmployee = new JTextField();
		txtEmployee.setBounds(355, 11, 145, 19);
		txtEmployee.setEditable(false);
		panelAdd.add(txtEmployee);
		txtEmployee.setColumns(10);
		
		lblState = new JLabel("State");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblState.setBounds(315, 41, 30, 19);
		panelAdd.add(lblState);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 163, 97, 136);
		panelAdd.add(panel);
		panel.setLayout(null);
		
		btnSave = new JButton("Save");
		btnSave.setForeground(SystemColor.controlText);
		btnSave.setBounds(10, 11, 77, 23);
		panel.add(btnSave);
		btnSave.setBackground(SystemColor.activeCaption);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(10, 102, 77, 23);
		panel.add(btnExit);
		btnExit.setForeground(new Color(128, 0, 0));
		btnExit.setBackground(SystemColor.activeCaption);
		
		btnClear = new JButton("Clear");
		btnClear.setForeground(SystemColor.controlText);
		btnClear.setBounds(10, 45, 77, 23);
		panel.add(btnClear);
		btnClear.setBackground(SystemColor.activeCaption);
		
		lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHours.setBounds(71, 71, 33, 19);
		panelAdd.add(lblHours);

		spnHour = new JSpinner();
		spnHour.setBounds(114, 72, 41, 20);
		panelAdd.add(spnHour);
		
		lblStatus = new JLabel("");
		lblStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblStatus.setBounds(165, 72, 335, 18);
		defaultBackColor = lblStatus.getBackground();
		panelAdd.add(lblStatus);
		
		panelSearch = new JPanel();
		tabbedPane.addTab("Search", new ImageIcon(FrmMain.class.getResource("/art/SearchIcon.png")), panelSearch, null);
		panelSearch.setLayout(null);
		
		label = new JLabel("Company");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(49, 11, 55, 19);
		panelSearch.add(label);
		
		cmbSearchCompany = new JComboBox<String>();
		cmbSearchCompany.setBounds(114, 11, 145, 20);
		cmbSearchCompany.addItem("");
		sqlConn.connectToDatabase(); // Open the connection
		rs = sqlConn.executeResultSetQuery("Select * from Companies");
		try {
			while(rs.next()){
				cmbSearchCompany.addItem(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sqlConn.closeConnection(); // Close the connection
		
		panelSearch.add(cmbSearchCompany);
		
		txtSearchEmployee = new JTextField();
		txtSearchEmployee.setColumns(10);
		txtSearchEmployee.setBounds(355, 11, 145, 19);
		panelSearch.add(txtSearchEmployee);
		
		label_1 = new JLabel("Employee");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(290, 11, 55, 19);
		panelSearch.add(label_1);
		
		dateChooserFrom = new JDateChooser();
		dateChooserFrom.setBounds(114, 41, 145, 20);
		dateChooserFrom.setDate(new Date());
		panelSearch.add(dateChooserFrom);
		
		cmbSearchState = new JComboBox<String>();
		cmbSearchState.setBounds(355, 42, 145, 19);
		sqlConn.connectToDatabase(); // Open the connection
		cmbSearchState.addItem("");
		rs = sqlConn.executeResultSetQuery("Select * from States");
		try {
			while(rs.next()){
				cmbSearchState.addItem(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sqlConn.closeConnection(); // Close the connection
		panelSearch.add(cmbSearchState);

		label_3 = new JLabel("State");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(315, 41, 30, 19);
		panelSearch.add(label_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 163, 97, 136);
		panelSearch.add(panel_1);
		
		btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(SystemColor.activeCaption);
		btnSearch.setBounds(10, 11, 77, 23);
		panel_1.add(btnSearch);
		
		btnSearchExit = new JButton("Exit");
		btnSearchExit.setForeground(new Color(128, 0, 0));
		btnSearchExit.setBackground(SystemColor.activeCaption);
		btnSearchExit.setBounds(10, 102, 77, 23);
		panel_1.add(btnSearchExit);
		
		btnSearchClear = new JButton("Clear");
		btnSearchClear.setForeground(Color.BLACK);
		btnSearchClear.setBackground(SystemColor.activeCaption);
		btnSearchClear.setBounds(10, 45, 77, 23);
		panel_1.add(btnSearchClear);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTo.setBounds(89, 73, 15, 19);
		panelSearch.add(lblTo);
		
		dateChooserTo = new JDateChooser();
		dateChooserTo.setBounds(114, 72, 145, 20);
		dateChooserTo.setDate(new Date());
		panelSearch.add(dateChooserTo);
		
		JLabel lblFrom = new JLabel("Date From");
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFrom.setBounds(44, 42, 60, 19);
		panelSearch.add(lblFrom);
		
		lblSearchStatus = new JLabel("");
		lblSearchStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblSearchStatus.setBounds(269, 72, 301, 18);
		panelSearch.add(lblSearchStatus);
		
		panelUpdate = new JPanel();
		panelUpdate.setToolTipText("Update Values");
		panelUpdate.setName("");
		panelUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelUpdate.setBounds(673, 71, 256, 362);
		panelSearch.add(panelUpdate);
		panelUpdate.setLayout(null);
		panelUpdate.setVisible(false);
		
		txtDetailedDescription = new JTextPane();
		txtDetailedDescription.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtDetailedDescription.setBounds(10, 222, 236, 100);
		panelUpdate.add(txtDetailedDescription);
		
		JLabel lblDescription_1 = new JLabel("Description");
		lblDescription_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDescription_1.setBounds(10, 202, 63, 19);
		panelUpdate.add(lblDescription_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 195, 236, 2);
		panelUpdate.add(separator);
		
		JLabel lblCompany_1 = new JLabel("ID");
		lblCompany_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCompany_1.setBounds(10, 15, 63, 19);
		panelUpdate.add(lblCompany_1);
		
		JLabel lblCompany_2 = new JLabel("Company");
		lblCompany_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCompany_2.setBounds(10, 45, 63, 19);
		panelUpdate.add(lblCompany_2);
		
		JLabel lblVisitDate = new JLabel("Visit Date");
		lblVisitDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVisitDate.setBounds(10, 75, 63, 19);
		panelUpdate.add(lblVisitDate);
		
		JLabel lblEmployee_1 = new JLabel("Employee");
		lblEmployee_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmployee_1.setBounds(10, 105, 63, 19);
		panelUpdate.add(lblEmployee_1);
		
		JLabel lblState_1 = new JLabel("State");
		lblState_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblState_1.setBounds(10, 135, 63, 19);
		panelUpdate.add(lblState_1);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDuration.setBounds(10, 165, 63, 19);
		panelUpdate.add(lblDuration);
		
		cmbUpdateState = new JComboBox<String>();
		cmbUpdateState.setBounds(101, 136, 145, 19);
		panelUpdate.add(cmbUpdateState);
		
		lblUpdateId = new JLabel("");
		lblUpdateId.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblUpdateId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUpdateId.setBounds(101, 15, 72, 19);
		panelUpdate.add(lblUpdateId);
		
		cmbUpdateCompany = new JComboBox<String>();
		cmbUpdateCompany.setBounds(101, 45, 145, 19);
		panelUpdate.add(cmbUpdateCompany);
		
		
		SqlConnector sqlUpdateConn = new SqlConnector(HelpMe.databaseName);
		ResultSet rsUpdate;
		sqlUpdateConn.connectToDatabase(); // Open the connection
		rsUpdate = sqlUpdateConn.executeResultSetQuery("Select * from Companies");
		try {
			while(rsUpdate.next()){
				cmbUpdateCompany.addItem(rsUpdate.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rsUpdate = sqlUpdateConn.executeResultSetQuery("Select * from States");
		try {
			while(rsUpdate.next()){
				cmbUpdateState.addItem(rsUpdate.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rsUpdate.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		sqlUpdateConn.closeConnection(); // Close the connection
		
		updateDtChooser = new JDateChooser();
		updateDtChooser.setBounds(101, 74, 145, 20);
		panelUpdate.add(updateDtChooser);
		
		txtUpdateEmployee = new JTextField();
		txtUpdateEmployee.setColumns(10);
		txtUpdateEmployee.setBounds(101, 105, 145, 19);
		panelUpdate.add(txtUpdateEmployee);
		
		spnUpdateHours = new JSpinner();
		spnUpdateHours.setBounds(101, 165, 41, 20);
		panelUpdate.add(spnUpdateHours);
		
		btnSearchUpdate = new JButton("Update");
		btnSearchUpdate.setBounds(10, 328, 78, 23);
		panelUpdate.add(btnSearchUpdate);
		btnSearchUpdate.setForeground(Color.BLACK);
		btnSearchUpdate.setBackground(SystemColor.activeCaption);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(151, 328, 95, 23);
		panelUpdate.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete.setForeground(Color.RED);
		btnDelete.setBackground(Color.RED);
		btnDelete.setVisible(false);
		btnSearchUpdate.setVisible(false);
		
		panelProperties = new JPanel();
//		tabbedPane.addTab("Properties", null, panelProperties, null);
		tabbedPane.addTab("Properties", new ImageIcon(FrmMain.class.getResource("/art/settings-blue-icon.png")), panelProperties, null);
		panelProperties.setLayout(null);
		
		panelStates = new JPanel();
		panelStates.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelStates.setBounds(10, 26, 242, 257);
		panelProperties.add(panelStates);
		panelStates.setLayout(null);
		
		txtStatesNewState = new JTextField();
		txtStatesNewState.setBounds(10, 11, 107, 22);
		panelStates.add(txtStatesNewState);
		txtStatesNewState.setColumns(10);
		
		btnAddNewState = new JButton("Add State");
		btnAddNewState.setBounds(127, 10, 105, 23);
		panelStates.add(btnAddNewState);
		btnAddNewState.setForeground(Color.BLACK);
		btnAddNewState.setBackground(SystemColor.activeCaption);
		
		btnRefreshStates = new JButton("Refresh Table");
		btnRefreshStates.setBounds(10, 41, 105, 23);
		panelStates.add(btnRefreshStates);
		btnRefreshStates.setForeground(Color.BLACK);
		btnRefreshStates.setBackground(SystemColor.activeCaption);
		
		btnDeleteState = new JButton("Delete State");
		btnDeleteState.setForeground(Color.RED);
		btnDeleteState.setBackground(Color.RED);
		btnDeleteState.setBounds(127, 41, 105, 23);
		panelStates.add(btnDeleteState);
		
		lblPropertiesStatesStatus = new JLabel("");
		lblPropertiesStatesStatus.setBounds(0, 241, 242, 16);
		panelStates.add(lblPropertiesStatesStatus);
		lblPropertiesStatesStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblStates = new JLabel("States");
		lblStates.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStates.setBounds(10, 7, 59, 19);
		panelProperties.add(lblStates);
		
		btnStatesExit = new JButton("Exit");
		btnStatesExit.setBounds(10, 401, 77, 23);
		panelProperties.add(btnStatesExit);
		btnStatesExit.setForeground(new Color(128, 0, 0));
		btnStatesExit.setBackground(SystemColor.activeCaption);
		
		panelCompanies = new JPanel();
		panelCompanies.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelCompanies.setBounds(262, 26, 366, 257);
		panelProperties.add(panelCompanies);
		panelCompanies.setLayout(null);
		
		txtCompaniesName = new JTextField();
		txtCompaniesName.setColumns(10);
		txtCompaniesName.setBounds(70, 14, 107, 22);
		panelCompanies.add(txtCompaniesName);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(10, 15, 50, 19);
		panelCompanies.add(lblName);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddress.setBounds(10, 45, 50, 19);
		panelCompanies.add(lblAddress);
		
		txtCompaniesAddress = new JTextField();
		txtCompaniesAddress.setColumns(10);
		txtCompaniesAddress.setBounds(70, 44, 284, 22);
		panelCompanies.add(txtCompaniesAddress);
		
		lblManager = new JLabel("Manager");
		lblManager.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblManager.setBounds(187, 15, 50, 19);
		panelCompanies.add(lblManager);
		
		txtCompaniesManager = new JTextField();
		txtCompaniesManager.setColumns(10);
		txtCompaniesManager.setBounds(247, 14, 107, 22);
		panelCompanies.add(txtCompaniesManager);
		
		btnAddNewCompany = new JButton("Add Company");
		btnAddNewCompany.setForeground(Color.BLACK);
		btnAddNewCompany.setBackground(SystemColor.activeCaption);
		btnAddNewCompany.setBounds(10, 75, 105, 23);
		panelCompanies.add(btnAddNewCompany);
		
		btnRefreshCompanies = new JButton("Refresh Table");
		btnRefreshCompanies.setForeground(Color.BLACK);
		btnRefreshCompanies.setBackground(SystemColor.activeCaption);
		btnRefreshCompanies.setBounds(125, 75, 105, 23);
		panelCompanies.add(btnRefreshCompanies);
		
		btnDeleteCompany = new JButton("Delete Company");
		btnDeleteCompany.setForeground(Color.RED);
		btnDeleteCompany.setBackground(Color.RED);
		btnDeleteCompany.setBounds(240, 75, 114, 23);
		panelCompanies.add(btnDeleteCompany);
		
		lblPropertiesCompStatus = new JLabel("");
		lblPropertiesCompStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPropertiesCompStatus.setBounds(0, 241, 366, 16);
		panelCompanies.add(lblPropertiesCompStatus);
		
		lblCompanies = new JLabel("Companies");
		lblCompanies.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCompanies.setBounds(262, 7, 63, 19);
		panelProperties.add(lblCompanies);
		
		panelUsers = new JPanel();
		panelUsers.setLayout(null);
		panelUsers.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelUsers.setBounds(638, 26, 242, 257);
		panelProperties.add(panelUsers);
		
		txtUsersNewUser = new JTextField();
		txtUsersNewUser.setColumns(10);
		txtUsersNewUser.setBounds(10, 11, 107, 22);
		panelUsers.add(txtUsersNewUser);
		
		btnAddNewUser = new JButton("Add User");

		btnAddNewUser.setForeground(Color.BLACK);
		btnAddNewUser.setBackground(SystemColor.activeCaption);
		btnAddNewUser.setBounds(127, 10, 105, 23);
		panelUsers.add(btnAddNewUser);
		
		btnRefreshUsers = new JButton("Refresh Table");
		btnRefreshUsers.setForeground(Color.BLACK);
		btnRefreshUsers.setBackground(SystemColor.activeCaption);
		btnRefreshUsers.setBounds(10, 41, 105, 23);
		panelUsers.add(btnRefreshUsers);
		
		btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.setForeground(Color.RED);
		btnDeleteUser.setBackground(Color.RED);
		btnDeleteUser.setBounds(127, 41, 105, 23);
		panelUsers.add(btnDeleteUser);
		
		lblPropertiesUsersStatus = new JLabel("");
		lblPropertiesUsersStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPropertiesUsersStatus.setBounds(0, 241, 242, 16);
		panelUsers.add(lblPropertiesUsersStatus);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsers.setBounds(638, 7, 59, 19);
		panelProperties.add(lblUsers);
		
		JPanel panelOther = new JPanel();
		panelOther.setToolTipText("Other Properties");
		panelOther.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelOther.setBounds(262, 285, 366, 44);
		panelProperties.add(panelOther);
		panelOther.setLayout(null);
		
		txtDbName = new JTextField();
		txtDbName.setColumns(10);
		txtDbName.setBounds(110, 11, 151, 22);
		txtDbName.setText(HelpMe.databaseName);
		panelOther.add(txtDbName);
		
		JLabel lblNam = new JLabel("Database Name");
		lblNam.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNam.setBounds(10, 12, 90, 19);
		panelOther.add(lblNam);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(279, 11, 77, 23);
		panelOther.add(btnUpdate);
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setBackground(SystemColor.activeCaption);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(638, 285, 242, 147);
		panelProperties.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblVisitsSolution = new JLabel("Visits Solution");
		lblVisitsSolution.setIcon(new ImageIcon(FrmMain.class.getResource("/art/MapIcon.png")));
		lblVisitsSolution.setForeground(new Color(255, 127, 80));
		lblVisitsSolution.setFont(new Font("Baskerville Old Face", Font.BOLD, 16));
		lblVisitsSolution.setBounds(58, 11, 143, 26);
		panel_2.add(lblVisitsSolution);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthor.setBounds(10, 48, 38, 19);
		panel_2.add(lblAuthor);
		
		JTextPane txtpnNickTsilivisApp = new JTextPane();
		txtpnNickTsilivisApp.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtpnNickTsilivisApp.setBackground(SystemColor.control);
		txtpnNickTsilivisApp.setEditable(false);
		txtpnNickTsilivisApp.setText(" Nick Tsilivis\r\n Software Engineer at Unisystems \r\n nick.tsilivis@gmail.com\t");
		txtpnNickTsilivisApp.setBounds(58, 48, 174, 48);
		panel_2.add(txtpnNickTsilivisApp);
		
		JLabel lblVersion = new JLabel("Version");
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVersion.setBounds(10, 108, 43, 19);
		panel_2.add(lblVersion);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPane_1.setText(" 0.0.1.0 ");
		textPane_1.setEditable(false);
		textPane_1.setBackground(SystemColor.control);
		textPane_1.setBounds(57, 107, 175, 20);
		panel_2.add(textPane_1);
		
		refreshStates();
		refreshCompanies();
		refreshUsers();
		
	}

	public void createEvents() {
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAddComponents();
				}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtEmployee.getText().equals("")){
					lblStatus.setText(" Error : Employee field should not be blank");
					lblStatus.setBackground(Color.red);
				}else{
					if(textPane.getText().equals("")){
						lblStatus.setText(" Error : Description field should not be blank");
						lblStatus.setBackground(Color.red);
					}else{
						SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
						ResultSet rs;
						int currentId = 0, stateId = 0, companyId = 0, updateStatus;
						
						sqlConnector.connectToDatabase(); // Open the connection
						rs = sqlConnector.executeResultSetQuery("Select max(id) from Visits");
						try {
							if(rs.next()){
								currentId = rs.getInt(1);
								if(currentId == 0){
									currentId = 0;
								}
								currentId++;
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						rs = sqlConnector.executeResultSetQuery("Select id from Companies where name = '" + cmbCompany.getSelectedItem() + "'");
						try {
							if(rs.next()){
								companyId = rs.getInt("id");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						rs = sqlConnector.executeResultSetQuery("Select id from States where description = '" + cmbState.getSelectedItem() + "'");
						try {
							if(rs.next()){
								stateId = rs.getInt("id");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String formatedDate = dateFormat.format(dateChooser.getDate());
						// Insert the record into the table
						String sqlStatement = "INSERT INTO Visits VALUES ('" + currentId + "','" + companyId + "','" + formatedDate + "','" +
												txtEmployee.getText() + "','" + textPane.getText() + "','" + stateId + "','" + spnHour.getValue().toString() + "')";
						updateStatus = sqlConnector.executeUpdateQuery(sqlStatement);
						if(updateStatus > 0){
							btnClear.doClick();
							lblStatus.setText(" Update Successful : Visit added with number " + currentId);
							lblStatus.setBackground(Color.green);
						}else{
							lblStatus.setText(" Update Unsuccessful : Visit not added ");
							lblStatus.setBackground(Color.red);
						}
						
						if(sqlConnector.isConnectionOpen()){
							sqlConnector.closeConnection();
						}
					}
				}
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to quit ?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		
		btnSearchExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to quit ?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		
		btnSearchClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSearchComponents();
				}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dateChooserFrom.getDate().compareTo(dateChooserTo.getDate()) > 0){
					lblSearchStatus.setText(" Error : Date 'From' must be after date 'To' ");
					lblSearchStatus.setBackground(Color.red);
				} else {
					
					SqlConnector sqlConnectorSearch = new SqlConnector(HelpMe.databaseName);
					ResultSet rsSearch = null, rsSearchCount = null;
					
					int stateId = 0, companyId = 0;
					String sqlStatement, recordsStatement;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formatedDateFrom = dateFormat.format(dateChooserFrom.getDate());
					String formatedDateTo = dateFormat.format(dateChooserTo.getDate());
					
					sqlStatement = "SELECT * FROM visits WHERE visitdate >= '" + formatedDateFrom + "' and visitdate <= '" + formatedDateTo + "' ";
					recordsStatement = "SELECT count(*) FROM visits WHERE visitdate >= '" + formatedDateFrom + "' and visitdate <= '" + formatedDateTo + "' ";
					
					sqlConnectorSearch.connectToDatabase();
					rsSearch = sqlConnectorSearch.executeResultSetQuery("Select id from Companies where name = '" + cmbSearchCompany.getSelectedItem() + "'");
					try {
						if(rsSearch.next()){
							companyId = rsSearch.getInt("id");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String tempSql = "Select id from States where description = '" + cmbState.getSelectedItem() + "'";
//					rsSearch = sqlConnectorSearch.executeResultSetQuery("Select id from States where description = '" + cmbState.getSelectedItem() + "'");
					rsSearch = sqlConnectorSearch.executeResultSetQuery(tempSql);
					try {
						if(rsSearch.next()){
							stateId = rsSearch.getInt("id");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if(cmbSearchCompany.getItemAt(cmbSearchCompany.getSelectedIndex()) != ""){
						sqlStatement += "AND company = '" + companyId + "' ";
						recordsStatement += "AND company = '" + companyId + "' ";
					}
					
					if(cmbSearchState.getItemAt(cmbSearchState.getSelectedIndex()) != ""){
						sqlStatement += "AND state = '" + stateId + "' ";
						recordsStatement += "AND state = '" + stateId + "' ";
					}
					
					if(!txtSearchEmployee.getText().trim().equals("")){
						sqlStatement += "AND employee LIKE '" + txtSearchEmployee.getText() + "' ";
						recordsStatement += "AND employee LIKE '" + txtSearchEmployee.getText() + "' ";
					}
					
					rsSearchCount = sqlConnectorSearch.executeResultSetQuery(recordsStatement);
					rsSearch = sqlConnectorSearch.executeResultSetQuery(sqlStatement);
					tempText = sqlStatement;
					try {
						if(rsSearchCount.next()){
							lblSearchStatus.setBackground(defaultBackColor);
							lblSearchStatus.setText("Visits found : " + rsSearchCount.getInt(1));
							
							// populate table
							if(tableScrollPane != null){
								panelSearch.remove(tableScrollPane);
							}
							String[] columnNames = {"ID", "Company", "Visit Date", "Employee","Description","State","Visit Duration"};
							try {
								rsJT = new ResultSetTable(rsSearch,columnNames);
								rsJT.populate();
								
							} catch (SQLException e) {
								e.printStackTrace();
							}	
							
							tableScrollPane = new JScrollPane(rsJT);
							tableScrollPane.setBounds(114, 103, 550, 329);
							panelSearch.add(tableScrollPane);
							
							rsJT.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									if (e.getClickCount() == 1) {
										JTable target = (JTable)e.getSource();
									    int row = target.getSelectedRow();
							
										final String ID = rsJT.getValueAt(row, 0).toString();
										SqlConnector connector = new SqlConnector(HelpMe.databaseName);
										
										String sqlStatement = "Select * From Visits Where id = '" + ID + "'";
										connector.connectToDatabase();
										
										ResultSet rs = connector.executeResultSetQuery(sqlStatement);
										
										try {
											if(rs.next()){
												btnSearchUpdate.setVisible(true);
												panelUpdate.setVisible(true);
												
												fillUpdatePanel(rs);
												
												connector.closeConnection();
												
												btnSearchUpdate.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														
														SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
														String formatedDate = dateFormat.format(updateDtChooser.getDate());
														Integer correctCompany = Integer.valueOf(cmbUpdateCompany.getSelectedIndex()) + 1;
														Integer correctState = Integer.valueOf(cmbUpdateState.getSelectedIndex()) + 1;
														
														SqlConnector updateConn = new SqlConnector(HelpMe.databaseName);
														updateConn.connectToDatabase();
														String statement = "Update Visits set company = '" + correctCompany.intValue() + "'"
																+ ", visitdate = '" + formatedDate + "', employee = '" + txtUpdateEmployee.getText() + "', description = '" + txtDetailedDescription.getText() 
																+ "', state = '" + correctState.intValue() + "', duration = '" + spnUpdateHours.getValue() + "'"
																+ " Where id = '" + lblUpdateId.getText() + "'";
														int status = updateConn.executeUpdateQuery(statement);
														if (status > 0) {
															btnSearchUpdate.setBackground(Color.GREEN);
														} else {
															btnSearchUpdate.setBackground(Color.red);
														}
														updateConn.closeConnection();
														
													}
												});
											}
										} catch (SQLException ex) {
											ex.printStackTrace();
										} catch (NumberFormatException ex) {
											ex.printStackTrace();
										}
										
									}
								}
							});
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					try {
						if(rsSearchCount.getInt(1) > 0)
							btnDelete.setVisible(true);
						else
							btnDelete.setVisible(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					sqlConnectorSearch.closeConnection();
				}
			}
		});

		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
				int deletionStatus;
				
				int position = rsJT.getSelectedRow();
				String ID = rsJT.getValueAt(position, 0).toString();
				String sqlStatement = "Delete from Visits where id = '" + ID + "'";
				
				sqlConnector.connectToDatabase();
				deletionStatus = sqlConnector.executeUpdateQuery(sqlStatement);
				if (deletionStatus > 0) {
					JOptionPane.showMessageDialog(rootPane, "Successfully delete record with ID : " + ID,"Succesfull Deletion", 
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(rootPane, "Could not delete the record.","Unsuccesfull Deletion", 
							JOptionPane.ERROR_MESSAGE);
				}
				ResultSet rs = sqlConnector.executeResultSetQuery(tempText);
				// populate table
				if(tableScrollPane != null){
					panelSearch.remove(tableScrollPane);
				}
				String[] columnNames = {"ID", "Company", "Visit Date", "Employee","Description","State","Visit Duration"};
				try {
					rsJT = new ResultSetTable(rs,columnNames);
					rsJT.populate();
				} catch (SQLException e) {
					e.printStackTrace();
				}	
				
				tableScrollPane = new JScrollPane(rsJT);
				tableScrollPane.setBounds(114, 103, 639, 329);
				panelSearch.add(tableScrollPane);
				
				if(sqlConnector.isConnectionOpen())
					sqlConnector.closeConnection();
			}
		});
		
		btnStatesExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to quit ?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					dispose();
				}			}
		});
		
		btnRefreshStates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshStates();
				lblPropertiesStatesStatus.setText("");
				lblPropertiesStatesStatus.setBackground(defaultBackColor);
			}
		});
		
		btnRefreshCompanies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshCompanies();
				lblPropertiesCompStatus.setText("");
				lblPropertiesCompStatus.setBackground(defaultBackColor);
			}
		});
		
		btnRefreshUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshUsers();
				lblPropertiesUsersStatus.setText("");
				lblPropertiesCompStatus.setBackground(defaultBackColor);
			}
		});
		
		btnAddNewState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtStatesNewState.getText().equals("")) {
					lblPropertiesStatesStatus.setText(" Error : State field should not be blank");
					lblPropertiesStatesStatus.setBackground(Color.red);
				} else {
					SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
					ResultSet rs;
					int currentId = 0, updateStatus;
					
					sqlConnector.connectToDatabase(); // Open the connection
					rs = sqlConnector.executeResultSetQuery("Select max(id) from States");
					try {
						if(rs.next()){
							currentId = rs.getInt(1);
							if(currentId == 0){
								currentId = 0;
							}
							currentId++;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					// Insert the record into the table
					String sqlStatement = "INSERT INTO States VALUES ('" + currentId + "','" + txtStatesNewState.getText() + "')";
					updateStatus = sqlConnector.executeUpdateQuery(sqlStatement);
					if (updateStatus > 0){
						txtStatesNewState.setText("");
						refreshStates();
						lblPropertiesStatesStatus.setText(" Update Successful : State added with number " + currentId);
						lblPropertiesStatesStatus.setBackground(Color.green);
					} else {
						lblPropertiesStatesStatus.setText(" Update Unsuccessful : State not added ");
						lblPropertiesStatesStatus.setBackground(Color.red);
					}
					
					if(sqlConnector.isConnectionOpen())
						sqlConnector.closeConnection();
				}
			}
		});
		
		btnAddNewCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtCompaniesName.getText().equals("")) {
					lblPropertiesCompStatus.setText(" Error : Name field should not be blank");
					lblPropertiesCompStatus.setBackground(Color.red);
				} else {
					if (txtCompaniesManager.getText().equals("")) {
						lblPropertiesCompStatus.setText(" Error : Manager field should not be blank");
						lblPropertiesCompStatus.setBackground(Color.red);
					} else {
						if (txtCompaniesAddress.getText().equals("")) {
							lblPropertiesCompStatus.setText(" Error : Address field should not be blank");
							lblPropertiesCompStatus.setBackground(Color.red);
						} else {
							SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
							ResultSet rs;
							int currentId = 0, updateStatus;
							
							sqlConnector.connectToDatabase(); // Open the connection
							rs = sqlConnector.executeResultSetQuery("Select max(id) from Companies");
							try {
								if(rs.next()){
									currentId = rs.getInt(1);
									if(currentId == 0){
										currentId = 0;
									}
									currentId++;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

							// Insert the record into the table
							String sqlStatement = "INSERT INTO Companies VALUES ('" + currentId + "','" + txtCompaniesName.getText() + "','" + txtCompaniesManager.getText() + 
									"','" + txtCompaniesAddress.getText() + "')";
							updateStatus = sqlConnector.executeUpdateQuery(sqlStatement);
							if (updateStatus > 0){
								txtStatesNewState.setText("");
								refreshCompanies();
								lblPropertiesCompStatus.setText(" Update Successful : Company added with number " + currentId);
								lblPropertiesCompStatus.setBackground(Color.green);
							} else {
								lblPropertiesCompStatus.setText(" Update Unsuccessful : Company not added ");
								lblPropertiesCompStatus.setBackground(Color.red);
							}
							
							if(sqlConnector.isConnectionOpen())
								sqlConnector.closeConnection();
						}
					}
				}
			}
		});
		
		btnAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsersNewUser.getText().equals("")) {
					lblPropertiesUsersStatus.setText(" Error : Username field should not be blank");
					lblPropertiesUsersStatus.setBackground(Color.red);
				} else {
					FrmPassword passwordDialog = new FrmPassword(HelpMe.PasswordState.NEW, frame, txtUsersNewUser.getText());
					passwordDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					passwordDialog.setVisible(true);
					
					passwordDialog.addWindowListener(new WindowListener(){
						public void windowClosed(WindowEvent e) {
							if (HelpMe.passwordUpdateStatus == 1) {
								lblPropertiesUsersStatus.setText("Successfully added user : " + txtUsersNewUser.getText());
								lblPropertiesUsersStatus.setBackground(Color.GREEN);
								txtUsersNewUser.setText("");
								refreshUsers();
							}

						}

						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowClosing(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}});
														
				}
			}
		});
		
		btnDeleteState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
				int deletionStatus;
				
				int position = rsJTStates.getSelectedRow();
				String ID = rsJTStates.getValueAt(position, 0).toString();
				String sqlStatement = "Delete from States where id = '" + ID + "'";
				
				sqlConnector.connectToDatabase();
				deletionStatus = sqlConnector.executeUpdateQuery(sqlStatement);
				if (deletionStatus > 0) {
					lblPropertiesStatesStatus.setText("Successfully deleted record with ID : " + ID);
				} else {
					lblPropertiesStatesStatus.setText("Could not delete the record.");
				}
				refreshStates();
				
				if(sqlConnector.isConnectionOpen())
					sqlConnector.closeConnection();
			}
		});
		
		btnDeleteCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete this record ?", "Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
					int deletionStatus;
				
					int position = rsJTCompanies.getSelectedRow();
					String ID = rsJTCompanies.getValueAt(position, 0).toString();
					String sqlStatement = "Delete from Companies where id = '" + ID + "'";
				
					sqlConnector.connectToDatabase();
					deletionStatus = sqlConnector.executeUpdateQuery(sqlStatement);
					if (deletionStatus > 0) {
						lblPropertiesCompStatus.setText("Successfully deleted record with ID : " + ID);
					} else {
						lblPropertiesCompStatus.setText("Could not delete the record.");
					}
					refreshCompanies();
					
					if(sqlConnector.isConnectionOpen())
						sqlConnector.closeConnection();
				}
			}
		});
		
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete this record ?", "Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == JOptionPane.YES_OPTION) {
					SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
					int deletionStatus;
					
					int position = rsJTUsers.getSelectedRow();
					String user = rsJTUsers.getValueAt(position, 0).toString();
					String sqlStatement = "Delete from Users where UserId = '" + user + "'";
				
					sqlConnector.connectToDatabase();
					deletionStatus = sqlConnector.executeUpdateQuery(sqlStatement);
					if (deletionStatus > 0) {
						lblPropertiesUsersStatus.setText("Successfully deleted user : " + user);
					} else {
						lblPropertiesUsersStatus.setText("Could not delete the record.");
					}
					refreshUsers();
					
					if(sqlConnector.isConnectionOpen())
						sqlConnector.closeConnection();
				}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(txtDbName.getText().equals(HelpMe.databaseName))) {
					int answer = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to change the database name? \n(It might cause serious problems)", "Change database name", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (answer == JOptionPane.YES_OPTION) {
						HelpMe.updateProperty("DATABASE_NAME", txtDbName.getText());
					}
					else {
						txtDbName.setText(HelpMe.databaseName);
					}
				}
			}
		});
		

	
	}
	
	public void clearAddComponents(){
		cmbCompany.setSelectedItem(cmbCompany.getItemAt(0));
		cmbState.setSelectedItem(cmbState.getItemAt(0));
		dateChooser.setDate(new Date());
		textPane.setText("");
//		txtEmployee.setText("");
		spnHour.setValue(0);
		lblStatus.setText("");
		lblStatus.setBackground(defaultBackColor);
	}
	
	public void clearSearchComponents(){
		cmbSearchCompany.setSelectedItem(cmbSearchCompany.getItemAt(0));
		cmbSearchState.setSelectedItem(cmbSearchState.getItemAt(0));
		dateChooserFrom.setDate(new Date());
		dateChooserTo.setDate(new Date());
		txtSearchEmployee.setText("");
		lblSearchStatus.setText("");
		lblSearchStatus.setBackground(defaultBackColor);
		btnDelete.setVisible(false);
		panelUpdate.setVisible(false);
		btnSearchUpdate.setVisible(false);
		rsJT.initializeTable();
	}
	
	public void refreshStates() {
		SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
		ResultSet rsStates = null;
		String sqlStatement;
		
		sqlStatement = "Select * From States";
		sqlConnector.connectToDatabase();
		rsStates = sqlConnector.executeResultSetQuery(sqlStatement);
		
		// populate table
		if(statesScrollPane != null){
			panelProperties.remove(statesScrollPane);
		}
		String[] columnNames = {"ID", "Description"};
		try {
			rsJTStates = new ResultSetTable(rsStates,columnNames);
			rsJTStates.populate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		statesScrollPane = new JScrollPane(rsJTStates);
		statesScrollPane.setBounds(10, 75, 222, 159);
		panelStates.add(statesScrollPane);
		
		if(sqlConnector.isConnectionOpen())
			sqlConnector.closeConnection();
	}
	
	public void refreshCompanies() {
		SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
		ResultSet rsCompanies = null;
		String sqlStatement;
		
		sqlStatement = "Select * From Companies";
		sqlConnector.connectToDatabase();
		rsCompanies = sqlConnector.executeResultSetQuery(sqlStatement);
		
		// populate table
		if(companiesScrollPane != null){
			panelProperties.remove(companiesScrollPane);
		}
		String[] columnNames = {"ID", "Name", "Address", "Manager"};
		try {
			rsJTCompanies = new ResultSetTable(rsCompanies,columnNames);
			rsJTCompanies.populate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		companiesScrollPane = new JScrollPane(rsJTCompanies);
		companiesScrollPane.setBounds(10, 109, 346, 126);
		panelCompanies.add(companiesScrollPane);
		
		if(sqlConnector.isConnectionOpen())
			sqlConnector.closeConnection();
	}
	
	public void refreshUsers() {
		SqlConnector sqlConnector = new SqlConnector(HelpMe.databaseName);
		ResultSet rsUsers = null;
		String sqlStatement;
		
		sqlStatement = "Select UserId From Users";
		sqlConnector.connectToDatabase();
		rsUsers = sqlConnector.executeResultSetQuery(sqlStatement);
		
		// populate table
		if(usersScrollPane != null){
			panelProperties.remove(usersScrollPane);
		}
		String[] columnNames = {"ID"};
		try {
			rsJTUsers = new ResultSetTable(rsUsers,columnNames);
			rsJTUsers.populate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lblPropertiesUsersStatus.setBackground(defaultBackColor);
		
		usersScrollPane = new JScrollPane(rsJTUsers);
		usersScrollPane.setBounds(10, 75, 222, 160);
		panelUsers.add(usersScrollPane);
		
		if(sqlConnector.isConnectionOpen())
			sqlConnector.closeConnection();
	}
	
	public void fillUpdatePanel(ResultSet selectedRecord) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date vDate = null;
			try {
				vDate = df.parse(selectedRecord.getString("visitdate"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lblUpdateId.setText(selectedRecord.getString("id"));

			cmbUpdateCompany.setSelectedIndex(selectedRecord.getInt("company") - 1);
			cmbUpdateState.setSelectedIndex(selectedRecord.getInt("state") - 1);
			
			txtUpdateEmployee.setText(selectedRecord.getString("employee"));
			
			updateDtChooser.setDate(vDate);
			
			spnUpdateHours.setValue(selectedRecord.getInt("duration"));

			txtDetailedDescription.setText(selectedRecord.getString("description"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
