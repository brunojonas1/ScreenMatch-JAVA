package brunojonas1.screenmatch.principal;

import brunojonas1.screenmatch.model.DataSeason;
import brunojonas1.screenmatch.model.DataSeries;
import brunojonas1.screenmatch.service.ConsumoApi;
import brunojonas1.screenmatch.service.ConverteDados;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

        private Scanner leitor = new Scanner(System.in);
        private ConsumoApi consumo = new ConsumoApi();
        private ConverteDados conversor = new ConverteDados();

        private final String ENDERECO = "http://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=4eed2a49";

        public void exibeMenu(){
            System.out.println("Digite o nome da série que você deseja buscar");
            var nomeBusca = leitor.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeBusca.replace(" ","+") + API_KEY);

            DataSeries data = conversor.obterDados(json, DataSeries.class);
            System.out.println(data);

            List<DataSeason> seasons = new ArrayList<>();
            for (int i = 1; i<= data.totalSeasons(); i++){
                json = consumo.obterDados(ENDERECO + nomeBusca.replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason dataSeason = conversor.obterDados(json, DataSeason.class);
                seasons.add(dataSeason);
            }
//            seasons.forEach(System.out::println);
            seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.episodeTitle())));

        }
}
