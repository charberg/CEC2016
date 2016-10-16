import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			case "openfile":
				System.out.println("open file");
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
