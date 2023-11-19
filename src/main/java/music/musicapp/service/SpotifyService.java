package music.musicapp.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import music.musicapp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotifyService {
    @Value("${application.spotify.client-id}")
    private String clientID;

    @Value("${application.spotify.client-secret}")
    private String clientSecret;

    private final static String GRANT_TYPE = "client_credentials";

    private final static HttpClient httpClient = HttpClient.newHttpClient();

    private final static String URL = "https://accounts.spotify.com/";

    private static String accessToken;

    public void getAccessToken() {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "api/token"))
                .header("Authorization", encodeHeader())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=" + GRANT_TYPE))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request , HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get access token");
            throw new RuntimeException(e);
        }
    }

    private String encodeHeader() {
        byte[] dataBase = (clientID + clientSecret).getBytes();
        String encodedHeader = Base64.getEncoder().encodeToString(dataBase);
        return "Basic " + encodedHeader;
    }
}
