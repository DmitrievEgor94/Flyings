package com.mycompany.dao;

import com.mycompany.models.Flight;
import com.mycompany.models.Passenger;
import com.mycompany.models.Ticket;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class FlightDao extends AbstractDao<Flight> {

    private static final String JQPL_FLIGHTS_WITH_PLANES = "select f.flightNumber,count(p.id) from Plane p join p.flights f " +
            " group by(f) having count(p.id) > 1";

    private static final String JPQL_GET_FLIGHTS_FOR_PASSENGER = "select f from Flight f join f.tickets t join t.passenger p " +
            "where p.firstName = :firstName and p.lastName = :lastName order by f.flightNumber";

    private static final String NATIVE_SQL_AVERAGE_PRICE_OF_FLIGHTS = "select f.flight_number, avg(t.cost) from flights f " +
            "join tickets t on f.id = t.flight_id group by(f.id) having avg(t.cost) > 200";

    public FlightDao() {
        super(Flight.class);
    }

    @Override
    public void delete(Flight flight) {
        for (Ticket ticket : flight.getTickets()) {
            ticket.getPassenger().getTickets().remove(ticket);
            ticket.getPlane().getTickets().remove(ticket);
        }

        super.delete(flight);
    }

    //2 part

    //JQPL
    public List<Flight> getFlightsForPassenger(Passenger passenger) {
        Query query = super.entityManager.createQuery(JPQL_GET_FLIGHTS_FOR_PASSENGER);

        query.setParameter("firstName", passenger.getFirstName());
        query.setParameter("lastName", passenger.getLastName());

        return query.getResultList();
    }

    public void printNumberOfPlanesPerFlight() {
        Query query = super.entityManager.createQuery(JQPL_FLIGHTS_WITH_PLANES);

        List resultList = query.getResultList();

        for (Object result : resultList) {

            Object[] arr = (Object[]) result;

            for (Object object : arr) {
                System.out.print(object + " ");
            }
            System.out.println();
        }
    }

    //NativeQuery
    public void printAveragePriceOfFlights() {

        Query nativeQuery = super.entityManager.createNativeQuery(NATIVE_SQL_AVERAGE_PRICE_OF_FLIGHTS);

        List resultList = nativeQuery.getResultList();

        for (Object result : resultList) {

            Object[] arr = (Object[]) result;

            String flightNumber = (String) arr[0];

            BigDecimal cost = (BigDecimal) arr[1];

            DecimalFormat df = new DecimalFormat("#,###.00");

            System.out.println("There is " + df.format(cost) + " average price for " + flightNumber);

        }
    }
}
