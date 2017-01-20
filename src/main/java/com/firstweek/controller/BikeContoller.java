package com.firstweek.controller;

import com.firstweek.model.Bike;
import com.firstweek.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@Controller
public class BikeContoller {

    @Autowired
    private BikeRepository bikeRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String bikes(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(value = "q", required = false) String q,
                        Model model) {
        Page bikePage;
        Pageable pageable = new PageRequest(pageNumber-1, 5);
        boolean isQ = (q != null);

        if (isQ) {
            bikePage = bikeRepository.findByMakeContainingIgnoreCase(q, pageable);
            model.addAttribute("bikeList", bikePage);
            model.addAttribute("q", q);

        } else {
            bikePage = bikeRepository.findAllByOrderByIdDesc(pageable);
            model.addAttribute("bikeList", bikePage);
        }
        // Why does Thymeleaf not like isFirst and isLast ?????
        model.addAttribute("firstPage", bikePage.isFirst());
        model.addAttribute("lastPage", bikePage.isLast());
        model.addAttribute("isQ", isQ);
        model.addAttribute("bikeListHasContent", bikePage.hasContent());
        model.addAttribute("bikeForm", new Bike());
        return "index";
    }


    @RequestMapping(value="/bike/{id}", method = RequestMethod.GET)
    public String detailBike(@PathVariable("id") int id, Model model) {
        model.addAttribute("bike", bikeRepository.findOne(id));
        return "bike";
    }

    @RequestMapping(value="/addBike", method = RequestMethod.POST)
    public String addBike(@Valid @ModelAttribute("bikeForm") Bike bikeForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editBike";
        }
        bikeForm.setCreated(LocalDateTime.now());
        bikeRepository.save(bikeForm);
        return "redirect:/";
    }

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.POST)
    public String deleteBike(@PathVariable("id") int id) {
        bikeRepository.delete(bikeRepository.findOne(id));
        return "redirect:/";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public String editBike(@PathVariable("id") int id, Model model) {
        model.addAttribute("bikeForm", bikeRepository.findOne(id));
        return "editBike";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String editBike(@Valid @ModelAttribute("bikeForm") Bike bikeForm, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "editBike";
        }
        bikeForm.setId(id);
        bikeForm.setCreated(bikeRepository.findOne(id).getCreated());
        bikeForm.setUpdated(LocalDateTime.now());
        bikeRepository.save(bikeForm);
        return "redirect:/bike/" + id;
    }


}
