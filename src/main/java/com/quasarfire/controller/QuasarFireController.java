package com.quasarfire.controller;

import com.quasarfire.entities.Satelite;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/quasarfire")
@RestController
public class QuasarFireController {

    @Autowired
    QuasarFireInterface quasarfire;

    @PostMapping(value = "/topsecret", produces = MediaType.APPLICATION_JSON_VALUE)
    public String topSecret(@Valid @RequestBody List<Satelite> satelites){
        System.out.println(satelites);

        return "";
    }

}
