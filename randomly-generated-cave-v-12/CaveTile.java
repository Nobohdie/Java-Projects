public class CaveTile{
	private int x;
	private int y;
	private String tile = Main.BLACK_BACK + "----";
	private String oldType;
	boolean standable = false;
	boolean isInteractable = false;
	String tileType = "wall";
	
	public CaveTile(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setType(String t)
	{
		
		if(t.equals("exit"))
		{
			tile = " () ";
			standable = true;
			tileType = t;
			isInteractable = true;
		}
		else if(t.equals("wall")) 
		{
			tile = Main.BLACK_BACK + "    ";
			standable = false;
			tileType = t;
		}
		else if(t.equals("player"))
		{
			oldType = tile;
			int chance = (int)(Math.random() * 100);
			if(chance <= 1) tile = "\u001B[36m :3 ";
			else tile = "\u001B[36m •› " + Main.RESET;
		}
		else if(t.equals("blank"))
		{
			tile = "    ";
			standable = true;
			tileType = t;
		}
		else if(t.equals("old"))
		{
			tile = oldType;
		}
		else if(t.equals("chest"))
		{
			tile = "\u001B[33m [] " + Main.RESET;
			standable = true;
			tileType = t;
			isInteractable = true;
		}
		else if(t.equals("shop"))
		{
			tile = "\u001B[32m $$ " + Main.RESET;
			standable = true;
			tileType = t;
			isInteractable = true;
		}
	}

	public boolean isInteractable()
	{
		return isInteractable;
	}

	public String getTileType()
	{
		return tileType;
	}

	public String getTile()
	{
		return tile;
	}

	public int getx()
	{
		return x;
	}

	public int gety()
	{
		return y;
	}

	public boolean isStandable()
	{
		return standable;
	}
}