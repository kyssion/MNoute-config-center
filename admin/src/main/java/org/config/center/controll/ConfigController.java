package org.config.center.controll;

import org.config.center.bean.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @RequestMapping("config/create")
    public Response configCreate(){
        return new Response();
    }
}
