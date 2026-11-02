# 1.安装

## 1.下载sonatype nexus，不必下载最新版本： https://download.sonatype.com/nexus/3/nexus-3.93.3-03-win-x86_64.zip

## 2.解压缩到d:\programs里面，发现它里面有2个文件夹

![image-20261101124923192](./演练15-从零搭建nexus私服.assets/image-20261101124923192.png)

## 3.安装服务，进入nexus-3.93.3-03/bin文件夹

![image-20261101125015053](./演练15-从零搭建nexus私服.assets/image-20261101125015053.png)

## 4.在install-nexus-service.bat上面点击右键-》以管理员身份运行

![image-20261101130158575](./演练15-从零搭建nexus私服.assets/image-20261101130158575.png)

## 5.然后弹出一个窗口，我们输入y回车

![image-20261101130337838](./演练15-从零搭建nexus私服.assets/image-20261101130337838.png)

## 6.窗口关闭了，然后我们可以以管理员身份打开一个cmd窗口，转到D:\programs\nexus-3.93.3-03-win-x86_64\nexus-3.93.3-03\bin，输入nexus.exe start SonatypeNexusRepository来驱动服务

![image-20261101130722610](./演练15-从零搭建nexus私服.assets/image-20261101130722610.png)

## 7.按win+r,然后输入services.msc,打开服务控制台，可以看到Sonatype Nexus Repository服务正在运行

![image-20261101132614464](./演练15-从零搭建nexus私服.assets/image-20261101132614464.png)

# 2.使用

## 2.1他是一种web服务，打开浏览器，输入：http://localhost:8081/ ,就可以打开下面的登录页面

![image-20261101133130285](./演练15-从零搭建nexus私服.assets/image-20261101133130285.png)

## 2.2.然后用户名是admin，密码根据他的提示找到这个文件，然后把密码复制粘贴进来，点击登录

![image-20261101133552548](./演练15-从零搭建nexus私服.assets/image-20261101133552548.png)

## 2.3.然后就会进入这个界面

![image-20261101133635321](./演练15-从零搭建nexus私服.assets/image-20261101133635321.png)

## 2.4.点击next,它会要求你修改密码，我们就设置为abcd1234，然后需要重新输入一遍，然后点击next

![image-20261101133838647](./演练15-从零搭建nexus私服.assets/image-20261101133838647.png)

## 2.5.出现这个窗口点击next

![image-20261101134012117](./演练15-从零搭建nexus私服.assets/image-20261101134012117.png)

## 2.6.然后弹出许可授权，点击agree

![image-20261101134124388](./演练15-从零搭建nexus私服.assets/image-20261101134124388.png)

## 2.7.然后弹出一个窗口，说是否允许匿名访问，我们选择禁用匿名访问，然后点击next

![image-20261101134254474](./演练15-从零搭建nexus私服.assets/image-20261101134254474.png)

## 2.8.在弹出的窗口点击finish即可

![image-20261101134331745](./演练15-从零搭建nexus私服.assets/image-20261101134331745.png)

## 2.9.然后就进入后台管理界面

![image-20261101134804925](./演练15-从零搭建nexus私服.assets/image-20261101134804925.png)

## 2.10.点击左边面板的Browse，界面如下

![image-20261101135055410](./演练15-从零搭建nexus私服.assets/image-20261101135055410.png)

# 3.创建仓库

## 3.1.点击左边面板上面的Settings，进入如下界面

![image-20261101140421702](./演练15-从零搭建nexus私服.assets/image-20261101140421702.png)

## 3.2 点击创建和管理仓库，进入下面的界面

![image-20261101140630466](./演练15-从零搭建nexus私服.assets/image-20261101140630466.png)

## 3.3点击创建仓库按钮，就会出现选择仓库类型的界面，可以看到，它支持很多类型的仓库，包括conda,npm,r，我们创建的是maven2仓库，我们选择maven2的，如果你需要创建带来仓库，就选择maven2(proxy)

![image-20261101141049899](./演练15-从零搭建nexus私服.assets/image-20261101141049899.png)

## 3.4 填写名称和url后点击创建

![image-20261101144704050](./演练15-从零搭建nexus私服.assets/image-20261101144704050.png)

![image-20261101144759546](./演练15-从零搭建nexus私服.assets/image-20261101144759546.png)

## 3.5这样，一个代理仓库就创建成功了

![image-20261101144852578](./演练15-从零搭建nexus私服.assets/image-20261101144852578.png)

# 4.创建宿主仓库

## 4.1点击创建仓库，选择maven2(hosted)

![image-20261101150043890](./演练15-从零搭建nexus私服.assets/image-20261101150043890.png)

## 4.2如果我们新建release仓库，我们在名称一栏填写maven-hosted-repo-release,然后在version Policy一栏选择Release，然后就可以点击下面的创建按钮来创建

![image-20261101150625615](./演练15-从零搭建nexus私服.assets/image-20261101150625615.png)



## 然后一个发行版的仓库就创建好了，注意这个仓库不需要同版本覆盖

![image-20261101150813473](./演练15-从零搭建nexus私服.assets/image-20261101150813473.png)

## 4.3 如果需要创建snapshot版本的仓库，只需要选择snapshot即可，然后点击创建

![image-20261101153115521](./演练15-从零搭建nexus私服.assets/image-20261101153115521.png)

![image-20261101153147666](./演练15-从零搭建nexus私服.assets/image-20261101153147666.png)

## 然后一个快照版本的hosted仓库就创建了

![image-20261101153305632](./演练15-从零搭建nexus私服.assets/image-20261101153305632.png)







# 5.创建仓库组

## 5.1点击创建仓库，选择maven2(group)

![image-20261101145054094](./演练15-从零搭建nexus私服.assets/image-20261101145054094.png)

