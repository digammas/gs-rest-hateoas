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
        greeting.add(ActionLink.of(methodOn(GreetingController.class).greeting(greeting.getContent()), "self", HttpMethod.GET));
        return greeting;
    }
}
