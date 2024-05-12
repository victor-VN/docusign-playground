package io.github.victorvn.docusignplayground.model;

import lombok.Data;

@Data
public class UpdateEnvelopeRequestBody {
    private String envelopeId;
    private String email;
}
