package com.gx.friends3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 高鑫_192235031
 * @time: 2020/9/1 20:56
 */
public class MyMap extends Mapper<LongWritable, Text, Text, NullWritable> {
    //注意，这里建的集合是全局性的
    private Map<String, List<String>> map = new HashMap<>();
    /**
     * setup()阶段，将问题1的输出文件读取到一个全局HashMap中
     * key：一个字母 ; value: 字母的list集合
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //这里就是预先读取问题1的解
        FileInputStream fis = new FileInputStream("F:\\data\\hadoopoutput\\part-r-00000");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        //按照":"分割开来，前部分就是用户名，后面就是他的朋友
        //将后部分装进一个列表
        while ((line = br.readLine()) != null) {
            //数据的格式处理
            String[] strings = line.split(":");
            String key = strings[0].trim();
            String[] chars = strings[1].split(",");
            List<String> list = new ArrayList<>();
            for (String str : chars){
                list.add(str);
            }
            //最终要放进全局性的map集合
            map.put(key, list);
        }
        br.close();
        fis.close();
    }
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //这里map输入的是问题2的解 如   a-b:	e,k
        //按照":"分割
        String[] strings = value.toString().split(":");
        String[] users = strings[0].split("-");//得到 a b
        //这里需要用到全局HashMap,即我们在Setup阶段添加到map中的数据
        List<String> list = map.get(users[0]);//根据a找到对应的朋友列表
        /**
         * 如果list集合中没有包含users[1]，即：user[0]与users[1]不是好友
         * 将a-b输出
         */
        if (!list.contains(users[1])){
            context.write(new Text(strings[0]), NullWritable.get());
        }
    }
}

