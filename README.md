# Java Note
## <font color=#B22222>Tool kit</font>
### [<font color=#FF4500>git</font>](#git)
## <font color=#B22222>ORM</font>
### [<font color=#FF4500>Hibernate</font>](#git)
### [<font color=#FF4500>JPA</font>](#git)
## <font color=#B22222>Spring</font>
### [<font color=#FF4500>SpringMVC</font>](#SpringMVC)
### [<font color=#FF4500>SpringData</font>](#SpringData)
### [<font color=#FF4500>SpringBoot</font>](#SpringBoot)
### [<font color=#FF4500>SpringCloud</font>](#SpringCloud)

<sapn id="git">
<font face="微软雅黑">
#### **基本命令**

- git config --global user.name "Your name"
- git config --global user.email "youremail@example.com"	
	- Ex:git config --global core.editor vim
- git init : 初始化一个仓库，会生成 .git文件 

#### **Commit 结构**

- git status(gst):查看repo状态	
- 工作区:
	- **.git目录** 
	- **暂存区**
	- **工作目录**
	
	![](https://camo.githubusercontent.com/7e36122d6585913b6c61de937a77209d3b5abebc/68747470733a2f2f692e696d6775722e636f6d2f42307731316e622e706e67)
- git add <fielname>(ga):添加一个文件到暂存区
- git add . (gaa):添加所有文件到暂存区
- git add *.js:添加所有后缀为js的文件到暂存区
- git rm -- cached <file>:存暂存区删除一个新文件
#### **恢复修改的文件**
	
***情况I***只修改了文件，没有任何git操作
- git checkout -- < filename > 

***情况II***修改了文件，并提交到了暂存区
- git log -- oneline：可省略
- git reset HEAD：回退到当前版本
- git checkout -- < filename >

***情况III***修改了文件并提交到了仓库
- git log -- oneline:可省略
- git reser HEAD^:回退到上一个版本
- git checkout -- < filename >

***Tip1:***情况II和情况III只有回退的版本不一样
对于情况II,并没有$ git commit,版本库没有更新记录，所以回退的是当前版本
对于情况III,由于执行了$ git commit,版本库已经有了提交记录，所以回退的是当前版本

***Tip2:***git reset 版本号 ---- 将暂存区回退到指定版本。
根据 $ git log --oneline 显示的版本号（下图黄色的字），可以回退到任何一个版本，也可通过 HEAD 来指定版本（下图红色的字）
eg:

|版本号|log|HEAD|remark|
|:-----------:|:------:|:------|:---------|
| <font color= #B8860B>2wrw343re</font>   | log1   |<font color= 'red'>HEAD</font>   |当前版本   |
| <font color= #B8860B>3ewreww34</font>   | log2   |<font color= 'red'>HEAD^</font>  |上一个版本 |
| <font color= #B8860B>ewew232rw</font>   | log3   |<font color= 'red'>HEAD^^</font> |上上一个版本|
| <font color= #B8860B>2n3ewerre</font>   | log4   |<font color= 'red'>HEAD~n</font> |第n个版本  |
</font>
</span>
<sapn id="SpringBoot">
</span>
