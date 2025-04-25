package com.sunsensor.group.security;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.*;

@Route("access-denied")
public class AccessDeniedView extends Div {
    public AccessDeniedView() {
        setText("Sinulla ei ole oikeuksia tälle sivulle.");
    }
}
