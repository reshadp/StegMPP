/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import net.patuck.stegmpp.stego.Stego;

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
	private JRadioButton r_reciever;
	private ButtonGroup sendOrRecieve;
	
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
		//panel.add(t_data);
		sp_data = new JScrollPane(t_data);
		panel.add(sp_data);
		l_key = new JLabel("Key:");
		panel.add(l_key);
		repaint();
		t_key = new JTextField();
		panel.add(t_key);
		
		sendOrRecieve = new ButtonGroup();
		r_sender =  new JRadioButton("Sender");
		sendOrRecieve.add(r_sender);
		panel.add(r_sender);
		r_reciever = new JRadioButton("Reciever");
		sendOrRecieve.add(r_reciever);
		panel.add(r_reciever);
		
		b_ok = new JButton("ok");
		b_ok.setMargin(new Insets(2, 4, 2, 4));
		b_ok.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				setVisible(false);
				Stego.Setup(t_data.getText(), t_key.getText(), r_sender.isSelected());
			}
		});
		panel.add(b_ok);
		
		
		
		setContentPane(panel);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(new Dimension(400, 400));
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
		//t_data.setBounds(10, y, getWidth()-20, 200);
		
		y += 210;
		
		l_key.setBounds(10, y, 50, 20);
		t_key.setBounds(50, y, getWidth()-60, 20);
		
		y += 30;
		
		r_sender.setBounds(10, y, 80, 20);
		r_reciever.setBounds(100, y, 100, 20);
		
		y += 30;
		
		b_ok.setBounds(10, y, 50, 30);
	}
}
