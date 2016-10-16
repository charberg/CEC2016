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
	private DefaultTableModel workerTableModel;
	private DefaultTableModel workerScheduleTableModel;
	private ArrayList<FoodItem> inventoryNoStock;
	private ArrayList<FoodStock> inventoryStock;
	private MainFrameListener listener;
	private ArrayList<Employee> workerList;
	
	private static String[] columnHeadersNoExp = new String[]{"Name", "Stock", "Popularity", "Restock Limit"};
	private static String[] columnHeadersExp = new String[]{"Name", "Stock", "Popularity", "Restock Limit", "Batch", "Expiry"};
	private JTable workerTable;
	private JTable workerScheduleTable;
	
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
	
	public JTable getStockTable()
	{
		return stockTable;
	}
	
	public boolean getStockSelected()
	{
		return expiryViewCheck.isSelected();
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
		
		JMenuItem mntmOpen = new JMenuItem("Open Food File");
		mntmOpen.setActionCommand("openfoodfile");
		mntmOpen.addActionListener(listener);
		mnFile.add(mntmOpen);
		
		JMenuItem mntmOpenEmployeeFile = new JMenuItem("Open Employee File");
		mntmOpenEmployeeFile.setActionCommand("openemployeefile");
		mntmOpenEmployeeFile.addActionListener(listener);
		mnFile.add(mntmOpenEmployeeFile);
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
		//sotckSidePanel.add(sortByLabel, gbc_sortByLabel);
		
		sortByCombo = new JComboBox<String>();
		GridBagConstraints gbc_sortByCombo = new GridBagConstraints();
		gbc_sortByCombo.insets = new Insets(0, 0, 5, 0);
		gbc_sortByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_sortByCombo.gridx = 0;
		gbc_sortByCombo.gridy = 3;
		//sotckSidePanel.add(sortByCombo, gbc_sortByCombo);
		
		inventoryStock = SQLiteJDBC.selectFoodStock();
		inventoryNoStock = SQLiteJDBC.selectFoodItem();
		
		stockTableModel = new DefaultTableModel(columnHeadersNoExp, 0);
		stockTable = new JTable(stockTableModel);
		JScrollPane stockScrollPanel = new JScrollPane(stockTable);
		stockPanel.add(stockScrollPanel, BorderLayout.CENTER);
		
		JPanel workerPanel = new JPanel();
		workerPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Employee", null, workerPanel, null);
		workerPanel.setLayout(new BorderLayout(0, 0));
		
		workerTableModel = new DefaultTableModel(new String[]{"Employee ID", "Name"}, 0);
		workerTable = new JTable(workerTableModel);
		JScrollPane workerScrollPanel = new JScrollPane(workerTable);
		workerPanel.add(workerScrollPanel, BorderLayout.CENTER);
		
		workerScheduleTableModel = new DefaultTableModel(new String[]{"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}, 0);
		JPanel workerSchedulePanel = new JPanel();
		tabbedPane.addTab("Schedule", null, workerSchedulePanel, null);
		workerSchedulePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane workerScheduleScrollPanel = new JScrollPane();
		workerSchedulePanel.add(workerScheduleScrollPanel, BorderLayout.CENTER);	
		
		workerScheduleTable = new JTable(workerScheduleTableModel);
		workerScheduleScrollPanel.setViewportView(workerScheduleTable);
		
		UpdateStock();
		setEmployeeTables();
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
		inventoryStock = SQLiteJDBC.selectFoodStock();
		inventoryNoStock = SQLiteJDBC.selectFoodItem();
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
				if(inventoryNoStock.get(i).getRestockLimit() > inventoryNoStock.get(i).getStock())
				{
					limitWarning(inventoryNoStock.get(i).getName());
				}
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

	public void setEmployeeTables()
	{
		workerList = SQLiteJDBC.selectEmployees();
		
		//Worker list
		for(int i = 0; i < workerList.size(); i++)
		{
			workerTableModel.addRow(new String[]{workerList.get(i).employeeId.toString(), workerList.get(i).name});
		}
		
		//Schedule
		String[][] schedule = ScheduleGenerator.generateSchedule(workerList);
		
		String[] scheduleRow = new String[7];
		scheduleRow[0] = "8:30-9:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][0];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "9:30-10:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][1];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "10:30-11:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][2];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "11:30-12:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][3];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "12:30-1:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][4];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "1:30-2:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][5];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "2:30-3:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][6];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "3:30-4:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][7];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		scheduleRow = new String[7];
		scheduleRow[0] = "4:30-5:30";
		for(int i = 0; i < 6; i++)
		{
			scheduleRow[i + 1] = schedule[i][8];
		}
		workerScheduleTableModel.addRow(scheduleRow);
		
		//TODO: Add overnights
	}
	
	public void setRestock(String value)
	{
		int selectedIndex = stockTable.getSelectedRow();
		if(expiryViewCheck.isSelected())
		{
			inventoryStock.get(selectedIndex).setRestockLimit(Integer.parseInt(value));
			SQLiteJDBC.updateFoodStock(inventoryStock.get(selectedIndex));
		}
		else
		{
			inventoryNoStock.get(selectedIndex).setRestockLimit(Integer.parseInt(value));
			SQLiteJDBC.updateFoodItem(inventoryNoStock.get(selectedIndex));
		}
		UpdateStock();
	}
	
	public void sortStock()
	{
		
	}

	public void limitWarning(String name)
	{
		EmailerSender.sendNotification(name);
		JOptionPane.showMessageDialog(this, name + " is low on stock, consider buying more.");
	}
}
