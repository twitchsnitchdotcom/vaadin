package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.storedobject.chart.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Route(value = "chartsview", layout = MainLayout.class)
@PageTitle("Charts | TwitchSnitch")
@PermitAll
public class ChartsView extends VerticalLayout {
    private final CrmService service;

    public ChartsView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(chartExample5(), chartExample2(), chartExample1(), chartExample6(), chartExample3(),
                chartExample7(), chartExample4(), demoStackedBarChart(), barChartWIthLabelsInside(),
                radarChartDemo(), dualFoolChat(), gaugeChart(), demoLineChart(), sunburstChart(),
                funnelChart(), multipleNeedleGauge(), scatterChart(), bubbleChart(), heatMapChart(),
                lineChartWithMarkArea(), lineChartWithTooltip(), gantChart(), xRangeChart(), activityChart());
    }

    private Component scatterChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Random X & Y integers
        Random r = new Random();
        Data xData = new Data(), yData = new Data();
        for (int i = 0; i < 60; i++) {
            xData.add(r.nextInt(24));
            yData.add(r.nextInt(24));
        }

        // Scatter chart
        ScatterChart sc = new ScatterChart(xData, yData);
        XAxis xAxis = new XAxis(xData);
        YAxis yAxis = new YAxis(yData);
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        sc.plotOn(rc);

        // Add it to the chart display
        soChart.add(sc);

        // Set the component for the view
        return soChart;
    }

    private Component bubbleChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Bubble chart requires 2 axes and then, values to be added for each data-point
        CategoryData days = new CategoryData("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        CategoryData slots = new CategoryData("Morning", "Noon", "Afternoon", "Evening", "Night");

        // Create the chart.
        BubbleChart chart = new BubbleChart(days, slots);
        chart.setBubbleSize(2); // Size of the bubble will be 2 times the temperature
        chart.setValueSuffix("\u00B0C");

        // Add some data-points
        chart.addData(0, 0, 27); // Sunday morning
        chart.addData(0, 3, 28); // Sunday evening
        chart.addData(1, 3, 31); // Monday evening
        chart.addData(1, 4, 25); // Monday night
        chart.addData("Wed", "Noon", 37); // Values can be added by directly addressing X/Y values too.

        // Bubble charts should be plotted on a rectangular coordinate system
        chart.plotOn(new RectangularCoordinate(new XAxis(days), new YAxis(slots)));

        // Add to the chart display area
        soChart.add(chart);

        // Set the component for the view
        return soChart;
    }

    private Component heatMapChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Heatmap chart requires 2 category axes and then, values to be added for each data-point
        CategoryData days = new CategoryData("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        CategoryData slots = new CategoryData("Morning", "Noon", "Afternoon", "Evening", "Night");

        // Create the chart.
        HeatmapChart chart = new HeatmapChart(days, slots);
        chart.getLabel(true).show(); // Want to display the value as labels

        // Add some data-points
        chart.addData(0, 0, 27); // Sunday morning
        chart.addData(0, 3, 28); // Sunday evening
        chart.addData(1, 3, 31); // Monday evening
        chart.addData(1, 4, 25); // Monday night
        chart.addData("Wed", "Noon", 37); // Values can be added by directly addressing X/Y values too.

        // Heatmap charts should be plotted on a rectangular coordinate system
        chart.plotOn(new RectangularCoordinate(new XAxis(days), new YAxis(slots)));

        // Add to the chart display area
        soChart.add(chart);

        // Set the component for the view
        return soChart;
    }

    private Component lineChartWithMarkArea(){
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

        // We need Number-type for both X and Y axes in this case
        XAxis xAxis = new XAxis(DataType.NUMBER);
        YAxis yAxis = new YAxis(DataType.NUMBER);

        // Mark area (From x = 20 to x = 30)
        MarkArea.Block area20_30 = new MarkArea.Block();
        area20_30.setName("Area (20 to 30)");
        area20_30.setSides(xAxis, 20, 30);

        // Mark area (Both X and Y axis are specified).
        MarkArea.Block rectBlock = new MarkArea.Block();
        rectBlock.setName("Rectangular Block");
        rectBlock.setSides(xAxis, yAxis, 15, 0.2, "max", 0.8);
        rectBlock.getItemStyle(true).setColor(new Color("red"));

        // Add Mark Areas to the chart.
        lineChart.getMarkArea(true).add(area20_30, rectBlock);

        // Line chart needs a coordinate system to plot on
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        lineChart.plotOn(rc);

        // Add to the chart display area with a simple title
        soChart.add(lineChart, new Title("Sample Line Chart"));

        // Set the component for the view
        return soChart;
    }

    private Component lineChartWithTooltip(){
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

        // Customize tooltips of the line chart
        AbstractDataProvider<?> yFormattedValues =
                yValues.create(
                        DataType.CATEGORY,
                        (v, i) ->
                                v.toString()
                                        .substring(0, 4)); // Specially formatted Y values (Bad code to trim decimals!)
        lineChart
                .getTooltip(true) // Get the tooltip
                .append("My Special Tooltip") // Added some text
                .newline() // New line
                .append("X = ")
                .append(xValues) // X values
                .newline() // New line
                .append("Y = ")
                .append(yFormattedValues); // Customized Y values

        // Add to the chart display area with a simple title
        soChart.add(lineChart, new Title("Line Chart with Customized Tooltips"));

        // Set the component for the view
        return soChart;
    }

    private Component gantChart(){
        // Define a chart component
        SOChart soChart = new SOChart();
        soChart.setSize("900px", "500px");

        // Sample project with few entries
        Project project = new Project();
        project.setStart(LocalDateTime.now().minusDays(10));
        Project.TaskGroup tg1 = project.createTaskGroup("Group 1");
        Project.Task tg1T1 = tg1.createTask("Task 1/1", 6);
        Project.Task tg1T2 = tg1.createTask("Task 1/2", 5);
        tg1T2.setCompleted(100); // This task is 100% complete
        project.dependsOn(tg1T2, tg1T1);
        Project.Task tg1T3 = tg1.createTask("Task 1/3", 11);
        Project.Task milestone = tg1.createTask("Example Milestone", 0); // Milestone
        project.dependsOn(milestone, tg1T3);
        project.dependsOn(tg1T2, milestone);
        Project.TaskGroup tg2 = project.createTaskGroup("Group 2");
        Project.Task tg2T1 = tg2.createTask("Task 2/1", 3);
        Project.Task tg2T2 = tg2.createTask("Task 2/2", 7);
        Project.Task tg2T3 = tg2.createTask("Task 2/3", 13);
        tg2.createTask("Task 2/4", 9);
        tg2T3.setColor(new Color("green")); // Specific color for this task
        tg2T1.setCompleted(35); // This task is 35% complete
        project.dependsOn(tg2T2, tg1);
        project.dependsOn(tg1T2, tg2T1);

        // Plot the project on a Gantt Chart
        GanttChart gc = new GanttChart(project);

        // Add the Gantt Chart to our chart component
        soChart.add(gc);

        // Set the component for the view
        return soChart;
    }

    private Component xRangeChart(){
        // Define a chart component
        SOChart soChart = new SOChart();
        soChart.setSize("100%", "100%");

        XRangeChart<Number, String> xrc1 = new XRangeChart<>(); // First chart
        // Add some range values
        xrc1.addData(4, 6, "Y5", new Color("red"));
        xrc1.addData(5, 8, "Y4", new Color("yellow"));
        xrc1.addData(1, 4, "Y4", "Delayed", new Color("pink"));
        xrc1.addData(8, 12, "Y2", new Color("blue"));
        xrc1.setYData(
                new CategoryData(
                        "Y1", "Y2", "Y3", "Y4", "Y5")); // We want the Y-axis to be of category type
        xrc1.getCoordinateSystem()
                .getPosition(true)
                .setBottom(Size.percentage(55)); // Leve 55% space below
        soChart.add(xrc1); // Add to the chart component
        XRangeChart<Number, Number> xrc2 = new XRangeChart<>(); // Second chart
        // Add some range values
        xrc2.addData(4, 6, 0, "Test Label", new Color("red"));
        xrc2.addData(4, 6, 5, "Another Label", new Color("red"));
        xrc2.addData(5, 8, 4, new Color("yellow"), 20);
        xrc2.addData(1, 4, 4, new Color("pink"), 50);
        xrc2.addData(8, 12, 2, "Completed", new Color("blue"), 100);
        xrc2.getCoordinateSystem()
                .getPosition(true)
                .setTop(Size.percentage(55)); // Leve 55% space above
        xrc2.getYAxis().setMax(6); // Y-axis is of numeric type (default). Setting max value
        xrc2.showProgressLabel(true); // Show progress values
        xrc2.getYAxis().setMin(-1); // Setting a custom min value
        xrc2.getXZoom(true); // Create X-axis zoom
        soChart.add(xrc2); // Add to the chart component

        // Set the component for the view
        return soChart;
    }

    private Component activityChart(){
        // Define a chart component
        SOChart soChart = new SOChart();
        soChart.setWidth("100%");

        // Activity list
        ActivityList activityList = new ActivityList();
        activityList.setStart(LocalDateTime.now()); // Set the start date
        // Create some activities
        ActivityList.ActivityGroup tg1 = activityList.createActivityGroup("Group 1"); // Group 1
        tg1.setExtraInfo("Cleaning activities"); // Some extra info
        ActivityList.Activity tg1T1 = // An activity that belongs to group 1
                tg1.createActivity("Activity 1/1", LocalDateTime.now().minusDays(10), 6);
        // Add another one just after the first onw
        ActivityList.Activity tg1T2 = tg1T1.createNext("Activity 1/2", 5);
        tg1T2.setCompleted(100); // This activity is 100% done
        ActivityList.Activity tg1T3 = tg1T2.createNext("Activity 1/3", 11); // Next
        tg1T3
                .createNext("Activity 1/4", 10)
                .setExtraInfo("Some extra info"); // Extra info: Will be shown as part of the tooltip
        ActivityList.ActivityGroup tg2 = activityList.createActivityGroup("Group 2"); // Group 2
        tg2.setExtraInfo("Other tasks"); // Some extra info
        // Add some activities under group 2 too
        ActivityList.Activity tg2T1 = tg2.createActivity("Activity 2/1", LocalDateTime.now(), 3);
        ActivityList.Activity tg2T2 = tg2T1.createNext("Activity 2/2", 7);
        ActivityList.Activity tg2T3 = tg2T2.createNext("Activity 2/3", 13);
        tg2T3.createNext("Activity 2/4", 9);
        tg2T3.setColor(new Color("green")); // Specific color for this task
        tg2T1.setCompleted(35); // This activity is 35% complete

        // Plot the activities on an Activity Chart
        ActivityChart ac = new ActivityChart(activityList);
        ac.getTimeAxisZoom().hide(); // Hiding the time-axis zoom

        // Add the chart to the chart component
        soChart.add(ac);

        // Set the component for the view
        return soChart;
    }

    private Component multipleNeedleGauge(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Gauge chart
        GaugeChart gc = new GaugeChart();
        gc.setMin(0);
        gc.setMax(240);
        gc.setValue(96);
        Label label = gc.getAxisLabel(true);
        label.setFontStyle(new Font(null, Font.Size.number(10)));
        label.setFormatter("{value}km/h");
        gc.addDialZone(50, new Color("blue"));
        gc.addDialZone(80, new Color(255, 246, 0));
        gc.addDialZone(100, new Color("red"));

        // Add it to the chart display
        soChart.add(gc);

        return soChart;
    }

    private Component funnelChart(){
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");
        FunnelChart fc =
                new FunnelChart(
                        new CategoryData("Twenty", "Forty", "Fifty", "EightY"), new Data(20, 40, 50, 80));

        // Add it to the chart display.
        soChart.add(fc);
        return soChart;
    }

    private Component sunburstChart(){

        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        SunburstChart chart =
                new SunburstChart(
                        new TreeData("Grandpa")
                                .add(new TreeData("Father", 10).add("Me", 5).add("Brother Peter", 1))
                                .add(
                                        new TreeData("Uncle Leo", 15)
                                                .add(new TreeData("Cousin Mary", 5).add("Jackson", 2))
                                                .add("Cousin Ben", 4)
                                                .add("Cousin Jack", 2)),
                        new TreeData("Nancy")
                                .add(new TreeData("Uncle Nike").add("Cousin Jetty", 1).add("Cousin Jenny", 2)));
        chart.setRadius(Size.percentage(90));

        // Add to the chart display area
        soChart.add(chart);

        // Set the component for the view
        return soChart;
    }

    private Component demoLineChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Generating some random values for a LineChart
        Random random = new Random();
        DateData xValues = new DateData();
        Data yValues = new Data();
        for (int x = 0; x < 12; x++) {
            xValues.add(LocalDate.of(2021, x + 1, 1));
            yValues.add(random.nextDouble());
        }
        xValues.setName("Months of 2021");
        yValues.setName("Random Values");

        // Line chart is initialized with the generated XY values
        LineChart lineChart = new LineChart(xValues, yValues);
        lineChart.setName("12 Random Values");

        // Adhoc features (for demo purpose)
        LineStyle lineStyle = lineChart.getLineStyle(true);
        lineStyle.setColor(new Color("red"));
        lineStyle.setWidth(4);
        ColorGradient cg =
                new ColorGradient(new Color("blue"), new Color("green"), new Color("yellow"));
        cg.setGradient(0, 0, 100, 100);
        lineChart.getAreaStyle(true).setColor(cg);
        PointSymbol ps = lineChart.getPointSymbol(true);
        ps.setType(PointSymbolType.DIAMOND);
        ps.setSize(15, 15);
        lineChart.setColors(new Color("black")); // Data-points should be in black
        lineChart.setSmoothness(100); // Make it very smooth

        // Define axes
        YAxis yAxis = new YAxis(yValues);
        yAxis.setName("Randoms");
        XAxis xAxis = new XAxis(xValues);
        xAxis.setMinAsMinData(); // We want to start the X axis from minimum of our data
        xAxis.getLabel(true).setFormatter("{MMM} {d}"); // Format the date
        xAxis.setName("2021");

        // Set some features on the X-axis for demo purpose
        Axis.GridAreas ga = xAxis.getGridAreas(true); // To customize grid areas
        AreaStyle as = ga.getStyle(true);
        as.setColors(new Color("D3EFED"), new Color("E9DBEF")); // Alternate colors for intervals

        // Coordinate system
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        lineChart.plotOn(rc); // Plot on a rectangular coordinate.

        // Title for the chart
        Title title = new Title("Smooth Line Chart");
        title.setSubtext("To demo adhoc chart features");
        title.getPosition(true).setLeft(Size.percentage(20)); // Leave 20% space on the left side

        // Add to the chart display area with a simple title
        soChart.add(lineChart, title);

        // Set the component for the view
        return soChart;
    }

    private Component gaugeChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Gauge chart
        GaugeChart gaugeChart = new GaugeChart();
        gaugeChart.setValue(36);

        // Add it to the chart display
        soChart.add(gaugeChart);

        return soChart;
    }
    private Component dualFoolChat(){
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

        BarChart bc2 = new BarChart(data, labels); // Second bar chart - axes reversed
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

        // Set the component for the view - our chart wrapped in a layout.
        return soChart;
    }

    private Component radarChartDemo(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Legs for the radar chart (We want a 4-legged radar here)
        CategoryData legs = new CategoryData("Quarter 1", "Quarter 2", "Quarter 3", "Quarter 4");
        Data budget = new Data(3500, 3200, 2500, 5000); // Sales budgets for each quarter
        budget.setName("Budget");
        Data sales = new Data(3200, 4500, 1300, 4000); // Sales for each quarter
        sales.setName("Sales");

        // Radar chart
        RadarChart chart = new RadarChart();
        chart.addData(budget, sales);

        // Radar chart needs to be plotted on a Radar Coordinate system
        RadarCoordinate radarCoordinate = new RadarCoordinate(legs);
        radarCoordinate.setCircular(true);
        chart.plotOn(radarCoordinate);

        // Add to the chart display area
        soChart.add(chart);

        // Set the component for the view
        return soChart;
    }
    
    private Component barChartWIthLabelsInside(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Generating some random values for the bar charts
        Random random = new Random();
        CategoryData xValues = new CategoryData();
        Data yValues1 = new Data(), yValues2 = new Data();
        for (int x = 0; x <= 11; x++) {
            xValues.add("" + (2010 + x));
            yValues1.add(random.nextInt(100));
            yValues2.add(random.nextInt(100));
        }

        // Define axes
        XAxis xAxis = new XAxis(xValues);
        xAxis.setMinAsMinData();
        YAxis yAxis1 = new YAxis(yValues1), yAxis2 = new YAxis(yValues2);

        // Bar charts are initialized with the generated XY values
        BarChart barChart1 = new BarChart(xValues, yValues1);
        barChart1.setName("Wheat");
        BarChart barChart2 = new BarChart(xValues, yValues2);
        barChart2.setName("Rice");
        barChart2.setBarGap(0);

        // Create and customize value-labels for one of the charts
        Chart.Label label = barChart1.getLabel(true);
        // Value-label takes the y-vales and chart name.
        // Part name "black" is defined further down
        label.setFormatter("{1} {black|{chart}}");
        label.setInside(true);
        label.setGap(15);
        label.setRotation(90);
        label.getPosition().bottom();
        com.storedobject.chart.Alignment alignment = label.getAlignment(true);
        alignment.alignCenter();
        alignment.justifyLeft();

        // Create a rich text part for the label
        RichTextStyle rich = label.getRichTextStyle(true);
        // Create a "specially styled part" named "black"
        TextStyle richText = rich.get("black", true);
        // Set color to black for that part.
        richText.setColor(new Color("black"));

        // Set the same customized label to the second chart too
        barChart2.setLabel(label);

        // Use a coordinate system
        RectangularCoordinate rc = new RectangularCoordinate();
        rc.addAxis(xAxis, yAxis1, yAxis2);
        barChart1.plotOn(rc);
        barChart2.plotOn(rc);

        // Add the coordinate system to the chart display (all attached charts will be automatically
        // added)
        soChart.add(rc);

        // Set the component for the view
        return soChart;
    }

    private Component demoStackedBarChart(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Generating some random values for a LineChart
        Random random = new Random();
        DateData xValues = new DateData();
        Data yValues1 = new Data(), yValues2 = new Data();
        for (int x = 0; x < 12; x++) {
            xValues.add(LocalDate.of(2021, x + 1, 1));
            yValues1.add(random.nextDouble());
            yValues2.add(random.nextDouble());
        }
        xValues.setName("Months of 2021");
        yValues1.setName("Random Values");

        // Bar charts is initialized with the generated XY values
        BarChart barChart1 = new BarChart(xValues, yValues1);
        barChart1.setName("Bar #1");
        barChart1.setStackName(
                "BC"); // Just a name - should be same for all the charts on the same stack
        BarChart barChart2 = new BarChart(xValues, yValues2);
        barChart2.setName("Bar #2");
        barChart2.setStackName(
                "BC"); // Just a name - should be same for all the charts on the same stack

        // Add a line chart too for demo purpose
        LineChart lineChart = new LineChart(xValues, yValues1);
        lineChart.setName("Line #1");

        // Define axes
        YAxis yAxis = new YAxis(yValues1); // Just need the value type as parameter
        XAxis xAxis = new XAxis(xValues);
        xAxis.setMinAsMinData(); // We want to start the X axis from minimum of our data
        xAxis.getLabel(true).setFormatter("{MMM}"); // Format the date
        xAxis.setName("2021");

        // Coordinate system
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        barChart1.plotOn(rc); // Plot on the rectangular coordinate.
        barChart2.plotOn(rc); // Also you could do rc.add(barChart1, barChart2, lineChart);
        lineChart.plotOn(rc);

        // Title for the chart
        Title title = new Title("Stacked Bars & a Line Chart");
        title.setSubtext("To demo stacking feature");
        title.getPosition(true).setLeft(Size.percentage(10)); // Leave 10% space on the left side

        // We want to customize the legend's position
        soChart.disableDefaultLegend();
        Legend legend = new Legend();
        legend.getPosition(true).setRight(Size.percentage(10)); // Leave 10% space on the right

        // Add to the chart display area with a simple title and our custom legend
        // (Since rc is added, no need to add the charts already plotted on it)
        soChart.add(rc, title, legend);

        // Set the component for the view
        return soChart;
    }
    
    private Component chartExample4(){
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

        // Set the component for the view
        return soChart;
    }


    private Component chartExample7(){
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

        // Define axes
        XAxis xAxisProduct = new XAxis(DataType.CATEGORY);
        xAxisProduct.setName(dataMatrix.getColumnDataName());
        XAxis xAxisYear = new XAxis(DataType.CATEGORY);
        xAxisYear.setName(dataMatrix.getRowDataName());
        YAxis yAxis = new YAxis(DataType.NUMBER);
        yAxis.setName(dataMatrix.getName());

        // First rectangular coordinate
        RectangularCoordinate rc1 = new RectangularCoordinate();
        rc1.addAxis(xAxisProduct, yAxis);
        rc1.getPosition(true)
                .setBottom(Size.percentage(55)); // Position it leaving 55% space at the bottom
        // Second rectangular coordinate
        RectangularCoordinate rc2 = new RectangularCoordinate();
        rc2.addAxis(xAxisYear, yAxis); // Same Y-axis is re-used here
        rc2.getPosition(true).setTop(Size.percentage(55)); // Position it leaving 55% space at the top

        // Bar chart variable
        BarChart bc;

        // Crate a bar chart for each data row
        for (int i = 0; i < dataMatrix.getRowCount(); i++) {
            bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
            bc.setName(dataMatrix.getRowName(i));
            bc.plotOn(rc1);
            soChart.add(bc);
        }
        // Crate a bar chart for each data column
        for (int i = 0; i < dataMatrix.getColumnCount(); i++) {
            bc = new BarChart(dataMatrix.getRowNames(), dataMatrix.getColumn(i));
            bc.setName(dataMatrix.getColumnName(i));
            bc.plotOn(rc2);
            soChart.add(bc);
        }
        return soChart;
    }

    private Component chartExample3(){
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
        soChart.add(
                new Title("Equation: y = a + a * x / (a - 11) where a = 1 to 10, x and y are positive"));

        // We don't want any legends
        soChart.disableDefaultLegend();

        // Set the component for the view
        return soChart;
    }
    private Component chartExample6(){

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

        // Bar chart variable
        BarChart bc;

        // Define the fist rectangular coordinate.
        XAxis xAxis = new XAxis(DataType.CATEGORY);
        xAxis.setName(dataMatrix.getColumnDataName());
        YAxis yAxis = new YAxis(DataType.NUMBER);
        yAxis.setName(dataMatrix.getName());
        RectangularCoordinate rc = new RectangularCoordinate();
        rc.addAxis(xAxis, yAxis);

        // Create a bar chart for each row
        for (int i = 0; i < dataMatrix.getRowCount(); i++) {
            // Bar chart for the row
            bc = new BarChart(dataMatrix.getColumnNames(), dataMatrix.getRow(i));
            bc.setName(dataMatrix.getRowName(i));
            // Plot that to the coordinate system defined
            bc.plotOn(rc);
            // Add that to the chart list
            soChart.add(bc);
        }
        rc.getPosition(true).setBottom(Size.percentage(55));

        // Define the 2nd rectangular coordinate
        xAxis = new XAxis(DataType.CATEGORY);
        xAxis.setName(dataMatrix.getRowDataName());
        rc = new RectangularCoordinate();
        rc.addAxis(xAxis, yAxis);
        rc.getPosition(true).setTop(Size.percentage(55));

        // Create a bar chart for each column
        for (int i = 0; i < dataMatrix.getColumnCount(); i++) {
            // Bar chart for the row
            bc = new BarChart(dataMatrix.getRowNames(), dataMatrix.getColumn(i));
            bc.setName(dataMatrix.getColumnName(i));
            // Plot that to the coordinate system defined
            bc.plotOn(rc);
            // Add that to the chart list
            soChart.add(bc);
        }
        return soChart;
    }
    private Component chartExample1(){
        // Creating a chart display area
        SOChart soChart = new SOChart();
        soChart.setSize("800px", "500px");

        // Let us define some inline data
        CategoryData labels = new CategoryData("Banana", "Apple", "Orange", "Grapes");
        Data data = new Data(25, 40, 20, 30);

        // We are going to create a couple of charts. So, each chart should be positioned appropriately
        // Create a self-positioning chart
        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
        Position p = new Position();
        p.setTop(Size.percentage(50));
        nc.setPosition(p); // Position it leaving 50% space at the top

        // Second chart to add
        BarChart bc = new BarChart(labels, data);
        RectangularCoordinate coordinate =
                new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        p = new Position();
        p.setBottom(Size.percentage(55));
        coordinate.setPosition(p); // Position it leaving 55% space at the bottom
        bc.plotOn(coordinate); // Bar chart needs to be plotted on a coordinate system

        // Just to demonstrate it, we are creating a "Download" and a "Zoom" toolbox button
        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());

        // Let's add some titles
        Title title = new Title("My First Chart");
        title.setSubtext("2nd Line of the Title");

        // Add the chart components to the chart display area
        soChart.add(nc, bc, toolbox, title);
        return soChart;
    }


private Component chartExample2(){
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

    // Set the component for the view
    return soChart;
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