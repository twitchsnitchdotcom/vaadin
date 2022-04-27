package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.storedobject.chart.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Route(value = "nft", layout = MainLayout.class)
@PageTitle("NFT | Traidable")
@PermitAll
public class NFTView extends VerticalLayout {
    private final CrmService service;

    public NFTView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(slider(), chart(), lineChart(), multipleLines(), circularTreeChart(), barChartReversed(), shapes());
    }



    public Component lineChart() {
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

// Generating some random values for a LineChart
        Random random = new Random();
        Data xValues = new Data(), yValues = new Data();
        for (int x = 0; x < 40; x++) {
            xValues.add(x);
            yValues.add(random.nextDouble());
        }
        xValues.setName("X Values");
        yValues.setName("Random Values");

// Line chart is initialized with the generated XY values
        LineChart lineChart = new LineChart(xValues, yValues);
        lineChart.setName("40 Random Values");

// Line chart needs a coordinate system to plot on
// We need Number-type for both X and Y axes in this case
        XAxis xAxis = new XAxis(DataType.NUMBER);
        YAxis yAxis = new YAxis(DataType.NUMBER);
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        lineChart.plotOn(rc);

// Add to the chart display area with a simple title
        soChart.add(lineChart, new Title("Sample Line Chart"));

// Add to my layout
        return soChart;
    }

    public Component multipleLines() {
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("600px", "650px");

// Generating 10 set of values for 10 LineCharts for the equation:
// y = a + a * x / (a - 11) where a = 1 to 10, x and y are positive
        LineChart[] lineCharts = new LineChart[10];
        Data[] xValues = new Data[lineCharts.length];
        Data[] yValues = new Data[lineCharts.length];
        int i;
        for (i = 0; i < lineCharts.length; i++) {
            xValues[i] = new Data();
            xValues[i].setName("X (a = " + (i + 1) + ")");
            yValues[i] = new Data();
            yValues[i].setName("Y (a = " + (i + 1) + ")");
        }
// For each line chart, we need only 2 end-points (because they are straight lines).
        int a;
        for (i = 0; i < lineCharts.length; i++) {
            a = i + 1;
            xValues[i].add(0);
            yValues[i].add(a);
            xValues[i].add(11 - a);
            yValues[i].add(0);
        }

// Line charts are initialized here
        for (i = 0; i < lineCharts.length; i++) {
            lineCharts[i] = new LineChart(xValues[i], yValues[i]);
            lineCharts[i].setName("a = " + (i + 1));
        }

// Line charts need a coordinate system to plot on
// We need Number-type for both X and Y axes in this case
        XAxis xAxis = new XAxis(DataType.NUMBER);
        YAxis yAxis = new YAxis(DataType.NUMBER);
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        for (i = 0; i < lineCharts.length; i++) {
            lineCharts[i].plotOn(rc);
            soChart.add(lineCharts[i]); // Add the chart to the display area
        }

// Add a simple title too
        soChart.add(new Title("Equation: y = a + a * x / (a - 11) where a = 1 to 10, x and y are positive"));

// We don't want any legends
        soChart.disableDefaultLegend();

// Add it to my layout
        return soChart;
    }

    public Component circularTreeChart() {
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

// Tree chart
// (By default it assumes circular shape. Otherwise, we can set orientation)
// All values are randomly generated
        TreeChart tc = new TreeChart();
        TreeData td = new TreeData("Root", 1000);
        tc.setTreeData(td);
        Random r = new Random();
        for (int i = 1; i < 21; i++) {
            td.add(new TreeData("Node " + i, r.nextInt(500)));
        }
        TreeData td1 = td.get(13);
        td = td.get(9);
        for (int i = 50; i < 56; i++) {
            td.add(new TreeData("Node " + i, r.nextInt(500)));
        }
        for (int i = 30; i < 34; i++) {
            td1.add(new TreeData("Node " + i, r.nextInt(500)));
        }

// Add to the chart display area with a simple title
        soChart.add(tc, new Title("A Circular Tree Chart"));

// Finally, add it to my layout
        return soChart;
    }

