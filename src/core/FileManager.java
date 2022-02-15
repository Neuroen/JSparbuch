package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager 
{
	private String homePath;
	private String userFile;
	private String dataFile;
	
	public FileManager()
	{
		homePath = System.getProperty("user.home");
		homePath += "/Morton_Software/Sparbuch/";
		File dirCheck = new File(homePath);
		if(!dirCheck.exists())
		{
			dirCheck.mkdirs();
		}
		userFile = homePath + "user.mlf";
		dataFile = homePath + "data.mlf";
	}
	
	public String GetUserFile()
	{
		return userFile;
	}
	
	public String GetDataFile()
	{
		return dataFile;
	}
	
	public String[] ReadFile(String filepath)
	{
		try 
		{
			File readFile = new File(filepath);
			FileReader freader = new FileReader(readFile);
			BufferedReader breader = new BufferedReader(freader);
			String data = "";
			while(breader.ready())
			{
				data += breader.readLine() + "\n";
			}
			breader.close();
			freader.close();
			return data.split("\n");
		} 
		catch (Exception e) 
		{
			return null;
		}
		
	}
	
	public boolean WriteFile(String filepath, String content)
	{
		try 
		{
			File writeFile = new File(filepath);
			FileWriter fwriter = new FileWriter(writeFile);
			fwriter.write(content);
			fwriter.close();
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
		
	}

}
