package com.cognifide.aemrules.checks.visitors;

import java.util.Set;

import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

import com.google.common.collect.Sets;

public class AnnotationsVisitor extends BaseTreeVisitor {

	private final Set<String> annotationTypes;

	private boolean annotatedWithAny;

	public AnnotationsVisitor(String... annotationTypes) {
		this.annotationTypes = Sets.newHashSet(annotationTypes);
	}

	@Override
	public void visitAnnotation(AnnotationTree annotationTree) {
		annotatedWithAny |= annotationTypes.remove(annotationTree.symbolType().fullyQualifiedName());
		super.visitAnnotation(annotationTree);
	}

	public boolean isAnnotatedWithAny() {
		return annotatedWithAny;
	}

	public boolean isAnnotatedWithAll() {
		return annotationTypes.isEmpty();
	}
}
