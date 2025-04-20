//510 lines of code
import java.util.ArrayList;
import java.util.Scanner;
public class Main  // MyProject 
{
	public static ArrayList<CaveTile> caveTiles = new ArrayList<CaveTile>();
	public static final String BLACK_BACK = "\u001B[30m";
	public static final String RESET = "\u001B[0m";
	public static int playerPos = 0;
	public static Scanner sc = new Scanner(System.in);
	public static boolean keepPlaying = true;
	public static String character = " :) ";
	public static Player player = new Player();
	public static int level = -1;
	
	public static void main(String[] args) 
	{
		clear(); // clear the console at start
		System.out.println("Project mining 1.2.0");
		System.out.println("CAVE GEN:\n");
		generateCave();
		printCave();
		while(keepPlaying)
			{
				move();
			}
		System.out.print("\nThanks for Playing! Enter your name to save your score: ");
		String name = sc.nextLine();
		if(name != "") {
		Past newRun = new Past(name, level);
		Save.writeFile(newRun);
		}
	}

	// a method to clear the replit console
	public static void clear()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void move()
	{
		System.out.println("\nINVENTORY:\n" + player.inventory());
		System.out.println("\n\nMove with WASD, e to open inventory, or enter q to quit: (type the letter, then enter)");
		System.out.println("If you are standing on an interactable, press enter to use it");
		String direction = sc.nextLine().toLowerCase();
		int moveAmount = 0;
		int tempPos = 0;
		caveTiles.get(playerPos).setType("old");
		if(direction.equals("w")) moveAmount = -5;
		else if(direction.equals("a")) moveAmount = -1;
		else if(direction.equals("s")) moveAmount = 5;
		else if(direction.equals("d")) moveAmount = 1;
		else if(direction.equals("q")) keepPlaying = false;
		else if(direction.equals("") && caveTiles.get(playerPos).isInteractable()) player.interact(caveTiles.get(playerPos).getTileType());
		else if(direction.equals("e"))
		{
			System.out.println(player.inventory());
			System.out.println("Choose an item to use or enter q to leave.");
			String item = sc.nextLine();
			player.useItem(item);
		}
		if((playerPos + moveAmount) >= 0 && (playerPos + moveAmount) < caveTiles.size()) tempPos = playerPos + moveAmount;
		else tempPos = playerPos;
		CaveTile tempTile = caveTiles.get(tempPos);
		boolean canMove = !(tempTile.gety() != caveTiles.get(playerPos).gety() && (direction.equals("d") || direction.equals("a")));
 		if(tempTile.isStandable() && canMove) playerPos = tempPos;
		clear();
		int chance = (int)(Math.random() * 15 + 1);
		if(caveTiles.get(playerPos).getTileType().equals("blank") && chance == 2 && level > 4) player.encounter();
		printCave();
	}

	public static void generateCave()
	{
		level ++;
		playerPos = 0;
		caveTiles = new ArrayList<CaveTile>();
		int y = 6;
		while (y > 0)
			{
				for(int x = 1; x <= 5; x++)
					{
						CaveTile newTile = new CaveTile(x, y);
						int num = (int)(Math.random() * 20 + 1);
						if(y == 6) newTile.setType("blank");
						else if(caveTiles.size() != 0 && y != 6)
						{
							boolean prevTile = (caveTiles.get(caveTiles.size() - 1).getTile().equals("    ") && x !=1);
							boolean upTile = (caveTiles.get(caveTiles.size() - (6 -1)).getTile().equals("    "));

							boolean adjTile = (prevTile || upTile);
							if(num >= 8 && adjTile) newTile.setType("blank");
						}
						else 
						{
							if(num >= 8) newTile.setType("blank");
						}
						caveTiles.add(newTile);
					}
				y --;
			}
		caveTiles = addInteractables(caveTiles);
	}

	public static ArrayList<CaveTile> addInteractables(ArrayList<CaveTile> cave)
	{
		boolean cont = true;
		boolean treasureMade = false;
		boolean shop = false;
		while (cont)
			{
				for(int i = 1; i < cave.size(); i ++)
					{
						int exit = (int)(Math.random() * 10 + 1);
						if(exit == 1 && cave.get(i).getTileType().equals("blank"))
						{
							cave.get(i).setType("exit");
							cont = false;
							return cave;
						}
						else if(exit == 2 && !treasureMade && cave.get(i).getTileType().equals("blank"))
						{
							treasureMade = true;
							cave.get(i).setType("chest");
						}
						else if(exit == 3 && cave.get(i).getTileType().equals("blank") && !shop)
						{
							shop = true;
							cave.get(i).setType("shop");
						}
					}
			}
		return cave;
	}

	public static void printCave()
	{
		System.out.println("Key:\n( •› ) -> you					( () ) -> exit, interactable\n( [] ) -> chest, interactable	( $$ ) shop, interactable");
		System.out.print("\n");
		System.out.println("Level " + level);
		String cave = "";
		caveTiles.get(playerPos).setType("player");
		for(int i = 0; i <= 5; i ++) cave += (BLACK_BACK + "----" + RESET);
		cave += "\n";
		System.out.println();
		for(CaveTile c : caveTiles)
			{
				if(c.getx() == 1) cave += (BLACK_BACK + "--" + RESET);
				cave += (c.getTile());
				if(c.getx() == 5) cave += (BLACK_BACK + "--" + RESET);
				if(c.getx() == 5) cave += ("\n");
			}
		for(int i = 0; i <= 5; i ++) cave += (BLACK_BACK + "----" + RESET);
		System.out.print(cave);
	}

	public static void removeChest()
	{
		caveTiles.get(playerPos).setType("blank");
	}
}