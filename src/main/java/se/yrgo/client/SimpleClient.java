package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
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
     * Run the PART 1 code first tos ee that we can get all of the customers with the CustomerManagementService-interface.
     * Then comment it out before testing part two.
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

//        PART 1: Test for printing info on all customers:
        CustomerManagementService customerService = container.getBean("customerManagement",CustomerManagementService.class);

        for (Customer customer : customerService.getAllCustomers()) {
            System.out.println(customer);
        }

//        PART 2: Record a call
//        CustomerManagementService customerService = container.getBean("customerManagement", CustomerManagementService.class);
//        CallHandlingService callService = container.getBean("callHandling", CallHandlingService.class);
//        DiaryManagementService diaryService = container.getBean("diaryManagement", DiaryManagementService.class);
//
//        customerService.newCustomer(new Customer("SPO1", "Spotify AB", "Good Customer"));
//
//        Call newCall = new Call("Larry Wall called from Acme Corp");
//        Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2025, Calendar.JULY, 1), "rac");
//        Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2025, Calendar.JUNE, 27), "rac");
//
//        List<Action> actions = new ArrayList<>();
//        actions.add(action1);
//        actions.add(action2);
//
//        try{
//            callService.recordCall("SPO1", newCall, actions);
//            System.out.println("Call recorded");
//        }catch (CustomerNotFoundException e){
//            System.out.println("That customer doesn't exist");
//        }
//
//        System.out.println("Here are the outstanding actions:");
//        Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
//        for (Action next: incompleteActions){
//            System.out.println(next);
//        }

        container.close();
    }
}