package com.jemmy.services.barcode.controller.frontend;

import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.service.BarcodeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/barcode")
@Api(value = "条形码",produces = MediaType.IMAGE_PNG_VALUE)
public class BarcodeController extends BaseController {

    @Autowired
    private BarcodeService barcodeService;
    @RequestMapping(value = "/1d", method = RequestMethod.GET)
    public void rendering(BarcodeRequestDto dto, HttpServletResponse response) throws IOException, InterruptedException {
        ByteArrayOutputStream bout=barcodeService.generateCode(dto);
        response.setContentType(dto.getTheReturn().getFormat());
        response.setContentLength(bout.size());
        response.getOutputStream().write(bout.toByteArray());
        response.getOutputStream().flush();
    }

}
