package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import org.junit.Test;

public class TestBlockNIOClient {
	
	//@Test
	public void client() throws IOException {
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6868));
		
		FileChannel inChannel = FileChannel.open(Paths.get("1.txt"),StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while(inChannel.read(buffer)!=-1) {
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		
		socketChannel.shutdownOutput();
		
		int len=0;
		
		while((len=socketChannel.read(buffer))!=-1) {
			buffer.flip();
			System.out.println(new String(buffer.array(),0,len));
			buffer.clear();
		}
		
		
		
		inChannel.close();
		socketChannel.close();
		
	}
	
	//@Test
	public void client1() throws IOException {
		
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6868));
		
		//sChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Scanner scanner = new Scanner(System.in);
		
		String str = scanner.next();
		buf.put(str.getBytes());
		buf.flip();
		sChannel.write(buf);
		buf.clear();
		
		sChannel.shutdownOutput();
		
		while(sChannel.read(buf)!=-1) {
			buf.flip();
			System.out.println(new String(buf.array(),0,buf.limit()));
			buf.clear();
		};
		
		sChannel.close();
	}
	
	@Test
	public void client2() throws IOException {
		
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6868));
		
		sChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String str = scanner.next();
			buf.put(str.getBytes());
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		
		sChannel.close();
	}
}
