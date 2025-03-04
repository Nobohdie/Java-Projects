import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Save
{
	public static Scanner sc = new Scanner(System.in);
	public static void writeFile(Farmer p1)
	{
		String fileName = "";
		SaveList saves = getSaveList();
		for(int i = 0; i < 1; i++)
			{
				System.out.print("Enter the file name you want to save this as (Make sure it's unique!): ");
				fileName = sc.nextLine();
				ArrayList<String> saveNames = saves.getSaves();
				for(String eachS : saveNames)
					{
						if(eachS.equals(fileName))
						{
							i--;
							System.out.println("This name is already taken. If you are trying to update your previous save, save this as \"name2\"\n");
						}
					}
			}

		saves.addString(fileName);
		updateSaveList(saves);
		try {
			FileOutputStream f = new FileOutputStream(new File(fileName));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(p1);
			
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} 
		System.out.println("Game saved successfully");
	}

	public static Farmer readFile()
	{
		System.out.print("Enter the file name you wish to load: ");
		String fileName = sc.nextLine();
		Farmer pr3 = new Farmer();
		try{
			FileInputStream fi = new FileInputStream(new File(fileName));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			pr3 = (Farmer) oi.readObject();
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

	public static void updateSaveList(SaveList s)
	{
		try {
			FileOutputStream f = new FileOutputStream(new File("savenames"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(s);
			
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} 
	}

	public static SaveList getSaveList()
	{
		SaveList pr3 = new SaveList();
		try{
			FileInputStream fi = new FileInputStream(new File("savenames"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			pr3 = (SaveList) oi.readObject();
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