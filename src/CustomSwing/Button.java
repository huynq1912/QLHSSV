/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomSwing;

/**
 *
 * @author khoa2
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;



public class Button extends JButton {

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        createImageShadow();
        repaint();
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        createImageShadow();
        repaint();
    }

    public void setRippleColor(Color color) {
        rippleEffect.setRippleColor(color);
    }

    public Color getRippleColor() {
        return rippleEffect.getRippleColor();
    }

    private int round = 10;
    private Color shadowColor = new Color(170, 170, 170);
    private BufferedImage imageShadow;
    private final Insets shadowSize = new Insets(2, 5, 8, 5);
    private final RippleEffect rippleEffect = new RippleEffect(this);

    public Button() {
        setBorder(new EmptyBorder(10, 12, 15, 12));
        setContentAreaFilled(false);
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(80, 80, 80));
        rippleEffect.setRippleColor(new Color(220, 220, 220));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double width = getWidth() - (shadowSize.left + shadowSize.right);
        double height = getHeight() - (shadowSize.top + shadowSize.bottom);
        double x = shadowSize.left;
        double y = shadowSize.top;
        //  Create Shadow Image
        g2.drawImage(imageShadow, 0, 0, null);
        //  Create Background Color
        g2.setColor(getBackground());
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, round, round));
        g2.fill(area);
        rippleEffect.reder(grphcs, area);
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        createImageShadow();
    }

    private void createImageShadow() {
        int height = getHeight();
        int width = getWidth();
        if (width > 0 && height > 0) {
            imageShadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageShadow.createGraphics();
            BufferedImage img = createShadow();
            if (img != null) {
                g2.drawImage(createShadow(), 0, 0, null);
            }
            g2.dispose();
        }
    }

    private BufferedImage createShadow() {
        int width = getWidth() - (shadowSize.left + shadowSize.right);
        int height = getHeight() - (shadowSize.top + shadowSize.bottom);
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fill(new RoundRectangle2D.Double(0, 0, width, height, round, round));
            g2.dispose();
            return new ShadowRenderer(5, 0.3f, shadowColor).createShadow(img);
        } else {
            return null;
        }
    }
}


class ShadowRenderer {

    private int size = 5;
    private float opacity = 0.5f;
    private Color color = Color.BLACK;

    public ShadowRenderer() {
        this(5, 0.5f, Color.BLACK);
    }

    public ShadowRenderer(final int size, final float opacity, final Color color) {
        this.size = size;
        this.opacity = opacity;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public float getOpacity() {
        return opacity;
    }

    public int getSize() {
        return size;
    }

    public BufferedImage createShadow(final BufferedImage image) {
        int shadowSize = size * 2;
        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();
        int dstWidth = srcWidth + shadowSize;
        int dstHeight = srcHeight + shadowSize;
        int left = size;
        int right = shadowSize - left;
        int yStop = dstHeight - right;
        int shadowRgb = color.getRGB() & 0x00FFFFFF;
        int[] aHistory = new int[shadowSize];
        int historyIdx;
        int aSum;
        BufferedImage dst = new BufferedImage(dstWidth, dstHeight,
                BufferedImage.TYPE_INT_ARGB);
        int[] dstBuffer = new int[dstWidth * dstHeight];
        int[] srcBuffer = new int[srcWidth * srcHeight];
        GraphicsUtilities.getPixels(image, 0, 0, srcWidth, srcHeight, srcBuffer);
        int lastPixelOffset = right * dstWidth;
        float hSumDivider = 1.0f / shadowSize;
        float vSumDivider = opacity / shadowSize;
        int[] hSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < hSumLookup.length; i++) {
            hSumLookup[i] = (int) (i * hSumDivider);
        }
        int[] vSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < vSumLookup.length; i++) {
            vSumLookup[i] = (int) (i * vSumDivider);
        }
        int srcOffset;
        for (int srcY = 0, dstOffset = left * dstWidth; srcY < srcHeight; srcY++) {
            for (historyIdx = 0; historyIdx < shadowSize;) {
                aHistory[historyIdx++] = 0;
            }
            aSum = 0;
            historyIdx = 0;
            srcOffset = srcY * srcWidth;
            for (int srcX = 0; srcX < srcWidth; srcX++) {
                int a = hSumLookup[aSum];
                dstBuffer[dstOffset++] = a << 24;
                aSum -= aHistory[historyIdx];
                a = srcBuffer[srcOffset + srcX] >>> 24;
                aHistory[historyIdx] = a;
                aSum += a;
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
            for (int i = 0; i < shadowSize; i++) {
                int a = hSumLookup[aSum];
                dstBuffer[dstOffset++] = a << 24;
                aSum -= aHistory[historyIdx];
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }

        for (int x = 0, bufferOffset = 0; x < dstWidth; x++, bufferOffset = x) {
            aSum = 0;
            for (historyIdx = 0; historyIdx < left;) {
                aHistory[historyIdx++] = 0;
            }
            for (int y = 0; y < right; y++, bufferOffset += dstWidth) {
                int a = dstBuffer[bufferOffset] >>> 24;
                aHistory[historyIdx++] = a;
                aSum += a;
            }
            bufferOffset = x;
            historyIdx = 0;
            for (int y = 0; y < yStop; y++, bufferOffset += dstWidth) {
                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb;
                aSum -= aHistory[historyIdx];
                a = dstBuffer[bufferOffset + lastPixelOffset] >>> 24;
                aHistory[historyIdx] = a;
                aSum += a;
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
            for (int y = yStop; y < dstHeight; y++, bufferOffset += dstWidth) {
                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb;
                aSum -= aHistory[historyIdx];
                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }
        GraphicsUtilities.setPixels(dst, 0, 0, dstWidth, dstHeight, dstBuffer);
        return dst;
    }
}


class RippleEffect {

    private final Component component;
    private Color rippleColor = new Color(255, 255, 255);
    private List<Effect> effects;

    public RippleEffect(Component component) {
        this.component = component;
        init();
    }

    private void init() {
        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addEffect(e.getPoint());
                }
            }
        });
    }

    public void addEffect(Point location) {
        effects.add(new Effect(component, location));
    }

    public void reder(Graphics g, Shape contain) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect != null) {
                effect.render(g2, contain);
            }
        }
    }

    private class Effect {

        private final Component component;
        private final Point location;
        private Animator animator;
        private float animate;

        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init();
        }

        private void init() {
            animator = new Animator(500, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    component.repaint();
                }

                @Override
                public void end() {
                    effects.remove(Effect.this);
                }
            });
            animator.setResolution(5);
            animator.setDeceleration(.5f);
            animator.start();
        }

        public void render(Graphics2D g2, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D()))));
            g2.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(area);
        }

        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            Shape shape = new Ellipse2D.Double(x - s, y - s, s * 2, s * 2);
            return shape;
        }

        private double getSize(Rectangle2D rec) {
            double size;
            if (rec.getWidth() > rec.getHeight()) {
                if (location.getX() < rec.getWidth() / 2) {
                    size = rec.getWidth() - location.getX();
                } else {
                    size = location.getX();
                }
            } else {
                if (location.getY() < rec.getHeight() / 2) {
                    size = rec.getHeight() - location.getY();
                } else {
                    size = location.getY();
                }
            }
            return size + (size * 0.1f);
        }
    }

    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    }

    public Color getRippleColor() {
        return rippleColor;
    }
}


class GraphicsUtilities {

    private GraphicsUtilities() {
    }

    // Returns the graphics configuration for the primary screen
    private static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static BufferedImage createColorModelCompatibleImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        return new BufferedImage(cm,
                cm.createCompatibleWritableRaster(image.getWidth(),
                        image.getHeight()),
                cm.isAlphaPremultiplied(), null);
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
        return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }

    public static BufferedImage createCompatibleImage(BufferedImage image,
            int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height,
                image.getTransparency());
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height);
    }

    public static BufferedImage createCompatibleTranslucentImage(int width,
            int height) {
        return getGraphicsConfiguration().createCompatibleImage(width, height,
                Transparency.TRANSLUCENT);
    }

    public static BufferedImage loadCompatibleImage(URL resource)
            throws IOException {
        BufferedImage image = ImageIO.read(resource);
        return toCompatibleImage(image);
    }

    public static BufferedImage toCompatibleImage(BufferedImage image) {
        if (image.getColorModel().equals(
                getGraphicsConfiguration().getColorModel())) {
            return image;
        }
        BufferedImage compatibleImage
                = getGraphicsConfiguration().createCompatibleImage(
                        image.getWidth(), image.getHeight(),
                        image.getTransparency());
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return compatibleImage;
    }

    public static BufferedImage createThumbnailFast(BufferedImage image,
            int newSize) {
        float ratio;
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > height) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than"
                        + " the image width");
            } else if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must"
                        + " be greater than 0");
            }
            ratio = (float) width / (float) height;
            width = newSize;
            height = (int) (newSize / ratio);
        } else {
            if (newSize >= height) {
                throw new IllegalArgumentException("newSize must be lower than"
                        + " the image height");
            } else if (newSize <= 0) {
                throw new IllegalArgumentException("newSize must"
                        + " be greater than 0");
            }
            ratio = (float) height / (float) width;
            height = newSize;
            width = (int) (newSize / ratio);
        }
        BufferedImage temp = createCompatibleImage(image, width, height);
        Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    }

    public static BufferedImage createThumbnailFast(BufferedImage image,
            int newWidth, int newHeight) {
        if (newWidth >= image.getWidth()
                || newHeight >= image.getHeight()) {
            throw new IllegalArgumentException("newWidth and newHeight cannot"
                    + " be greater than the image"
                    + " dimensions");
        } else if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must"
                    + " be greater than 0");
        }
        BufferedImage temp = createCompatibleImage(image, newWidth, newHeight);
        Graphics2D g2 = temp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, temp.getWidth(), temp.getHeight(), null);
        g2.dispose();
        return temp;
    }

    public static BufferedImage createThumbnail(BufferedImage image,
            int newSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean isWidthGreater = width > height;
        if (isWidthGreater) {
            if (newSize >= width) {
                throw new IllegalArgumentException("newSize must be lower than"
                        + " the image width");
            }
        } else if (newSize >= height) {
            throw new IllegalArgumentException("newSize must be lower than"
                    + " the image height");
        }
        if (newSize <= 0) {
            throw new IllegalArgumentException("newSize must"
                    + " be greater than 0");
        }
        float ratioWH = (float) width / (float) height;
        float ratioHW = (float) height / (float) width;
        BufferedImage thumb = image;
        do {
            if (isWidthGreater) {
                width /= 2;
                if (width < newSize) {
                    width = newSize;
                }
                height = (int) (width / ratioWH);
            } else {
                height /= 2;
                if (height < newSize) {
                    height = newSize;
                }
                width = (int) (height / ratioHW);
            }
            BufferedImage temp = createCompatibleImage(image, width, height);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();
            thumb = temp;
        } while (newSize != (isWidthGreater ? width : height));
        return thumb;
    }

    public static BufferedImage createThumbnail(BufferedImage image,
            int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (newWidth >= width || newHeight >= height) {
            throw new IllegalArgumentException("newWidth and newHeight cannot"
                    + " be greater than the image"
                    + " dimensions");
        } else if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("newWidth and newHeight must"
                    + " be greater than 0");
        }
        BufferedImage thumb = image;
        do {
            if (width > newWidth) {
                width /= 2;
                if (width < newWidth) {
                    width = newWidth;
                }
            }
            if (height > newHeight) {
                height /= 2;
                if (height < newHeight) {
                    height = newHeight;
                }
            }
            BufferedImage temp = createCompatibleImage(image, width, height);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();
            thumb = temp;
        } while (width != newWidth || height != newHeight);
        return thumb;
    }

    public static int[] getPixels(BufferedImage img,
            int x, int y, int w, int h, int[] pixels) {
        if (w == 0 || h == 0) {
            return new int[0];
        }
        if (pixels == null) {
            pixels = new int[w * h];
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length"
                    + " >= w*h");
        }
        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB) {
            Raster raster = img.getRaster();
            return (int[]) raster.getDataElements(x, y, w, h, pixels);
        }
        // Unmanages the image
        return img.getRGB(x, y, w, h, pixels, 0, w);
    }

    public static void setPixels(BufferedImage img,
            int x, int y, int w, int h, int[] pixels) {
        if (pixels == null || w == 0 || h == 0) {
            return;
        } else if (pixels.length < w * h) {
            throw new IllegalArgumentException("pixels array must have a length"
                    + " >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB) {
            WritableRaster raster = img.getRaster();
            raster.setDataElements(x, y, w, h, pixels);
        } else {
            // Unmanages the image
            img.setRGB(x, y, w, h, pixels, 0, w);
        }
    }
}
