package io.github.victorvn.docusignplayground.controller;

import io.github.victorvn.docusignplayground.model.CreateEnvelopeRequestBody;
import io.github.victorvn.docusignplayground.model.UpdateEnvelopeRequestBody;
import io.github.victorvn.docusignplayground.service.DocusignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.docusign.esign.client.ApiException;

@RestController
@RequestMapping("/docusign")
public class EnvelopeController {

    @Value("docusign.inbox.folder.id")
    private String inboxFolderId;

    @Autowired
    private DocusignService docusignService;

    @PostMapping
    public ResponseEntity<Object> sendEnvelope(@RequestBody CreateEnvelopeRequestBody requestBody){
        try {
            return ResponseEntity.ok().body(docusignService.sendNewEnvelope(requestBody));
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBody());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateEmail(@RequestBody UpdateEnvelopeRequestBody requestBody){
        try {
            return ResponseEntity.ok().body(docusignService.updateEnvelopeEmail(requestBody.getEmail(), requestBody.getEnvelopeId()));
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBody());
        }
    }

    @GetMapping("list/autoresponded")
    public ResponseEntity<Object> listEnvelopes(@RequestParam String dateFrom){
        try {
            return ResponseEntity.ok().body(docusignService.listAllEnvelopes(dateFrom));
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body(e.getResponseBody());
        }
    }
}
