
package triangle;

import resizable.ResizableImage;
import java.util.Random;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;
    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + " size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();

        Random random = new Random();
        gBuffer.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));


        int x1 = size.width / 2;
        int y1 = 0;
        int x2 = 0;
        int y2 = size.height;
        int x3 = size.width;
        int y3 = size.height;

        drawRecursiveTriangles(gBuffer, x1, y1, x2, y2, x3, y3, 5);

        gBuffer.dispose();
        return bufferedImage;
    }

    private void drawRecursiveTriangles(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3, int depth) {

        Random random = new Random();
        g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));



        if (depth == 0) {
            int[] xPoints = {x1, x2, x3};
            int[] yPoints = {y1, y2, y3};
            g.fillPolygon(xPoints, yPoints, 3);
            return;
        }


        int mx1 = (x1 + x2) / 2;
        int my1 = (y1 + y2) / 2;
        int mx2 = (x2 + x3) / 2;
        int my2 = (y2 + y3) / 2;
        int mx3 = (x3 + x1) / 2;
        int my3 = (y3 + y1) / 2;


        int[] xPoints = {mx1, mx2, mx3};
        int[] yPoints = {my1, my2, my3};
        g.fillPolygon(xPoints, yPoints, 3);


        drawRecursiveTriangles(g, x1, y1, mx1, my1, mx3, my3, depth - 1);
        drawRecursiveTriangles(g, mx1, my1, x2, y2, mx2, my2, depth - 1);
        drawRecursiveTriangles(g, mx3, my3, mx2, my2, x3, y3, depth - 1);
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
