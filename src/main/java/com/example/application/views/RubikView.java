package com.example.application.views;

import com.storedobject.vaadin.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

import static com.storedobject.vaadin.Application.message;
import static com.vaadin.flow.dom.ElementUtil.setComponent;
import static org.atmosphere.util.IOUtils.close;

@Route(value = "rubiks", layout = MainLayout.class)
@PageTitle("Rubiks | TwitchSnitch")
@PermitAll
public class RubikView extends VerticalLayout {

  private final RubikCube rubikCube;

  public RubikView() {
    rubikCube = new RubikCube();
    rubikCube.addSolvedListener(rc -> message("Congratulations! You solved the Rubik Cube!!"));
    TextField commandsField = new TextField("Moves (SiGN Notation - Example: F'RL2B)");
    commandsField.setWidth("100%");
    ButtonLayout buttonLayout = new ButtonLayout();
    Button move = new Button("Move", VaadinIcon.THUMBS_UP_O, e -> move(commandsField.getValue()));
    Button shuffle = new Button("Shuffle", VaadinIcon.RANDOM, e -> rubikCube.shuffle(20));
    Button reset = new Button("Reset", e -> rubikCube.reset());
    Button exit = new Button("Exit", e -> close());
    buttonLayout.add(move, shuffle, reset, exit);
    commandsField.addValueChangeListener(e -> move(e.getValue()));
    Div div = new Div(commandsField, buttonLayout, rubikCube);
    add(div);
  }

  private void move(String move) {
    rubikCube.move(move);
  }
}
