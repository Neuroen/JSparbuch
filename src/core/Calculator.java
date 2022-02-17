package core;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	
	public int CalculateDaysToTargetDate(String targetDate)
	{
		try 
		{		
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Date currentDate = sdf.parse(LocalDateTime.now().getDayOfMonth() + "." + (LocalDateTime.now().getMonthValue()) + "." + LocalDateTime.now().getYear());
			Date td = sdf.parse(targetDate);
			long diffMillis = Math.abs(td.getTime() - currentDate.getTime());
			long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
			return (int) diffDays;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return -1;
	}
	
	public int CalculateWeeksFromDays(int days)
	{
		return days / 7;
	}
	
	public int CalculateMonthsFromDays(int days)
	{
		int weeks = CalculateWeeksFromDays(days);
		return weeks / 4;
	}
	
	public float CalculateMoneyPerWeek(int days, float missingMoney)
	{
		int weeks = CalculateWeeksFromDays(days);
		return missingMoney / weeks;
	}
	
	public float CalculateMoneyPerMonth(int days, float missingMoney)
	{
		int months = CalculateMonthsFromDays(days);
		return missingMoney / months;
	}
}
