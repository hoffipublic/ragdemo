package com.hoffi.ai.ragdemo.rest;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
    public String base() {
      return "The Base Controller works!";
    }
}
