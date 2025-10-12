package is.hi.hbv501g.verkefni.services.implementations;


import is.hi.hbv501g.verkefni.persistence.entities.movieHall;
import is.hi.hbv501g.verkefni.persistence.repositories.movieHallRepository;
import is.hi.hbv501g.verkefni.services.movieHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class moviehallServiceimplementation implements movieHallService {

    private final movieHallRepository movieHallRepository;

    @Override
    public movieHall create(String name, String location) {
        movieHall movieHall = is.hi.hbv501g.verkefni.persistence.entities.movieHall.builder()
                .name(name)
                .location(location)
                .nowShowing(false)
                .build();
        return movieHallRepository.save(movieHall);
    }

    @Override
    public movieHall update(Long movieHallId, String name, String location, Boolean nowShowing){
        movieHall movieHall = movieHallRepository.findById(movieHallId)
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
    public List<movieHall> listNowPlaying(){
        return movieHallRepository.findByNowShowingTrue();
    }

    @Override
    public List<movieHall> listMovieHalls() {
        return movieHallRepository.findAll();
    }

    @Override
    public movieHall getMovieHallById(Long movieHallId) {
        return movieHallRepository.findById(movieHallId)
                .orElseThrow(() -> new IllegalArgumentException("Moviehall not found"));
    }

    @Override
    public List<movieHall> filterMovieHalls(String name, String location) {
        return movieHallRepository.findAll().stream()
                .filter(m -> name == null || m.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(m -> location == null || m.getLocation().equalsIgnoreCase(location))
                .toList();
    }
}

