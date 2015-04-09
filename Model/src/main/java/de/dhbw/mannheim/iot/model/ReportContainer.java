package de.dhbw.mannheim.iot.model;

import de.dhbw.mannheim.assemblylinesim.model.Report;

/**
 * Created by D059166 on 09.04.2015.
 */
public class ReportContainer implements Model {

    public Report report;

    public ReportContainer(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

}
