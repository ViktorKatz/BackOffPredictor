package GUI;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.MainProgram;

//import javax.swing.filechooser.FileSystemView;
import javax.swing.*;

@SuppressWarnings("serial")
public class TextDialog extends Dialog implements ActionListener{
	private Font myFont = new Font("SansSerif", Font.ITALIC, 12);
	private Panel panel = new Panel();
	private List<String> fileList = new ArrayList<String>();
	private JTable table;
	//Label label = new Label("Choose a file:");

	public TextDialog(Frame parent) {
		super(parent, "Import text file", true); 
		setSize(300, 200);
		setFont(myFont);
		addComponents();
		setLayout(null);
		setResizable(false);
		setBackground(new Color(207, 235, 249));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		       dispose();
			}
		});
		
		setVisible(true);
	}

	private void addComponents() {
		addButtons();
		addTable();
	}
	
	public void addTable() {
		String[] columnHeaders={"Chosen File Paths:"};
		DefaultTableModel dtm = new DefaultTableModel(columnHeaders, 0);
		
		table = new JTable( dtm ) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        }
		};
	    table.setPreferredScrollableViewportSize(new Dimension(200, 50));
	    table.setRowHeight(25);
	    table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    table.setFont(myFont);
	    panel.add( new JScrollPane(table));
	    panel.setBounds(50, 50, 200, 190);
		add(panel);
	}

	
	private void addChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select .txt file");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .txt files", "txt");
		fileChooser.addChoosableFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			fileList.add(fileChooser.getSelectedFile().getPath());
			//Pravi tabelu:
		    
		    TableModel tm = table.getModel();
		    DefaultTableModel dtm = (DefaultTableModel) tm;
			dtm.addRow( new String[]{fileChooser.getSelectedFile().getName()});
		    dtm.fireTableDataChanged();
			//repaint();
		}
	}
	
	private void addButtons() {
		Button button1 = new Button("OK");
		button1.setBounds(200, 160, 80, 20);
		add(button1);
		button1.addActionListener(this);
		button1.setBackground(Color.LIGHT_GRAY);
		button1.setActionCommand("end");
		
		Button button2 = new Button("Browse...");
		button2.setBounds(20, 160, 80, 20);
		add(button2);
		button2.addActionListener(this);
		button2.setBackground(Color.LIGHT_GRAY);
		button2.setActionCommand("browse");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "browse") {
			addChooser();
		}
		else if(e.getActionCommand() == "end") {
			if(fileList.isEmpty()) dispose();
			try {
				MainProgram.makeDictionaryFromDirectories(fileList);
			}
			catch(IOException io) {
			}
			dispose();
		}
	}
}
