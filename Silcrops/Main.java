import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Arrays;
import java.io.*;


//699 lines
public class Main{
	public static final String BLUE = "\u001B[36m";
	public static final String GREEN = "\u001B[32m";
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String B_BACK = "\u001B[46m";
	public static int money = 0;
	private static int numHarvested = 0;
	private static int seeds = 5;
	private static int pricePerCrop = 1;
	public static boolean wateringCan = false;
	public static boolean harvester = false;
	public static boolean planter = false;
	public static boolean purchasedLand = false;
	public static int day = 1;
	public static int minute = 0;
	public static int hour = 12;
	private static int maxWater = 20;
	private static int water = maxWater;
	private static int selected = 0;
	private static Scanner sc = new Scanner(System.in);

	public static ArrayList<Plot> farm = new ArrayList<Plot>();

	

	//print base information
	public static void main(String[] args) 
	{
		clear(); // clear the console at start

		String welcomeMessage = ("Welcome to " + GREEN + " § Silcrops §" + RESET + "! Each bracket [] holds a place for a crop.\nEach symbol represents a different stage of growth: \n[~]  -> empty, needs to be planted\n[°]  -> seedling, needs water\n[»]  -> sprout, needs water\n[§]  -> This is a ripe silcrop, ready to harvest");
		slowPrint(welcomeMessage);
		String infoText = ("\n\nYour selected crop will be in " + RED + "red" + RESET + " or highlighted " + B_BACK + "blue" + RESET + " if watered\n\nAll adjacent crops are in " + GREEN + "green" + RESET + ", and all watered crops are in " + BLUE + "blue" + RESET) + ("\nWatering crops takes from your water supply. Your water supply refreshes every morning.") + ("\n\nThe actions at the bottom tell you what you can do.\nEnter the name of the action you would like to perform or just the first letter.") + ("\n\nWould you like to load a game or start a new one?\n[load] [new]\n");
		slowPrint(infoText);
		
		String choice = sc.nextLine();
		if(choice.length() > 0) choice = choice.substring(0,1);
		if(choice.equals("l"))
		{
			Farmer readFarmer = new Farmer();
			readFarmer = Save.readFile();
			money = readFarmer.getMoney();
			numHarvested = readFarmer.getHarv();
			seeds = readFarmer.getSeeds();
			pricePerCrop = readFarmer.getValue();
			wateringCan = readFarmer.getWatering();
			harvester = readFarmer.getHarvester();
			planter = readFarmer.getPlanter();
			day = readFarmer.getDay();
			maxWater = readFarmer.getMaxWater();
			farm = readFarmer.getFarm();
			Plot.nextPosition += farm.size();
		}
		else
		{
			for(int i = 0; i < 18; i++)
			{
				Plot newPlot = new Plot();
				farm.add(newPlot);
			}
		}

		farm.get(selected).setSelected();

		clear();
		printFarm();
	}

