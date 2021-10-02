package com.quasarfire.implementations;

import com.quasarfire.interfaces.ObtainMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ObtainMessageImpl implements ObtainMessage {

    public ObtainMessageImpl() {
    }

    @Override
    public String getMessage(List<List<String>> sendingMessages) {
        List<List<String>> requestMessages = new ArrayList<List<String>>(sendingMessages);
        if (!validateLongMessages(requestMessages)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la longitud de los mensajes no coinciden");
        }

        return composeMessage(sendingMessages);
    }

    public boolean validateLongMessages(List<List<String>> sendingMessages){
        if(sendingMessages.size() > 1 && sendingMessages.get(0).size() - sendingMessages.get(1).size() == 0){
            sendingMessages.remove(0);
            return sendingMessages.size() != 1 ? validateLongMessages(sendingMessages): true;
        }else{
            return false;
        }
    }

    private String composeMessage(List<List<String>> sendingMessages){
        int lenMessage = 0;
        List<String> composedMessage = new ArrayList<String>();
        if(sendingMessages != null && !sendingMessages.isEmpty()) {
            lenMessage = sendingMessages.get(0).size();
            for (List<String> listMessage : sendingMessages) {
                if (composedMessage.isEmpty()) {
                    composedMessage.addAll(listMessage);
                    continue;
                }
                for (int i = 0; i < lenMessage; i++) {

                    if (composedMessage.get(i).equals("") && !listMessage.get(i).equals("")) {
                        composedMessage.set(i, listMessage.get(i));
                    }
                }
            }

            composedMessage.removeAll(Collections.singleton(""));
            if (composedMessage.size() != lenMessage) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay informacion suficiente");
            } else {
                return String.join(" ", composedMessage);
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay informacion en los mensajes");
    }

}
