package project.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutsController {
    @GetMapping("/layouts/header")
    public String header() { return "/layouts/header";}
}
