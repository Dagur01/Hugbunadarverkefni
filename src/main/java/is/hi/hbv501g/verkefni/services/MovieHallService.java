package is.hi.hbv501g.verkefni.services;

import is.hi.hbv501g.verkefni.persistence.entities.MovieHall;

import java.util.List;

public interface MovieHallService {
    MovieHall create(MovieHall h);

    MovieHall get(Long id);

    List<MovieHall> list();

    MovieHall update(Long id, MovieHall update);

    void delete(Long id);

    void changeCapacity(Long id, int totalSeats);
}

