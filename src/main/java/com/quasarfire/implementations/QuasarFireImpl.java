package com.quasarfire.implementations;

import com.quasarfire.entities.Satelite;
import com.quasarfire.entities.ShipPosition;
import com.quasarfire.entities.TopSecretRequestSplit;
import com.quasarfire.entities.TopSecretResponse;
import com.quasarfire.interfaces.ObtainLocation;
import com.quasarfire.interfaces.ObtainMessage;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuasarFireImpl implements QuasarFireInterface {

    private List<Satelite> satelites = new ArrayList<>();
    private List<Double> distances = new ArrayList<>();
    private ShipPosition positionShip = new ShipPosition();
    private List<List<String>> sendingMessages = new ArrayList<>();

    @Autowired
    private ObtainLocation obtainLocation;

    @Autowired
    private ObtainMessage obtainMessage;

    private QuasarFireImpl() {
        if(satelites != null){
            satelites.add(Satelite.builder().name("Kenovi").xPosition(-500).yPosition(-200).build());
            satelites.add(Satelite.builder().name("Skywalker").xPosition(-500).yPosition(-200).build());
            satelites.add(Satelite.builder().name("Sato").xPosition(-500).yPosition(-200).build());
        }
    }


    @Override
    public TopSecretResponse getInfoTopSecret(List<Satelite> sateliteInfo) {
        String recoverMessage = null;
        TopSecretResponse response;
        initLists();
        for(int i = 0; i < sateliteInfo.size(); i++){
           distances.add(sateliteInfo.get(i).getDistance());
           sendingMessages.add(sateliteInfo.get(i).getMessage());
        }

        positionShip = obtainLocation.getLocation(distances.stream().mapToDouble(Double::doubleValue).toArray());

        recoverMessage = obtainMessage.getMessage(sendingMessages);

        response = TopSecretResponse.builder().position(positionShip).message(recoverMessage).build();
        initLists();
        return response;
    }

    @Override
    public TopSecretResponse getInfoTopSecretSplit(String name, TopSecretRequestSplit requestSplit) {
        String recoverMessage = null;
        boolean modified = false;
        TopSecretResponse response;


        for(int i = 0; i < satelites.size(); i++) {
            if(satelites.get(i).getName().equals(name)){
                if(satelites.get(i).getMessage() != null){
                    satelites.get(i).setDistance(requestSplit.getDistance());
                    distances.set(i,requestSplit.getDistance());
                    satelites.get(i).setMessage(requestSplit.getMessage());
                    sendingMessages.set(i,requestSplit.getMessage());
                    modified = true;
                }else{
                    satelites.get(i).setDistance(requestSplit.getDistance());
                    distances.add(requestSplit.getDistance());
                    satelites.get(i).setMessage(requestSplit.getMessage());
                    sendingMessages.add(requestSplit.getMessage());
                    modified = true;
                }
                break;
            }
        }
        if(!modified){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El satelite no existe");
        }

        positionShip = obtainLocation.getLocation(distances.stream().mapToDouble(Double::doubleValue).toArray());

        recoverMessage = obtainMessage.getMessage(sendingMessages);

        response = TopSecretResponse.builder().position(positionShip).message(recoverMessage).build();

        return response;
    }

    private void initLists(){
        distances = new ArrayList<>();
        positionShip = new ShipPosition();
        sendingMessages = new ArrayList<>();
    }

}
