package com.mycompany;

import com.mycompany.dao.FlightDao;
import com.mycompany.dao.PassengerDao;
import com.mycompany.dao.PlaneDao;
import com.mycompany.dao.TicketDao;
import com.mycompany.models.Flight;
import com.mycompany.models.Passenger;
import com.mycompany.models.Ticket;
import com.mycompany.source.EntityManager;

import javax.persistence.Tuple;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        DataGenerator.generate();

        PassengerDao passengerDao = new PassengerDao();
        PlaneDao planeDao = new PlaneDao();
        FlightDao flightDao = new FlightDao();
        TicketDao ticketDao = new TicketDao();

        Passenger passenger = passengerDao.findById(1);

        //1 query
        planeDao.getPlanesForPassenger(passenger)
                .forEach(plane -> System.out.println(passenger.getLastName() + " flies on " + plane.getPlaneNumber() + "."));
        System.out.println();

        //2 query
        flightDao.getFlightsForPassenger(passenger)
                .forEach(flight -> System.out.println(passenger.getLastName() + " has " + flight.getFlightNumber() + " flight."));
        System.out.println();

        //3 query
        passengerDao.printPassengersWithSeveralTickets();

        //4 query
        flightDao.printNumberOfPlanesPerFlight();

        System.out.println();

        //5 query
        Flight flight = flightDao.findById(1);
        List<Ticket> ticketsOfFlight = ticketDao.getTicketsOfFlight(flight);
        System.out.println("For " + flight.getFlightNumber() + " flight tickets are:");
        ticketsOfFlight.forEach(ticket -> System.out.print(ticket.getId() + " "));
        System.out.println();
        System.out.println();

        //6query
        flightDao.printAveragePriceOfFlights();
        System.out.println();

        //7 query
        ticketDao.getTicketsOfPassenger(passenger).forEach(ticket -> System.out.println(ticket.getDate()));

        List<Tuple> passengersWithExpensiveTickets = passengerDao.getPassengersWithCostOfTickets();

        System.out.println();

        //8 query
        for (Tuple passengersWithExpensiveTicket : passengersWithExpensiveTickets) {

            Passenger foundPassenger = (Passenger) passengersWithExpensiveTicket.get(0);

            System.out.println(foundPassenger.getFirstName() + " " + foundPassenger.getLastName() + " " + "has tickets with cost " +
                    passengersWithExpensiveTicket.get(1));
        }

        EntityManager.close();
    }
}
