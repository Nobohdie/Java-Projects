import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Save
{
	public static Scanner sc = new Scanner(System.in);
	public static void writeFile(Past newRun)
	{
		String fileName = "leaderboard";
		ArrayList<Past> scores = readFile();
		int index = 0;
		boolean found = false;

		
		while (!found)
			{
				if(scores.get(index).getLevel() > newRun.getLevel()) index ++;
				else 
				{
					found = true;
				}
			}
		
		
		scores.add(index, newRun);
		System.out.println("\nLEADERBOARD:");
		for(int i = 0; i < scores.size(); i ++)
			{
				System.out.print((i+1) + ". " + scores.get(i).toString());
				System.out.println();
				if (i == 4) i = scores.size();
			}

		try {
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(scores);
			
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} 
		System.out.println("Game saved successfully");
	}

	public static ArrayList<Past> readFile()
	{
		String fileName = "leaderboard";
		ArrayList<Past> pr3 = new ArrayList<Past>();
		try{
			FileInputStream fi = new FileInputStream(new File(fileName));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			pr3 = (ArrayList<Past>) oi.readObject();
			oi.close();
			fi.close();
			return pr3;
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pr3;
	}

}