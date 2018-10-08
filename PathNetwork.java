package main_pkg;

public class PathNetwork 
{
	private String[] dependencyStrings;
	private PathItem[] dependencies;
	private String name;
	private int duration;
	
	public PathNetwork(String[] dependencyStrings, String name, int duration)
	{
		this.dependencyStrings = dependencyStrings;
		this.name = name;
		this.duration = duration;
		this.dependencies = new PathItem[dependencyStrings.length];
		
	}

}
