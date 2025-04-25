package com.sunsensor.group.sensor.ui.view;

import com.sunsensor.group.base.ui.view.MainLayout;
import com.sunsensor.group.sensor.domain.Measurement;
import com.sunsensor.group.sensor.service.MeasurementService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "measurement-charts", layout = MainLayout.class)
@PageTitle("Mittausgraafit")
@Menu(title = "Graafit", icon = "vaadin:chart", order = 3)
public class MeasurementChartView extends VerticalLayout {

    private final MeasurementService measurementService;
    private final ComboBox<String> timeRangeSelector = new ComboBox<>("Aikajakso");

    public MeasurementChartView(MeasurementService measurementService) {
        this.measurementService = measurementService;

        setSizeFull();
        addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.Gap.MEDIUM);
        timeRangeSelector.setItems("Päivä", "Viikko", "Kuukausi", "Vuosi", "Kaikki");
        timeRangeSelector.setValue("Päivä");
        timeRangeSelector.addValueChangeListener(event -> updateChart());

        add(timeRangeSelector);
        updateChart();
    }

    private void updateChart() {
        removeAll();
        add(timeRangeSelector);

        List<Measurement> data = measurementService.findAll();
        String range = timeRangeSelector.getValue();

        // Grouping by date based on selected range
        Map<String, Double> grouped = switch (range) {
            case "Viikko" -> data.stream().collect(Collectors.groupingBy(
                    m -> m.getDate().with(java.time.DayOfWeek.MONDAY).toString(),
                    Collectors.averagingDouble(Measurement::getLux)
            ));
            case "Kuukausi" -> data.stream().collect(Collectors.groupingBy(
                    m -> m.getDate().withDayOfMonth(1).toString(),
                    Collectors.averagingDouble(Measurement::getLux)
            ));
            case "Vuosi" -> data.stream().collect(Collectors.groupingBy(
                    m -> String.valueOf(m.getDate().getYear()),
                    Collectors.averagingDouble(Measurement::getLux)
            ));
            case "Kaikki" -> Map.of("Kaikki mittaukset", data.stream().mapToDouble(Measurement::getLux).average().orElse(0.0));
            default -> data.stream().collect(Collectors.groupingBy(
                    m -> m.getDate().toString(),
                    Collectors.averagingDouble(Measurement::getLux)
            ));
        };

        Chart chart = new Chart(ChartType.LINE);
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Valoisuus " + range.toLowerCase() + " tasolla");

        XAxis x = new XAxis();
        x.setCategories(grouped.keySet().toArray(String[]::new));
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Lux");
        conf.addyAxis(y);

        ListSeries luxSeries = new ListSeries("Lux", grouped.values().toArray(new Number[0]));
        conf.addSeries(luxSeries);

        add(chart);
    }
}
