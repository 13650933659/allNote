

1、字节流
	/**
	 * 以字节码的方式拷贝文件
	 * @param sourcePath	原文件全路径
	 * @param targetPath	目标文件全路径
	 * @param append		是否在目标文件追加内容
	 */
	public static void copy_ByteStream(String sourcePath, String targetPath, boolean append) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(sourcePath);
			fos = new FileOutputStream(targetPath, append);
			
			int total = 0;
			int len = 0;
			byte[] arr = new byte[1024];
			while((len = fis.read(arr)) != -1){
				fos.write(arr, 0, len);
				total += len;
			}
			System.out.println(String.format("%s文件大小为：%s字节。", sourcePath, total));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

2、字符流
	/**
	 * 以字符码的方式拷贝文件
	 * @param sourcePath	原文件全路径
	 * @param targetPath	目标文件全路径
	 * @param append		是否在目标文件追加内容
	 */
	public static void copy_CharStream(String sourcePath, String targetPath, boolean append) {
		FileReader fr = null;
		FileWriter fw = null;
		
		try {
			fr = new FileReader(sourcePath);
			fw = new FileWriter(targetPath, append);
			
			int total = 0;
			int len = 0;
			char[] arr = new char[1024];
			while((len = fr.read(arr)) != -1){
				fw.write(arr, 0, len);
				total += len;
			}
			System.out.println(String.format("%s文件大小为：%s字符。", sourcePath, total));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
3、缓冲字符流
	/**
	 * 以缓冲字符码的方式拷贝文件（关流时记得从大到小）
	 * @param sourcePath	原文件全路径
	 * @param targetPath	目标文件全路径
	 * @param append		是否在目标文件追加内容
	 */
	public static void copy_BufferedCharStream(String sourcePath, String targetPath, boolean append) {
		FileReader fr = null;
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(sourcePath);
			fw = new FileWriter(targetPath, append);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
			
			int total = 0;
			String str = "";
			while((str = br.readLine()) != null){
				bw.write(str);
				bw.newLine();
				total++;
			}
			System.out.println(String.format("%s文件大小为：%s行。", sourcePath, total));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
				if (fr != null) {
					fr.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


4、对象流(对象需要继承Serializable接口，使用关键字transient标识不需要被序列号的属性，不知道需不需要set\get方法)
	try{
		//连接《127.0.0.1:9988》服务器
		socket = new Socket("127.0.0.1",9988);

		//阻塞等待，服务器回应的《m》对象
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		message = (Message)objectInputStream.readObject();
		//把对象《ui》，发送给服务器
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.writeObject(ui);
	}catch(Exception e){
		e.printStackTrace();
	}finally{//这里没关，不知道为什么}

	//QQ server和client传输字串的列子,这个很少用了，以后再看吧
	try{
		//连接《127.0.0.1:9988》服务器
		socket = new Socket("127.0.0.1",9988);
				
		//定义用来得到客户发来信息的工具
		InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
		bufferedReader=new BufferedReader(inputStreamReader);
				
		//定义发送工具
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		//等客户按下发送就执行  pw.println(requestInfo);
				
		//循环的等待服务器回应的消息，并附加到jsp_jta
		while(true){
			respondInfo=br1.readLine();//阻塞等待服务器回应的消息
			jsp_jta.append("服务器   对  用户 说：  "+respondInfo+"\r\n");
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{//这里没关流，不知道为什么}


5、递归查找指定的文件夹所有的子文件夹和子文件
	/**
	 * 递归查找指定的文件夹所有的子文件夹和子文件
	 * @param f		指定的文件夹
	 * @param depth	当前是第几层
	 */
	public static void recursiveFindFile(File f, int depth) {
		if (f == null) {
			throw new NullPointerException("参数不能为空");
		}
		
		String str = "";
		for(int i = 0; i < depth; i++){
			str += "	";
		}
		String fileName = f.getName();
		if (f.isDirectory()) {
			System.out.println(str + fileName);
			
			File[] children = f.listFiles();
			int len = children.length;
			for (int i = 0; i < len; i++) {
				recursiveFindFile(children[i], depth + 1);
			}
		} else {
			System.out.println(str + fileName);
		}
	}


