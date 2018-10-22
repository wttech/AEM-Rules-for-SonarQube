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

import java.util.ArrayList;
import java.util.List;

public class SynchornizedKeywordUsageCheck {

    private final List<String> list = new ArrayList<>(16);

    public synchronized void addElement(String s) { // Noncompliant {{Usage of 'synchronized' keyword should be avoided if possible.}}
        list.add(s);
    }

    public void removeElement(String s) {
        synchronized (list) { // Noncompliant
            list.remove(s);
        }
    }

    private int counter = 0;

    public synchronized void increment() { // Noncompliant
        counter++;
    }

    @Override
    public synchronized void doubleIncrement() { // Noncompliant
        counter++;
        counter++;
    }

    @Override
    public synchronized void tripleIncrement() { // Noncompliant
        counter++;
        counter++;
        counter++;
    }

    public
    synchronized // Noncompliant
    void
    quadrupleIncrement() {
        counter++;
        counter++;
        counter++;
        counter++;
    }
}
