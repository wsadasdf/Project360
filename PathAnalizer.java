package main_pkg;
import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
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
	private int duration;
	private String[] dependencies;
	private String itemName = "";
	private PathItem pathItem = null, iterater = null, dispIterater = null;
	private boolean mark;
	
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
			dependencies = dependencyField.getText().split(",");
			
			String test = dependencyField.getText();
			String test2 = name.getText();
			Object test3 = Integer.parseInt(durationField.getText());
			
			if (test.contains(test2))
			{
				JFrame dependencyFrame = new JFrame("Dependency Error");
				dependencyFrame.setVisible(true);
				dependencyFrame.setSize(500,500);
				JLabel dependencyLabel = new JLabel("Network node cannot depend on itself!");
				JPanel dependencyPanel = new JPanel();
				dependencyFrame.add(dependencyPanel);
				dependencyFrame.add(dependencyLabel);
			}
			/*
			else if (search(name.getText()) != null && (pathItem.getSize() > 1))
			{
				JFrame dependencyFrame = new JFrame("Dependency Error");
				dependencyFrame.setVisible(true);
				dependencyFrame.setSize(500,500);
				JLabel dependencyLabel = new JLabel("Circular function detected!");
				JPanel dependencyPanel = new JPanel();
				dependencyFrame.add(dependencyPanel);
				dependencyFrame.add(dependencyLabel);
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
			
			else if (pathItem == null && dependencies != null)
			{
				JFrame dependencyFrame = new JFrame("Dependency Error");
				dependencyFrame.setVisible(true);
				dependencyFrame.setSize(500,500);
				JLabel dependencyLabel = new JLabel("Head node cannot contain dependencies!");
				JPanel dependencyPanel = new JPanel();
				dependencyFrame.add(dependencyPanel);
				dependencyFrame.add(dependencyLabel);
			}
			
			if (pathItem == null)
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

				iterater.nextItem = new PathItem(duration, itemName,dependencies,mark);
			}
			display();
			name.setText(null);
			durationField.setText("");
			dependencyField.setText(null);
		}
		
		if(e.getActionCommand().equals("finish"))
		{
			
			//buildNetwork(pathItem);
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
		while(dispIterater.nextItem != null)
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
				iterater.mark = true;
			}
			iterater = iterater.nextItem;
		}
		pathItem = iterater;
	}
	
	private void checkFlag(String name) 
	{
		iterater = pathItem;
		while(iterater!=null)
		{
			if ((iterater.getDependencies().toString()).contains(name))
			{
				iterater.mark = false;
			}
		}
	}
	
	private void checkDependency(String name)
	{
		iterater = pathItem;
		dependencyFlag();
		while(iterater!=null)
		{
			if (iterater.mark != true)
			{
				iterater = iterater.nextItem;
			}
			else
			{
				checkFlag(iterater.getName().toString());
			}
		}
		pathItem = iterater;
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
