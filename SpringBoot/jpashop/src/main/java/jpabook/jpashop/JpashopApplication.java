package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {

		hello h = new hello();
		h.setData("hello");
		String data = h.getData();
		System.out.println("data = " + data);

		SpringApplication.run(JpashopApplication.class, args);
	}

}
