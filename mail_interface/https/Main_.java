package mail_interface.https;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main_ {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://roma.corriere.it")).GET().build();

        HttpResponse<String> response;
        try{
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String sAppo = response.body();
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
