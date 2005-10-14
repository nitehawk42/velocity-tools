/*
 * Copyright 2004 The Apache Software Foundation.
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

package org.apache.velocity.tools.view.tools;

import java.util.Map;

/**
 * Interface for tools that can be passed configuration parameters.
 *
 * @author <a href="mailto:nathan@esha.com">Nathan Bubna</a>
 * @version $Id$
 * @since VelocityTools 1.2
 */
public interface Configurable
{

    /**
     * Configure this tool using the specified parameters. The
     * map of parameters passed in is guaranteed not to be null.
     *
     * @param parameters the configuration data for this tool
     */
    public void configure(Map parameters);

}
