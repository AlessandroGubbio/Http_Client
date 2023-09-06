package mail_interface.https;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;


// pag 2 &o=2   torna un boolean se ci sta un risultato
public class Subito {
    public static void Stamparisultati(String sResp) {
        String sPrima = "SmallCard-module_item-title__1y5U3\">";
        String sDopo = "</h2><div class=\"\"><div class=\"";
        int iPos = sResp.indexOf("Nessun risultato al momento...");
        int iPosEnd;
        String sRes;
        if(iPos>=0)
        {
            //Ho finito
            System.out.println("Mi dispiace, non ci sono risultati");
        }
        else
        {
            iPos = sResp.indexOf(sPrima,0);
            while(iPos > 0)
            {
                iPosEnd = sResp.indexOf(sDopo,iPos);
                if(iPosEnd>=0)
                {
                    sRes = sResp.substring(iPos+sPrima.length(), iPosEnd);
                    System.out.println(sRes);
                    iPos = sResp.indexOf(sPrima,iPosEnd);
                }
                else
                    iPos = -1;
            }
        }
        //SmallCard-module_item-title__1y5U3">"
        //</h2><div class=""><div class="
        //Nessun risultato al momento...
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
        System.out.println(url);

        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20)).build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();

        HttpResponse<String> response;
        try{
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String sAppo = response.body();
            System.out.println("satatus: " + response.statusCode());
            //System.out.println(sAppo);
            // while stampa finche torna risultati
            Stamparisultati(response.body());
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}
