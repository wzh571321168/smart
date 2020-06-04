package com.ruoyi.client.handler;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.vo.MonitorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class StreamPushHandler extends BaseMsgHandler{

    private static String FFMPEG_PATH="H:\\ffmpeg-20190101-1dcb5b7-win64-static\\bin\\ffmpeg.exe";
    volatile ConcurrentHashMap<String, Process> pushMap=new ConcurrentHashMap<>();
    volatile ConcurrentHashMap<String, Integer> pushCountMap=new ConcurrentHashMap<>();

    @PreDestroy
    public void destroy(){
        if(pushMap.size()!=0){
            for( Map.Entry<String,Process> entry:pushMap.entrySet()){
                try {
                    entry.getValue().destroy();
                }catch (Exception e){
                    log.error("进程销毁失败：destroy fail!");
                }
            }
        }
    }
    @Override
    public void msgHandler(String msg) {
        MonitorVo monitorVo = JSONObject.parseObject(msg, MonitorVo.class);
        push(monitorVo);
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();
        String s = map.putIfAbsent("1", "1");
        String s1 = map.putIfAbsent("1", "2");
        System.out.println(11);
       /* PushWorkFactory pushWorkFactory=new PushWorkFactory();
        MonitorVo monitorVo=new MonitorVo();
        monitorVo.setAddress("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
        monitorVo.setRtmpServer("rtmp://127.0.0.1:1935/stream/test");
        monitorVo.setUid("52011763-4cff-4839-849e-e26782b92f5a");
        pushWorkFactory.push(monitorVo,true);*/


        /*Runtime runtime = Runtime.getRuntime();
        String s="cmd /C ffmpeg -re -i rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov -vcodec libx264 -preset:v ultrafast -tune:v zerolatency -acodec copy -f flv rtmp://127.0.0.1:1935/stream/test";
        try {
            Process exec = runtime.exec(s);
            Thread.sleep(50000);
            exec.destroy();
            System.out.printf("exit");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    public synchronized void push(MonitorVo monitorVo){
        if(monitorVo.getFlag()) {
            if (pushCountMap.get(monitorVo.getUid()) != null&&pushCountMap.get(monitorVo.getUid()) >0) {
                Integer count = pushCountMap.get(monitorVo.getUid());
                count++;
                pushCountMap.put(monitorVo.getUid(), count);
                return;
            }
            pushCountMap.put(monitorVo.getUid(), 1);
            executeCommand(builder(monitorVo.getUid(), monitorVo.getRtmpServer()), monitorVo.getUid());
        }else {
            if (pushCountMap.get(monitorVo.getUid())!=null&&pushCountMap.get(monitorVo.getUid())<2){
                Integer count = pushCountMap.get(monitorVo.getUid());
                count--;
                pushCountMap.put(monitorVo.getUid(), count);
                Process process = pushMap.get(monitorVo.getUid());
                new ProcessKiller(process).start();
            }
        }
    }

   /* public synchronized void pushDone(MonitorVo monitorVo) {
        Integer integer = pushCountMap.get(monitorVo.getAddress());
        if(integer>1){
            integer--;
            pushCountMap.put(monitorVo.getAddress(),integer);
        }else {
            pushCountMap.put(monitorVo.getAddress(),0);
            pushMap.put(monitorVo.getAddress(), false);
        }
    }*/


    /**
     * 初始化时利用反射获取jave-1.0.1.jar中FFmpeg.exe的路径
     * 利用jave-1.0.1.jar来避免本地安装FFmpeg.exe
     */
   /* static {
        DefaultFFMPEGLocator locator = new DefaultFFMPEGLocator();
        try {
            Method method = locator.getClass().getDeclaredMethod("getFFMPEGExecutablePath");
            method.setAccessible(true);
            FFMPEG_PATH = (String) method.invoke(locator);
            method.setAccessible(false);
            log.info("--- 获取FFmpeg可执行路径成功 --- 路径信息为：" + FFMPEG_PATH);
        } catch (Exception e) {
            log.error("--- 获取FFmpeg可执行路径失败！ --- 错误信息： " + e.getMessage());
        }

    }*/
    /**
     * 获取FFmpeg程序的路径（windows和linux环境下路径不一样）
     *
     * @return
     */
    public String getFFmpegPath() {
        return FFMPEG_PATH;
    }

    /**
     *
     *
     * @param commonds
     */

    /**
     * 执行FFmpeg命令
     * @param commonds FFmpeg命令
     * @return FFmpeg执行命令过程中产生的各信息，执行出错时返回null
     */
    public void executeCommand(List<String> commonds, String uid) {
        if (CollectionUtils.isEmpty(commonds)) {
            log.error("--- 指令执行失败，因为要执行的FFmpeg指令为空！ ---");
        }
        if(pushMap.get(uid)!=null){
            return;
        }
        LinkedList<String> ffmpegCmds = new LinkedList<>(commonds);
        // ffmpegCmds.addFirst("ffmpeg"); // 设置ffmpeg程序所在路径

        Runtime runtime = Runtime.getRuntime();
        Process ffmpeg = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(ffmpegCmds);
            builder.redirectErrorStream(true);
            System.out.println(Arrays.toString(ffmpegCmds.toArray()).replace(",", ""));
            Process process = pushMap.putIfAbsent(uid, ffmpeg);
            if(process!=null){
                ffmpeg.destroy();
                return;
            }
            ffmpeg = builder.start();
            new Thread(new PushProcessExcuteInfo(ffmpeg)).start();
            //String result = waitForExcute(ffmpeg);
            // 输出执行的命令信息
//            String cmdStr = Arrays.toString(ffmpegCmds.toArray()).replace(",", "");
//
//            String resultStr = result != null ? "正常" : "【异常】";
//            log.info("--- FFmepg命令： ---" + cmdStr + " 已执行完毕,执行结果：" + resultStr);
            //return result;

        } catch (Exception e) {
            log.error("--- FFmpeg命令执行出错！ --- 出错信息： " + e.getMessage());
            return;
        } finally {
            if (null != ffmpeg) {
                ProcessKiller ffmpegKiller = new ProcessKiller(ffmpeg);
                // JVM退出时，先通过钩子关闭FFmepg进程
                runtime.addShutdownHook(ffmpegKiller);
            }
        }
    }

    //ffmpeg -re -i “rtsp://admin:123456@192.168.2.165:554/cam/realmonitor?channel=1&subtype=0&unicast=true&proto=Onvif” -vcodec libx264 -preset:v ultrafast -tune:v zerolatency -acodec copy -f flv “rtmp://localhost:1935/hls/mystream”
    public List<String> builder(String input, String output)  {
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add("ffmpeg"); // 添加转换工具路径
        convert.add("-re");
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(input); // 添加要转换格式的视频文件的路径
        convert.add("-vcodec");
        convert.add("libx264");
        convert.add("-preset:v");
        convert.add("ultrafast");
        convert.add("-tune:v");
        convert.add("zerolatency");
        convert.add("-acodec");
        convert.add("copy");
        convert.add("-f");
        convert.add("flv");
        convert.add(output);
        return convert;
    }

    /**
     * FFmpeg进程执行输出，必须使用此函数，否则会出现进程阻塞现象
     * 当FFmpeg进程执行完所有命令后，本函数返回FFmpeg进程退出时的状态值；
     * @return 进程执行命令过程中产生的各种信息，执行命令过程出错时返回null
     */
    public class PushProcessExcuteInfo implements Runnable {
        private Process process;

        public PushProcessExcuteInfo(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            InputStream errorStream = null;
            BufferedReader br1 = null;
            BufferedReader br2 = null;
            StringBuffer returnStr = new StringBuffer(); // 存储FFmpeg执行命令过程中产生的信息
            int exitValue = -1;
            try {
                inputStream = process.getInputStream();
                errorStream = process.getErrorStream();
                br1 = new BufferedReader(new InputStreamReader(inputStream));
                br2 = new BufferedReader(new InputStreamReader(errorStream));
                boolean finished = false;
                while (!finished) {
                    try { // while内部使用一个try-catch块，这样当某一次循环读取抛出异常时，可以结束当次读取，返回条件处开始下一次读取
                        String line1 = null;
                        String line2 = null;
                        while ((line1 = br1.readLine()) != null) {
                            log.info(line1);
                        }
                        while ((line2 = br2.readLine()) != null) {
                            log.info(line2);
                            returnStr.append(line2 + "\n");
                        }
                        exitValue = process.exitValue();
                        finished = true;
                    } catch (IllegalThreadStateException e) { // 防止线程的start方法被重复调用
                        log.error("--- 本次读取标准输出流或错误流信息出错 --- 错误信息： " + e.getMessage());
                        Thread.sleep(500);
                    } catch (Exception e2) {
                        log.error("--- 本次读取标准输出流或错误流信息出错 --- 错误信息： " + e2.getMessage());
                    }
                }
            } catch (Exception e) {
                log.error("--- 执行FFmpeg程序时读取标准输出或错误流的信息出错 ---");
            } finally {
                try {
                    if (null != br1) {
                        br1.close();
                    }
                    if (null != br2) {
                        br2.close();
                    }
                    if (null != inputStream) {
                        inputStream.close();
                    }
                    if (null != errorStream) {
                        errorStream.close();
                    }
                } catch (IOException e) {
                    log.error("--- 关闭读取的标准输出流或错误流时出错 ---");
                }

            }

        }
    }

    /**
     * 在程序退出前结束已有的FFmpeg进程
     */
    private class ProcessKiller extends Thread {
        private Process process;

        public ProcessKiller(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            this.process.destroy();
            log.info("--- 已销毁进程 --- 进程名： " + process.toString());
        }
    }
}
