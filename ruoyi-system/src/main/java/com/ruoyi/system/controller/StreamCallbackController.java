package com.ruoyi.system.controller;

import com.ruoyi.system.service.ICallbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 视频流回调
 */
@RestController
@RequestMapping("callback")
public class StreamCallbackController {
    @Autowired
    private ICallbackService callbackService;
    @RequestMapping("onConnect")
    public String on_connect(HttpServletRequest request){
        System.out.printf("onConnect");
        return "onConnect";
    }
    @GetMapping("onPublish")
    public String onPublish(HttpServletRequest request){
        System.out.printf("onPublish");

        return "onPublish";
    }
    @GetMapping("onPublishDone")
    public String onPublishDone(@RequestParam("uid") String uid){
        System.out.printf("onPublishDone");
        return "onPublishDone";
    }

    /**
     * 播放回调
     * @param uid
     * @return
     */
    @GetMapping("onPlay")
    public String onPlay(@RequestParam("uid") String uid){

        System.out.printf("onPlay");
        if(StringUtils.isNoneBlank(uid)){
            callbackService.push(uid,true);
            //EigenFaceRecognizer eigenFaceRecognizer = EigenFaceRecognizer.create();

        }
        return "onPlay";
    }

    /**
     * 播放关闭回调
     * @param uid
     */
    @GetMapping("onPlayDone")
    public String onPlayDone(@RequestParam("uid") String uid){
        System.out.printf("onPlayDone");
        if(StringUtils.isNoneBlank(uid)){
            callbackService.push(uid,false);
        }
        return "onPlayDone";
    }
}
