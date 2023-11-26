package com.sachuk.keu.controllers.rest.dia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiaTESTResultController {
    @GetMapping("/authdone")
    public String endAuth(){
        return "cabinet"; // TODO
    }
}
