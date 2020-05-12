package GUI;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GUI extends Frame implements ActionListener{
	private Font myFont = new Font("SansSerif", Font.BOLD, 24);

	public GUI() throws HeadlessException {
		super("Back-off Prediction");
		addComponents();
		setLayout(null);
		setSize(1600, 900);
		setBackground(Color.lightGray);
		//setLayout(null);
		setVisible(true);
	}

	public void addMenu() {
		MenuBar menuBar = new MenuBar();
		Menu firstMenu = new Menu("Home"); //save, save as, exit;
		Menu secondMenu = new Menu("Import File"); //from your computer, from our library {...}
		Menu thirdMenu = new Menu("Graph Format"); //pie, graph...
		firstMenu.add("Save");
		firstMenu.add("Save As");
		firstMenu.add("Exit");
		firstMenu.addActionListener(this);
		
		Menu subMenu = new Menu("From our library");
		CheckboxMenuItem field = new CheckboxMenuItem("Blogs");
	    subMenu.add(field);
	    field = new CheckboxMenuItem("Wikipedia");
	    subMenu.add(field);
		secondMenu.add(subMenu);
		secondMenu.add("From your computer");
		secondMenu.addActionListener(this);
		
		thirdMenu.add("Pie");
		thirdMenu.add("Bar");
		
		menuBar.add(firstMenu);
		menuBar.add(secondMenu);
		setMenuBar(menuBar);
	}
	
	public void addLabels() {
		
		/*Problem je sto kod svake labele zelim da je stavim na tacno 
		 * odredjeno mesto na frame ali nmg nikako. Svi odgovori koje sam nasla na netu su
		 * ili sa SWT ili mi kazeu da u konstruktoru stavim setLayout(null) a ja kad stavim
		 * to setLayout na null prikaze mi prazan panel*/
		
		Label label1 = new Label("Enter your text here:");
		label1.setFont(myFont);
		label1.setBounds(200, 200, 400, 50);
		add(label1);
		label1.setAlignment(Label.LEFT);
		//label1.setLocation(10, 30);
		
		Label label2 = new Label("Bigram discount in %:");
		label2.setFont(myFont);
		label2.setBounds(200, 300, 400, 50);
		add(label2);	
		label2.setAlignment(Label.LEFT);
		//label2.setLocation(10, 30);
		
		Label label3 = new Label("Trigram discount in %:");
		label3.setFont(myFont);
		label3.setBounds(200, 400, 400, 50);
		add(label3);
		label3.setAlignment(Label.LEFT);
		//label3.setLocation(10, 30);
		
	}
	
	public void paint(Graphics g) {
		//g.setFont(myFont);
		//g.drawString("Enter your text here: ", 60, 150);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		switch(command) {
		case "Exit":
			dispose();
		case "Save":
			
			
		
		}
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	void addComponents() {
		addMenu();
		addLabels();
	}
	
	public static void main(String args[]) {
		new GUI();
	}

}
