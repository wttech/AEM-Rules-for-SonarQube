/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2019 Cognifide Limited
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
package com.cognifide.aemrules.utils;

import java.util.*;
import java.util.function.Consumer;

/**
 * @link https://gist.github.com/kendfinger/9007632
 */
public class MultiMap<K, V> {

    private Map<K, Collection<V>> delegate;

    public static <K, V> MultiMap<K, V> builder(Consumer<MultiMap<K, V>> builder) {
        MultiMap<K, V> result = new MultiMap<>();
        builder.accept(result);
        return result;
    }

    public void put(K key, Collection<V> values) {
        createMap();
        delegate.put(key, values);
    }

    private void createMap() {
        if (delegate == null) {
            delegate = new HashMap<>();
        }
    }

    public Collection<V> getAll(K key) {
        if (getAll().containsKey(key)) {
            return getAll().get(key);
        } else {
            LinkedList<V> value = new LinkedList<>();
            getAll().put(key, value);
            return value;
        }
    }

    public void add(K key, V value) {
        getAll(key).add(value);
    }

    public boolean remove(K key, V value) {
        return getAll(key).remove(value);
    }

    public void clear() {
        delegate = null;
        createMap();
    }

    public void clear(K key) {
        put(key, new LinkedList<V>());
    }

    public Map<K, Collection<V>> getAll() {
        createMap();
        return delegate;
    }

    public boolean addAll(K key, Collection<V> values) {
        return getAll(key).addAll(values);
    }

    public boolean isEmpty() {
        return getAll().isEmpty();
    }

    public boolean isEmpty(K key) {
        return getAll(key).isEmpty();
    }

    public boolean containsKey(V key) {
        return getAll().containsKey(key);
    }
}