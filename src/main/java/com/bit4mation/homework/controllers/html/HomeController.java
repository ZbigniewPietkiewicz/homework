package com.bit4mation.homework.controllers.html;

import com.bit4mation.homework.services.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private TreeService treeService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listTrees", treeService.getAllTrees());

        return "home/index";
    }
}