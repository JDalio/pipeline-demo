package eva.dalio.demo;

import java.io.Serializable;

public class Line implements Serializable {

    private String value;

    public Line(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Line{" +
                "value='" + value + '\'' +
                '}';
    }
}
