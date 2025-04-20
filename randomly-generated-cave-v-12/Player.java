import java.util.ArrayList;
import java.util.Scanner;
public class Player{

	private int health = 20;
	private ArrayList<String> items = new ArrayList<String>();
	private int power = 5;
	private Scanner sc = new Scanner(System.in);
	public int level = 0;
	private int armor = 4;
	//private int weaponDmg = 1;
	private int numCoins;
	
	public Player()
	{
		items.add("pickaxe");
	}

	public String inventory()
	{
		String inventory = "[";
		int healthPotions = 0;
		int strengthPotions = 0;
		int coins = 0;
		for(String s : items)
			{
				if(s.equals("health potion")) healthPotions ++;
				else if(s.equals("strength potion")) strengthPotions ++;
				else if(s.equals("coin")) coins ++;
				else inventory += s + " ";
			}
		if(healthPotions != 0) inventory += "health potion x" + healthPotions + " ";
		if(strengthPotions != 0) inventory += "strength potion x" + strengthPotions + " ";
		if(coins != 0) inventory += coins + "x coin ";
		numCoins = coins;
		return inventory + "]" + " Health: " + health;
		
	}

	public void addItem(String item)
	{
		items.add(item);
	}

	public void useItem(String item)
	{
		if(item.equals("health potion") && isInArray(items, item))
		{
			items.remove(item);
			health += 5 + (level / 2);
		}
		else if(item.equals("strength potion") && isInArray(items, item))
		{
			items.remove(item);
			power += 5 + (level / 2);
		}
		else if(item.equals("coin") && isInArray(items, item))
		{
			items.remove(item);
		}
		else System.out.println("You cannot use this item");
	}

	public void interact(String type)
	{
		if(type.equals("exit"))
		{
			Main.generateCave();
			level ++;
		}
		else if(type.equals("chest"))
		{
			int treasure = (int)(Math.random() * 10);
			String reward;
			if(treasure == 9 || treasure == 8) reward = "health potion";
			else if(treasure == 7) reward = "strength potion";
			else reward = "coin";
			int randAmt = 1;
			if (reward.equals("coin")) randAmt = (int)(Math.random() * 4 + 1);
			for(int i = 0; i < randAmt; i ++)
				{
					items.add(reward);
				}
			System.out.println("\nYou collected " + randAmt + " " + reward);
			System.out.print("\nPress enter to continue...");
			sc.nextLine();
			Main.removeChest();
		}
		else if(type.equals("shop"))
		{
			System.out.println("Welcome to the store!");
			int randwpn = (int)(Math.random() * 5);
			String[] weapon = {"sword", "dagger", "scythe", "bow", "flintlock"};
			int[] wpnPrice = {5, 7, 9, 12, 15};
			String[] temp = {weapon[randwpn], "health potion", "strength potion", ("+" + (armor / 2) + " to armor (type armor)")};
			ArrayList<String> purchasables = new ArrayList<String>();
			for(String s : temp)
				{
					purchasables.add(s);
				}
			int[] tempPrices = {wpnPrice[randwpn], 3, 3, (1 + armor)};
			ArrayList<Integer> prices = new ArrayList<Integer>();
			for(int s : tempPrices)
				{
					prices.add(s);
				}
			boolean cont = true;
			int choiceInt = 0;
			while(cont)
			{
				System.out.println("Here's what's in stock:");
				for(int i = 0; i < purchasables.size(); i ++)
				{
					System.out.print(purchasables.get(i) + " " + prices.get(i) + " coins, ");
				}
				System.out.print("\nType the name of what you'd like to buy or q to quit: ");
				String choice = sc.nextLine();
				if(isInArray(purchasables, choice)) choiceInt = purchasables.indexOf(choice);
				if(isInArray(purchasables, choice) && numCoins >= prices.get(choiceInt))
				{
					purchasables.remove(choice);
					prices.remove(choiceInt);
					items.add(choice);
					for(int i = 0; i < prices.get(choiceInt); i++)
						{
							numCoins --;
							items.remove("coin");
						}
				}
				else if(choice.equals("q")) cont = false;
				else if(choice.equals("armor") && numCoins >= (1+armor))
				{
					armor += armor / 2;
					purchasables.remove(purchasables.size()-1);
					for(int i = 0; i < (1 + armor); i++)
						{
							numCoins --;
							items.remove("coin");
						}
				}
				Main.clear();
				System.out.println("You purchased 1 " + choice );
			}
			Main.caveTiles.get(Main.playerPos).setType("blank");
		}
	}

