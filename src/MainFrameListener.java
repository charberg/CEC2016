import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		}
	}

}
