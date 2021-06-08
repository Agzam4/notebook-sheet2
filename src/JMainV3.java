package TextPrinter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.ComponentOrientation;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import java.awt.Graphics;

public class JMainV3 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
					}
					PrinterV3 printer = new PrinterV3();
					boolean b = !printer.Init();
					System.out.println(b);
					if(b) {
						Installer.Install();
					}else {
						JMainV3 frame = new JMainV3(printer);
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public JMainV3(PrinterV3 printer) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		//		splitPane.setResizeWeight(0.5);
		splitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		splitPane.setInheritsPopupMenu(true);
		splitPane.setContinuousLayout(true);
		splitPane.setAutoscrolls(true);
		splitPane.setDividerSize(10);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane imgScroll = new JScrollPane();
		imgScroll.getVerticalScrollBar().setUnitIncrement(15);
		imgScroll.getHorizontalScrollBar().setUnitIncrement(15);
		splitPane.setLeftComponent(imgScroll);

		JLabel iv = new JLabel("");
		imgScroll.setViewportView(iv);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		printer.update(textPane.getText(),
				(int) 500,
				false, false,
				(int) 150,
				(double) 3.5,
				35, 0,0,0,0);
		iv.setIcon(new ImageIcon(printer.getImg()));
		iv.repaint();

		JButton ok = new JButton("Ok");
		panel_2.add(ok);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Light", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
//
//		JPanel zoomPanel = new JPanel();
//		((FlowLayout)zoomPanel.getLayout()).setAlignment(FlowLayout.LEFT);
//		panel_3.add(zoomPanel);
//		JLabel zoomL = new JLabel("Zoom: ");
//		zoomPanel.add(zoomL);
		JCheckBox ww = new JCheckBox();
		
		JSpinner zoom = new JSpinner();
		JSpinner distance = new JSpinner();
		JSpinner width = new JSpinner();
		JSpinner ws = new JSpinner();
		
		zoom.setModel(new SpinnerNumberModel(7, 5, 25, 1));
		distance.setModel(new SpinnerNumberModel(38, 10, 50, 1));
		width.setModel(new SpinnerNumberModel(27, 1, 99, 1));
		ws.setModel(new SpinnerNumberModel(9, 1, 10, 1));
		
		createSettingsPanel(zoom, "Zoom: ", panel_3);
		createSettingsPanel(distance, "Distance: ", panel_3);
		createSettingsPanel(width, "Width: ", panel_3);
		createSettingsPanel(ws, "Word size: ", panel_3);
		createSettingsPanel(ww, "Word wrap: ", panel_3);

		JSpinner ttt = new JSpinner();
		JSpinner x1 = new JSpinner();
		JSpinner x2 = new JSpinner();
		
		ttt.setModel(new SpinnerNumberModel(7, -25, 25, 1));
		x1.setModel(new SpinnerNumberModel(20, 0, 1000, 5));
		x2.setModel(new SpinnerNumberModel(100, 0, 1000, 5));
		
		createSettingsPanel(ttt, "T: ", panel_4);
		createSettingsPanel(x1, "X1: ", panel_4);
		createSettingsPanel(x2, "X2: ", panel_4);

		JSpinner l = new JSpinner();
		JSpinner lr = new JSpinner();
		JSpinner lx = new JSpinner();
		JSpinner ly = new JSpinner();
		
		l.setModel(new SpinnerNumberModel(200, 0, 255, 1));
		lr.setModel(new SpinnerNumberModel(4500, 0, 10000, 25));
		lx.setModel(new SpinnerNumberModel(1350, 0, 10000, 25));
		ly.setModel(new SpinnerNumberModel(1500, 0, 10000, 25));

		createSettingsPanel(l, "Light: ", panel_5);
		createSettingsPanel(lr, "R: ", panel_5);
		createSettingsPanel(lx, "X: ", panel_5);
		createSettingsPanel(ly, "Y: ", panel_5);

		
//		JPanel distancePanel = new JPanel();
//		((FlowLayout)distancePanel.getLayout()).setAlignment(FlowLayout.LEFT);
//		panel_3.add(distancePanel);
//		JLabel distanceL = new JLabel("Distance: ");
//		distancePanel.add(distanceL);
//		distancePanel.add(distance);
//
//		JPanel widthPanel = new JPanel();
//		((FlowLayout)widthPanel.getLayout()).setAlignment(FlowLayout.LEFT);
//		panel_3.add(widthPanel);
//		JLabel widthL = new JLabel("Width: ");
//		widthPanel.add(widthL);
//		widthPanel.add(width);
//
//		JPanel wsPanel = new JPanel();
//		((FlowLayout)wsPanel.getLayout()).setAlignment(FlowLayout.LEFT);
//		panel_3.add(wsPanel);
//		JLabel wsl = new JLabel("Word size: ");
//		wsPanel.add(wsl);
//		wsPanel.add(ws);

		
		splitPane.setDividerLocation(iv.getIcon().getIconWidth());
		iv.repaint();

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread printerr = new Thread() {

					public void run() {
						int dis = 60 - ((int) distance.getValue());
						int vs = 11-((int) (ws.getValue()));
						printer.update(textPane.getText(),
								(int) width.getValue()*vs/2,
								false, ww.isSelected(),
								(int) zoom.getValue(),
								(double) (dis)/10,
								vs,
								
								(int)lr.getValue(),
								(int)lx.getValue(),
								(int)ly.getValue(),
								(int)l.getValue());
						//printer.getImg()
						iv.setIcon(new ImageIcon(printer.transform(
								(int)ttt.getValue(),
								(int)x1.getValue()*(int)zoom.getValue(),
								(int)x2.getValue()*(int)zoom.getValue())));
						iv.repaint();
					};
				};
				printerr.start();
			}
		});
		
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
	}
	
	private void createSettingsPanel(JComponent component, String name, JPanel panel) {
		JPanel jPanel = new JPanel();
		((FlowLayout)jPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		panel.add(jPanel);
		JLabel l = new JLabel(name);
		jPanel.add(l);
		jPanel.add(component);
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
