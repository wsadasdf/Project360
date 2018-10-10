package main_pkg;
import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;

public class PathAnalizer extends Frame
implements ActionListener, WindowListener
{
	/**
	 main class holds the window and the fields to be entered
	 */
	//variable block

	private static final long serialVersionUID = 7942054704709588561L;
	private TextField name,durationField,dependencyField;
	private Button enterButton, finishButton, aboutButton, helpButton, restartButton, quitButton;	//getButton calculates the required items
	private int duration, longest;
	private String[] dependencies;
	private String itemName = "";
	private PathItem pathItem = null, iterater = null, dispIterater = null, testItem = null;
	private boolean mark;
	private PathItem testing;
	private PathNetwork network;
	private Vector<PathItem> paths = new Vector<PathItem>(1);
	private ArrayList<PathItem> events = new ArrayList<PathItem>();
	
	public PathAnalizer()
	{
		duration = 0;
		setLayout(new FlowLayout());
		add(new Label("Path Name "));
		name = new TextField(null,10);
		name.setEditable(true);
		add(name);
		add(new Label("Path Duration "));
		durationField = new TextField("",10);
		add(durationField);
		add(new Label("dependancies "));
		dependencyField = new TextField(null,10);
		add(dependencyField);
		
		/*
		add(new Label("Path Dependencies "));
		durationField = new TextField("",10);
		add(dependencyField);
		*/

		enterButton = new Button("enter");
		add(enterButton);
		finishButton = new Button("finish");
		add (finishButton);
		

		//shan test

		aboutButton = new Button("About");
		add(aboutButton);
		helpButton = new Button("Help");
		add(helpButton);
		restartButton = new Button("Restart");
		add(restartButton);
		quitButton = new Button("Quit");
		add(quitButton);

		//shan test end

		enterButton.addActionListener(this);
		aboutButton.addActionListener(this);
		helpButton.addActionListener(this);
		quitButton.addActionListener(this);
		finishButton.addActionListener(this);
		addWindowListener(this);
		setTitle("Path Analizer");
		setSize(250,250);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new PathAnalizer();
	}

	public void windowClosing(WindowEvent event)
	{
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("enter"))
		{
			duration = Integer.parseInt(durationField.getText());
			itemName = name.getText();
			String temp = dependencyField.getText();
			if(events.isEmpty())
			{
				if(temp.equals(""))
				{
					dependencies = null;
					events.add(new PathItem(duration,itemName));
				}
				else
				{
					dependencies = temp.split(",");
					events.add(new PathItem(duration,itemName,dependencies));
				}
			}
			else
			{
				if(temp.equals(""))
				{
					dependencies = null;
					events.add(new PathItem(duration,itemName));
				}
				else
				{
					dependencies = temp.split(",");
					events.add(new PathItem(duration,itemName,dependencies));
				}
			}
		}
		//	}
			
			
			
			
			
			
			
			
			
			
			
			
			//String test = dependencyField.getText();
			//String test2 = name.getText();
			//Object test3 = Integer.parseInt(durationField.getText());
			/*
			if (test.contains(test2) && pathItem.getSize() > 1)
			{
				JFrame dependencyFrame = new JFrame("Dependency Error");
				dependencyFrame.setVisible(true);
				dependencyFrame.setSize(500,500);
				JLabel dependencyLabel = new JLabel("Network node cannot depend on itself!");
				JPanel dependencyPanel = new JPanel();
				dependencyFrame.add(dependencyPanel);
				dependencyFrame.add(dependencyLabel);
			}
			
			
			else if ((pathItem == null) && pathItem.getSize() > 1)
			{
				JFrame identicalFrame = new JFrame("Identical Name Error");
				identicalFrame.setVisible(true);
				identicalFrame.setSize(500,500);
				JLabel identicalLabel = new JLabel("Identical name function detected!");
				JPanel identicalPanel = new JPanel();
				identicalFrame.add(identicalPanel);
				identicalFrame.add(identicalLabel);
			}*/
			
			else if (duration < 0)
			{
				JFrame intErrorFrame = new JFrame("Integer Error");
				intErrorFrame.setVisible(true);
				intErrorFrame.setSize(500,500);
				JLabel intErrorLabel = new JLabel("Integers cannot be less than 0!");
				JPanel intErrorPanel = new JPanel();
				intErrorFrame.add(intErrorPanel);
				intErrorFrame.add(intErrorLabel);
			}
			
			/*else if (pathItem == null && dependencies != null)
			{
				JFrame dependencyFrame = new JFrame("Dependency Error");
				dependencyFrame.setVisible(true);
				dependencyFrame.setSize(500,500);
				JLabel dependencyLabel = new JLabel("Head node cannot contain dependencies!");
				JPanel dependencyPanel = new JPanel();
				dependencyFrame.add(dependencyPanel);
				dependencyFrame.add(dependencyLabel);
			}*/
			
	/*		if (pathItem == null)
			{
				pathItem = new PathItem(duration,itemName,dependencies,mark);
				iterater = pathItem;
			}

			else
			{
				while(iterater.nextItem != null)
				{
					iterater = iterater.nextItem;
				}

				iterater.nextItem = new PathItem(duration, itemName,dependencies,mark);*/
			//}
			printPath(pathItem);
			System.out.print("\n");
			name.setText(null);
			durationField.setText("");
			dependencyField.setText(null);
		//}
		
		if(e.getActionCommand().equals("finish"))
		{	
			Iterator<PathItem> iter = events.iterator();
			//PathItem iter = pathItem;
			while((iter.hasNext()))
			{
				PathItem temp = iter.next();
				if(temp.dependencyStrings == null)
				{
					network = new PathNetwork(temp.copy());
					iter.remove();
				}
			}
			
			iter = events.iterator();
			while(iter.hasNext())
			{
				PathItem temp = iter.next();
				if(searchDependencies(network.pathItem.getName(),temp))
				{
					network.nextItem.add(temp.copy());
					iter.remove();
				}
			}
			iter = events.iterator();
			while(iter.hasNext())
			{
				PathItem temp = iter.next();
				
				for(int i = 0; i < network.nextItem.size(); i++)
				{
					PathItem netIter = network.nextItem.get(i);
					if(!searchDependencies(netIter.getName(), temp))
					{
						netIter = netIter.nextItem;
					}
					else
					{
						while(netIter != null)
						{
							if(searchDependencies(netIter.getName(), temp))
							{
								netIter.nextItem = temp;
							}
							netIter = netIter.nextItem;
						}
					}
				}
			}
			printNetwork(network);
		
		}

		//shantest

		if(e.getActionCommand().equals("Help"))
		{
			JFrame helpFrame = new JFrame("Help");
			helpFrame.setVisible(true);
			helpFrame.setSize(425,700);
			JLabel helpLabel = new JLabel();
			helpLabel.setText("<html><p style=\"width:300px\">"+"The input consists of multiple occurences of the following: activity name, duration, and dependencies (predecessors).<br><br>There is no maximum on the number of activities and predecessors. <br><br>Activity names can be multiple characters. <br><br>Duration must be an integer.<br><br>If another user input is found, then an error is displayed, and the user must re-enter another input before proceeding. The starting node or nodes do not have predecessors. Once all inputs are completed, then the processing can begin.<br><br>Output:<br>Pressing the enter button enters the information that is in the input fields into a hidden list that will be printed upon pressing the finish button. Input field is then cleared.<br><br>Pressing the finish button prints a list of all paths in the network that were submitted, with the duration of each path. The output field consists of the names of all activities in the path, displayed in descending order of duration.<br><br>About:<br>Prompts a new window that explains the project introduction and the overview of the program.<br><br>Restart:<br>Clears all fields of any inputs or outputs and restarts the process of inputting. Upon clicking, all inputs and paths must be re-entered and resubmitted.<br><br>Quit:<br>Halts any processes and quits out of the program immediately. No data will be saved."+"</p></html>");
			JPanel helpPanel = new JPanel();
			helpFrame.add(helpPanel);
			helpFrame.add(helpLabel);
		}

		if(e.getActionCommand().equals("About"))
		{
			JFrame aboutFrame = new JFrame("About");
			aboutFrame.setVisible(true);
			aboutFrame.setSize(425,400);
			JLabel aboutLabel = new JLabel("Fillertext");
			aboutLabel.setText("<html><p style=\"width:300px\">"+"Project Introduction:<br>The project is designed to analyze a network diagram and determine all the paths in the network. A consumer can continue to input the activity name, duration, and dependencies until they press the finish button, where the list of all the activity names and dependencies are displayed by duration amount in decreasing order.<br><br>Overview of the program:<br>The program analyzes a network diagram and determines all the paths in the network. There are certain fields for inputting information (activity name, duration, dependencies), a certain button that can reset the current activity list (restart), a button that brings up the help menu for any issues regarding the program (restart), and another button that allows a user to exit out of the program in its entirety (quit). The output of the program is a network diagram with a list of the user-inputted activity names, sorted in decreasing order. Afterwards, there should be another prompt that allows one to reset the field and continue again."+"</p></html>");
			JPanel aboutPanel = new JPanel();
			aboutFrame.add(aboutPanel);
			aboutFrame.add(aboutLabel);
		}

		if(e.getActionCommand().equals("Quit"))
		{
			System.exit(0);
		}
		//shantest end
	}

	private void display()
	{
		dispIterater = pathItem;
		dispIterater.display();
		while(dispIterater != null)
		{
			dispIterater = dispIterater.nextItem;
			dispIterater.display();
		}
	}
	
	private String[] pathDisplay(PathItem pathItem, int index)
	{
		PathItem iterator = pathItem;
		String[] result = new String[2];
		result[0] = "";
		result[0] += iterator.getName()+ "->";
		result[1] = "" + iterator.getDuration() + ",";
		return result;
	}
	
	/*
	private PathItem search(String name)
	{
		PathItem result = null;
		iterater = pathItem;
		while(iterater!=null)
		{
			if(iterater.getName().equals(name))
			{
				result = iterater;
			}
			else
			{
				iterater = iterater.nextItem;
			}
		}
		iterater = pathItem;
		System.out.print(result.getName());				//testing search function
		return result;
	}
	
	
	private void dependencyFlag()
	{
		iterater = pathItem;
		while(iterater!=null)
		{
			if(iterater.getDependencies() != null) 
			{
				iterater.mark = true; //mark any list with dependencies as True
			}
			iterater = iterater.nextItem;
		}
		
	}
	
	private void checkFlag(String name) 
	{
		iterater = pathItem; 
		while(iterater!=null) 
		{
			for(int i = 0; i < iterater.getDependencies().length; i++) //for loop check every dependency
			{
				if ((iterater.getDependencies()[i].equals(name))) 
				{
					iterater.mark = false; //if the dependencies contain the string name change to false
				}
				else
				{
					iterater.mark = true; //else remain true
				}
			}
			iterater = iterater.nextItem;
		}
	}
	
	private void markDependency()
	{
		iterater = pathItem;
		dependencyFlag(); //mark T/F rids of head or single nodes
		while(iterater!=null)
		{
			if (iterater.mark != true) //if false continue through list
			{
				 
			}
			else
			{
				checkFlag(iterater.getName()); //if marked true check linked list for dependency and mark t or f
			}
			iterater = iterater.nextItem;
		}
		 //pathItem is fixed by iterater
	}
	
	
	private void buildVector()
	{
		PathItem iterater = pathItem;
		while (iterater != null)
		{
			if (iterater.mark == true)
			{
				PathItem tempIterater;
				testItem = iterater.copy();
				tempIterater = testItem;
				tempIterater = tempIterater.nextItem;
				SearchAddVector(iterater.getName());
				iterater = iterater.nextItem;
				testing = testItem;
				testItem = null;
			}
			else
			{
				iterater= iterater.nextItem;
			}
		}
	}
	*/
	private void SearchAddVector(String name)
	{
		iterater = pathItem;
		while (iterater != null)
		{
			for(int i = 0; i < iterater.getDependencies().length; i++) 
			{
				if ((iterater.getDependencies()[i].equals(name)))
				{
					PathItem testIterater = testItem;
					while(testIterater != null)
					{
						testIterater = testIterater.nextItem;
					}
					testIterater = iterater.copy();
					SearchAddVector(iterater.getName());
				}
			}
			iterater = iterater.nextItem;
		}
	}
	
	
	void add(PathItem toAdd)
	{
		paths.add(toAdd);
	}
	
	
	PathItem findLongest(Vector<PathItem> list)		//finds longest path
	{
		PathItem result = list.get(0);
		
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 1; j < list.size(); j++)
			{
				if(list.get(i).calcLength() > list.get(j).calcLength() && list.get(i).calcLength() > result.calcLength())
				{
					result = list.get(i);
				}
			}
		}
		return result;
	}
	
	public void printPath(PathItem path)
	{
		
		Iterator<PathItem> iter = events.iterator();
		while(iter.hasNext())
		{
			System.out.print(iter.next().getName()+"->");
		}
		//if(path == null)
		//{
		//	return;
		//}
		//else
	//	{
		//	System.out.print(path.getName()+"->");
		//	printPath(path.nextItem);
			
		//}
	}
	public void printPlus(PathItem path)
	{
		if(path == null)
			return;
		else
		{
			System.out.print(path.getName()+"->");
			printPlus(path.nextItem);
		}
	}
	
	
	public void printNetwork(PathNetwork path)
	{
		if(path == null)
		{
			return;
		}
		else
		{
			System.out.print(path.pathItem.getName()+"->\t");
			for(int i = 0; i < path.nextItem.size(); i++)
			{
				printPlus(path.nextItem.get(i));
				//System.out.print(path.nextItem.get(i).calcLength());
				System.out.print("\n \t");
			}
			
		}
	}
	
	
	
	
	
	
	
	public boolean searchDependencies(String str, PathItem item)
	{
		boolean result = false;
		if(item.dependencyStrings == null)
			return false;
		String[] list = item.dependencyStrings;
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].equals(str))
			{
				result = true;
				break;
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	public void buildNetwork(PathItem pathItem)
	{
		PathItem iterater = pathItem;
		while(iterater != null)
		{
			if(iterater.isHead())
			{
				
			}
		}
	}

	public void windowOpened(WindowEvent evt) { }

	public void windowClosed(WindowEvent evt) { }



	@Override

	public void windowActivated(WindowEvent arg0) {

		// TODO Auto-generated method stub

		

	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {

		// TODO Auto-generated method stub

		

	}



	@Override

	public void windowDeiconified(WindowEvent arg0) {

		// TODO Auto-generated method stub

		

	}



	@Override

	public void windowIconified(WindowEvent arg0) {

		// TODO Auto-generated method stub

		

	}
}
