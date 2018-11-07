package main_pkg;

import java.util.ArrayList;
import java.util.Iterator;


public class PathNetwork 
{
	public PathItem item;
	public String name;
	public int duration;
	public ArrayList<PathItem> nextItem;
	public ArrayList<PathNetwork> children;
	public int headTime;
	public PathNetwork(PathItem item)
	{
		this.item = item.copy();
		children = new ArrayList<PathNetwork>(0);
		duration = 0;
	}
	
	
	
	
}
