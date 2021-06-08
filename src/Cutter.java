package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Cutter {

	public static void main(String[] args) throws IOException {
		BufferedImage main = ImageIO.read(new File(System.getProperty("user.dir") + "\\cutting\\Main.png"));

		final int rc = Color.RED.getRGB();
		final int gc = Color.GREEN.getRGB();
		final int w = 274;
		final int h = 278;
		final String names = "qwerasdfzxcvQWERASDFZXCV";
		final char[] cs = names.toCharArray();

		ArrayList<Integer> rx = new ArrayList<Integer>();
		ArrayList<Integer> ry = new ArrayList<Integer>();
		
		ArrayList<Integer> gx = new ArrayList<Integer>();
		ArrayList<Integer> gy = new ArrayList<Integer>();

		ArrayList<Integer> srx = new ArrayList<Integer>();
		ArrayList<Integer> sry = new ArrayList<Integer>();
		

		for (int y = 0; y < main.getHeight(); y++) {
			for (int x = 0; x < main.getWidth(); x++) {
				if(main.getRGB(x, y) == rc) {
					rx.add(x);
					ry.add(y);
				}
				if(main.getRGB(x, y) == gc) {
					gx.add(x);
					gy.add(y);
				}
//				Color c = new Color(main.getRGB(x, y));
//				int g = (c.getRed()+c.getGreen()+c.getBlue())/3;
//				g = g < 100 ? 0 : 255;
//				main.setRGB(x, y, (new Color(g,g,g)).getRGB());
			}
		}
		
		// SORT
		
//		ArrayList<Integer> ids = new ArrayList<Integer>();
//		ArrayList<Integer> roy = new ArrayList<Integer>();
//		int yv1 = (int)(Math.round(ry.get(0)/h)*h);
//		roy.add(yv1);
//		ids.add(0);
//		for (int i = 1; i < ry.size(); i++) {
//			int yv = (int)(Math.round(ry.get(i)/h)*h);
//			if(yv == roy.get(i-1)) {
//				ids.add(i);
//			}else {
//				Collections.sort(ids);
//				for (int j = 0; j < ids.size(); j++) {
//					srx.add(rx.get(ids.get(j)));
//					sry.add(ry.get(ids.get(j)));
//				}
//				ids.clear();
//				ids.add(i);
//			}
//			roy.add(yv);
//		}

		System.out.println("RED:");
		for (int i = 0; i < srx.size(); i++) {
			System.out.println(sry.get(i));//srx.get(i) + ":" + 
		}

		for (int i = 0; i < rx.size(); i++) {
			BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) newImage.getGraphics();
			g.drawImage(main, -rx.get(i), -ry.get(i), null);
			g.dispose();

			String input = JOptionPane.showInputDialog(null, new JLabel(new ImageIcon(newImage)));
			String name = (input).equals((input).toLowerCase()) ?
					"l" + input : ("U" + input).toLowerCase();
			try {
				ImageIO.write(newImage, "png", new File(System.getProperty("user.dir") +
						"\\cutting\\1\\" + name + ".png"));
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					 input = JOptionPane.showInputDialog(null, new JLabel(new ImageIcon(newImage)));
					 name = (input).equals((input).toLowerCase()) ?
							"l" + input : ("U" + input).toLowerCase();
					ImageIO.write(newImage, "png", new File(System.getProperty("user.dir") +
							"\\cutting\\1\\" + name + ".png"));
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
