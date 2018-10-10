package main_pkg;

import java.util.ArrayList;
import java.util.Iterator;


public class PathNetwork 
{
	public PathItem pathItem;
	public ArrayList<PathItem> nextItem;
	
	public PathNetwork(PathItem pathItem)
	{
		this.pathItem = pathItem;
		nextItem = new ArrayList<PathItem>();
	}	
}
