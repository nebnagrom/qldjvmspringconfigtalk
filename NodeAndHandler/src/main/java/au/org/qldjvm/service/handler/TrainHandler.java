package au.org.qldjvm.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("trainHandler")
public class TrainHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(TrainHandler.class);

    public String handle(String message) {

        String handleResult = "train handler just handled a message<br>";
        logger.debug(handleResult);
        return handleResult;
    }

}
