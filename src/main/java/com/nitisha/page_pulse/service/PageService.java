package com.nitisha.page_pulse.service;

import com.nitisha.page_pulse.dto.PageResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    public PageResponse analyze(String url) {

        try {

            long start = System.currentTimeMillis();

            Connection.Response response = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .execute();

            Document document = response.parse();

            long end = System.currentTimeMillis();

            return parseDocument(document, response, end - start);

        } catch (Exception e) {

            PageResponse page = new PageResponse();
            page.setHttpStatus(0);
            page.setResponseTime(0);
            page.setTitle("Error");
            page.setDescription(e.getMessage());
            page.setH1Count(0);
            page.setImageCount(0);
            page.setMissingAltCount(0);
            page.setInternalLinks(0);
            page.setWordCount(0);

            return page;
        }
    }

    protected PageResponse parseDocument(Document document, Connection.Response response, long responseTime) {

        PageResponse page = new PageResponse();

        page.setHttpStatus(response.statusCode());
        page.setResponseTime(responseTime);
        page.setTitle(document.title());

        Element meta = document.selectFirst("meta[name=description]");
        page.setDescription(meta != null ? meta.attr("content") : "Not Found");

        page.setH1Count(document.select("h1").size());
        page.setImageCount(document.select("img").size());
        page.setMissingAltCount(document.select("img:not([alt])").size());

        String text = document.body().text();
        int words = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
        page.setWordCount(words);

        String domain = response.url().getHost();
        int internalLinks = 0;

        for (Element link : document.select("a[href]")) {
            String absUrl = link.absUrl("href");

            if (!absUrl.isEmpty() && absUrl.contains(domain)) {
                internalLinks++;
            }
        }

        page.setInternalLinks(internalLinks);

        return page;
    }
}