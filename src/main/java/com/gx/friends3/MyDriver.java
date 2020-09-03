package com.gx.friends3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

/**
 * @description:
 * @author: 高鑫_192235031
 * @time: 2020/9/1 21:55
 */
public class MyDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //通过Job来封装本次MR信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //指定驱动类、Mapper类、Reducer类
        job.setJarByClass(MyDriver.class);
        job.setMapperClass(MyMap.class);
        job.setReducerClass(MyReduce.class);

        //指定Mapper类的输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        //指定Reducer类的输出KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置输入、输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //提交Job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
