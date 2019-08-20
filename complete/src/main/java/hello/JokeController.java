package hello;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Joke.class)
public class JokeController {

    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/joke")
    public HttpEntity<Joke> joke(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Joke joke = new Joke(String.format(TEMPLATE, name));
        return new ResponseEntity<>(joke, HttpStatus.OK);
    }
}
