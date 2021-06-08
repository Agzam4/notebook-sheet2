package TextPrinter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FontCreator {

	public static void main(String[] args) {
		
		/* Reading Image */
		BufferedImage input;
		try {
			input = ImageIO.read(new File("fontCreator\\BLANKTEST.png"));
			System.out.println("Input image size: " + input.getWidth() + "x" + input.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		/* Search the darkest and lightest colors */
		int darkest = 255;
		int lightest = 0;
		for (int x = 0; x < input.getWidth(); x++) {
			for (int y = 0; y < input.getHeight(); y++) {
				Color color = new Color(input.getRGB(x, y));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				
				if(red/25 < blue/25 && green/25 < blue/25) {
					input.setRGB(x, y, Color.BLUE.getRGB());
				}else {
					int gray = (red+green+blue)/3;
					if(lightest < gray)
						lightest = gray;
					if(darkest > gray)
						darkest = gray;
				}
			}
		}
		int average = (int) ((darkest*0.7+lightest/0.7)/2);
		System.out.println("Color bounds: " + darkest + "-" + lightest);

		for (int x = 0; x < input.getWidth(); x++) {
			for (int y = 0; y < input.getHeight(); y++) {
				Color color = new Color(input.getRGB(x, y));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				int gray = (red+green+blue)/3;
				if(gray > average) {
					input.setRGB(x, y, Color.WHITE.getRGB());
				}
				if(gray < average) {
					input.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		
		/*Cutting*/
		int sx = 0;
		int sy = 0;

		// X
		for (int x = 0; x < input.getWidth(); x++) {
			boolean hasBlack = false;
			for (int y = 0; y < input.getHeight(); y++) {
				if(input.getRGB(x, y) == Color.BLACK.getRGB()) {
					hasBlack = true;
					y = input.getHeight();
				}
			}
			if(hasBlack) {
				break;
			}else {
				sx = x+1;
			}
		}
		// Y
		for (int y = 0; y < input.getHeight(); y++) {
			boolean hasBlack = false;
			for (int x = 0; x < input.getWidth(); x++) {
				if(input.getRGB(x, y) == Color.BLACK.getRGB()) {
					hasBlack = true;
					x = input.getHeight();
				}
			}
			if(hasBlack) {
				break;
			}else {
				sy = y+1;
			}
		}
		BufferedImage cuttingImg = new BufferedImage(input.getWidth()-sx, input.getHeight()-sy,
				BufferedImage.TYPE_BYTE_GRAY);

		Graphics2D g = (Graphics2D) cuttingImg.getGraphics();
		g.drawImage(input, -sx, -sy, null);
		g.dispose();

		try {
			ImageIO.write(input, "png", new File("fontCreator\\outout.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ImageIO.write(cuttingImg, "png", new File("fontCreator\\cutting.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
