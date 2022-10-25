package com.hlkj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Lixiangping
 * @createTime 2022年10月25日 11:39
 * @decription:
 */
@RestController
public class Controller {

    @Resource
    private DynamicMessages dynamicMessages;

    @GetMapping("/kaiguan")
    public boolean kaiguan(){
        return dynamicMessages.isDisableRegistration();
    }

}
