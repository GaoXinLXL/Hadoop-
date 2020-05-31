package com.gx.InvertedIndex;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.io.IOException;
import java.util.StringTokenizer;

public class InvertedIndexMapper extends Mapper<LongWritable, Text,Text,Text> {
    //存储单词和文档名称
    private Text keyInfo = new Text();
    //存储词频，初始化为1
    private Text valueInfo = new Text("1");

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //得到这行数据所在的文件分片
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        //根据文件切片得到文件名
        String fileName = fileSplit.getPath().getName();
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()){
            keyInfo.set(itr.nextToken()+":"+fileName);
            context.write(keyInfo,valueInfo);
        }
    }
}


