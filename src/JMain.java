package TextPrinter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;

public class JMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMain frame = new JMain();
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
	public JMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		JLabel iv = new JLabel();
		scrollPane_1.setViewportView(iv);
		

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(iv, popupMenu);
		
		JMenuItem save = new JMenuItem("Save");
		popupMenu.add(save);
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save");
				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						 Icon icon = iv.getIcon();
						BufferedImage bi = new BufferedImage(
			                    icon.getIconWidth(),
			                    icon.getIconHeight(),
			                    BufferedImage.TYPE_INT_RGB);
			                Graphics g = bi.createGraphics();
			                // paint the Icon to the BufferedImage.
			                icon.paintIcon(null, g, 0,0);
			                g.dispose();
						ImageIO.write(bi, "png", new File(fc.getSelectedFile() + ".png"));
						JOptionPane.showMessageDialog(null, "Saved!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("\n Àדחאל4");
		scrollPane.setViewportView(textPane);
		
		JButton ok = new JButton("Ok");
		scrollPane.setColumnHeaderView(ok);
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread printer = new Thread() {
					
					public void run() {
						Printer.print(iv, textPane.getText(), 27, false, true,
								7, 1.5, 2);//21
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//						}
						iv.repaint();
//						contentPane.repaint();
//						scrollPane_1.repaint();
					};
				};
				printer.start();
			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
