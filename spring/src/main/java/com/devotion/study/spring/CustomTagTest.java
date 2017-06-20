package com.devotion.study.spring;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomTagTest {

	@SuppressWarnings({ "unchecked", "resource" })
	@Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		List<File> fileList = (List<File>) ctx.getBean("javaList");
		for (File file : fileList) {
			System.out.println("fileName = " + file.getName());
		}
	}

}
