package brunojonas1.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataSeries (@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("imdbRating") String rating) {


}
