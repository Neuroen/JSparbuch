package core;

public class Static_Settings 
{
	private static char currency = '€';
	
	public static void SetCurrency(char newCurrency)
	{
		currency = newCurrency;
	}
	
	public static char GetCurrency()
	{
		return currency;
	}
	
	public static void LoadSettings(String settings)
	{
		//Implementieren wenn mehr Settings vorhanden
	}

}
