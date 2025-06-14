package brunojonas1.screenmatch;

import brunojonas1.screenmatch.model.DataSeries;
import brunojonas1.screenmatch.service.ConsumoApi;
import brunojonas1.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=The+Flash&apikey=4eed2a49");
		System.out.println(json);
		ConverteDados converte = new ConverteDados();
		DataSeries data = converte.obterDados(json, DataSeries.class);
		System.out.println(data);
	}
}
