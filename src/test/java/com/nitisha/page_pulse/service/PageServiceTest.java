package com.nitisha.page_pulse.service;

import com.nitisha.page_pulse.dto.PageResponse;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PageServiceTest {

    private PageService pageService;

    @BeforeEach
    void setUp() {
        pageService = new PageService();
    }

    @Test
    void parseDocument_shouldMapAllFieldsCorrectly() throws Exception {
        Document document = mock(Document.class);
        Connection.Response response = mock(Connection.Response.class);

        when(response.statusCode()).thenReturn(200);
        when(response.url()).thenReturn(new URL("https://example.com/page"));

        when(document.title()).thenReturn("Example Title");

        Element metaDescription = mock(Element.class);
        when(metaDescription.attr("content")).thenReturn("Example description");
        when(document.selectFirst("meta[name=description]")).thenReturn(metaDescription);

        Elements h1Elements = mock(Elements.class);
        when(h1Elements.size()).thenReturn(2);
        when(document.select("h1")).thenReturn(h1Elements);

        Elements imgElements = mock(Elements.class);
        when(imgElements.size()).thenReturn(5);
        when(document.select("img")).thenReturn(imgElements);

        Elements missingAltElements = mock(Elements.class);
        when(missingAltElements.size()).thenReturn(1);
        when(document.select("img:not([alt])")).thenReturn(missingAltElements);

        Element body = mock(Element.class);
        when(body.text()).thenReturn("This is some sample body text for word count");
        when(document.body()).thenReturn(body);

        Elements links = mock(Elements.class);
        when(links.iterator()).thenReturn(java.util.Collections.<Element>emptyList().iterator());
        when(document.select("a[href]")).thenReturn(links);

        PageResponse result = pageService.parseDocument(document, response, 250L);

        assertEquals(200, result.getHttpStatus());
        assertEquals(250L, result.getResponseTime());
        assertEquals("Example Title", result.getTitle());
        assertEquals("Example description", result.getDescription());
        assertEquals(2, result.getH1Count());
        assertEquals(5, result.getImageCount());
        assertEquals(1, result.getMissingAltCount());
        assertEquals(9, result.getWordCount());
        assertEquals(0, result.getInternalLinks());
    }

    @Test
    void parseDocument_shouldReturnNotFound_whenMetaDescriptionMissing() throws Exception {
        Document document = mock(Document.class);
        Connection.Response response = mock(Connection.Response.class);

        when(response.statusCode()).thenReturn(200);
        when(response.url()).thenReturn(new URL("https://example.com"));
        when(document.title()).thenReturn("No Meta Page");
        when(document.selectFirst("meta[name=description]")).thenReturn(null);

        Elements empty = mock(Elements.class);
        when(empty.size()).thenReturn(0);
        when(document.select("h1")).thenReturn(empty);
        when(document.select("img")).thenReturn(empty);
        when(document.select("img:not([alt])")).thenReturn(empty);

        Element body = mock(Element.class);
        when(body.text()).thenReturn("");
        when(document.body()).thenReturn(body);

        Elements links = mock(Elements.class);
        when(links.iterator()).thenReturn(java.util.Collections.<Element>emptyList().iterator());
        when(document.select("a[href]")).thenReturn(links);

        PageResponse result = pageService.parseDocument(document, response, 100L);

        assertEquals("Not Found", result.getDescription());
        assertEquals(0, result.getWordCount());
    }

    @Test
    void analyze_shouldReturnErrorResponse_whenUrlIsInvalid() {
        // Failure case 1: malformed URL syntax should trigger the catch block in analyze()
        PageResponse result = pageService.analyze("not-a-valid-url");

        assertEquals(0, result.getHttpStatus());
        assertEquals(0, result.getResponseTime());
        assertEquals("Error", result.getTitle());
        assertNotNull(result.getDescription());
        assertEquals(0, result.getH1Count());
        assertEquals(0, result.getImageCount());
        assertEquals(0, result.getMissingAltCount());
        assertEquals(0, result.getInternalLinks());
        assertEquals(0, result.getWordCount());
    }

    @Test
    void analyze_shouldReturnErrorResponse_whenDomainIsUnreachable() {
        // Failure case 2: URL is syntactically valid, but the domain does not
        // exist, so Jsoup throws an UnknownHostException when trying to connect.
        // The service should catch this gracefully instead of crashing.
        PageResponse result = pageService.analyze("https://this-domain-does-not-exist-asdkjaskdj123.com");

        assertEquals(0, result.getHttpStatus());
        assertEquals(0, result.getResponseTime());
        assertEquals("Error", result.getTitle());
        assertNotNull(result.getDescription());
        assertEquals(0, result.getH1Count());
        assertEquals(0, result.getImageCount());
        assertEquals(0, result.getMissingAltCount());
        assertEquals(0, result.getInternalLinks());
        assertEquals(0, result.getWordCount());
    }
}