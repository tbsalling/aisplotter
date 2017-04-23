/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.tbsalling.ais.plotter.javafx;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Thomas Darimont
 * @see <a href="https://github.com/thomasdarimont/spring-labs/blob/master/spring-boot-javafx/src/main/java/demo/AbstractJavaFxApplicationSupport.java">AbstractJavaFxApplicationSupport.java</a>
 */
public abstract class SpringBootJavaFxApplication extends Application {

    private static String[] savedArgs;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(getClass(), savedArgs);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    protected static void launchApp(Class<? extends SpringBootJavaFxApplication> appClass, String[] args) {
        SpringBootJavaFxApplication.savedArgs = args;
        Application.launch(appClass, args);
    }
}