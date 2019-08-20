package hello;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;

public class ActionLink extends Link {

    private String method;

    public ActionLink(String href) {
        super(href);
    }

    public ActionLink(String href, String rel) {
        super(href, rel);
    }

    public ActionLink(String href, String rel, String method) {
        super(href, rel);
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ActionLink withMethod(String method) {
        this.method = method;
        return this;
    }

    public ActionLink withMethod(HttpMethod method) {
        this.method = method.name();
        return this;
    }

    public static ActionLink from(Link clone) {
        return new ActionLink(clone.getHref(), clone.getRel());
    }

    public static ActionLink of(Object value, String rel, HttpMethod method) {
        return ActionLink.from(ControllerLinkBuilder
                .linkTo(value).withRel(rel)
        ).withMethod(method);
    }
}
