package brunojonas1.screenmatch.principal;

import brunojonas1.screenmatch.model.DataEpisodes;
import brunojonas1.screenmatch.model.DataSeason;
import brunojonas1.screenmatch.model.DataSeries;
import brunojonas1.screenmatch.model.Episodes;
import brunojonas1.screenmatch.service.ConsumoApi;
import brunojonas1.screenmatch.service.ConverteDados;
import org.springframework.cglib.core.Local;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitor = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4eed2a49";

    public void exibeMenu() {
        System.out.println("Digite o nome da série que você deseja buscar");
        var nomeBusca = leitor.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeBusca.replace(" ", "+") + API_KEY);

        DataSeries data = conversor.obterDados(json, DataSeries.class);
        System.out.println(data);

        List<DataSeason> seasons = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = consumo.obterDados(ENDERECO + nomeBusca.replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = conversor.obterDados(json, DataSeason.class);
            seasons.add(dataSeason);
        }
//            seasons.forEach(System.out::println);
        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.episodeTitle())));

        List<DataEpisodes> dataEpisodesList = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        System.out.printf("\n Lista dos Episódios mais bem avaliados\n");
        dataEpisodesList.stream()
                .filter(e -> !e.ratingEpisode().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisodes::ratingEpisode).reversed())
                .limit(5)
                .map(e -> e.episodeTitle().toUpperCase())
                //.peek (e -> System.out::println("Mapeamento " + e)) -> Seria aqui o uso do peek, ele serve para dar uma espiada no que o programa está fazendo
                .forEach(System.out::println);

        List<Episodes> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episodes(t.number(), d))
                ).collect(Collectors.toList());

//        episodes.forEach(System.out::println);

//        System.out.println("A partir de que ano você quer buscar os episódios?");
//        var year = leitor.nextInt();
//        leitor.nextLine();
//
//        LocalDate sourceDate = LocalDate.of(year,1,1);
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleased()!= null && e.getReleased().isAfter(sourceDate))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getSeason() +
//                                " Episódio: " + e.getTitle() +
//                                " Data de Lançamento: " + e.getReleased().format(df)));
//        System.out.println("Digite um techo do título: ");
//        var trechoTitulo = leitor.nextLine();

//        Optional<Episodes> filterEpisiode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (filterEpisiode.isPresent()){
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + filterEpisiode.get().getSeason());
//        } else {
//            System.out.println("Episódio não encontrado :( ");
//        }

        Map<Integer, Double> ratingForSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episodes::getSeason, Collectors.averagingDouble(Episodes::getRating)));
        System.out.println(ratingForSeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episodes::getRating));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}