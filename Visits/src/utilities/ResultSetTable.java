package utilities;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultSetTable extends JTable {
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel dataModel;
	private ResultSet rs;
	private String[] columnNames;
	private String[] rowData;

	public String[] getRowData() {
		return rowData;
	}

	public void setRowData(String[] rowData) {
		this.rowData = rowData;
	}

	public ResultSetTable(ResultSet tempRs) throws SQLException{
		super();
		rs = tempRs;
	}
	
	public ResultSetTable(ResultSet tempRs, String[] tempColumnNames) throws SQLException{
		super();
		rs = tempRs;
		columnNames = tempColumnNames;
	}
	
	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
	public void populate() throws SQLException {
		dataModel = new DefaultTableModel();
//		dataModel = new DefaultTableModel();

		setModel(dataModel);
		
		try {
			ResultSetMetaData mdata = rs.getMetaData();
			int colCount = mdata.getColumnCount();
			dataModel.setColumnIdentifiers(columnNames);
			// Populate with data
			while(rs.next()) {
				rowData = new String[colCount];
				for (int i=1; i<=colCount; i++) {
					rowData[i-1] = rs.getString(i);
				}
				dataModel.addRow(rowData);
			}
		}
		finally {
			try {
				rs.close();
			}
			catch(SQLException ignore){}
		}
		
	}
	
	public void initializeTable() {
		dataModel.setRowCount(0);
		try {
			populate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
} // End of Class declaration
