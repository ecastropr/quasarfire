package com.quasarfire.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Satelite {

    private String name;
    @JsonIgnore
    private double xPosition;
    @JsonIgnore
    private double yPosition;
    private double distance;
    private List<String> message;

}
