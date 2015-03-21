package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.JavaConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.portlet.*;
import java.util.Locale;

@Service
public class PortletServiceImpl implements PortletService {

    private PortletRequest getPortletRequest() {
        Object o = RequestContextHolder.currentRequestAttributes().getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, RequestAttributes.SCOPE_REQUEST);
        if (o instanceof PortletRequest) {
            return ((PortletRequest) o);
        }
        throw new RuntimeException("no PortletRequest.");
    }

    @Override
    public PortletConfig getPortletConfig() {
        Object o = getPortletRequest().getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
        if (o instanceof PortletConfig) {
            return ((PortletConfig) o);
        }
        throw new RuntimeException("no PortletConfig.");
    }

    @Override
    public RenderRequest getRenderRequest() {
        PortletRequest o = getPortletRequest();
        if (o instanceof RenderRequest) {
            return ((RenderRequest) o);
        }
        throw new RuntimeException("no RenderRequest.");
    }

    @Override
    public ResourceRequest getResourceRequest() {
        PortletRequest o = getPortletRequest();
        if (o instanceof ResourceRequest) {
            return ((ResourceRequest) o);
        }
        throw new RuntimeException("no ResourceRequest.");
    }

    @Override
    public ActionRequest getActionRequest() {
        PortletRequest o = getPortletRequest();
        if (o instanceof ActionRequest) {
            return ((ActionRequest) o);
        }
        throw new RuntimeException("no ActionRequest.");
    }

    @Override
    public EventRequest getEventRequest() {
        PortletRequest o = getPortletRequest();
        if (o instanceof EventRequest) {
            return ((EventRequest) o);
        }
        throw new RuntimeException("no EventRequest.");
    }

    private PortletResponse getPortletResponse() {
        Object o = getPortletRequest().getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);
        if (o instanceof PortletResponse) {
            return ((PortletResponse) o);
        }
        throw new RuntimeException("no PortletResponse.");
    }

    @Override
    public RenderResponse getRenderResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof RenderResponse) {
            return ((RenderResponse) o);
        }
        throw new RuntimeException("no RenderResponse.");
    }

    public ResourceResponse getResourceResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof ResourceResponse) {
            return ((ResourceResponse) o);
        }
        throw new RuntimeException("no ResourceResponse.");
    }

    public ActionResponse getActionResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof ActionResponse) {
            return ((ActionResponse) o);
        }
        throw new RuntimeException("no ActionResponse.");
    }

    public EventResponse getEventResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof EventResponse) {
            return ((EventResponse) o);
        }
        throw new RuntimeException("no EventResponse.");
    }

    public PortletSession getPortletSession() {
        return getPortletRequest().getPortletSession();
    }

    public Locale getLocale() {
        return getPortletRequest().getLocale();
    }

    public WindowState getWindowStateExclusive() {
        return LiferayWindowState.EXCLUSIVE;
    }

    public WindowState getWindowStatePopup() {
        return LiferayWindowState.POP_UP;
    }

    public WindowState getWindowStateMaximized() {
        return LiferayWindowState.MAXIMIZED;
    }

    public WindowState getWindowStateMinimized() {
        return LiferayWindowState.MINIMIZED;
    }

    public WindowState getWindowStateNormal() {
        return LiferayWindowState.NORMAL;
    }
}
