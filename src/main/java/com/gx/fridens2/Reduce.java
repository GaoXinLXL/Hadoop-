package com.gx.fridens2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description: 找两两共同好友
 * @author: 高鑫_192235031
 * @time: 2020/9/1 16:34
 */
public class Reduce extends Reducer<Text, Text, Text, Text> {
    //这里设为静态变量，原因是我用到了lambda表达式
    //在lambda里修改的变量，不能是局部变量
    //当然，如果不用lambda表达式，则可以不必在此处设为静态
    static String res = "";
    static Set<String> set = new TreeSet<>();

    /**
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //下面使用了JDK1.8的特性lambda表达式，大大简化了程序开发
        //但要注意表达式里的变量不能是局部变量，原因在于lambda表达式简化自匿名内部类
        values.forEach(value -> set.add(value.toString()));
        set.forEach(name -> res += name + ",");
        //注意去掉最后多余的逗号
        context.write(key,new Text(res.substring(0,res.length()-1)));
        //因为是静态变量，生命周期大于此方法周期（跟类同行，对此方法来说近似于永久存在）
        //所以必须在方法末尾重新设置为初值
        //局部变量随着方法结束而消亡，新方法开始又创建，所以不需要显示设初值
        res = "";
        set.clear();
    }
}


