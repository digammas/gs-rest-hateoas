package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Joke extends ResourceSupport {

    private final String content;

    @JsonCreator
    public Joke(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @JsonProperty("greeting")
    public Greeting getGreeting() {
        return new Greeting("Yellow!");
    }
}
