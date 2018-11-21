/*
 * Copyright 2003,2004,2006 Jeremias Maerki.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jemmy.services.barcode.code.postnet;

import com.jemmy.services.barcode.code.ExtBitmapCanvasProvider;
import com.jemmy.services.barcode.model.enums.TextAlignEnum;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.BaselineAlignment;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractVariableHeightLogicHandler;
import org.krysalis.barcode4j.impl.HeightVariableBarcodeBean;
import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
import org.krysalis.barcode4j.output.Canvas;
import org.krysalis.barcode4j.output.CanvasProvider;

import java.awt.*;

/**
 * Logic Handler for POSTNET.
 * 
 * @author Chris Dolphy
 * @version $Id: POSTNETLogicHandler.java,v 1.4 2008/05/13 13:00:44 jmaerki Exp $
 */
@Slf4j
public class ExtPOSTNETLogicHandler extends AbstractVariableHeightLogicHandler {
    private ExtBitmapCanvasProvider canvasProvider;
    private TextAlignEnum textAlignEnum=TextAlignEnum.CENTER;
    private boolean isExtCanvasProvider;
    /**
     * Constructor
     * @param bcBean the barcode implementation class
     * @param canvas the canvas to paint to
     */
    public ExtPOSTNETLogicHandler(HeightVariableBarcodeBean bcBean, Canvas canvas, CanvasProvider canvasProvider) {
        super(bcBean, canvas);
        isExtCanvasProvider=canvasProvider instanceof ExtBitmapCanvasProvider;
        if(isExtCanvasProvider){
            this.canvasProvider=(ExtBitmapCanvasProvider)canvasProvider;
            this.textAlignEnum=this.canvasProvider.textAlign();

        }
    }

    private double getStartY() {
        if (bcBean.hasQuietZone()) {
            return bcBean.getVerticalQuietZone();
        } else {
            return 0.0;
        }
    }            

    /** @see org.krysalis.barcode4j.ClassicBarcodeLogicHandler */
    @Override
    public void startBarcode(String msg, String formattedMsg) {
        super.startBarcode(msg, formattedMsg);
        y = getStartY();
    }

    /**
     * @see org.krysalis.barcode4j.ClassicBarcodeLogicHandler#addBar(boolean, int)
     */
    public void addBar(boolean black, int height) {
        POSTNETBean pnBean = (POSTNETBean)bcBean;
        final double w = black ? bcBean.getBarWidth(1) : bcBean.getBarWidth(-1);
        final double h = bcBean.getBarHeight(height);
        final BaselineAlignment baselinePosition = pnBean.getBaselinePosition();

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
        if (black) {
            if (bcBean.getMsgPosition() == HumanReadablePlacement.HRP_TOP) {
                if (baselinePosition == BaselineAlignment.ALIGN_TOP) {
                    canvas.drawRectWH(x, y + bcBean.getHumanReadableHeight(), w, h);
                } else if (baselinePosition == BaselineAlignment.ALIGN_BOTTOM) {
                    canvas.drawRectWH(x, y + bcBean.getHeight() - h, w, h);
                }
            } else {
                if (baselinePosition == BaselineAlignment.ALIGN_TOP) {
                    canvas.drawRectWH(x, y, w, h);
                } else if (baselinePosition == BaselineAlignment.ALIGN_BOTTOM) {
                    canvas.drawRectWH(x, y + bcBean.getBarHeight() - h, w, h);
                } 
            }
        }
        if(isExtCanvasProvider){
            canvasProvider.resetColor(oldColor);
        }
        x += w;
    }

}
