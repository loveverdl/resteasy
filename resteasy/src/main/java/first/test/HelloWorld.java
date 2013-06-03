package first.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;

@Path("/hello world")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class HelloWorld {
	private Map<String, User> userList = new HashMap<String, User>();

	@Path(value = "/{user}")
	@GET
	public User hello(@PathParam("user") @DefaultValue("") String name,
			@CookieParam("sessionid") int id) {
		return userList.get(name);
	}

	@Path(value = "/test")
	@GET
	public String hellotest() {
		return "test";
	}

	@Path(value = "/add")
	@PUT
	public int addUser(List<User> list) {
		System.out.println("before" + userList.size());
		for (User user : list)
			userList.put(user.getName(), user);
		System.out.println("after" + userList.size());
		return userList.size();
	}

	@GET
	@Path(value = "/mult")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String get(MultipartInput input) throws IOException {
		List<InputPart> parts = input.getParts();
		String s = parts.get(0).getBodyAsString();
		User u = parts.get(1).getBody(User.class, null);
		return s + u.getName();
	}
}
