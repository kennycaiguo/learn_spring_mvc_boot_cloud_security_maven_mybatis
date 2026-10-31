# 方法1、纯手工创建

## 1.1 还是我们上面的idea-maven-project,在项目名称上面点击右键-》新建-》模块

![image-20261030163257995](./演练4-idea用maven创建javaweb项目.assets/image-20261030163257995.png)

## 1.2 在弹出的窗口中输入模块名称maven-web1,点击创建

![image-20261030163458704](./演练4-idea用maven创建javaweb项目.assets/image-20261030163458704.png)

## 1.3 然后模块就创建出来了，不过此时它应该还是一个普通的java项目

![image-20261030163645180](./演练4-idea用maven创建javaweb项目.assets/image-20261030163645180.png)

## 1.4 然后我们需要在main文件夹里面创建一个webapp文件夹

![image-20261030163832230](./演练4-idea用maven创建javaweb项目.assets/image-20261030163832230.png)

## 1.5然后需要在webapp文件夹里面创建一个WEB-INF文件夹，在里面创建一个web.xml,内容如下

![image-20261030164129237](./演练4-idea用maven创建javaweb项目.assets/image-20261030164129237.png)

## 1.6修改一下模块里面的pom.xml文件，把打包方式改为war

![image-20261030164437436](./演练4-idea用maven创建javaweb项目.assets/image-20261030164437436.png)

## 1.7 添加依赖，比如添加servlet-api

![image-20261030175258070](./演练4-idea用maven创建javaweb项目.assets/image-20261030175258070.png)

### 添加了servlet-api后，idea就识别出来了，webapp的图标里面多了一个蓝色小圆圈。

![image-20261030175719225](./演练4-idea用maven创建javaweb项目.assets/image-20261030175719225.png)

#### 这里需要注意的是这个目录的名称必须是webapp，否则识别不出来

## 1.8在webapp里面新建一个index.html文件，内容如下

![image-20261030180347005](./演练4-idea用maven创建javaweb项目.assets/image-20261030180347005.png)

## 1.9 然后我们在main/java/里面添加一些代码

![image-20261030180729799](./演练4-idea用maven创建javaweb项目.assets/image-20261030180729799.png)

## 1.10 然后我们需要配置一下，把项目部署到tomcat，现在暂时还是使用以前的部署方式，先不要使用war包

![image-20261030181111562](./演练4-idea用maven创建javaweb项目.assets/image-20261030181111562.png)

## 1.11 启动服务器，会先展示index页面，效果如下

![image-20261030181313573](./演练4-idea用maven创建javaweb项目.assets/image-20261030181313573.png)

## 1.12 然后我们输入hello，效果如下

![image-20261030181410880](./演练4-idea用maven创建javaweb项目.assets/image-20261030181410880.png)

### 注意，如果你在web.xml中把metadata-complete设置位true，/hello路径就无法工作，因为metadata-complete属性会忽略注解。

![image-20261030181717990](./演练4-idea用maven创建javaweb项目.assets/image-20261030181717990.png)

# 方法2.就是我们以前一直在使用的添加web支持的方式

## 2.1在项目上面点击右键-》新建-》模块-》起名maven-web02

![image-20261030182103060](./演练4-idea用maven创建javaweb项目.assets/image-20261030182103060.png)

![image-20261030182219677](./演练4-idea用maven创建javaweb项目.assets/image-20261030182219677.png)

## 2.2 点击文件-》项目结构-》模块，选择我们的maven-web02模块，在他的上面点击右键—》添加->web

![image-20261030182445276](./演练4-idea用maven创建javaweb项目.assets/image-20261030182445276.png)

## 2.3.注意此时他的路径是不对的，这就是maven项目特殊的地方，我们需要修改路径

![image-20261030200717773](./演练4-idea用maven创建javaweb项目.assets/image-20261030200717773.png)

## 2.4下面web资源那一栏也需要修改

![image-20261030200810630](./演练4-idea用maven创建javaweb项目.assets/image-20261030200810630.png)

## 2.5然后点击下面的修正按钮创建构件

![image-20261030191547049](./演练4-idea用maven创建javaweb项目.assets/image-20261030191547049.png)

![image-20261030191621823](./演练4-idea用maven创建javaweb项目.assets/image-20261030191621823.png)

## 2.6然后修改模块里面的pom.xml,添加打包配置和servlet-api依赖

![image-20261030192834689](./演练4-idea用maven创建javaweb项目.assets/image-20261030192834689.png)

## 

## 2.7然后我们把它部署到tomcat

![image-20261030193644057](./演练4-idea用maven创建javaweb项目.assets/image-20261030193644057.png)

## 2.9然后我们在webapp文件夹里面添加一个index.html

![image-20261030201303939](./演练4-idea用maven创建javaweb项目.assets/image-20261030201303939.png)

## 2.10 启动服务器，发现index能够正常打开

![image-20261030194003467](./演练4-idea用maven创建javaweb项目.assets/image-20261030194003467.png)

# 方法3.使用idea提供的Maven ArcheType

## 3.1在项目上面点击右键-》新建-》模块-》Maven ArcheType，在弹出的窗口中填写模块相关信息，如图。然后点击创建

![image-20261030194604437](./演练4-idea用maven创建javaweb项目.assets/image-20261030194604437.png)

## 3.2然后maven会下载一下模板，等待一下

![image-20261030194703928](./演练4-idea用maven创建javaweb项目.assets/image-20261030194703928.png)

### 创建完成后长这样

![image-20261030194816251](./演练4-idea用maven创建javaweb项目.assets/image-20261030194816251.png)

## 3.3我们发现，这个原型生成的项目还是有一些缺陷，main文件夹里面没有java文件夹。src里面没有test文件夹，默认是jsp页面，我们需要手动修改一下

![image-20261030195203676](./演练4-idea用maven创建javaweb项目.assets/image-20261030195203676.png)

## 3.4 还有，生成的web.xml也不好，我们需要手动替换为下面的内容

![image-20261030195616625](./演练4-idea用maven创建javaweb项目.assets/image-20261030195616625.png)

## 3.5检查一下该模块下面的pom.xml,发现它添加了一个我们不需要的依赖

![image-20261030195714973](./演练4-idea用maven创建javaweb项目.assets/image-20261030195714973.png)

## 3.6 把它删除了，然后添加servlet-api依赖

![image-20261030200235019](./演练4-idea用maven创建javaweb项目.assets/image-20261030200235019.png)

## 3.7 然后把index.jsp改名index.html,内容如下

![image-20261030201535333](./演练4-idea用maven创建javaweb项目.assets/image-20261030201535333.png)

## 3.8 启动服务器，效果如下

![image-20261030201628714](./演练4-idea用maven创建javaweb项目.assets/image-20261030201628714.png)

# 总的来说，可能方式2比较好一点，注意需要把网站的根改为webapp而不是web

# 此外还有一种快速复制maven项目的方法，还是比较方便，如果你不想创建项目，可以使用这种方法

##  快速拷贝Maven项目

第一步：在硬盘上直接复制粘贴，例如将`maven-web-002`拷贝为`maven-web-003`。注意：直接在工程目录下复制，然后还是粘贴到工程目录下，修改名字。

第二步：删除target目录。

第三步：修改`pom.xml`中的`<artifactId>maven-web-003</artifactId>`

第四步：打开IDEA工具，在`pom.xml`文件上右键，然后点击`+ Add as Maven Project`。

# 