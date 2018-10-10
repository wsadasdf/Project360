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
			
			try
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
					Iterator<PathItem> iter = events.iterator();
					while(iter.hasNext())
					{
						PathItem dupe = iter.next();
						if(dupe.getName().equals(itemName))
						{
							throw  new UnsupportedOperationException();
						}
					}
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
				name.setText(null);
				durationField.setText("");
				dependencyField.setText(null);
			}
			catch(IllegalArgumentException exception)
			{
				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "dependency not an integer"
						+ "", "Error", JOptionPane.ERROR_MESSAGE);

			}
			catch(UnsupportedOperationException exception)
			{
				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Node already exists"
						+ "", "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				name.setText(null);
				durationField.setText("");
				dependencyField.setText(null);
			}

		}	
			
				
		if(e.getActionCommand().equals("finish"))						//finish
		{	
			Iterator<PathItem> iter = events.iterator();
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
					//iter.remove();
				}
			}
			iter = events.iterator();
			while(iter.hasNext())
			{
				PathItem temp = iter.next();
				
				for(int i = 0; i < network.nextItem.size(); i++)
				{
					PathItem netIter = network.nextItem.get(i);

					
						while(netIter != null)
						{
							
							if(searchDependencies(netIter.getName(), temp))
							{
								netIter.nextItem = temp.copy();
							}
							netIter = netIter.nextItem;
						}
				}
			}
			JFrame outputFrame = new JFrame("Output");
			outputFrame.setVisible(true);
			outputFrame.setSize(425,650);
			JLabel outputLabel = new JLabel();
			String output = printNetwork(network);
			outputLabel.setText("<html><p style=\"width:300px\">"+output+"<br><br>"+printLongest(network)+"</p></html>");
			JPanel outputPanel = new JPanel();
			outputFrame.add(outputPanel);
			outputFrame.add(outputLabel);
		}

		//shantest

		if(e.getActionCommand().equals("Help"))
		{
			JFrame helpFrame = new JFrame("Help");
			helpFrame.setVisible(true);
			helpFrame.setSize(425,650);
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
			aboutFrame.setSize(425,450);
			JLabel aboutLabel = new JLabel("Fillertext");
			aboutLabel.setText("<html><p style=\"width:300px\">"+"Project Introduction:<br>The project is designed to analyze a network diagram and determine all the paths in the network. A consumer can continue to input the activity name, duration, and dependencies until they press the finish button, where the list of all the activity names and dependencies are displayed by duration amount in decreasing order.<br><br>Overview of the program:<br>The program analyzes a network diagram and determines all the paths in the network. There are certain fields for inputting information (activity name, duration, dependencies), a certain button that can reset the current activity list (restart), a button that brings up the help menu for any issues regarding the program (restart), and another button that allows a user to exit out of the program in its entirety (quit). The output of the program is a network diagram with a list of the user-inputted activity names, sorted in decreasing order. Afterwards, there should be another prompt that allows one to reset the field and continue again.<br><br>This application is a team project assignment for CSE360: Introduction to Software Engineering.<br><br>Team Members:<br>Duncan Everhart<br>Shaun Xiong"+"</p></html>");
			JPanel aboutPanel = new JPanel();
			aboutFrame.add(aboutPanel);
			aboutFrame.add(aboutLabel);
		}
		
		if(e.getActionCommand().equals("Restart"))
		{
			pathItem = null;
			name.setText(null);
			durationField.setText("");
			dependencyField.setText(null);
			network = null;
			events.clear();
		}

		if(e.getActionCommand().equals("Quit"))
		{
			System.exit(0);
		}
		//shantest end
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
	
	public String printPath(PathItem path)
	{
		String result="";
		Iterator<PathItem> iter = events.iterator();
		while(iter.hasNext())
		{
			result+=(iter.next().getName()+"->");
		}
		return result;
	}
	public String printPlus(PathItem path)
	{
		if(path == null)
			return "";
		else
		{
			return(path.getName()+"->")+printPlus(path.nextItem);
		}
	}
	
	
	public String printNetwork(PathNetwork path)
	{
		if(path == null)
		{
			return "";
		}
		else
		{
			String result="";
			int length= 0;
			result+=(path.pathItem.getName()+"->\t");
			Iterator<PathItem> temp = path.nextItem.iterator();
			while(temp.hasNext())
			{
				PathItem item = temp.next();
				result+=printPlus(item);
				length = network.pathItem.getDuration();
				length += depth(item);
				result+=(length)+("\n \t");
			}
			return result; 
			
			
		}
	}
	
	public int depth(PathItem item)
	{
		if(item == null)
			return 0;
		else
			return item.getDuration() + depth(item.nextItem);
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
	
	
	public int dependencyCount(String parent, ArrayList<PathItem> child)
	{
		int count= 0;
		Iterator<PathItem> iter = child.iterator();
		while(iter.hasNext())
		{
			PathItem item = iter.next();
			if(searchDependencies(parent,item) == true)
				count++;
		}
		return count;
		
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
	
	String printLongest(PathNetwork paths)
	{
		PathItem longest = paths.nextItem.get(0);
		Iterator<PathItem> temp = paths.nextItem.iterator();
		while(temp.hasNext())
		{
			int length  = 0;
			PathItem item = temp.next();
			for(int i = 0; i < paths.nextItem.size()-1; i++)
			{
				
				if(depth(longest)<depth(item)) {
					longest = item.copy();
				}
			}
			
			
		}
		String result;
		result = "\nlongest path: ";
		result+=network.pathItem.getName();
		result+="->";
		result+=printPlus(longest);
		result+=((depth(longest)+network.pathItem.getDuration()));
		return result;
	}
}
