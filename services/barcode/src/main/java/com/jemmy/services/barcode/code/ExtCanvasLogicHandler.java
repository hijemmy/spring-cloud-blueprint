package com.jemmy.services.barcode.code;

import com.jemmy.services.barcode.model.enums.TextAlignEnum;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.TextAlignment;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.DefaultCanvasLogicHandler;
import org.krysalis.barcode4j.impl.DrawingUtil;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;
import org.krysalis.barcode4j.tools.MessagePatternUtil;

import java.awt.*;

/**
 * @author Jemmy
 */
@Slf4j
public class ExtCanvasLogicHandler extends DefaultCanvasLogicHandler {
    private ExtBitmapCanvasProvider canvasProvider;
    private double x = 0.0;
    private double y;
    private String formattedMsg;
    private TextAlignEnum textAlignEnum=TextAlignEnum.CENTER;
    private boolean isExtCanvasProvider;
    /**
     * Main constructor.
     *
     * @param bcBean the barcode implementation class
     * @param canvas the canvas to paint to
     */
    public ExtCanvasLogicHandler(AbstractBarcodeBean bcBean, Canvas canvas,CanvasProvider canvasProvider) {
       super(bcBean, canvas);
        isExtCanvasProvider=canvasProvider instanceof ExtBitmapCanvasProvider;
        if(isExtCanvasProvider){
            this.canvasProvider=(ExtBitmapCanvasProvider)canvasProvider;
            this.textAlignEnum=this.canvasProvider.textAlign();

        }
    }



    /** {@inheritDoc} */
    @Override
    public void startBarcode(String msg, String formattedMsg) {
        this.formattedMsg = MessagePatternUtil.applyCustomMessagePattern(
                formattedMsg, bcBean.getPattern());

        //Calculate extents
        this.dimensions = bcBean.calcDimensions(msg);

        canvas.establishDimensions(dimensions);
        x = getStartX();
        y = getStartY();
    }


    /** {@inheritDoc} */
    @Override
    public void addBar(boolean black, int width) {
        final double w = bcBean.getBarWidth(width);
        Color oldColor=null;
        if(isExtCanvasProvider){
            if (black) {
                log.debug("设置条码颜色");
                oldColor=canvasProvider.changeBarColor();
            }else {
                log.debug("设置空条码颜色");
                oldColor=canvasProvider.changeEmptyBarColor();
            }
        }
        canvas.drawRectWH(x, y, w, bcBean.getBarHeight());
        if(isExtCanvasProvider){
            canvasProvider.resetColor(oldColor);
        }
        x += w;
    }



    /** {@inheritDoc} */
    @Override
    public void endBarcode() {
        if (bcBean.getMsgPosition() == HumanReadablePlacement.HRP_NONE) {
            //nop
        } else {
            double ty = getTextBaselinePosition();
            DrawingUtil.drawText(canvas, bcBean, formattedMsg,
                    getStartX(), x, ty, TextAlignment.byName(textAlignEnum.getAlign()));
        }
    }

}
