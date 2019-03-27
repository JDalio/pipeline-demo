package eva.dalio.demo;

import java.io.Serializable;

public class Allele implements Serializable {
    private String sequenceId;
    private long position;
    private String deletedSequence;
    private String insertedSequence;
    private boolean isVariant;

    public Allele(String sequenceId, long position, String deletedSequence, String insertedSequence) {
        this.sequenceId = sequenceId;
        this.position = position;
        this.deletedSequence = deletedSequence;
        this.insertedSequence = insertedSequence;
        isVariant = !deletedSequence.equals("") || !insertedSequence.equals("");
    }

    public boolean isVariant() {
        return isVariant;
    }

    @Override
    public String toString() {
        return "{" +
                "sequenceId='" + sequenceId + '\'' +
                ", position=" + position +
                ", deletedSequence='" + deletedSequence + '\'' +
                ", insertedSequence='" + insertedSequence + '\'' +
                ", isVariant=" + isVariant +
                "}\n";
    }
}
