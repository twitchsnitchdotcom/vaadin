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
import de.f0rce.viz.Viz;
import de.f0rce.viz.VizFormat;

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
        add(viz(), slider());
    }

    private Component viz() {
        Viz viz = new Viz(); // height: 300px - width: 600px
        viz.setFormat(VizFormat.svg); // default
        viz.setGraph("digraph H { a -> b }"); // simple dot notation
        return viz;
    }


    private Splide slider(){
        ImageSlide slide1 = new ImageSlide("https://lh3.googleusercontent.com/z5ZYxxEMXyYZcnFbDnYg5HEMOUKUIvmgxp89ohL4NVHYomoa5PIS56JPg3FFSQ1DYb_rTlemD8RLsNBzYW1SFoG6L5C4DlC3osAP=w335");
        ImageSlide slide2 = new ImageSlide("https://lh3.googleusercontent.com/fRREGr2ADoIG1FILZjEBoaWb2A6FpJDHebH0xM9P8OPgGfJ_eQiDnnJqtbb4zQVuQ0uPpUQxXTx2wfYZpqM_jRsk_nGuht_xNuHsKGU=w335");
        ImageSlide slide3 = new ImageSlide("https://lh3.googleusercontent.com/HLlVT316u7fQRJX6h8solpLuELHlXfqdjAioS2aPvZcNf4G_MIAMgsVgmbUVt9aFyoYh1x5LmJPHacqx3FYdnq-nQoL5KRKdYgN2NA=w335");

        Splide slider = new Splide(Arrays.asList(slide1, slide2, slide3));
        slider.setId("images-slider-demo");
        slider.setWidth("450px");
        slider.setHeight("300px");
        slider.getElement().getStyle().set("margin", "auto");
        return slider;
    }
}