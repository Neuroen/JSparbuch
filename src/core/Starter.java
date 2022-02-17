package core;

public class Starter 
{
	public static void main(String[] args) 
	{
		if(args.length == 0)
		{
			if(!System.getProperty("os.name").contains("Windows"))
			{
				Swing_View sw = new Swing_View();
				sw.StartGui();
			}
			else 
			{
				Windows_View wv = new Windows_View();
				wv.StartGui();
			}
		}
		else 
		{
			
		}
	}
}
