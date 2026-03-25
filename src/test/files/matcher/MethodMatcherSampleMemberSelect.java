package com.vml.test;

class MyClass1 {
}

class MyClass2 {
}

class TestClass {
  void test(MyClass1 m1, MyClass2 m2) {
  }

  void bar() {
    MyClass1 m1 = new MyClass1();
    MyClass2 m2 = new MyClass2();
    this.test(m1, m2);
  }
}
