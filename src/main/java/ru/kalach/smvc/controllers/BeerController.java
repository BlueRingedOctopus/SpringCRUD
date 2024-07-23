package ru.kalach.smvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kalach.smvc.dao.BeerDAO;
import ru.kalach.smvc.models.Beer;

import javax.validation.Valid;

@Controller
@RequestMapping("/beer")
public class BeerController {

    private final BeerDAO beerDAO;

    @Autowired
    public BeerController(BeerDAO beerDAO) {
        this.beerDAO = beerDAO;
    }

    @GetMapping()
    public String index(Model model) {
        //Получаем все пиво из DAO и передаем на отображение в представление
        model.addAttribute("beer", beerDAO.index());
        return "beer/index";
    }


    @GetMapping("/{beerid}")
    public String showBeer(@PathVariable("beerid") int beerId, Model model) {
        //Получаем одно пиво по id из DAO и передаем на отображение в представление
        model.addAttribute("beer", beerDAO.show(beerId));
        return "beer/show";
    }

    @GetMapping("/new")
    public String newBeer(Model model) {
        model.addAttribute("beer", new Beer());

        return "beer/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("beer") @Valid Beer beer,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "beer/new";

        beerDAO.save(beer);
        return "redirect:/beer/";
    }

    @GetMapping("/{beerid}/edit")
    public String edit(Model model, @PathVariable("beerid") int beerId) {
        model.addAttribute("beer", beerDAO.show(beerId));
        return "beer/edit";
    }

    @PatchMapping("/{beerid}")
    public String update(@ModelAttribute("beer") @Valid Beer beer,
                         BindingResult bindingResult,
                         @PathVariable("beerid") int beerId) {
        if (bindingResult.hasErrors())
            return "beer/edit";

        beerDAO.update(beerId, beer);
        return "redirect:/beer";
    }

    @DeleteMapping("/{beerid}")
    public String delete(@PathVariable("beerid") int beerId) {
        beerDAO.delete(beerId);
        return "redirect:/beer";
    }
}
