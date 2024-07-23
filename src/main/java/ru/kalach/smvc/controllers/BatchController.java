package ru.kalach.smvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kalach.smvc.dao.BeerDAO;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    private final BeerDAO beerDAO;

    @Autowired
    public BatchController(BeerDAO beerDAO) {
        this.beerDAO = beerDAO;
    }

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch() {
        beerDAO.testMultipleUpdate();
        return "redirect:/beer";
    }

    @GetMapping("/with")
    public String withBatch() {
        beerDAO.testBatchUpdate();
        return "redirect:/beer";
    }
}
