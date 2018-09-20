package main_pkg;
import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
public class PathAnalizer extends Frame
implements ActionListener, WindowListener
{
	
	private TextField filler;
	private Button testButton;
	private int path;
	public PathAnalizer()
	{
		path = 0;
		setLayout(new FlowLayout());
		add(new Label("Path"));
		filler = new TextField("0",10);
		filler.setEditable(true);
		add(filler);
		testButton = new Button("counter");
		add(testButton);
		testButton.addActionListener(this);
		addWindowListener(this);
		setTitle("Path Analizer");
		setSize(250,100);
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
	public void actionPerformed(ActionEvent e) {
		path = Integer.parseInt(filler.getText());
		System.out.print(path);
	}

}
