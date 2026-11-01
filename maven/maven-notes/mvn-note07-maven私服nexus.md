# Maven私服

## 什么是私服

**<font style="color:#DF2A3F;">Maven 私服是一种特殊的远程仓库，它是架设在局域网内的仓库服务</font>**，用来代理位于外部的远程仓库（中央仓库、其他远程公共仓库）。一些无法从外部仓库下载到的构件，也能从本地上传到私服供其他人使用。

Maven 私服其实并不是 Maven 的核心概念，它仅仅是一种衍生出来的特殊的仓库，但这并不代表它不重要，相反由于私服具有降低中央仓库负荷、节省外网带宽、以及提高项目稳定性等优点，使得私服在实际开发过程中得到了相当普遍地使用。建立了 Maven 私服后，当局域网内的用户需要某个构件时，会先请求本地仓库，若本地仓库不存在所需构件，则请求 Maven 私服，将所需构件下载到本地仓库，若私服中不存在所需构件，再去请求外部的远程仓库，将所需构件下载并缓存到 Maven 私服，若外部远程仓库不存在所需构件，则 Maven 直接报错。

## Maven仓库管理器Nexus

### 什么是Nexus

Nexus 是 Sonatype（中央仓库实际的维护方） 公司发布的一款仓库（Repository）管理软件，常用来搭建 Maven 私服，所以也有人将 Nexus 称为“Maven仓库管理器”。 Sonatype Nexus 是当前最流行，使用最广泛的 Maven 仓库管理器。Nexus 分为开源版和专业版，开源版足以满足大部分 Maven 用户的需求。

### Nexus仓库的类型

Nexus默认内置了许多仓库，这些仓库被分为三大类，每种类型的仓库用于存放特定的`jar`包：

1. 代理仓库（proxy）：缓存下载过的依赖，避免重复从外网拉取。例如Nexus中内置的`maven-central`库就是一个代理仓库，从远程中央仓库中下载的`jar`包会被缓存到`maven-central`这个代理仓库中。
2. 宿主仓库（hosted）：存储本地构建的私有构件，如公司内部开发的 Jar 包。对于宿主仓库来说，包含多个子分类，例如：
   1. Release：存放稳定版本（如Nexus内置的`maven-releases`仓库），<font style="color:rgb(64, 64, 64);">一旦发布到</font><code><font style="color:rgb(64, 64, 64);">Release</font></code><font style="color:rgb(64, 64, 64);">类型的仓库，同一个版本的构件</font>**<font style="color:rgb(64, 64, 64);">不允许重复部署或覆盖</font>**<font style="color:rgb(64, 64, 64);">。</font>
   2. Snapshot：存放快照版本（如Nexus内置的`maven-snapshots`仓库），<font style="color:rgb(64, 64, 64);">发布到</font><code><font style="color:rgb(64, 64, 64);">Snapshot</font></code><font style="color:rgb(64, 64, 64);">类型的仓库的构件</font>**<font style="color:rgb(64, 64, 64);">可以被多次覆盖</font>**<font style="color:rgb(64, 64, 64);">。</font>
3. 仓库组（group）：将多个仓库合并为一个逻辑入口，简化客户端配置。例如Nexus中内置的`maven-public`仓库，主要是为了简化配置，这么多仓库不需要都配置，只需要配置一个仓库组就行了。

![1748415005673-89afe0b0-da62-4231-b017-f4ea2c2fb0e5.png](./mvn-note07-maven私服nexus.assets/1748415005673-89afe0b0-da62-4231-b017-f4ea2c2fb0e5-624934.png)

![1748416008789-f89e99fa-867a-465a-b102-52e9e8af9fc9.png](./mvn-note07-maven私服nexus.assets/1748416008789-f89e99fa-867a-465a-b102-52e9e8af9fc9-962956.png)

### 仓库为什么要分类

<font style="color:rgb(64, 64, 64);">分类的目的是</font><font style="color:rgb(64, 64, 64);"> </font>**<font style="color:rgb(64, 64, 64);">明确职责，优化管理</font>**<font style="color:rgb(64, 64, 64);">：</font>

