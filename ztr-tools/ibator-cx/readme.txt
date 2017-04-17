开发者社区

标题: ibator改造之返回数据库注释和数据库分页 [打印本页]
作者: 树仔    时间: 2011-9-2 14:45:21     标题: ibator改造之返回数据库注释和数据库分页

欢迎 Javaeye 祸鞋归来。
参考文献： 
1、Ibator支持分页的plugin 
2、ibator改进，生成中文注释
插件基于ibator1.2.2（http://svn.apache.org/repos/asf/ibatis/java/ibator） 
个人感觉用ibator Eclipse插件不如直接用这种方式生产代码方便，出错几率小的多，还能log跟踪。
更新：
ibator_cx.rar为最新版的src。具体增加和修改的，参见ConfigIbatisOracle.xml
ibator_2010-12-7.jar版本增加了一个插件，ChangeReturnPlugin，功能：
把所有delete、update、insert返回为int类型的方法改为返回布尔值类型
   大部分都用不到返回的行数，多数情况只是关心是否执行成功
在配置文件中加入如下配置即可：
<!-- 修改dao里面返回值，把增删改的返回值由整型改为布尔型 -->
   <ibatorPlugin type="org.apache.ibatis.ibator.plugins.ChangeReturnPlugin" />
和插件SerializablePlugin一样使用，如果没有配置，就不会修改dao类里面的部分方法的返回值，方法还是默认返回int类型。
ibator_2010-12-7.jar对应的源码过些天在上传。

强烈建议用一下的java方法生成所需的xml、dao、pojo。不建议用ibator的eclipse插件方式。
自己可以新建一个java工程，里面存放这个.java文件和ibator.jar文件。如果要生成代码到其他工程里面去可以在xml里面配置：
targetProject="../你要生成的目的工程名/src">  记得xml里面的3个targetProject都要修改哦。

Java代码  
package ibator;   
  
import java.io.File;   
import java.util.ArrayList;   
import java.util.List;   
  
import org.apache.ibatis.ibator.api.Ibator;   
import org.apache.ibatis.ibator.config.IbatorConfiguration;   
import org.apache.ibatis.ibator.config.xml.IbatorConfigurationParser;   
import org.apache.ibatis.ibator.internal.DefaultShellCallback;   
  
public class IbatorRunTest {   
  
    public static void main(String... strings) {   
        try {   
            List<String> warnings = new ArrayList<String>();   
            boolean overwrite = true;   
            File configFile = new File(ClassLoader.getSystemResource("ConfigIbatisExample.xml").getFile());   
            IbatorConfigurationParser cp = new IbatorConfigurationParser(   
                    warnings);   
            IbatorConfiguration config = cp   
                    .parseIbatorConfiguration(configFile);   
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);   
            Ibator ibator = new Ibator(config, callback, warnings);   
            ibator.generate(null);   
            for (String warning : warnings) {   
                System.out.println("warning:" + warning);   
            }   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
    }   
  
}  

package ibator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.ibator.api.Ibator;
import org.apache.ibatis.ibator.config.IbatorConfiguration;
import org.apache.ibatis.ibator.config.xml.IbatorConfigurationParser;
import org.apache.ibatis.ibator.internal.DefaultShellCallback;

public class IbatorRunTest {

        public static void main(String... strings) {
                try {
                        List<String> warnings = new ArrayList<String>();
                        boolean overwrite = true;
                        File configFile = new File(ClassLoader.getSystemResource("ConfigIbatisExample.xml").getFile());
                        IbatorConfigurationParser cp = new IbatorConfigurationParser(
                                        warnings);
                        IbatorConfiguration config = cp
                                        .parseIbatorConfiguration(configFile);
                        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
                        Ibator ibator = new Ibator(config, callback, warnings);
                        ibator.generate(null);
                        for (String warning : warnings) {
                                System.out.println("warning:" + warning);
                        }
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }

}

修改日志： 
1、增加数据库注释，oracle默认不返回数据库注释，需要设置一个参数
2、当oracle得字段为number没有指定长度时，ibator会设置字段为Big Decimal 
3、去掉不必要的其他注释
4、改进分页的生成方式
5、其他详情见附件源码


配置文件代码  
<?xml version="1.0" encoding="UTF-8"?>   
<!DOCTYPE ibatorConfiguration   
  PUBLIC "-//Apache Software Foundation//DTD Apache iBATIS Ibator Configuration 1.0//EN"  
  "http://ibatis.apache.org/dtd/ibator-config_1_0.dtd">   
  
<ibatorConfiguration>   
    <classPathEntry location="E:/jars/ojdbc/ojdbc14_10.2.0.4.jar" />   
    <ibatorContext id="FlatJava5" targetRuntime="Ibatis2Java5">   
        <property name="suppressTypeWarnings" value="true" />   
        <!-- Serializable化 -->   
        <ibatorPlugin type="org.apache.ibatis.ibator.plugins.SerializablePlugin" />   
        <!-- 这个插件添加方法为例(实际上的内部类)来支持不区分大小写像搜索。这个演示了增加功能,通过一个实例类插件,而不是延长上课。 -->   
        <ibatorPlugin type="org.apache.ibatis.ibator.plugins.CaseInsensitiveLikePlugin" />   
        <!-- 分页 -->   
        <ibatorPlugin type="org.apache.ibatis.ibator.plugins.PaginationPlugin">   
            <property name="enablePagination" value="true" />   
            <!-- mysql or oracle -->   
            <property name="databaseType" value="oracle" />   
        </ibatorPlugin>   
        <!-- 重命名example类 -->   
        <ibatorPlugin   
            type="org.apache.ibatis.ibator.plugins.RenameExampleClassPlugin">   
            <property name="searchString" value="Example$" />   
            <property name="replaceString" value="Criteria" />   
        </ibatorPlugin>   
        <!-- 产生sqlmap.xml   
        <ibatorPlugin type="org.apache.ibatis.ibator.plugins.SqlMapConfigPlugin">   
            <property name="targetPackage" value="ibatortest.generated.flat" />   
            <property name="targetProject" value="src" />   
        </ibatorPlugin>   
        -->   
  
