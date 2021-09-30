package com.quasarfire.controller;

import com.quasarfire.interfaces.QuasarFireInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@RequestMapping("api/quasarfire")
@RestController
public class QuasarFireController {

    @Autowired
    QuasarFireInterface quasarfire;

    @RequestMapping("GetPosition")
    @GetMapping()
    public String getPosition(@Valid @RequestParam("distances") double [] distances){
        return quasarfire.getLocation(distances);
    }
}