//    public Component datamatricCharts() {
//        // Creating a chart display area
//        SOChart soChart = new SOChart();
//        soChart.setSize("800px", "500px");
//
//        // To hold multiple charts
//        List<Chart> charts = new ArrayList<>();
//
//        // Create multiple charts
//        createCharts(charts);
//
//        // Add the chart component(s) to the chart display area
//        charts.forEach(soChart::add);
//
//        // Add to my layout
//        return soChart;
//    }
//
//    private void createCharts(List<Chart> charts) {
//        // Define a data matrix to hold production data.
//        DataMatrix dataMatrix = new DataMatrix("Production in Million Tons");
//        // Columns contain products
//        dataMatrix.setColumnNames("Matcha Latte", "Milk Tea", "Cheese Cocoa");
//        dataMatrix.setColumnDataName("Products");
//        // Rows contain years of production
//        dataMatrix.setRowNames("2012", "2013", "2014", "2015");
//        dataMatrix.setRowDataName("Years");
//        // Add row values
//        dataMatrix.addRow(41.1, 86.5, 24.1);
//        dataMatrix.addRow(30.4, 92.1, 24.1);
//        dataMatrix.addRow(31.9, 85.7, 67.2);
//        dataMatrix.addRow(53.3, 85.1, 86.4);
//
//        // Define axes
//        XAxis xAxisProduct = new XAxis(DataType.CATEGORY);
//        xAxisProduct.setName(dataMatrix.getColumnDataName());
//        XAxis xAxisYear = new XAxis(DataType.CATEGORY);
//        xAxisYear.setName(dataMatrix.getRowDataName());
//        YAxis yAxis = new YAxis(DataType.NUMBER);
//        yAxis.setName(dataMatrix.getName());
//
//        // First rectangular coordinate
//        RectangularCoordinate rc1 = new RectangularCoordinate();
//        rc1.addAxis(xAxisProduct, yAxis);
//        rc1.getPosition(true)
//                .setBottom(Size.percentage(55)); // Position it leaving 55% space at the bottom
//        // Second rectangular coordinate
//        RectangularCoordinate rc2 = new RectangularCoordinate();
//        rc2.addAxis(xAxisYear, yAxis); // Same Y-axis is re-used here
//        rc2.getPosition(true).setTop(Size.percentage(55)); // Position it leaving 55% space at the top
//
//        // Bar chart variable
//        BarChart bc;
//
//        // Crate a bar chart for each data row
//        for (int i = 0; i < dataMatrix.getRowCount(); i++) {
//            bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
//            bc.setName(dataMatrix.getRowName(i));
//            bc.plotOn(rc1);
//            charts.add(bc);
//        }
//        // Crate a bar chart for each data column
//        for (int i = 0; i < dataMatrix.getColumnCount(); i++) {
//            bc = new BarChart(dataMatrix.getRowNames(), dataMatrix.getColumn(i));
//            bc.setName(dataMatrix.getColumnName(i));
//            bc.plotOn(rc2);
//            charts.add(bc);
//        }
//    }

    public Component barChartReversed() {
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("900px", "500px");

        // Let us define some inline data
        CategoryData labels =
                new CategoryData("April Fool's Day", "Marriage Day", "Election Day", "Any Other Day");
        Data data = new Data(5, 20, 100, 2);

        // Axes
        XAxis xAxis;
        YAxis yAxis;

        // Bar chart
        BarChart bc1 = new BarChart(labels, data); // First bar chart
        xAxis = new XAxis(labels);
        xAxis.getLabel(true).setRotation(45);
        yAxis = new YAxis(data);
        RectangularCoordinate coordinate = new RectangularCoordinate(xAxis, yAxis);
        bc1.plotOn(coordinate); // Bar chart needs to be plotted on a coordinate system
        coordinate.getPosition(true).setRight(Size.percentage(60)); // Leave space on the right side

        BarChart bc2 = new BarChart(data, labels); // Second bar chart
        xAxis = new XAxis(data);
        yAxis = new YAxis(labels);
        coordinate = new RectangularCoordinate(xAxis, yAxis);
        bc2.plotOn(coordinate); // Bar chart needs to be plotted on a coordinate system
        coordinate.getPosition(true).setLeft(Size.percentage(60)); // Leave space on the left side

        // Just to demonstrate it, we are creating a "Download" and a "Zoom" toolbox button
        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());

        // Switching off the default legend
        soChart.disableDefaultLegend();

        // Let's add some titles
        Title title = new Title("Probability of Getting Fooled");
        title.setSubtext("Truth is always simple but mostly hidden - Syam");

        // Add the chart components to the chart display area
        soChart.add(bc1, bc2, toolbox, title);

        // Add to the view
        return soChart;

    }

    public Component shapes() {
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");
        // Examples of shapes that can be added to SOChart
        ShapeGroup shapes = new ShapeGroup(); // Optional grouping
        shapes.setZ(100);
        shapes.getPosition(true).center();
        Ring ring = new Ring(100, 20);
        ring.setDraggable(true);
        var style = ring.getStyle(true);
        style.setStrokeColor(new Color("red"));
        Text text = new Text("Hello World!\nHow are you?");
        text.getStyle(true).setStrokeColor(new Color("red"));
        Font font = new Font(Font.Family.fantasy(), Font.Size.x_large());
        font.setStyle(Font.Style.OBLIQUE);
        text.setFont(font);
        Sector arc = new Sector(80, 0, 45);
        arc.getStyle(true).setFillColor(new Color("yellow"));
        Rectangle rectangle = new Rectangle(40, 30, 20);
        rectangle.getStyle(true).setStrokeColor(new Color("red"));
        BezierCurve bc = new BezierCurve(new Shape.Point(0, 0),
                new Shape.Point(40, 40),
                new Shape.Point(30, 20));
        Polygon polygon = new Polygon(new Shape.Point(0, 0),
                new Shape.Point(-20, -30),
                new Shape.Point(10, -10));
        polygon.useBezierSmoothening(0.6, true);

// Grouped together. Also, can be added individually.
        shapes.add(ring, text, bc, rectangle, arc, polygon);

// Add to SOChart along with others like chart, legend, title etc.
        soChart.add(shapes);
        return soChart;
    }

    public Component chart() {

        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Let us define some inline data.
        CategoryData labels = new CategoryData("Banana", "Apple", "Orange", "Grapes");
        Data data = new Data(25, 40, 20, 30);

// We are going to create a couple of charts. So, each chart should be positioned
// appropriately.
// Create a self-positioning chart.
        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
        Position p = new Position();
        p.setTop(Size.percentage(50));
        nc.setPosition(p); // Position it leaving 50% space at the top

// Second chart to add.
        BarChart bc = new BarChart(labels, data);
        RectangularCoordinate rc;
        rc = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        p = new Position();
        p.setBottom(Size.percentage(55));
        rc.setPosition(p); // Position it leaving 55% space at the bottom
        bc.plotOn(rc); // Bar chart needs to be plotted on a coordinate system

// Just to demonstrate it, we are creating a "Download" and a "Zoom" toolbox button.
        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());

// Let's add some titles.
        Title title = new Title("My First Chart");
        title.setSubtext("2nd Line of the Title");

// Add the chart components to the chart display area.
        soChart.add(nc, bc, toolbox, title);

// Now, add the chart display (which is a Vaadin Component) to your layout.
        return soChart;
    }

    private Splide slider() {
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