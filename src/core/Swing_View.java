package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Swing_View 
{
	private FileManager fm;
	private DataHolder dh;
	private Calculator calc;
	
	private JFrame frame;
	private JTable transactionTable;
	private SpringLayout layout;
	private JList accountList;
	private DefaultTableModel tableModel;
	
	//Labels
	private JLabel totalBalanceLabel;
	private JLabel totalTimeToTargetDateLabel;
	
	//Calculation Module
	private JTabbedPane targetCalculateBox;
	private JPanel savingForTargetMenu;
	private JPanel savingForecastMenu;
	private JTabbedPane calculatorTabs;
	private JPanel weekSavingPanel;
	private JPanel monthSavingPanel;
	private JPanel quarterSavingPanel;
	private JPanel yearSavingPanel;
	private JLabel weekSavingDescriptionLabel;
	private JLabel monthSavingDescriptionLabel;
	private JLabel quarterSavingDescriptionLabel;
	private JLabel yearSavingDescriptionLabel;
	//Calculation Module - Forecast Panel
	private JLabel timeUnitSelectionLabel;
	private JComboBox<String> timeUnitSelectorBox;
	private JTextField timeInputField;
	private JCheckBox forecastUseTransactionMiddleValueCheckBox;
	private JLabel moneyValueInputLabel;
	private JTextField moneyValueForForecasField;
	private JLabel forecastResultLabel;
	private SpringLayout forecastBoxLayout;
	//Calculation Module Vars
	private int oldTimeUnitSelection = 0;
	
	private JButton addTransactionButton;
	//Menu Bar
	private JMenuBar menuBar;
	//General Menu
	private JMenu generalMenu;
	private JMenuItem aboutItem;
	private JMenuItem settingsItem;
	private JMenuItem exItem;
	//Account Menu
	private JMenu accountMenu;
	private JMenuItem createNewAccountItem;
	private JMenuItem editAccountItem;
	private JMenuItem deleteAccountItem;
	private JMenuItem importAccountListItem;
	private JMenuItem exportAccountListItem;
	//Transaction Menu
	private JMenu transactionMenu;
	private JMenuItem editTransactionItem;
	private JMenuItem deleteTransactionItem;
	
	private Object[] tableColumnNames = 
	{
		"Datum",
		"Bezeichnung",
		"Wert"
	};
	
	public Swing_View() 
	{
		fm = new FileManager();
		dh = new DataHolder();
		calc = new Calculator();
		dh.SetAccountsList(fm.ReadFile(fm.GetUserFile()));
		dh.SetTransactionsList(fm.ReadFile(fm.GetDataFile()));
		initialize();
	}
	
	public void StartGui() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Swing_View window = new Swing_View();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initialize() 
	{
		frame = new JFrame("Sparbuch");
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		layout = new SpringLayout(); 
		
		//Accounts List
		accountList = new JList<String>();
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountList.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, accountList, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, accountList, 300, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, accountList, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, accountList, 500, SpringLayout.SOUTH, frame);
		
		//Transaction Table
		transactionTable = new JTable();
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, transactionTable, 305, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, transactionTable, 700, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, transactionTable, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, transactionTable, 500, SpringLayout.SOUTH, frame);
		
		//Account Calc Box
		targetCalculateBox = new JTabbedPane();
		layout.putConstraint(SpringLayout.WEST, targetCalculateBox, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, targetCalculateBox, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, targetCalculateBox, 300, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, targetCalculateBox, 450, SpringLayout.SOUTH, frame);
		
		savingForTargetMenu = new JPanel();
		savingForTargetMenu.setLayout(new GridLayout());
		
		savingForecastMenu = new JPanel();
		forecastBoxLayout = new SpringLayout();
		savingForecastMenu.setLayout(forecastBoxLayout);
		
		//Forecast Menu Init
		timeUnitSelectionLabel = new JLabel("Zeit auswählen:");
		timeUnitSelectorBox = new JComboBox<String>();
		timeInputField = new JTextField();
		forecastUseTransactionMiddleValueCheckBox = new JCheckBox("Einzahlungsmittelwert benutzen");
		moneyValueInputLabel = new JLabel("Einzahlung eingeben:");
		moneyValueForForecasField = new JTextField();
		forecastResultLabel = new JLabel("Test");
		savingForecastMenu.add(timeUnitSelectionLabel);
		savingForecastMenu.add(timeUnitSelectorBox);
		savingForecastMenu.add(timeInputField);
		savingForecastMenu.add(forecastUseTransactionMiddleValueCheckBox);
		savingForecastMenu.add(moneyValueInputLabel);
		savingForecastMenu.add(moneyValueForForecasField);
		savingForecastMenu.add(forecastResultLabel);
		
		timeUnitSelectorBox.addItem("Tag(e)");
		timeUnitSelectorBox.addItem("Woche(n)");
		timeUnitSelectorBox.addItem("Monat(e)");
		timeUnitSelectorBox.addItem("Quartal(e)");
		timeUnitSelectorBox.addItem("Jahr(e)");
		
		//Set Positions
		forecastBoxLayout.putConstraint(SpringLayout.EAST, timeUnitSelectorBox, -5, SpringLayout.EAST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, timeUnitSelectorBox, 5, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.EAST, timeInputField, -110, SpringLayout.EAST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.WEST, timeInputField, 130, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, timeInputField, 7, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.WEST, timeUnitSelectionLabel, 5, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, timeUnitSelectionLabel, 10, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.WEST, forecastUseTransactionMiddleValueCheckBox, 5, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, forecastUseTransactionMiddleValueCheckBox, 30, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.WEST, moneyValueInputLabel, 5, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, moneyValueInputLabel, 62, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.EAST, moneyValueForForecasField, -5, SpringLayout.EAST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.WEST, moneyValueForForecasField, 170, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, moneyValueForForecasField, 60, SpringLayout.NORTH, savingForecastMenu);
		
		forecastBoxLayout.putConstraint(SpringLayout.WEST, forecastResultLabel, 5, SpringLayout.WEST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.EAST, forecastResultLabel, -5, SpringLayout.EAST, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.NORTH, forecastResultLabel, 100, SpringLayout.NORTH, savingForecastMenu);
		forecastBoxLayout.putConstraint(SpringLayout.SOUTH, forecastResultLabel, 5, SpringLayout.SOUTH, savingForecastMenu);
		
		calculatorTabs = new JTabbedPane();
		calculatorTabs.setEnabled(false);
		
		weekSavingPanel = new JPanel();
		weekSavingPanel.setLayout(new GridLayout());
		
		monthSavingPanel = new JPanel();
		monthSavingPanel.setLayout(new GridLayout());
		
		quarterSavingPanel = new JPanel();
		quarterSavingPanel.setLayout(new GridLayout());
		
		yearSavingPanel = new JPanel();
		yearSavingPanel.setLayout(new GridLayout());
		
		weekSavingDescriptionLabel = new JLabel("");
		monthSavingDescriptionLabel = new JLabel("");
		quarterSavingDescriptionLabel = new JLabel("");
		yearSavingDescriptionLabel = new JLabel("");
		
		weekSavingPanel.add(weekSavingDescriptionLabel);
		monthSavingPanel.add(monthSavingDescriptionLabel);
		quarterSavingPanel.add(quarterSavingDescriptionLabel);
		yearSavingPanel.add(yearSavingDescriptionLabel);
		
		calculatorTabs.addTab("Woche", weekSavingPanel);
		calculatorTabs.addTab("Monat", monthSavingPanel);
		calculatorTabs.addTab("Quartal", quarterSavingPanel);
		calculatorTabs.addTab("Jahr", yearSavingPanel);
		
		savingForTargetMenu.add(calculatorTabs);
		
		targetCalculateBox.addTab("Sparhilfe", savingForTargetMenu);
		targetCalculateBox.addTab("Spar Prognose", savingForecastMenu);
		
		targetCalculateBox.setEnabledAt(0, false);
		targetCalculateBox.setEnabledAt(1, false);
		
		//Add Transaction Button
		addTransactionButton = new JButton("Geld Buchen");
		layout.putConstraint(SpringLayout.WEST, addTransactionButton, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, addTransactionButton, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, addTransactionButton, 490, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, addTransactionButton, 500, SpringLayout.SOUTH, frame);
		
		//Labels
		totalBalanceLabel = new JLabel("0" + Static_Settings.GetCurrency());
		Font balanceFont = new Font("arial", 0, 32);
		totalBalanceLabel.setFont(balanceFont);
		layout.putConstraint(SpringLayout.WEST, totalBalanceLabel, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, totalBalanceLabel, 1000, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, totalBalanceLabel, 30, SpringLayout.NORTH, frame);
		
		totalTimeToTargetDateLabel = new JLabel("");
		layout.putConstraint(SpringLayout.WEST, totalTimeToTargetDateLabel, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, totalTimeToTargetDateLabel, 1000, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, totalTimeToTargetDateLabel, 270, SpringLayout.NORTH, frame);
		
		//Menu Bar, Menus und Items
		menuBar = new JMenuBar();
		//General Menu
		generalMenu = new JMenu("Allgemein");
		aboutItem = new JMenuItem("Über Sparbuch");
		settingsItem = new JMenuItem("Einstellungen");
		exItem = new JMenuItem("Beenden");
		
		generalMenu.add(aboutItem);
		generalMenu.add(settingsItem);
		generalMenu.add(new JSeparator());
		generalMenu.add(exItem);
		
		//Account Menu
		accountMenu = new JMenu("Konten");
		createNewAccountItem = new JMenuItem("Neues Konto erstellen");
		editAccountItem = new JMenuItem("Konto Bearbeiten");
		deleteAccountItem = new JMenuItem("Konto Löschen");
		importAccountListItem = new JMenuItem("Kontenliste Importieren");
		exportAccountListItem = new JMenuItem("Kontenliste Exportieren");
		
		accountMenu.add(createNewAccountItem);
		accountMenu.add(editAccountItem);
		accountMenu.add(deleteAccountItem);
		accountMenu.add(new JSeparator());
		accountMenu.add(importAccountListItem);
		accountMenu.add(exportAccountListItem);
		editAccountItem.setEnabled(false);
		deleteAccountItem.setEnabled(false);
		
		//Transaction Menu
		transactionMenu = new JMenu("Transaktionen");
		editTransactionItem = new JMenuItem("Transaktion Bearbeiten");
		deleteTransactionItem = new JMenuItem("Transaktion Löschen");
		
		transactionMenu.add(editTransactionItem);
		transactionMenu.add(deleteTransactionItem);
		editTransactionItem.setEnabled(false);
		deleteTransactionItem.setEnabled(false);
		
		//menuBar.add(generalMenu); Einbauen wenn Fertig
		menuBar.add(accountMenu);
		menuBar.add(transactionMenu);
		
		frame.setLayout(layout);
		frame.add(accountList);
		frame.add(transactionTable);
		frame.add(targetCalculateBox);
		frame.add(addTransactionButton);
		frame.add(totalBalanceLabel);
		frame.add(totalTimeToTargetDateLabel);
		frame.add(menuBar);
		
		//Events
		accountList.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent event)
			{
				if(accountList.getSelectedIndex() != -1)
				{
					UpdateBalanceText();
					UpdateCalculatorBoxForecast();
					targetCalculateBox.setEnabledAt(1, true);
					editAccountItem.setEnabled(true);
					deleteAccountItem.setEnabled(true);
					if(transactionTable.getRowCount() > 0)
					{
						forecastUseTransactionMiddleValueCheckBox.setEnabled(true);
					}
					else 
					{
						forecastUseTransactionMiddleValueCheckBox.setEnabled(false);
					}
				}
			}
		});
		
		transactionTable.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent event)
			{
				if(transactionTable.getSelectedRow() > 0)
				{
					editTransactionItem.setEnabled(true);
					deleteTransactionItem.setEnabled(true);
				}
				else 
				{
					editTransactionItem.setEnabled(false);
					deleteTransactionItem.setEnabled(false);
				}
			}
		});
		
		createNewAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AccountDialog ad = new AccountDialog();
				String dataString = ad.OpenAccountDialog();
				if(dataString == null)
				{
					return;
				}
				dh.InsertNewAccount(dataString);
				UpdateAccountsList();				
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
			}
		});
		
		editAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AccountDialog ad = new AccountDialog();
				String accountData = dh.GetAccountDataFromName(accountList.getSelectedValue().toString());
				ad.SetAccountNameField(accountData.split(";")[1]);
				if(!accountData.split(";")[2].equals("none"))
				{
					ad.SetAccountTargetField(accountData.split(";")[2]);
				}
				if(!accountData.split(";")[3].equals("none"))
				{
					ad.SetAccountTargetDate(accountData.split(";")[3]);
				}
				accountData = accountData.split(";")[0] + ";" + ad.OpenAccountDialog();
				dh.EditAccount(accountList.getSelectedValue().toString(), accountData);
				UpdateAccountsList();
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
			}
		});
		
		deleteAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(accountList.getSelectedIndex() == -1)
				{
					return;
				}
				dh.DeleteAccount(accountList.getSelectedValue().toString());
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
				editAccountItem.setEnabled(false);
				deleteAccountItem.setEnabled(false);
				UpdateAccountsList();
				UpdateBalanceText();
			}
		});
		
		importAccountListItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser jfc = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Morton List File", "mlf");
				jfc.setFileFilter(fileFilter);
				jfc.setDialogType(JFileChooser.SAVE_DIALOG);
				jfc.setDialogTitle("Kontenliste Importieren");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showDialog(frame, "Importieren");
				if(result == JFileChooser.APPROVE_OPTION)
				{
					String[] data = fm.ReadFile(jfc.getSelectedFile().getAbsolutePath());
					for(int i = 0; i < data.length; i++)
					{
						String currentAccount = data[i].substring(data[i].indexOf(";") + 1);
						if(!dh.CheckIfAccountExists(currentAccount))
						{
							dh.InsertNewAccount(currentAccount);
						}
					}
					fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
					UpdateAccountsList();
				}
			}
		});
		
		exportAccountListItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser jfc = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Morton List File", "mlf");
				jfc.setFileFilter(fileFilter);
				jfc.setDialogType(JFileChooser.SAVE_DIALOG);
				jfc.setDialogTitle("Kontenliste Exportieren");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showDialog(frame, "Exportieren");
				if(result == JFileChooser.APPROVE_OPTION)
				{
					fm.WriteFile(jfc.getSelectedFile().getAbsolutePath() + ".mlf", dh.GetAccountDataAsStringFormattet());
				}
			}
		});
		
		addTransactionButton.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				TransactionDialog td = new TransactionDialog();
				String transactionData = td.OpenTransactionDialog();
				if(transactionData == null)
				{
					return;
				}
				dh.AddTransaction(accountList.getSelectedValue().toString(), transactionData);
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
			}
		});
		
		editTransactionItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				TransactionDialog td = new TransactionDialog();
				String transactionData = dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()).get(transactionTable.getSelectedRow() - 1);
				td.SetTransactionDate(transactionData.split(";")[1]);
				td.SetDescriptionField(transactionData.split(";")[2]);
				td.SetMoneyField(transactionData.split(";")[3]);
				String oldTransactionData = transactionData;
				transactionData = td.OpenTransactionDialog();
				if(transactionData == null)
				{
					return;
				}
				System.out.println(transactionData);
				dh.EditTransaction(oldTransactionData, transactionData);
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
			}
		});
		
		deleteTransactionItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int tableID = transactionTable.getSelectedRow();
				//TODO Warnungs Message ausgeben.
				dh.DeleteTransactionFromIdentifier(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()).get(tableID - 1));
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
			}
		});
		
		//Calculator Module Events
		
		forecastUseTransactionMiddleValueCheckBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(forecastUseTransactionMiddleValueCheckBox.isSelected())
				{
					moneyValueForForecasField.setEnabled(false);
				}
				else
				{
					moneyValueForForecasField.setEnabled(true);
				}
				UpdateCalculatorBoxForecast();
			}
		});
		
		timeInputField.getDocument().addDocumentListener(new DocumentListener() 
		{	
			public void removeUpdate(DocumentEvent e) {}
			
			public void insertUpdate(DocumentEvent e) 
			{
				UpdateCalculatorBoxForecast();
			}
			
			public void changedUpdate(DocumentEvent e) {}
		});
		
		moneyValueForForecasField.getDocument().addDocumentListener(new DocumentListener() 
		{
			public void removeUpdate(DocumentEvent e) {}
			
			public void insertUpdate(DocumentEvent e) 
			{
				UpdateCalculatorBoxForecast();
			}
			
			public void changedUpdate(DocumentEvent e) {}
		});
		
		timeUnitSelectorBox.addActionListener(new ActionListener() 
		{	
			public void actionPerformed(ActionEvent e) 
			{
				
				if(oldTimeUnitSelection != timeUnitSelectorBox.getSelectedIndex())
				{
					switch (oldTimeUnitSelection) 
					{
						case 0: //Days
							timeInputField.setText("1");
							break;
						case 1: //Weeks
							if(timeUnitSelectorBox.getSelectedIndex() == 0)
							{
								timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 7 + "");
							}
							else 
							{
								timeInputField.setText("1");
							}
							break;
						case 2: //Months
							switch (timeUnitSelectorBox.getSelectedIndex()) 
							{
								case 0:
									timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 31 + "");
									break;
								case 1:
									timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 4 + "");
									break;
								default:
									timeInputField.setText("1");
									break;
							}
							break;
						case 3: //Quarters
							switch (timeUnitSelectorBox.getSelectedIndex()) 
							{
								case 0:
									timeInputField.setText((Integer.parseInt(timeInputField.getText()) * 3) * 31 + "");
									break;
								case 1:
									timeInputField.setText((Integer.parseInt(timeInputField.getText()) * 3) * 4 + "");
									break;
								case 2:
									timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 3 + "");
									break;
								default:
									timeInputField.setText("1");
									break;
							}
							break;
						case 4: //Years
							switch (timeUnitSelectorBox.getSelectedIndex()) 
							{
								case 0:
									timeInputField.setText((Integer.parseInt(timeInputField.getText()) * 365) + "");
									break;
								case 1:
									timeInputField.setText((Integer.parseInt(timeInputField.getText()) * 52) + "");
									break;
								case 2:
									timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 12 + "");
									break;
								case 3:
									timeInputField.setText(Integer.parseInt(timeInputField.getText()) * 4 + "");
									break;
								default:
									timeInputField.setText("1");
									break;
							}
							break;
	
						default:
							break;
					}
					oldTimeUnitSelection = timeUnitSelectorBox.getSelectedIndex();
				}
				UpdateCalculatorBoxForecast();
			}
		});
		
		UpdateAccountsList();
		UpdateBalanceText();
		UpdateCalculatorBoxForecast();
	}
	
	private void UpdateTransactionTable(ArrayList<String> transactions) 
	{
		tableModel = new DefaultTableModel(0, tableColumnNames.length)
		{
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};
		tableModel.addRow(tableColumnNames);
		for(int i = 0; i < transactions.size(); i++)
		{
			String currentTransaction = transactions.get(i).substring(transactions.get(i).indexOf(";") + 1);
			tableModel.addRow(currentTransaction.split(";"));
		}
		transactionTable.setModel(tableModel);
	}
	
	private void UpdateBalanceText()
	{
		if(accountList.getSelectedIndex() == -1)
		{
			totalBalanceLabel.setText("Konto auswählen");
			weekSavingDescriptionLabel.setText("");
			monthSavingDescriptionLabel.setText("");
			totalTimeToTargetDateLabel.setText("");
			addTransactionButton.setEnabled(false);
			return;
		}
		else 
		{
			addTransactionButton.setEnabled(true);
		}
		ArrayList<String> transactions = dh.GetTransactionsForAccount(accountList.getSelectedValue().toString());
		UpdateTransactionTable(transactions);
		float target = dh.GetAccountTarget(accountList.getSelectedValue().toString());
		float balance = calc.CalculateTotalBalance(transactions);
		float difference = 0.0f;
		if(target == -1)
		{
			targetCalculateBox.setEnabledAt(0, false);
			targetCalculateBox.setSelectedIndex(1);
			totalBalanceLabel.setText("<html><body>" + balance + Static_Settings.GetCurrency() + "<br><br>Kein Ziel Gesetzt</body></html>");
		}
		else 
		{
			targetCalculateBox.setEnabledAt(0, true);
			difference = target - balance;
			totalBalanceLabel.setText("<html><body>" + balance + Static_Settings.GetCurrency() + " /<br>" + target + Static_Settings.GetCurrency() + "<br><br>noch " + difference + Static_Settings.GetCurrency() + " bis zum Ziel</body></html>");
		}
		
		//Prüfe ob Target Date vorhanden wenn ja aktiviere das Berechnungsmodul
		String date = dh.GetAccountTargetDate(accountList.getSelectedValue().toString());
		if(date.equals("none"))
		{
			totalTimeToTargetDateLabel.setText("Kein Zieldatum angegeben.");
			weekSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			monthSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			quarterSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			yearSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			calculatorTabs.setEnabled(false);
		}
		else 
		{
			int days = calc.CalculateDaysToTargetDate(date);
			CheckTabAvailbillity(days);
			totalTimeToTargetDateLabel.setText("<html><body>Zieldatum: " + date + "<br>" + days + " Tage verbleibend</body></html>");
			calculatorTabs.setEnabled(true);
			if(calc.CalculateWeeksFromDays(days) <= 0)
			{
				days = 7;
			}
			weekSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateWeeksFromDays(days) + " Wochen verbleibend. Sparen Sie pro Woche " + String.format("%.02f", calc.CalculateMoneyPerWeek(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			monthSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateMonthsFromDays(days) + " Monat(e) verbleibend. Sparen Sie pro Monat " + String.format("%.02f", calc.CalculateMoneyPerMonth(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			quarterSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateQuartersFromDays(days) + " Quartal(e) verbleibend. Sparen Sie pro Quartal " + String.format("%.02f", calc.CalculateMoneyPerQuarter(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			yearSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateYearsFromDays(days) + " Quartal(e) verbleibend. Sparen Sie pro Quartal " + String.format("%.02f", calc.CalculateMoneyPerYear(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
		}
	}
	
	private void CheckTabAvailbillity(int days)
	{
		//Check Months
		if(calc.CalculateMonthsFromDays(days) <= 0)
		{
			calculatorTabs.setEnabledAt(1, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(1, true);
		}
		//Check Quarter
		if(calc.CalculateMonthsFromDays(days) < 3)
		{
			calculatorTabs.setEnabledAt(2, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(2, true);
		}
		//Check Year
		if(calc.CalculateMonthsFromDays(days) < 12)
		{
			calculatorTabs.setEnabledAt(3, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(3, true);
		}
	}
	
	private void UpdateAccountsList()
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		ArrayList<String> accounts = dh.GetAccountsList();
		for(int i = 0; i < accounts.size(); i++)
		{
			model.addElement(accounts.get(i).split(";")[1]);
		}
		accountList.setModel(model);
		if(accounts.size() > 0)
		{
			exportAccountListItem.setEnabled(true);
		}
		else 
		{
			exportAccountListItem.setEnabled(false);
		}
	}
	
	private void UpdateCalculatorBoxForecast()
	{
		if(timeInputField.getText().length() <= 0)
		{
			forecastResultLabel.setText("Für Prognose Daten eingeben.");
			return;
		}
		
		if(!forecastUseTransactionMiddleValueCheckBox.isSelected())
		{
			if(moneyValueForForecasField.getText().length() <= 0)
			{
				forecastResultLabel.setText("Für Prognose Daten eingeben.");
				return;
			}
			float moneyToSave = Float.parseFloat(moneyValueForForecasField.getText());
			int time = Integer.parseInt(timeInputField.getText());
			float result = calc.CalculateForecast(time, moneyToSave);
			forecastResultLabel.setText("<html><body>Wenn Sie jede(n) " + timeUnitSelectorBox.getSelectedItem() + " " + moneyToSave + 
					Static_Settings.GetCurrency() + " Für " + time + " " + timeUnitSelectorBox.getSelectedItem() + " Sparen haben Sie nach ablauf " + result + Static_Settings.GetCurrency() 
					+ " Erspart");
		}
		else 
		{
			float moneyToSave = calc.CalculateMiddleValueFromTransactions(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
			int time = Integer.parseInt(timeInputField.getText());
			float result = calc.CalculateForecast(time, moneyToSave);
			forecastResultLabel.setText("<html><body>Wenn Sie jede(n) " + timeUnitSelectorBox.getSelectedItem() + " " + String.format("%.02f", moneyToSave) + 
					Static_Settings.GetCurrency() + " Für " + time + " " + timeUnitSelectorBox.getSelectedItem() + " Sparen haben Sie nach ablauf " + String.format("%.02f", result) + Static_Settings.GetCurrency() 
					+ " Erspart");
		}
	}
}
