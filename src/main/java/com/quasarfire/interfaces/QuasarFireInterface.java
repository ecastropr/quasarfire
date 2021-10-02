package com.quasarfire.interfaces;

import com.quasarfire.entities.Satelite;
import com.quasarfire.entities.TopSecretRequestSplit;
import com.quasarfire.entities.TopSecretResponse;

import java.util.List;

public interface QuasarFireInterface {

    TopSecretResponse getInfoTopSecret(List<Satelite> information);

    TopSecretResponse getInfoTopSecretSplit(String name, TopSecretRequestSplit requestSplit);

}
