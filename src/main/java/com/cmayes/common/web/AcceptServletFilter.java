package com.cmayes.common.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wraps {@link HttpServletRequest}s to set the <code>accept</code> header to
 * the MIME type implied by the extension in the URI (if any). Also strips the
 * extension from the URI for {@link HttpServletRequest#getRequestURI()}. Based
 * on the post at {@link http
 * ://www.zienit.nl/blog/2010/01/rest/control-jax-rs-content-negotiation
 * -with-filters}.
 * 
 * @author cmayes
 */
public class AcceptServletFilter implements Filter {
	/** The key for the <code>accept</code> header. */
	private static final String ACCEPT_KEY = "accept";
	/** Holds a map of extensions to MIME types. */
	private HashMap<String, String> map = new HashMap<String, String>();

	/**
	 * Reads the mappings defined in the webapp's web.xml. Note that the
	 * extension's dot is added in the method, so you should not include it in
	 * your mapping in the web.xml.
	 * 
	 * @param config
	 *            The configuration.
	 * @throws ServletException
	 *             If there's a problem with the init.
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@SuppressWarnings("unchecked")
	public void init(final FilterConfig config) throws ServletException {
		final Enumeration<String> names = config.getInitParameterNames();
		while (names.hasMoreElements()) {
			final String name = names.nextElement();
			map.put("." + name, config.getInitParameter(name));
		}
		System.out.println("Map: " + map);
	}

	/**
	 * Not needed for this filter.
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * Wraps the servlet request with the processed data related to the URI
	 * extension and the <code>accept</code> header.
	 * 
	 * @param request
	 *            The servlet request.
	 * @param response
	 *            The servlet response.
	 * @param chain
	 *            The filter chain that we add to.
	 * @throws ServletException
	 *             If there's a problem with the filter.
	 * @throws IOException
	 *             If there is a problem filtering the request.
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest srvRequest = (HttpServletRequest) request;
		final String uri = srvRequest.getRequestURI();
		final List<String> extension = getExtension(uri);
		final String type = map.get(extension.get(1));
		final String servletPath;
		final String fullUri;
		final Vector<String> values = new Vector<String>();
		if (type != null) {
			fullUri = extension.get(0);
			servletPath = srvRequest.getServletPath();
			values.add(type);
		} else {
			fullUri = uri;
			servletPath = getExtension(srvRequest.getServletPath()).get(0);
			final Enumeration<String> headers = srvRequest
					.getHeaders(ACCEPT_KEY);
			while (headers.hasMoreElements()) {
				values.add(headers.nextElement());
			}
		}
		chain.doFilter(new Wrapper(srvRequest, fullUri, servletPath, values),
				response);
	}

	/**
	 * Looks for supported extensions in the URI path.
	 * 
	 * @param path
	 *            The URI to process.
	 * @return The found extension or a blank string.
	 */
	private List<String> getExtension(final String path) {
		final int index = path.lastIndexOf('.');
		if (index == -1) {
			return Arrays.asList(path, "");
		}
		final String extension = path.substring(index);
		System.out.println("searching for: " + extension);
		if (map.get(extension) == null) {
			return Arrays.asList(path, "");
		}
		return Arrays.asList(
				path.substring(0, path.length() - extension.length()),
				extension);
	}

	/**
	 * Wraps the request to interpret supported URI extensions as requests for a
	 * specific MIME type. Modifies the URI and the Accept header as needed.
	 * 
	 * @author cmayes
	 */
	private static final class Wrapper extends HttpServletRequestWrapper {
		/** The processed URI. */
		private final String uri;
		/** The processed Servlet path. */
		private final String servletPath;
		/** The <code>accept</code> header values. */
		private final Vector<String> accept;

		/**
		 * Wraps the request.
		 * 
		 * @param request
		 *            The servlet request to wrap.
		 * @param theUri
		 *            The URI with the extension removed (if the extension is
		 *            supported).
		 * @param thePath
		 *            The servlet path with the extension removed (if the
		 *            extension is supported).
		 * @param theMime
		 *            The values for the <code>accept</code> header.
		 */
		public Wrapper(final HttpServletRequest request, final String theUri,
				final String thePath, final Vector<String> theMime) {
			super(request);
			uri = theUri;
			servletPath = thePath;
			accept = theMime;
		}

		@Override
		public String getRequestURI() {
			return uri;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpServletRequestWrapper#getServletPath()
		 */
		@Override
		public String getServletPath() {
			return servletPath;
		}

		/**
		 * Adds the appropriate MIME type to the request header if we have a
		 * supported extension in the URI. If the header is <code>accept</code>,
		 * then we return a single-element vector with the extension-matching
		 * MIME type (if there is one).
		 * 
		 * @param name
		 *            The name of the header to get.
		 * @return The matching header.
		 * @see javax.servlet.http.HttpServletRequestWrapper#getHeaders(java.lang.String)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public Enumeration<String> getHeaders(final String name) {
			if (ACCEPT_KEY.equals(name)) {
				return accept.elements();
			}
			return super.getHeaders(name);
		}

		/**
		 * Returns the appropriate <code>accept</code> header if it's requested,
		 * delegating to the wrapped request otherwise.
		 * 
		 * @param name
		 *            The requested header name.
		 * @return The first value for the requested header, <code>null</code>
		 *         if there is no value.
		 * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.
		 *      String)
		 */
		@Override
		public String getHeader(final String name) {
			if (ACCEPT_KEY.equalsIgnoreCase(name)) {
				if (accept.size() > 0) {
					accept.get(0);
				} else {
					return null;
				}
			}
			return super.getHeader(name);
		}

	}
}
