package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PrinterV3 {
	
	/**
	 * @author Agzam4
	 **/

	
	// "/*" to delite all code
	//* 
	
	private BufferedImage[] images;
	private BufferedImage bgTile = getBgTile();
	public String chars = "";
	public boolean Init() {
		
		BufferedImage[] lowers;
		BufferedImage[] uppers;
		String upper = "";
		String lower = "";
		File[] fs = new File(
				System.getProperty("user.dir") + "\\data\\v4").listFiles();
		BufferedImage[] images = new BufferedImage[fs.length];
		boolean isl[] = new boolean[fs.length];
		
		if(fs.length < 5) {
			return false;
		}
		
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(fs[i]);
				char[] name = fs[i].getName().toCharArray();
				if(name[0] == 'l') {
					lower += (name[1]+"").toLowerCase();
					isl[i] = true;
				}else {
					upper += (name[1]+"").toUpperCase();
					isl[i] = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		int l = 0;
		int u = 0;
		lowers = new BufferedImage[lower.length()];
		uppers = new BufferedImage[upper.length()];
		for (int i = 0; i < images.length; i++) {
			if(isl[i]) {
				lowers[l] = images[i];
				l++;
			}else {
				uppers[u] = images[i];
				u++;
			}
		}
		int main = 0;
		for (int i = 0; i < uppers.length; i++) {
			images[main] = uppers[i];
			main++;
		}
		for (int j = 0; j < lowers.length; j++) {
			images[main] = lowers[j];
			main++;
		}
		chars = upper + lower;
		this.images = images;
		return true;
	}

	private BufferedImage getBgTile() {
		try {
			return ImageIO.read(new File(
					System.getProperty("user.dir") + "\\data\\paperTile.png"));
		} catch (IOException e) {
			return null;
		}
	}

	static final int w = 274;
	static final int h = 278;
	public static int W = 1;
	
	 BufferedImage mainImg;
	
	public BufferedImage getImg() {
		return mainImg;
	}
	
	public void update(String text, int max, boolean cut, boolean ww,
			int zoom, double distance, int wordSize,
			
			
			int gr, int gx, int gy, int gl) {
		
		String txt = text;
		String formtxt = txt;
		formtxt = formtxt.replaceAll("\\\\", "\u2216");
		formtxt = formtxt.replaceAll("/", "\u2215");
		formtxt = formtxt.replaceAll("\"", "\u2036");
		formtxt = formtxt.replaceAll(":", "\u205A");
		formtxt = formtxt.replaceAll("\t", "____");
		String wordWrap = "";
		String[] words = formtxt.split(" ");
		char[] cs;
		// WW 
		if(ww) {
			int lenght = 0;
			for (int i = 0; i < words.length; i++) {
				lenght += words[i].length()+1;
				if(!(lenght > max*distance)) {
					wordWrap += words[i] + " ";
				}else {
					wordWrap += "\n" + words[i] + " ";
					lenght = words[i].length()+1;
				}
			}
			cs = wordWrap.toCharArray();
		}else{
			cs = formtxt.toCharArray();
		}

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
			}else if((cs[i]+"").equals(" ")){
				px++;
			}else {
				int index = chars.indexOf(cs[i] + "");
				try {
					g.drawImage(images[index], (int) (px*nw/distance),
							(int) (py*nh + Math.random()*(nh/10)),
							(int) (nw + Math.random()*(nh/10)),
							(int) (nh + Math.random()*(nh/10)+nh/5),
							null);
					px++;
				} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
				}
			}
			if(px > (bx)-1 && !ww) {
				px = 0;
				py ++;
			}
		}
		
		drawGradient(main, g, gx/zoom, gy/zoom, gr/zoom, gl);

		g.dispose();
		mainImg = main;
		W = main.getWidth();
	}
	
	
	private void drawGradient(BufferedImage img, Graphics2D g, int x, int y, int radius, int l) {
		float[] dist = {.0f, 0.5f};
		if(radius < 10)
			radius = 10;
		RadialGradientPaint gradientPaint = new RadialGradientPaint(new Point(x, y),
				radius, dist, new Color[] {new Color(255,255,255,l), new Color(255,255,255,0)} ,CycleMethod.REFLECT);
		g.setPaint(gradientPaint);
	    g.fillOval(x-radius/2, y-radius/2, radius, radius);
	}
	 BufferedImage transform(int t, int x1, int x2) {

	        final double TH = Math.toRadians(t);
	        final int D = x2 - x1;
	        final int W = mainImg.getWidth();
	        final int H = mainImg.getHeight();

	        final int dD = (int) (D / (2 * TH) * Math.sin(2 * TH));
	        final int dH = (int) (D / TH * Math.pow(Math.sin(TH), 2));
	        final int pH = (int) ((W - x2) * Math.tan(2 * TH));

	        final int width = W - (D - dD);
	        final int height = (int) (H + dH + pH);

	        BufferedImage img2 = new BufferedImage(width, Math.abs(height), BufferedImage.TYPE_INT_ARGB);

	        for (int x = 0; x < x1; x++) {
	            for (int y = 0; y < H; y++) {
	            try {
	                int rgb = mainImg.getRGB(x, y);
	                img2.setRGB(x, y, rgb);
	            } catch (ArrayIndexOutOfBoundsException e) {
//	            	return getImg();
				}
	            }
	        }

	        for (int x = x1; x < x2; x++) {
	            for (int y = 0; y < H; y++) {
	            	try {
	                    int rgb = mainImg.getRGB(x, y);
	                    int dx = (int) (D / (2 * TH) * Math.sin(2 * (x-x1) * TH / D));
	                    int dy = (int) (D / TH * Math.pow(Math.sin((x-x1) * TH / D), 2));
	                    img2.setRGB(x1 + dx, y + dy, rgb);
						
					} catch (ArrayIndexOutOfBoundsException e) {
//		            	return getImg();
					}
	            }
	        }

	        for (int x = x2; x < W; x++) {
	            for (int y = 0; y < H; y++) {
	            	try {
	                int rgb = mainImg.getRGB(x, y);
	                int dp = (int) ((x - x2) * Math.tan(2 * TH));
	                img2.setRGB(x - (D - dD), y + dH + dp, rgb);
	            } catch (ArrayIndexOutOfBoundsException e) {
//	            	return getImg();
				}
	            }
	        }

	        return img2;
	    }
}
