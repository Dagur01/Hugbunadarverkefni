package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;

import java.util.List;

public interface MovieHallService {

    MovieHall create(String name, String location);
    MovieHall update(Long movieHallId, String name, String location, Boolean nowShowing);
    void delete(Long movieHallId);

    List<MovieHall> listNowPlaying();
    List<MovieHall> listMovieHalls();
    MovieHall getMovieHallById(Long movieHallId);
    List<MovieHall> filterMovieHalls(String name, String location);
}
