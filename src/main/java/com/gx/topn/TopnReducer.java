package com.gx.topn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;

public class TopnReducer extends Reducer<NullWritable, IntWritable,NullWritable,IntWritable> {
    private TreeMap<Integer,String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
        public int compare(Integer a, Integer b) {
            return b - a;
        }
    });
    @Override
    protected void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        for (IntWritable value : values){
            map.put(value.get()," ");
            if (map.size()>5){
                map.remove(map.firstKey());
            }
        }
        for (Integer i : map.keySet()){
            context.write(NullWritable.get(),new IntWritable(i));
        }
    }
}
