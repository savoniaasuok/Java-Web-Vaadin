package com.sunsensor.group.base.ui.view;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route(value = "", layout = MainLayout.class)  // tai @Route("main") jos haluat että se toimii osoitteessa /main
@PageTitle("Main")
public class MainView extends VerticalLayout {

    public MainView() {
        add(new H1("Tervetuloa Lumen pääsivulle"));
    }
}
