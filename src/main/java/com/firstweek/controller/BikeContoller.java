package com.firstweek.controller;

import com.firstweek.model.Bike;
import com.firstweek.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
public class BikeContoller {

    @Autowired
    private BikeRepository bikeRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String bikes(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(value = "q", required = false) String q,
                        Model model) {
        Pageable pageable = new PageRequest(pageNumber-1, 5);
        Page bikePage = bikeRepository.findAll(pageable);
        model.addAttribute("bikeList", bikePage);

        if (q != null) {
            model.addAttribute("bikeList", bikeRepository.findByMakeContainingIgnoreCase(q, pageable));
        }

//        Why does Thymeleaf not like isFirst and isLast ?????
        model.addAttribute("firstPage", bikePage.isFirst());
        model.addAttribute("lastPage", bikePage.isLast());
        model.addAttribute("bike", new Bike());
        return "index";
    }


    @RequestMapping(value="/bike/{id}", method = RequestMethod.GET)
    public String detailBike(@PathVariable("id") int id, Model model) {
        model.addAttribute("bike", bikeRepository.findOne(id));
        return "bike";
    }

    @RequestMapping(value="/addBike", method = RequestMethod.POST)
    public String addBike(@Valid Bike bike) {
        bike.setCreated(LocalDateTime.now());
        bikeRepository.save(bike);
        return "redirect:/";
    }

    @RequestMapping(value ="/delete/{id}", method = RequestMethod.POST)
    public String deleteBike(@PathVariable("id") int id) {
        bikeRepository.delete(bikeRepository.findOne(id));
        return "redirect:/";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public String editBike(@PathVariable("id") int id, Model model) {
        model.addAttribute("bike", bikeRepository.findOne(id));
        return "editBike";
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public String editBike(@Valid Bike bike, @PathVariable("id") int id) {
        bike.setId(id);
        bike.setCreated(bikeRepository.findOne(id).getCreated());
        bike.setUpdated(LocalDateTime.now());
        bikeRepository.save(bike);
        return "redirect:/bike/" + id;
    }


}
