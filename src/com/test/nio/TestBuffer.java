package com.test.nio;

import java.nio.ByteBuffer;

import org.junit.Test;
/*
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 * 
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 * 
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 * 
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position : 位置，表示缓冲区中正在操作数据的位置。
 * 
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 * 
 * 0 <= mark <= position <= limit <= capacity
 * 
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class TestBuffer {
	
	@Test
	public void test1() {
		String str= "abcdef";
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		System.out.println("***************allocate()***********");
		int limit1 = buffer.limit();
		int capacity1 = buffer.capacity();
		int position1 = buffer.position();
		System.out.println(limit1+"--"+capacity1+"--"+position1);
		
		buffer.put(str.getBytes());
		System.out.println("***************put()***********");
		int limit2 = buffer.limit();
		int capacity2 = buffer.capacity();
		int position2 = buffer.position();
		System.out.println(limit2+"--"+capacity2+"--"+position2);
		
		buffer.flip();
		System.out.println("***************flip()***********");
		int limit3 = buffer.limit();
		int capacity3 = buffer.capacity();
		int position3 = buffer.position();
		System.out.println(limit3+"--"+capacity3+"--"+position3);
		
		byte[] dst = new byte[buffer.limit()];
		buffer.get(dst, 0, buffer.limit());
		System.out.println("***************get()***********");
		System.out.println(new String(dst,0,dst.length));
		int limit4 = buffer.limit();
		int capacity4 = buffer.capacity();
		int position4 = buffer.position();
		System.out.println(limit4+"--"+capacity4+"--"+position4);
		
		buffer.clear();
		System.out.println("***************clear()***********");
		//System.out.println((char)buffer.get());
		int limit5 = buffer.limit();
		int capacity5 = buffer.capacity();
		int position5 = buffer.position();
		System.out.println(limit5+"--"+capacity5+"--"+position5);
		System.out.println(buffer.isDirect());
	}
}