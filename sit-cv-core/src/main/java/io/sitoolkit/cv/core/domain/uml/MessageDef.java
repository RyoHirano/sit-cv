package io.sitoolkit.cv.core.domain.uml;

import java.util.List;

import lombok.Data;

@Data
public class MessageDef extends SequenceElement {
    private MessageType type = MessageType.SYNC;
    private String requestName;
    private String requestQualifiedSignature;
    private LifeLineDef target;
    private String responseName;

    @Override
    public List<String> write(LifeLineDef lifeLine, SequenceElementWriter writer) {
        return writer.write(lifeLine, this);
    }
}