## 5.2仓库名称填写：maven-repo-group1,然后在下面根据需要添加仓库，把我们刚刚自己创建的仓库添加进来

![image-20261101153904837](./演练15-从零搭建nexus私服.assets/image-20261101153904837.png)



## 5.3点击创建即可

![image-20261101153939665](./演练15-从零搭建nexus私服.assets/image-20261101153939665.png)

# 6.使用nexus下载jar包

## 6.1在D盘上面新建一个maven-nexus-repo文件夹

![image-20261101154310924](./演练15-从零搭建nexus私服.assets/image-20261101154310924.png)

## 6.2 进入maven安装目录里面的conf/settings.xml，把这个文件夹配置为本地仓库文件夹

![image-20261101154754938](./演练15-从零搭建nexus私服.assets/image-20261101154754938.png)

## 6.3 然后我们用我们第一个手动创建的maven-project来测试一下，发现它工作正常，会重新下载需要的jar包到d:\maven-nexus-repo中

![image-20261101155223888](./演练15-从零搭建nexus私服.assets/image-20261101155223888.png)

![image-20261101155337859](./演练15-从零搭建nexus私服.assets/image-20261101155337859.png)

## 6.4 此外，我们还需要设置镜像地址，在settings.xml中找到mirrors标签，在里面添加一个mirror标签，内容如下,url是我们仓库组的链接

```
<mirror>
  <id>nexus-kenny</id>
  <mirrorOf>central</mirrorOf>
  <name>nexuskenny</name>
  <url>http://localhost:8081/repository/maven-repo-group1/</url>
</mirror>
```

### 注意：这个id可以随便写，但是一旦配置好了，在别处使用的时候就得是这个id

## 6.5可以设置nexus的登录密码，在settings.xml的servers标签里面,添加一个server标签，内容如下，注意id必须和上面配置的一样

```
<server>
  <id>nexus-kenny</id>
  <username>admin</username>
  <password>abcd1234</password>
</server>
```

## 6.6打开idea工具，看看他的设置有没有同步更新

![image-20261101161550637](./演练15-从零搭建nexus私服.assets/image-20261101161550637.png)

## 6.7.把我们刚刚下载到私服仓库里面的jar包全部删除，

![image-20261101161854137](./演练15-从零搭建nexus私服.assets/image-20261101161854137.png)

## 然后在idea工具里面构建任意一个maven项目，比如maven-01，发现他会重新下载jar包到私服仓库

![image-20261101161955114](./演练15-从零搭建nexus私服.assets/image-20261101161955114.png)

## 然后就编译成功

![image-20261101163953146](./演练15-从零搭建nexus私服.assets/image-20261101163953146.png)

## 6.8在浏览器里面也可以看到我们下载的jar包

![image-20261101165039646](./演练15-从零搭建nexus私服.assets/image-20261101165039646.png)

# 7.利用idea部署jar包到nexus私服

## 7.1因为我们自己写的jar包在中央仓库找不到，我们需要把它部署到私服的宿主仓库，如果的发行版就部署到Release的宿主仓库，如果是snapshot版本，就部署到snapshot宿主仓库

## 7.2打开需要部署的项目的pom.xml文件，添加下面的配置,需要到http://localhost:8081/#admin/repository/repositories 里面拷贝对应仓库的url

```
<distributionManagement>
    <repository>
        <id>nexus-kenny</id>
        <url>http://localhost:8081/repository/maven-hosted-repo-release/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-kenny</id>
        <url>http://localhost:8081/repository/maven-hosted-repo-snapshot/</url>
    </snapshotRepository>
</distributionManagement>
```

## 7.3我们新建一个模块，起名：maven-nexus-module

![image-20261101171536331](./演练15-从零搭建nexus私服.assets/image-20261101171536331.png)

## 7.4在他的pom.xml文件里面把packing设置为jar

![image-20261101171701082](./演练15-从零搭建nexus私服.assets/image-20261101171701082.png)

## 7.5然后添加部署配置

![image-20261101172219313](./演练15-从零搭建nexus私服.assets/image-20261101172219313.png)

## 7.6刷新maven工具

## 7.7 然后我们随便写一点代码

![image-20261101172502066](./演练15-从零搭建nexus私服.assets/image-20261101172502066.png)

## 7.8此时我们只需要点击deploy，因为这个生命周期是专门为部署到maven私服准备的

![image-20261101172750571](./演练15-从零搭建nexus私服.assets/image-20261101172750571.png)

## 7.9我们可以查看一下仓库，发现有这个jar包了

![image-20261101172929190](./演练15-从零搭建nexus私服.assets/image-20261101172929190.png)

## 7.10在私服的服务器上面也可以看见

![image-20261101173017627](./演练15-从零搭建nexus私服.assets/image-20261101173017627.png)

## 7.11当然，此时是快照版的。如果你需要发行版，你需要在你的pom.xml里面把它改为Release

![image-20261101173340389](./演练15-从零搭建nexus私服.assets/image-20261101173340389.png)

## 7.12然后重新部署

![image-20261101173546159](./演练15-从零搭建nexus私服.assets/image-20261101173546159.png)

## 7.13此时在服务器上面就会有我们的发行版

![image-20261101175556747](./演练15-从零搭建nexus私服.assets/image-20261101175556747.png)

## 7.14然后我们可以创建一个use_maven_nexus模块，在里面依赖我们的maven_nexus_module

![image-20261101180244527](./演练15-从零搭建nexus私服.assets/image-20261101180244527.png)

## 7.5然后是没有任何问题，可以编译通过

![image-20261101180550170](./演练15-从零搭建nexus私服.assets/image-20261101180550170.png)

### 其实如果内存允许，我们可以继续使用这个私服，只是当内存不足的时候，我们还是使用本地仓库就好了

