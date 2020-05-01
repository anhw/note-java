# Java Note
## <font color=#B22222>Java Base</font>
### [<font color=#FF4500>JDK</font>](#JDK)
### [<font color=#FF4500>JVM</font>](#JVM)
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

<span id="JDK">

###### System.exit(int status);

java.lang.System#exit
```
public static void exit(int status) {
    Runtime.getRuntime().exit(status);
}
```
status != 0 表示非正常退出，一般放在 catch 块中
status = 0 表示正常退出

###### Java不可变类（immutable）机制与String的不可变性

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
 ###### Serializable
 1. 对于实现 Serializable 接口的类，在反序列化时，并不要求该类具有一个无参的构造方法，因为在反序列化的过程中实际上是去其继承树上找到一个没有实现 Serializable 接口的父类((最终会找到 Object )，然后构造该类的对象，再逐层往下的去设置各个可以反序列化的属性(也就是没有被 transient 修饰的非静态属性))
 > eg:
 ```java
public class Parent {
    public Parent() {
        System.err.println("Parent no-arg constructor invoked！");
    }
}

public class Elvis extends Parent implements Serializable {
	
	public static final Elvis INSTANCE = new Elvis();

	public Elvis() {
		System.out.println("Elvis no-arg constructor invoked!");
	}
}
@Test
public void testSerialization() throws Exception {
	Elvis elvis1 = Elvis.INSTANCE;
	FileOutputStream fos = new FileOutputStream("a.txt");
	ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(elvis1);
    oos.flush();
    oos.close();

    Elvis elvis2 = null;
    FileInputStream fis = new FileInputStream("a.txt");
    ObjectInputStream ois = new ObjectInputStream(fis);
    elvis2 = (Elvis) ois.readObject();

    System.out.println("elvis1与elvis2相等吗？ ===> " + (elvis1 == elvis2));
}
 ```
*打印结果*
```java
Parent no-arg constructor invoked！
Elvis no-arg constructor invoked!
===开始反序列化===
Parent no-arg constructor invoked！
elvis1与elvis2相等吗？ ===> false
```
</span>
<span id="JVM">

## JVM 基本原理 
### Java 对象的内存布局
####Java 新建对象的方式
* new 语句 通过调用构造器来初始化实例字段
* 反射 通过调用构造器来初始化实例字段
* 反序列化 复制已有的数据，初始化新建对象的实例
* Object.clone 复制已有的数据，初始化新建对象的实例
* Unsafe.allocateInstance 没有初始化实例字段
以 new 语句为例，它编译而成的字节码将包含用来请求内存的 new 指令，以及用来调用构造器的 invokespecial 指令
```jvm
// Foo foo = new Foo(); // 编译而成的字节码 
0 new Foo 
3 dup 
4 invokespecial Foo() 
7 astore_1
```
当我们调用一个构造器时，它将优先调用父类的构造器，直至 Object 类。这些构造器的调用者皆为同一对象，也就是通过 new 指令新建而来的对象。  
你应该已经发现了其中的玄机：通过 new 指令新建出来的对象，它的内存其实涵盖了所有父类中的实例字段。也就是说，虽然子类无法访问父类的私有实例字段，或者子类的实例字段隐藏了父类的同名实例字段，但是子类的实例还是会为这些父类实例字段分配内存的。
#### 压缩指针
在 Java 虚拟机中，每个 Java 对象都有一个对象头（object header），这个由标记字段(mark word)和类型指针所构成。其中，标记字段用以存储 Java 虚拟机有关该对象的运行数据，如哈希码、GC 信息以及锁信息，它的最后两位被用来表示该对象的锁状态，其中，00代表轻量级锁，01代表无锁（或偏向锁），10代表重量级锁，11则跟垃圾回收算法的标记有关。而类型指针则指向该对象的类  

