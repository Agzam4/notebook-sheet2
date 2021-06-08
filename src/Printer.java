package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Printer {

	private static BufferedImage[] images = getImages();
	private static BufferedImage bgTile = getBgTile();
	public static final String abc = "!$%,'-0123456789\u205A\u2022"
			+ "יצףךו¸םדרשחץתפûגאןנמכהז‎ÿקסלטעüב‏_";
	
	private static BufferedImage[] getImages() {
		BufferedImage[] images = new BufferedImage[abc.length()];
		char[] cs = abc.toCharArray();
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(new File(
						System.getProperty("user.dir") + "\\data\\Abc\\" 
				+ cs[i] + ".png"));
			} catch (IOException e) {
				System.err.print(cs[i]+"");
			}
		}
		return images;
	}
	
	private static BufferedImage getBgTile() {
		try {
			return ImageIO.read(new File(
					System.getProperty("user.dir") + "\\data\\paperTile.png"));
		} catch (IOException e) {
			return null;
		}
	}

	static final int w = 274;
	static final int h = 278;
	public static void print(JLabel iv, String text, int max, boolean cut, boolean ww,
			int zoom, double distance, int wordSize) {
		
//		int zoom = 7;
//		double distance = 1.5;
//		int wordSize = 2;
		String txt = text.toLowerCase();
		String formtxt = txt.replaceAll(" ", "_");
		formtxt = formtxt.replaceAll("\\?", "%");
		formtxt = formtxt.replaceAll("\"", "'");
		formtxt = formtxt.replaceAll("\\.", "\\$");
		formtxt = formtxt.replaceAll(":", "\u205A");
		formtxt = formtxt.replaceAll("@", "\u2022");
		String wordWrap = "";
		String[] words = formtxt.split("_");
		char[] cs;
		// WW 
		if(ww) {
			int lenght = 0;
			for (int i = 0; i < words.length; i++) {
				lenght += words[i].length()+1;
				if(!(lenght > max*distance)) {
					wordWrap += words[i] + "_";
				}else {
					wordWrap += "\n" + words[i] + "_";
					lenght = words[i].length()+1;
				}
			}
			// - words[i].length()
			cs = wordWrap.toCharArray();
		}else{
			cs = formtxt.toCharArray();
		}
		
		System.out.println(wordWrap);

		int nw = w/zoom;
		int nh = h/zoom;
		int px = 0;
		int py = 0;
		
		// BOUNDS 
		
		int bx = (int) (max*distance);
		int by = 1;
		
		if(cut) {
			for (int i = 0; i < cs.length; i++) {
				if((cs[i]+"").equals("\n")) {
					px = 0;
					by++;
					py++;
				}else {
					px++;
				}
				if(px > max-1) {
					px = 0;
					by++;
					py++;
				}
			}
		}else{
			by = (max*210)/148;
		}
		
		BufferedImage main = new BufferedImage((max)*(274/zoom),
				(by*(278/zoom)),
				BufferedImage.TYPE_INT_RGB);
		//(int) ((Math.floor(txt.length()/max)+1)
		px = 0;
		py = 0;
				
		Graphics2D g = (Graphics2D) main.getGraphics();
		g.setColor(new Color(148,156,162));
		g.fillRect(0, 0, main.getWidth(), main.getHeight());
		
		for (int x = 0; x < max/wordSize + 1; x++) {
			for (int y = 0; y < by/wordSize + 1; y++) {
				g.drawImage(bgTile, (int)(x*nw*wordSize),(int)(y*nh*wordSize),
						(int)(nw*wordSize),(int)(nh*wordSize), null);
			}
		}
		for (int i = 0; i < cs.length; i++) {
			if((cs[i]+"").equals("\n")) {
				px = 0;
				py++;
			}else {
				try {
					int index = abc.indexOf(cs[i] + "");
					g.drawImage(images[index], (int) (px*nw/distance),
							(int) (py*nh + Math.random()*(nh/10)),
							(int) (nw+ Math.random()*(nh/10)),
							(int) (nh+ Math.random()*(nh/10)),
							null);
					px ++;
				} catch (ArrayIndexOutOfBoundsException e) {
//					System.err.println("SE: \""  + cs[i] + "\"");
				}
			}
			if(px > (bx)-1 && !ww) {
				px = 0;
				py ++;
			}
		}
		
		g.dispose();
		iv.setIcon(new ImageIcon(main));
	}
}


//g.drawImage(bgTile, (i%max)*nw - 1,
//		(int) (Math.floor(i/max))*w/zoom,
//		nw, h/zoom, null);
/*
 * try {
			BufferedImage img = ImageIO.read(new File(
					System.getProperty("user.dir") + "\\data\\Abc\\" 
			+ cs[i] + ".png"));
			images[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) images[i].getGraphics();

			for (int x = 0; x < img.getWidth(); x+=1) {
				for (int y = 0; y < img.getHeight(); y+=1) {
					Color c = new Color(img.getRGB(x, y));
					int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
					g.setColor(new Color(0,0,0,255-grey));
					g.drawRect(x, y, 1, 1);
				}
			}
			g.dispose();
			ImageIO.write(images[i], "png", new File(
					System.getProperty("user.dir") + "\\data\\Abc\\" 
			+ cs[i] + ".png"));
//			for (int x = 0; x < images[i].getWidth(); x++) {
//				for (int y = 0; y < images[i].getHeight(); y++) {
//					Color c = new Color(images[i].getRGB(x, y));
//					int grey = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
//					setAlpha((byte) grey, images[i], x, y);
//				}
//			}
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir") + "\\data\\Abs\\" 
			+ cs[i] + ".png");
			e.printStackTrace();
		}
		System.out.println(i+ ")");
 */
		