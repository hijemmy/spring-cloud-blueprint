package com.jemmy.services.barcode.controller.frontend;

import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.base.constant.GlobalConstant;
import com.jemmy.common.core.annotation.ValidateAnnotation;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.barcode.model.dto.barcode.BarcodeRequestDto;
import com.jemmy.services.barcode.model.dto.barcode.TestDto;
import com.jemmy.services.barcode.model.vo.barcode.TestVo;
import com.jemmy.services.barcode.service.BarcodeService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/api/barcode")
@Api(value = "条形码",produces = MediaType.IMAGE_PNG_VALUE)
@Validated
public class BarcodeController extends BaseController {

    @Autowired
    private BarcodeService barcodeService;
    @RequestMapping(value = "/1d", method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(httpMethod = GlobalConstant.HttpMethod.GET, value = "一维条形码",produces = MediaType.IMAGE_PNG_VALUE+GlobalConstant.Symbol.COMMA+MediaType.IMAGE_JPEG_VALUE+GlobalConstant.Symbol.COMMA,consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    @ValidateAnnotation
    public void rendering(@Valid @RequestBody BarcodeRequestDto dto, BindingResult bindingResult, HttpServletResponse response) throws IOException, InterruptedException {
        ByteArrayOutputStream bout=barcodeService.generateCode(dto);
        response.setContentType(dto.getTheReturn().getFormat());
        response.setContentLength(bout.size());
        response.getOutputStream().write(bout.toByteArray());
        response.getOutputStream().flush();
    }

    @RequestMapping(value = "/test/{name}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiOperation(httpMethod =GlobalConstant.HttpMethod.GET,value = "测试用例",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",value = "认证token",required = true,paramType = "header")
    })
    public MvcResult<TestVo> test(@Valid @PathVariable @NotBlank  String name, @Valid @NotNull @Min(1) @RequestParam Integer age){
        TestVo msg=new TestVo();
        msg.setAge(18);
        msg.setName("Jemmy");
        return MvcResultBuilder.ok(msg);
    }
}
