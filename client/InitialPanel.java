package client;
import javax.swing.*;
public class InitialPanel {
//data fields
JPanel initialPanel;
JTextPane initialText;
//constructor
	public InitialPanel()
	{
		JTextPane initialText = new JTextPane();
		initialText.setText("BATTLESHIP");
		initialPanel.add(initialText);
	}
	
	public JPanel getInitialPanel()
	{
		return initialPanel;
	}
}

