






1、Lucene是什么：Lucene是一个高效的，基于Java的全文检索库;开源免费
	1、Lucene官方网站：http://lucene.apache.org/
	2、学习的版本：lucene4.10.3
	3、杂项
		1、数据的分类
			1、结构化数据:有固定类型或者有固定长度的数据，例如:数据库中的数据(mysql,oracle等), 元数据(就是windows中的数据)
				1、结构化数据搜索方法:数据库中数据通过sql语句可以搜索，元数据(windows中的)通过windows提供的搜索栏进行搜索
			2、非结构化数据:没有固定类型和固定长度的数据，例如: world文档中的数据, 邮件中的数据
				1、Ctrl+F顺序扫描法: 中是使用的顺序扫描法,拿到搜索的关键字,去文档中,逐字匹配,直到找到和关键字一致的内容为止.
					优点: 如果文档中存在要找的关键字就一定能找到想要的内容
					缺点: 慢, 效率低
				2、全文检索算法(倒排索引算法):将文件中的内容拆成词(去掉停用词a, an, the ,的, 地, 得, 啊, 嗯 ,呵呵,标点符号,空格,等没有意义的词，然后合并相同词), 将这些词组成索引
					优点: 搜索速度快
					缺点: 因为创建的索引需要占用磁盘空间,所以这个算法会使用掉更多的磁盘空间,这是用空间换时间
		2、Lucene应用领域
			1、互联网全文检索引擎(比如百度,  谷歌,  必应)
			2、站内全文检索引擎(淘宝, 京东搜索功能)
			3、优化数据库查询(因为数据库中使用like关键字是全表扫描也就是顺序扫描算法,查询慢)
