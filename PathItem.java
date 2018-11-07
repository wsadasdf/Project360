package main_pkg;
import java.util.ArrayList;

public class PathItem 
{
	public int duration = 0;
	private String name = "";
	public ArrayList<String> dependencyStrings;
	private String[] dependencies;
	public PathItem nextItem = null;
	private int length;
	private boolean head = false;
	private int depth;
	public int headTime;
	
	
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
	public PathItem(int duration, String name, ArrayList<String> dependencies)
	{
		this.duration = duration;
		this.name = name;
		this.dependencies = new String[0];
		this.dependencyStrings = dependencies;
		this.length = 0;
	}
	public PathItem(int duration, String name)
	{
		this.duration = duration;
		this.name = name;
		this.dependencyStrings = null;
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
	
	public ArrayList<String> getDependencies()
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
		PathItem temp = new PathItem(this.duration,this.name,this.dependencyStrings);
		temp.nextItem = this.nextItem;
		
		return temp;
	}
	
	
	
	public boolean isHead()
	{
		return head;
	}
	
	public int getDepth()
	{
		this.depth = 1;
		PathItem iter = this;
		while(iter != null)
		{
			depth++;
			iter = iter.nextItem;
		}
		return depth;
	}
}
