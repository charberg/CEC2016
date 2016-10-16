import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable stockTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 515);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "name_1040126215182");
		
		JPanel stockPanel = new JPanel();
		stockPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Stock", null, stockPanel, null);
		stockPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel sotckSidePanel = new JPanel();
		stockPanel.add(sotckSidePanel, BorderLayout.EAST);
		GridBagLayout gbl_sotckSidePanel = new GridBagLayout();
		gbl_sotckSidePanel.columnWidths = new int[]{73, 0};
		gbl_sotckSidePanel.rowHeights = new int[]{23, 0, 0, 0, 0};
		gbl_sotckSidePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_sotckSidePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sotckSidePanel.setLayout(gbl_sotckSidePanel);
		
		JCheckBox expandedViewCheck = new JCheckBox("Expanded View");
		GridBagConstraints gbc_expandedViewCheck = new GridBagConstraints();
		gbc_expandedViewCheck.anchor = GridBagConstraints.NORTHWEST;
		gbc_expandedViewCheck.insets = new Insets(0, 0, 5, 0);
		gbc_expandedViewCheck.gridx = 0;
		gbc_expandedViewCheck.gridy = 0;
		sotckSidePanel.add(expandedViewCheck, gbc_expandedViewCheck);
		
		JButton setLimitButton = new JButton("Set Limit");
		GridBagConstraints gbc_setLimitButton = new GridBagConstraints();
		gbc_setLimitButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_setLimitButton.insets = new Insets(0, 0, 5, 0);
		gbc_setLimitButton.anchor = GridBagConstraints.NORTH;
		gbc_setLimitButton.gridx = 0;
		gbc_setLimitButton.gridy = 1;
		sotckSidePanel.add(setLimitButton, gbc_setLimitButton);
		
		JLabel sortByLabel = new JLabel("Sort By: ");
		GridBagConstraints gbc_sortByLabel = new GridBagConstraints();
		gbc_sortByLabel.anchor = GridBagConstraints.NORTH;
		gbc_sortByLabel.insets = new Insets(0, 0, 5, 0);
		gbc_sortByLabel.gridx = 0;
		gbc_sortByLabel.gridy = 2;
		sotckSidePanel.add(sortByLabel, gbc_sortByLabel);
		
		JList sortByList = new JList();
		GridBagConstraints gbc_sortByList = new GridBagConstraints();
		gbc_sortByList.anchor = GridBagConstraints.NORTH;
		gbc_sortByList.fill = GridBagConstraints.HORIZONTAL;
		gbc_sortByList.gridx = 0;
		gbc_sortByList.gridy = 3;
		sotckSidePanel.add(sortByList, gbc_sortByList);
		
		stockTable = new JTable();
		stockPanel.add(stockTable, BorderLayout.CENTER);
		
		JPanel workerPanel = new JPanel();
		workerPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Worker", null, workerPanel, null);
	}
}
