package com.example.application.views;

import com.example.application.data.Book;
import com.example.application.data.service.CrmService;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPoint;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPolygon;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.flowingcode.vaadin.addons.twincolgrid.TwinColGrid;
import com.vaadin.componentfactory.Chat;
import com.vaadin.componentfactory.EnhancedRichTextEditor;
import com.vaadin.componentfactory.model.Message;
import com.vaadin.componentfactory.timeline.Timeline;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Configuration;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.jfancy.StarsRating;

import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Route(value = "twitchchannel", layout = MainLayout.class)
@PageTitle("Twitch Channel | TwitchSnitch")
@PermitAll
public class TwitchChannelView extends VerticalLayout {
    private static int MESSAGE_LOAD_NUMBER = 100;
    private int messageStartNum1 = 0;
    private int messageStartNum2 = 0;

    public TwitchChannelView() {
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(addBasicChat(), addChatWithCustomLoading(), richTextEditor(), timeline(), googleMaps());
        // Component erstellen
        StarsRating starsRating = new StarsRating();
        // Listener Hinzufügen
        starsRating.addValueChangeListener(e -> {
            System.out.println("Value has changed to: " + starsRating.getValue());
        });

        // Button zum auslesen erstellen
        Button btn = new Button("Test Button");
        btn.addClickListener(e -> {
            System.out.println("Value: " + starsRating.getValue());
        });

        // Components zum Layout hinzufügen
        add(starsRating, btn);
    }
    
    private GoogleMap googleMaps(){
        GoogleMap gmaps = new GoogleMap("AIzaSyBF6aBrztZl9ylaWEvQa0YOzrUHjnsXOoY",null,null);
        gmaps.setMapType(GoogleMap.MapType.SATELLITE);
        gmaps.setSizeFull();
        gmaps.setCenter(new LatLon(0,0));
        gmaps.addMarker("Center", new LatLon(0,0), true, "");
        GoogleMapPolygon gmp = gmaps.addPolygon(Arrays.asList(new GoogleMapPoint(gmaps.getCenter()),
                new GoogleMapPoint(gmaps.getCenter().getLat(),gmaps.getCenter().getLon()+1),
                new GoogleMapPoint(gmaps.getCenter().getLat()+1,gmaps.getCenter().getLon())));

        return gmaps;
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

    private Chat addBasicChat() {
        Chat chat = new Chat();
        chat.setMessages(generateMessages(messageStartNum1));
        chat.setLazyLoadTriggerOffset(0);
        chat.scrollToBottom();

        chat.addChatNewMessageListener(event -> {
            chat.addNewMessage(new Message(event.getMessage(),
                    "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce54bf11889067.562541ef7cde4.png",
                    "Ben Smith", true));
            chat.clearInput();
            chat.scrollToBottom();
        });

        chat.addLazyLoadTriggerEvent(e -> {
            messageStartNum1 += MESSAGE_LOAD_NUMBER;
            List<Message> list = generateMessages(messageStartNum1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {}
            chat.setLoading(false);
            chat.addMessagesToTop(list);
        });

        return chat;
    }

    private Chat addChatWithCustomLoading() {
        Chat chat = new Chat();
        chat.setMessages(generateMessages(messageStartNum2));
        chat.setDebouncePeriod(200);
        chat.setLazyLoadTriggerOffset(2500);
        chat.scrollToBottom();

        chat.addChatNewMessageListener(event -> {
            event.getSource().addNewMessage(new Message(event.getMessage(),
                    "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce54bf11889067.562541ef7cde4.png",
                    "Ben Smith", true));
            event.getSource().clearInput();
            event.getSource().scrollToBottom();
        });

        chat.addLazyLoadTriggerEvent(e -> {
            messageStartNum2 += MESSAGE_LOAD_NUMBER;
            List<Message> list = generateMessages(messageStartNum2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {}
            chat.setLoading(false);
            chat.addMessagesToTop(list);
        });

        chat.setLoadingIndicator(createLoadingComponent());

        Div description = new Div();
        description.setText("In this example LazyLoadTriggerOffset is set to 2500 so loading of new messages " +
                "will be started when user scroll 2500px close to top.\n" +
                "Also custom loading indicator is used in this example");

        return chat;
    }

    private Component createLoadingComponent() {
        Div loading = new Div();
        loading.setText("Loading...");
        loading.getElement().setAttribute("style", "text-align: center;");
        return loading;
    }


    private List<Message> generateMessages(int start) {
        List<Message> list = new ArrayList<>();

        for(int i = start; i < start + MESSAGE_LOAD_NUMBER; i++) {
            String body = i +  " Lorem Ipsum on yksinkertaisesti testausteksti, jota tulostus- ja ladontateollisuudet käyttävät. Lorem Ipsum on ollut teollisuuden normaali testausteksti jo 1500-luvulta asti, jolloin tuntematon tulostaja otti kaljuunan ja sekoitti sen tehdäkseen esimerkkikirjan. ";
            if (i % 2 == 0) {
                list.add(new Message(body, "", "Johana Livingstone", false));
            } else {
                list.add(new Message(body, "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce54bf11889067.562541ef7cde4.png", "Ben Smith", true));
            }
        }

        Collections.reverse(list);
        return list;
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


}