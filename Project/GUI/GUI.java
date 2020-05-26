package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.*;
import javax.swing.table.*;

import helpers.StringHelper;
import main.MainProgram;
import model.Prediction;

import java.util.stream.Collectors;
import java.util.stream.Collectors.* ;

@SuppressWarnings("serial")
public class GUI extends Frame implements ActionListener, TextListener{
	//dodati greske: kad ne unese nikakav folder/file, bigram disconts
	private Font myFont = new Font("SansSerif", Font.BOLD, 24);
	private Panel panel = new Panel();
	private TextArea textArea;
	private JTable table;
	private final static int predictionsShown = 10;

	public GUI() throws HeadlessException {
		super("Back-off Prediction");
		addComponents();
		setLayout(null);
		setSize(1600, 900);
		setBackground(new Color(207, 235, 249));
		setResizable(false);
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
		Menu secondMenu = new Menu("Import Data");
		
		firstMenu.add("Save");
		firstMenu.add("Clear");
		firstMenu.add("Exit");
		firstMenu.addActionListener(this);
		secondMenu.add("Import .dict file");
		secondMenu.add("Add Data from .txt file");
		secondMenu.addActionListener(this);
		
		menuBar.add(firstMenu);
		menuBar.add(secondMenu);
		setMenuBar(menuBar);
	}
	
	private void addLabels() {
		Label label1 = new Label("Enter your text here:");
		label1.setFont(myFont);
		label1.setBounds(100, 100, 300, 50);
		add(label1);
		label1.setAlignment(Label.LEFT);
		
		Label label2 = new Label("Bigram discount in %:");
		label2.setFont(myFont);
		label2.setBounds(100, 600, 400, 50);
		add(label2);	
		label2.setAlignment(Label.LEFT);
		
		Label label3 = new Label("Trigram discount in %:");
		label3.setFont(myFont);
		label3.setBounds(100, 750, 400, 50);
		add(label3);
		label3.setAlignment(Label.LEFT);
	}
	
	private void addTextBox() {
		textArea = new TextArea();
		textArea.setFont(myFont);
		add(textArea);
		textArea.addTextListener(this);
		textArea.setBounds(100, 150, 800, 400);
		textArea.setPreferredSize(new Dimension(100,100));
	}
	
	public void addScrollbar() {
		// dodati jos da value ide u discounte
		Label bigramLabel = new Label(String.valueOf(0));
		bigramLabel.setFont(myFont);
		bigramLabel.setBounds(690, 650, 200, 40);
		add(bigramLabel);
		bigramLabel.setAlignment(Label.LEFT);
		
		final Scrollbar bigramScroller = new Scrollbar(Scrollbar.HORIZONTAL);
		bigramScroller.setBounds(500, 600, 400, 50);
	    bigramScroller.setMaximum (110);
	    bigramScroller.setMinimum (0);
	    bigramScroller.addAdjustmentListener(new AdjustmentListener() {
	       @Override
	       public void adjustmentValueChanged(AdjustmentEvent e) {
	          bigramLabel.setText("" +bigramScroller.getValue());
	          MainProgram.setDiscount(2, bigramScroller.getValue()/100.0);
	       }
	    });
	    add(bigramScroller);
	    
	    Label trigramLabel = new Label(String.valueOf(0));
		trigramLabel.setFont(myFont);
		trigramLabel.setBounds(690, 800, 200, 40);
		add(trigramLabel);
		trigramLabel.setAlignment(Label.LEFT);
	    
		final Scrollbar trigramScroller = new Scrollbar(Scrollbar.HORIZONTAL);
		trigramScroller.setBounds(500, 750, 400, 50);
	    trigramScroller.setMaximum (110);
	    trigramScroller.setMinimum (0);
	    trigramScroller.addAdjustmentListener(new AdjustmentListener() {
	       @Override
	       public void adjustmentValueChanged(AdjustmentEvent e) {
	          trigramLabel.setText("" +trigramScroller.getValue());
	          MainProgram.setDiscount(3, trigramScroller.getValue()/100.0);
	       }
	    });
	    add(trigramScroller);
	}
	
