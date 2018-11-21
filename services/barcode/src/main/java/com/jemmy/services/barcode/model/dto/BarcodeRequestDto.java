/*
 * Copyright 2002-2004,2007 Jeremias Maerki or contributors to Barcode4J, as applicable
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
package com.jemmy.services.barcode.model.dto;

import com.jemmy.services.barcode.model.enums.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.awt.*;

/**
 * This is just a little helper bean for the JSP page.
 *
 * @author Jemmy
 * @version $Id: BarcodeRequestDto.java,v 1.6 2010/10/25 09:28:47 jmaerki Exp $
 */
@Data
@ApiModel
public class BarcodeRequestDto {
    /**
     * 文本
     */
    @ApiModelProperty(value = "文本")
    private Text text=new Text();
    /**
     * 码条
     */
    @ApiModelProperty(value = "码条")
    private Bar bar=new Bar();
    /**
     * 条码
     */
    @Valid
    @NotNull
    @ApiModelProperty(value = "条码")
    private BarCode barCode=new BarCode();

    /**
     * 外框
     */
    @NotNull
    @ApiModelProperty(value = "外框")
    private BarBorder barBorder=new BarBorder();
    /**
     * 返回格式
     */
    @NotNull
    @ApiModelProperty(value = "返回格式")
    private Return theReturn=new Return();


    /**
     * 返回格式
     */
    @Data
    @ApiModel
    public static class Return{
        /**
         * 返回的条码格式
         */
        @ApiModelProperty(value = "返回格式",allowableValues = "image/png,image/jpeg")
        private String format;
        private boolean svgEmbed;
        private boolean gray;


    }

    /**
     * 条码设置
     */
    @Data
    @ApiModel
    public static class BarCode{

        @ApiModelProperty(value = "旋转角度",allowableValues = "0,90,180,270")
        private Integer oritention=0;
        /**
         * 对齐
         */
        @ApiModelProperty(value = "对齐")
        private String align;
        /**
         * 条码类型
         */
        @NotNull
        @ApiModelProperty(required = true,value = "条码类型")
        private CodeTypeEnum type;
        /**
         * 条码内容
         */
        @NotEmpty
        @ApiModelProperty(required = true,value = "条码内容")
        private String code;

        @Range(min = 10,max = 2400)
        @ApiModelProperty(required = false,value = "分辨率",hidden = true)
        private Integer resolution=300;

        /***
         * 各种编码的属性配置
         */
        /**
         * 宽度
         */
        private String moduleWidth;
        /**
         *
         */
        private Float wideFactor;
        /**
         * 静区
         */
        private String quietZone;
        private String verticalQuietZone;
        private String intercharGapWidth;
        private CheckSumModeEnum checkSum;
        private String bearerBarWidth;
        private Boolean bearerBox;
        private String trackHeight;
        private String ascenderHeight;
        private String tallBarHeight;
        private String shortBarHeight;
        private BaselineAlignmentEnum baselineAlignment;
    }

    /**
     * 外框设置
     */
    @Data
    public static class BarBorder{
        /**
         * 高度
         */
        private String height;
        /**
         * 宽度
         */
        private String width;
        /**
         * 是否显示边框
         */
        private boolean showBorder=true;
        /**
         * 内边距,单位为像素
         */
        private float margin=0;
        /**
         * 背景色
         */
        private Color bgColor=Color.RED;

    }

    /**
     * 码条配置
     */
    @Data
    public static class Bar{
        /**
         * 条码颜色
         */
        private Color color=Color.BLUE;
        /**
         *空码条颜色
         */
        private Color emptyColor=Color.MAGENTA;
        /**
         * 单位码条宽度,单位为px
         */
        private Float width=1f;
    }


    /**
     * 文本配置
     */
    @Data
    public static class Text{
        /**
         *  仅支持 none,bop,bottom
         *  文本放置位置
         */
        @ApiModelProperty(value = "文本放置位置")
        private HumanReadablePlacementEnum placement;
        @ApiModelProperty(value = "文本对齐方式")
        private TextAlignEnum align=TextAlignEnum.LEFT;
        /**
         * 字体大小
         */
        @ApiModelProperty(value = "字体大小,单位为mm,cm,pt,mw",example ="1.3mm" )
        private String fontSize;
        /**
         * 字体颜色
         */
        private Color color=Color.YELLOW;
        /**
         * 字体名称
         */
        @ApiModelProperty(value = "字体名称")
        private String fontName;
        /**
         * 文本显示模式
         */
        private String pattern;
        /**
         * 文字与条码距离
         */
        private Float offset=0.2f;
        private Boolean displayChecksum=false;
        private Boolean displayStartStop=false;
    }



}
