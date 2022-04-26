package com.example.application.views;

import com.example.application.data.Book;
import com.example.application.data.service.CrmService;
import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.*;

@Route(value = "twitchuser", layout = MainLayout.class)
@PageTitle("Twitch User | TwitchSnitch")
@PermitAll
public class TwitchUserView extends VerticalLayout {
    private final CrmService service;

    public TwitchUserView(CrmService service) {
        initializeData();
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dragAndDropDemo();
    }

    private final Set<Book> selectedBooks = new HashSet<>();
    private final List<Book> availableBooks = new ArrayList<>();

    private TwinColGrid<Book> twinColGrid;

    public void dragAndDropDemo() {

        twinColGrid = new TwinColGrid<>(availableBooks, "TwinColGrid demo with drag and drop support")
                .addSortableColumn(Book::getIsbn, Comparator.comparing(Book::getIsbn), "ISBN")
                .addSortableColumn(Book::getTitle, Comparator.comparing(Book::getTitle), "Title")
                .withAvailableGridCaption("Available books")
                .withSelectionGridCaption("Added books")
                .withoutAddAllButton()
                .withSizeFull()
                .withDragAndDropSupport()
                .withSelectionGridReordering()
                .selectRowOnClick();

        twinColGrid.setValue(selectedBooks);

        final Label countLabel = new Label("Selected items in left grid: 0");
        twinColGrid.getAvailableGrid().addSelectionListener(
                e -> countLabel.setText("Selected items in left grid: " + e.getAllSelectedItems().size()));
        twinColGrid.addValueChangeListener(e -> countLabel.setText("Selected items in left grid: 0"));

        add(twinColGrid, countLabel);

        addReorderingToggle();
        setSizeFull();
    }

    private void initializeData() {
        selectedBooks.add(new Book("1478375108", "Vaadin Recipes"));
        selectedBooks.add(new Book("9789526800677", "Book of Vaadin: Volume 2 "));


        availableBooks.add(new Book("1478375108", "Vaadin Recipes"));
        availableBooks.add(new Book("9781849515221", "Learning Vaadin"));
        availableBooks
                .add(new Book("9781782162261", "Vaadin 7 UI Design By Example: Beginner\u2019s Guide"));
        availableBooks.add(new Book("9781849518802", "Vaadin 7 Cookbook"));
        availableBooks.add(new Book("9526800605", "Book of Vaadin: 7th Edition, 1st Revision"));
        availableBooks.add(new Book("9789526800677", "Book of Vaadin: Volume 2 "));
        availableBooks.add(new Book("9529267533", "Book of Vaadin"));
        availableBooks.add(new Book("1782169776", "Learning Vaadin 7, Second Edition"));
    }

    private void addReorderingToggle() {
        Checkbox checkbox = new Checkbox("Selection Grid reordering allowed", true);
        Span description = new Span("(Reordering is disabled while the grid is sorted)");
        description.setVisible(false);

        SerializableRunnable refresh = () -> {
            boolean sorted = !twinColGrid.getSelectionGrid().getSortOrder().isEmpty();
            boolean allowed = twinColGrid.isSelectionGridReorderingAllowed();
            description.setVisible(sorted && allowed);
        };

        checkbox.addValueChangeListener(ev -> {
            twinColGrid.setSelectionGridReorderingAllowed(ev.getValue());
            refresh.run();
        });

        twinColGrid.getSelectionGrid().addSortListener(ev -> refresh.run());
        add(new Div(checkbox, description));
    }
}