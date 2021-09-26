package ru.diasoft.micro.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.mdp.mdptechtoken.MDPActualTechToken;
import ru.diasoft.micro.util.LoggerUtils1;

import java.util.Collection;
import java.util.Map;

import static ru.diasoft.micro.constants.MainConstants.HEADER_USE_TECH_TOKEN;
import static ru.diasoft.micro.constants.MainConstants.USE_TECH_TOKEN;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String FEIGN_AUTHORIZATION_HEADER = "Authorization";
    private static final String FEIGN_TOKEN_TYPE = "Bearer";

    private static final DSLogger logger = DSLogManager.getLogger(FeignClientInterceptor.class);
    @Autowired
    MDPActualTechToken actualToken;

    private boolean useTechToken(RequestTemplate requestTemplate) {
        Map<String, Collection<String>> headers = requestTemplate.headers();
        return headers.containsKey(HEADER_USE_TECH_TOKEN)
                && headers.get(HEADER_USE_TECH_TOKEN).contains(USE_TECH_TOKEN);
    }

    public void apply(RequestTemplate requestTemplate) {
        if (useTechToken(requestTemplate)) {
            LoggerUtils1.writeToLogDebug(logger, "process request technological token start !");
            String actualTechToken = actualToken.getActualTechToken();

            if (StringUtils.hasText(actualTechToken)) {
                requestTemplate.header(FEIGN_AUTHORIZATION_HEADER, String.format("%s %s", FEIGN_TOKEN_TYPE, actualTechToken));
            } else {
                LoggerUtils1.writeToLogDebug(logger, "Failed to identify technology token");
            }
        }
    }
}
