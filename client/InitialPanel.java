package client;
import javax.swing.*;
public class InitialPanel extends JPanel {
//data fields
JTextPane initialText;
//constructor
	public InitialPanel()
	{
		JTextPane initialText = new JTextPane();
		initialText.setText("BATTLESHIP");
		this.add(initialText);
	}
	
}

