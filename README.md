# ETOOL4K Project

ETOOL4K (Open-EDGN's Toolbox for Kotlin) 是一個新穎且 100% 源代碼開放的輕量級 `Kotlin` 模塊。

![](https://img.shields.io/badge/LICENSE-MIT-green.svg) ![](https://img.shields.io/badge/CODE-Kotlin-green.svg)  ![](https://img.shields.io/badge/BUILD-Gradle-green.svg) [![](https://jitpack.io/v/OPEN-EDGN/ETOOL4K.svg)](https://jitpack.io/#OPEN-EDGN/ETOOL4K)

## 入门

此项目为多个子模块组成的合辑，各个模块之间不存在任何引用，可同时引用不同版本的子模块。

注意：此项目针对 `Kotlin` 的语法特性做了一些调整，对于`Java`可能出现异常；

如有发现漏洞或者意见可在项目`Issues` 下反馈.

### 开始之前

> 在开始之前，你需要将模块引入到项目中，下面介绍了`Gradle` 和`Apache Maven` 的引入方法

#### Maven & Gradle

1. 将JitPack存储库添加到您的构建文件中

Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Gradle

```groovy
allprojects {
	repositories {
        //其他仓库 
		maven { url 'https://jitpack.io' }
	}
}
```

2. 添加依赖项

Maven

```xml
<dependency>
    <groupId>com.github.OPEN-EDGN.ETOOL4K</groupId>
    <artifactId>${modName}</artifactId>
    <version>${version}</version>
</dependency>
```

Gradle

```groovy
dependencies {
    implementation 'com.github.OPEN-EDGN.ETOOL4K:${modName}:${version}'
}
```

> 其中，`${modName}` 代表 引用的模块名称，而 `${version}` 则代表引用的版本号，请按需引入。


### 使用方法
1. [Logger4k Project](/logger4k/README.md)
