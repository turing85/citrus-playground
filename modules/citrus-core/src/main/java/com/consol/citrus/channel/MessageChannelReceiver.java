/*
 * Copyright 2006-2010 the original author or authors.
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

package com.consol.citrus.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.channel.*;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.util.StringUtils;

import com.consol.citrus.exceptions.ActionTimeoutException;
import com.consol.citrus.exceptions.CitrusRuntimeException;
import com.consol.citrus.message.AbstractMessageReceiver;
import com.consol.citrus.message.MessageReceiver;

/**
 * Receive messages from {@link MessageChannel} instance.
 * @author Christoph Christoph
 */
public class MessageChannelReceiver extends AbstractMessageReceiver implements BeanFactoryAware {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(MessageChannelReceiver.class);
    
    /** Pollable channel */
    private PollableChannel channel;
    
    /** Destination channel name */
    private String channelName;
    
    /** Message channel template */
    private MessageChannelTemplate messageChannelTemplate = new MessageChannelTemplate();
    
    /** The parent bean factory used for channel name resolving */
    private BeanFactory beanFactory;
    
    /** Channel resolver instance */
    private ChannelResolver channelResolver;
    
    /**
     * @see MessageReceiver#receive(long)
     * @throws ActionTimeoutException
     */
    @Override
    public Message<?> receive(long timeout) {
        String channelName = getDestinationChannelName();
        
        log.info("Receiving message from: " + channelName);
        
        messageChannelTemplate.setReceiveTimeout(timeout);
        Message<?> received = messageChannelTemplate.receive(getDestinationChannel());
        
        if(received == null) {
            throw new ActionTimeoutException("Action timeout while receiving message from channel '"
                    + channelName + "'");
        }
        
        return received;
    }

    /**
     * @see MessageReceiver#receiveSelected(String, long)
     */
    @Override
    public Message<?> receiveSelected(String selector, long timeout) {
        throw new UnsupportedOperationException("MessageChannelTemplate " +
        		"does not support selected receiving.");
    }
    
    /**
     * Get the destination channel depending on settings in this message sender.
     * Either a direct channel object is set or a channel name which will be resolved 
     * to a channel.
     * 
     * @return the destination channel object.
     */
    private PollableChannel getDestinationChannel() {
        if (channel != null) {
            return channel;
        } else if (StringUtils.hasText(channelName)) {
            MessageChannel messageChannel = resolveChannelName(channelName);
            if (messageChannel instanceof PollableChannel) {
                return (PollableChannel)messageChannel;
            } else {
                throw new CitrusRuntimeException("Invalid destination channel type " + messageChannel.getClass().getName()
                        + " - must be of type PollableChannel");
            }
        } else {
            throw new CitrusRuntimeException("Neither channel name nor channel object is set - " +
                    "please specify destination channel");
        }
    }

    /**
     * Gets the channel name depending on what is set in this message sender. 
     * Either channel name is set directly or channel object is consulted for channel name.
     * 
     * @return the channel name.
     */
    private String getDestinationChannelName() {
        if (channel != null) {
            return channel.getName();
        } else if (StringUtils.hasText(channelName)) {
            return channelName;
        } else {
            throw new CitrusRuntimeException("Neither channel name nor channel object is set - " +
                    "please specify destination channel");
        }
    }

    /**
     * Resolve the channel by name.
     * @param channelName the name to resolve
     * @return the MessageChannel object
     */
    protected MessageChannel resolveChannelName(String channelName) {
        if (channelResolver == null) {
            channelResolver = new BeanFactoryChannelResolver(beanFactory);
        }
        
        return channelResolver.resolveChannelName(channelName);
    }

    /**
     * Set the target channel to receive message from.
     * @param channel the channel to set
     */
    public void setChannel(PollableChannel channel) {
        this.channel = channel;
    }

    /**
     * Set the message channel template.
     * @param messageChannelTemplate the messageChannelTemplate to set
     */
    public void setMessageChannelTemplate(
            MessageChannelTemplate messageChannelTemplate) {
        this.messageChannelTemplate = messageChannelTemplate;
    }
    
    /**
     * Sets the bean factory for channel resolver.
     * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     */
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    /**
     * Sets the destination channel name.
     * @param channelName the channelName to set
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    /**
     * Set the channel resolver.
     * @param channelResolver the channelResolver to set
     */
    public void setChannelResolver(ChannelResolver channelResolver) {
        this.channelResolver = channelResolver;
    }
}
