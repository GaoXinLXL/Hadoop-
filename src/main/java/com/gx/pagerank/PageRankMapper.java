package com.gx.pagerank;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.StringTokenizer;

public class PageRankMapper extends Mapper<LongWritable, Text,Text,Text> {
    Text keyInfo = new Text();
    Text valueInfo = new Text();
    private String id;//记录网页名
    private float pr;//网页PR值
    private int count;//当前网页拥有的链接数
    private float avg_pr;//当前网页分出去的平均PR值

    @Override//输入<A,0.25 B C D>
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer itr = new StringTokenizer(value.toString());
        id = itr.nextToken();
        pr = Float.parseFloat(itr.nextToken());
        count = itr.countTokens();
        avg_pr = pr/count;
        String linkIds = "@";
        while (itr.hasMoreTokens()){
            String linkId = itr.nextToken();
            keyInfo.set(linkId);
            valueInfo.set("$"+avg_pr);
            linkIds += " " + linkId;
            context.write(keyInfo,valueInfo);//第一种输出类型 <B,$0.0833>、<C,$0.0833>、<D,$0.0833>
        }
        keyInfo.set(id);
        valueInfo.set(linkIds);
        context.write(keyInfo,valueInfo);//第二种输出类型 <A,@ B C D>
    }
}
