package com.jemmy.services.barcode.code.fourstate;

import com.jemmy.services.barcode.code.ExtBitmapCanvasProvider;
import com.jemmy.services.barcode.model.enums.TextAlignEnum;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractVariableHeightLogicHandler;
import org.krysalis.barcode4j.impl.HeightVariableBarcodeBean;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

import java.awt.*;

@Slf4j
public class ExtFourStateLogicHandler extends AbstractVariableHeightLogicHandler {
    private ExtBitmapCanvasProvider canvasProvider;
    private TextAlignEnum textAlignEnum=TextAlignEnum.CENTER;
    private boolean isExtCanvasProvider;
    public ExtFourStateLogicHandler(HeightVariableBarcodeBean bcBean, Canvas canvas, CanvasProvider canvasProvider) {
        super(bcBean, canvas);
        isExtCanvasProvider=canvasProvider instanceof ExtBitmapCanvasProvider;
        if(isExtCanvasProvider){
            this.canvasProvider=(ExtBitmapCanvasProvider)canvasProvider;
            this.textAlignEnum=this.canvasProvider.textAlign();

        }
    }


    private double getStartY() {
        double y = 0.0;
        if (bcBean.hasQuietZone()) {
            y += bcBean.getVerticalQuietZone();
        }
        if (bcBean.getMsgPosition() == HumanReadablePlacement.HRP_TOP) {
            y += bcBean.getHumanReadableHeight();
        }
        return y;
    }

    /** {@inheritDoc} */
    public void addBar(boolean black, int height) {
        final double w = bcBean.getBarWidth(1);
        final double h = bcBean.getBarHeight(height);

        final double middle = bcBean.getBarHeight() / 2;
        double y1;
        switch (height) {
            case 0:
            case 2:
                y1 = middle - (bcBean.getBarHeight(0) / 2);
                break;
            case 1:
            case 3:
                y1 = middle - (bcBean.getBarHeight(3) / 2);
                break;
            default:
                throw new RuntimeException("Bug!");
        }
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
        canvas.drawRectWH(x, getStartY() + y1, w, h);
        if(isExtCanvasProvider){
            canvasProvider.resetColor(oldColor);
        }
        x += w + bcBean.getBarWidth(-1);
    }
}
