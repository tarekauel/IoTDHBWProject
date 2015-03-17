package de.dhbw.mannheim.iot.communication.DemoModel;

import java.util.Objects;

/**
 * @author Tarek Auel
 * @since March 13, 2015.
 */
public class DemoModelChild extends DemoModel {

    private final int intPayload;

    public DemoModelChild(String payload, int intPayload) {
        super(payload);
        this.intPayload = intPayload;
    }

    @Override
    public String toString() {
        return "DemoModelChild{" +
                "intPayload=" + intPayload +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DemoModelChild && Objects.equals(o.toString(), toString());
    }

}
