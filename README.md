<h1 id="toc_0">DataBinding详细的使用方法及工作中常遇到的问题(一)</h1>

<h2 id="toc_1">前言</h2>

<p>用DataBinding差不多现经手了三个项目,也用它开发了一年左右,对它的特性以及使用方面有了一些自己的理解,这里面可以和大家一起分享一下,详解DataBinding文档中的知识点,以及工作中可能遇到的一些问题,我每讲一部分都是写一个相应的demo以供参考.</p>

<h2 id="toc_2">DataBinding是什么?</h2>

<p>官网文档地址:<a href="https://developer.android.com/topic/libraries/data-binding/index.html">https://developer.android.com/topic/libraries/data-binding/index.html</a><br/>
翻译文档:自己谷歌搜索,网上已经有,这里就不再翻译<br/>
官方文档的原话是:Data Binding Library to write declarative layouts and minimize the glue code necessary to bind your application logic and layouts,The Data Binding Library offers both flexibility and broad compatibility — it&#39;s a support library,大致的意思就是DataBinding是一个通过胶水代码写声明布局文件并减少绑定应用程序逻辑的一个具有兼容和灵活性的一个支持库.看了之后还是一脸懵逼,不要紧,学知识嘛,慢慢来.</p>

<h2 id="toc_3">如何集成</h2>

<p>集成很简单,在你app module的Gradle下面添加的代码:</p>

<pre><code>android {
    ....
    dataBinding {
        enabled = true
    }
}
</code></pre>

<p>提醒:现在建议大家的AS更新到最新,目前最新稳定版式2.3.3.另外,你们公司如果接受了DataBinding这个还算有点新的东西,那么我建议大家使用JDK1.8,具体的详细介绍,请看官网上面的介绍,<a href="https://developer.android.com/guide/platform/j8-jack.html">https://developer.android.com/guide/platform/j8-jack.html</a>.</p>

<p>AS2.1以上才支持jack,如果你得AS版本比较低的话,可以使用兼容库Retrolambda.目前的稳定版而言,暂时不能使用instant run.<br/>
集成如下:</p>

<pre><code>android {
    defaultConfig {
        ....
        jackOptions {
            enabled true
        }
    }
    ....
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
</code></pre>

<p>note:如果你用的AS3.0的话,就可以不通过jack进行编译,而且可以使用instant run,还加快的编译速度.具体的介绍:<br/>
<a href="http://developers.googleblog.cn/2017/05/android-studio-30-canary-1.html">http://developers.googleblog.cn/2017/05/android-studio-30-canary-1.html</a></p>

<h2 id="toc_4">如何使用</h2>

<p>在工作中,我们经常遇到请求到一个对象,然后对象的信息需要在xml中展示的情况,用它怎么做到吗?比普通的做法有什么优势?<br/>
*  假如我们请求到的是一个学生的实体类,那么我们先创建一个学生类:</p>

<pre><code>public class Student {
    private String name;
    private int age;
    private String headerImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }
}
</code></pre>

<ul>
<li> 这个学生类包括姓名,年龄和头像的url,现在我们模拟一下网络请求:</li>
</ul>