	//prints the farm and prompts user choice
	public static void printFarm()
	{
		clear();
		//print farm information
		System.out.println("Day: " + day + "\n" + String.format("%02d", hour) + ":" + String.format("%02d", minute));
		System.out.println("Current Inventory:\nBalance: $" + money + "	Seeds: " + seeds + "	Harvested Crops: " + numHarvested + "	Water: " + water);
		if(wateringCan) System.out.print("watering can  ");
		if(harvester) System.out.print("harvester  ");
		if(planter) System.out.print("planter  ");
		System.out.println();

		
		//Print the field
		String farmString = "";
		for(Plot eachPlot : farm)
			{
				boolean left = selected - 1 == eachPlot.getPosition() && selected % 6 != 0;
				boolean right = selected + 1 == eachPlot.getPosition() && (selected + 1) % 6 != 0;
				boolean up = selected - 6 == eachPlot.getPosition();
				boolean down = selected + 6 == eachPlot.getPosition();

				if(selected == eachPlot.getPosition() && eachPlot.getIsWatered()) farmString += (B_BACK);
				else if(selected == eachPlot.getPosition()) farmString += (RED);
				else if(eachPlot.getIsWatered()) farmString += (BLUE);
				else if(left || right || up || down) farmString += (GREEN);

				farmString += eachPlot.getPlot();
				farmString += RESET;
				farmString += "  ";

				if((eachPlot.getPosition() + 1) % 6 == 0) farmString += "\n";
			}
		System.out.print(farmString);

		System.out.println("\n[move]  [water]  [harvest]  [sell]  [plant]  [upgrade]  [rest]  [Q to quit]");
		String choice = sc.nextLine().toLowerCase();
		if(choice.length() > 0) choice = choice.substring(0, 1);
		if(choice.equals("m")) move();
		else if (choice.equals("w") && water > 0) water();
		else if (choice.equals("h")) harvest();
		else if (choice.equals("s")) sell();
		else if (choice.equals("p") && seeds > 0) plant();
		else if (choice.equals("u")) upgrade();
		else if (choice.equals("q")) end();
		else if (choice.equals("r"))
		{
			System.out.print("\nHow long would you like to rest (hours)? ");
			int sleeptime = sc.nextInt();
			sleeptime = (sleeptime * 60);
			increaseTime(sleeptime - 20);
			for(int i = 0; i < sleeptime; i++)
			{
				for(int j = 0; j < farm.size(); j++)
				{
					farm.get(j).grow();
				}
			}
		} 

		for(int j = 0; j < farm.size(); j++)
		{
			farm.get(j).grow();
		}
		
		increaseTime(10);
		
		if(purchasedLand)
		{
			purchasedLand = false;
			for(int i = 0; i < 6; i++)
				{
					Plot newPlot = new Plot();
					farm.add(newPlot);
				}
		}
		printFarm();
	}

	//allows upgrades to be purchased
	private static void upgrade()
	{
		System.out.println("Welcome to the store. Here you can spend money to upgrade tools.");
		System.out.println("What would you like to purchase?");
		System.out.println("[watering can ($5)]  [planter ($5)]  [harvester ($5)]  [better seeds ($" + (pricePerCrop * 3) + ")]  [more land ($10)]  [upgrade water ($ " + (maxWater / 10) + ")]");
		String purchaseChoice = sc.nextLine().toLowerCase();
		if(purchaseChoice.length() > 0) purchaseChoice = purchaseChoice.substring(0, 1);
		if (purchaseChoice.equals("w") && !wateringCan && money >= 5)
		{
			wateringCan = true;
			money -= 5;
			System.out.println("You purchased a watering can upgrade, you can now water crops adjacent to the one selected");
		}
		else if (purchaseChoice.equals("p") && !planter && money >= 5)
		{
			planter = true;
			money -= 5;
			System.out.println("You purchased a planter upgrade, you can now plant crops adjacent to the one selected");
		}
		else if (purchaseChoice.equals("h") && !harvester && money >= 5)
		{
			harvester = true;
			money -= 5;
			System.out.println("You purchased a harvester upgrade, you can now harvest crops adjacent to the one selected");
		}
		else if (purchaseChoice.equals("b") && money >= (pricePerCrop * 3))
		{
			money -= (pricePerCrop * 3);
			pricePerCrop += 1;
			System.out.println("You purchased better seeds. Your crops now yeild a higher profit");
		}
		else if (purchaseChoice.equals("m") && money >= 10)
		{
			purchasedLand = true;
			money -= 10;
		}
		else if (money >= (maxWater / 10) && purchaseChoice.equals("u"))
		{
			maxWater += 5;
			money -= (maxWater / 10);
		}
		else System.out.println("You cannot purchase this right now");
		System.out.print("\nPress enter to continue...");
		sc.nextLine();
	}
	
	//changes selected plot
	private static void move()
	{
		System.out.println("[Left] [Right] [Up] [Down]");
		String d = sc.nextLine().toLowerCase();
		if(d.length() > 0) d = d.substring(0, 1);
		if (d.equals("l")) selected --;
		else if (d.equals("r")) selected ++;
		else if (d.equals("u")) selected -= 6;
		else if (d.equals("d")) selected += 6;
		if (selected < 0) selected = 0;
		if (selected > (farm.size() - 1)) selected = (farm.size() - 1);
	}

