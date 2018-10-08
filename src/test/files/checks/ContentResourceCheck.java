package com.example;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

public class ContentResourceCheck {

  private Page pageA;

  private Page pageB;

  private void noNullCheck() {
    Resource contentResourceA = pageA.getContentResource();
    Resource contentResourceB = pageB.getContentResource();
    Iterable<Resource> children = contentResourceA.getChildren(); // Noncompliant
    if (contentResourceA != null) {
      Iterable<Resource> childrenB = contentResourceB.getChildren(); // Noncompliant
    }
    if (contentResourceA != null) {
      contentResourceA = pageB.getContentResource();
      Iterable<Resource> childrenB = contentResourceA.getChildren(); // Noncompliant
    }
  }

  private void withNullCheck() {
    Resource contentResourceA = pageA.getContentResource();
    Resource contentResourceB = pageB.getContentResource();
    Iterable<Resource> children = null;
    if (contentResourceA != null) {
      children = contentResourceA.getChildren();
      children = contentResourceB.getChildren(); // Noncompliant
    } else if (contentResourceB != null){
      children = contentResourceB.getChildren();
    }
  }
}