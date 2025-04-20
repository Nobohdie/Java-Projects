import java.io.Serializable;

public class Plot implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int stage;
	private String sign;
	private boolean isWatered;
	private boolean isSelected;
	public static int nextPosition = 0;
	private int position;
	public Plot()
	{
		stage = 0;
		sign = "~";
		isWatered = false;
		isSelected = false;
		position = nextPosition;
		nextPosition ++;
	}

	public String getPlot()
	{
		String crop = "[" + sign + "]";
		return crop;
	}

	public int getPosition()
	{
		return position;
	}

	public boolean getIsSelected()
	{
		return isSelected;
	}

	public void setSelected()
	{
		isSelected = true;
	}

	public boolean getIsWatered()
	{
		return isWatered;
	}

	public void water()
	{
		isWatered = true;
	}

	public void grow()
	{
		if(isWatered)
		{
			int rand = (int)(Math.random() * 30);
			if(rand == 1 || rand == 2)
			{
				stage ++;
				if(stage > 3) stage = 3;
				else if(stage == 2) sign = "»";
				else if(stage == 3) sign = "§";
				isWatered = false;
			}
			else if(rand == 3) isWatered = false;
		}
	}

	public int getStage()
	{
		return stage;
	}

	public void setStage(int stge)
	{
		stage = stge;
		if(stage == 1) sign = "°";
		else if(stage == 2) sign = "»";
		else if(stage == 3) sign = "§";
		else sign = "~";
	}
}