package main_pkg;

public class PathItem 
{
	private int duration = 0;
	private String name = "";
	public String[] dependencyStrings;
	public PathItem nextItem = null;
	
	public PathItem(int duration, String name, String[] dependencies)
	{
		this.duration = duration;
		this.name = name;
		this.dependencyStrings = dependencies;
	}
	
	public void addPath(PathItem toAdd)
	{
		this.nextItem = toAdd;
	}
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
	
	public String[] getDependencies()
	{
		return this.dependencyStrings;
	}
	
	public int getSize()
	{
		int size = 1;
		PathItem iterater = this.nextItem;
		while(iterater != null)
		{
			size++;
			iterater = iterater.nextItem;
		}
		return size;
	}
	
}
