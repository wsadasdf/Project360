package main_pkg;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Stack;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PathAnalizer extends Frame
implements ActionListener, WindowListener
{
	/**
	 main class holds the window and the fields to be entered
	 */
	//variable block

	private static final long serialVersionUID = 7942054704709588561L;
	private boolean headCreated = false;
	private boolean error = false;
	private TextField name,durationField,dependencyField;
	private String results = "";
	private JCheckBox critPath = new JCheckBox();
	private Button enterButton, finishButton, aboutButton, helpButton, restartButton, quitButton, editButton;	//getButton calculates the required items
	private int duration, maxDuration = 0;
	private String[] dependencies;
	private String itemName = "";
	private Stack<PathNetwork> stack = new Stack<PathNetwork>();
	private ArrayList<PathNetwork> paths = new ArrayList<PathNetwork>();
	private ArrayList<PathNetwork> sortedPaths = new ArrayList<PathNetwork>();
	private PathNetwork network;
	private TextField reportField;
	private ArrayList<PathItem> events = new ArrayList<PathItem>();
	private ArrayList<PathItem> eventsCopy, sortedCopy;
	
	public PathAnalizer()
	{
		duration = 0;
		reportField = new  TextField("",10);
		critPath.setText("only critical");
		setLayout(new FlowLayout());
		add(new Label("Path Name "));
		name = new TextField("",10);
		name.setEditable(true);
		add(name);
		add(new Label("Path Duration "));
		durationField = new TextField("",10);
		add(durationField);
		add(new Label("dependancies "));
		dependencyField = new TextField("",10);
		add(dependencyField);
		add(new Label("report name"));
		add(reportField);
		add(critPath);
		editButton = new Button("edit");
		enterButton = new Button("enter");
		add(enterButton);
		finishButton = new Button("finish");
		add (finishButton);
		

		//shan test
		add(editButton);
		aboutButton = new Button("About");
		add(aboutButton);
		helpButton = new Button("Help");
		add(helpButton);
		restartButton = new Button("Restart");
		add(restartButton);
		quitButton = new Button("Quit");
		add(quitButton);
		Button report = new Button("generate report");
		add(report);

		//shan test end
		report.addActionListener(this);
		enterButton.addActionListener(this);
		aboutButton.addActionListener(this);
		helpButton.addActionListener(this);
		quitButton.addActionListener(this);
		finishButton.addActionListener(this);
		restartButton.addActionListener(this);
		editButton.addActionListener(this);
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
		if(e.getActionCommand().equals("generate report"));
		{
			try
			{
				Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("'created at' H:mm:ss a 'on' MM/dd/yyyy");
		        String timeStamp = sdf.format(cal.getTime());
				String[] result;
				result = results.split("\n");
				PrintWriter writer = new PrintWriter(reportField.getText(), "UTF-8");
				writer.println(timeStamp);
				writer.println();
				writer.println("report name: "+ reportField.getText());
				writer.println();
				writer.println("Activities ordered by duration");
				writer.println();
				sortedCopy = new ArrayList<PathItem>();
				Iterator<PathItem> garbIter;
				while(!eventsCopy.isEmpty())
				{
					int tempMax=0;
					for(int i = 0; i < eventsCopy.size(); i++)
					{
						if(eventsCopy.get(i).duration >= tempMax)
						{
							tempMax = eventsCopy.get(i).duration;
						}
						
					}
					for(int i = 0; i < eventsCopy.size(); i++)
					{
						if(eventsCopy.get(i).duration == tempMax)
						{
							sortedCopy.add(eventsCopy.get(i));
							eventsCopy.remove(i);
						}
					}
				}
				
				
				
				garbIter = sortedCopy.iterator();
				while(garbIter.hasNext())
				{
					PathItem garb = garbIter.next();
					writer.println(garb.getName()+"("+garb.duration+")");
				}
				writer.println();
				
				for(int i = 0; i < result.length; i++)
				{
					writer.println(result[i]);
				}
				
				writer.close();
			}
			catch(IOException exception)
			{
				System.out.print(exception.getMessage());
			}
		}
		if(e.getActionCommand().equals("edit"))
		{
			int toChange = Integer.parseInt(durationField.getText());
			String nodeName = name.getText();
			change(nodeName,toChange,network);
		}
		if(e.getActionCommand().equals("Restart"))
		{
			error = false;
			headCreated = false;
			itemName = "";
			duration = 0;
			dependencies = null;
			name.setText(null);
			durationField.setText("");
			dependencyField.setText(null);
			critPath.setSelected(false);
			network = null;
			events.clear();
			paths.clear();
			sortedPaths.clear();
			maxDuration = 0;
			results = "";
		}
		
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
				JOptionPane.showMessageDialog(panel, "duration is not an integer"
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
				dependencyField.setText("");
				durationField.setText(null);
			}

		}	
			
				
		if(e.getActionCommand().equals("finish"))						//finish
		{	
			eventsCopy = (ArrayList<PathItem>)events.clone();
			if(!events.isEmpty()) 
			{
				sortedPaths.clear();
				paths.clear();
				results = "";
				Iterator<PathItem> iter = events.iterator();
				while((iter.hasNext()))
				{
					PathItem temp = iter.next();
					if((temp.dependencyStrings == null))
					{
						if(headCreated)
						{
							error = true;
							JPanel panel = new JPanel();
							JOptionPane.showMessageDialog(panel, "disjoint node detected"
									+ "", "Error", JOptionPane.ERROR_MESSAGE);
							break;
						}
						else
						{
							network = new PathNetwork(temp.copy());
							System.out.print(network.item.getName()+"("+network.item.getDuration()+")->");
							headCreated = true;
							iter.remove();
						}
						
					}
				}
				
				for(int i = 0; i < events.size(); i++)
				{
					if(error)
					{
						break;
					}
					iter = events.iterator();
					while((iter.hasNext()))
					{
						PathItem temp = iter.next();
						insert(temp,network);
						if(temp.dependencyStrings.size() == 0)
						{
							iter.remove();
							break;
							
						}
					}
				}
				
				
				makePaths(network);
				Iterator<PathNetwork> tempIter = paths.iterator();
				
				//calculates the path durations
				while(tempIter.hasNext())
				{
					PathNetwork temp = tempIter.next();
					temp.item.headTime = getDuration(temp);
					
				}
				
				System.out.print("\n");
				sort(paths);
				if(critPath.isSelected() && !error)
				{
					maxDuration = sortedPaths.get(0).item.headTime;
					Iterator<PathNetwork> pathIter = sortedPaths.iterator();
					while(pathIter.hasNext())
					{
						PathNetwork temp = pathIter.next();
						if(temp.item.headTime < maxDuration)
						{
							pathIter.remove();
						}
					}
					printNetwork(sortedPaths);
					results = "only critical paths\n" + results;
					System.out.print(results);
				}
				else if(!error)
				{
					printNetwork(sortedPaths);
					results = "all paths\n" + results;
					System.out.print(results);
				}
				
				
				if(!error)
				{
					JFrame outputFrame = new JFrame("Output");
					outputFrame.setVisible(true);
					outputFrame.setSize(425,650);
					JLabel outputLabel = new JLabel();
					outputLabel.setText("<html><p style=\"width:300px\">"+results+"<br><br>"+"</p></html>");
					JPanel outputPanel = new JPanel();
					outputFrame.add(outputPanel);
					outputFrame.add(outputLabel);
					
				}
			}
			else
			{
				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "no events entered"
						+ "", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
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
		
		

		if(e.getActionCommand().equals("Quit"))
		{
			System.exit(0);
		}
		//shantest end
	}
	
	
	public void printNetwork(ArrayList<PathNetwork> paths)
	{		
		Iterator<PathNetwork> iter = paths.iterator();
		while(iter.hasNext())
		{
			PathNetwork temp = iter.next();
			path(temp);
			results += temp.item.headTime;
			results += "\n";
		}
		
		return;
	}
	
	
	public void path(PathNetwork paths)
	{
		
		if(paths.children.size() == 1)
		{
			results += paths.children.get(0).item.getName() +"("+ paths.children.get(0).item.getDuration() + ")->";
			path(paths.children.get(0));
		}
		else
		{
			return;
		}
	}
	
	
	void insert(PathItem toInsert, PathNetwork net)
	{
		PathNetwork temp = net;
		boolean done = false;
			if(searchDependencies(temp.item.getName(),toInsert))
			{
				for(int i = 0; i < temp.children.size(); i++)
				{
					if(temp.children.get(i).item.getName().equals(toInsert.getName()))
					{
						done = true;
						break;
					}
					
				}
				if(!done)
				{
					temp.children.add(new PathNetwork(toInsert));
					System.out.print(toInsert.getName() +"("+toInsert.getDuration()+ ")->");
				}
			}
			else
			{
				if(temp.children.size() > 0)
				{
					for(int i = 0; i < temp.children.size(); i++)
					{
						insert(toInsert, temp.children.get(i));
					}
				}
			}
		return;
	}
	
	
	void makePaths(PathNetwork network)
	{
		PathNetwork netIter = network;
		if(network.children.size() > 0)
		{
			stack.push(netIter);
			for(int i = 0; i < netIter.children.size(); i++)
			{
				
				makePaths(netIter.children.get(i));
			}
			stack.pop();
			
		}
		else
		{
			stack.push(new PathNetwork(network.item.copy()));
			Iterator<PathNetwork> stackIter = stack.iterator();
			PathNetwork head = new PathNetwork(stack.firstElement().item.copy());
			PathNetwork iter = head;
			while(stackIter.hasNext())
			{
				PathNetwork temp = stackIter.next();
				iter.children = new ArrayList<PathNetwork>(0);
				iter.children.add( new PathNetwork(temp.item.copy()));
				iter = iter.children.get(0);
				
			}
			paths.add(head);
			stack.pop();
		}
	}
	
	
	public boolean searchDependencies(String str, PathItem item)
	{
		boolean result = false;
		if(item.dependencyStrings == null)
			return false;
		ArrayList<String> list = item.dependencyStrings;
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).equals(str))
			{
				result = true;
				break;
			}
		}
		return result;
	}
	
	
	public int getDuration(PathNetwork path)
	{
		int result = 0;
		while(path.children.size() != 0)
		{
			path = path.children.get(0);
			result += path.item.duration;
			
		}

		return result;
	}
	
	
	public void sort(ArrayList<PathNetwork> toSort)
	{
		if(toSort.size() == 0)
		{
			return;
		}
		maxDuration = 0;
		Iterator<PathNetwork> pathIter = toSort.iterator();
		while(pathIter.hasNext())
		{
			PathNetwork temp = pathIter.next();
			if(temp.item.headTime > maxDuration)
			{
				maxDuration = temp.item.headTime;
			}
		}
		pathIter = toSort.iterator();
		while(pathIter.hasNext())
		{
			PathNetwork temp = pathIter.next();
			if(temp.item.headTime == maxDuration)
			{
				sortedPaths.add(temp);
				pathIter.remove();
			}
		}
		sort(toSort);
		return;
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

	
	public void change(String toChange, int time, PathNetwork net)
	{
		if(net.item.getName().equals(toChange))
		{
			net.item.duration = time;
		}
		for(int i = 0; i < net.children.size(); i++)
		{
			change(toChange,time,net.children.get(i));
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
