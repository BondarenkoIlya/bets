package factory;

import model.Bookmaker;
import model.Customer;

/**
 * Created by Дом on 18.02.2016.
 */
public class CustomerFactory {
    public Customer createCustomer(String name){
        Customer customer = new Customer(name);
        Bookmaker.customerList.add(customer);//Почему только с большой буквы?
        return customer;
    }
}