Java 虚拟机引入了压缩指针的概念，将原本的 64 位指针压缩成 32 位。压缩指针要求 Java 虚拟机堆中对象的起始地址要对齐至 8 的倍数。Java 虚拟机还会对每个类的字段进行重排列，使得字段也能够内存对齐
## JVM 高效编译
### synchronized 的实现
在 Java 程序中我们可以利用 synchronized 关键字来对程序进行加锁。它既可以用来申明一个 synchronized 代码块，也可以直接标记静态方法或者实例方法。  
当声明 synchronized 代码块时，编译而成的字节码将包含 monitorenter 、 monitorexit 指令。这两种指令均会消耗操作数栈上的一个引用类型的元素（也就是 synchronized 关键字括号里的引用），作为所要加锁解锁的锁对象。
#### synchronized 代码块
```java
public void foo(Object lock) { 
    synchronized (lock) { 
        lock.hashCode(); 
    } 
}
```
*上面的Java代码将编译为下面的字节码*
```jvm
public void foo(java.lang.Object);
    Code:
       0: aload_1
       1: dup
       2: astore_2
       3: monitorenter
       4: aload_1
       5: invokevirtual #2                  // Method java/lang/Object.hashCode:()I
       8: pop
       9: aload_2
      10: monitorexit
      11: goto          19
      14: astore_3
      15: aload_2
      16: monitorexit
      17: aload_3
      18: athrow
      19: return
    Exception table:
       from    to  target type
           4    11    14   any
          14    17    14   any
```
以上字节码中会看到一个 mointorenter 和多个 monitorexit ，这是因为 JVM 要保证获得的锁在正常路径和异常路径上都可以被解锁。
#### synchronized 标记方法
当用 synchronized 标记方法时，你会看到字节码中方法的访问标记包括 ACC_SYNCHRONIZED。该标记表示在进入该方法时，Java 虚拟机需要进行 monitorenter 操作。而在退出该方法时，不管是正常返回，还是向调用者抛异常，Java 虚拟机均需要进行 monitorexit 操作。
```java
public synchronized void foo(Object lock) { 
    lock.hashCode(); 
}
```
*面的Java代码将编译为下面的字节码*
```jvm
public synchronized void foo(java.lang.Object);
    descriptor: (Ljava/lang/Object;)V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=1, locals=2, args_size=2
         0: aload_1
         1: invokevirtual #2                  // Method java/lang/Object.hashCode:()I
         4: pop
         5: return
      LineNumberTable:
        line 23: 0
        line 24: 5

```
</span>
这里 monitorenter 和 monitorexit 操作所对应的锁对象是隐式的。对于实例方法来说，这两个操作对应的锁对象是 this；对于静态方法来说，这两个操作对应的锁对象则是所在类的 Class 实例
当执行 monitorenter 时，如果目标锁对象的计数器为 0，那么说明它没有被其他线程所持有。在这个情况下，Java 虚拟机会将该锁对象的持有线程设置为当前线程，并且将其计数器加 1。  

在目标锁对象的计数器不为 0 的情况下，如果锁对象的持有线程是当前线程，那么 Java 虚拟机可以将其计数器加 1，否则需要等待，直至持有线程释放该锁。  

当执行 monitorexit 时，Java 虚拟机则需将锁对象的计数器减 1。当计数器减为 0 时，那便代表该锁已经被释放掉了。  

之所以采用这种计数器的方式，是为了允许同一个线程重复获取同一把锁。举个例子，如果一个 Java 类中拥有多个 synchronized 方法，那么这些方法之间的相互调用，不管是直接的还是间接的，都会涉及对同一把锁的重复加锁操作。因此，我们需要设计这么一个可重入的特性，来避免编程里的隐式约束。
#### HotSpot 虚拟机中具体的锁实现
> **重量级锁**

重量级锁会阻塞、唤醒请求加锁的线程。它针对的是多个线程同时竞争同一把锁的情况。Java 虚拟机采取了自适应自旋，来避免线程在面对非常小的 synchronized 代码块时，仍会被阻塞、唤醒的情况。
>> 重量级锁是 Java 虚拟机中最为基础的锁实现。在这种状态下，Java 虚拟机会阻塞加锁失败的线程，并且在目标锁被释放的时候，唤醒这些线程。  

