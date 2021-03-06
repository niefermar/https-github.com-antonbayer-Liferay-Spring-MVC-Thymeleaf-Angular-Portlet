package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class PortletServiceImpl implements PortletService {

    @Override
    public PortletRequest getPortletRequest() {
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
    public PortletPreferences getPortletPreferences() {
        return getPortletRequest().getPreferences();
    }

    @Override
    public PortletContext getPortletContext() {
        return getPortletSession().getPortletContext();
    }

    @Override
    public ThemeDisplay getThemeDisplay() {
        return ServiceContextThreadLocal.getServiceContext().getThemeDisplay();
    }

    @Override
    public PortletURL getPortletUrl(Map<String, String> params) {
        return getPortletUrl(params, getThemeDisplay().getLayout().getPlid(), PortalUtil.getPortletId(getPortletRequest()));
    }

    @Override
    public PortletURL getPortletUrl(Map<String, String> params, String url, String portletName) {
        Layout layout = null;
        try {
            layout = LayoutLocalServiceUtil.getFriendlyURLLayout(getThemeDisplay().getScopeGroupId(), false, url);
        } catch (PortalException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        if (layout == null) {
            throw new RuntimeException("no Layout.");
        }
        return getPortletUrl(params, layout.getPlid(), getPortletId(portletName));
    }

    private PortletURL getPortletUrl(Map<String, String> params, long plid, String portletId) {
        PortletURL redirectURL = PortletURLFactoryUtil.create(getPortletRequest(), portletId, plid, PortletRequest.RENDER_PHASE);

        for (String key : params.keySet()) {
            redirectURL.setParameter(key, params.get(key));
        }
        try {
            redirectURL.setWindowState(LiferayWindowState.NORMAL);
        } catch (WindowStateException e) {
            e.printStackTrace();
        }
        try {
            redirectURL.setPortletMode(PortletMode.VIEW);
        } catch (PortletModeException e) {
            e.printStackTrace();
        }
        return redirectURL;
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

    @Override
    public PortletResponse getPortletResponse() {
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

    @Override
    public ResourceResponse getResourceResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof ResourceResponse) {
            return ((ResourceResponse) o);
        }
        throw new RuntimeException("no ResourceResponse.");
    }

    @Override
    public ActionResponse getActionResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof ActionResponse) {
            return ((ActionResponse) o);
        }
        throw new RuntimeException("no ActionResponse.");
    }

    @Override
    public EventResponse getEventResponse() {
        PortletResponse o = getPortletResponse();
        if (o instanceof EventResponse) {
            return ((EventResponse) o);
        }
        throw new RuntimeException("no EventResponse.");
    }

    @Override
    public PortletSession getPortletSession() {
        return getPortletRequest().getPortletSession();
    }

    @Override
    public Locale getLocale() {
        return getPortletRequest().getLocale();
    }

    @Override
    public WindowState getWindowStateExclusive() {
        return LiferayWindowState.EXCLUSIVE;
    }

    @Override
    public WindowState getWindowStatePopup() {
        return LiferayWindowState.POP_UP;
    }

    @Override
    public WindowState getWindowStateMaximized() {
        return LiferayWindowState.MAXIMIZED;
    }

    @Override
    public WindowState getWindowStateMinimized() {
        return LiferayWindowState.MINIMIZED;
    }

    @Override
    public WindowState getWindowStateNormal() {
        return LiferayWindowState.NORMAL;
    }

    @Override
    public String getStaticContentUrl(String path) {
        PortletRequest portletRequest = getPortletRequest();
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
        return PortalUtil.getStaticResourceURL(httpServletRequest, portletRequest.getContextPath() + STATIC_CONTENT_RESOURCE_URL + path);
    }

    @Override
    public String getPortletId(String portletName) {
        List<com.liferay.portal.model.Portlet> portletList = PortletLocalServiceUtil.getPortlets();
        for (
                com.liferay.portal.model.Portlet portlet : portletList) {
            if (portletName.equals(portlet.getPortletName())) {
                return portlet.getPortletId();
            }
        }
        throw new RuntimeException("no valid PortletName:" + portletName);
    }
}
