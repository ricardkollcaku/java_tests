package javaTests;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SendFunctionAsParam {
    public void run() {
        A a = new A();
        B b = new B();
        C c = new C();
        b.test(a::call, "dddd");
        c.test(a::call, "sdfsfd");

        E e = new E();
        D d = new D();
        E.Type.REGULAR.priceAlgo.apply(d, 4);

        b.test1(a::call1, "dd");
    }
}

class A {
    public void call(String s) {
        System.out.println("u thirr " + s);
    }

    public String call1(String s, String s1) {
        System.out.println("u thirr  " + s);
        return s;
    }
}

class B {

    public void test(Consumer<String> consumer, String s) {
        consumer.accept(s);
    }

    public void test1(BiFunction<String, String, String> consumer, String s) {
        consumer.apply(s, s);
    }

}

class C {

    public void test(Consumer<String> consumer, String s) {
        consumer.accept(s);
    }

}


class E {
    enum Type {
        REGULAR(D::calcreg), NEW_RELEASE(D::calcnew);

        public final BiFunction<D, Integer, Integer> priceAlgo;

        Type(BiFunction<D, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
        }
    }
}

class D {
    public int calcreg(Integer integer) {
        return 3;
    }

    public int calcnew(Integer integer) {
        return 5;
    }
}
