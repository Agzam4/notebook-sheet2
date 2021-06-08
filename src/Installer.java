package TextPrinter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Installer extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	public static void Install() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Installer frame = new Installer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Installer frame = new Installer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Installer() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Installing the font");
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Font file:");
		panel_2.add(lblNewLabel_1);
		
		textField = new JTextField("Auto");
		textField.setEditable(false);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.setEnabled(false);
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JButton install = new JButton("Install");
		panel_3.add(install);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		
		JProgressBar progressBar = new JProgressBar();
		panel_5.add(progressBar);
		
		JLabel pp = new JLabel("0%");
		panel_5.add(pp);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_6.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		install.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.removeAll();
				contentPane.repaint();
				contentPane.setVisible(false);
				contentPane.add(panel_4, BorderLayout.CENTER);
				contentPane.repaint();
				contentPane.setVisible(true);
				
				Thread t = new Thread() {
					@Override
					public void run() {
						ImageFasten.go(progressBar, pp, textPane, new File("data\\v4\\font.png"));
					}
				};
				t.start();
			}
		});
		
		
	}

	
}
