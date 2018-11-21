package com.jemmy.services.barcode.service.impl;

import com.jemmy.services.barcode.code.ExtBitmapCanvasProvider;
import com.jemmy.services.barcode.builder.ConfigurationBuilder;
import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;
import com.jemmy.services.barcode.service.BarcodeClassResolverService;
import com.jemmy.services.barcode.service.BarcodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avalon.framework.configuration.Configuration;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.eps.EPSCanvasProvider;
import org.krysalis.barcode4j.output.svg.SVGCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Jemmy
 */
@Slf4j
@Service
public class BarcodeServiceImpl implements BarcodeService {

	@Autowired
	private BarcodeClassResolverService barcodeClassResolverService;

	public ByteArrayOutputStream generateCode(BarcodeRequestDto dto) {

		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration cfg =builder.build(dto);
		String format = dto.getTheReturn().getFormat();
		if (null == format) {
			format = MimeTypes.MIME_SVG;
		}
		String code = dto.getBarCode().getCode();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
		try {


			BarcodeGenerator gen = BarcodeUtil.createBarcodeGenerator(cfg,barcodeClassResolverService.getResolver());
			int oritention= dto.getBarCode().getOritention();
			if (format.equals(MimeTypes.MIME_SVG)) {
				//Create Barcode and render it to SVG
				SVGCanvasProvider svg = new SVGCanvasProvider(false, oritention);
				gen.generateBarcode(svg, code);
				org.w3c.dom.DocumentFragment frag = svg.getDOMFragment();

				//Serialize SVG barcode
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer trans = factory.newTransformer();
				Source src = new javax.xml.transform.dom.DOMSource(frag);
				Result res = new javax.xml.transform.stream.StreamResult(bout);
				trans.transform(src, res);
			} else if (format.equals(MimeTypes.MIME_EPS)) {
				EPSCanvasProvider eps = new EPSCanvasProvider(bout,oritention);
				gen.generateBarcode(eps, code);
				eps.finish();
			} else {
				ExtBitmapCanvasProvider bitmap = (dto.getTheReturn().isGray()
						? new ExtBitmapCanvasProvider(
						bout, format,
						BufferedImage.TYPE_BYTE_GRAY, true, dto)
						: new ExtBitmapCanvasProvider(
						bout, format,
						//todo 此处应选择正确的颜色模型
						BufferedImage.TYPE_INT_RGB, false, dto));
				gen.generateBarcode(bitmap, code);
				bitmap.finish();
			}
		} catch (Exception e) {
			log.error("Error while generating barcode", e);
		} catch (Throwable t) {
			log.error("Error while generating barcode", t);
		} finally {
			try {
				bout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bout;
	}
}
