import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
				if(frame.getStockTable().getSelectedRow() == -1 || frame.getStockSelected())
				{
					break;
				}
				String value = JOptionPane.showInputDialog(frame, "Input the new value:","Restock Limit", JOptionPane.PLAIN_MESSAGE);
				if(value != null && !value.isEmpty())
				{
					frame.setRestock(value);
				}
				break;
			case "exportcsv":
				String content = "";
				if(frame.getStockSelected())
				{
					content = SQLiteJDBC.foodStockToCSV();
				}
				else
				{
					content = SQLiteJDBC.foodItemsToCSV();
				}
				PrintWriter writer;
				try {
					writer = new PrintWriter("food.csv", "UTF-8");
					writer.print(content);
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
		}
	}

}
