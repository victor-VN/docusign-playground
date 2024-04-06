package io.github.victorvn.docusignplayground.model;

public class UpdateEnvelopeRequestBody {
    private String envelopeId;
    private String email;

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
