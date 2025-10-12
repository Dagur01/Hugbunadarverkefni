package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.movieHall;

import java.util.List;

public interface movieHallService {

    movieHall create(String name, String location);
    movieHall update(Long movieHallId, String name, String location, Boolean nowShowing);
    void delete(Long movieHallId);

    List<movieHall> listNowPlaying();
    List<movieHall> listMovieHalls();
    movieHall getMovieHallById(Long movieHallId);
    List<movieHall> filterMovieHalls(String name, String location);
}
