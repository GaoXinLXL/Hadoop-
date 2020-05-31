package com.gx.hdfsdemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HDFS_CRUD {
    FileSystem fs = null;
    /**
     *初始化客户端对象
    */
    @Before//这个注解是Junit里的，保证init()方法最先执行
    public void init() throws IOException {
        //构造一个配置参数对象，设置一个参数：要访问的HDFS的URL
        Configuration conf = new Configuration();
        //这里指定使用的HDFS
        conf.set("fs.defaultFS","hdfs://hadoop01:9000");
        //设置客户端身份
        System.setProperty("HADOOP_USER_NAME","root");
        //通过FileSystem的静态方法获取文件系统的客户端对象
        fs = FileSystem.get(conf);
    }

    /**
     *上传本地文件到HDFS
    */
    @Test
    public void testAddFileToHdfs() throws IOException {
        //要上传的文件所在的本地路径
        Path src = new Path("F:/test.tst");
        //要上传的HDFS的目标路径
        Path dst = new Path("/testFile");
        //上传文件的方法
        fs.copyFromLocalFile(src,dst);
        //关闭资源
        fs.close();
    }

    /**
     *从HDFS下载文件到本地
    */
    @Test
    public void testDownloadFileToLocal() throws IOException {
        //下载文件
        fs.copyToLocalFile(new Path("/testFile"),new Path("F:/"));
        fs.close();
    }
}
