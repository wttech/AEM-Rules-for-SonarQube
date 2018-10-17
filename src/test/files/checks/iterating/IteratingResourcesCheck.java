/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
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
 * #L%
 */
package com.example;

import com.cognifide.slice.api.provider.ModelProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.sling.api.resource.Resource;

public class IteratingResourcesCheck {

    public List<SimpleModel> iteratingWhile(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        while (resources.hasNext()) { // Noncompliant {{Use ModelProvider#getListFromResources instead of iteration}}
            models.add(modelProvider.get(SimpleModel.class, resources.next()));
        }
        return models;
    }

    public List<SimpleModel> iteratingDoWhile(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        Resource resource = null;
        do { // Noncompliant
            resource = resources.hasNext() ? resources.next() : null;
            if (null != resource) {
                models.add(modelProvider.get(SimpleModel.class, resource));
            }
        } while (null != resource);
        return models;
    }

    public List<SimpleModel> iteratingWhileInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        while (resources.hasNext()) { // Noncompliant
            Resource resource = resources.next();
            addModel(models, modelProvider, SimpleModel.class, resource);
        }
        return models;
    }

    public List<SimpleModel> iteratingDoWhile(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        Resource resource = null;
        do { // Noncompliant
            resource = resources.hasNext() ? resources.next() : null;
            if (null != resource) {
                addModel(models, modelProvider, SimpleModel.class, resource);
            }
        } while (null != resource);
        return models;
    }

    public List<SimpleModel> iteratingDoWhileRightOperand(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        Resource resource = null;
        do { // Noncompliant
            resource = resources.hasNext() ? resources.next() : null;
            if (null != resource) {
                addModel(models, modelProvider, SimpleModel.class, resource);
            }
        } while (resource != null);
        return models;
    }

    public List<SimpleModel> iteratingFor(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        for (Iterator<Resource> iterator = resources; iterator.hasNext(); ) { // Noncompliant
            models.add(modelProvider.get(SimpleModel.class, iterator.next()));
        }
        return models;
    }

    public List<SimpleModel> iteratingForInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        for (Iterator<Resource> iterator = resources; iterator.hasNext(); ) { // Noncompliant
            Resource resource = iterator.next();
            addModel(models, modelProvider, SimpleModel.class, resource);
        }
        return models;
    }

    public List<SimpleModel> iteratingForInnerMethod(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        for (; resources.hasNext(); ) { // Noncompliant
            Resource resource = iterator.next();
            addModel(models, modelProvider, SimpleModel.class, resource);
        }
        return models;
    }


    private void addModel(List<SimpleModel> models, ModelProvider modelProvider, Class<?> modelClass, Resource resource) {
        models.add(modelProvider.get(modelClass, resource));
    }

    public List<SimpleModel> iteratingForEach(Iterable<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        for (Resource resource : resources) { // Noncompliant
            models.add(modelProvider.get(SimpleModel.class, resource));
        }
        return models;
    }

    public List<SimpleModel> iteratingForNotCallingModelProvider(Iterator<Resource> resources, ModelProvider modelProvider) {
        List<SimpleModel> models = new ArrayList<>();
        for (Iterator<Resource> iterator = resources; iterator.hasNext(); ) {
            //models.add(modelProvider.get(SimpleModel.class, resources.next()));
        }
        return models;
    }

    public void a(List<String> list) {
        for (Iterator<? extends CharSequence> iter = list.iterator(); iter.hasNext(); ) {
            CharSequence seq = iter.next();
            FileInputStream fis = null;
            if (seq.equals("sequence")) {
                try {
                    fis = new FileInputStream((String) seq);
                } catch (FileNotFoundException x) {
                    //not important
                } finally {
                    if (null != fis) {
                        try {
                            fis.close();
                        } catch (IOException x) {
                            //not important
                        }
                    }
                }
            }
        }
    }

    public List<SimpleModel> getModelsListFromIterator(Iterator<Resource> resources, ModelProvider modelProvider) {
        return modelProvider.getListFromResources(SimpleModel.class, resources);
    }

    public List<SimpleModel> getModelsListFromItarable(Iterable<Resource> resources, ModelProvider modelProvider) {
        return modelProvider.getListFromResources(SimpleModel.class, resources.iterator());
    }

    public void loops(Iterator<String> stringIt, List<String> stringList, Resource resource, ModelProvider modelProvider) {
        for (; ; ) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        for (int i = 0; i < 1; i++) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        while (true) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        do {
            modelProvider.get(SimpleModel.class, resource);
            break;
        } while (true);
        for (String s : Collections.<String>emptyList()) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        for (Iterator<String> it = stringList.iterator(); it.hasNext(); ) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        for (Iterator<String> it = stringIt; it.hasNext(); ) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        for (; stringIt.hasNext(); ) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        while (stringList.iterator().hasNext()) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        do {
            modelProvider.get(SimpleModel.class, resource);
            break;
        } while (stringList.iterator().hasNext());
        while (stringIt.hasNext()) {
            modelProvider.get(SimpleModel.class, resource);
            break;
        }
        do {
            modelProvider.get(SimpleModel.class, resource);
            break;
        } while (stringIt.hasNext());
    }

    class SimpleModel {

    }
}