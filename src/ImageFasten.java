package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

public class ImageFasten {

	static final int ww = 274;
	static final int hh = 278;
	
	public static void main(String[] args) throws IOException {
		/** Create Font **/
		String names = "";
		File[] fs = new File("data\\v3+").listFiles();
		BufferedImage[] imgs = new BufferedImage[fs.length];
		for (int i = 0; i < imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(fs[i]);
				char[] name = fs[i].getName().toCharArray();
				names += name[0] == 'l' ? 
						(int)((name[1]+"").toLowerCase().toCharArray()[0]) :
							(int)((name[1]+"").toUpperCase().toCharArray()[0]);
				names += (char)(5);
				System.out.print(name[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		names += (char)(5);
		names += (char)(5);
		try (FileWriter writer = new FileWriter(new File("test\\names.font"))) {
			writer.write(names);
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		/** Create Font **/
		BufferedImage main = combineImages(imgs);
		System.out.println("Craeting combinedImagesAll");
		ImageIO.write(main, "png", new File("test\\combinedImagesAll.png"));
		System.out.println("Ready: combinedImagesAll");
	}
	public static void go(JProgressBar pb, JLabel pp, JTextPane info, File input) {
		info.setContentType("text/bbcode");//TODO
//		for (int imgs = 1; imgs < 25; imgs++) {
//			int w = (int) Math.ceil(Math.sqrt(imgs));
//			int h = (int) Math.floor((imgs)/w)+1;
//			if(imgs > w*h) {
//				System.err.println(imgs + ": "  + w + "x" + h);
//			}else {
//				System.out.println(imgs + ": "  + w + "x" + h);
//			}
//		}
		
		/** Create Font **/
//		String names = "";
//		File[] fs = new File("data\\v3+").listFiles();
//		BufferedImage[] imgs = new BufferedImage[fs.length];
//		for (int i = 0; i < imgs.length; i++) {
//			try {
//				imgs[i] = ImageIO.read(fs[i]);
//				char[] name = fs[i].getName().toCharArray();
//				names += name[0] == 'l' ? 
//						(int)((name[1]+"").toLowerCase().toCharArray()[0]) :
//							(int)((name[1]+"").toUpperCase().toCharArray()[0]);
//				names += (char)(5);
//				System.out.print(name[1]);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		try (FileWriter writer = new FileWriter(new File("test\\names.font"))) {
//			writer.write(names);
//			writer.flush();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		try {
			/** Create Font **/
//			BufferedImage main = combineImages(imgs);
//			System.out.println("Craeting combinedImagesAll");
//			ImageIO.write(main, "png", new File("test\\combinedImagesAll.png"));
//			System.out.println("Ready: combinedImagesAll");

			/** Load Font TODO**/

			String infos = "";
			pb.setValue(0);
			
			BufferedImage font = ImageIO.read(input);
			int nw = font.getWidth()/ww;
			int nh = font.getHeight()/hh;

			byte[] all = Files.readAllBytes(Paths.get("data\\v4\\datafonts\\names.font"));
			String txt = new String(all);
			String[] fns = txt.split((char)(5)+"");
			
			pb.setMaximum(nw*nh*2);
			
			String db = "";
			for (int i = 0; i < fns.length; i++) {
				int mi = Integer.valueOf(fns[i]);
				String mii = (char)(mi) + "";
				db += (char)(mi);
				fns[i] = (isLower(mii)) ? "l" + mii.toLowerCase() : "u" + mii.toLowerCase();
			}
			
			BufferedImage[] imgs2 = unCombineBigImage(font, db.toCharArray(), pb, pp, info, infos);
			for (int i = 0; i < imgs2.length; i++) {
				try {
					ImageIO.write(imgs2[i], "png", new File("data\\v4\\datafonts\\" + fns[i] + ".png"));
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				pb.setValue(pb.getValue()+1);
				pp.setText((pb.getValue()*100)/pb.getMaximum() + "%");
				System.out.println("WritingUnC: " + i + "/" + imgs2.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
//		try {
//			BufferedImage img = combine3Images(ImageIO.read(new File("l0.png")),
//					ImageIO.read(new File("l1.png")),ImageIO.read(new File("l2.png")));
//			ImageIO.write(img,
//					"png", new File("test\\combined3Images.png"));
//			
////			BufferedImage[] is = unCombineImage(img);
////			ImageIO.write(is[0],
////					"png", new File("test\\uncombinedImage0.png"));
////			ImageIO.write(is[1],
////					"png", new File("test\\uncombinedImage1.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			BufferedImage img = combine2Images(ImageIO.read(new File("l0.png")),
//					ImageIO.read(new File("l1.png")));
//			ImageIO.write(img,
//					"png", new File("test\\combinedImages.png"));
//			
//			BufferedImage[] is = unCombineImage(img);
//			ImageIO.write(is[0],
//					"png", new File("test\\uncombinedImage0.png"));
//			ImageIO.write(is[1],
//					"png", new File("test\\uncombinedImage1.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		System.exit(0);
	}
	private static boolean isLower(String str) {
		return str.equals(str.toLowerCase());
	}
	

	public static BufferedImage combine2Images(BufferedImage img1, BufferedImage img2) {
//		long st = System.nanoTime();
		BufferedImage newImg = new BufferedImage(img1.getWidth(), img1.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) newImg.getGraphics();
		for (int y = 0; y < newImg.getHeight(); y++) {
			for (int x = 0; x < newImg.getWidth(); x++) {
				int alpha1 = (img1.getRGB(x, y) >> 24) & 0xFF; 
				int alpha2 = (img2.getRGB(x, y) >> 24) & 0xFF; 
				int alfa = (alpha1 > alpha2) ? alpha1 : alpha2;
				g.setColor(new Color(alpha1,alpha2,alfa));
				g.fillRect(x, y, 1, 1);
			}
		}
		g.dispose();
//		System.out.println((System.nanoTime() - st)/1000000000D);
		return newImg;
	}
	

	public static BufferedImage combine3Images(BufferedImage img1, BufferedImage img2, BufferedImage img3) {
//		long st = System.nanoTime();
		BufferedImage newImg = new BufferedImage(img1.getWidth(), img1.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) newImg.getGraphics();
		for (int y = 0; y < newImg.getHeight(); y++) {
			for (int x = 0; x < newImg.getWidth(); x++) {
				int alpha1 = (img1.getRGB(x, y) >> 24) & 0xFF; 
				int alpha2 = (img2.getRGB(x, y) >> 24) & 0xFF; 
				int alpha3 = (img3.getRGB(x, y) >> 24) & 0xFF; 
				g.setColor(new Color(alpha1,alpha2,alpha3));
				g.fillRect(x, y, 1, 1);
			}
		}
		g.dispose();
		//System.out.println((System.nanoTime() - st)/1000000000D);
		return newImg;
	}
	

	public static BufferedImage combineImages(BufferedImage[] imgs) {
		int nw = (int) Math.ceil(Math.sqrt(imgs.length/3));
		int nh = (int) Math.floor((imgs.length/3)/nw);
		nw++;
		System.out.println(nw + "x" + nh);
		BufferedImage main = new BufferedImage((nw+1)*ww, nh*hh, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) main.getGraphics();
		int px = 0;
		int py = 0;
		for (int i = 0; i < imgs.length; i+=3) {
			try {
				g.drawImage(combine3Images(imgs[i], imgs[i+1], imgs[i+2]), px*ww, py*hh, null);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			px++;
			if(px > nw) {
				px = 0;
				py++;
			}
//			System.out.println("Comb: " + i + "/" + imgs.length);
		}
		g.dispose();
		return main;
	}
	

	public static BufferedImage[] unCombineImage(BufferedImage img) {
//		long st = System.nanoTime();
		BufferedImage[] newImges = new BufferedImage[2];

		newImges[0] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		newImges[1] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = (Graphics2D) newImges[0].getGraphics();
		Graphics2D g2 = (Graphics2D) newImges[1].getGraphics();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int alpha1 = (c.getRed()); 
				int alpha2 = (c.getGreen()); 
				g1.setColor(new Color(0,0,0,alpha1));
				g1.fillRect(x, y, 1, 1);
				g2.setColor(new Color(0,0,0,alpha2));
				g2.fillRect(x, y, 1, 1);
			}
		}
		g1.dispose();
		g2.dispose();
//		System.out.println((System.nanoTime() - st)/1000000000D); 
		return newImges;
	}
	
	public static BufferedImage[] unCombine3Images(BufferedImage img) {
		BufferedImage[] newImges = new BufferedImage[3];//img.getWidth()img.getHeight()
		newImges[0] = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_ARGB);
		newImges[1] = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_ARGB);
		newImges[2] = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = (Graphics2D) newImges[0].getGraphics();
		Graphics2D g2 = (Graphics2D) newImges[1].getGraphics();
		Graphics2D g3 = (Graphics2D) newImges[2].getGraphics();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				g1.setColor(new Color(20,20,20,c.getRed()));
				g1.fillRect(x, y, 1, 1);
				g2.setColor(new Color(20,20,20,c.getGreen()));
				g2.fillRect(x, y, 1, 1);
				g3.setColor(new Color(20,20,20,c.getBlue()));
				g3.fillRect(x, y, 1, 1);
			}
		}
		g1.dispose();
		g2.dispose();
		g3.dispose();
		return newImges;
	}
	
	public static BufferedImage[] unCombineBigImage(BufferedImage img, char[] names,
			JProgressBar pb, JLabel pp, JTextPane info, String infos) {
//		info.setContentType("text/plain");
		info.setContentType("text/html");
		int max = pb.getMaximum();
		int nw = img.getWidth()/ww;
		int nh = img.getHeight()/hh;
		BufferedImage[] newImges = new BufferedImage[(nw*nh*3)];
		System.out.println("!!!! " + nw + "x" + nh);
		int px = 0;
		int py = 0;
		for (int i = 0; i < newImges.length+3; i+=3) {
			BufferedImage cuttenImg = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) cuttenImg.getGraphics();
			g.drawImage(img, -px*ww, -py*hh, null);
			g.dispose();
			
			BufferedImage[] imges3 = unCombine3Images(cuttenImg);
			try { 
				newImges[i] = imges3[0];
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try { 
				newImges[i+1] = imges3[1];
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try { 
				newImges[i+2] = imges3[2];
			} catch (ArrayIndexOutOfBoundsException e) {
			}
//			System.out.println("UnComb: " + i + "/" + newImges.length + " | " + px + " | " +
//					String.valueOf(names[i]) + " " +
//					String.valueOf(names[i+1]) + " " +
//					String.valueOf(names[i+2]) + " "); TODO

			try {
				System.out.print(String.valueOf(names[i]) + " " +
						String.valueOf(names[i+1]) + " " +
						String.valueOf(names[i+2]) + " ");
				pb.setValue(pb.getValue()+1);
				pp.setText((pb.getValue()*100)/max + "%");
				infos += "<b style='color:#41d399'>" + "Created: " +
						String.valueOf(names[i]) + " " +
						String.valueOf(names[i+1]) + " " +
						String.valueOf(names[i+2]) + "</b><br/>";
			} catch (ArrayIndexOutOfBoundsException e) {
//				return newImges;
			}
			info.setText(infos);
			px++;
			if(px > nw-1) {
				System.out.println();
				px = 0;
				py++;
			}
		}
		return newImges;
	}
}
