package com.quasarfire.interfaces;

import com.quasarfire.entities.ShipPosition;
import org.springframework.stereotype.Service;


public interface ObtainLocation {

    ShipPosition getLocation(double [] distances);
}
