/*
 * Copyright (c) 2010-2012. Axon Framework
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

package org.axonframework.eventstore.mongo.criteria;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.conversions.Bson;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Allard Buijze
 */
public class SimpleMongoOperatorTest {

    @Test
    public void testSimpleOperator() {
        Bson dbObject = new SimpleMongoOperator(new MongoProperty("prop"), "$bla", "value").asBson();

        String actual = dbObject.toString();
        assertEquals("{ \"prop\" : { \"$bla\" : \"value\"}}", actual);
    }
}
