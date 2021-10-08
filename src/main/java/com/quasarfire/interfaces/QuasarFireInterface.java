package com.quasarfire.interfaces;

import com.quasarfire.models.Satelite;
import com.quasarfire.models.TopSecretRequestSplit;
import com.quasarfire.models.TopSecretResponse;

import java.util.List;

public interface QuasarFireInterface {

    TopSecretResponse getInfoTopSecret(List<Satelite> information);

    TopSecretResponse getInfoTopSecretSplit(String name, TopSecretRequestSplit requestSplit);

}