<pre><code> @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        initData();
    }

    private void initData() {
        //模拟网络请求部分,假如请求到了一个学生实体
        Student student = new Student();
        student.setAge(25);
        student.setName(&quot;李宁&quot;);
        student.setHeaderImg(&quot;http://i2.muimg.com/567571/b528af67d68f7597.png&quot;);
    }
</code></pre>

<ul>
<li>下面正常的做法就是拿到xml里面的空间,然后一个一个赋值上去,是不是感觉这样做不仅要findViewById,还要拿到各种控件进行一系列的赋值挺麻烦的,这时候DataBinding的作用就展示出来了,数据绑定,就是要把数据直接绑定到xml上面.下面看我华丽的操作:</li>
</ul>

<h6 id="toc_5">XMl中:</h6>

<pre><code>&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
&lt;layout&gt;

&lt;data&gt;

    &lt;variable
        name=&quot;student&quot;
        type=&quot;com.DataBinding.lsh.databindingdemo.Student&quot; /&gt;
&lt;/data&gt;
    &lt;LinearLayout xmlns:android=&quot;http://schemas.android.com/apk/res/android&quot;
                  xmlns:app=&quot;http://schemas.android.com/apk/res-auto&quot;
                  xmlns:tools=&quot;http://schemas.android.com/tools&quot;
                  android:layout_width=&quot;match_parent&quot;
                  android:layout_height=&quot;match_parent&quot;
                  android:orientation=&quot;vertical&quot;
                  tools:context=&quot;com.DataBinding.lsh.databindingdemo.Demo1.Demo1Activity&quot;&gt;


    &lt;/LinearLayout&gt;
&lt;/layout&gt;
</code></pre>

<p>跟布局的标签换了,换成了layout,其次多了data标签,下面有个一个varible标签,它的作用就是用来声明xml中绑定的变量,这里面的Type一定要写引用的全路径,不过好在现在都可以智能提示,打上一个Student,选择正确的一个,下面我们就要在xml的控件中绑定我们声明变量的字段,你可能会说,咦?student只是声明了,不是还没有给进行赋值吗?现在绑定上去不会报错吗?这个不用担心,它有一系列的默认值.一阵乱敲之后:</p>

<pre><code>&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
&lt;layout&gt;

    &lt;data&gt;

        &lt;variable
            name=&quot;student&quot;
            type=&quot;com.DataBinding.lsh.databindingdemo.Student&quot; /&gt;
    &lt;/data&gt;

    &lt;LinearLayout xmlns:android=&quot;http://schemas.android.com/apk/res/android&quot;
                  xmlns:app=&quot;http://schemas.android.com/apk/res-auto&quot;
                  xmlns:fresco=&quot;http://schemas.android.com/apk/res-auto&quot;
                  xmlns:tools=&quot;http://schemas.android.com/tools&quot;
                  android:layout_width=&quot;match_parent&quot;
                  android:layout_height=&quot;match_parent&quot;
                  android:orientation=&quot;vertical&quot;
                  tools:context=&quot;com.DataBinding.lsh.databindingdemo.Demo1.Demo1Activity&quot;&gt;

        &lt;com.facebook.drawee.view.SimpleDraweeView
            android:layout_width=&quot;60dp&quot;
            android:layout_height=&quot;60dp&quot;
            android:layout_gravity=&quot;center&quot;
            fresco:actualImageScaleType=&quot;fitXY&quot;
            fresco:placeholderImage=&quot;@mipmap/ic_launcher&quot;
            fresco:placeholderImageScaleType=&quot;centerCrop&quot;
            fresco:roundAsCircle=&quot;true&quot; /&gt;

        &lt;TextView
            android:layout_width=&quot;match_parent&quot;
            android:layout_height=&quot;wrap_content&quot;
            android:gravity=&quot;center&quot;
            android:text=&quot;李宁&quot;
            android:textSize=&quot;24sp&quot; /&gt;

        &lt;TextView
            android:layout_width=&quot;match_parent&quot;
            android:layout_height=&quot;wrap_content&quot;
            android:gravity=&quot;center&quot;
            android:text=&quot;25&quot;
            android:textSize=&quot;24sp&quot; /&gt;


    &lt;/LinearLayout&gt;
&lt;/layout&gt;
</code></pre>

<p><img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/72379493.jpg" alt=""/></p>

<p>我这里面用的加载图片的工具是fresco,它很强大,尤其在内存方面,而且还有一集成了一些常用的功能,比如圆形头像,圆角头像,gif播放,等常用的一些功能.</p>

<p><strong>接下来开始绑定:</strong><br/>
<img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/10176861.jpg" alt=""/><br/>
先绑定TextView的属性,写法如上述图片,控件中自带的这些属性一般可以直接绑定,如果想绑定一些没有的属性,就需要自己去指定,下面绑定图片地址展示图片的时候会用到.我们xml中除了图片,文本就绑定好了,那么activity要怎么操作呢?</p>

<h6 id="toc_6">在Activity中:</h6>

<p><img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/68147946.jpg" alt=""/></p>

<ul>
<li>DataBinding通过DataBindingUtil来进行绑定视图,常用的有两个方法,一个是setContentView方法,一个是bind方法,前者一般用着Activity加载视图,后者就是Fragment等.</li>
<li>一个xml对应一个Binding的java文件,这个文件是自动生成的,<strong>如果提示不出来这个类,重启As,如果还是不行,把项目中的build文件夹删除了,重新编译</strong>,满满的都是经验啊.这个类在build下面是可以找到的,路径如下图:<img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/1766341.jpg" alt=""/></li>
<li>最后我们对xml中的变量进行赋值</li>
</ul>

<p><strong>异常</strong><br/>
我们愉快的编译运行,忽然闪退了,看异常:<br/>
<img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/43621228.jpg" alt=""/><br/>
点进去:<br/>
<img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/64324709.jpg" alt=""/><br/>
mboundView2就是我们设置年龄文本,后面的studentAge就是我们设置的年龄,这地方怎么会报错呢?<br/>
想一想,原来是我们student的age是int类型的,但是text属性不能直接设置int类型,那怎么办?<br/>
<strong>在string.xml中</strong></p>

<pre><code>        &lt;TextView
            android:layout_width=&quot;match_parent&quot;
            android:layout_height=&quot;wrap_content&quot;
            android:gravity=&quot;center&quot;
            android:text=&quot;@{@string/intToString(student.age)}&quot;
            android:textSize=&quot;24sp&quot; /&gt;
</code></pre>

<p><strong>在activity_demo1.xml中:</strong></p>

<pre><code>        &lt;TextView
            android:layout_width=&quot;match_parent&quot;
            android:layout_height=&quot;wrap_content&quot;
            android:gravity=&quot;center&quot;
            android:text=&quot;@{@string/intToString(student.age)}&quot;
            android:textSize=&quot;24sp&quot; /&gt;
</code></pre>

<p>其他的类型的转化就可以参照这个去改.</p>

<p><strong>绑定图片</strong></p>

<pre><code>public class ViewBindAdapter {
    @BindingAdapter({&quot;bind:url&quot;})
    public static void setImgUrl(SimpleDraweeView imageView, String uri) {
        if (!TextUtils.isEmpty(uri)) {
            imageView.setImageURI(Uri.parse(uri));
        }
    }
}
</code></pre>

<p>xml中:</p>

<pre><code>        &lt;com.facebook.drawee.view.SimpleDraweeView
            app:url=&quot;@{student.headerImg}&quot;
            android:layout_width=&quot;60dp&quot;
            android:layout_height=&quot;60dp&quot;
            android:layout_gravity=&quot;center&quot;
            fresco:actualImageScaleType=&quot;fitXY&quot;
            fresco:placeholderImage=&quot;@mipmap/zhanwei&quot;
            fresco:placeholderImageScaleType=&quot;centerCrop&quot;
            fresco:roundAsCircle=&quot;true&quot; /&gt;
</code></pre>

<p>我们新建一个类,专门存放绑定的自定义属性,@BindingAdapter({&quot;bind:url&quot;})这句主要是声明xml中写的自定义的属性,可以连续写多个,比如这种:</p>

<pre><code>  @BindingAdapter(value = {&quot;uri&quot;, &quot;placeholderImageRes&quot;, &quot;request_width&quot;,&quot;request_height&quot;}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes,
                                 int width, int height){
                                 }
</code></pre>

<p>requireAll = false的意思是不用写全所有参数,方法的第一个参数是控件的本身,就是这么简单,这样就可以绑定成功了.</p>

<p>note:注意app:url=&quot;@{student.headerImg}&quot;这个没有智能提示,代码颜色也没有改变,都是正常的,只要写对就行.</p>

<p>最新来一张图:<br/>
<img src="http://opgkgu3ek.bkt.clouddn.com/17-6-16/1186068.jpg" alt=""/><br/>
到这里,这些基本的操作就完成了,你已经对databinding有了一个大体的认识,接下来的几篇,将会更加深入的介绍它的使用方法和底层的原理.<br/>
demo地址:<a href="https://github.com/adonis-lsh/DataBindingDemo">https://github.com/adonis-lsh/DataBindingDemo</a></p>
