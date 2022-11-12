package com.backend.backend.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

public class ExportacaoUtil {
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_CACHE_CONTROL_VALUE = "no-cache, no-store, must-revalidate";
    private static final String HEADER_PRAGMA = "Pragma";
    private static final String HEADER_PRAGMA_VALUE = "no-cache";
    private static final String HEADER_EXPIRES = "Expires";
    private static final String HEADER_EXPIRES_VALUE = "0";
    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_ATTACHMENT_FILENAME = "attachment; filename=";

    private static HttpHeaders getHttpHeaders (String nome) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_CACHE_CONTROL, HEADER_CACHE_CONTROL_VALUE);
        headers.add(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
        headers.add(HEADER_EXPIRES, HEADER_EXPIRES_VALUE);
        headers.add(HEADER_CONTENT_DISPOSITION, HEADER_ATTACHMENT_FILENAME + nome);

        return headers;
    }

    private static ResponseEntity<InputStreamResource> montaResponse(byte[] byteArrayOutputStream, HttpHeaders headers){
        return ResponseEntity.ok().headers(headers).contentLength(byteArrayOutputStream.length)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream)));
    }

    public static ResponseEntity<InputStreamResource> output(byte[] byteArrayOutputStream, String nome){
        return montaResponse(byteArrayOutputStream, getHttpHeaders(nome));
    }
}
