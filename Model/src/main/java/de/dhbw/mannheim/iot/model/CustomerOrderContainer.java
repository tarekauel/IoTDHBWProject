package de.dhbw.mannheim.iot.model;

import de.dhbw.mannheim.erpsim.model.CustomerOrder;

/**
 * Created by D059166 on 09.04.2015.
 */
public class CustomerOrderContainer implements Model {

    private CustomerOrder customerOrder;

    public CustomerOrderContainer(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

}
