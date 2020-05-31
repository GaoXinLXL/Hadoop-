package com.gx.pagerank;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;

public class PageRankDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String pathIn = "F:\\hadooptemp\\input3";
        String pathOut = "F:\\hadooptemp\\output3";
        for (int i = 0; i < 3; i++) {
            Job job = Job.getInstance(conf);
            job.setJarByClass(PageRankDriver.class);
            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.setInputPaths(job,new Path(pathIn));
            FileOutputFormat.setOutputPath(job,new Path(pathOut));
            pathIn = pathOut;
            pathOut = pathOut + i;
            boolean result = job.waitForCompletion(true);
        }
    }
}
