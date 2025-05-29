package com.github.laziestcoder.handsontry.wifi_cracker.controller;

import com.github.laziestcoder.handsontry.wifi_cracker.service.ScannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author TOWFIQUL ISLAM
 * @since 11/11/24
 */

@RestController
@RequestMapping("/api/scanner")
public class ScannerRestController {

    private final ScannerService scannerService;

    public ScannerRestController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping("/wifi")
    public ResponseEntity<Map<String, List<String>>> getWifiNetworks() {
        return ResponseEntity.ok(Map.of("wifi", scannerService.scanWifiNetworks()));
    }

}
