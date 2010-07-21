package com.cmayes.common.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps {@link HttpServletRequest}s to set the <code>accept</code> header to
 * the MIME type implied by the extension in the URI (if any). Also strips the
 * extension from the URI for {@link HttpServletRequest#getRequestURI()}. Based
 * on the post at {@link http
 * ://www.zienit.nl/blog/2010/01/rest/control-jax-rs-content
 * -negotiation-with-filters}.
 * 
 * @author cmayes
 */
public class AcceptServletFilter implements Filter {
    /** The key for the <code>accept</code> header. */
    public static final String ACCEPT_KEY = "accept";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** Holds a map of extensions to MIME types. */
    private Map<String, String> map = new HashMap<String, String>();

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
        logger.debug("Map after init: " + map);
    }

    /**
     * Not needed for this filter.
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * Wraps the servlet request to intercept calls to
     * {@link HttpServletRequest#getRequestURI()},
     * {@link HttpServletRequest#getServletPath()},
     * {@link HttpServletRequest#getHeader(String)}, and
     * {@link HttpServletRequest#getHeaders(String)}, manipulating the results
     * to include MIME type data implied by the URI extension (e.g. foo.json).
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
        final String extension = getExtension(uri);
        final String type = map.get(extension);
        final List<String> values = new ArrayList<String>();
        if (type != null) {
            values.add(type);
        } else {
            final Enumeration<String> headers = srvRequest
                    .getHeaders(ACCEPT_KEY);
            while (headers.hasMoreElements()) {
                values.add(headers.nextElement());
            }
        }
        chain.doFilter(new Wrapper(srvRequest, extension, values), response);
    }

    /**
     * Looks for supported extensions in the URI path.
     * 
     * @param path
     *            The URI to process.
     * @return The found extension or a blank string.
     */
    private String getExtension(final String path) {
        final int index = path.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        final String extension = path.substring(index);
        if (map.containsKey(extension)) {
            return extension;
        }
        return "";
    }

    /**
     * Wraps the request to interpret supported URI extensions as requests for a
     * specific MIME type. Modifies the URI, servlet path, and the Accept header
     * as needed.
     * 
     * @author cmayes
     */
    private static final class Wrapper extends HttpServletRequestWrapper {
        /** Logger. */
        private final Logger logger = LoggerFactory.getLogger(getClass());
        /** The <code>accept</code> header values. */
        private final List<String> accept;
        /** The extension for the accept header (if any). */
        private final String acceptExtension;

        /**
         * Wraps the request.
         * 
         * @param request
         *            The servlet request to wrap.
         * @param extension
         *            The extension of the supported extension, a blank string
         *            otherwise.
         * @param theMime
         *            The values for the <code>accept</code> header.
         */
        public Wrapper(final HttpServletRequest request,
                final String extension, final List<String> theMime) {
            super(request);
            accept = theMime;
            acceptExtension = extension;
            logger.debug("Initialized with extension {}, accept: {}",
                    acceptExtension, accept);
        }

        /***
         * Strips the extension if it's found.
         * 
         * @return the part of this request's URL from the protocol name up to
         *         the query string in the first line of the HTTP request.
         * @see javax.servlet.http.HttpServletRequestWrapper#getRequestURI()
         */
        @Override
        public String getRequestURI() {
            // The URI can change (JSP resolution, etc), so we need to strip
            // each call.
            return stripAcceptExtension(super.getRequestURI());
        }

        /**
         * Strips the extension if it's found.
         * 
         * @return the part of this request's URL that calls the servlet. This
         *         path starts with a "/" character and includes either the
         *         servlet name or a path to the servlet, but does not include
         *         any extra path information or a query string.
         * @see javax.servlet.http.HttpServletRequestWrapper#getServletPath()
         */
        @Override
        public String getServletPath() {
            // The URI can change (JSP resolution, etc), so we need to strip
            // each call.
            return stripAcceptExtension(super.getServletPath());
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
                return java.util.Collections.enumeration(accept);
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
                    return accept.get(0);
                } else {
                    return null;
                }
            }
            return super.getHeader(name);
        }

        /**
         * Removes the stripped accept URI extension.
         * 
         * @param path
         *            The URI to process.
         * @return The found extension or a blank string.
         */
        private String stripAcceptExtension(final String path) {
            if (acceptExtension.isEmpty()) {
                return path;
            }

            final int index = path.lastIndexOf('.');
            if (index == -1) {
                return path;
            }
            final String extension = path.substring(index);

            logger.debug("searching for: " + extension);
            if (acceptExtension.equals(extension)) {
                return path.substring(0, path.length() - extension.length());
            }
            return path;
        }
    }
}
