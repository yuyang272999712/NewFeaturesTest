package com.example.MultipartThreadDownloador;

import java.io.IOException;

/**
 * 多线程下载
 */
public class DownloadSimple {
    public static void main(String[] args) throws IOException {
        String url = "http://42.81.15.27:8666/file/05c23d09d36c955bc2eb0a60ea504814?sdk_id=258&task_id=2193740583487471979&business_id=4097&bkt=p2-nb-873&xcode=0cccc406f54340a3903c01b3c4d9a5e8168befccf0515faa316128a2cdfcce4d&fid=1913468660-250528-759837029180950&time=1470281527&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-TViyHuqTd4n5IaGhID3pIrN1euQ%3D&to=sd&fm=Nin,B,T,t&sta_dx=172&sta_cs=1101&sta_ft=pdf&sta_ct=7&fm2=Ningbo,B,T,t&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=140005c23d09d36c955bc2eb0a60ea5048145055744e00000abb90f2&sl=78250062&expires=8h&rt=sh&r=998355336&mlogid=5020797212220286724&vuk=2636345392&vbdid=2489395223&fin=Mac%E6%93%8D%E4%BD%9C%E8%AF%B4%E6%98%8E%20%20Mavericks%2010.9%E2%80%94%E5%BC%A0%E5%AE%81%E5%8D%9A%E3%80%90%E9%AB%98%E6%B8%85%E3%80%91.pdf&fn=Mac%E6%93%8D%E4%BD%9C%E8%AF%B4%E6%98%8E%20%20Mavericks%2010.9%E2%80%94%E5%BC%A0%E5%AE%81%E5%8D%9A%E3%80%90%E9%AB%98%E6%B8%85%E3%80%91.pdf&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=5020797212220286724&dp-callid=0.1.1&sdk_id=258";
        //TODO 这是对应的Mac OS路径
        String dir = "/Users/yuyang/Desktop";
        String fileName = "Mac操作手册.pdf";
        int threadCount = 4;

        MultipartThreadDownloador downloador = new MultipartThreadDownloador(url, dir, fileName, threadCount);
        downloador.download();
    }
}
