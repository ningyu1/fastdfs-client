/*
 * Copyright (c) 2017, Tsoft and/or its affiliates. All rights reserved.
 * FileName: Main.java
 * Author:   ningyu
 * Date:     2017年5月16日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package io.github.ningyu.test.fastdfs;

import io.github.ningyu.fastdfs.client.DefaultStorageClient;
import io.github.ningyu.fastdfs.model.StorePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年5月16日 下午7:42:38
 */
/**
 * @author ningyu
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        DefaultStorageClient client = context.getBean(DefaultStorageClient.class);
        String path = ClassLoader.getSystemResource("123456.txt").getPath();
        File file = new File(path);
        FileInputStream fileInputStream = FileUtils.openInputStream(file);
//        StorePath storePath = client.uploadFile("group1", fileInputStream, file.length(), "txt");
//        client.appendFile("group1", "M00/02/92/wKgAMFkmO3OEGptHAAAAAADK-eg377.txt", fileInputStream, file.length());
//        client.appendFile("group1/M00/02/92/wKgAMFklSKaEHo0gAAAAAPBgSIY987.txt", fileInputStream, file.length());
//        StorePath storePath = client.uploadAppenderFile("group1", fileInputStream, file.length(), "txt");
        client.modifyFile("group1", "M00/02/92/wKgAMFkmO3OEGptHAAAAAADK-eg377.txt", fileInputStream, file.length(), 0);
//        client.modifyFile("group1/M00/02/92/wKgAMFkmO3OEGptHAAAAAADK-eg377.txt", fileInputStream, file.length(), 12);
//        client.truncateFile("group1/M00/02/92/wKgAMFkmO3OEGptHAAAAAADK-eg377.txt");
//        client.truncateFile("group1/M00/02/92/wKgAMFklSKaEHo0gAAAAAPBgSIY987.txt", 0);
        fileInputStream.close();
//        System.out.println(storePath.getFullPath());
//        System.out.println(storePath.getGroup());
//        System.out.println(storePath.getPath());
    }
}


