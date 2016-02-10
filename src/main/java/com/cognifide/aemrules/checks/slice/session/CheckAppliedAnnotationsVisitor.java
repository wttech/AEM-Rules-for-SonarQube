/*
 * Copyright 2016 Cognifide Limited.
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
package com.cognifide.aemrules.checks.slice.session;

import com.cognifide.aemrules.util.TypeUtils;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

class CheckAppliedAnnotationsVisitor extends BaseTreeVisitor {
	
	private static final String SLICE_RESOURCE_ANNOTATION = "com.cognifide.slice.mapper.annotation.SliceResource";
	private boolean sliceResourceAnnotation;

	@Override
	public void visitAnnotation(AnnotationTree annotationTree) {
		sliceResourceAnnotation |= TypeUtils.isOfType(annotationTree.annotationType(), SLICE_RESOURCE_ANNOTATION);
		super.visitAnnotation(annotationTree);
	}

	public boolean hasSliceResourceAnnotation() {
		return sliceResourceAnnotation;
	}
	
}
