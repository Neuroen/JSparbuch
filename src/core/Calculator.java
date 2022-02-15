package core;

import java.util.ArrayList;

public class Calculator 
{
	public float CalculateTotalBalance(ArrayList<String> transactions)
	{
		float balance = 0.0f;
		for(int i = 0; i < transactions.size(); i++)
		{
			balance += Float.valueOf(transactions.get(i).split(";")[3]);
		}
		return balance;
	}
}
