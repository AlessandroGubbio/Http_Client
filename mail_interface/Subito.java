package mail_interface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

public class Subito {
    public static void Stamparisultati(String resp){
        int pos = resp.indexOf("Nessun risultato al momento...");
        if(pos>=0){

        }else{
            pos = resp.indexOf(pos);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("prodotto? ");
        String cosa = scanner.nextLine();
        System.out.println("categoria? ");
        String categoria = scanner.nextLine();
        System.out.println("regione? ");
        String regione = scanner.nextLine();
        System.out.println("città? ");
        String citta = scanner.nextLine();
        String url = "https://subito.it/annunci-" + regione + "/vendita/" + categoria + "/" + citta + "/?q=" + cosa;

        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();

        HttpResponse<String> response;
        try{
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}
