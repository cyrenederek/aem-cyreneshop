package com.cyreneshop.core.models;

import com.adobe.cq.wcm.core.components.models.Title;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Title.class, resourceType = "cyreneshop/components/producttitle", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductTitle implements Title {
    @ScriptVariable
    private Page currentPage;

    @Self @Via(type = ResourceSuperType.class)
    private Title title;

    @ValueMapValue
    private String productId;

    @Override
    public String getText() {
        return productId + ":" + currentPage.getTitle();
    }

}
