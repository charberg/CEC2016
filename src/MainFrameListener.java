import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MainFrameListener implements ActionListener{
	
	private MainFrame frame;
	
	public MainFrameListener(MainFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String source = arg0.getActionCommand();
		
		switch(source)
		{
			case "expiryview":
				frame.UpdateStock();
				break;
			case "openfoodfile":
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(frame);
				if(result != -1)
				{
					String file = fc.getSelectedFile().getAbsolutePath();
					SQLiteJDBC.bulkInsert(DocParser.parseFoodListDocx(file));
					frame.UpdateStock();
				}
				break;
			case "openemployeefile":
				JFileChooser fc2 = new JFileChooser();
				int result2 = fc2.showOpenDialog(frame);
				if(result2 != -1)
				{
					String file = fc2.getSelectedFile().getAbsolutePath();
					SQLiteJDBC.bulkInsertEmployees(DocParser.parseEmployeeListDocx(file));
					frame.setEmployeeTables();
				}
				break;
			case "setrestock":
				if(frame.getStockTable().getSelectedRow() == -1)
				{
					break;
				}
				String value = JOptionPane.showInputDialog(frame, "Input the new value:","Restock Limit", JOptionPane.PLAIN_MESSAGE);
				if(value != null && !value.isEmpty())
				{
					frame.setRestock(value);
				}
				break;
		}
	}

}