* **<font style="color:rgb(64, 64, 64);">隔离环境</font>**<font style="color:rgb(64, 64, 64);">：区分快照（Snapshot）和正式版（Release），避免混淆。</font>
* **<font style="color:rgb(64, 64, 64);">权限控制</font>**<font style="color:rgb(64, 64, 64);">：可以为不同仓库设置不同的读写权限（如开发人员可上传 Snapshot，但只有管理员能发布 Release）。</font>
* **<font style="color:rgb(64, 64, 64);">性能优化</font>**<font style="color:rgb(64, 64, 64);">：代理仓库可以缓存热门依赖，而宿主仓库专注于私有构件。</font>
* **<font style="color:rgb(64, 64, 64);">简化客户端配置</font>**<font style="color:rgb(64, 64, 64);">：通过仓库组统一暴露仓库，客户端无需关心底层细节。</font>

### 安装Nexus

#### 下载Nexus

下载地址：<https://help.sonatype.com/en/download.html>

![1782648989942-ee952ebc-a09b-46a3-a03a-c87f80902ce7.png](./mvn-note07-maven私服nexus.assets/1782648989942-ee952ebc-a09b-46a3-a03a-c87f80902ce7-102984.png)

#### 解压安装Nexus

下载后解压到一个没有中文的路径下：

![1782649019855-1f87c8bd-1950-4911-b229-b19e5c847d1b.png](./mvn-note07-maven私服nexus.assets/1782649019855-1f87c8bd-1950-4911-b229-b19e5c847d1b-588545.png)

打开以上文件夹后，可以看到两个子目录，如下：

![1782649081981-fe744f93-2eb1-4859-adb5-89a46a72bdb9.png](./mvn-note07-maven私服nexus.assets/1782649081981-fe744f93-2eb1-4859-adb5-89a46a72bdb9-322662.png)

#### 启动Nexus服务

进入`nexus-3.93.2-01\bin`目录下：

![1782649180086-baf879c9-d505-4b03-a42a-67b730e364a7.png](./mvn-note07-maven私服nexus.assets/1782649180086-baf879c9-d505-4b03-a42a-67b730e364a7-106486.png)

双击 `install-nexus-service.bat`批处理文件进行服务的安装。

然后执行这个命令启动服务【**以管理员身份运行**】：`nexus.exe start SonatypeNexusRepository`

![1782649286934-d38d671b-b683-4a4b-aba0-db70245af341.png](./mvn-note07-maven私服nexus.assets/1782649286934-d38d671b-b683-4a4b-aba0-db70245af341-541700.png)

**等待 2-5 分钟，查看服务是否启动成功：**

![1782649387123-d36b80af-226e-443e-b58d-8cdbad4fe6a0.png](./mvn-note07-maven私服nexus.assets/1782649387123-d36b80af-226e-443e-b58d-8cdbad4fe6a0-548872.png)

**<font style="color:#DF2A3F;">一定要注意：如果不准备用私服，一定要把这个服务的自动启动修改为手动启动，要不然每次开机都会启动这个服务，这个服务比较耗费内存。</font>**

#### 访问Nexus

访问地址：http://localhost:8081

![1782649474522-4a2bab51-b2cd-4006-b442-aec1f3f38cb3.png](./mvn-note07-maven私服nexus.assets/1782649474522-4a2bab51-b2cd-4006-b442-aec1f3f38cb3-126389.png)

**第一次登录去这里找密码：**`nexus-3.93.2-01-win-x86_64\sonatype-work\nexus3`，用户名是 `admin`

![1782649614157-4e2228e2-327a-454e-87d9-76a714a4c78c.png](./mvn-note07-maven私服nexus.assets/1782649614157-4e2228e2-327a-454e-87d9-76a714a4c78c-249545.png)

**登录成功后，第一次要求修改密码，至少八位（这里设置为 12345678），另外为了安全，建议拒绝匿名访问：**

![1782650034285-6799d745-7c4a-44cd-99ce-ff22f8c960db.png](./mvn-note07-maven私服nexus.assets/1782650034285-6799d745-7c4a-44cd-99ce-ff22f8c960db-582861.png)

**端口可以修改，在这个文件中：**

![1782649838674-3385907e-04e1-4af1-9325-eaa59cf2c2df.png](./mvn-note07-maven私服nexus.assets/1782649838674-3385907e-04e1-4af1-9325-eaa59cf2c2df-455564.png)

## Nexus私服的应用

