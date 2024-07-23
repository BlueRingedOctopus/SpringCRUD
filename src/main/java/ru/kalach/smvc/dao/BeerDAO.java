package ru.kalach.smvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kalach.smvc.models.Beer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeerDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BeerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Beer> index() {
        return jdbcTemplate.query("SELECT * FROM Beers", new BeanPropertyRowMapper<>(Beer.class));
    }


    public Beer show(int beerId) {
        return jdbcTemplate.query("SELECT * FROM Beers WHERE beerId=?", new Object[]{beerId}, new BeanPropertyRowMapper<>(Beer.class))
                .stream().findAny().orElse(null);
    }

    public void save(Beer beer) {
        jdbcTemplate.update("INSERT INTO Beers(beertitle, beerstyle, brewery, abv) VALUES(?, ?, ?, ?)", beer.getBeerTitle(),
                beer.getBeerStyle(), beer.getBrewery(), beer.getAbv());
    }

        public void update(int beerId, Beer updatedBeer) {
        jdbcTemplate.update("UPDATE Beers SET beertitle=?, beerstyle=?, brewery=?, abv=? WHERE beerId=?",
                updatedBeer.getBeerTitle(), updatedBeer.getBeerStyle(), updatedBeer.getBrewery(), updatedBeer.getAbv(), beerId);
    }

    public void delete(int beerId) {
        jdbcTemplate.update("DELETE FROM Beers WHERE beerId=?", beerId);
    }

    /////////////////////////////////

    public void testMultipleUpdate(){
        List<Beer> beer = create1000Beers();

        long before = System.currentTimeMillis();

        for(Beer b : beer){
            jdbcTemplate.update("INSERT INTO Beers VALUES(?, ?, ?, ?, ?)", b.getBeerId(), b.getBeerTitle(),
                    b.getBeerStyle(), b.getBrewery(), b.getAbv());
        }

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate(){
        List<Beer> beer = create1000Beers();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Beers VALUES(?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, beer.get(i).getBeerId());
                        ps.setString(2, beer.get(i).getBeerTitle());
                        ps.setString(3, beer.get(i).getBeerStyle());
                        ps.setString(4, beer.get(i).getBrewery());
                        ps.setDouble(5, beer.get(i).getAbv());
                    }

                    @Override
                    public int getBatchSize() {
                        return beer.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Beer> create1000Beers() {
        List<Beer> beer = new ArrayList<>();

        for (int i = 0; i < 1000; i++)
            beer.add(new Beer(i, "Title" + i, "IPA", "Baltika Breweries " + i, 8));

        return beer;
    }
}
