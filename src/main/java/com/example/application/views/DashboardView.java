package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.driverjs.DriverJS;
import org.vaadin.addon.driverjs.model.StepDefinitionBuilder;
import org.vaadin.addon.driverjs.model.StepPosition;

import javax.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Vaadin CRM")
@PermitAll
public class DashboardView extends VerticalLayout {

    private final CrmService service;

    public DashboardView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        HorizontalLayout titleBox = new HorizontalLayout();
        titleBox.setAlignItems(Alignment.BASELINE);
        H3 title = new H3("Vaadin Driver JS, ported to Vaadin v17");
        title.setId("title-id");
        titleBox.add(title);
        add(titleBox);

        Label label = new Label("A label :)");
        label.setWidth("100px");
        add(label);

        Label anotherline = new Label("Another label ;)");
        anotherline.setWidth("100px");
        add(anotherline);

        DriverJS driver = new DriverJS();
        driver.setStepDefinitions(StepDefinitionBuilder.ofComponent(title)
                        .withTitle("Title for the Popover").withDescription("Description for it"),
                StepDefinitionBuilder.ofComponent(label).withTitle("This the first label"),
                StepDefinitionBuilder.ofComponent(anotherline)
                        .withTitle("las one!").withPosition(StepPosition.RIGHT));

        Button sliderValue = new Button("Open driver tour");
        sliderValue.addClickListener(e -> driver.start());

        add(sliderValue);

        add(driver);

    }


}