>> Java 线程的阻塞以及唤醒，都是依靠操作系统来完成的。举例来说，对于符合 posix 接口的操作系统（如 macOS 和绝大部分的 Linux），上述操作是通过 pthread 的互斥锁（mutex）来实现的。此外，这些操作将涉及系统调用，需要从操作系统的用户态切换至内核态，其开销非常之大。  

>> 为了尽量避免昂贵的线程阻塞、唤醒操作，Java 虚拟机会在线程进入阻塞状态之前，以及被唤醒后竞争不到锁的情况下，进入自旋状态，在处理器上空跑并且轮询锁是否被释放。如果此时锁恰好被释放了，那么当前线程便无须进入阻塞状态，而是直接获得这把锁。

>> 与线程阻塞相比，自旋状态可能会浪费大量的处理器资源。这是因为当前线程仍处于运行状况，只不过跑的是无用指令。它期望在运行无用指令的过程中，锁能够被释放出来。

>> 我们可以用等红绿灯作为例子。Java 线程的阻塞相当于熄火停车，而自旋状态相当于怠速停车。如果红灯的等待时间非常长，那么熄火停车相对省油一些；如果红灯的等待时间非常短，比如说我们在 synchronized 代码块里只做了一个整型加法，那么在短时间内锁肯定会被释放出来，因此怠速停车更加合适。

>> 然而，对于 Java 虚拟机来说，它并不能看到红灯的剩余时间，也就没办法根据等待时间的长短来选择自旋还是阻塞。Java 虚拟机给出的方案是自适应自旋，根据以往自旋等待时是否能够获得锁，来动态调整自旋的时间（循环数目）。

>> 就上面的例子来说，如果之前不熄火等到了绿灯，那么这次不熄火的时间就长一点；如果之前不熄火没等到绿灯，那么这次不熄火的时间就短一点。

>> 自旋状态还带来另外一个副作用，那便是不公平的锁机制。处于阻塞状态的线程，并没有办法立刻竞争被释放的锁。然而，处于自旋状态的线程，则很有可能优先获得这把锁。

> **轻量级锁**

轻量级锁采用 CAS 操作，将锁对象的标记字段替换为一个指针，指向当前线程栈上的一块空间，存储着锁对象原本的标记字段。它针对的是多个线程在不同时间段申请同一把锁的情况。  
>> 你可能见到过深夜的十字路口，四个方向都闪黄灯的情况。由于深夜十字路口的车辆来往可能比较少，如果还设置红绿灯交替，那么很有可能出现四个方向仅有一辆车在等红灯的情况。因此，因此，红绿灯可能被设置为闪黄灯的情况，代表车辆可以自由通过，但是司机需要注意观察。

>> Java 虚拟机也存在着类似的情形：多个线程在不同的时间段请求同一把锁，也就是说没有锁竞争。针对这种情形，Java 虚拟机采用了轻量级锁，来避免重量级锁的阻塞以及唤醒。

