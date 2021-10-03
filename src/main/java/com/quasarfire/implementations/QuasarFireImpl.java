package com.quasarfire.implementations;

import com.quasarfire.entities.Satelite;
import com.quasarfire.entities.ShipPosition;
import com.quasarfire.entities.TopSecretRequestSplit;
import com.quasarfire.entities.TopSecretResponse;
import com.quasarfire.interfaces.ObtainLocation;
import com.quasarfire.interfaces.ObtainMessage;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class QuasarFireImpl implements QuasarFireInterface {

    @Value("${message.satelite-not-found}")
    private String sateliteNotFound;

    private List<Satelite> satelites = new ArrayList<>();
    private Map<String,Double> distances = new HashMap<>();
    private ShipPosition positionShip = new ShipPosition();
    private Map<String,List<String>> sendingMessages = new HashMap<>();

    @Autowired
    private ObtainLocation obtainLocation;

    @Autowired
    private ObtainMessage obtainMessage;

    private QuasarFireImpl() {

            satelites.add(Satelite.builder().name("kenovi").xPosition(-500).yPosition(-200).build());
            satelites.add(Satelite.builder().name("skywalker").xPosition(-500).yPosition(-200).build());
            satelites.add(Satelite.builder().name("sato").xPosition(-500).yPosition(-200).build());

    }


    @Override
    public TopSecretResponse getInfoTopSecret(List<Satelite> sateliteInfo) {
        String recoverMessage;
        TopSecretResponse response;
        List<List<String>> messages = new ArrayList<>();

        initLists();
        for(Satelite satelite: sateliteInfo){
           distances.put(satelite.getName().toLowerCase(),satelite.getDistance());
           sendingMessages.put(satelite.getName().toLowerCase(),satelite.getMessage());
        }
        messages.addAll(sendingMessages.values());

        positionShip = obtainLocation.getLocation(distances.values().stream().mapToDouble(Double::doubleValue).toArray());

        recoverMessage = obtainMessage.getMessage(messages);

        response = TopSecretResponse.builder().position(positionShip).message(recoverMessage).build();
        initLists();
        return response;
    }

    @Override
    public TopSecretResponse getInfoTopSecretSplit(String name, TopSecretRequestSplit requestSplit) {
        String recoverMessage;
        boolean modified = false;
        TopSecretResponse response;
        List<List<String>> messages = new ArrayList<>();

        for(Satelite satelite: satelites) {
            if(satelite.getName().equalsIgnoreCase(name)){
                distances.put(name.toLowerCase(),requestSplit.getDistance());
                sendingMessages.put(name.toLowerCase(),requestSplit.getMessage());
                modified = true;
                break;
            }
        }
        if(!modified){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, sateliteNotFound);
        }

        messages.addAll(sendingMessages.values());
        positionShip = obtainLocation.getLocation(distances.values().stream().mapToDouble(Double::doubleValue).toArray());

        recoverMessage = obtainMessage.getMessage(messages);

        response = TopSecretResponse.builder().position(positionShip).message(recoverMessage).build();
        initLists();
        return response;
    }

    private void initLists(){
        positionShip = new ShipPosition();
        sendingMessages.clear();
        distances.clear();
    }

}
