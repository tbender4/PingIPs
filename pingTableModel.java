
import java.text.*;
import java.util.*;

import javax.swing.table.AbstractTableModel;

public class pingTableModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ping pingTool = new Ping();
	DateFormat df = new SimpleDateFormat ("hh:mm:ss a MM/dd/yy");
	Date dateobj = new Date();
	private String[] columnNames = { "IP Address", "Site Location", "Status", "Last Updated"};
	
	private Object[][] data = { { "192.168.1.1", "555 Sample Street", "", "" } ,
								{ "192.168.1.2", "556 Sample Street", "", "" } };
	
	private Object[][] emptyData = { { "", "", "", "" } };
	
	

	//following three methods are necessary 
	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
    
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    } 
    
    public boolean isCellEditable (int rowIndex, int columnIndex) {
    	if (columnIndex == 2)
    		return false;
    	else
    		return true;
    }
    
    public void deleteData () {
    	data = emptyData;
    }
    
    public void updatePingStatus(int row) {
    	data[row][2] = "Pending";
    	System.out.println(data[row][0]);
    	String status = pingTool.executePing ((String) data[row][0]);
    	System.out.println(status);
    	data[row][2] = status;
    	
    	dateobj = new Date(); //gets latest time
    	data[row][3] = df.format(dateobj.getTime());
    }
    

}
