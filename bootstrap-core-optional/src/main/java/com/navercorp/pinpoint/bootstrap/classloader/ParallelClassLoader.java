/*
 * Copyright 2017 NAVER Corp.
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

package com.navercorp.pinpoint.bootstrap.classloader;


import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author Taejin Koo
 */
class ParallelClassLoader extends BaseClassLoader {

    static {
        if (ClassLoader.registerAsParallelCapable()) {
            System.err.println("PINPOINT ClassLoader::registerAsParallelCapable() fail");
        }
    }

    private final BootLoader bootLoader = new LauncherBootLoader();

    public ParallelClassLoader(URL[] urls, ClassLoader parent) {
        this(urls, parent, PROFILER_LIB_CLASS);
    }

    public ParallelClassLoader(URL[] urls, ClassLoader parent, LibClass libClass) {
        super(urls, parent, libClass);
    }

    @Override
    protected URL findBootstrapResource0(String name) {
        return bootLoader.findResource(name);
    }

    @Override
    protected Enumeration<URL> findBootstrapResources0(String name) throws IOException {
        return this.bootLoader.findResources(name);
    }

    @Override
    protected Object getClassLoadingLock0(String name) {
        return getClassLoadingLock(name);
    }


    @Override
    protected Class findBootstrapClassOrNull0(ClassLoader classLoader, String name) {
        return this.bootLoader.findBootstrapClassOrNull(classLoader, name);
    }
}
