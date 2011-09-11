package au.org.qldjvm.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("carHandler")
public class CarHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(CarHandler.class);

    public String handle(String message) {

        String handleResult = "car handler just handled a message<br>";
        logger.debug(handleResult);
        return handleResult;
    }

}
