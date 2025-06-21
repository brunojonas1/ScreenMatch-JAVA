package brunojonas1.screenmatch.model;

import org.springframework.cglib.core.Local;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodes {
    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double rating;
    private LocalDate released;

    public Episodes(Integer seasonNumber, DataEpisodes dataEpisodes) {
        this.season = seasonNumber;
        this.title = dataEpisodes.episodeTitle();
        this.numberEpisode = dataEpisodes.numberEpisode();

        try {
            this.rating = Double.valueOf(dataEpisodes.ratingEpisode());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
    }
        try {
            this.released = LocalDate.parse(dataEpisodes.released());
        } catch (DateTimeException ex) {
            this.released = null;
        }

    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Temporada=" + season +
                ", Título='" + title + '\'' +
                ", Número do episódio=" + numberEpisode +
                ", Avaliação=" + rating +
                ", Data de lançamento=" + released;
    }
}
