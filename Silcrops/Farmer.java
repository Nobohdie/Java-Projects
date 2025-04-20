import java.io.Serializable;
import java.util.ArrayList;
 
public class Farmer implements Serializable
{
	//private final long serialVersionUID = 1L;
	public int money;
	private int numHarvested;
	private int seeds;
	private int pricePerCrop;
	public boolean wateringCan;
	public boolean harvester;
	public boolean planter;
	public int day;
	private int maxWater;
	private ArrayList<Plot> farm = new ArrayList<Plot>();
	
	public Farmer(int m, int harv, int s, int val, boolean w, boolean h, boolean p, int d, int mw, ArrayList<Plot> frm)
	{
		money = m;
		numHarvested = harv;
		seeds = s;
		pricePerCrop = val;
		wateringCan = w;
		planter = p;
		harvester = h;
		day = d;
		maxWater = mw;
		farm = frm;
	}

	public Farmer()
	{};

	public int getMoney()
	{
		return money;
	}

	public int getHarv()
	{
		return numHarvested;
	}

	public int getSeeds()
	{
		return seeds;
	}

	public int getValue()
	{
		return pricePerCrop;
	}

	public boolean getWatering()
	{
		return wateringCan;
	}

	public boolean getHarvester()
	{
		return harvester;
	}

	public boolean getPlanter()
	{
		return planter;
	}

	public int getDay()
	{
		return day;
	}

	public int getMaxWater()
	{
		return maxWater;
	}

	public ArrayList<Plot> getFarm()
	{
		return farm;
	}
}