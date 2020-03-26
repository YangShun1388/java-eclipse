package com.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/*
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 		
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 */
public class TestChannel {

	@Test
	public void test1() {

		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel inChannel = null;
		FileChannel outChannel =null;

		try {
			inputStream = new FileInputStream("1.txt");
			outputStream = new FileOutputStream("2.txt");

			inChannel = inputStream.getChannel();
			outChannel = outputStream.getChannel();

			ByteBuffer buf = ByteBuffer.allocate(1024);

			while(inChannel.read(buf)!=-1) {
				buf.flip();
				outChannel.write(buf);
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(outChannel !=null) {
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inChannel!=null) {
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!=null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test2() {

		FileChannel inChannel = null;
		FileChannel outChannel =null;

		try {

			inChannel = FileChannel.open(Paths.get("1.txt"),StandardOpenOption.READ);
			outChannel = FileChannel.open(Paths.get("3.txt"), StandardOpenOption.WRITE,
					StandardOpenOption.READ,StandardOpenOption.CREATE);

			MappedByteBuffer inmap = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
			MappedByteBuffer outmap = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

			byte[] dst = new byte[inmap.limit()];
			
			inmap.get(dst);
			outmap.put(dst);

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(outChannel !=null) {
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inChannel!=null) {
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
