package com.jemmy.services.barcode.controller.frontend;

import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.base.constant.GlobalConstant;
import com.jemmy.common.core.annotation.ValidateAnnotation;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.service.BarcodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/api/barcode")
@Api(value = "条形码",produces = MediaType.IMAGE_PNG_VALUE)
public class BarcodeController extends BaseController {

    @Autowired
    private BarcodeService barcodeService;
    @RequestMapping(value = "/1d", method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(httpMethod = "GET", value = "一维条形码",produces = MediaType.IMAGE_PNG_VALUE+GlobalConstant.Symbol.COMMA+MediaType.IMAGE_JPEG_VALUE+GlobalConstant.Symbol.COMMA,consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    @ValidateAnnotation
    public void rendering(@Valid @RequestBody BarcodeRequestDto dto, BindingResult bindingResult, HttpServletResponse response) throws IOException, InterruptedException {
        ByteArrayOutputStream bout=barcodeService.generateCode(dto);
        response.setContentType(dto.getTheReturn().getFormat());
        response.setContentLength(bout.size());
        response.getOutputStream().write(bout.toByteArray());
        response.getOutputStream().flush();
    }

}