	public void updateChart(List<Prediction> dataList) {	   
	   List<String[]> dataStringList = dataList.stream()
		.filter(p -> !p.word.equals(" ") && !p.word.equals(""))
	   .sorted( (Prediction pr1, Prediction pr2) -> {
		   if(pr1.probability > pr2.probability)
			   return -1;
		   if(pr1.probability < pr2.probability)
			   return 1;
		   return 0;
	   } )
	   .limit(predictionsShown)
	   .map( new Function<Prediction, String[]>() {
		@Override
		public String[] apply(Prediction pred) {
			String[] s = {pred.word, String.format("%.10f", pred.probability*100.0) + "%"};
			return s;
		}
	   })
	   .collect( Collectors.toList() ) ;
	   
	   //Pravi tabelu:
	    
	    TableModel tm = table.getModel();
	    
	    DefaultTableModel dtm = (DefaultTableModel) tm;
	    
		dtm.setRowCount(0);
	    
	    for (String[] data : dataStringList) {
			dtm.addRow(data);
		}
	    
	    dtm.fireTableDataChanged();
	    
		repaint();
	}
	
	public void addPanel() {
		String[] columnHeaders={"Word", "Probability"};
		
		DefaultTableModel dtm = new DefaultTableModel(columnHeaders, predictionsShown);
		
		table = new JTable( dtm ) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        }
		};

	    table.setPreferredScrollableViewportSize(new Dimension(600, 400));
	    table.setRowHeight(50);
	    table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    table.getColumnModel().getColumn(1).setPreferredWidth(100);
	    table.setFont(myFont);
	    panel.add( new JScrollPane(table), BorderLayout.WEST);
	    panel.setBounds(870, 150, 800, 500);
		add(panel);
	}
	
	public void paint(Graphics g) {
	//	g.setFont(myFont);
	//	g.drawString("Enter your text here: ", 60, 150);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		switch(command) {
		case "Save":
			String filename = (String)JOptionPane.showInputDialog(
                    this,
                    "Please enter the custom filename:\n",
                    "Saving...",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");
			
			try {
				MainProgram.saveDictionary(filename);
			}
			catch(IOException e) {
				JOptionPane.showMessageDialog(this,
					    "Error in saving the file!\nPlease try with another filename.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
			
			break;
		case "Clear":
			//metoda koja clearuje recnik
			break;
		case "Exit":
			dispose();
			break;
		case "Add Data from .txt file":
			TextDialog t = new TextDialog(this);
			break;
		case "Import .dict file":
			DictDialog d = new DictDialog(this);
			break;
		}
		
	
	}
	
	private void addComponents() {
		addMenu();
		addLabels();
		addTextBox();
		addScrollbar();
		addPanel();
	}

	@Override
	public void textValueChanged(TextEvent t) {
		
		String text = textArea.getText();
		
		if(!text.endsWith(" ") && !text.endsWith(".") && !text.endsWith("?") && !text.endsWith("!"))
			return;		//The chart is updated only on spacebar or EOS
		
		String bareText = StringHelper.removeUnwantedChars(text);
		
		String adjustedText = StringHelper.replaceNUMChars(StringHelper.replaceEOSChars(bareText));
	
		String[] words = StringHelper.divideToUnigrams(adjustedText);
		
		MainProgram.addToCurrentDictionary(words);
		
		if(text.endsWith(".") || text.endsWith("?") || text.endsWith("!"))
			MainProgram.addToCurrentDictionary( Arrays.copyOfRange(words, 0, words.length-1) );
		
		if(words.length<2)
			return;		//The chart is updated only when there is enough data for a prefix.
		
		String[] prefix = StringHelper.getNLastWords(words, 2);
		
		List<Prediction> predictions = MainProgram.getPredictions(prefix);
		
		updateChart(predictions);
	}
	
	
	public static void main(String args[]) {
		new GUI();
	}


}
