package bank.api.infra.externalServices;

import bank.api.domain.services.IKeycloakAcessService;
import bank.api.presentation.dtos.autenticacao.Credenciais;
import bank.api.presentation.dtos.autenticacao.Token;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
public class KeycloakAcessService implements IKeycloakAcessService {
    @Inject
    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @Inject
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String clientSecret;

    @Override
    public Token getJwtToken(Credenciais credenciais) throws IOException {
        String urlParameters = "username=" + credenciais.username() +
                "&password=" + credenciais.password() +
                "&grant_type=password";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        URL url = new URL("http://localhost:8180/realms/quarkus/protocol/openid-connect/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String userCredentials = clientId + ":" + clientSecret;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        conn.setRequestProperty("Authorization", basicAuth);

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData);
        }

        InputStream is = conn.getInputStream();
        JsonObject jsonResponse = Json.createReader(new InputStreamReader(is, StandardCharsets.UTF_8)).readObject();
        String accessToken = jsonResponse.getString("access_token");
        Integer expiresIn = jsonResponse.getInt("expires_in");
        String refreshToken = jsonResponse.getString("refresh_token");

        return new Token(accessToken, expiresIn, refreshToken);
    }
}
