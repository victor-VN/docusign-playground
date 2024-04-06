package io.github.victorvn.docusignplayground.service;

import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.RecipientsUpdateSummary;
import io.github.victorvn.docusignplayground.model.CreateEnvelopeRequestBody;

import java.util.List;

public interface DocusignService {

    public List<Envelope> listAllEnvelopes(String dateFrom) throws ApiException;
    public EnvelopeSummary sendNewEnvelope(CreateEnvelopeRequestBody requestBody) throws ApiException;
    public RecipientsUpdateSummary updateEnvelopeEmail(String email, String envelopeId) throws ApiException;
}
