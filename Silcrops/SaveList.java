import java.io.Serializable;
import java.util.ArrayList;

public class SaveList implements Serializable{

	private ArrayList<String> saveNames = new ArrayList<String>();
	
	public SaveList(ArrayList<String> s)
	{
		saveNames = s;
	}
	public SaveList(){
	}

	public ArrayList<String> getSaves()
	{
		return saveNames;
	}

	public void addString(String s)
	{
		saveNames.add(s);
	}
}