        <!-- driverClass="com.mysql.jdbc.Driver" -->   
        <jdbcConnection driverClass="oracle.jdbc.driver.OracleDriver"  
            connectionURL="jdbcracle:thin127.0.0.1:1521ra10g" userId="test"  
            password="test" >   
            <!-- 是否返回数据库注释，MySQL默认是true，oracle默认是false -->   
            <property name="remarksReporting" value="true"/>   
            </jdbcConnection>   
        <javaModelGenerator targetPackage="test.pojo"  
            targetProject="src">   
            <!-- 如果为TRUE，下面的设置了schema,那么包名就是会增加个schema名，   
            如果schema=“aaa”  , test.pojo.aaa.XXXX;   
            <property name="enableSubPackages" value="true" />   
             -->   
             <!-- 继承哪个父类，这个为了有时候日志需要打印整个对象，而采用的apache打印对象的每个属性 -->   
             <property name="rootClass" value="ibator.BaseBean" />   
        </javaModelGenerator>   
  
        <sqlMapGenerator targetPackage="test.sqlmap"  
            targetProject="src">   
            <!--   
            <property name="enableSubPackages" value="true" />   
             -->   
        </sqlMapGenerator>   
  
        <daoGenerator type="SPRING" targetPackage="test.dao" implementationPackage="test.dao.impl"  
            targetProject="src">   
            <!--   
            <property name="enableSubPackages" value="true" />   
             -->   
        </daoGenerator>   
  
        <!--   
        如果数据库里面有多个相同表名在不同的schema下，那么你得加上:   
        schema="XXXX"和<property name="ignoreQualifiersAtRuntime" value="true" />   
        不然ibator会以找到的最后一个为最终对象，这个问题苦恼了我很久，后来才发现   
         -->   
        <table tableName="OTA_APPLETE_INFO" schema="XXX">   
         <!-- 忽略schema，避免在xml中出现schema.表名   
          -->   
         <property name="ignoreQualifiersAtRuntime" value="true" />   
          <!-- 精确到时分秒时，需要设置下：  jdbcType="TIMESTAMP"-->   
          <columnOverride column="UPDATED_DATE" jdbcType="TIMESTAMP"/>   
        </table>   
    </ibatorContext>   
</ibatorConfiguration>  

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE ibatorConfiguration
  PUBLIC "-//Apache Software Foundation//DTD Apache iBATIS Ibator Configuration 1.0//EN"
  "http://ibatis.apache.org/dtd/ibator-config_1_0.dtd">

<ibatorConfiguration>
        <classPathEntry location="E:/jars/ojdbc/ojdbc14_10.2.0.4.jar" />
        <ibatorContext id="FlatJava5" targetRuntime="Ibatis2Java5">
                <property name="suppressTypeWarnings" value="true" />
                <!-- Serializable化 -->
                <ibatorPlugin type="org.apache.ibatis.ibator.plugins.SerializablePlugin" />
                <!-- 这个插件添加方法为例(实际上的内部类)来支持不区分大小写像搜索。这个演示了增加功能,通过一个实例类插件,而不是延长上课。 -->
                <ibatorPlugin type="org.apache.ibatis.ibator.plugins.CaseInsensitiveLikePlugin" />
                <!-- 分页 -->
                <ibatorPlugin type="org.apache.ibatis.ibator.plugins.PaginationPlugin">
                        <property name="enablePagination" value="true" />
                        <!-- mysql or oracle -->
                        <property name="databaseType" value="oracle" />
                </ibatorPlugin>
                <!-- 重命名example类 -->
                <ibatorPlugin
                        type="org.apache.ibatis.ibator.plugins.RenameExampleClassPlugin">
                        <property name="searchString" value="Example$" />
                        <property name="replaceString" value="Criteria" />
                </ibatorPlugin>
                <!-- 产生sqlmap.xml
                <ibatorPlugin type="org.apache.ibatis.ibator.plugins.SqlMapConfigPlugin">
                        <property name="targetPackage" value="ibatortest.generated.flat" />
                        <property name="targetProject" value="src" />
                </ibatorPlugin>
                -->

