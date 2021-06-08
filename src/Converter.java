package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Converter {

	public static final String abc = "יצףךו¸םדרשחץתפûגאןנמכהז‎ÿקסלטעüב‏";
	static final int w = 274;
	static final int h = 278;
	
	public static void main(String[] args) {
		File[] fss = (new File(System.getProperty("user.dir") + "\\converter\\v3")).listFiles();
		BufferedImage[] images = new BufferedImage[fss.length];
		for (int i = 0; i < fss.length; i++) {
			try {
				BufferedImage img = ImageIO.read(fss[i]);
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
				ImageIO.write(images[i], "png", fss[i]);
				System.out.println(i + " - comlited");
			} catch (IOException e) {
//				e.printStackTrace();
				System.err.println();
			}
		}
	}

}
