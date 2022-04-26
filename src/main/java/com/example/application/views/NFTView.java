package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.vaadin.componentfactory.addons.splide.ImageSlide;
import com.vaadin.componentfactory.addons.splide.Splide;
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
import java.util.Arrays;

@Route(value = "nft", layout = MainLayout.class)
@PageTitle("NFT | Traidable")
@PermitAll
public class NFTView extends VerticalLayout {
    private final CrmService service;

    public NFTView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart(), slider());
    }

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        service.findAllCompanies().forEach(company ->
            dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployeeCount())));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Splide slider(){
        ImageSlide slide1 = new ImageSlide("images/slide_1.jpg");
        ImageSlide slide2 = new ImageSlide("images/slide_2.png");
        ImageSlide slide3 = new ImageSlide("https://source.unsplash.com/random/1000x1000?sig=3");

        Splide slider = new Splide(Arrays.asList(slide1, slide2, slide3));
        slider.setId("images-slider-demo");
        slider.setWidth("450px");
        slider.setHeight("300px");
        slider.getElement().getStyle().set("margin", "auto");
        return slider;
    }
}