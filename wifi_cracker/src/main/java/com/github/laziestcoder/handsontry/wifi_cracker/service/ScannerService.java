package com.github.laziestcoder.handsontry.wifi_cracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TOWFIQUL ISLAM
 * @since 11/11/24
 */

@Service
public class ScannerService {

    public static Logger log = LoggerFactory.getLogger(ScannerService.class);

    public List<String> scanWifiNetworks() {
        List<String> wifiNetworks = new ArrayList<>();
        try {
            // Define the command to execute (adjust 'wlan0' to your interface name if different)
            ProcessBuilder processBuilder = new ProcessBuilder("sudo", "/usr/sbin/iwlist", "wlan0", "scan");
            Process process = processBuilder.start();

            // Read the command's output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder networkInfo = new StringBuilder();

            log.info("reader {}", reader.lines().count());

            while ((line = reader.readLine()) != null) {
                log.info("line {}", line);
                // Collect network details in blocks
                if (line.trim().startsWith("Cell")) {
                    if (!networkInfo.isEmpty()) {
                        wifiNetworks.add(networkInfo.toString());
                        networkInfo.setLength(0);
                    }
                }
                networkInfo.append(line.trim()).append("\n");
            }
            if (!networkInfo.isEmpty()) {
                wifiNetworks.add(networkInfo.toString());
            }

            process.waitFor();
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage(), e);
        }
        return wifiNetworks;
    }
}
