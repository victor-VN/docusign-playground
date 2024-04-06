package io.github.victorvn.docusignplayground.model;

import java.util.List;

public class CreateEnvelopeRequestBody {

    private String templateId;
    private String status;
    private List<SignerRequest> signers;


    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SignerRequest> getSigners() {
        return signers;
    }

    public void setSigners(List<SignerRequest> signers) {
        this.signers = signers;
    }


}
