package com.github.laziestcoder.handsontry.webshell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author TOWFIQUL ISLAM
 * @since 6/11/24
 */


@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
