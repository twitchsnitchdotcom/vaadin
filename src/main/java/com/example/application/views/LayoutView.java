package com.example.application.views;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPoint;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapPolygon;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
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

@Route(value = "layout", layout = MainLayout.class)
@PageTitle("Layout")
@PermitAll
public class LayoutView extends VerticalLayout {
    private static int MESSAGE_LOAD_NUMBER = 100;
    private int messageStartNum1 = 0;
    private int messageStartNum2 = 0;

    public LayoutView() {
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(googleMaps());
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
        add(starsRating);
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




}