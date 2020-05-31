package com.gx.quchong;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class MyMapper extends Mapper<LongWritable, Text,Text,NullWritable> {
    private static Text keyInfo = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        keyInfo = value;
        context.write(keyInfo, NullWritable.get());
    }
}
