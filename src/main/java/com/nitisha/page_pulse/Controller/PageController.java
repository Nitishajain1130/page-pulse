package com.nitisha.page_pulse.controller;

import com.nitisha.page_pulse.dto.PageRequest;
import com.nitisha.page_pulse.dto.PageResponse;
import com.nitisha.page_pulse.service.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PageController {

    @Autowired
    private PageService pageService;

    @PostMapping("/analyze")
    public PageResponse analyze(@Valid @RequestBody PageRequest request) throws Exception {

        return pageService.analyze(request.getUrl());

    }
}