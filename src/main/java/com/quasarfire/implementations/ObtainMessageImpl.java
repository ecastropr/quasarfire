package com.quasarfire.implementations;

import com.quasarfire.interfaces.ObtainMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtainMessageImpl implements ObtainMessage {

    private List<String> composedMessage = new ArrayList<String>();
    private String returnMessage = "";

    @Override
    public String getMessage(List<List<String>> sendingMessages) {
        List<List<String>> requestMessages = new ArrayList<List<String>>(sendingMessages);
        if (!validateLongMessages(requestMessages)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "la longitud de los mensajes es distinta");
        }

        return composeMessage(sendingMessages);
    }

    private boolean validateLongMessages (List<List<String>> sendingMessages){
        if(sendingMessages.size() > 1 && sendingMessages.get(0).size() - sendingMessages.get(1).size() == 0){
            sendingMessages.remove(0);
            return sendingMessages.size() != 1 ? validateLongMessages(sendingMessages): true;
        }else{
            return false;
        }
    }

    private String composeMessage(List<List<String>> sendingMessages){
        int lenMessage = 0;
        composedMessage = new ArrayList<String>();
        if(sendingMessages != null && !sendingMessages.isEmpty()) {
            lenMessage = sendingMessages.get(0).size();
            for (List<String> listMessage : sendingMessages) {
                if (this.composedMessage.isEmpty()) {
                    this.composedMessage.addAll(listMessage);
                    continue;
                }
                for (int i = 0; i < lenMessage; i++) {

                    if (this.composedMessage.get(i).equals("") && !listMessage.get(i).equals("")) {
                        this.composedMessage.set(i, listMessage.get(i));
                    }
                }
            }

            composedMessage.removeAll(Collections.singleton(""));
            if (composedMessage.size() != lenMessage) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay informacion suficiente");
            } else {
                return String.join(" ", composedMessage);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay informacion en los mensajes");
    }

}
