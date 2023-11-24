package music.musicapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotifyServiceImpl {
    @Value("${application.spotify.client-id}")
    private String clientID;

    @Value("${application.spotify.client-secret}")
    private String clientSecret;

    private final static String GRANT_TYPE = "client_credentials&";

    private final static HttpClient httpClient = HttpClient.newHttpClient();

    private final static String URL = "https://accounts.spotify.com/";

    private final static String urlToEndpoints = "https://api.spotify.com/";

    public String getAccessToken() {

        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=" + GRANT_TYPE + "client_id=" + clientID + "&client_secret=" + clientSecret))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String[] responseOfApi = response.body().split(",");
            String access = Arrays.stream(responseOfApi).findFirst().orElseThrow(RuntimeException::new);
            return access.substring(17, access.length() - 1);
        } catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get access token");
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> getArtist() {

        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(urlToEndpoints + "v1/artists/0TnOYISbd1XYRBk9myaseg"))
                .header("Authorization" , "Bearer " + getAccessToken())
                .GET()
                .build();
        System.out.println(request.headers());
        try {

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response;
        } catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get artist");
            throw new RuntimeException(e);
        }
    }
}
