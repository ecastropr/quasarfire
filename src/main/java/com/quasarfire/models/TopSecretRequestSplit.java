package com.quasarfire.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopSecretRequestSplit {

    private double distance;
    private List<String> message;

}
