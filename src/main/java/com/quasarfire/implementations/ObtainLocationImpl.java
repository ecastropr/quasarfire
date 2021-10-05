package com.quasarfire.implementations;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.quasarfire.entities.ShipPosition;
import com.quasarfire.interfaces.ObtainLocation;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ObtainLocationImpl implements ObtainLocation {
    @Value("${message.inssuficient-info}")
    private String insufficientInfoMessage;

    @Value("#{${satelites-location}}")
    private double[][] satelitesLocation;


    @Override
    public ShipPosition getLocation(double[] distances) {
        ShipPosition position = new ShipPosition();
        double[] location;
        try {
            NonLinearLeastSquaresSolver nonLinearSolver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(satelitesLocation, distances), new LevenbergMarquardtOptimizer());
            LeastSquaresOptimizer.Optimum SquaresOptimum = nonLinearSolver.solve();
            location = SquaresOptimum.getPoint().toArray();
            position.setX(Precision.round(location[0],2));
            position.setY(Precision.round(location[1],2));

            return position;
        }catch (IllegalArgumentException | NullPointerException exception )
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, insufficientInfoMessage);
        }
    }
}