### 浏览仓库

![1782650095433-19189bc7-e628-4113-ba89-ed4b26bdcb28.png](./mvn-note07-maven私服nexus.assets/1782650095433-19189bc7-e628-4113-ba89-ed4b26bdcb28-983148.png)

### 设置仓库

#### 创建仓库

![1782650296127-bf707c48-b0e2-4ed7-a968-28903fa7b079.png](./mvn-note07-maven私服nexus.assets/1782650296127-bf707c48-b0e2-4ed7-a968-28903fa7b079-932490.png)

#### 创建代理仓库

![1782650355236-b5f5c71d-6b99-45fb-bfa5-090f9d4318bb.png](./mvn-note07-maven私服nexus.assets/1782650355236-b5f5c71d-6b99-45fb-bfa5-090f9d4318bb-094701.png)

![1782650437417-3b5d78e0-33af-4530-9ae4-e85850f44222.png](./mvn-note07-maven私服nexus.assets/1782650437417-3b5d78e0-33af-4530-9ae4-e85850f44222-368372.png)

阿里云 Maven 镜像：`https://maven.aliyun.com/repository/central`

设置好以上两项之后，拉到页面底部，点击创建仓库即可。

#### 创建宿主仓库：Release

![1782650547315-3309495b-c4c2-4ff1-8239-1c706961c9c6.png](./mvn-note07-maven私服nexus.assets/1782650547315-3309495b-c4c2-4ff1-8239-1c706961c9c6-469990.png)

![1782650609627-c1bbb791-b17f-4de2-a346-be86d9b468f6.png](./mvn-note07-maven私服nexus.assets/1782650609627-c1bbb791-b17f-4de2-a346-be86d9b468f6-121142.png)

到页面底部点击创建仓库。

#### 创建宿主仓库：Snapshot

![1782650659580-aee7e6c6-9a18-4c64-b42c-4456f0ce5faf.png](./mvn-note07-maven私服nexus.assets/1782650659580-aee7e6c6-9a18-4c64-b42c-4456f0ce5faf-054318.png)

![1782650691760-ed4bfd14-18d1-4271-8ae3-1a4e5db4da5b.png](./mvn-note07-maven私服nexus.assets/1782650691760-ed4bfd14-18d1-4271-8ae3-1a4e5db4da5b-892507.png)

拉到页面底部，点击创建仓库即可。

#### 创建仓库组

![1782650734522-274b713e-ba05-4832-819b-9d2f4633201a.png](./mvn-note07-maven私服nexus.assets/1782650734522-274b713e-ba05-4832-819b-9d2f4633201a-191869.png)

![1782650811756-af53ab3d-d84d-4358-b7b0-ebcfb5e8f310.png](./mvn-note07-maven私服nexus.assets/1782650811756-af53ab3d-d84d-4358-b7b0-ebcfb5e8f310-814832.png)

拉到页面底部，点击创建仓库即可。

最后所有创建的仓库如下：

![1782650886093-6db65b21-7f71-4fd1-84b2-835d3fdf889d.png](./mvn-note07-maven私服nexus.assets/1782650886093-6db65b21-7f71-4fd1-84b2-835d3fdf889d-494456.png)

### 使用Nexus下载jar包

#### 设置Maven本地仓库地址

修改`MAVEN_HOME/conf/settings.xml`文件：

```xml
<localRepository>D:\repository-nexus</localRepository>
```

#### 设置`<mirror>`标签

```xml
<mirror>
  <id>nexus-jkweilai</id>
  <mirrorOf>central</mirrorOf>
  <name>nexusjkweilai</name>
  <url>http://localhost:8081/repository/maven-public-jk/</url>
</mirror>
```

url从这里获取：

![1782651012565-6412f18e-e892-4184-98d9-5360c76cbf50.png](./mvn-note07-maven私服nexus.assets/1782651012565-6412f18e-e892-4184-98d9-5360c76cbf50-066669.png)

#### 设置Nexus的用户名和密码

找到 `settings.xml`文件的 `servers`标签，添加以下配置：

```xml
<server>
  <id>nexus-jkweilai</id>
  <username>admin</username>
  <password>12345678</password>
</server>
```

注意：`<id>`必须和`<mirror>`中的`<id>`保持一致。

#### 确定IDEA中Maven指向的本地仓库地址