	public void encounter()
	{
		String[] enemyTypes  = {"skeleton", "zombie", "goblin", "fomorian", "chimera", "Blob", "slime", "cave dweller"};
		String enemy = enemyTypes[(int)(Math.random() * 7)];
		int enemyHealth = (int)(Math.random() * 20 + 1) + level;
		System.out.println("You encounter a level " + level + " " + enemy);
		while (enemyHealth > 0 && health > 0)
			{
				
				System.out.println("It's your turn. choose an item from your inventory to use");
				System.out.println(inventory());
				String itemChoice = sc.nextLine();
				if(itemChoice.equals("coin") && isInArray(items, itemChoice))
				{
					int rand = (int)(Math.random() * 10 + 1);
					if(rand == 2)
					{
						System.out.println("The coin flys from your hand, straight through the skull of the " + enemy);
						System.out.println("The coin bounces off of the cave walls and returns to your hand.");
						enemyHealth = 0;
					}
					else
					{
						System.out.println("You throw a coin at the enemy, and deal 1 damage.");
						useItem("coin");
						enemyHealth --;
					}
				}
				else if(itemChoice.equals("health potion") && isInArray(items, itemChoice))
				{
					System.out.println("You use a health potion, regaining 5 hp.");
					useItem("health potion");
				}
				else if(itemChoice.equals("strength potion") && isInArray(items, itemChoice))
				{
					System.out.println("You use a strength potion, gaining 5 extra power.");
					useItem("strength potion");
				}
				else if(itemChoice.equals("pickaxe") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your pickaxe, dealing " + (1 + power) + " damage.");
					enemyHealth -= 1 + power;
				}
				else if(itemChoice.equals("sword") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your pickaxe, dealing " + (5 + power) + " damage.");
					enemyHealth -= 5 + power;
				}
				else if(itemChoice.equals("dagger") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your dagger, dealing " + (3 + power) + " damage.");
					enemyHealth -= 3 + power;
				}
				else if(itemChoice.equals("scythe") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your scythe, dealing " + (7 + power) + " damage.");
					enemyHealth -= 7 + power;
				}
				else if(itemChoice.equals("bow") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your bow, dealing " + (9 + power) + " damage.");
					enemyHealth -= 9 + power;
				}
				else if(itemChoice.equals("flintlock") && isInArray(items, itemChoice))
				{
					System.out.println("You strike with your flintlock, dealing " + (12 + power) + " damage.");
					enemyHealth -= 12 + power;
				}
				else System.out.println("You do not have this item");

				if( enemyHealth >= 0 && health >= 0)
				{
					int damage = (int)(Math.random() * 5 + level);
					System.out.println("The " + enemy + " attacks you and deals " + damage + " damage");
					System.out.println("Your armor blocks " + armor + " of it");
					damage = damage - armor;
					if(damage < 0) damage = 0;
					health -= (damage);
				}
				System.out.print("\n\nPress enter to continue...");
				sc.nextLine();
				Main.clear();
			}
		if(health <= 0) 
		{
			System.out.println("YOU DIED!");
			System.out.println(inventory());
			System.out.println("You made it to level " + level);

			System.out.print("\nThanks for Playing! Enter your name to save your score: ");
			String name = sc.nextLine();
			Past newRun = new Past(name, level);
			Save.writeFile(newRun);
			System.exit(0);
			
		}
		else if(enemyHealth <= 0)
		{
			int drop = (int) (Math.random() * 5);
			System.out.println("You killed the " + enemy + " and gained " + drop + " coin");
			for(int i = 0; i < drop; i ++)
				{
					items.add("coin");
					numCoins ++;
				}
			power = 5;
		}
		System.out.print("\n\nPress enter to continue...");
		sc.nextLine();
		Main.clear();
	}

	public static boolean isInArray(ArrayList<String> arr, String s)
	{
		for(String eachString : arr)
			{
				if(eachString.equals(s)) return true;
			}
		return false;
	}

	public int getLevel()
	{
		return level;
	}
}