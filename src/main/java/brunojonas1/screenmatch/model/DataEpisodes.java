package brunojonas1.screenmatch.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisodes(@JsonAlias("Title") String episodeTitle,
                           @JsonAlias("Episode") Integer numberEpisode,
                           @JsonAlias("imdbRating") String ratingEpisode,
                           @JsonAlias("Released") String released) {

}
