package com.cmayes.common.web;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.easymock.Capture;
import org.junit.Test;

/**
 * Tests for {@link AcceptServletFilter}.
 * 
 * @author cmayes
 * 
 */
public class TestAcceptServletFilter {
    private static final String URI_FOR_JSP = "/uri/for.jsp";
    private static final String OTHER_VAL = "otherVal";
    private static final String OTHER_HEADER = "otherHeader";
    /** The XML extension. */
    private static final String XML = "xml";
    /** The atom extension. */
    private static final String ATOM = "atom";
    /** The ATOM URI. */
    private static final String URI_WITH_SUPP_EXT_ATOM = "/uri/with/supp/ext.atom";
    /** The supported URI without an extension. */
    private static final String URI_WITH_SUPP_EXT_BARE = "/uri/with/supp/ext";
    /** An unsuppored extension URI. */
    private static final String URI_WITH_UNSUPP_EXT = "/uri/with/unsupp/ext.badext";
    /** No extension. */
    private static final String URI_WITH_NO_EXT = "/uri/with/no/ext";
    /** Map of extensions to MIME types. */
    private static final Map<String, String> MIME_MAP = new HashMap<String, String>();

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testNoExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_NO_EXT).times(2);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(true).andReturn(false);
        expect(mockEnum.nextElement()).andReturn(MIME_MAP.get(ATOM));
        expect(req.getHeaders(AcceptServletFilter.ACCEPT_KEY)).andReturn(
                mockEnum);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getRequestURI(), is(URI_WITH_NO_EXT));
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testUnsupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_UNSUPP_EXT).times(2);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(true).andReturn(false);
        expect(mockEnum.nextElement()).andReturn(MIME_MAP.get(ATOM));
        expect(req.getHeaders(AcceptServletFilter.ACCEPT_KEY)).andReturn(
                mockEnum);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getRequestURI(), is(URI_WITH_UNSUPP_EXT));
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM).times(2);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getRequestURI(), is(URI_WITH_SUPP_EXT_BARE));
        verify(filterConfig, req, resp, chain);
    }
    
    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testSupportedExtSwitchedUri() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getRequestURI()).andReturn(URI_FOR_JSP);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getRequestURI(), is(URI_FOR_JSP));
        verify(filterConfig, req, resp, chain);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testSupportedExtSwitchedUriNoExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getRequestURI()).andReturn(URI_WITH_NO_EXT);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getRequestURI(), is(URI_WITH_NO_EXT));
        verify(filterConfig, req, resp, chain);
    }


    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testServletSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getServletPath()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getServletPath(), is(URI_WITH_SUPP_EXT_BARE));
        verify(filterConfig, req, resp, chain);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAcceptHeaderSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getServletPath()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getServletPath(), is(URI_WITH_SUPP_EXT_BARE));
        assertThat(wrappedReq.getHeader(AcceptServletFilter.ACCEPT_KEY),
                is(MIME_MAP.get(ATOM)));
        verify(filterConfig, req, resp, chain);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAcceptHeadersSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getServletPath()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getServletPath(), is(URI_WITH_SUPP_EXT_BARE));
        @SuppressWarnings("unchecked")
        final Enumeration<String> headers = wrappedReq
                .getHeaders(AcceptServletFilter.ACCEPT_KEY);
        assertThat(headers.nextElement(), is(MIME_MAP.get(ATOM)));
        assertFalse(headers.hasMoreElements());
        verify(filterConfig, req, resp, chain);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAcceptHeaderUnsupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_UNSUPP_EXT);
        final ServletResponse resp = createMock(ServletResponse.class);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(true).andReturn(false);
        expect(mockEnum.nextElement()).andReturn(MIME_MAP.get(XML));
        expect(req.getHeaders(AcceptServletFilter.ACCEPT_KEY)).andReturn(
                mockEnum);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getHeader(AcceptServletFilter.ACCEPT_KEY),
                is(MIME_MAP.get(XML)));
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAcceptHeadersUnsupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_UNSUPP_EXT);
        final ServletResponse resp = createMock(ServletResponse.class);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(true).andReturn(false);
        expect(mockEnum.nextElement()).andReturn(MIME_MAP.get(XML));
        expect(req.getHeaders(AcceptServletFilter.ACCEPT_KEY)).andReturn(
                mockEnum);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        @SuppressWarnings("unchecked")
        final Enumeration<String> headers = wrappedReq
                .getHeaders(AcceptServletFilter.ACCEPT_KEY);
        assertThat(headers.nextElement(), is(MIME_MAP.get(XML)));
        assertFalse(headers.hasMoreElements());
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testAcceptHeaderUnsupportedExtNoMime() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_UNSUPP_EXT);
        final ServletResponse resp = createMock(ServletResponse.class);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(false);
        expect(req.getHeaders(AcceptServletFilter.ACCEPT_KEY)).andReturn(
                mockEnum);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        assertNull(wrappedReq.getHeader(AcceptServletFilter.ACCEPT_KEY));
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testOtherHeadersSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        @SuppressWarnings("unchecked")
        final Enumeration<String> mockEnum = createMock(Enumeration.class);
        expect(mockEnum.hasMoreElements()).andReturn(false);
        expect(mockEnum.nextElement()).andReturn(OTHER_VAL);
        expect(req.getHeaders(OTHER_HEADER)).andReturn(mockEnum);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain, mockEnum);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        final HttpServletRequest wrappedReq = capture.getValue();
        @SuppressWarnings("unchecked")
        final Enumeration<String> headers = wrappedReq.getHeaders(OTHER_HEADER);
        assertThat(headers.nextElement(), is(OTHER_VAL));
        assertFalse(headers.hasMoreElements());
        verify(filterConfig, req, resp, chain, mockEnum);
    }

    /**
     * Test.
     * 
     * @throws Exception
     *             If there is a problem.
     */
    @Test
    public void testOtherHeaderSupportedExt() throws Exception {
        final AcceptServletFilter acceptServletFilter = new AcceptServletFilter();
        final FilterConfig filterConfig = createMock(FilterConfig.class);
        final TestFilterConfig testFilterConfig = new TestFilterConfig();
        expect(filterConfig.getInitParameterNames()).andDelegateTo(
                testFilterConfig);
        expect(filterConfig.getInitParameter(anyObject(String.class)))
                .andDelegateTo(testFilterConfig).times(MIME_MAP.size());
        final HttpServletRequest req = createMock(HttpServletRequest.class);
        expect(req.getRequestURI()).andReturn(URI_WITH_SUPP_EXT_ATOM);
        expect(req.getHeader(OTHER_HEADER)).andReturn(OTHER_VAL);
        final ServletResponse resp = createMock(ServletResponse.class);
        final FilterChain chain = createMock(FilterChain.class);
        final Capture<HttpServletRequest> capture = new Capture<HttpServletRequest>();
        chain.doFilter(capture(capture), eq(resp));
        replay(filterConfig, req, resp, chain);
        acceptServletFilter.init(filterConfig);
        acceptServletFilter.doFilter(req, resp, chain);
        acceptServletFilter.destroy();
        final HttpServletRequest wrappedReq = capture.getValue();
        assertThat(wrappedReq.getHeader(OTHER_HEADER), is(OTHER_VAL));
        verify(filterConfig, req, resp, chain);
    }

    /**
     * Test class for {@link FilterConfig} calls. EasyMock delegates to an
     * instance of this class for fetching matched MIME types.
     * 
     * @author cmayes
     */
    private final class TestFilterConfig implements FilterConfig {

        @Override
        public String getFilterName() {
            return null;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public String getInitParameter(final String theName) {
            return MIME_MAP.get(theName);
        }

        @Override
        public Enumeration<String> getInitParameterNames() {
            return java.util.Collections.enumeration(MIME_MAP.keySet());
        }
    }

    static {
        MIME_MAP.put(ATOM, "application/atom+xml");
        MIME_MAP.put(XML, "application/xml");
        MIME_MAP.put("rss", "application/rss+xml");
        MIME_MAP.put("html", "text/html");
        MIME_MAP.put("json", "application/json");
    }
}
