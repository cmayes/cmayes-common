package com.cmayes.common.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.cmayes.common.MediaType;

/**
 * Tests for {@link MediaType}.
 * 
 * @author cmayes
 */
public class TestMediaType {
    /**
     * Tests primary extension's presence in all extensions list.
     */
    @Test
    public void testTextPrimaryExtension() {
        final List<String> allExtensions = MediaType.TEXT.getAllExtensions();
        assertThat(allExtensions.size(), greaterThanOrEqualTo(1));
        assertThat(allExtensions.get(0),
                equalTo(MediaType.TEXT.getPrimaryExtension()));
    }

    /**
     * Tests the MIME type value.
     */
    @Test
    public void testTextMime() {
        assertThat(MediaType.TEXT.getMimeType(), equalTo("text/plain"));
    }

    /**
     * Basic check of the description field.
     */
    @Test
    public void testTextDesc() {
        assertThat(MediaType.TEXT.getDescription(), containsString("text"));
    }

    /**
     * Makes sure that the stock enum valueOf works.
     */
    @Test
    public void testStockValueOf() {
        assertThat(MediaType.valueOf(MediaType.TEXT.name()),
                equalTo(MediaType.TEXT));
    }

}
