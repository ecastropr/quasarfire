package com.quasarfire.controller;

import com.quasarfire.entities.Satelite;
import com.quasarfire.entities.TopSecretRequestSplit;
import com.quasarfire.entities.TopSecretResponse;
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
    public TopSecretResponse topSecret(@Valid @RequestBody List<Satelite> satelites){

        return quasarfire.getInfoTopSecret(satelites);
    }

    @PostMapping(value = "/topsecret/{satelite_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TopSecretResponse topSecretSplit(@Valid @PathVariable String satelite_name, @Valid @RequestBody TopSecretRequestSplit requestSplit){

        return quasarfire.getInfoTopSecretSplit(satelite_name , requestSplit);
    }


}
