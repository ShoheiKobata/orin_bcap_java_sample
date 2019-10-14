# orin_bcap_java_sample

Samples to control RC8 using the b-cap library

## Usage

create a file 'Makefile.bat' for bcapcilent class.

`path = .\orin_bcap_java_sample\SimpleSamples\bCAPClient\Src\Makefile.bat`

~~~ Makefile.bat
echo off
rem =====Change to your environment
set JAVAROOT=C:\Program Files\Java\jdk1.7.0_79
set bcapROOT=D:\orin_bcap_java_sample
rem ===== change to your environment
cd /d "%bcapROOT%\SimpleSamples\bCAPClient\Src"
rem =====Build=====
"%JAVAROOT%\Bin\javac.exe" -d ..\Bin orin2\library\ConnOptParser.java orin2\library\HResult.java orin2\library\ORiN2Exception.java orin2\bcap\BCAPByteConverter.java orin2\bcap\BCAPClient.java orin2\bcap\BCAPConnectionBase.java orin2\bcap\BCAPConnectionTCP.java orin2\bcap\BCAPConnectionUDP.java orin2\bcap\BCAPDefine.java orin2\bcap\BCAPPacket.java orin2\bcap\BCAPPacketConverter.java
rem ===============
cd /d "%bcapROOT%\SimpleSamples\bCAPClient\Bin"
rem =====Make jar file=====
"%JAVAROOT%\Bin\jar.exe" cvf jbCAPClient.jar .\
rem =======================
~~~

create a file 'Makefile.bat' for programs slass.

`path = .\orin_bcap_java_sample\SimpleSamples\Makefile.bat`

~~~Makefile.bat
echo off
rem =====Chenge to your environment
set JAVAROOT=C:\Program Files\Java\jdk1.7.0_79
set bcapROOT=D:\orin_bcap_java_sample
rem =====Chenge to your environment
cd /d "%bcapROOT%\SimpleSamples"
rem =====Build=====
"%JAVAROOT%\Bin\javac.exe" -cp .;%bcapROOT%\SimpleSamples\bCAPClient\Bin\jbCAPClient.jar IOControl.java
rem ===============
~~~

program execution

~~~cmd
cd /d path_to_samples
java -cp .;bCAPClient\Bin\jbCAPClient.jar IOControl
~~~

EOF
