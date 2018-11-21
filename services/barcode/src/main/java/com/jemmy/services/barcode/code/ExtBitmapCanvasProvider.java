package com.jemmy.services.barcode.code;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.model.enums.TextAlignEnum;
import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.TextAlignment;
import org.krysalis.barcode4j.output.AbstractCanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoderRegistry;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Jemmy
 */
public class ExtBitmapCanvasProvider extends AbstractCanvasProvider {

    /**
     * 背景颜色
     */
    private Color bgColor;
    /**
     * 文本背景色
     */
    private Color textColor;
    private Color barColor;
    private Color emptyColor;
    private String mime;
    private OutputStream out;
    private int resolution;
    private int imageType;
    private boolean antiAlias;
    private BufferedImage image;
    private Java2DCanvasProvider delegate;
    private float textOffset;
    private boolean showBoarder;
    private TextAlignEnum textAlign;

    /**
     * Creates a new BitmapCanvasProvider.
     * @param out OutputStream to write to
     * @param mime MIME type of the desired output format (ex. "image/png")
     * @param imageType the desired image type (Values: BufferedImage.TYPE_*)
     * @param antiAlias true if anti-aliasing should be enabled
     */
    public ExtBitmapCanvasProvider(OutputStream out, String mime, int imageType, boolean antiAlias, BarcodeRequestDto dto) {
        super(dto.getBarCode().getOritention());
        this.out = out;
        this.mime = mime;
        this.resolution = dto.getBarCode().getResolution();
        this.imageType = imageType;
        this.antiAlias = antiAlias;
        this.bgColor=dto.getBarBorder().getBgColor();
        this.textColor=dto.getText().getColor();
        this.textOffset=dto.getText().getOffset();
        this.showBoarder=dto.getBarBorder().isShowBorder();
        this.barColor=dto.getBar().getColor();
        this.emptyColor=dto.getBar().getEmptyColor();
        this.textAlign=dto.getText().getAlign();
    }

    /**
     * Call this method to finish any pending operations after the
     * BarcodeGenerator has finished its work.
     * @throws IOException in case of an I/O problem
     */
    public void finish() throws IOException {
        this.image.flush();
        if (this.out != null) {
            final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
            encoder.encode(this.image, out, mime, resolution);
        }
    }

    /**
     * Returns the buffered image that is used to paint the barcode on.
     * @return the image.
     */
    public BufferedImage getBufferedImage() {
        return this.image;
    }

    /** {@inheritDoc} */
    @Override
    public void establishDimensions(BarcodeDimension dim) {
        super.establishDimensions(dim);
        this.image = BitmapBuilder.prepareImage(dim, getOrientation(),
                this.resolution, this.imageType);
        final Graphics2D g2d=prepareGraphics2D(this.image, dim, getOrientation(),this.antiAlias);
        this.delegate = new ExtJava2DCanvasProvider(g2d, getOrientation(),textColor,textOffset);
        this.delegate.establishDimensions(dim);
    }

    public Color changeBarColor(){
        Graphics2D g2d=this.delegate.getGraphics2D();
        Color oldColor=g2d.getColor();
        g2d.setColor(barColor);
        return oldColor;
    }

    public Color changeEmptyBarColor(){
        Graphics2D g2d=this.delegate.getGraphics2D();
        Color oldColor=g2d.getColor();
        g2d.setColor(emptyColor);
        return oldColor;
    }

    public void resetColor(Color oldColor){
        this.delegate.getGraphics2D().setColor(oldColor);
    }

    private void paintBoarder(Graphics2D g2d){
        Stroke oldStroke=g2d.getStroke();
        float strokeWidth=0.1f;
        Stroke newStroke=new BasicStroke(strokeWidth);
        Color oldColor=g2d.getColor();
        g2d.setStroke(newStroke);
        g2d.setColor(Color.GREEN);
        g2d.draw(new Rectangle2D.Double(0,0,image.getWidth(),image.getHeight()));
        g2d.setStroke(oldStroke);
        g2d.setColor(oldColor);

    }

    /** {@inheritDoc} */
    public void deviceFillRect(double x, double y, double w, double h) {
        this.delegate.deviceFillRect(x, y, w, h);
    }

    public TextAlignEnum textAlign(){
        return this.textAlign;
    }

    /** {@inheritDoc} */
    public void deviceText(String text,
                           double x1, double x2, double y1,
                           String fontName, double fontSize, TextAlignment textAlign) {
        this.delegate.deviceText(text, x1, x2, y1, fontName, fontSize, textAlign);
    }


    private Graphics2D prepareGraphics2D(BufferedImage image,
                                               BarcodeDimension dim, int orientation,
                                               boolean antiAlias) {
        Graphics2D g2d = image.createGraphics();
        if (antiAlias) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setBackground(bgColor);
        g2d.setColor(textColor);

        g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
        g2d.scale(image.getWidth() / dim.getWidthPlusQuiet(orientation),
                image.getHeight() / dim.getHeightPlusQuiet(orientation));

        if(showBoarder){
            paintBoarder(g2d);
        }
        return g2d;
    }
}
