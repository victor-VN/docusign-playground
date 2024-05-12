package io.github.victorvn.docusignplayground.model;

import lombok.Data;
import java.util.List;

@Data
public class CreateEnvelopeRequestBody {
    private String templateId;
    private String status;
    private List<SignerRequest> signers;
}
