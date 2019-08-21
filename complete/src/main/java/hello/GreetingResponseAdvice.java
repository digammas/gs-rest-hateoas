package hello;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@ControllerAdvice
public class GreetingResponseAdvice implements ResponseBodyAdvice<Greeting> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (!(methodParameter.getGenericParameterType() instanceof ParameterizedType)) return false;
        Type[] types = ((ParameterizedType) methodParameter.getGenericParameterType()).getActualTypeArguments();
        if (types == null || types.length == 0 || !(types[0] instanceof Class)) return false;
        return Greeting.class.isAssignableFrom((Class) types[0]);
    }

    @Override
    public Greeting beforeBodyWrite(
            Greeting greeting,
            MethodParameter methodParameter,
            MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> aClass,
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {


        long max;
        GreetingController obj = methodOn(GreetingController.class);

        System.out.println("Super optimized");
        ActionLink link = ActionLink.of(obj.greeting("###"), "self", HttpMethod.GET);
        max = 0;
        for (int j = 0; j < 100; j++) {
            max += 1000;
            long ms = new Date().getTime();
            for (long i = 0; i < max; i++) {
                link.getHref().replace("###", "123");
            }
            System.out.printf("%d, %d\n", max, new Date().getTime() - ms);
        }

        System.out.println("Optimized");
        max = 0;
        for (int j = 0; j < 100; j++) {
            max += 1000;
            long ms = new Date().getTime();
            for (long i = 0; i < max; i++) {
                ActionLink.of(obj.greeting("John"), "self", HttpMethod.GET);
            }
            System.out.printf("%d, %d\n", max, new Date().getTime() - ms);
        }

        System.out.println("Non optimized");
        max = 0;
        for (int j = 0; j < 100; j++) {
            max += 1000;
            long ms = new Date().getTime();
            for (long i = 0; i < max; i++) {
                ActionLink.of(methodOn(GreetingController.class).greeting("John"), "self", HttpMethod.GET);
            }
            System.out.printf("%d, %d\n", max, new Date().getTime() - ms);
        }

        greeting.add(ActionLink.of(methodOn(GreetingController.class).greeting(greeting.getContent()), "self", HttpMethod.GET));
        return greeting;
    }
}
