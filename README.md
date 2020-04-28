# Java Note
## <font color=#B22222>Java Base</font>
### [<font color=#FF4500>JDK</font>](#git)
## <font color=#B22222>Tool kit</font>
### [<font color=#FF4500>git</font>](#git)
## <font color=#B22222>MOM</font>
### [<font color=#FF4500>ActiveMQ</font>](#ActiveMQ)
### [<font color=#FF4500>RabbitMQ</font>](#RabbitMQ)
### [<font color=#FF4500>RocketMQ</font>](#RocketMQ)
## <font color=#B22222>Database</font>
### [<font color=#FF4500>MySQL</font>](#RocketMQ)
### [<font color=#FF4500>Oracle</font>](#RocketMQ)
### [<font color=#FF4500>Redis</font>](#RocketMQ)
## <font color=#B22222>ORM</font>
### [<font color=#FF4500>Hibernate</font>](#git)
### [<font color=#FF4500>JPA</font>](#git)
## <font color=#B22222>Spring</font>
### [<font color=#FF4500>SpringMVC</font>](#SpringMVC)
### [<font color=#FF4500>SpringData</font>](#SpringData)
### [<font color=#FF4500>SpringBoot</font>](#SpringBoot)
### [<font color=#FF4500>SpringCloud</font>](#SpringCloud)

<sapn id="JDK">

######System.exit(int status);

java.lang.System#exit
```
public static void exit(int status) {
    Runtime.getRuntime().exit(status);
}
```
status != 0 表示非正常退出，一般放在 catch 块中
status = 0 表示正常退出

######Java不可变类（immutable）机制与String的不可变性

[原文链接](https://www.cnblogs.com/jaylon/p/5721571.html)

String类就是典型的不可变类

**不可变类**  
所谓的不可变类是指这个类的实例一旦创建完成后，就不能改变其成员变量值。如JDK内部自带的很多不可变类：Interger、Long和String等。

**可变类**  
相对于不可变类，可变类创建实例后可以改变其成员变量值，开发中创建的大部分类都属于可变类
* 不可变类的优缺点
1. 线程安全
不可变对象是线程安全的，在线程之间可以相互共享，不需要利用特殊机制来保证同步问题，因为对象的值无法改变。可以降低并发错误的可能性，因为不需要用一些锁机制等保证内存一致性问题也减少了同步开销。
2. 易于构造、使用和测试
* 不可变类需要遵守以下原则：
1. 类添加 final 修饰符，保证类不被继承
2. 保证所有成员变量必须私有，并且加上 final 修饰符
3. 不提供改变成员变量的方法，包括 setter
4. 通过构造器初始化所有成员，进行深拷贝，使后面对成员变量的修改不影响新创建的值
	> eg: String 类中的代码片段，使用 Arrays.copyOf 对字符数组进行深拷贝，其中 copyOf 最终调用到一个本地方法，由 JVM 实现
	```
	public String(char value[]) {
        this.value = Arrays.copyOf(value, value.length);
    }
	```
5. 在 getter方法中不要直接返回对象本身，而是克隆对象，并返回对象的拷贝
	> eg: String 类中的代码片段,使用 System.arraycopy() 对字符数组进行深拷贝
	```
	public char[] toCharArray() {
        // Cannot use Arrays.copyOf because of class initialization order issues
        char result[] = new char[value.length];
        System.arraycopy(value, 0, result, 0, value.length);
        return result;
    }
	```
* String对象的不可变性的优缺点  
**优点**
1. 字符串常量池的需要.
字符串常量池可以将一些字符常量放在常量池中重复使用，避免每次都重新创建相同的对象、节省存储空间。但如果字符串是可变的，此时相同内容的String还指向常量池的同一个内存空间，当某个变量改变了该内存的值时，其他遍历的值也会发生改变。所以不符合常量池设计的初衷。

2. 线程安全考虑。
同一个字符串实例可以被多个线程共享。这样便不用因为线程安全问题而使用同步。字符串自己便是线程安全的。

3. 类加载器要用到字符串，不可变性提供了安全性，以便正确的类被加载。譬如你想加载java.sql.Connection类，而这个值被改成了myhacked.Connection，那么会对你的数据库造成不可知的破坏。

4. 支持hash映射和缓存。
因为字符串是不可变的，所以在它创建的时候hashcode就被缓存了，不需要重新计算。这就使得字符串很适合作为Map中的键，字符串的处理速度要快过其它的键对象。这就是HashMap中的键往往都使用字符串。

**缺点**
1. 如果有对String对象值改变的需求，那么会创建大量的String对象。
* 可通过反射对不可变类的成员变量进行修改
```
	//创建字符串"Hello World"， 并赋给引用s
	String s = "Hello World"; 
	System.out.println("s = " + s);	//Hello World

	//获取String类中的value字段
	Field valueFieldOfString = String.class.getDeclaredField("value");
	//改变value属性的访问权限
	valueFieldOfString.setAccessible(true);

	//获取s对象上的value属性的值
	char[] value = (char[]) valueFieldOfString.get(s);
	//改变value所引用的数组中的第5个字符
	value[5] = '_';
	System.out.println("s = " + s);  //Hello_World
```
***打印结果***
```
s = Hello World
s = Hello_World
```
</span>
***
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
	
***情况I*** 只修改了文件，没有任何git操作
- git checkout -- < filename > 

***情况II*** 修改了文件，并提交到了暂存区
- git log -- oneline：可省略
- git reset HEAD：回退到当前版本
- git checkout -- < filename >

***情况III*** 修改了文件并提交到了仓库
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
***
<sapn id="SpringBoot">
</span>
