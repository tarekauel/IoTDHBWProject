package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.model.Model;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public interface SimpleAlgorithm {

    void receive(Model model);

    Class<? extends Model> getModelClass();

}
