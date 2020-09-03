package com.gx.friends;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 * @description: 找出每一位用户的好友，输出好友列表，每一行代表一个用户和他的好友列表。
 * @author: 高鑫_192235031
 * @time: 2020/9/1 11:05
 */
public class FriendsMapper extends Mapper<LongWritable, Text,Text, Text>{

    //在map方法外创建对象，一次创建多次使用，节约时间、空间，避免产生垃圾对象
    private Text name1 = new Text();
    private Text name2 = new Text();

    /**
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        //获取数据
        String line = value.toString();
        //分割
        String[] words = line.split(",");
        //赋值
        name1.set(words[0]);
        name2.set(words[1]);
        //设为map输出
        context.write(name1, name2);
        context.write(name2, name1);
    }
}
