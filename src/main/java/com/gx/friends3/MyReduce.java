package com.gx.friends3;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

/**
 * @description:
 * @author: 高鑫_192235031
 * @time: 2020/9/1 21:52
 */
public class MyReduce extends Reducer<Text, NullWritable, Text, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //此问题的reduce保持map阶段的输出就行了
        context.write(key, NullWritable.get());
    }
}

