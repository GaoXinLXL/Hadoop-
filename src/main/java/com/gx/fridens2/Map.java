package com.gx.fridens2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.Arrays;

/**
 * @description:
 * @author: 高鑫_192235031
 * @time: 2020/9/1 16:18
 */
public class Map extends Mapper<LongWritable, Text, Text, Text> {


    /**
     * 输入格式:a :b,c,e,j,k
     *          b :a,e,k
     *          c :a,d,j
     *          d :c
     *          ...
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //按照":"将数据分为两类：用户和他的朋友们
        String[] strings = value.toString().split(":");
        //获取该用户名，注意去掉空格符
        String user = strings[0].trim();
        //获取该用户的朋友字符串数组
        String[] chars = strings[1].split(",");
        //为了让输出数据看起来好看，最好进行排序，这里按照字典序排序
        Arrays.sort(chars);
        //这里是此题关键，对问题1的解进行反向思考
        //他的朋友们的公共好友就是他本人，所以要找两人公共好友，两两遍历即可
        for (int i = 0; i < chars.length; i++){
            for (int j = i + 1; j < chars.length; j++){
                String a_b = chars[i] + "-" + chars[j] + ":";//b-c:
                context.write(new Text(a_b), new Text(user));//b-c:a
            }
        }
    }
}

