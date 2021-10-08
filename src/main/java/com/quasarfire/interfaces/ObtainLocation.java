package com.quasarfire.interfaces;

import com.quasarfire.models.ShipPosition;


public interface ObtainLocation {

    ShipPosition getLocation(double [] distances);
}
