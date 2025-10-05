package is.hi.hbv501g.verkefni;
import is.hi.hbv501g.verkefni.persistence.entities.Movie;

import is.hi.hbv501g.verkefni.persistence.repositories.MovieRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories
public class VerkefniApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerkefniApplication.class, args);
    }


    @Bean
    CommandLineRunner seedMovies(MovieRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Movie.builder().title("Dune: Part Two").genre("Sci-Fi").ageRating(12).duration(166L).build());
                repo.save(Movie.builder().title("Inside Out 2").genre("Animation").ageRating(7).duration(96L).build());
                repo.save(Movie.builder().title("The Dark Knight").genre("Action").ageRating(16).duration(152L).build());
            }
        };
    }
}
