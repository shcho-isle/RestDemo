package com.example.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AttractionRestController.REST_URL)
public class AttractionRestController {
    static final String REST_URL = "/rest/attractions";
}
