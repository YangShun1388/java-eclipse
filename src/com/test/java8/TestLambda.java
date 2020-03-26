package com.test.java8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

public class TestLambda {
	
	@Test
	public void test() {
		System.out.println("Test Junit 5");
	}
	@Test
	public void test1() {
		Consumer<String> con = (x) -> System.out.println(x);
		con.accept("test lambda!");
		
		Supplier<String> sp = () -> "hello";
		System.out.println(sp.get());
		
		Function<String, String> fc = (x) -> x.substring(0,10);
		fc.apply("hello woreld!");
		
	}
}
