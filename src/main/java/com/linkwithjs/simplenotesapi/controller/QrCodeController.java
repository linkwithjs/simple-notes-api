package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @GetMapping("/generate-qr")
    public ResponseEntity<byte[]> generateQrCode(@RequestParam String text) {
        try {
            byte[] qrCodeImage = qrCodeService.generateQrCode(text);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcode.png");
            headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