>> ##### Java 虚拟机是怎么区分轻量级锁和重量级锁的
![](https://wiki.openjdk.java.net/display/HotSpot/Synchronization?preview=/11829266/12025861/Synchronization.gif)  

>> 当进行加锁操作时，Java 虚拟机会判断是否已经是重量级锁。如果不是，它会在当前线程的当前栈桢中划出一块空间，作为该锁的锁记录，并且将锁对象的标记字段复制到该锁记录中。  

>> 然后，Java 虚拟机会尝试用 CAS（compare-and-swap）操作替换锁对象的标记字段。这里解释一下，CAS 是一个原子操作，它会比较目标地址的值是否和期望值相等，如果相等，则替换为一个新的值。

>> 假设当前锁对象的标记字段为 X…XYZ，Java 虚拟机会比较该字段是否为 X…X01。如果是，则替换为刚才分配的锁记录的地址。由于内存对齐的缘故，它的最后两位为 00。此时，该线程已成功获得这把锁，可以继续执行了。

>> 如果不是 X…X01，那么有两种可能。第一，该线程重复获取同一把锁。此时，Java 虚拟机会将锁记录清零，以代表该锁被重复获取。第二，其他线程持有该锁。此时，Java 虚拟机会将这把锁膨胀为重量级锁，并且阻塞当前线程。

>> 当进行解锁操作时，如果当前锁记录（你可以将一个线程的所有锁记录想象成一个栈结构，每次加锁压入一条锁记录，解锁弹出一条锁记录，当前锁记录指的便是栈顶的锁记录）的值为 0，则代表重复进入同一把锁，直接返回即可。

>> 否则，Java 虚拟机会尝试用 CAS 操作，比较锁对象的标记字段的值是否为当前锁记录的地址。如果是，则替换为锁记录中的值，也就是锁对象原本的标记字段。此时，该线程已经成功释放这把锁。

>> 如果不是，则意味着这把锁已经被膨胀为重量级锁。此时，Java 虚拟机会进入重量级锁的释放过程，唤醒因竞争该锁而被阻塞了的线程。

> **偏向锁**

偏向锁只会在第一次请求时采用 CAS 操作，在锁对象的标记字段中记录下当前线程的地址。在之后的运行过程中，持有该偏向锁的线程的加锁操作将直接返回。它针对的是锁仅会被同一线程持有的情况。
>> 如果说轻量级锁针对的情况很乐观，那么接下来的偏向锁针对的情况则更加乐观：从始至终只有一个线程请求某一把锁。

>> 这就好比你在私家庄园里装了个红绿灯，并且庄园里只有你在开车。偏向锁的做法便是在红绿灯处识别来车的车牌号。如果匹配到你的车牌号，那么直接亮绿灯。

>> 具体来说，在线程进行加锁时，如果该锁对象支持偏向锁，那么 Java 虚拟机会通过 CAS 操作，将当前线程的地址记录在锁对象的标记字段之中，并且将标记字段的最后三位设置为 101。

>> 在接下来的运行过程中，每当有线程请求这把锁，Java 虚拟机只需判断锁对象标记字段中：最后三位是否为 101，是否包含当前线程的地址，以及 epoch 值是否和锁对象的类的 epoch 值相同。如果都满足，那么当前线程持有该偏向锁，可以直接返回。

###### 这里的 epoch 值是一个什么概念呢？

>> 我们先从偏向锁的撤销讲起。当请求加锁的线程和锁对象标记字段保持的线程地址不匹配时（而且 epoch 值相等，如若不等，那么当前线程可以将该锁重偏向至自己），Java 虚拟机需要撤销该偏向锁。这个撤销过程非常麻烦，它要求持有偏向锁的线程到达安全点，再将偏向锁替换成轻量级锁。

>> 如果某一类锁对象的总撤销数超过了一个阈值（对应 Java 虚拟机参数 -XX:BiasedLockingBulkRebiasThreshold，默认为 20），那么 Java 虚拟机会宣布这个类的偏向锁失效。

>> 具体的做法便是在每个类中维护一个 epoch 值，你可以理解为第几代偏向锁。当设置偏向锁时，Java 虚拟机需要将该 epoch 值复制到锁对象的标记字段中。

>> 在宣布某个类的偏向锁失效时，Java 虚拟机实则将该类的 epoch 值加 1，表示之前那一代的偏向锁已经失效。而新设置的偏向锁则需要复制新的 epoch 值。

>> 为了保证当前持有偏向锁并且已加锁的线程不至于因此丢锁，Java 虚拟机需要遍历所有线程的 Java 栈，找出该类已加锁的实例，并且将它们标记字段中的 epoch 值加 1。该操作需要所有线程处于安全点状态。

>> 如果总撤销数超过另一个阈值（对应 Java 虚拟机参数 -XX:BiasedLockingBulkRevokeThreshold，默认值为 40），那么 Java 虚拟机会认为这个类已经不再适合偏向锁。此时，Java 虚拟机会撤销该类实例的偏向锁，并且在之后的加锁过程中直接为该类实例设置轻量级锁。

***
<span id="git">
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
<span id="SpringBoot">
</span>
