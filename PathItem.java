package main_pkg;

public class PathItem 
{
	private int duration = 0;
	private String name = "";
	public String[] dependencyStrings;
	public PathItem nextItem = null;
	private int length;
	private boolean head = false;
	
	public boolean mark = false;
	
	public PathItem(int duration, String name, String[] dependencies, boolean mark)
	{
		this.duration = duration;
		this.name = name;
		this.dependencyStrings = dependencies;
		this.mark = mark;
		this.length = 0;
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
	
	public int calcLength()
	{
		this.length = this.duration;
		PathItem iterater = this.nextItem;
		while(iterater != null)
		{
			this.length += iterater.getLength();
		}
		return this.length;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public PathItem copy()
	{
		PathItem temp = new PathItem(this.duration,this.name,this.dependencyStrings,this.mark);
		temp.nextItem = null;
		return temp;
	}
	
	public boolean isHead()
	{
		return head;
	}
}
