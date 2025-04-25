package com.sunsensor.group.sensor.ui.view;

import com.sunsensor.group.base.ui.component.ViewToolbar;
import com.sunsensor.group.base.ui.view.MainLayout;
import com.sunsensor.group.sensor.domain.*;
import com.sunsensor.group.sensor.service.MeasurementService;
import com.sunsensor.group.sensor.service.SensorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.FlexLayout;


import java.util.Optional;

@Route(value = "measurements", layout = MainLayout.class)
@PageTitle("Mittaukset")
@Menu(title = "Mittaukset", icon = "vaadin:line-bar-chart", order = 2)
public class MeasurementView extends Div {

    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ComboBox<Sensor> sensorFilter = new ComboBox<>("Anturi");
    private final DatePicker dateFilter = new DatePicker("Päivä");
    private final NumberField luxMin = new NumberField("Min. Lux");
    private final NumberField tempMax = new NumberField("Max. Lämpötila");
    private final NumberField cloudinessMax = new NumberField("Max. Pilvisyys");

    private final Button filterBtn = new Button("Suodata", event -> updateGrid());
    private final Button resetBtn = new Button("Tyhjennä", event -> resetFilters());


    private final Grid<Measurement> measurementGrid = new Grid<>(Measurement.class, false);

    public MeasurementView(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;

        setSizeFull();
        addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM);

        var addButton = new Button("Lisää mittaus", event -> openEditor(new Measurement()));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        sensorFilter.setItems(sensorService.findAll());
        sensorFilter.setItemLabelGenerator(Sensor::getName);
        sensorFilter.setPlaceholder("Valitse anturi");

        HorizontalLayout filters = new HorizontalLayout(sensorFilter, dateFilter, luxMin, tempMax, cloudinessMax, filterBtn, resetBtn);
        filters.setSpacing(true);


        add(filters);


        add(new ViewToolbar("Mittaukset", addButton));

        configureGrid();
        add(measurementGrid);
        updateGrid();
    }

    private void configureGrid() {
        measurementGrid.addColumn(m -> m.getSensor().getName()).setHeader("Anturi").setAutoWidth(true);
        measurementGrid.addColumn(Measurement::getDate).setHeader("Päivä");
        measurementGrid.addColumn(Measurement::getTime).setHeader("Aika");
        measurementGrid.addColumn(Measurement::getLux).setHeader("Lux");
        measurementGrid.addColumn(Measurement::getTemperature).setHeader("Lämpötila");
        measurementGrid.addColumn(Measurement::getHumidity).setHeader("Kosteus");
        measurementGrid.addColumn(m -> Optional.ofNullable(m.getWeather()).map(Weather::getUvIndex).orElse(0.0)).setHeader("UV");
        measurementGrid.addColumn(m -> Optional.ofNullable(m.getWeather()).map(Weather::getCloudinessPercent).orElse(0.0)).setHeader("Pilvisyys");
        measurementGrid.addComponentColumn(measurement -> {
            var edit = new Button("Muokkaa", e -> openEditor(measurement));
            var delete = new Button("Poista", e -> {
                measurementService.delete(measurement.getId());
                updateGrid();
                Notification.show("Mittaus poistettu", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
            return new HorizontalLayout(edit, delete);
        }).setHeader("Toiminnot");
    }

    private void updateGrid() {
        var all = measurementService.findAll();
        var filtered = all.stream()
                .filter(m -> sensorFilter.isEmpty() || m.getSensor().equals(sensorFilter.getValue()))
                .filter(m -> dateFilter.isEmpty() || m.getDate().equals(dateFilter.getValue()))
                .filter(m -> luxMin.isEmpty() || m.getLux() >= luxMin.getValue())
                .filter(m -> tempMax.isEmpty() || m.getTemperature() <= tempMax.getValue())
                .filter(m -> cloudinessMax.isEmpty() || Optional.ofNullable(m.getWeather()).map(Weather::getCloudinessPercent).orElse(0.0) <= cloudinessMax.getValue())
                .toList();

        measurementGrid.setItems(filtered);
    }

    private void resetFilters() {
        sensorFilter.clear();
        dateFilter.clear();
        luxMin.clear();
        tempMax.clear();
        cloudinessMax.clear();
        updateGrid();
    }


    private void openEditor(Measurement measurement) {
        Dialog dialog = new Dialog();
        dialog.setModal(true);
        dialog.setDraggable(true);
        dialog.setCloseOnOutsideClick(true);

        ComboBox<Sensor> sensorBox = new ComboBox<>("Anturi");
        sensorBox.setItems(sensorService.findAll());
        sensorBox.setItemLabelGenerator(Sensor::getName);
        sensorBox.setValue(Optional.ofNullable(measurement.getSensor()).orElse(null));

        DatePicker dateField = new DatePicker("Päivämäärä");
        dateField.setValue(Optional.ofNullable(measurement.getDate()).orElse(null));

        TimePicker timeField = new TimePicker("Kellonaika");
        timeField.setValue(Optional.ofNullable(measurement.getTime()).orElse(null));

        NumberField lux = new NumberField("Lux");
        lux.setValue(measurement.getLux());

        NumberField temp = new NumberField("Lämpötila");
        temp.setValue(measurement.getTemperature());

        NumberField humidity = new NumberField("Kosteus");
        humidity.setValue(measurement.getHumidity());

        NumberField lat = new NumberField("Latitude");
        lat.setValue(measurement.getLatitude());

        NumberField lon = new NumberField("Longitude");
        lon.setValue(measurement.getLongitude());

        NumberField uv = new NumberField("UV-indeksi");
        NumberField cloud = new NumberField("Pilvisyys %");
        if (measurement.getWeather() != null) {
            uv.setValue(measurement.getWeather().getUvIndex());
            cloud.setValue(measurement.getWeather().getCloudinessPercent());
        }

        FormLayout form = new FormLayout(sensorBox, dateField, timeField, lux, temp, humidity, lat, lon, uv, cloud);

        Button save = new Button("Tallenna", e -> {
            measurement.setSensor(sensorBox.getValue());
            measurement.setDate(dateField.getValue());
            measurement.setTime(timeField.getValue());
            measurement.setLux(lux.getValue());
            measurement.setTemperature(temp.getValue());
            measurement.setHumidity(humidity.getValue());
            measurement.setLatitude(lat.getValue());
            measurement.setLongitude(lon.getValue());

            Weather weather = Optional.ofNullable(measurement.getWeather()).orElse(new Weather());
            weather.setUvIndex(uv.getValue());
            weather.setCloudinessPercent(cloud.getValue());
            measurement.setWeather(weather);

            measurementService.save(measurement);
            updateGrid();
            dialog.close();
            Notification.show("Mittaus tallennettu", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        Button cancel = new Button("Peruuta", e -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.add(form, new HorizontalLayout(save, cancel));
        dialog.open();
    }
}
