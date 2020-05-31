package com.gx.InvertedIndex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class InvertedIndexCombiner extends Reducer<Text, Text,Text,Text> {
    private Text keyInfo = new Text();
    private Text valueInfo = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum = 0;//词频统计
        for(Text value : values){
            sum += Integer.parseInt(value.toString());
        }
        int splitIndex = key.toString().indexOf(":");
        //重新设置key为单词
        keyInfo.set(key.toString().substring(0,splitIndex));
        //重新设置value为文档名加词频
        valueInfo.set(key.toString().substring(splitIndex+1)+":"+sum);
        context.write(keyInfo,valueInfo);
    }
}


