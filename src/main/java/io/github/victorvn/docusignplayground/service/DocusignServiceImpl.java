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

    @Value("${docusign.account.id}")
    private String accountId;

    @Override
    public EnvelopeSummary sendNewEnvelope(CreateEnvelopeRequestBody requestBody) throws ApiException {
        //1
        ApiClient apiClient = authService.getDocusignClient();

        //2
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        //3
        Envelope envelope = new Envelope();

        //4
        List<TemplateRole> roleList = requestBody.getSigners().stream().map(
                 item -> new TemplateRole()
                         .roleName(item.getRoleName())
                         .email(item.getEmail())
                         .name(item.getName())
        ).collect(Collectors.toList());

        //5
        EnvelopeDefinition definition = new EnvelopeDefinition()
                .envelopeId(envelope.getEnvelopeId())
                .status(requestBody.getStatus())
                .templateId(requestBody.getTemplateId())
                .templateRoles(roleList);

        //6
        return envelopesApi.createEnvelope(accountId, definition);
    }

    @Override
    public RecipientsUpdateSummary updateEnvelopeEmail(String email, String envelopeId) throws ApiException {
        ApiClient apiClient = authService.getDocusignClient();
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        Envelope envelope = new Envelope();

        //7
        Signer signer = new Signer()
                .email(email)
                .recipientId("2")
                .roleName("stranger_signer");

        //8
        Recipients recipients = new Recipients()
                .addSignersItem(signer);

        //9
        envelope.setRecipients(recipients);

        //10
        EnvelopesApi.UpdateRecipientsOptions updateOptions = envelopesApi.new UpdateRecipientsOptions();
        updateOptions.setResendEnvelope("true");

        //11
        return  envelopesApi.updateRecipients(accountId, envelopeId, recipients, updateOptions);
    }
}
