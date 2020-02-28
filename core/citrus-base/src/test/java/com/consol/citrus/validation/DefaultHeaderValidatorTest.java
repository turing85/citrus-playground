/*
 * Copyright 2006-2018 the original author or authors.
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

package com.consol.citrus.validation;

import java.util.Collections;

import com.consol.citrus.UnitTestSupport;
import com.consol.citrus.exceptions.ValidationException;
import com.consol.citrus.validation.context.HeaderValidationContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Christoph Deppisch
 * @since 2.7.6
 */
public class DefaultHeaderValidatorTest extends UnitTestSupport {

    private DefaultHeaderValidator validator = new DefaultHeaderValidator();
    private HeaderValidationContext validationContext = new HeaderValidationContext();

    @Test(dataProvider = "successData")
    public void testValidateHeader(Object receivedValue, Object controlValue) {
        validator.validateHeader("foo", receivedValue, controlValue, context, validationContext);
    }

    @DataProvider
    public Object[][] successData() {
        return new Object[][] {
            new Object[] { "foo", "foo" },
            new Object[] { null, "" },
            new Object[] { null, null },
            new Object[] { new String[] {"foo", "bar"}, new String[] {"foo", "bar"} },
            new Object[] { Collections.singletonMap("key", "value"), Collections.singletonMap("key", "value") }
        };
    }

    @Test
    public void testValidateHeaderVariableSupport() {
        context.setVariable("control", "bar");

        validator.validateHeader("foo", "bar", "${control}", context, validationContext);
    }

    @Test
    public void testValidateHeaderValidationMatcherSupport() {
        validator.validateHeader("foo", "bar", "@ignore@", context, validationContext);
        validator.validateHeader("foo", "bar", "@hasLength(3)@", context, validationContext);
    }

    @Test(dataProvider = "errorData", expectedExceptions = ValidationException.class)
    public void testValidateHeaderError(Object receivedValue, Object controlValue) {
        validator.validateHeader("foo", receivedValue, controlValue, context, validationContext);
    }

    @DataProvider
    public Object[][] errorData() {
        return new Object[][] {
                new Object[] { "foo", "wrong" },
                new Object[] { null, "wrong" },
                new Object[] { "foo", null },
                new Object[] { new String[] {"foo", "bar"}, new String[] {"foo", "wrong"} },
                new Object[] { Collections.singletonMap("key", "value"), Collections.singletonMap("key", "wrong") }
        };
    }
}
