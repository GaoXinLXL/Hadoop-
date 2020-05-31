package com.gx.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class PageRankReducer extends Reducer<Text, Text,Text, Text> {
    private Text keyInfo = new Text();
    private Text valueInfo = new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        float pr = 0;
        String link = "";
        for(Text value : values){
            if('$'==value.toString().charAt(0)){//以$开头
                pr += Float.parseFloat(value.toString().substring(1));
            }else {//以@开头
                link += value.toString().substring(1);
            }
        }
        pr = 0.8f * pr + 0.2f * 0.25f;//加入跳转因子，进行平滑处理。最后的0.25f其实是网页总数分之一
        keyInfo.set(key);
        valueInfo.set(pr+link);
        context.write(keyInfo,valueInfo);
    }
}
