package GUI;

import java.io.File;
import java.io.IOException;
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
		path = "../Recnici";
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
		//String file = choice.getSelectedItem() + "";
		Desktop desktop = Desktop.getDesktop();
		File file = new File(choice.getSelectedItem());
		String filePath = file.getAbsolutePath();
		try {
          // desktop.open(file); 
          desktop.open(new File(filePath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//sacuvaj koji je recnik
		dispose();
	}
	
}
