package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class TextDialog extends Dialog implements ActionListener{
	private Font myFont = new Font("SansSerif", Font.ITALIC, 12);
	Label label = new Label("Choose a file:");

	public TextDialog(Frame parent) {
		super(parent, "Import text file", true); 
		setSize(300, 200);
		setFont(myFont);
		addComponents();
		setLayout(null);
		setBackground(new Color(207, 235, 249));
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		       dispose(); //close window on X
		       //NE RADI
			}
		});
	}

	private void addComponents() {
		addButtons();
		addLabel();
	}
	
	private void addChooser() {
		//throw exception ako nije izabran txt file (approve option)
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select .txt file");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .txt files", "txt");
		fileChooser.addChoosableFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			label.setText("Izabrano: " + fileChooser.getSelectedFile().getPath());
			//fileChooser.getSelectedFile(); fajl koji ce se koristiti
		}
	}
	
	private void addLabel() {
		label.setBounds(100, 50, 100, 50);
		label.setFont(myFont);
		add(label);
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
			dispose();
		}
		//sacuvaj koji je recnik
	}
}
