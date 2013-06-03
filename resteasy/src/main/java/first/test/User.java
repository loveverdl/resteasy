package first.test;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	public User() {

	}

	public User(String name) {
		this.name = name;
		age = 10;
	}

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
