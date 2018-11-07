package main_pkg;
import java.util.ArrayList;

public class PathItem 
{
	public int duration = 0;
	private String name = "";
	public ArrayList<String> dependencyStrings;
	@SuppressWarnings("unused")
	private String[] dependencies;
	public PathItem nextItem = null;
	private int length;
	public int headTime;
	
	//constructor for node with dependencies (all other nodes)
	public PathItem(int duration, String name, String[] dependencies)
	{
		this.duration = duration;
		this.name = name;
		this.dependencies = dependencies;
		this.dependencyStrings = new ArrayList<String>(0);
		for(int i = 0; i < dependencies.length; i++)
		{
			this.dependencyStrings.add(dependencies[i]);
		}
		this.length = 0;
	}
	
	//constructor for the copy() function
	public PathItem(int duration, String name, ArrayList<String> dependencies)
	{
		this.duration = duration;
		this.name = name;
		this.dependencies = new String[0];
		this.dependencyStrings = dependencies;
		this.length = 0;
	}
	
	//constructor for node with no dependencies (head node)
	public PathItem(int duration, String name)
	{
		this.duration = duration;
		this.name = name;
		this.dependencyStrings = null;
		this.length = 0;
	}
	
	
	//fuctions 
	
	public void display()
	{
		System.out.print("name: " + this.name+ "\nduration: " + this.duration + "\n");
	}
	

	public String getName()
	{
		return this.name;
	}
	
	
	public int getDuration()
	{
		return this.duration;
	}
	
	
	public ArrayList<String> getDependencies()
	{
		return this.dependencyStrings;
	}
	
	
	public int getLength()
	{
		return this.length;
	}
	
	
	public PathItem copy()
	{
		PathItem temp = new PathItem(this.duration,this.name,this.dependencyStrings);
		temp.nextItem = this.nextItem;
		
		return temp;
	}

	
}
