package com.gx.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.TreeMap;

public class TopnMapper extends Mapper<LongWritable, Text, NullWritable, IntWritable> {
    private TreeMap<Integer,String> map = new TreeMap<Integer, String>();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] nums = line.split(" ");
        for (String num : nums){
            map.put(Integer.parseInt(num)," ");
            if(map.size()>5){
                map.remove(map.firstKey());
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Integer i : map.keySet()){
            try {
                context.write(NullWritable.get(),new IntWritable(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
