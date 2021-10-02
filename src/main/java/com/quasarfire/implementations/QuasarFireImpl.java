package com.quasarfire.implementations;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.quasarfire.entities.Satelite;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuasarFireImpl implements QuasarFireInterface {

    private List<Satelite> satelites;

    public static final double[][] satelitesLocation = {{-500,-200},{100,-100},{500,100}};

    public QuasarFireImpl() {
        satelites = new ArrayList<Satelite>();
        satelites.add(Satelite.builder().name("Kenobi").xPosition(-500).yPosition(-200).build());
        satelites.add(Satelite.builder().name("Skywalker").xPosition(100).yPosition(-100).build());
        satelites.add(Satelite.builder().name("Sato").xPosition(500).yPosition(100).build());
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

    @Override
    public String getMessage(List<List<String>> sendingMessages) {
        String phrase = "";

        if (!validateLongMessages(sendingMessages)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la longitud de los mensajes es distinta");
        }

        System.out.println(validateLongMessages(sendingMessages));

        return "";
    }

    public boolean validateLongMessages (List<List<String>> sendingMessages){
        if(sendingMessages.size() > 1 && sendingMessages.get(0).size() - sendingMessages.get(1).size() == 0){
            sendingMessages.remove(0);
            return sendingMessages.size() != 1 ? validateLongMessages(sendingMessages): true;
        }else{
            return false;
        }
    }

}
