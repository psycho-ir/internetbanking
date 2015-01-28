package com.samenea.payments.testutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Just a util class to test application events
 * It will help a test to examine events raised on a specific actions
 */
public class ApplicationEventCollector implements ApplicationListener<ApplicationEvent>{
    private final List<ApplicationEvent> collectedEvents = new ArrayList<ApplicationEvent>();
    private Logger logger = LoggerFactory.getLogger(ApplicationEventCollector.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        collectedEvents.add(event);
        logger.debug("****** Event Occurred is : source {} and time stamp is {}", event.getSource(), event.getTimestamp());
    }

    /**
     * clear collected events
     */
    public void reset(){
        collectedEvents.clear();
    }

    /**
     *
     * @return collected Events
     */
    public List<ApplicationEvent> getCollectedEvents() {
        return Collections.unmodifiableList(collectedEvents);
    }
    public int countCollectedEvents(){
        return collectedEvents.size();
    }

    /**
     * Counts number of events occurred for a specific type and its subtypes
     * @param eventClass the class of event
     * @return number of occurrences for this event type or its subtypes
     */
    public int countOccurrences(Class<? extends ApplicationEvent> eventClass){
        int numberOfOccurrences = 0;
        for (ApplicationEvent collectedEvent : collectedEvents) {
            if(eventClass.isAssignableFrom(collectedEvent.getClass()) ){
                numberOfOccurrences ++;
            }
        }
        return numberOfOccurrences;
    }
}