![1748420366169-e927a0cd-f1c7-4838-a37a-c9ab8fc9a4dc.png](./mvn-note07-maven私服nexus.assets/1748420366169-e927a0cd-f1c7-4838-a37a-c9ab8fc9a4dc-224480.png)

#### 随意运行一个Maven项目的`clean`

![1748420677388-10d3929e-df1c-4f03-9d10-b6de32f1e269.png](./mvn-note07-maven私服nexus.assets/1748420677388-10d3929e-df1c-4f03-9d10-b6de32f1e269-747142.png)

![1782651441773-5641fd5f-0512-48fa-90bb-4cb5a33ba49f.png](./mvn-note07-maven私服nexus.assets/1782651441773-5641fd5f-0512-48fa-90bb-4cb5a33ba49f-534707.png)

#### 观察本地仓库

![1748420870835-8315dad6-74ed-4086-bcf2-3a9737ec2a8d.png](./mvn-note07-maven私服nexus.assets/1748420870835-8315dad6-74ed-4086-bcf2-3a9737ec2a8d-398326.png)

#### 观察私服上的`maven-public-jk`

![1782651942223-bb932752-3d75-4dd3-aaa3-25b4d2c7e852.png](./mvn-note07-maven私服nexus.assets/1782651942223-bb932752-3d75-4dd3-aaa3-25b4d2c7e852-907562.png)

![1782651957041-e4ce9d3c-1a35-4bd3-aeed-1c1fad7f9e9b.png](./mvn-note07-maven私服nexus.assets/1782651957041-e4ce9d3c-1a35-4bd3-aeed-1c1fad7f9e9b-298800.png)

### 使用IDEA部署jar包到Nexus私服

私服Nexus是部署在局域网的，是全公司共享的仓库地址，每个团队都可以将已完成的功能或测试版本发布到私服供别人来使用。

#### 设置部署路径

打开要部署的项目的pom.xml文件，设置上传路径

```xml
<distributionManagement>
    <repository>
        <id>nexus-jkweilai</id>
        <url>http://localhost:8081/repository/maven-releases-jk/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-jkweilai</id>
        <url>http://localhost:8081/repository/maven-snapshots-jk/</url>
    </snapshotRepository>
</distributionManagement>
```

url从这里拿：

![1782652004548-40518d83-cfdf-4101-a7e9-49dc633c7679.png](./mvn-note07-maven私服nexus.assets/1782652004548-40518d83-cfdf-4101-a7e9-49dc633c7679-394540.png)

#### 运行deploy部署命令

![1748421306635-f4f8bdd6-4ea8-4796-9a4d-c689d1ed0dd2.png](./mvn-note07-maven私服nexus.assets/1748421306635-f4f8bdd6-4ea8-4796-9a4d-c689d1ed0dd2-026564.png)

#### 观察私服对应仓库变化

* release项目部署：项目 pom.xml 中需要这样配置 <code><font style="color:#080808;background-color:#ffffff;"><version>1.0-RELEASE</version></font></code><font style="color:#080808;background-color:#ffffff;">，以 </font><code><font style="color:#080808;background-color:#ffffff;">RELEASE</font></code><font style="color:#080808;background-color:#ffffff;">结尾。</font>

![1782652296238-42a29e90-12a3-4299-89b0-a759cfca5d88.png](./mvn-note07-maven私服nexus.assets/1782652296238-42a29e90-12a3-4299-89b0-a759cfca5d88-004959.png)

* snapshot项目部署：项目 pom.xml 中需要这样配置 <code><font style="color:#080808;background-color:#ffffff;"><version>1.0-SNAPSHOT</version></font></code><font style="color:#080808;background-color:#ffffff;">，以 </font><code><font style="color:#080808;background-color:#ffffff;">SNAPSHOT</font></code><font style="color:#080808;background-color:#ffffff;"> 结尾。</font>

![1782652244115-0bec38b5-826d-46d4-be8a-e79c52e0d79a.png](./mvn-note07-maven私服nexus.assets/1782652244115-0bec38b5-826d-46d4-be8a-e79c52e0d79a-949987.png)


> 更新: 2026-06-29 02:00:39  
> 原文: <https://www.yuque.com/dujubin/java/hp5bllxqf7g9gmn5>

