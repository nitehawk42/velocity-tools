package org.apache.velocity.tools.generic;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

/**
 * <p>Tests for LoopTool</p>
 *
 * @author Nathan Bubna
 * @since VelocityTools 2.0
 * @version $Id$
 */
public class LoopToolTests {

    public static final String[] ARRAY = { "foo", "bar", "woogie" };

    public @Test void ctorLoopTool() throws Exception
    {
        try
        {
            new LoopTool();
        }
        catch (Exception e)
        {
            fail("Constructor 'LoopTool()' failed due to: " + e);
        }
    }

    public @Test void methodSkip_int() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY);
        // skip nothing
        loop.skip(0);
        assertEquals(i.next(), ARRAY[0]);
        // skip one (should be on 2 now)
        loop.skip(1);
        assertEquals(i.next(), ARRAY[2]);
        // end this
        loop.pop();
        // start over to skip 2
        i = loop.watch(ARRAY);
        loop.skip(2);
        assertEquals(i.next(), ARRAY[2]);
    }

    public @Test void methodSkip_intString() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY, "i");
        Iterator j = loop.watch(ARRAY, "j");
        Iterator k = loop.watch(ARRAY, "k");
        // these should not do anything
        loop.skip(1, null);
        loop.skip(1, "");
        loop.skip(1, "test");
        // these should work
        loop.skip(1, "i");
        loop.skip(1, "j");
        loop.skip(1, "k");
        assertEquals(i.next(), ARRAY[1]);
        assertEquals(j.next(), ARRAY[1]);
        assertEquals(k.next(), ARRAY[1]);
    }

    public @Test void methodStop() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY);
        assertTrue(i.hasNext());
        loop.stop();
        assertFalse(i.hasNext());
    }

    public @Test void methodStopAll() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY);
        Iterator j = loop.watch(ARRAY);
        Iterator k = loop.watch(j);
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());
        loop.stopAll();
        assertFalse(i.hasNext() || j.hasNext() || k.hasNext());
    }

    public @Test void methodStopTo_String() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY, "i");
        Iterator j = loop.watch(ARRAY, "j");
        Iterator k = loop.watch(ARRAY, "k");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());

        // these shouldn't stop anything
        loop.stopTo(null);
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());
        loop.stopTo("");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());
        loop.stopTo("test");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());

        // this should only stop j and k
        loop.stopTo("j");
        assertTrue(i.hasNext());
        assertFalse(j.hasNext() || k.hasNext());
    }

    public @Test void methodStop_String() throws Exception
    {
        LoopTool loop = new LoopTool();
        Iterator i = loop.watch(ARRAY, "i");
        Iterator j = loop.watch(ARRAY, "j");
        Iterator k = loop.watch(ARRAY, "k");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());

        // these shouldn't stop anything
        loop.stop(null);
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());
        loop.stop("");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());
        loop.stop("test");
        assertTrue(i.hasNext() && j.hasNext() && k.hasNext());

        // this should only stop j
        loop.stop("j");
        assertTrue(i.hasNext() && k.hasNext());
        assertFalse(j.hasNext());
    }

    public @Test void methodWatch_Object() throws Exception
    {
        LoopTool loop = new LoopTool();
        // try to watch unwatchable things
        Iterator i = loop.watch(null);
        assertNull(i);
        assertNull(loop.watch(new Object()));
        // watch an array
        assertNotNull(loop.watch(ARRAY));
        // watch an iterator
        assertNotNull(loop.watch(loop.watch(ARRAY)));
        //TODO: watch enumeration
        // watch collection
        assertNotNull(loop.watch(LIST));
        // watch map
        assertNotNull(loop.watch(new HashMap()));
        // watch iterable
        assertNotNull(loop.watch(new MyIterable()));
        // watch object w/iterator method
        assertNotNull(loop.watch(new HasIteratorMethod()));
    }

    static final Collection LIST = Arrays.asList(ARRAY);
    public static class HasIteratorMethod
    {
        public Iterator iterator()
        {
            return LoopToolTests.LIST.iterator();
        }
    }
    public static class MyIterable extends HasIteratorMethod implements Iterable
    {
    }

    public @Test void methodWatch_ObjectString() throws Exception
    {
        LoopTool loop = new LoopTool();
        // null names are invalid
        Iterator i = loop.watch(ARRAY, null);
        assertNull(i);
        // empty names are ok
        assertNotNull(loop.watch(ARRAY, ""));
        // so are real ones
        assertNotNull(loop.watch(ARRAY, "name"));
    }

}
        