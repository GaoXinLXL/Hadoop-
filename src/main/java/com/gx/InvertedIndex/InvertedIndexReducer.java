package com.gx.InvertedIndex;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class InvertedIndexReducer extends Reducer<Text,Text,Text,Text> {
    private static Text valueInfo = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //生成文档列表
        String fileList = new String();
        for(Text value : values){
            fileList += value.toString()+";";//注意这是分号，记录了单词所属的那些文档
        }
        valueInfo.set(fileList);
        context.write(key,valueInfo);
    }
}

