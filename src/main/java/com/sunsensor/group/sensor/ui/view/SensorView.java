package com.sunsensor.group.sensor.ui.view;

import com.sunsensor.group.base.ui.component.ViewToolbar;
import com.sunsensor.group.base.ui.view.MainLayout;
import com.sunsensor.group.sensor.domain.Sensor;
import com.sunsensor.group.sensor.service.SensorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Optional;

@Route(value = "sensors", layout = MainLayout.class)
@PageTitle("Sensors")
@AnonymousAllowed
@Menu(title = "Sensorit", icon = "vaadin:sitemap", order = 1)
public class SensorView extends Div {

    private final SensorService sensorService;

    private final Grid<Sensor> sensorGrid = new Grid<>(Sensor.class, false);

    public SensorView(SensorService sensorService) {
        this.sensorService = sensorService;

        setSizeFull();
        addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM);

        var addButton = new Button("Lisää uusi anturi", event -> openEditor(new Sensor()));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(new ViewToolbar("Anturit", addButton));

        configureGrid();
        add(sensorGrid);
        updateGrid();
    }

    private void configureGrid() {
        sensorGrid.addColumn(Sensor::getName).setHeader("Nimi").setAutoWidth(true);
        sensorGrid.addColumn(Sensor::getLocationDescription).setHeader("Sijainti").setAutoWidth(true);

        sensorGrid.addComponentColumn(sensor -> {
            var edit = new Button("Muokkaa", e -> openEditor(sensor));
            var delete = new Button("Poista", e -> {
                sensorService.delete(sensor.getId());
                updateGrid();
                Notification.show("Anturi poistettu", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
            return new HorizontalLayout(edit, delete);
        }).setHeader("Toiminnot");
    }

    private void updateGrid() {
        sensorGrid.setItems(sensorService.findAll());
    }

    private void openEditor(Sensor sensor) {
        Dialog dialog = new Dialog();
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setCloseOnOutsideClick(true);

        TextField nameField = new TextField("Anturin nimi");
        nameField.setValue(Optional.ofNullable(sensor.getName()).orElse(""));

        TextField locationField = new TextField("Sijaintikuvaus");
        locationField.setValue(Optional.ofNullable(sensor.getLocationDescription()).orElse(""));

        FormLayout form = new FormLayout();
        form.add(nameField, locationField);

        Button save = new Button("Tallenna", e -> {
            sensor.setName(nameField.getValue());
            sensor.setLocationDescription(locationField.getValue());
            sensorService.save(sensor);
            updateGrid();
            dialog.close();
            Notification.show("Anturi tallennettu", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        Button cancel = new Button("Peruuta", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.add(form, new HorizontalLayout(save, cancel));
        dialog.open();
    }
}
