package io.github.victorvn.docusignplayground.controller;

import com.docusign.esign.client.ApiException;
import io.github.victorvn.docusignplayground.model.CreateEnvelopeRequestBody;
import io.github.victorvn.docusignplayground.model.UpdateEnvelopeRequestBody;
import io.github.victorvn.docusignplayground.service.DocusignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docusign")
public class EnvelopeController {

    //1
    @Autowired
    private DocusignService docusignService;


    //2
    @PostMapping
    public ResponseEntity<Object> sendEnvelope(@RequestBody CreateEnvelopeRequestBody requestBody){
        try {
            //3
            return ResponseEntity.ok().body(docusignService.sendNewEnvelope(requestBody));
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //4
    @PutMapping
    public ResponseEntity<Object> updateEmail(@RequestBody UpdateEnvelopeRequestBody requestBody){
        try {
            //5
            return ResponseEntity.ok().body(docusignService.updateEnvelopeEmail(requestBody.getEmail(), requestBody.getEnvelopeId()));
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
