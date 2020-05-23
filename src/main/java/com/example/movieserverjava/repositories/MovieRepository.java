package com.example.movieserverjava.repositories;

        import com.example.movieserverjava.models.Movie;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;


public interface MovieRepository
        extends CrudRepository<Movie, Long> {
    @Query(value = "select * from movie where name=:name", nativeQuery = true)
    public Movie findUserByName(
            @Param("name") String name);

    @Query(value = "select * from movie where imdbid=:imdbid", nativeQuery = true)
    public Movie findMovieById(
            @Param("imdbid") String imdbid);
}