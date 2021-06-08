package TextPrinter;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class JMainV2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
				}

				
				PrinterV2 printer = new PrinterV2();
				boolean b = !printer.Init();
				System.out.println(b);
				if(b) {
					Installer.Install();
				}else {
					try {
						JMainV2 frame = new JMainV2(printer);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JMainV2(PrinterV2 printer) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 561, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 3);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{150, 150, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
				GridBagConstraints gbc_panel_21 = new GridBagConstraints();
				gbc_panel_21.insets = new Insets(0, 0, 5, 0);
				gbc_panel_21.fill = GridBagConstraints.BOTH;
				gbc_panel_21.gridx = 0;
				gbc_panel_21.gridy = 1;
								
								JPanel panel_2 = new JPanel();
								GridBagConstraints gbc_panel_2 = new GridBagConstraints();
								gbc_panel_2.insets = new Insets(0, 0, 5, 0);
								gbc_panel_2.fill = GridBagConstraints.BOTH;
								gbc_panel_2.gridx = 0;
								gbc_panel_2.gridy = 0;
								panel.add(panel_2, gbc_panel_2);
								panel_2.setLayout(new CardLayout(0, 0));
								
								JPanel panel_3 = new JPanel();
								panel_3.setLayout(new BorderLayout(0, 0));
								
								JScrollPane scrollPane_1 = new JScrollPane();
								panel_3.add(scrollPane_1);
								
								JLabel iv = new JLabel("");
								scrollPane_1.setViewportView(iv);
								
								JPopupMenu popupMenu = new JPopupMenu();
								addPopup(iv, popupMenu);
								
								JMenuItem save = new JMenuItem("Save");
								popupMenu.add(save);
								panel_2.add(panel_3, "name_534943855855100");
						
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
								JScrollPane settingsScroll = new JScrollPane();
								GridBagConstraints gbc_settingsScroll = new GridBagConstraints();
								gbc_settingsScroll.fill = GridBagConstraints.BOTH;
								gbc_settingsScroll.gridx = 0;
								gbc_settingsScroll.gridy = 1;
								panel.add(settingsScroll, gbc_settingsScroll);
								
								JPanel panel_4 = new JPanel();
								settingsScroll.setViewportView(panel_4);
								panel_4.setLayout(new MigLayout("", "[][][][grow]", "[][][][][][]"));
								
								JLabel lblNewLabel = new JLabel("Settings");
								panel_4.add(lblNewLabel, "flowx,cell 0 0");
								
								JLabel lblNewLabel_1 = new JLabel("Zoom:");
								panel_4.add(lblNewLabel_1, "flowx,cell 0 1");
								
								JLabel lblNewLabel_2 = new JLabel("distance:");//Character 
								panel_4.add(lblNewLabel_2, "flowx,cell 0 2");
								
								JLabel xSizeLable = new JLabel("Width: ");
								panel_4.add(xSizeLable, "flowx,cell 0 3");
								
								JLabel lblNewLabel_3 = new JLabel("Word size:");
								panel_4.add(lblNewLabel_3, "flowx,cell 0 4");
								
								JCheckBox wordWrap = new JCheckBox(" Word wrap");
								wordWrap.setSelected(true);
								wordWrap.setFocusable(false);
								panel_4.add(wordWrap, "cell 0 5");
								
								JSpinner wordSize = new JSpinner();
								wordSize.setModel(new SpinnerNumberModel(9, 1, 10, 1));
								panel_4.add(wordSize, "cell 0 4,growx");
								
								JSpinner width = new JSpinner();
								width.setModel(new SpinnerNumberModel(27, 1, 99, 1));
								panel_4.add(width, "cell 0 3,growx");
								
								JSpinner distance = new JSpinner();
								distance.setModel(new SpinnerNumberModel(45, 10, 50, 1));
								panel_4.add(distance, "cell 0 2,growx");
								
								JSpinner zoom = new JSpinner();
								zoom.setModel(new SpinnerNumberModel(7, 1, 25, 1));
								panel_4.add(zoom, "cell 0 1,growx");
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.SOUTH);
		
		
		JButton ok = new JButton("Convert");
		ok.setFocusable(false);
		panel_6.add(ok);
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread printerr = new Thread() {
					
					public void run() {
						int dis = 60 - ((int) distance.getValue());
						int vs = 11-((int) (wordSize.getValue()));
						printer.print(iv, textPane.getText(),
								(int) width.getValue()*vs/2,
								false, wordWrap.isSelected(),
								(int) zoom.getValue(),
								(double) (dis)/10,
								vs);
						iv.repaint();
					};
				};
				printerr.start();
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
