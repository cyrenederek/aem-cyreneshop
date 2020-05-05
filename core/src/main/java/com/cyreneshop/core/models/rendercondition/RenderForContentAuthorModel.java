package com.cyreneshop.core.models.rendercondition;

import com.adobe.granite.ui.components.rendercondition.RenderCondition;
import com.adobe.granite.ui.components.rendercondition.SimpleRenderCondition;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.log4j.Logger;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Iterator;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RenderForContentAuthorModel {

    private static final Logger LOG = Logger.getLogger(RenderForContentAuthorModel.class);

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String allowedGroup;

    @PostConstruct
    protected void init() {
        try {
            ResourceResolver resolver = request.getResourceResolver();
            Session session = resolver.adaptTo(Session.class);
            UserManager userManager = AccessControlUtil.getUserManager(session);
            Iterator<Authorizable> groups = userManager.findAuthorizables("jcr:primaryType", "rep:Group");
            boolean isRendered = false;
            while(groups.hasNext()) {
                Authorizable authorizable = groups.next();
                String groupName = authorizable.getPrincipal().getName();
                if (StringUtils.equals(groupName, allowedGroup)) {
                    isRendered = true;
                }
            }

            request.setAttribute(RenderCondition.class.getName(), new SimpleRenderCondition(isRendered));

        } catch(RepositoryException e) {
            LOG.error("Caught " + e + " while activating " + this.getClass().getName(), e);
        }
    }

    public String getTest() {
        return "This is test";
    }
}
