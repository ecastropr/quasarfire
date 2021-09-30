package com.quasarfire.implementations;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

@Service
public class QuasarFireImpl implements QuasarFireInterface {

    public static final double[][] satelitesLocation = {{-500,-200},{100,-100},{500,100}};

    public QuasarFireImpl() {


    }

    @Override
    public String getLocation(double[] distances) {
        double[] location;
        try {
            NonLinearLeastSquaresSolver nonLinearSolver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(satelitesLocation, distances), new LevenbergMarquardtOptimizer());
            LeastSquaresOptimizer.Optimum SquaresOptimum = nonLinearSolver.solve();
            location = SquaresOptimum.getPoint().toArray();
            return "Coordenada X = " + location[0] + ", " + "Coordenada Y = " + location[1];
        }catch (IllegalArgumentException | NullPointerException exception )
        {
            return "No se puede determinar la posicion de la nave";
        }
    }

}
