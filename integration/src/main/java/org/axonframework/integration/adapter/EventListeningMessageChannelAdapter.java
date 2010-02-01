/*
 * Copyright (c) 2010. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.integration.adapter;

import org.axonframework.core.Event;
import org.axonframework.core.eventhandler.EventBus;
import org.axonframework.core.eventhandler.EventListener;
import org.axonframework.core.eventhandler.EventSequencingPolicy;
import org.axonframework.core.eventhandler.FullConcurrencyPolicy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.GenericMessage;

/**
 * Adapter class that sends Events from an event bus to a Spring Integration Message Channel. All events are wrapped in
 * GenericMessage instances. The adapter automatically subscribes itself to the provided <code>EventBus</code>.
 * <p/>
 * Optionally, this adapter can be configured with a filter, which can block or accept messages based on their type.
 *
 * @author Allard Buijze
 * @since 0.4
 */
public class EventListeningMessageChannelAdapter implements EventListener, InitializingBean {

    private final MessageChannel channel;
    private final EventFilter filter;
    private final EventBus eventBus;

    /**
     * Initialize an adapter to forward messages from the given <code>eventBus</code> to the given <code>channel</code>.
     * Messages are not filtered; all messages are forwarded to the MessageChannel
     *
     * @param eventBus The event bus to subscribe to.
     * @param channel  The channel to send event messages to.
     */
    public EventListeningMessageChannelAdapter(EventBus eventBus, MessageChannel channel) {
        this.eventBus = eventBus;
        this.channel = channel;
        this.filter = new NoFilter();
    }

    /**
     * Initialize an adapter to forward messages from the given <code>eventBus</code> to the given <code>channel</code>.
     * Messages are filtered using the given <code>filter</code>
     *
     * @param eventBus The event bus to subscribe to.
     * @param channel  The channel to send event messages to.
     * @param filter   The filter that indicates which messages to forward.
     */
    public EventListeningMessageChannelAdapter(EventBus eventBus, MessageChannel channel, EventFilter filter) {
        this.channel = channel;
        this.eventBus = eventBus;
        this.filter = filter;
    }

    /**
     * Subscribes this event listener to the event bus.
     */
    @Override
    public void afterPropertiesSet() {
        eventBus.subscribe(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(Class<? extends Event> eventType) {
        return filter.accept(eventType);
    }

    /**
     * If allows by the filter, wraps the given <code>event</code> in a {@link GenericMessage} ands sends it to the
     * configured {@link MessageChannel}.
     *
     * @param event the event to handle
     */
    @Override
    public void handle(Event event) {
        if (filter.accept(event.getClass())) {
            channel.send(new GenericMessage<Event>(event));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventSequencingPolicy getEventSequencingPolicy() {
        return new FullConcurrencyPolicy();
    }

}
