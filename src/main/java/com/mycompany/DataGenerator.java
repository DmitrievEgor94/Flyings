package com.mycompany;

import com.mycompany.dao.FlightDao;
import com.mycompany.dao.PassengerDao;
import com.mycompany.dao.PlaneDao;
import com.mycompany.dao.TicketDao;
import com.mycompany.models.Flight;
import com.mycompany.models.Passenger;
import com.mycompany.models.Plane;
import com.mycompany.models.Ticket;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DataGenerator {

    public static void generate() {
        TicketDao ticketDao = new TicketDao();
        PassengerDao passengerDao = new PassengerDao();
        FlightDao flightDao = new FlightDao();
        PlaneDao planeDao = new PlaneDao();

        Passenger passenger1 = new Passenger("Egor", "Dmitriev");
        Passenger passenger2 = new Passenger("Andrey", "Gladchenko");
        Passenger passenger3 = new Passenger("Kirill", "Denisov");

        Passenger[] passengers = {
                passenger1,
                passenger2,
                passenger3
        };

        Flight flight1 = new Flight("XYZ123");
        Flight flight2 = new Flight("ABC123");

        Flight[] flights = {
                flight1,
                flight2
        };

        Plane plane1 = new Plane("Aerobus1");
        Plane plane2 = new Plane("Aerobus2");
        Plane plane3 = new Plane("Aerobus3");

        Plane[] planes = {
                plane1,
                plane2,
                plane3
        };

        Ticket ticket1 = new Ticket(new Date(), 1, 100);
        Ticket ticket2 = new Ticket(new Date(), 2, 200);
        Ticket ticket3 = new Ticket(new Date(), 3, 300);

        Ticket[] tickets = new Ticket[]{
                ticket1,
                ticket2,
                ticket3
        };

        ticket1.setFlight(flight1);
        ticket2.setFlight(flight1);
        ticket3.setFlight(flight2);

        for (int i = 0; i < tickets.length; i++) {
            tickets[i].setPassenger(passengers[i]);
            tickets[i].setPlane(planes[i]);
            ticketDao.save(tickets[i]);
        }

        Ticket ticket4 = new Ticket(new Date(), 23, 200);
        ticket4.setPassenger(passenger1);
        ticket4.setPlane(plane2);
        ticket4.setFlight(flight2);

        ticketDao.save(ticket4);

        Set<Plane> planesSet = new HashSet<>(Arrays.asList(planes));

        for (Flight flight : flights) {
            flight.setPlanes(planesSet);
            flightDao.update(flight);
        }
    }
}
