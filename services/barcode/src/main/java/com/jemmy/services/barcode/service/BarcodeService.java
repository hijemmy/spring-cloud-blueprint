package com.jemmy.services.barcode.service;

import com.jemmy.services.barcode.model.dto.BarcodeRequestDto;

import java.io.ByteArrayOutputStream;

public interface BarcodeService {

    ByteArrayOutputStream generateCode(BarcodeRequestDto dto);

}
