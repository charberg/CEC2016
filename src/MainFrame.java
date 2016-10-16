import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame {
	
	private JPanel stockPanel;
	private JPanel contentPane;
	private JTable stockTable;
	private JCheckBox expiryViewCheck;
	private JComboBox<String> sortByCombo;
	private DefaultTableModel stockTableModel;
	private ArrayList<FoodItem> inventoryNoStock;
	private ArrayList<FoodStock> inventoryStock;
	private MainFrameListener listener;
	
	private static String[] columnHeadersNoExp = new String[]{"Name", "Stock", "Popularity", "Restock Limit"};
	private static String[] columnHeadersExp = new String[]{"Name", "Stock", "Popularity", "Restock Limit", "Batch", "Expiry"};
	
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
		setTitle("Squirtle Squad Leos Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 515);
		
		listener = new MainFrameListener(this);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setActionCommand("openfile");
		mntmOpen.addActionListener(listener);
		mnFile.add(mntmOpen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "name_1040126215182");
		
		stockPanel = new JPanel();
		stockPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Stock", null, stockPanel, null);
		stockPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel sotckSidePanel = new JPanel();
		stockPanel.add(sotckSidePanel, BorderLayout.EAST);
		GridBagLayout gbl_sotckSidePanel = new GridBagLayout();
		gbl_sotckSidePanel.columnWidths = new int[]{73, 0};
		gbl_sotckSidePanel.rowHeights = new int[]{23, 0, 0, 0, 0, 0};
		gbl_sotckSidePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_sotckSidePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sotckSidePanel.setLayout(gbl_sotckSidePanel);
		
		expiryViewCheck = new JCheckBox("Expiry View");
		expiryViewCheck.setActionCommand("expiryview");
		expiryViewCheck.addActionListener(listener);
		GridBagConstraints gbc_expiryViewCheck = new GridBagConstraints();
		gbc_expiryViewCheck.anchor = GridBagConstraints.NORTHWEST;
		gbc_expiryViewCheck.insets = new Insets(0, 0, 5, 0);
		gbc_expiryViewCheck.gridx = 0;
		gbc_expiryViewCheck.gridy = 0;
		sotckSidePanel.add(expiryViewCheck, gbc_expiryViewCheck);
		
		JButton setLimitButton = new JButton("Set Restock Limit");
		setLimitButton.setActionCommand("setrestock");
		setLimitButton.addActionListener(listener);
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
		
		sortByCombo = new JComboBox<String>();
		GridBagConstraints gbc_sortByCombo = new GridBagConstraints();
		gbc_sortByCombo.insets = new Insets(0, 0, 5, 0);
		gbc_sortByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_sortByCombo.gridx = 0;
		gbc_sortByCombo.gridy = 3;
		sotckSidePanel.add(sortByCombo, gbc_sortByCombo);
		
		inventoryStock = SQLiteJDBC.selectFoodStock();
		inventoryNoStock = SQLiteJDBC.selectFoodItem();
		
		stockTableModel = new DefaultTableModel(columnHeadersNoExp, 0);
		stockTable = new JTable(stockTableModel);
		JScrollPane stockScrollPanel = new JScrollPane(stockTable);
		stockPanel.add(stockScrollPanel, BorderLayout.CENTER);
		
		UpdateStock();
		
		JPanel workerPanel = new JPanel();
		workerPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Worker", null, workerPanel, null);
	}
	
	/**
	 * Updates the stock panel to show new table and combo box.
	 */
	public void UpdateStock()
	{
		//Table
		stockTableModel.setColumnCount(0);
		stockTableModel.setRowCount(0);
		String[] columns = columnHeadersExp;
		if(expiryViewCheck.isSelected()) {
			//Activate column
			stockTableModel.setColumnIdentifiers(columnHeadersExp);
			for(int i = 0; i < inventoryStock.size(); i++) {
				stockTableModel.addRow(new String[]{inventoryStock.get(i).getName(), inventoryStock.get(i).getStock().toString(), inventoryStock.get(i).getPopularity().toString(), inventoryStock.get(i).getRestockLimit().toString(), inventoryStock.get(i).getBatchNumber().toString(), inventoryStock.get(i).getExpiryDate().toString()});
			}
		} 
		else {
			//Deactivate column
			stockTableModel.setColumnIdentifiers(columnHeadersNoExp);
			for(int i = 0; i < inventoryNoStock.size(); i++) {
				stockTableModel.addRow(new String[]{inventoryNoStock.get(i).getName(), inventoryNoStock.get(i).getStock().toString(), inventoryNoStock.get(i).getPopularity().toString(), inventoryNoStock.get(i).getRestockLimit().toString()});
			}
			columns = columnHeadersNoExp;
		}

		//Combo Box
		sortByCombo.removeAllItems();
		for(int i = 0; i < columns.length; i++) {
			sortByCombo.addItem(columns[i]);
		}
	}

	public void setRestock(String value)
	{
		int selectedIndex = stockTable.getSelectedRow();
		if(expiryViewCheck.isSelected())
		{
			inventoryStock.get(selectedIndex).setRestockLimit(Integer.parseInt(value));
		}
		else
		{
			inventoryNoStock.get(selectedIndex).setRestockLimit(Integer.parseInt(value));
		}
		UpdateStock();
		
	}
}
