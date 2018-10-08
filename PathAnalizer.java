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
	private TextField name,durationField;
	private Button enterButton, getButton, aboutButton, helpButton, restartButton, quitButton;	//getButton calculates the required items
	private int duration;
	private String itemName = "";
	private PathItem pathItem = null, iterater = null, dispIterater = null;
	public PathAnalizer()
	{
		duration = 0;
		setLayout(new FlowLayout());
		add(new Label("Path Name "));
		name = new TextField("",10);
		name.setEditable(true);
		add(name);
		add(new Label("Path Duration "));
		durationField = new TextField("",10);
		add(durationField);
		enterButton = new Button("enter");
		add(enterButton);
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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("enter"))
		{
			duration = Integer.parseInt(durationField.getText());
			itemName = name.getText();
			if(pathItem == null)
			{
				pathItem = new PathItem(duration,itemName);
				iterater = pathItem;
			}
			else
			{
				while(iterater.nextItem != null)
				{
					iterater = iterater.nextItem;
				}
				iterater.nextItem = new PathItem(duration, itemName);
			}
			display();
		}
		//shantest
		if(e.getActionCommand().equals("Help"))
		{
			JFrame helpFrame = new JFrame("Help");
			helpFrame.setVisible(true);
			helpFrame.setSize(500,500);
			JLabel helpLabel = new JLabel("Fillertext");
			JPanel helpPanel = new JPanel();
			helpFrame.add(helpPanel);
			helpFrame.add(helpLabel);
		}
		if(e.getActionCommand().equals("About"))
		{
			JFrame aboutFrame = new JFrame("About");
			aboutFrame.setVisible(true);
			aboutFrame.setSize(500,500);
			JLabel aboutLabel = new JLabel("Fillertext");
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
}
