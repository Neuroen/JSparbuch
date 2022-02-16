package core;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class TransactionDialog 
{
	private JTextField moneyField = new JTextField();
	private JTextField descriptionField = new JTextField();
	private UtilDateModel model = new UtilDateModel();
	private Properties p = new Properties();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public TransactionDialog()
	{
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	}
	
	public void SetMoneyField(String money)
	{
		moneyField.setText(money);
	}
	
	public void SetDescriptionField(String description)
	{
		descriptionField.setText(description);
	}
	
	public void SetTransactionDate(String date)
	{
		datePicker.getModel().setDay(Integer.valueOf(date.split("\\.")[0]));
		datePicker.getModel().setMonth(Integer.valueOf(date.split("\\.")[1]));
		datePicker.getModel().setYear(Integer.valueOf(date.split("\\.")[2]));
		datePicker.getModel().setSelected(true);
	}
	
	public String OpenTransactionDialog()
	{
		final JComponent[] inputComponents = new JComponent[]
		{
			new JLabel("Wert"),
			moneyField,
			new JLabel("Bezeichnung"),
			descriptionField,
			new JLabel("Datum"),
			datePicker
		};
		
		int result = JOptionPane.showConfirmDialog(null, inputComponents, "Konto Manager", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION)
		{
			String money = moneyField.getText();
			if(money.length() <= 0)
			{
				JOptionPane.showConfirmDialog(null, null, "Keine Summe eingefügt", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			String description = descriptionField.getText();
			String date = "";
			if(description.length() <= 0)
			{
				
				return null;
			}
			if(datePicker.getModel().getValue() == null)
			{
				JOptionPane.showConfirmDialog(null, null, "Datum nicht Valide", JOptionPane.ERROR_MESSAGE);
			}
			else 
			{
				date = datePicker.getModel().getDay() + "." + (datePicker.getModel().getMonth() + 1) + "." + datePicker.getModel().getYear();
			}
			String transactionData = date + ";" + description + ";" + money;
			return transactionData;
		}
		return null;
	}
}
