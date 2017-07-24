package io.falcon.assessment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParameterMessage implements Serializable{
    private static final long serialVersionUID = 6880027772858740818L;

    private int seq;
    private int size;

    @Override
    public String toString() {
        return "Seq : " + seq + ", Size : " + size;
    }
}
