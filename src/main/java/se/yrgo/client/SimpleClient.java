package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.dataaccess.RecordNotFoundException;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.*;

public class SimpleClient {

    /**
     * I have tests for every method in the CMS-service-class for this assignment,
     * just comment out the previous one after going one at a time or just run them all at once.
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

//        Code tests for Inlämning 4 & 5
//        Same method-tests as last assignment + a few new ones to test for more classes, but I have a time-measurement print around each method.
        CustomerManagementService service = container.getBean("customerManagementService", CustomerManagementService.class);
                CallHandlingService callService = container.getBean("callHandlingService", CallHandlingService.class);
                DiaryManagementService diaryService = container.getBean("diaryManagementService", DiaryManagementService.class);

        Customer spotify = new Customer("SPO01", "Spotify AB", "bosse@spotify.se", "123456789", "Some notes");
        Call spotifyCall = new Call("Call Jim tomorrow", new java.util.Date());
        Customer klarna = new Customer("KLA01", "Klarna AB", "eva@klarna.se", "987654321", "Some other notes");
        Customer volvo = new Customer("VOL01", "Volvo AB", "najib@volvo.se", "9998887771", "Some other notes again");

        //Test of registering new customers
        service.newCustomer(spotify);
        System.out.println("Spotify registered");

        try { // And of recording a call for spotify
            service.recordCall(spotify.getCustomerId(), spotifyCall);
            System.out.println("Call recorded" + "\n");
        } catch (CustomerNotFoundException e) {
            System.out.println("Call was not recorded");
            throw new RuntimeException(e);
        }

        service.newCustomer(klarna);
        System.out.println("Klarna registered");

        service.newCustomer(volvo);
        System.out.println("Volvo registered" + "\n");

        // Test for updating email for klarna:
        Customer klarnaWithDifferentEmail = new Customer("KLA01", "Klarna AB", "differentEmail@klarna.se", "987654321", "Some other notes");
        try {
            service.updateCustomer(klarnaWithDifferentEmail);
            System.out.println("Klarna updated");
            //Here we can even find the customer by referencing the original customers id,
            // since they are the same
            System.out.println(service.findCustomerById("KLA01"));
        } catch (CustomerNotFoundException e) {
            System.out.println("Klarna was not updated");
            throw new RuntimeException(e);
        }

        //Here we try and delete the customer and see if it is still in the list of customers:
        try {
            service.deleteCustomer(volvo); //Volvo should now be missing
            System.out.println("Volvo deleted");
            for (Customer customer : service.getAllCustomers()) {
                System.out.println(customer);
            }
        } catch (CustomerNotFoundException e) {
            System.out.println("Could not retrieve list of customers");
            throw new RuntimeException(e);
        }

        //Here we try to find customers by name:
        System.out.println("Customer found by name:");
        System.out.println(service.findCustomersByName("Spotify AB"));

        //Here we try to get the full customer-details from a customer(spotify):
        try {
            System.out.println(service.getFullCustomerDetail("SPO01"));
            System.out.println("Details of Spotify AB");
        } catch (CustomerNotFoundException e) {
            System.out.println("Customer not found");
            throw new RuntimeException(e);
        }

        Call newCall = new Call("Larry Wall called from Acme Corp");
        Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2025, Calendar.JULY, 1), "rac");
        Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2025, Calendar.JUNE, 27), "rac");

        List<Action> actions = new ArrayList<>();
        actions.add(action1);
        actions.add(action2);

        try{
            callService.recordCall("SPO01", newCall, actions);
            System.out.println("Call recorded");
        }catch (CustomerNotFoundException e){
            System.out.println("That customer doesn't exist");
        }

        System.out.println("Here are the outstanding actions:");
        Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
            for (Action next: incompleteActions){
                System.out.println(next);
            }


//        Code tests for Inlämning 3
//                PART 1: Test for printing info on all customers:
//                CustomerManagementService customerService = container.getBean("customerManagement",CustomerManagementService.class);
//
//                for (Customer customer : customerService.getAllCustomers()) {
//                    System.out.println(customer);
//                }
//
//                PART 2: Record a call
//                CustomerManagementService customerService = container.getBean("customerManagementService", CustomerManagementService.class);
//                CallHandlingService callService = container.getBean("callHandlingService", CallHandlingService.class);
//                DiaryManagementService diaryService = container.getBean("diaryManagementService", DiaryManagementService.class);
//
//                customerService.newCustomer(new Customer("SPO1", "Spotify AB", "Good Customer"));
//
//                Call newCall = new Call("Larry Wall called from Acme Corp");
//                Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2025, Calendar.JULY, 1), "rac");
//                Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2025, Calendar.JUNE, 27), "rac");
//
//                List<Action> actions = new ArrayList<>();
//                actions.add(action1);
//                actions.add(action2);
//
//                try{
//                    callService.recordCall("SPO1", newCall, actions);
//                    System.out.println("Call recorded");
//                }catch (CustomerNotFoundException e){
//                    System.out.println("That customer doesn't exist");
//                }
//
//                System.out.println("Here are the outstanding actions:");
//                Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
//                for (Action next: incompleteActions){
//                    System.out.println(next);
//                }


        container.close();
    }
}