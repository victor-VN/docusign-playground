package io.github.victorvn.docusignplayground.service.auth;

import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.auth.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


@Service
public class DocusignAuthServiceImpl implements DocusignAuthService {

    @Autowired
    private ApplicationContext ctx;

    @Value("docusign.private.key.file.name")
    private String privateKeyFileName;
    @Value("docusign.client.id")
    private String clientId;
    @Value("docusign.user.id")
    private String userId;
    @Value("docusign.user.scopes")
    private String scope;

    int accessExpirationMs=9600000;
    private static final long TOKEN_EXPIRATION_IN_SECONDS = 3600;

    @Override
    public ApiClient getDocusignClient(){

        InputStream privateKey = readFileFromResources(privateKeyFileName);
        List<String> scopes = Arrays.stream(scope.split(",")).toList();

        try {
            ApiClient apiClient = new ApiClient();
            byte[] privateKeyBytes = privateKey.readAllBytes();

            OAuth.OAuthToken oAuthToken = apiClient.requestJWTUserToken(
                    clientId,
                    userId,
                    scopes,
                    privateKeyBytes,
                    TOKEN_EXPIRATION_IN_SECONDS
            );

            String accessToken = oAuthToken.getAccessToken();

            apiClient.addDefaultHeader("Authorization", "Bearer " + accessToken);

            return apiClient;
        } catch (Exception e) {
            return null;
        }
    }

    private InputStream readFileFromResources(String fileName){
        Resource resource = ctx.getResource("classpath:" + fileName);
        File file = null;
        InputStream inputStream = null;
        try {
            file = resource.getFile();
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return inputStream;
    }

}
