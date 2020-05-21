package GUI;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import main.MainProgram;

//import java.awt.Desktop;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class DictDialog extends Dialog implements ItemListener, ActionListener{
	private Font myFont = new Font("SansSerif", Font.ITALIC, 24);
    private String path;
    private File folder;
    private File[] listOfFiles;   
    private Choice choice;

	public DictDialog(Frame parent) {
		super(parent, "Import dictionari", true); 
		path = "../Recnici/";
        folder = new File(path); //ako ne postoji throwuj gresku
        listOfFiles = folder.listFiles();     
        choice = new Choice();
		
		setSize(300, 200);
		addComponents();
		setLayout(null);
		setBackground(new Color(207, 235, 249));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		       dispose(); //close window on X
			}
		});
		setVisible(true);
	}
	
	private void addComponents() {
		addChoice();
		addButton();
	}
	
	private void addChoice() {
        for (File file : listOfFiles) {
			choice.add(file.getName());
        }
        choice.addItemListener(this);
        choice.setBounds(50, 80, 200, 70);
        add(choice);
	}
	
	private void addButton() {
		Button button = new Button("OK");
		//button.setFont(myFont);
		button.setBounds(210, 170, 80, 20);
		button.addActionListener(this);
		button.setBackground(Color.LIGHT_GRAY);
		add(button);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//System.out.println(path+choice.getSelectedItem());
			MainProgram.readDictionary(path+choice.getSelectedItem());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this,
				    "Error in reading dictionary!\nEither the file i s corrupted, or you do not have the permission to read files.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		dispose();
	}
	
}