	//water selected and adjacent plots
	private static void water()
	{
		if(farm.get(selected).getStage() != 0 && water > 0 && !farm.get(selected).getIsWatered()) 
		{
			farm.get(selected).water();
			water -= 1;
		}
		if(wateringCan) 
		{
			if(selected - 1 >= 0 && selected % 6 != 0 && farm.get(selected - 1).getStage() != 0)
			{
				farm.get(selected -1).water();
			}
			if(selected < (farm.size()) && (selected + 1) % 6 != 0 && farm.get(selected + 1).getStage() != 0)
			{
				farm.get(selected + 1).water();
			}
			if(selected - 6 >= 0 && farm.get(selected - 6).getStage() != 0)
			{
				farm.get(selected - 6).water();
			}
			if(selected + 6 < (farm.size()) && farm.get(selected + 6).getStage() != 0)
			{
				farm.get(selected + 6).water();
			}
		}
	}

	//plant in selected and adjacent plots
	private static void plant()
	{
		if(farm.get(selected).getStage() == 0) 
		{
			farm.get(selected).setStage(1);
			seeds -= 1;
		}
		if(planter) 
		{
			if(selected - 1 >= 0 && selected % 6 != 0 && farm.get(selected - 1).getStage() == 0)
			{
				farm.get(selected -1).setStage(1);
			}
			if(selected < (farm.size()) && (selected + 1) % 6 != 0 && farm.get(selected + 1).getStage() == 0)
			{
				farm.get(selected + 1).setStage(1);
			}
			if(selected - 6 >= 0 && farm.get(selected - 6).getStage() == 0)
			{
				farm.get(selected - 6).setStage(1);
			}
			if(selected + 6 < (farm.size()) && farm.get(selected + 6).getStage() == 0)
			{
				farm.get(selected + 6).setStage(1);
			}
		}
	}

	//harvests selected and adjacent plots
	private static void harvest()
	{
		if(farm.get(selected).getStage() == 3) 
		{
			farm.get(selected).setStage(0);
			numHarvested ++;
			seeds += (int)(Math.random() * 2 + 1);
		}
		if(planter) 
		{
			if(selected - 1 >= 0 && selected % 6 != 0 && farm.get(selected - 1).getStage() == 3)
			{
				farm.get(selected -1).setStage(0);
				numHarvested ++;
			}
			if(selected < (farm.size()) && (selected + 1) % 6 != 0 && farm.get(selected + 1).getStage() == 3)
			{
				farm.get(selected + 1).setStage(0);
				numHarvested ++;
			}
			if(selected - 6 >= 0 && farm.get(selected - 6).getStage() == 3)
			{
				farm.get(selected - 6).setStage(0);
				numHarvested ++;
			}
			if(selected + 6 < (farm.size()) && farm.get(selected + 6).getStage() == 3)
			{
				farm.get(selected + 6).setStage(0);
				numHarvested ++;
			}
		}
	}

	private static void sell()
	{
		if(numHarvested > 0)
		{
			System.out.print("How many would you like to sell? ");
			int numToSell = sc.nextInt();
			if(numToSell > numHarvested)
			{
				System.out.println("You cannot sell this much!");
				sell();
			}
			else
			{
				numHarvested -= numToSell;
				money += (numToSell * pricePerCrop);
			}
		}
	}
	
	//increase the clock by a given time
	private static void increaseTime(int time)
	{
		for(int i = 0; i < time; i++)
			{
				minute++;
				if (minute >= 60)
				{
					minute = 0;
					hour++;
				}
				if(hour == 24)
				{
					hour = 0;
					day++;
					water = maxWater;
				}
			}
	}
	
	// a method to clear the replit console
	public static void clear()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	//ends the game
	private static void end()
	{
		System.out.println("Would you like to save your game? (Y/N)");
		String saveQ = sc.nextLine().toLowerCase();
		if(saveQ.length() > 0) saveQ = saveQ.substring(0, 1);
		if(saveQ.equals("y"))
		{
			Farmer saveFarmer = new Farmer(money, numHarvested, seeds, pricePerCrop, wateringCan, harvester, planter, day, maxWater, farm);
			Save.writeFile(saveFarmer);
		}
		System.exit(0);
	}

	//prints a string over time
	public static void slowPrint(String string)
    {
    	for(int i = 0; i < string.length(); i++)
    	{
    		wait(10);
    		System.out.print(string.substring(i, (i+1)));
    	}
    }

	//a wait function
	public static void wait(int time)
    {
        try
        {
            Thread.sleep(time);   
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}