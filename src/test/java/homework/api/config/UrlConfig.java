package homework.api.config;

import org.aeonbits.owner.Config;
@Config.Sources({
        "classpath:url.properties"
})
public interface UrlConfig extends Config {
    @Config.Key("correctUrl.value")
    String getCorrectUrl();

    @Config.Key("longUrl.value")
    String getLongUrl();

    @Config.Key("incorrectUrl.value")
    String getIncorrectUrl();

    @Config.Key("emptyUrl.value")
    String getEmptyUrl();

    @Config.Key("urlWithOmissions.value")
    String getUrlWithOmissions();
}
