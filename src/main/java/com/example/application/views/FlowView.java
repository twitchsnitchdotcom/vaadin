package com.example.application.views;

import com.example.application.data.Book;
import com.example.application.data.service.CrmService;
import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.vaadin.componentfactory.EnhancedRichTextEditor;
import com.vaadin.componentfactory.timeline.Timeline;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.f0rce.viz.Viz;
import de.f0rce.viz.VizFormat;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.*;

@Route(value = "twitchuser", layout = MainLayout.class)
@PageTitle("Twitch User | TwitchSnitch")
@PermitAll
public class FlowView extends VerticalLayout {
    private final CrmService service;

    public FlowView(CrmService service) {
        initializeData();
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dragAndDropDemo();
        add(richTextEditor(), timeline(), viz());
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

    public EnhancedRichTextEditor richTextEditor(){
        EnhancedRichTextEditor rte = new EnhancedRichTextEditor();

        Button textButton1 = new Button("");
        textButton1.setIcon(VaadinIcon.AIRPLANE.create());
        textButton1.addClickShortcut(Key.F8);
        textButton1.getElement().setProperty("title", "Airplanes are flying machines.");
        textButton1.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        textButton1.addClickListener(event -> {
            rte.addText("Airplanes are flying machines. ");
        });

        Button textButton2 = new Button("");
        textButton2.setIcon(VaadinIcon.DENTAL_CHAIR.create());
        textButton2.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        textButton2.getElement().setProperty("title", "Dentists are drilling people.");
        textButton2.addClickShortcut(Key.F9);
        textButton2.addClickListener(event -> {
            rte.addText("Dentists are drilling people. ");
        });

        rte.addCustomButtons(textButton1,textButton2);

        return rte;
    }

    private Timeline timeline(){
        // create items
        Item item1 = new Item(LocalDateTime.of(2021, 8, 11, 2, 30, 00),
                LocalDateTime.of(2021, 8, 11, 8, 00, 00), "Item 1");
        item1.setId("1");

        Item item2 = new Item(LocalDateTime.of(2021, 8, 11, 9, 00, 00),
                LocalDateTime.of(2021, 8, 11, 17, 00, 00), "Item 2");
        item2.setId("2");

        Item item3 = new Item(LocalDateTime.of(2021, 8, 12, 0, 30, 00),
                LocalDateTime.of(2021, 8, 12, 3, 00, 00), "Item 3");
        item3.setId("3");

        Item item4 = new Item(LocalDateTime.of(2021, 8, 12, 4, 30, 00),
                LocalDateTime.of(2021, 8, 12, 20, 00, 00), "Item 4");
        item4.setId("4");

        Item item5 = new Item(LocalDateTime.of(2021, 8, 12, 21, 30, 00),
                LocalDateTime.of(2021, 8, 13, 01, 15, 00), "Item 5");
        item5.setId("5");

        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

        // make them editable
        items.forEach(i -> {
            i.setEditable(true);
            i.setUpdateTime(true);
        });

        // create timeline
        Timeline timeline = new Timeline(items);

        // set timeline range
        timeline.setTimelineRange(
                LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

        // set multiselection to timeline
        timeline.setMultiselect(true);

        return timeline;
    }

    private Component viz() {
        Viz viz = new Viz(); // height: 300px - width: 600px
        viz.setFormat(VizFormat.svg); // default
        viz.setGraph("digraph H { a -> b }"); // simple dot notation
        return viz;
    }
}