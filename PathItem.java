package main_pkg;

public class PathItem 
{
	private int duration = 0;
	private String name = "";
	public PathItem nextItem = null;
	public PathItem(int duration, String name)
	{
		this.duration = duration;
		this.name = name;
	}
	
	
	public void addPath(PathItem toAdd)
	{
		this.nextItem = toAdd;
	}
	public void display()
	{
		System.out.print("name: " + this.name+ "\nduration: " + this.duration + "\n");
	}
}
