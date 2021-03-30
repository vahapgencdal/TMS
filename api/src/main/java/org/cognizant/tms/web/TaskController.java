package org.cognizant.tms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {

    @RequestMapping("/")
    public String welcome() {
        return "index";
    }

    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    @RequestMapping("/add")
    public String add() {
        return "add";
    }

    @RequestMapping("/update")
    public String update() {
        return "add";
    }
}