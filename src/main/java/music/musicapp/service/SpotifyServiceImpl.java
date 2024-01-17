package music.musicapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import music.musicapp.dto.spotify.GenresResponse;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

    private static String accessToken;

    @Scheduled(fixedDelay = 3600000)
    public void getAccessToken() {

        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(URL + "api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=" + GRANT_TYPE + "client_id=" + clientID + "&client_secret=" + clientSecret))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String[] responseOfApi = response.body().split(",");
            String access = Arrays.stream(responseOfApi).findFirst().orElseThrow(
                    () -> new RestException(ExceptionEnum.SPOTIFY_AUTHORIZATION_EXCEPTION));
            accessToken = access.substring(17, access.length() - 1);
        }
        catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get access token");
            throw new RestException(ExceptionEnum.SPOTIFY_AUTHORIZATION_EXCEPTION);
        }
    }

    public HttpResponse<String> getArtist() {

        final HttpRequest request = buildGetHttpRequest(URI.create(urlToEndpoints + "v1/artists/0TnOYISbd1XYRBk9myaseg"));
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get artist");
            throw new RestException(ExceptionEnum.ARTIST_NOT_FOUND);
        }
    }

    public GenresResponse getGenres() {
        try {
            final HttpRequest request = buildGetHttpRequest(URI.create(urlToEndpoints + "v1/recommendations/available-genre-seeds"));

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            var responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, GenresResponse.class);
        } catch (IOException | InterruptedException e) {
            log.error("Error sending http request to spotify to get genres");
            throw new RestException(ExceptionEnum.GENRES_NOT_FOUND);
        }
    }

    private HttpRequest buildGetHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
    }
}