package com.quasarfire.interfaces;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface QuasarFireInterface {

    String getLocation(double [] distances);

    String getMessage(List<List<String>> sendingMessages);
}
