package core;

import java.util.Date;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AccountDialog 
{
	private JTextField accountNameField = new JTextField();
	private JTextField accountTargetField = new JTextField();
	private UtilDateModel model = new UtilDateModel();
	private Properties p = new Properties();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	public AccountDialog()
	{
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	}
	
	public void SetAccountNameField(String accountName)
	{
		accountNameField.setText(accountName);
	}
	
	public void SetAccountTargetField(String target)
	{
		accountTargetField.setText(target);
	}
	
	public void SetAccountTargetDate(String date)
	{
		datePicker.getModel().setDay(Integer.valueOf(date.split("\\.")[0]));
		datePicker.getModel().setMonth(Integer.valueOf(date.split("\\.")[1]));
		datePicker.getModel().setYear(Integer.valueOf(date.split("\\.")[2]));
		datePicker.getModel().setSelected(true);
	}
	
	public String OpenAccountDialog()
	{
		final JComponent[] inputComponents = new JComponent[]
		{
			new JLabel("Kontoname"),
			accountNameField,
			new JLabel("Sparziel"),
			accountTargetField,
			new JLabel("Datum Sparziel"),
			datePicker
		};
		
		int result = JOptionPane.showConfirmDialog(null, inputComponents, "Konto Manager", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION)
		{
			String name = accountNameField.getText();
			if(name.length() <= 0)
			{
				JOptionPane.showConfirmDialog(null, null, "Kein Kontoname eingefügt", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			String target = accountTargetField.getText();
			String date = "";
			if(target.length() <= 0)
			{
				target = "none";
			}
			if(datePicker.getModel().getValue() == null)
			{
				date = "none";
			}
			else 
			{
				date = datePicker.getModel().getDay() + "." + (datePicker.getModel().getMonth() + 1) + "." + datePicker.getModel().getYear();
			}
			String accountData = accountNameField.getText() + ";" + target + ";" + date;
			return accountData;
		}
		return null;
	}
}
