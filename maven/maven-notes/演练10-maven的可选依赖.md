## 1.新建一个模块maven-09

<img src="./演练10-maven的可选依赖.assets/image-20261031183504958.png" alt="image-20261031183504958" style="zoom:50%;" />

<img src="./演练10-maven的可选依赖.assets/image-20261031183528981.png" alt="image-20261031183528981" style="zoom: 50%;" />

## 2.添加一个mysql依赖，在这个依赖里面添加一个< optional>true< /optional>,这个依赖就变为可选依赖

![image-20261031183910392](./演练10-maven的可选依赖.assets/image-20261031183910392.png)

## 3.设置了optional之后，这个jar包就不支持传递了。我们新建一个maven-10模块，在里面引入maven-09模块，你会发现，它没有mysql的包耶。

![image-20261031184214025](./演练10-maven的可选依赖.assets/image-20261031184214025.png)

## 4.回到maven-09模块，把optional的值设置为false，刷新maven工具在maven-10里面可以看到mysql驱动包

![image-20261031184530561](./演练10-maven的可选依赖.assets/image-20261031184530561.png)