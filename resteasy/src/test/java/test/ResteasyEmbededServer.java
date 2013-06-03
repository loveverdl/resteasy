package test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import first.test.HelloWorld;

public class ResteasyEmbededServer extends SpringJUnit4ClassRunner {

	private TJWSEmbeddedJaxrsServer server = new TJWSEmbeddedJaxrsServer();

	public ResteasyEmbededServer(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	public void run(RunNotifier notifier) {
		int port = findAnnotationValueByClass(Port.class).value();
		Class[] resourceClasses = findAnnotationValueByClass(Resources.class)
				.value();

		startServer(port, resourceClasses);
		try {
			super.run(notifier);
		} finally {
			server.stop();
		}
	}

	private void startServer(int port, Class[] resourceClasses) {
		server.setPort(port);
		List<Class> actualResourceClasses = server.getDeployment()
				.getActualResourceClasses();
		Collections.addAll(actualResourceClasses, resourceClasses);
		server.start();
	}

	private <T> T findAnnotationValueByClass(Class<T> annotationClass) {
		for (Annotation annotation : getTestClass().getAnnotations()) {
			if (annotation.annotationType().equals(annotationClass)) {
				return (T) annotation;
			}
		}
		throw new IllegalStateException(String.format(
				"Can't find %s on test class: %s", annotationClass,
				getTestClass()));
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Resources {
		public Class[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Port {
		public int value();
	}
}
