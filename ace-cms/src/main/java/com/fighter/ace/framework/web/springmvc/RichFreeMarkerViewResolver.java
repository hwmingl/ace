package com.fighter.ace.framework.web.springmvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * Created by hanebert on 16/4/24.
 *
 * ViewResolver for RichFreeMarkerView
 *
 * Override buildView, if viewName start with / , then ignore prefix.
 */
public class RichFreeMarkerViewResolver extends AbstractTemplateViewResolver {
    /**
     * Set default viewClass
     */
    public RichFreeMarkerViewResolver() {
        setViewClass(RichFreeMarkerView.class);
    }

    /**
     * if viewName start with / , then ignore prefix.
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);
        // start with / ignore prefix
        if (viewName.startsWith("/")) {
            view.setUrl(viewName + getSuffix());
        }
        return view;
    }
}
