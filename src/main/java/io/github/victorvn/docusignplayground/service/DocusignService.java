package io.github.victorvn.docusignplayground.service;

import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.RecipientsUpdateSummary;
import io.github.victorvn.docusignplayground.model.CreateEnvelopeRequestBody;

public interface DocusignService {
    public EnvelopeSummary sendNewEnvelope(CreateEnvelopeRequestBody requestBody) throws ApiException;
    public RecipientsUpdateSummary updateEnvelopeEmail(String email, String envelopeId) throws ApiException;
}
