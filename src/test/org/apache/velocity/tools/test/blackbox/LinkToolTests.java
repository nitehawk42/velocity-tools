package org.apache.velocity.tools.test.blackbox;

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

import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.tools.view.tools.LinkTool;
import org.apache.velocity.tools.view.context.ViewContext;
import org.apache.velocity.tools.view.context.ChainedContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>LinkTool tests.</p>
 *
 * @author Christopher Schultz
 * @version $Id$
 */
public class LinkToolTests
{
    public @Test void testAddAllParameters()
    {
        HashMap params = new HashMap();
        params.put("a", "b");
        InvocationHandler handler = new ServletAdaptor("/test", params);
        Object proxy
            = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                     new Class[] { HttpServletRequest.class,
                                                   HttpServletResponse.class },
                                     handler);

        HttpServletRequest request = (HttpServletRequest)proxy;
        HttpServletResponse response = (HttpServletResponse)proxy;
        ViewContext vc = new ChainedContext(null, // Velocity
                                            request, // Request
                                            response, // Response
                                            null // application
                                            );

        LinkTool link = new LinkTool();
        link.init(vc);

        String url = link.setRelative("/target")
            .addQueryData("foo", "bar")
            .addQueryData("bar", "baz")
            .addAllParameters()
            .toString();

        Assert.assertEquals("/test/target?foo=bar&amp;bar=baz&amp;a=b", url);
    }

    public @Test void testAddMultiValueParameters()
    {
        HashMap params = new HashMap();
        params.put("a", new String[] { "a", "b", "c" });
        InvocationHandler handler = new ServletAdaptor("/test", params);
        Object proxy
            = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                     new Class[] { HttpServletRequest.class,
                                                   HttpServletResponse.class },
                                     handler);

        HttpServletRequest request = (HttpServletRequest)proxy;
        HttpServletResponse response = (HttpServletResponse)proxy;
        ViewContext vc = new ChainedContext(null, // Velocity
                                            request, // Request
                                            response, // Response
                                            null // application
                                            );

        LinkTool link = new LinkTool();
        link.init(vc);

        String url = link.setRelative("/target")
            .addQueryData("foo", "bar")
            .addQueryData("bar", "baz")
            .addAllParameters()
            .toString();

        Assert.assertEquals("/test/target?foo=bar&amp;bar=baz&amp;a=a&amp;a=b&amp;a=c", url);
    }

    public @Test void testAddIgnoreParameters()
    {
        HashMap params = new HashMap();
        params.put("a", "b");
        params.put("b", "c");
        InvocationHandler handler = new ServletAdaptor("/test", params);
        Object proxy
            = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                     new Class[] { HttpServletRequest.class,
                                                   HttpServletResponse.class },
                                     handler);

        HttpServletRequest request = (HttpServletRequest)proxy;
        HttpServletResponse response = (HttpServletResponse)proxy;
        ViewContext vc = new ChainedContext(null, // Velocity
                                            request, // Request
                                            response, // Response
                                            null // application
                                            );

        LinkTool link = new LinkTool();
        link.init(vc);

        String url = link.setRelative("/target")
            .addQueryData("foo", "bar")
            .addQueryData("bar", "baz")
            .addIgnore("b")
            .addAllParameters()
            .toString();

        Assert.assertEquals("/test/target?foo=bar&amp;bar=baz&amp;a=b", url);
    }

    public @Test void testAddAllParametersFirst()
    {
        HashMap params = new HashMap();
        params.put("a", "b");
        InvocationHandler handler = new ServletAdaptor("/test", params);
        Object proxy
            = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                     new Class[] { HttpServletRequest.class,
                                                   HttpServletResponse.class },
                                     handler);

        HttpServletRequest request = (HttpServletRequest)proxy;
        HttpServletResponse response = (HttpServletResponse)proxy;
        ViewContext vc = new ChainedContext(null, // Velocity
                                            request, // Request
                                            response, // Response
                                            null // application
                                            );

        LinkTool link = new LinkTool();
        link.init(vc);

        String url = link.setRelative("/target")
            .addAllParameters()
            .addQueryData("foo", "bar")
            .addQueryData("bar", "baz")
            .toString();

        Assert.assertEquals("/test/target?a=b&amp;foo=bar&amp;bar=baz", url);
    }

}
