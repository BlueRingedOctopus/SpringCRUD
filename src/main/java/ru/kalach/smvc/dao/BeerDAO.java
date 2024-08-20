package ru.kalach.smvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kalach.smvc.models.Beer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeerDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BeerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Beer> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Beer p", Beer.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Beer show(int beerId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Beer.class, beerId);
    }

    @Transactional
    public void save(Beer beer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(beer);
    }

    @Transactional
    public void update(int beerId, Beer updatedBeer) {
        Session session = sessionFactory.getCurrentSession();
        Beer beerToBeUpdated = session.get(Beer.class, beerId);

        beerToBeUpdated.setBeerTitle(updatedBeer.getBeerTitle());
        beerToBeUpdated.setBrewery(updatedBeer.getBrewery());
        beerToBeUpdated.setBeerStyle(updatedBeer.getBeerStyle());
        beerToBeUpdated.setAbv(updatedBeer.getAbv());
    }

    @Transactional
    public void delete(int beerId) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Beer.class, beerId));

    }
}

    /////////////////////////////////

//    public void testMultipleUpdate(){
//        List<Beer> beer = create1000Beers();
//
//        long before = System.currentTimeMillis();
//
//        for(Beer b : beer){
//            jdbcTemplate.update("INSERT INTO Beers VALUES(?, ?, ?, ?, ?)", b.getBeerId(), b.getBeerTitle(),
//                    b.getBeerStyle(), b.getBrewery(), b.getAbv());
//        }
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Time: " + (after - before));
//    }
//
//    public void testBatchUpdate(){
//        List<Beer> beer = create1000Beers();
//
//        long before = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate("INSERT INTO Beers VALUES(?, ?, ?, ?, ?)",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setInt(1, beer.get(i).getBeerId());
//                        ps.setString(2, beer.get(i).getBeerTitle());
//                        ps.setString(3, beer.get(i).getBeerStyle());
//                        ps.setString(4, beer.get(i).getBrewery());
//                        ps.setDouble(5, beer.get(i).getAbv());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return beer.size();
//                    }
//                });
//
//        long after = System.currentTimeMillis();
//        System.out.println("Time: " + (after - before));
//    }
//
//    private List<Beer> create1000Beers() {
//        List<Beer> beer = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++)
//            beer.add(new Beer(i, "Title" + i, "IPA", "Baltika Breweries " + i, 8));
//
//        return beer;
//    }
//}
