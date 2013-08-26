
package stegmpp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import stegmpp.xmpp.Message;
import stegmpp.xmpp.XEP0085;

/**
 * The UI class is responsible for making the user interface.
 * @author Reshad Patuck
 */
public class UI extends JFrame
{
	
	private static UI ui = new UI();
	
	private Connect connect;
	private Steganography steganography;
	
	private JPanel window;
	
	private JToolBar toolbar;
	private JButton b_connection;
	private JButton b_steganography;
	
	private JTextPane t_chatBox;
	private StyledDocument sd;
	private JScrollPane sp_chatBox;
	
	private JTextField t_message;
	private JButton b_send;
	
	
	/**
	 * Default constructor which initialises the title.
	 */
	private UI()
	{
		super("StegMPP");
	}
	
	
	/**
	 * Get the UI object being used.
	 * @return the UI object.
	 */
	public static UI getUI()
	{
		return ui;
	}

	
	/**
	 * The build method builds the UI.
	 */
	public void build()
	{
		connect = new Connect();
		steganography = new Steganography();
		
		window = new JPanel();
		window.setLayout(null);
		
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		b_connection = new JButton("Connection");
		b_connection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				connect.setVisible(true);
			}
		}
		);
		toolbar.add(b_connection);
		b_steganography = new JButton("Steganography");
		b_steganography.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				steganography.setVisible(true);
			}
		}
		);
		toolbar.add(b_steganography);
		window.add(toolbar);
		
		t_chatBox = new JTextPane();
		t_chatBox.setEditable(false);
		t_chatBox.setText("");
		t_chatBox.setContentType("text/html");
		sd = t_chatBox.getStyledDocument();
		sp_chatBox = new JScrollPane(t_chatBox);
		sp_chatBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp_chatBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		window.add(sp_chatBox);
		
		t_message = new JTextField();
		t_message.setEnabled(false);
		window.add(t_message);
		
		b_send = new JButton("Send");
		window.add(b_send);
		
		addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentResized(ComponentEvent ce)
			{
				repaint();
			}
		}
		);
		setSize(new Dimension(400, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(window);
		setLocationRelativeTo(null);
		setVisible(true);
		
		steganography.setVisible(true); // Open the steganography window on startup.
		repaint();
	}
	
	
	/**
	 * Override the paint method to repaint the UI and set the size of components.
	 * @param g Graphics object.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		// draw components in the correct position and with the correct size.
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		int y=0;
		
		
		toolbar.setBounds(0, y, windowWidth, 30);
		
		y += 2;
		
		b_connection.setBounds(1, y, 100, 25);
		b_steganography.setBounds(105, y, 130, 25);
		
		y += 38;
		sp_chatBox.setBounds(10, y-5, (windowWidth-25), (windowHeight-y)-60);
		// Set vertical scroll bar to maximum.
		t_chatBox.setCaretPosition(t_chatBox.getDocument().getLength());
		// Set the size of the Text pane to that of the scroll pane.
		t_chatBox.setSize(sp_chatBox.getSize());
		
		y += (windowHeight-y)-60;
		t_message.setBounds(10, y, windowWidth-100, 20);
		b_send.setBounds(windowWidth-85, y, 70, 20);
	}

	
	/**
	 * Get the connect ui object.
	 * @return the connect object used by the UI.
	 */
	public Connect getConnect()
	{
		return connect;
	}
	
	
	/**
	 * The print method styles and prints a message to the chatBox.
	 * @param out the message.
	 * @param style the Style.
	 */
	public void print(String out, int style)
	{
		try
		{
			javax.swing.text.Style s = t_chatBox.addStyle("style", null);
			
			if( style >= Style.OUTGOING )
			{
				StyleConstants.setForeground(s, new Color(0x00, 0x00, 0xDD));
				StyleConstants.setBold(s, true);
				style -= Style.OUTGOING;
			}
			if( style >= Style.INCOMING )
			{
				StyleConstants.setForeground(s, new Color(0x00, 0xCC, 0x00));
				StyleConstants.setBold(s, true);
				style -= Style.INCOMING;
			}
			if( style >= Style.SYSTEM )
			{
				StyleConstants.setForeground(s, new Color(0x99, 0x00, 0x00));
				StyleConstants.setBold(s, true);
				style -= Style.SYSTEM;
			}
			
			
			sd.insertString(sd.getLength(), out, s);
			
			repaint();
		}
		catch (BadLocationException ex)
		{
			Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	/**
	 * Calls print with default styling.
	 * @param out the message.
	 */
	public void print(String out)
	{
		print(out, Style.DEFAULT);
	}
	
	
	/**
	 * Calls the println method with default styling.
	 * @param out the message.
	 */
	public void println(String out)
	{
		println(out, Style.DEFAULT);
	}
	
	
	/**
	 * Adds new line to the message and calls print.
	 * @param out The message.
	 * @param style The Style.
	 */
	public void println(String out, int style)
	{
		print(out + "\n", style);
	}

	
	/**
	 * Enable the message box so the user can start sending messages.
	 */
	public void enableMessaging()
	{
		t_message.setEnabled(true);
		t_message.addKeyListener(new KeyAdapter() 
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
				XEP0085 xep =new XEP0085(t_message.getText());
				new Thread(xep).start();
			}
		}
		);
		
		t_message.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				b_send.getActionListeners()[0].actionPerformed(ae);
			}
		}
		);
		
		b_send.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if(t_message.getText().trim().length() > 0)
				{
					Message message = new Message();
					message.addBody(t_message.getText());
					message.sendMessage();
					print("[" + connect.getUsername() + "] ",Style.INCOMING);
					println(t_message.getText());
					t_message.setText("");
				}
			}
		}
		);
	}

	
	/**
	 * The setData method sets the data received over the covert channel.
	 * @param data The hidden message received by the program.
	 */
	public void setData(String data)
	{
		steganography.setData(data);
	}
	
}
