package com.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestBlockNIOServer {


	//@Test
	public void server() throws IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.bind(new InetSocketAddress(6868));

		SocketChannel sChannel = ssChannel.accept();

		FileChannel outChannel = FileChannel.open(Paths.get("4.txt"),StandardOpenOption.WRITE,
				StandardOpenOption.READ,StandardOpenOption.CREATE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		while(sChannel.read(buffer)!=-1) {
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		};

		buffer.put("服务器接受到了数据".getBytes());
		buffer.flip();
		sChannel.write(buffer);

		outChannel.close();
		sChannel.close();
		ssChannel.close();

	}

	//@Test
	public void server1() throws IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.bind(new InetSocketAddress(6868));
		while(true) {
			SocketChannel sChannel = ssChannel.accept();

			ByteBuffer buffer = ByteBuffer.allocate(1024);

			while(sChannel.read(buffer)!=-1) {
				buffer.flip();
				System.out.println(new String(buffer.array(),0,buffer.limit()));
				buffer.clear();
			};

			Scanner scanner = new Scanner(System.in);
			String str = scanner.next();
			buffer.put(str.getBytes());
			buffer.flip();
			sChannel.write(buffer);

			sChannel.shutdownInput();
		}
	}

	@Test
	public void server2() throws IOException {
		
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		ssChannel.configureBlocking(false);
		
		ssChannel.bind(new InetSocketAddress(6868));
		
		Selector selector = Selector.open();
		
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select()>0) {
			
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			
			while(iterator.hasNext()) {
				
				SelectionKey sk = iterator.next();
				
				if(sk.isAcceptable()) {
					
					SocketChannel sChannel = ssChannel.accept();
					
					sChannel.configureBlocking(false);
					
					sChannel.register(selector, SelectionKey.OP_READ);
					
				}else if(sk.isReadable()) {
					SocketChannel sChannel = (SocketChannel) sk.channel();
					
					ByteBuffer buf = ByteBuffer.allocate(1024);
					
					int len = 0;
					while((len = sChannel.read(buf)) > 0 ){
						buf.flip();
						System.out.println(new String(buf.array(), 0, len));
						buf.clear();
					}
				}
				
				iterator.remove();
			}
			
			
		}

		

	}
}
