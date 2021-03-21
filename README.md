# <img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/logo/Logo.png" width="10%"/> CloudSimSEC

[![](https://img.shields.io/badge/Powered%20By-CloudSimSEC-green)](https://github.com/HayaKus/CloudSimSEC)
![](https://img.shields.io/badge/release-v1.0-blue)
![](https://img.shields.io/badge/language-Java%20%7C%20HTML-orange)
[![](https://img.shields.io/badge/platform-windows-orange)](https://www.microsoft.com/windows/)
[![](https://img.shields.io/badge/license-MIT-orange)](/LICENSE)

The document is explained in both English and Chinese, and the bold part is a Chinese translation.  
**该文件同时使用英文和中文进行说明，其中加粗部分为中文翻译。**

If you found it helpful to you, please give me a star and recommend it to your friends around you.(●'◡'●)  
**如果您觉得软件对您有所帮助，点一下右上角的star并推荐给周围的朋友就是对我极大的支持。(●'◡'●)**

## Table of Contents 内容列表

- [Update 更新](#update-更新)
- [Background 背景](#background-背景)
- [Download/Install 下载/安装](#downloadinstall-下载安装)
- [Usage 使用说明](#usage-使用说明)
	- [1. the parameters of data center 数据中心的参数](#1-the-parameters-of-data-center-数据中心的参数)
	- [2. the parameters of physical hosts 物理主机的参数](#2-the-parameters-of-physical-hosts-物理主机的参数)
	- [3. the parameters of virtual machines 虚拟机的参数](#3-the-parameters-of-virtual-machines-虚拟机的参数)
	- [4. the parameters of cloud tasks 云任务的参数](#4-the-parameters-of-cloud-tasks-云任务的参数)
	- [5. the parameters of users 用户的参数](#5-the-parameters-of-users-用户的参数)
	- [6. other components 其他组件](#6-other-components-其他组件)
- [Result 结果说明](#result-结果说明)
- [Related Efforts 相关仓库](#related-efforts-相关仓库)
- [Maintainers 维护者](#maintainers-维护者)
- [Contributing 如何贡献](#contributing-如何贡献)
	- [Contributors 贡献者](#contributors-贡献者)
- [License 使用许可](#license-使用许可)

## Update 更新 （建议使用分支“qosEvaluation”中的版本） 
### 2020.12.18 重写分支qosEvaluation的用户界面
- 分支“qosEvaluation”的CloudSimSEC的中文名称修改为“服务评价与能耗预测仿真平台”
- 重写了分支“qosEvaluation”的CloudSimSEC的用户界面（原来黑色的那个被吐槽太丑了（′▽｀））
- 导入了UI界面的jar包（根目录下的文件“beautyeye_lnf.jar”）

<div align=center><img src="https://github.com/HayaKus/CloudSimSEC/blob/qosEvaluation/graphs/usage/newUI.png" width="100%"/></div>

### 2020.11.18 新建分支qosEvaluation
- 初始页面新增“QoS评估”页面，可以设置基于QoS感知区域评估的相关参数
- 结果页面的“首页”页面中新增“基于QoS感知区域评估的相关参数”一栏
- 结果页面的“混合对比”页面、“RR”页面、“DVFS”页面、“ST”页面中“所有物理主机的平均利用率”修改为“所有物理服务器的平均评估值”
- 结果页面的“混合对比”页面、“RR”页面、“DVFS”页面、“ST”页面中特定服务器的“利用率”修改为特定服务器的“评估值”
- 关于基于QoS感知区域的评估算法，可参考该篇论文：[Dynamic service migration in ultra-dense multi-access edge computing network for high-mobility scenarios](https://jwcn-eurasipjournals.springeropen.com/articles/10.1186/s13638-020-01805-2)


## Background 背景

There are more and more traffic demands of cloud data centers in recent years, leading to the continuous establishment and expansion of cloud data centers around the world. Although their economic utility is increasing, huge energy consumption has also received more and more attention. The problem of energy consumption in cloud data centers has changed from past distribution to present centralization, because cloud computing can achieve the flexibility and scalability of computing resources (such as networks, storage, applications, and services). In order to optimize the energy consumption of the cloud data center, it is necessary to establish an energy consumption simulator for the cloud data center.  
**近年来，云数据中心面临着越来越多的流量需求，导致世界各地的云数据中心不断地组建和扩张。虽然其经济效用日益增加，但巨大的能耗也受到了越来越多的关注。由于云计算可以实现计算资源（例如网络，存储，应用和服务）的灵活性和可扩展性，因此云数据中心能耗问题从过去的分散方式变为现在以集中方式凸显。为了对云数据中心的能耗使用情况进行优化，针对云数据中心建立一个能耗模拟器是十分必要的。**

Original CloudSim goal is to provide a generalized and extensible simulation framework that enables modeling, simulation, and experimentation of emerging Cloud computing infrastructures and application services, allowing its users to focus on specific system design issues that they want to investigate, without getting concerned about the low level details related to Cloud-based infrastructures and services.  
**原版CloudSim的目标是提供一个通用且可扩展的仿真框架，该框架能够对新兴的云计算基础架构和应用程序服务进行建模、仿真和实验，从而使其用户能够专注于他们想调查的特定系统设计问题，而不必担心基于云的基础架构和服务相关的底层问题。**

Original CloudSim is developed in [the Cloud Computing and Distributed Systems (CLOUDS) Laboratory](http://cloudbus.org/), at [the Computer Science and Software Engineering Department](http://www.csse.unimelb.edu.au/) of [the University of Melbourne](http://www.unimelb.edu.au/).  
**原版CloudSim由[墨尔本大学](http://www.unimelb.edu.au/)的[计算机科学与软件工程系](http://www.csse.unimelb.edu.au/)的[开发云计算与分布式系统（云）实验室](http://cloudbus.org/)开发。**

More information can be found on the [CloudSim's web site](http://cloudbus.org/cloudsim/).  
**您可以在[CloudSim的官方网站](http://cloudbus.org/cloudsim/)上查找到更多信息。**

CloudSimSEC is an extension of Cloudsim specifically designed to simulate the energy consumption of cloud data centers. There are main features of CloudSimSEC, i.e.,  
**CloudSimSEC是一款专门用于模拟云数据中心能耗Cloudsim的延伸作品。CloudSimSEC的主要特点为：**

- support for simulating energy consumption of cloud data centers via RBF neural network
- **能够通过RBF神经网络模拟云数据中心的能耗**
- support for simulating different energy consumptions of cloud data centers by replacing different models
- **能够通过替换不同的模型来模拟不同云数据中心的能耗**
- support for freely configurating the hardware and tasks in cloud data centers
- **能够自由配置云数据中心中的硬件和任务**
- support for simulating energy consumption via user-friendly graphical user interface (GUI)
- **能够通过对用户友好的图形化用户界面（GUI）来进行能耗模拟**
- support for changing the independent variable interfaces of various models of energy consumption, which can be used to increase or decrease the input variables of models of energy consumption
- **支持更改各种能耗模型的自变量接口，可用于增加或减少能耗模型的输入变量**

## Download/Install 下载/安装

Download install pack (539MB): [CloudSimSEC__setup.exe](https://twocups.cn/download/CloudSimSEC__setup.exe) (Note: This is the installation package of the branch 'master'.)  
**下载安装包（539MB）：[CloudSimSEC__setup.exe](https://twocups.cn/download/CloudSimSEC__setup.exe)**（务必注意：这是主分支的安装包）

Note: Do not install this software in the "C://", otherwise there will be a problem that the user does not have permission to open this software.  
**注意：不要把这个软件安装在C盘，否则会出现用户没有权限打开软件的问题。**  

In addition, there are some environments that must be installed locally, i.e.,  
**此外，还有以下环境必须被安装：**

- beautyeye_lnf.jar (The file 'beautyeye_lnf.jar' has been placed in the source file.)
- jdk1.8.0_201 (The file 'jdk1.8.0_201' has been placed in the source file.)
- MATLAB R2017b (More information can be found on the [MATLAB's web site](https://www.mathworks.com/).)  

Or you can also run directly the source code: The source file does not need to be installed, it can be opened directly with an editor that supports java, such as [IDEA](http://www.jetbrains.com/idea/).  
**或者您也可以直接运行源代码：源文件不需要被安装，可以使用支持Java的编辑器直接打开它，例如 [IDEA](http://www.jetbrains.com/idea/)。**

## Usage 使用说明

Install pack: After the software is installed, click the 'CloudSimSEC' file on the desktop to open the software.  
**安装包：安装完成之后，点击桌面的'CloudSimSEC'文件即可打开软件。**

Source code: Run '/PowerForecast/main/Main.java' through a compiler (e.g., [IDEA](http://www.jetbrains.com/idea/)) to open the software.  
**源代码：通过编译器（例如 [IDEA](http://www.jetbrains.com/idea/)）运行'/PowerForecast/main/Main.java'即可打开软件。**

<div align=center><img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/usage/CloudSimSEC%E9%A6%96%E9%A1%B5.png" width="80%"/></div>

The parameters that can be set in the cloud data centers are mainly divided into five parts, i.e.,  
**云数据中心里可以被设置的参数主要被分为五个部分，即**

### 1. the parameters of data center 数据中心的参数

- "数据中心名称" is the name of the data center.
- **"数据中心名称"是数据中心的名称。**
- "IT系统能耗模型" is the energy consumption model of the IT system in the cloud data center.
- **"IT系统能耗模型"是云数据中心中IT系统的能耗模型。**
- "IT维护系统能耗模型" is the energy consumption model of the system for maintaining IT system operation in the cloud data center.
- **"IT维护系统能耗模型"是云数据中心中用于维护IT系统正常运行的系统的能耗模型。**
- "IT维护系统最大功耗（W）" is the maximum power consumption of the system for maintaining IT system operation in the cloud data center.
- **"IT维护系统最大功耗（W）"是云数据中心中用于维护IT系统正常运行的系统的最大功耗。**
- "消防系统功耗（W）" is the power consumption of the fire fighting linkage control system in the cloud data center.
- **"消防系统功耗（W）"是云数据中心中消防系统的功耗。**
- "基础设施系统功耗（W）" is the power consumption of the infrastructure system in the cloud data center.
- **"基础设施系统功耗（W）"是云数据中心中基础设置系统的功耗。**
- "模拟间隔（s）" is the time interval of the simulator.
- **"模拟间隔（s）"是模拟器每次模拟的时间间隔。**

### 2. the parameters of physical hosts 物理主机的参数

- "主机+number" represents different types of physical hosts.
- **"主机+数字"代表了不同的物理主机种类。**
- "数量" is the number of each physical host.
- **"数量"是每种物理主机的数量。**
- "运算能力（MIPS）" is the computing power of each physical host.
- **"运算能力（MIPS）"是每种物理主机的运算能力。**
- "空闲功耗（W）" is the power consumption of each physical host during idle.
- **"空闲功耗（W）"是每台物理主机在空闲时的功耗。**
- "最大功耗（W）" is the maximum power consumption of each physical host.
- **"最大功耗（W）"是每台物理主机的最大功耗。**
- "内存（MB）" is the memory of each physical host.
- **"内存（MB）"是每台物理主机的内存。**
- "带宽（bit/s）" is the bandwidth of each physical host.
- **"带宽（bit/s）"是每台物理主机的带宽。**
- "存储（GB）" is the storage of each physical host.
- **"存储（GB）"是每台物理主机的存储。**

### 3. the parameters of virtual machines 虚拟机的参数

- "虚拟机+number" represents different types of virtual machines.
- **"虚拟机+数字"代表了不同的虚拟机种类。**
- "运算能力（MIPS）" is the computing power of each virtual machine.
- **"运算能力（MIPS）"是每种虚拟机的运算能力。**
- "内存（MB）" is the memory of each virtual machine.
- **"内存（MB）"是每台虚拟机的内存。**
- "带宽（bit/s）" is the bandwidth of each virtual machine.
- **"带宽（bit/s）"是每台虚拟机的带宽。**
- "存储（GB）" is the storage of each virtual machine.
- **"存储（GB）"是每台虚拟机的存储。**

### 4. the parameters of cloud tasks 云任务的参数

- "云任务+number" represents different types of cloud tasks.
- **"云任务+数字"代表了不同的云任务种类。**
- "云任务量（MI）" is the calculation of the cloud task.
- **"云任务量（MI）"是云任务的计算量。**
- "文件数量" is the number of files for the cloud task.
- **"文件数量"是云任务文件的数量。**
- "输出文件数量" is the number of output files for the cloud task.
- **"输出文件数量"是云任务输出文件的数量。**

### 5. the parameters of users 用户的参数

- "用户数量" is the number of person/people using this cloud data center.
- **"用户数量"是正在使用该云数据中心的人的数量。**
- "虚拟机+number" represents different types of virtual machines.
- **"虚拟机+数字"代表了不同的虚拟机种类。**
- "虚拟机数量" is the number of each virtual machine.
- **"数量"是每种虚拟机的数量。**
- "云任务种类（名称）" is the types of cloud tasks running on their virtual machine, which is set in the "云任务" part.
- **"云任务种类（名称）"是运行在虚拟机上云任务的种类，这可以在"云任务"部分设置。**

### 6. other components 其他组件

- The button "训练模型" records the method for modeling RBF neural networks using MATLAB.
- **"训练模型"按钮记录了使用MATLAB对数据进行RBF神经网络建模的方法。**
- The button "保存结果" helps users to store the simulation results in a local folder.
- **"保存结果"按钮帮助用户把模拟结果存放到本地文件夹中。**
- The button "帮助" records the author's [contact information](https://github.com/HayaKus).
- **"帮助"按钮记录了作者的[联系方式](https://github.com/HayaKus)。**
- The button "清空" clears all the default parameters in the simulator.
- **"清空"按钮会清空模拟器中所有默认的参数。**
- The button "重置" resets all parameters in the simulator to the default parameters.
- **"重置"按钮会将模拟器中所有的参数重置为默认参数。**
- The button "生成结果" collects all parameters and starts to simulate energy consumption.
- **"生成结果"按钮会收集所有的参数并开始模拟能耗。**

## Result 结果说明

The homepage will show the comparison of the energy consumption of the cloud data center without the scheduling algorithm and with RR algorithm, DVFS algorithm, SR algorithm. And some system information of simulation will be shown.  
**主页中会显示云数据中心在没有调度算法、只有RR算法、只有DVFS算法和只有SR算法的情况下的能耗对比情况和一些模拟出的系统信息。**

<div align=center><img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/result/CloudSimSEC%E6%80%BB%E8%83%BD%E8%80%97%E5%AF%B9%E6%AF%94.jpg" width="80%"/></div>

In addition, the average power and average utilization of all physical hosts in the above four cases will be shown.  
**并且，以上四种情况下所有物理主机的平均功率和平均利用率都会被显示。**

<div align=center><img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/result/CloudSimSEC%E6%B7%B7%E5%90%88%E5%AF%B9%E6%AF%94.jpg" width="80%"/></div>

Moreover, the average power and average utilization of every physical host in the above four cases will be shown.  
**此外，以上四种情况下每一台物理主机的平均功率和平均利用率都会被显示。**

<div align=center><img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/result/CloudSimSEC%E6%AF%8F%E5%8F%B0%E4%B8%BB%E6%9C%BA%E6%B7%B7%E5%90%88%E5%AF%B9%E6%AF%94.jpg" width="80%"/></div>


## Related Efforts 相关仓库

A web version of CloudSimSEC will be launched soon.  
**近期会推出网页版的CloudSimSEC，敬请期待。**

## Maintainers 维护者

<img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/logo/HayaKus.png" width="5%"/> [林皓伟](https://github.com/HayaKus)  

**Website: [twocups.cn](https://twocups.cn)**  

**Gmail：haya.haowei.lin@gmail.com**

**E-Mail: haya.haowei.lin@qq.com**  


## Contributing 如何贡献

Feel free to dive in! [Open an issue](https://github.com/HayaKus/CloudSimSEC/issues/new) or submit PRs.  
**非常欢迎你的加入! [提一个Issue](https://github.com/HayaKus/CloudSimSEC/issues/new) 或者提交一个 Pull Request.**

### Contributors 贡献者

Thanks goes to these wonderful people:  
**感谢以下参与项目的人：**  

<img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/logo/HayaKus.png" width="5%"/> [林皓伟](https://github.com/HayaKus)  

<img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/logo/lampic.png" width="5%"/> [张诗陶](https://github.com/lampic) 

<img src="https://github.com/HayaKus/CloudSimSEC/blob/master/graphs/logo/StVak.png" width="5%"/> [StVak](https://github.com/StVak)

## License 使用许可

[MIT](LICENSE) © 林皓伟
