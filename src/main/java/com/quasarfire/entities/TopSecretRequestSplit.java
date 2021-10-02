package com.quasarfire.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
