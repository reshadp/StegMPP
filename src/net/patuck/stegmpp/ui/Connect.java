
package net.patuck.stegmpp.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.patuck.stegmpp.xmpp.Session;

/**
 * The Connect class sets up the connection parameters.
 * @author reshad
 */
public class Connect extends JFrame
{
	
	private JPanel panel;
	private JLabel l_username;
	private JTextField t_username;
	private JLabel l_password;
	private JPasswordField t_password;
	private JLabel l_server;
	private JTextField t_server;
	private JLabel l_port;
	private JTextField t_port;
	private JLabel l_to;
	private JTextField t_to;
	
	private JButton b_connect;
	private JButton b_disconnect;
	
	/**
	 * Default constructor.
	 * Sets the title and makes the Connect frame.
	 */
	public Connect()
	{
		super("Connection Settings");
		
		panel = new JPanel();
		panel.setLayout(null);
		
		l_username = new JLabel("Username:");
		panel.add(l_username);
		t_username = new JTextField();
		t_username.addFocusListener(new FocusEventHandler());
		panel.add(t_username);
		l_password = new JLabel("Password:");
		panel.add(l_password);
		t_password = new JPasswordField();
		t_password.addFocusListener(new FocusEventHandler());
		panel.add(t_password);
		l_server = new JLabel("Server:");
		panel.add(l_server);
		t_server = new JTextField();
		t_server.addFocusListener(new FocusEventHandler());
		panel.add(t_server);
		l_port = new JLabel("Port:");
		panel.add(l_port);
		t_port = new JTextField();
		t_port.addFocusListener(new FocusEventHandler());
		panel.add(t_port);
		l_to = new JLabel("To:");
		panel.add(l_to);
		t_to = new JTextField();
		t_to.addFocusListener(new FocusEventHandler());
		panel.add(t_to);
		
		b_connect =  new JButton("Connect");
		b_connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// Setup the session.
				Session.setUsername(getUsername());
				Session.setPassword(getPassword());
				Session.setServer(getServer());
				Session.setPort(Integer.parseInt(getPort()));
				if(t_to.getText().indexOf('@') < 0)
				{
					t_to.setText(t_to.getText() + '@' + getServer());
				}
				Session.setTo(getTo());
				setVisible(false);
				
				// Connect to server.
				Session.connect();
			}
		});
		panel.add(b_connect);
		b_disconnect = new JButton("Disconnect");
		b_disconnect.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// Disconnect the sesion.
				Session.disconnect();
				setVisible(false);
			}
		});
		panel.add(b_disconnect);
		
		// Populate data.
		t_username.setText(Session.getUsername());
		t_password.setText(Session.getPassword());
		t_server.setText(Session.getServer());
		t_port.setText(String.valueOf(Session.getPort()));
		t_to.setText(Session.getTo());
		
		setContentPane(panel);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setSize(350, 210);
		setLocationRelativeTo(null);
		repaint();
	}
	
	
	/**
	 * Draw the components.
	 * @param g Graphics object.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		int c1=10;
		int c2=90;
		int y=10;
		
		l_username.setBounds(c1, y, 80, 10);
		t_username.setBounds(c2, y-5, 250 ,20);
		y += 30;
		l_password.setBounds(c1, y, 80, 10);
		t_password.setBounds(c2, y-5, 250 ,20);
		y += 30;
		l_server.setBounds(c1, y, 80, 10);
		t_server.setBounds(c2, y-5, 250 ,20);
		y += 30;
		l_port.setBounds(c1, y, 80, 10);
		t_port.setBounds(c2, y-5, 250 ,20);
		y += 30;
		l_to.setBounds(c1, y, 80, 10);
		t_to.setBounds(c2, y-5, 250 ,20);
		
		y += 25;
		
		b_connect.setBounds(30,y,120,20);
		b_disconnect.setBounds(200, y, 120, 20);
	}
	
	
	/**
	 * Getter method to get the username.
	 * @return the username.
	 */
	public String getUsername()
	{
		return t_username.getText();
	}
	
	
	/**
	 * Getter method to get the password.
	 * Changes the password from a char array to a string.
	 * @return the password.
	 */
	public String getPassword()
	{
		return String.copyValueOf(t_password.getPassword());
	}
	
	
	/**
	 * Getter method to get the server.
	 * @return the server.
	 */
	public String getServer()
	{
		return t_server.getText();
	}
	
	
	/**
	 * Getter method to get the port number.
	 * Change the port number to a string.
	 * @return the pot number.
	 */
	public String getPort()
	{
		return t_port.getText();
	}
	
	
	/**
	 * Getter method to get the to field.
	 * @return the value of the to field.
	 */
	public String getTo()
	{
		return t_to.getText();
	}
	
}
