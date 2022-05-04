package core;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DataHolder 
{
	private ArrayList<String> accountsList = new ArrayList<>();
	private ArrayList<String> transactionsList = new ArrayList<>();
	
	public void SetAccountsList(String[] accounts)
	{
		if(accounts != null)
		{
			for(int i = 0; i < accounts.length; i++)
			{
				accountsList.add(accounts[i]);
			}
		}
	}
	
	public ArrayList<String> GetAccountsList()
	{
		return accountsList;
	}
	
	public int GetAccountCount()
	{
		return accountsList.size();
	}
	
	public String GetAccountDataFromName(String account)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				return accountsList.get(i);
			}
		}
		return null;
	}
	
	public String GetAccountNameByID(int id)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(Integer.parseInt(accountsList.get(i).split(";")[0]) == id)
			{
				return accountsList.get(i).split(";")[1];
			}
		}
		return null;
	}
	
	public void EditAccount(String oldName, String newValue)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(oldName))
			{
				accountsList.set(i, newValue);
			}
		}
	}
	
	public void InsertNewAccount(String account)
	{
		int id = 0;
		boolean canUseID = false;
		int[] idsInUse = new int[accountsList.size()];
		for(int i = 0; i < accountsList.size(); i++)
		{
			idsInUse[i] = Integer.valueOf(accountsList.get(i).split(";")[0]);
		}
		while(!canUseID)
		{
			boolean collide = false;
			for(int i = 0; i < idsInUse.length; i++)
			{
				if(id == idsInUse[i])
				{
					collide = true;
					break;
				}
			}
			if(!collide)
			{
				account = id + ";" + account;
				accountsList.add(account);
				return;
			}
			id++;
		}
	}
	
	public boolean CheckIfAccountExists(String account)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				return true;
			}
		}
		return false;
	}
	
	public void DeleteAccount(String account)
	{
		int id = GetAccountIDFromName(account);
		DeleteAllTransactionFromID(id);
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				accountsList.remove(i);
			}
		}
	}
	
	public String GetAccountDataAsStringFormattet()
	{
		String userFileContentString = "";
		for(int i = 0; i < accountsList.size(); i++)
		{
			userFileContentString += accountsList.get(i) + "\n";
		}
		return userFileContentString;
	}
	
	public void SetTransactionsList(String[] transactions)
	{
		if(transactions != null)
		{
			for(int i = 0; i < transactions.length; i++)
			{
				transactionsList.add(transactions[i]);
			}
		}
	}
	
	public ArrayList<String> GetTransactionsList()
	{
		return transactionsList;
	}
	
	public void EditTransaction(String oldValue, String newValue)
	{
		for(int i = 0; i < transactionsList.size(); i++)
		{
			if(transactionsList.get(i).equals(oldValue))
			{
				transactionsList.set(i, oldValue.split(";")[0] + ";" + newValue);
				return;
			}
		}
	}
	
	public void DeleteAllTransactionFromID(int id)
	{
		for(int i = 0; i < transactionsList.size(); i++)
		{
			if(id == Integer.valueOf(transactionsList.get(i).split(";")[0]))
			{
				transactionsList.remove(i);
			}
		}
	}
	
	public void DeleteTransactionFromIdentifier(String identifier)
	{
		for(int i = 0; i < transactionsList.size(); i++)
		{
			if(transactionsList.get(i).equals(identifier))
			{
				transactionsList.remove(i);
				return;
			}
		}
	}
	
	public String GetTransactionDataAsStringFormattet()
	{
		String transactionDataString = "";
		for(int i = 0; i < transactionsList.size(); i++)
		{
			transactionDataString += transactionsList.get(i) + "\n";
		}
		return transactionDataString;
	}
	
	public String GetTransactionDataAsStringFormattetForExport()
	{
		String transactionDataString = "";
		for(int i = 0; i < transactionsList.size(); i++)
		{
			transactionDataString += GetAccountNameByID(Integer.parseInt(transactionsList.get(i).split(";")[0]));
			transactionDataString += transactionsList.get(i).split(";")[1]; //Date
			transactionDataString += transactionsList.get(i).split(";")[2]; //Description
			transactionDataString += transactionsList.get(i).split(";")[3]; //Value
		}
		return transactionDataString;
	}
	
	public ArrayList<String> GetTransactionsForAccount(String account)
	{
		int accountID = GetAccountIDFromName(account);
		ArrayList<String> selectedTransactions = new ArrayList<>();
		for(int i = 0; i < transactionsList.size(); i++)
		{
			if(Integer.valueOf(transactionsList.get(i).split(";")[0]) == accountID)
			{
				selectedTransactions.add(transactionsList.get(i));
			}
		}
		return selectedTransactions;
	}
	
	public void AddTransaction(String account, String transaction)
	{
		int id = GetAccountIDFromName(account);
		transaction = id + ";" + transaction;
		transactionsList.add(transaction);
	}
	
	public float GetAccountTarget(String account)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				if(!accountsList.get(i).split(";")[2].equals("none"))
				{
					return Float.valueOf(accountsList.get(i).split(";")[2]);
				}
				break;
			}
		}
		return -1;
	}
	
	public String GetAccountTargetDate(String account)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				return accountsList.get(i).split(";")[3];
			}
		}
		return null;
	}
	
	private int GetAccountIDFromName(String account)
	{
		for(int i = 0; i < accountsList.size(); i++)
		{
			if(accountsList.get(i).split(";")[1].equals(account))
			{
				return Integer.valueOf(accountsList.get(i).split(";")[0]);
			}
		}
		return -1;
	}
}
