package com.samenea.payments.model;

import com.google.common.collect.Maps;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.ProductSpec;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author: Jalal Ashrafi
 * Date: 2/13/13
 */
public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    /**
     * Convert key value getCriteria to a map
     * @param criteria
     * @return
     */
    public static Map<String, String> getKeyValueMap(String criteria) {
        final String[] keyValues = criteria.split(ProductSpec.AND);
        Map<String,String> keyValueMap = Maps.newHashMap();
        for (String keyValue : keyValues) {
            final String[] splittedKeyValue = keyValue.split(ProductSpec.EQUAL);
            keyValueMap.put(splittedKeyValue[0].trim(), splittedKeyValue[1].trim());
        }
        return keyValueMap;
    }
}
