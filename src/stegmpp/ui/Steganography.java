package stegmpp.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import stegmpp.stego.Stego;

/**
 * This class defines the Steganography frame, used to set the steganographic variables.
 * @author reshad
 */
public class Steganography extends JFrame
{
	private JPanel panel;
	private JLabel l_data;
	private JTextArea t_data;
	private JScrollPane sp_data;
	private JLabel l_key;
	private JTextField t_key;
	
	private JRadioButton r_sender;
	private JRadioButton r_receiver;
	private ButtonGroup sendOrReceive;
	
	private JCheckBox cb_leadingSpace;
	private JCheckBox cb_trailingSpace;
	private JCheckBox cb_idValue;
//	private JCheckBox cb_typeCase;
	private JCheckBox cb_langAttribute;
	private JCheckBox cb_typeAttribute;
	
	private JButton b_ok;
	
	/**
	 * Default constructor.
	 * sets the title and draws the frame.
	 */
	public Steganography()
	{
		super("Steganography Settings");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		l_data = new JLabel("Data:");
		panel.add(l_data);
		
		t_data = new JTextArea();
		sp_data = new JScrollPane(t_data);
		panel.add(sp_data);
		
		l_key = new JLabel("Key:");
		panel.add(l_key);
		
		t_key = new JTextField("key");
		panel.add(t_key);
		
		sendOrReceive = new ButtonGroup();
		
		r_sender =  new JRadioButton("Sender");
		sendOrReceive.add(r_sender);
		panel.add(r_sender);
		
		r_receiver = new JRadioButton("Receiver");
		r_receiver.setSelected(true);
		sendOrReceive.add(r_receiver);
		panel.add(r_receiver);
		
		cb_leadingSpace = new JCheckBox("Leading Space");
		cb_leadingSpace.setSelected(true);
		panel.add(cb_leadingSpace);
		
		cb_trailingSpace = new JCheckBox("Trailing Space");
		cb_trailingSpace.setSelected(true);
		panel.add(cb_trailingSpace);
		
		cb_idValue = new JCheckBox("Value of ID attribute");
		cb_idValue.setSelected(true);
		panel.add(cb_idValue);
		
//		cb_typeCase = new JCheckBox("Case of type attribute");
//		cb_typeCase.setSelected(false);
//		panel.add(cb_typeCase);
		
		cb_langAttribute = new JCheckBox("xml:lang attribute");
		cb_langAttribute.setSelected(true);
		panel.add(cb_langAttribute);
		
		cb_typeAttribute = new JCheckBox("Presence of type attribute");
		cb_typeAttribute.setSelected(true);
		panel.add(cb_typeAttribute);
		
		b_ok = new JButton("ok");
		b_ok.setMargin(new Insets(2, 4, 2, 4));
		b_ok.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				setVisible(false);
				Stego.Setup(t_data.getText(), t_key.getText(), r_sender.isSelected(), getCheckedTypes());
			}
		}
		);
		panel.add(b_ok);
		
		repaint();
		setContentPane(panel);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(new Dimension(400, 500));
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	
	/**
	 * Draw the components.
	 * @param g Graphics object
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		int y = 10;
		
		l_data.setBounds(10, y, 100, 20);
		
		y += 20;
		sp_data.setBounds(10, y, getWidth()-20, 200);
		t_data.setSize(getWidth()-20, 200);
		
		y += 210;
		l_key.setBounds(10, y, 50, 20);
		t_key.setBounds(50, y, getWidth()-60, 20);
		
		y += 30;
		r_sender.setBounds(10, y, 80, 20);
		r_receiver.setBounds(100, y, 100, 20);
		
		y += 30;
		cb_idValue.setBounds(10, y, 300, 20);
		
//		y += 20;
//		cb_typeCase.setBounds(10, y, 200, 20);
		
		y += 20;
		cb_langAttribute.setBounds(10, y, 300, 20);
		
		y += 20;
		cb_typeAttribute.setBounds(10, y, 300, 20);
		
		y += 20;
		cb_leadingSpace.setBounds(10, y, 300, 20);
		
		y += 20;
		cb_trailingSpace.setBounds(10, y, 300, 20);
		
		y += 30;
		b_ok.setBounds(10, y, 50, 30);
	}
	
	/**
	 * Set the text in the data message box.
	 * @param message the message to set.
	 */
	protected void setData(String message)
	{
		t_data.setText(message);
	}
	
	/**
	 * Get a list of check-boxes selected by class name of their implementation.
	 * @return the list of check-boxes selected by the user..
	 */
	private List<String> getCheckedTypes()
	{
		List<String> list = new ArrayList<>();
		if(cb_idValue.isSelected())
		{
			list.add("IDValue");
		}
		if(cb_langAttribute.isSelected())
		{
			list.add("LangAttribute");
		}
		if(cb_typeAttribute.isSelected())
		{
			list.add("TypeAttribute");
		}
		if(cb_leadingSpace.isSelected())
		{
			list.add("LeadingSpace");
		}
		if(cb_trailingSpace.isSelected())
		{
			list.add("TrailingSpace");
		}
		return list;
	}
}
