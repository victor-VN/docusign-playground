package io.github.victorvn.docusignplayground.service;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.*;
import io.github.victorvn.docusignplayground.model.CreateEnvelopeRequestBody;
import io.github.victorvn.docusignplayground.service.auth.DocusignAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocusignServiceImpl implements DocusignService {

    @Autowired
    private DocusignAuthService authService;

    @Value("docusign.account.id")
    private String accountId;

    @Override
    public EnvelopeSummary sendNewEnvelope(CreateEnvelopeRequestBody requestBody) throws ApiException {
        ApiClient apiClient = authService.getDocusignClient();
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        Envelope envelope = new Envelope();

        List<TemplateRole> roleList = requestBody.getSigners().stream().map(
                 item -> new TemplateRole()
                         .roleName(item.getRoleName())
                         .email(item.getEmail())
                         .name(item.getName())
        ).collect(Collectors.toList());

        EnvelopeDefinition definition = new EnvelopeDefinition()
                .envelopeId(envelope.getEnvelopeId())
                .status(requestBody.getStatus())
                .templateId(requestBody.getTemplateId())
                .templateRoles(roleList);

        return envelopesApi.createEnvelope(accountId, definition);
    }

    @Override
    public RecipientsUpdateSummary updateEnvelopeEmail(String email, String envelopeId) throws ApiException {
        ApiClient apiClient = authService.getDocusignClient();
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        Envelope envelope = new Envelope();

        Signer signer = new Signer()
                .email(email)
                .recipientId("2")
                .roleName("stranger_signer");

        Recipients recipients = new Recipients()
                .addSignersItem(signer);

        envelope.setRecipients(recipients);

        EnvelopesApi.UpdateRecipientsOptions updateOptions = envelopesApi.new UpdateRecipientsOptions();
        updateOptions.setResendEnvelope("true");

        return  envelopesApi.updateRecipients(accountId, envelopeId, recipients, updateOptions);
    }

    @Override
    public List<Envelope> listAllEnvelopes(String dateFrom) throws ApiException {
        ApiClient apiClient = authService.getDocusignClient();
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        EnvelopesApi.ListStatusChangesOptions options = envelopesApi.new ListStatusChangesOptions();
        options.setFromDate(dateFrom);
        options.setInclude("recipients");

        EnvelopesInformation envelopesInformation = envelopesApi.listStatusChanges(accountId, options);

        return envelopesInformation.getEnvelopes().stream()
                .filter(envelope -> envelope.getStatus().equals("sent"))
                .filter(envelope -> envelope.getRecipients().getSigners().stream().anyMatch(
                                signer -> signer.getStatus().equals("autoresponded")
                        )
                ).collect(Collectors.toList());
    }
}
