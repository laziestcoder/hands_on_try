package com.github.laziestcoder.handsontry.train_ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@RequiredArgsConstructor
@RequestMapping("/.well-known/acme-challenge")
@RestController
public class TestController {

    private static final String FILE_PATH_PREFIX = "/home/bKash.com/towfiqul.exabyting/";

    @GetMapping("/{fileName}")
    public Resource getFile(@PathVariable("fileName") String fileName) {
        String fullPath = FILE_PATH_PREFIX + fileName;
        Resource resource = new FileSystemResource(fullPath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new IllegalArgumentException("File not found or not readable: " + fullPath);
        }

        return resource;
    }
}
