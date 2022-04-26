package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.storedobject.chart.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "chartsview", layout = MainLayout.class)
@PageTitle("Charts | TwitchSnitch")
@PermitAll
public class ChartsView extends VerticalLayout {
    private final CrmService service;

    public ChartsView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(chartExample5());
    }

    private Component chartExample5() {

        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Define a data matrix to hold production data.
        DataMatrix dataMatrix = new DataMatrix("Production in Million Tons");
        // Columns contain products
        dataMatrix.setColumnNames("Matcha Latte", "Milk Tea", "Cheese Cocoa");
        dataMatrix.setColumnDataName("Products");
        // Rows contain years of production
        dataMatrix.setRowNames("2012", "2013", "2014", "2015");
        dataMatrix.setRowDataName("Years");
        // Add row values
        dataMatrix.addRow(41.1, 86.5, 24.1);
        dataMatrix.addRow(30.4, 92.1, 24.1);
        dataMatrix.addRow(31.9, 85.7, 67.2);
        dataMatrix.addRow(53.3, 85.1, 86.4);

        // Define a polar coordinate system with radius and angle.
        RadiusAxis radiusAxis = new RadiusAxis(DataType.CATEGORY);
        AngleAxis angleAxis = new AngleAxis(DataType.NUMBER);
        PolarCoordinate pc = new PolarCoordinate(radiusAxis, angleAxis);

        // Bar chart variable
        BarChart bc;
        // Create a bar chart for each row
        for (int i = 0; i < dataMatrix.getRowCount(); i++) {
            // Bar chart for the row
            bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
            bc.setName(dataMatrix.getRowName(i));
            // Plot that to the coordinate system defined
            bc.plotOn(pc);
            // Add that to the chart list
            soChart.add(bc);
        }
        return soChart;
    }
}