2、使用4.3的版本
	1、需要的依赖
			luceneHome/core/lucene-core-4.10.3.jar							// lucene核心包
			luceneHome/analysis/common/lucene-analyzers-common-4.10.3.jar	// 分词包
			luceneHome/queryparser/lucene-queryparser-4.10.3.jar			// 查询解析
			junit依赖														// junit依赖
			commons-io.jar													// io流操作
	2、分词器
		1、CJK中日韩分词器(二分法)
		2、IK分词器(支持中文比较好)
			1、需要的依赖
				IKHome/IKAnalyzer2012FF_u1.jar
			2、需要的配置文件
				1、ext.dic				// 扩展词典，每一个词是一行，不能有空格 比如：编程思想，会分成：编程、思想、编程思想 吗？还是说只有一个 编程思想
				2、stopword.dic			// 停用词典，每一个词是一行
				3、IKAnalyzer.cfg.xml	// IK主配置文件
					<?xml version="1.0" encoding="UTF-8"?>
					<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">  
					<properties>  
						<comment>IK Analyzer 扩展配置</comment>
						<!--用户可以在这里配置自己的扩展字典 -->
						<entry key="ext_dict">ext.dic;</entry> 
						<!--用户可以在这里配置自己的扩展停止词字典-->
						<entry key="ext_stopwords">stopword.dic;</entry> 
					</properties>
	3、增删改查
		1、创建文档(分词的时候也就是创建索引的时候)
			1、java代码
				@Test
				public void testIndexCreate() throws Exception{
					//创建文档列表,保存多个Docuemnt
					List<Document> docList = new ArrayList<Document>();
					
					//指定文件所在目录
					File dir = new File("E:\\01传智课程\\黑28期0523\\Lucene&Solr\\01.参考资料\\searchsource"); 
					//循环文件夹取出文件
					for(File file : dir.listFiles()){
						//文件名称
						String fileName = file.getName();
						//文件内容
						String fileContext = FileUtils.readFileToString(file);
						//文件大小
						Long fileSize = FileUtils.sizeOf(file);
						
						//文档对象,文件系统中的一个文件就是一个Docuemnt对象
						Document doc = new Document();
						
						//第一个参数:域名
						//第二个参数:域值
						//第三个参数:是否存储,是为yes,不存储为no

						// 下面三个是优化好的TextField
						//是否分词:要,因为它要索引,并且它不是一个整体,分词有意义
						//是否索引:要,因为要通过它来进行搜索
						//是否存储:要,因为要直接在页面上显示
						TextField nameFiled = new TextField("fileName", fileName, Store.YES);
						//是否分词: 要,因为要根据内容进行搜索,并且它分词有意义
						//是否索引: 要,因为要根据它进行搜索
						//是否存储: 可以要也可以不要,不存储搜索完内容就提取不出来
						TextField contextFiled = new TextField("fileContext", fileContext, Store.NO);
						//是否分词: 要, 因为数字要对比,搜索文档的时候可以搜大小, lunene内部对数字进行了分词算法
						//是否索引: 要, 因为要根据大小进行搜索
						//是否存储: 要, 因为要显示文档大小
						LongField sizeFiled = new LongField("fileSize", fileSize, Store.YES);
						
						//将所有的域都存入文档中
						doc.add(nameFiled);
						doc.add(contextFiled);
						doc.add(sizeFiled);
						
						//将文档存入文档集合中
						docList.add(doc);
					}
					
					//创建分词器,StandardAnalyzer标准分词器,标准分词器对英文分词效果很好,对中文是单字分词
					Analyzer analyzer = new IKAnalyzer();
					//指定索引和文档存储的目录Directory是lucene的
					Directory directory = FSDirectory.open(new File("E:\\dic"));
					//创建写对象的初始化对象
					IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
					// 创建索引和文档写对象，这个很重要
					IndexWriter indexWriter = new IndexWriter(directory, config);
					
					//将文档加入到索引和文档的写对象中
					for(Document doc : docList){
						indexWriter.addDocument(doc);
					}
					//提交
					indexWriter.commit();
					//关闭流
					indexWriter.close();
				}
			2、luke的使用(界面的其他使用自己看)
				1、解压到C:\Software\OpenSource\luke
				2、cd到此目录使用：java -jar lukeall-4.10.3.jar 运行他就可以观察我们的 分词索引库了
				3、概念理解
					索引：域名:词  这样的形式
					索引库: 放索引的文件夹(这个文件夹可以自己随意创建,在里面放索引就是索引库)
					Term词元: 就是一个词, 是lucene中词的最小单位
		2、检索文档
				@Test
				public void testIndexSearch() throws Exception{
					/**
					 *	1、创建Query对象（6种构建查询条件的）
					 */
					// 1.1、创建查询对象，这个只能查文本
					// 第一个参数:如果搜索语法中指定域名从指定域中搜索,如果搜索时只写了查询关键字,则从默认搜索域中进行搜索, 第二个参数:分词器(创建索引和所有时所用的分词器必须一致)
					QueryParser queryParser = new QueryParser("fileContext", new IKAnalyzer());
					//查询语法=域名:搜索的关键字
					Query query = queryParser.parse("fileName:web");
					


					// 1.2、创建词元:就是词  
					Term term = new Term("fileName", "apache");
					TermQuery query = new TermQuery(term);


					// 1.3、根据数字范围查询
					//第一个参数:域名 第二个参数:最小值,  第三个参数:最大值, 第四个参数:是否包含最小值,   第五个参数:是否包含最大值
					Query query = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);

					// 1.4、布尔查询，可以组合多个Query进行查询
					// 需求文件名称包含apache的,并且文件大小大于等于100 小于等于1000字节的文章
					BooleanQuery query = new BooleanQuery();
					
					Query numericQuery = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);
					TermQuery termQuery = new TermQuery(new Term("fileName", "apache"));
					
					//Occur是逻辑条件
					//must相当于and关键字,是并且的意思
					//should,相当于or关键字或者的意思
					//must_not相当于not关键字, 非的意思
					//注意:单独使用must_not  或者 独自使用must_not没有任何意义
					query.add(termQuery, Occur.MUST);
					query.add(numericQuery, Occur.MUST);

					// 1.5、查询所有文档
					MatchAllDocsQuery query = new MatchAllDocsQuery();
					
					// 1.6、多个域查询(只能查询文本)
					String [] fields = {"fileName","fileContext"};
					//从文件名称和文件内容中查询,只有含有apache的就查出来
					MultiFieldQueryParser multiQuery = new MultiFieldQueryParser(fields, new IKAnalyzer());
					//输入需要搜索的关键字
					Query query = multiQuery.parse("apache");


					/**
					 *	2、创建IndexSearcher对象
					 */
					//指定索引和文档的目录
					Directory dir = FSDirectory.open(new File("E:\\dic"));
					//索引和文档的读取对象
					IndexReader indexReader = IndexReader.open(dir);
					//创建索引的搜索对象
					IndexSearcher indexSearcher = new IndexSearcher(indexReader);
					//搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
					TopDocs topdocs = indexSearcher.search(query, 5);
					//一共搜索到多少条记录
					System.out.println("=====count=====" + topdocs.totalHits);
					//从搜索结果对象中获取结果集
					ScoreDoc[] scoreDocs = topdocs.scoreDocs;
					
					for(ScoreDoc scoreDoc : scoreDocs){
						//获取docID
						int docID = scoreDoc.doc;
						//通过文档ID从硬盘中读取出对应的文档
						Document document = indexReader.document(docID);
						//get域名可以取出值 打印
						System.out.println("fileName:" + document.get("fileName"));
						System.out.println("fileSize:" + document.get("fileSize"));
						System.out.println("============================================================");
					}
					
				}
		3、删除文档(这个会把整个文档删除，会把索引=词元顺带删除吗？不同文档的相同词元会合并吗)
			@Test
			public void testIndexDel() throws Exception{
				// 创建indexWriter看前面...
				
				//删除所有
				//indexWriter.deleteAll();
				
				//根据名称进行删除
				//Term词元,就是一个词, 第一个参数:域名, 第二个参数:要删除含有此关键词的数据
				indexWriter.deleteDocuments(new Term("fileName", "apache"));
				
				//提交
				indexWriter.commit();
				//关闭
				indexWriter.close();
			}
		4、更新文档
			/**
			 * 更新就是按照传入的Term进行搜索,如果找到结果那么删除,将更新的内容重新生成一个Document对象
			 * 如果没有搜索到结果,那么将更新的内容直接添加一个新的Document对象
			 * @throws Exception
			 */
			@Test
			public void testIndexUpdate() throws Exception{
				// 创建indexWriter看前面...
				//根据文件名称进行更新
				Term term = new Term("fileName", "web");
				//更新的对象
				Document doc = new Document();
				doc.add(new TextField("fileName", "xxxxxx", Store.YES));
				doc.add(new TextField("fileContext", "think in java xxxxxxx", Store.NO));
				doc.add(new LongField("fileSize", 100L, Store.YES));
				
				//更新
				indexWriter.updateDocument(term, doc);
				
				//提交
				indexWriter.commit();
				//关闭
				indexWriter.close();
			}
	











