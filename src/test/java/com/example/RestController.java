package com.example;
public class RestController {

    B b1 = new B();

    public Response 
    put(String path) {

        A a1 = new A();
        a1.bar();

        double r = b1.foo();
        return new Response();
    }





    
}

class Response {

}

class A {

    public A() {

    }

    public void bar() {

    }
}

class B {

    public double foo() {
        return -1.0;
    }

}