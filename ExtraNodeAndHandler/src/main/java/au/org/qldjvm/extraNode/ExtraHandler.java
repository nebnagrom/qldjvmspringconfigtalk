package au.org.qldjvm.extraNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import au.org.qldjvm.service.handler.Handler;

@Component("extraHandler")
public class ExtraHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(ExtraHandler.class);

    public String handle(String message) {

        String handleResult = "extra handler just handled a message<br>";
        logger.debug(handleResult);
        return handleResult;
    }

}
