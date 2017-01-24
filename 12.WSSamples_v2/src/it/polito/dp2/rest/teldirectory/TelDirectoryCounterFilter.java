package it.polito.dp2.rest.teldirectory;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class TelDirectoryCounterFilter implements ContainerRequestFilter {

	CounterImpl counter;

	public TelDirectoryCounterFilter() {
		counter = CounterImpl.getCounter();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getMethod().equals("GET"))
			counter.increment(1);
	}

}
