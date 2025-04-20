import java.io.Serializable;
public class Past implements Serializable
{
	private String name = "";
	private int level = 0;
	public Past(String name, int level)
	{
		this.name = name;
		this.level = level;
	}

	public String toString()
	{
		return (name + ": Level " + level);
	}

	public int getLevel()
	{
		return level;
	}

	public String getName()
	{
		return name;
	}
}