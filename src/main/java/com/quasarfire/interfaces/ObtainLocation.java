package com.quasarfire.interfaces;

import com.quasarfire.entities.ShipPosition;


public interface ObtainLocation {

    ShipPosition getLocation(double [] distances);
}
