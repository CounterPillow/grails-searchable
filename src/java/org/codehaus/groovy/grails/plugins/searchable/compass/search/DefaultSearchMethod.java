/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.searchable.compass.search;

import groovy.lang.Closure;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.plugins.searchable.SearchableMethod;
import org.codehaus.groovy.grails.plugins.searchable.compass.support.AbstractSearchableMethod;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.compass.core.*;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * The default search method implementation
 *
 * @author Maurice Nicholson
 */
public class DefaultSearchMethod extends AbstractSearchableMethod implements SearchableMethod {
    private static Log LOG = LogFactory.getLog(DefaultSearchMethod.class);

    private SearchableCompassQueryBuilder compassQueryBuilder;
    private SearchableHitCollector hitCollector;
    private SearchableSearchResultFactory searchResultFactory;
    
    public DefaultSearchMethod(String methodName, Compass compass, Map defaultOptions) {
        super(methodName, compass, defaultOptions);
    }

    public Object invoke(Object[] args) {
        Assert.notNull(args, "args cannot be null");
        Assert.notEmpty(args, "args cannot be empty");

        final Object query = getQuery(args);
        Assert.notNull(query, "No query String or Closure argument given to " + getMethodName() + "(): you must supply one");
        final Map options = getOptions(args);

        return doInCompass(new CompassCallback() {
            public Object doInCompass(CompassSession session) throws CompassException {
                CompassQuery compassQuery = compassQueryBuilder.buildQuery(session, options, query);
                long start = System.currentTimeMillis();
                CompassHits hits = compassQuery.hits();
                if (LOG.isDebugEnabled()) {
                    long time = System.currentTimeMillis() - start;
                    LOG.debug("query: [" + compassQuery + "], [" + hits.length() + "] hits, took [" + time + "] millis");
                }
//                long time = System.currentTimeMillis() - start;
//                System.out.println("query: [" + compassQuery + "], [" + hits.length() + "] hits, took [" + time + "] millis");
                Object collectedHits = hitCollector.collect(hits, options);
                return searchResultFactory.buildSearchResult(hits, collectedHits, options);
            }
        });
    }

    private Object getQuery(Object[] args) {
        for (int i = 0, max = args.length; i < max; i++) {
            if (args[i] instanceof Closure || args[i] instanceof String) {
                return args[i];
            }
        }
        return null;
    }

    public void setCompassQueryBuilder(SearchableCompassQueryBuilder compassQueryBuilder) {
        this.compassQueryBuilder = compassQueryBuilder;
    }

    public void setHitCollector(SearchableHitCollector hitCollector) {
        this.hitCollector = hitCollector;
    }

    public void setSearchResultFactory(SearchableSearchResultFactory searchResultFactory) {
        this.searchResultFactory = searchResultFactory;
    }
}
