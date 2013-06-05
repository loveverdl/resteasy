package test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

import first.test.HelloWorld;
import first.test.User;

@RunWith(JUnit4.class)
public class HelloWorldTest {
	private String urlRoot = "http://localhost:8081/resteasy/hello%20world";

	@Test
	public void testHello() {
		HttpClient httpClient = new DefaultHttpClient();
		String urllistallbook = "http://localhost:8081/resteasy/hello%20world/wang";

		HttpGet httpGet = new HttpGet(urllistallbook);
		try {
			HttpResponse response = new DefaultHttpClient().execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			// 204服务器已经处理成功，但没有返回任何数据
			if (code == 200 || code == 204) {
				String content = EntityUtils.toString(response.getEntity());
				System.out.println("listaoobook输出===>" + content);
			} else {
				System.err.println("建立请求失败，返回状态码==》" + code);
				Assert.fail("listbooks");
			}

		} catch (Exception e) {
			// TODO: handle exception
			Assert.fail("listbooks失败" + e.getMessage());
		}
	}

	@Test
	public void addBookTest() {
		ClientRequest request = new ClientRequest(
				"http://localhost:8081/resteasy/hello%20world/add");

		request.header("custom-header", "value");
		// We're posting XML and a JAXB object
		User user = new User();
		user.setName("wang");
		user.setAge(11);
		request.body("application/json", user);
		try {
			request.put();
			this.testHello();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Form f = new Form();
		User user = new User();
		user.setName("wang");
		user.setAge(11);
		f.add("a", "d");
		f.add("u", user);
		Client client = Client.create();
		WebResource resource = client
				.resource("http://localhost:8081/resteasy/hello%20world/mult");

		//
		// ClientRequest request = new ClientRequest(
		// "http://localhost:8081/resteasy/hello%20world/add");
		//
		// request.header("custom-header", "value");
		// // We're posting XML and a JAXB object
		// List<User> list = new ArrayList<User>();
		// User user = new User();
		// user.setName("wang");
		// user.setAge(11);
		// list.add(user);
		// user = new User();
		// user.setName("ling");
		// user.setAge(11);
		//
		// list.add(user);
		// request.body("application/json", list);
		// try {
		// request.put();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// ClientRequest request = new ClientRequest(
		// "http://localhost:8081/resteasy/hello%20world/mult");
		//
		// request.header("custom-header", "value");
		// MultiValueMap<String, Object> m = new LinkedMultiValueMap<String,
		// Object>();
		// m.add("first", "tet");
		// // We're posting XML and a JAXB object
		// User user = new User();
		// user.setName("wang");
		// user.setAge(11);
		// m.add("second", user);
		// request.body(MediaType.MULTIPART_FORM_DATA, m);
		// ClientResponse<String> response = null;
		// try {
		// response = request.put(String.class);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// if (response.getStatus() == 200) // OK!
		// {
		// String str = response.getEntity();
		// System.out.println(str);
		// } else {
		// System.out.print(response.getStatus());
		// }
	}
}
