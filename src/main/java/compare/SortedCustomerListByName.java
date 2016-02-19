package compare;

import model.Bookmaker;
import model.Customer;

import java.util.Comparator;

/**
 * Created by Дом on 19.02.2016.
 */
public class SortedCustomerListByName implements Comparator<Customer> {

    public int compare(Customer o1, Customer o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();
        return str1.compareTo(str2);
    }
}
