package com.gx.friends;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

/**
 * @description: driver类基本就是套路
 * @author: 高鑫_192235031
 * @time: 2020/9/1 12:49
 */
public class FriendsDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //通过Job来封装本次MR信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定驱动类、Mapper类、Reducer类
        job.setJarByClass(FriendsDriver.class);
        job.setMapperClass(FriendsMapper.class);
        job.setReducerClass(FriendsReducer.class);

        //指定Mapper类的输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //指定Reducer类的输出KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置输入、输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //提交Job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
