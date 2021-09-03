package cpm.web.utils.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServicesController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ServicesController() {
        super();
    }

    protected String convertData() {
        LOGGER.info(this.getClass().getName());
        return this.getClass().getName();
    }
}