                <!-- driverClass="com.mysql.jdbc.Driver" -->
                <jdbcConnection driverClass="oracle.jdbc.driver.OracleDriver"
                        connectionURL="jdbcracle:thin127.0.0.1:1521ra10g" userId="test"
                        password="test" >
                        <!-- 是否返回数据库注释，MySQL默认是true，oracle默认是false -->
                        <property name="remarksReporting" value="true"/>
                        </jdbcConnection>
                <javaModelGenerator targetPackage="test.pojo"
                        targetProject="src">
                        <!-- 如果为TRUE，下面的设置了schema,那么包名就是会增加个schema名，
                        如果schema=“aaa”  , test.pojo.aaa.XXXX;
                        <property name="enableSubPackages" value="true" />
                         -->
                         <!-- 继承哪个父类，这个为了有时候日志需要打印整个对象，而采用的apache打印对象的每个属性 -->
                         <property name="rootClass" value="ibator.BaseBean" />
                </javaModelGenerator>

                <sqlMapGenerator targetPackage="test.sqlmap"
                        targetProject="src">
                        <!--
                        <property name="enableSubPackages" value="true" />
                         -->
                </sqlMapGenerator>

                <daoGenerator type="SPRING" targetPackage="test.dao" implementationPackage="test.dao.impl"
                        targetProject="src">
                        <!--
                        <property name="enableSubPackages" value="true" />
                         -->
                </daoGenerator>

                <!--
                如果数据库里面有多个相同表名在不同的schema下，那么你得加上:
                schema="XXXX"和<property name="ignoreQualifiersAtRuntime" value="true" />
                不然ibator会以找到的最后一个为最终对象，这个问题苦恼了我很久，后来才发现
                 -->
                <table tableName="OTA_APPLETE_INFO" schema="XXX">
                 <!-- 忽略schema，避免在xml中出现schema.表名
                  -->
                 <property name="ignoreQualifiersAtRuntime" value="true" />
                  <!-- 精确到时分秒时，需要设置下：  jdbcType="TIMESTAMP"-->
                  <columnOverride column="UPDATED_DATE" jdbcType="TIMESTAMP"/>
                </table>
        </ibatorContext>
</ibatorConfiguration>


Java代码  
package test.pojo;   
  
import ibator.BaseBean;   
import java.io.Serializable;   
import java.util.Date;   
/**  
* 2009-07-27  ss       
* <p>  
* 每个应用的具体信息  
*/  
public class BaseUsers extends BaseBean implements Serializable {   
    /**  
     * 用户ID  
     */  
    private String userId;   
  
    /**  
     * 组织结构编码  
     */  
    private String groupId;   
  
    /**  
     * 姓名  
     */  
    private String userName;   
  
    /**  
     * 证件号码  
     */  
    private String cardNo;  

package test.pojo;

import ibator.BaseBean;
import java.io.Serializable;
import java.util.Date;
/**
* 2009-07-27  ss     
* <p>
* 每个应用的具体信息
*/
public class BaseUsers extends BaseBean implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 组织结构编码
     */
    private String groupId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 证件号码
     */
    private String cardNo;  


附件一个为编译后的jar文件，一个是源码+doc。



ibator.jar.rar (430.9 KB) 
下载次数: 208 
ibator_src_doc.rar (370.7 KB) 
下载次数: 213 
ibator-2010-12-7.jar.rar (432.6 KB) 
下载次数: 154 
ibator_src.rar (420.9 KB) 
下载次数: 96 
ibator_cx.rar (342.1 KB) 
下载次数: 122
作者: ghnslmyp    时间: 2011-9-2 14:45:31

沉了？（...............）
作者: bhepfgpp    时间: 2011-9-2 14:45:44

我想知道对于sqlserver 2008是否也可以返回注释？
作者: chaosmax    时间: 2011-9-2 14:46:05

tjc 写道
我想知道对于sqlserver 2008是否也可以返回注释？

试一试就知道了的，我这里一般用mysql和Oracle，测试都可以的。
作者: xiezeshen66    时间: 2011-9-2 14:46:13

刚试了，不行。。。
作者: ygw92711    时间: 2011-9-2 14:46:22

有没有mybatis generator 1.3.0的?
作者: gwqpbjko    时间: 2011-9-2 14:46:30

gaifo 写道
有没有mybatis generator 1.3.0的?

没有，有时间看看他们的区别。
作者: yhvpqcyt    时间: 2011-9-2 14:46:39

gaifo 写道
有没有mybatis generator 1.3.0的?

看了下，就是重构了下生成注释的方法，感觉和ibator1.2.2区别不大。所以暂时不打算修改mybatis generator 1.3.0。
作者: yantoy    时间: 2011-9-2 14:46:48

public class Method extends JavaElement {
//        if (!interfaceMethod) {
作者: izzie2021    时间: 2011-9-2 14:46:59

增加了service层的代码生成。 



欢迎光临 开发者社区 (http://devbbs.doit.com.cn/)	 Powered by Discuz! X2