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

package org.axonframework.core.eventhandler.annotation;

import java.lang.reflect.Method;

/**
 * Thrown when an {@link org.axonframework.core.eventhandler.annotation.EventHandler} annotated method was found that
 * does not conform to the rules that apply to those methods.
 *
 * @author Allard Buijze
 * @see org.axonframework.core.eventhandler.annotation.EventHandler
 * @since 0.1
 */
public class UnsupportedHandlerMethodException extends RuntimeException {

    private final Method violatingMethod;

    /**
     * Initialize the exception with a <code>message</code> and the <code>violatingMethod</code>
     *
     * @param message         a descriptive message of the violation
     * @param violatingMethod the method that violates the rules of annotated Event Handlers
     */
    public UnsupportedHandlerMethodException(String message, Method violatingMethod) {
        super(message);
        this.violatingMethod = violatingMethod;
    }

    /**
     * A reference to the method that violated the event handler rules
     *
     * @return the method that violated the event handler rules
     *
     * @see org.axonframework.core.eventhandler.annotation.EventHandler
     */
    public Method getViolatingMethod() {
        return violatingMethod;
    }
}
