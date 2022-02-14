package core;

import java.util.ArrayList;

public class DataHolder 
{
	private ArrayList<String> accountsList = new ArrayList<>();
	private ArrayList<String> transactionsList = new ArrayList<>();
	
	public void SetAccountsList(String[] accounts)
	{
		for(int i = 0; i < accounts.length; i++)
		{
			accountsList.add(accounts[i]);
		}
	}
	
	public ArrayList<String> GetAccountsList()
	{
		return accountsList;
	}
	
	public void SetTransactionsList(String[] transactions)
	{
		for(int i = 0; i < transactions.length; i++)
		{
			transactionsList.add(transactions[i]);
		}
	}
	
	public ArrayList<String> GetTransactionsList()
	{
		return transactionsList;
	}
	
	public ArrayList<String> GetTransactionsForAccount(int accountID)
	{
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
}
