package com.quasarfire.interfaces;

import java.util.List;

public interface ObtainMessage {

    String getMessage(List<List<String>> sendingMessages);

    boolean validateLongMessages (List<List<String>> sendingMessages);
}
