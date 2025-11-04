package is.hi.hbv501g.verkefni.services.implementations;


import is.hi.hbv501g.verkefni.persistence.repositories.MovieHallRepository;
import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;
import is.hi.hbv501g.verkefni.services.MovieHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieHallServiceImplementation implements MovieHallService {

    private final MovieHallRepository movieHallRepository;

    @Override
    public MovieHall create(String name, String location) {
        MovieHall movieHall = is.hi.hbv501g.verkefni.persistence.entities.MovieHall.builder()
                .name(name)
                .location(location)
                .nowShowing(false)
                .build();
        return movieHallRepository.save(movieHall);
    }

    @Override
    public MovieHall update(Long movieHallId, String name, String location, Boolean nowShowing){
        MovieHall movieHall = movieHallRepository.findById(movieHallId)
                .orElseThrow(() ->  new IllegalArgumentException("MovieHall not found"));
        movieHall.setName(name);
        movieHall.setLocation(location);
        movieHall.setNowShowing(nowShowing);
        return movieHallRepository.save(movieHall);
    }
    @Override
    public void delete(Long movieHallId){
        movieHallRepository.deleteById(movieHallId);
    }

    @Override
    public List<MovieHall> listNowPlaying(){
        return movieHallRepository.findByNowShowingTrue();
    }

    @Override
    public List<MovieHall> listMovieHalls() {
        return movieHallRepository.findAll();
    }

    @Override
    public MovieHall getMovieHallById(Long movieHallId) {
        return movieHallRepository.findById(movieHallId)
                .orElseThrow(() -> new IllegalArgumentException("Moviehall not found"));
    }

    @Override
    public List<MovieHall> filterMovieHalls(String name, String location) {
        return movieHallRepository.findAll().stream()
                .filter(m -> name == null || m.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(m -> location == null || m.getLocation().equalsIgnoreCase(location))
                .toList();
    }
}

