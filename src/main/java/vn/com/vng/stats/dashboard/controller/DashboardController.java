package vn.com.vng.stats.dashboard.controller;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    private static final String PATH = "/error";

    @RequestMapping(value = "/")
    public String welcome() {
        return "redirect:/issues";
    }


}