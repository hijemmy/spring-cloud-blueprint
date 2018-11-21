package com.jemmy.services.barcode.code;

import org.krysalis.barcode4j.TextAlignment;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;

/**
 * @author Jemmy
 */
public class ExtJava2DCanvasProvider extends Java2DCanvasProvider {
    private Color textColor;
    private Graphics2D g2d;
    private float textOffset;

    public ExtJava2DCanvasProvider(Graphics2D g2d, int orientation,Color textColor,float textOffset) {
        super(g2d, orientation);
        this.textColor=textColor;
        this.g2d=g2d;
        this.textOffset=textOffset;
    }

    @Override
    public void deviceText(String text, double x1, double x2, double y1, String fontName, double fontSize, TextAlignment textAlign) {
        Font font = new Font(fontName, Font.PLAIN,
                (int)Math.round(fontSize));
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc, text);

        final float textwidth = (float)gv.getLogicalBounds().getWidth();
        final float distributableSpace = (float)((x2 - x1) - textwidth);
        final float intercharSpace;
        if (gv.getNumGlyphs() > 1) {
            intercharSpace = distributableSpace / (gv.getNumGlyphs() - 1);
        } else {
            intercharSpace = 0.0f;
        }
        final float indent;
        if (textAlign == TextAlignment.TA_JUSTIFY) {
            if (text.length() > 1) {
                indent = 0.0f;
            } else {
                //Center if only one character
                indent = distributableSpace / 2;
            }
        } else if (textAlign == TextAlignment.TA_CENTER) {
            indent = distributableSpace / 2;
        } else if (textAlign == TextAlignment.TA_RIGHT) {
            indent = distributableSpace;
        } else {
            indent = 0.0f;
        }
        Font oldFont = g2d.getFont();
        Color oldFgColor=g2d.getColor();
        g2d.setFont(font);
        g2d.setColor(textColor);
        if (textAlign == TextAlignment.TA_JUSTIFY) {
            //move the individual glyphs
            for (int i = 0; i < gv.getNumGlyphs(); i++) {
                Point2D point = gv.getGlyphPosition(i);
                point.setLocation(point.getX() + i * intercharSpace, point.getY());
                gv.setGlyphPosition(i, point);

            }
        }
        //调整此处的值,以变更文本位置
        g2d.drawGlyphVector(gv, (float)x1 + indent, (float)y1+textOffset);
        g2d.setFont(oldFont);
        g2d.setColor(oldFgColor);
    }

}
