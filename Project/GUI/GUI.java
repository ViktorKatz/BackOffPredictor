package GUI;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GUI extends Frame implements ActionListener, TextListener{
	//dodati greske: kad ne unese nikakav folder/file, bigram disconts
	private Font myFont = new Font("SansSerif", Font.BOLD, 24);
	private TextDialog textDialog;
	private DictDialog dictDialog;
	
	public GUI() throws HeadlessException {
		super("Back-off Prediction");
		addComponents();
		setLayout(null);
		setSize(1600, 900);
		setBackground(new Color(207, 235, 249));
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		       dispose(); //close window on X
			}
		});
	}

	public void addMenu() {
		MenuBar menuBar = new MenuBar();
		Menu firstMenu = new Menu("Home"); //save, save as, exit;
		Menu secondMenu = new Menu("Import File"); //from your computer, from our library {...}
		Menu thirdMenu = new Menu("Chart Type"); //pie, graph...
		
		firstMenu.add("Save");
		firstMenu.add("Save As");
		firstMenu.add("Clear");
		firstMenu.add("Exit");
		firstMenu.addActionListener(this);
		
		secondMenu.add("From folder with texts");
		secondMenu.add("From .dict file");
		secondMenu.addActionListener(this);
		
		thirdMenu.add("Pie Chart");
		thirdMenu.add("Bar Chart");
		thirdMenu.addActionListener(this);
		
		menuBar.add(firstMenu);
		menuBar.add(secondMenu);
		menuBar.add(thirdMenu);
		setMenuBar(menuBar);
	}
	
	private void addLabels() {
		Label label1 = new Label("Enter your text here:");
		label1.setFont(myFont);
		label1.setBounds(100, 100, 400, 50);
		add(label1);
		label1.setAlignment(Label.LEFT);
		
		Label label2 = new Label("Bigram discount in %:");
		label2.setFont(myFont);
		label2.setBounds(100, 400, 400, 50);
		add(label2);	
		label2.setAlignment(Label.LEFT);
		
		Label label3 = new Label("Trigram discount in %:");
		label3.setFont(myFont);
		label3.setBounds(100, 700, 400, 50);
		add(label3);
		label3.setAlignment(Label.LEFT);
	}
	
	private void addTextBox() {
		TextArea textArea = new TextArea();
		add(textArea);
		textArea.setBounds(500, 110, 400, 170);
		textArea.setPreferredSize(new Dimension(100,100));
	}
	
	public void addScrollbar() {
		// dodati jos da value ide u discounte
		Label bigramLabel = new Label(String.valueOf(0));
		bigramLabel.setFont(myFont);
		bigramLabel.setBounds(690, 480, 200, 40);
		add(bigramLabel);
		bigramLabel.setAlignment(Label.LEFT);
		
		final Scrollbar bigramScroller = new Scrollbar(Scrollbar.HORIZONTAL);
		bigramScroller.setBounds(500, 400, 400, 50);
	    bigramScroller.setMaximum (110);
	    bigramScroller.setMinimum (0);
	    bigramScroller.addAdjustmentListener(new AdjustmentListener() {
	       @Override
	       public void adjustmentValueChanged(AdjustmentEvent e) {
	          bigramLabel.setText("" +bigramScroller.getValue());
	       }
	    });
	    add(bigramScroller);
	    
	    Label trigramLabel = new Label(String.valueOf(0));
		trigramLabel.setFont(myFont);
		trigramLabel.setBounds(690, 780, 200, 40);
		add(trigramLabel);
		trigramLabel.setAlignment(Label.LEFT);
	    
		final Scrollbar trigramScroller = new Scrollbar(Scrollbar.HORIZONTAL);
		trigramScroller.setBounds(500, 700, 400, 50);
	    trigramScroller.setMaximum (110);
	    trigramScroller.setMinimum (0);
	    trigramScroller.addAdjustmentListener(new AdjustmentListener() {
	       @Override
	       public void adjustmentValueChanged(AdjustmentEvent e) {
	          trigramLabel.setText("" +trigramScroller.getValue());
	       }
	    });
	    add(trigramScroller);
	}
	
	public void addPieChart() {
		
	}

	
	public void paint(Graphics g) {
		g.setFont(myFont);
	//	g.drawString("Enter your text here: ", 60, 150);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		switch(command) {
		case "Save":
			//metoda koja cuva text file/recnik?
			break;
		case "Clear":
			//metoda koja clearuje recnik
			break;
		case "Save as":
			//sacuvaj gde hoces na kompu
			break;
		case "Exit":
			dispose();
		case "From folder with texts":
			TextDialog t = new TextDialog(this);
			break;
		case "From .dict file":
			DictDialog d = new DictDialog(this);
			break;
			
			
		
		}
		
	
	}
	
	private void addComponents() {
		addMenu();
		addLabels();
		addTextBox();
		addScrollbar();
	}

	@Override
	public void textValueChanged(TextEvent t) {
		// dodaj monogram, biram i trigram		
	}
	
	
	public static void main(String args[]) {
		new GUI();
	}


}
