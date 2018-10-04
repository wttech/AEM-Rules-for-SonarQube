package com.example;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

public class ContentResourceCheck {

  private Page page;

  private void test() {
    Resource contentResource = page.getContentResource();
    Iterable<Resource> children = contentResource.getChildren(); // Noncompliant
  }

  private void test2() {
    Resource contentResource = page.getContentResource();
    if (contentResource != null) {
      Iterable<Resource> children = contentResource.getChildren();
    }
  }
}
