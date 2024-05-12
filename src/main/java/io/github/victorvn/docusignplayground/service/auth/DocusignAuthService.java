package io.github.victorvn.docusignplayground.service.auth;

import com.docusign.esign.client.ApiClient;

public interface DocusignAuthService {
    public ApiClient getDocusignClient();
}
