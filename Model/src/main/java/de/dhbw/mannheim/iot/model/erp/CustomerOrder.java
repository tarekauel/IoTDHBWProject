package de.dhbw.mannheim.iot.model.erp;

import de.dhbw.mannheim.erpsim.model.OrderPosition;
import de.dhbw.mannheim.iot.model.Model;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class CustomerOrder extends Model {

    private final String customerFirstName;

    private final String customerLastName;

    private final String customerNumber;

    private final long orderNumber;

    private final OrderPosition[] positions;

    public CustomerOrder(de.dhbw.mannheim.erpsim.model.CustomerOrder co) {
        customerFirstName = co.getCustomerFirstName();
        customerLastName = co.getCustomerLastName();
        customerNumber = co.getCustomerNumber();
        orderNumber = co.getOrderNumber();
        positions = co.getPositions();
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public OrderPosition[] getPositions() {
        return positions;
    }
}
