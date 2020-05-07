package com.cyreneshop.core.models;

import com.adobe.cq.wcm.core.components.models.Title;
import com.cyreneshop.core.models.dataobjects.ProductData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Title.class, resourceType = "cyreneshop/components/producttitle", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductTitle implements Title {
    private static final String DATA_PATH = "/content/cyreneshop/batchdata/products";
    private static final Logger LOG = Logger.getLogger(ProductTitle.class);
    private static final String PN_DATA = "data";

    @Self @Via(type = ResourceSuperType.class)
    private Title title;

    @ValueMapValue
    private String productId;

    @ScriptVariable
    private Resource resource;

    private ObjectMapper mapper;
    private String text;

    @PostConstruct
    protected void init() {
        mapper = new ObjectMapper();
        try {
            if (StringUtils.isNotBlank(productId)) {
                text = "Product Id not found";

                ResourceResolver resolver = resource.getResourceResolver();
                Resource dataResource = resolver.getResource(DATA_PATH);
                String data = dataResource.getValueMap().get(PN_DATA, StringUtils.EMPTY);
                TypeReference<List<ProductData>> listType = new TypeReference<List<ProductData>>() {};
                List<ProductData> productList = mapper.readValue(data, listType);

                productList.stream().filter(d -> StringUtils.equalsIgnoreCase(d.getProductId(), productId)).findFirst().ifPresent(t -> {
                    text = t.getProductName().concat(", ").concat(t.getDescription());
                });
            }

        } catch (IOException e) {
            LOG.error("Caught " + e + " while activating " + this.getClass().getName(), e);
        }

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getType() {
        return title.getType();
    }

    @Override
    public String getLinkURL() {
        return title.getLinkURL();
    }

}
