


















1、原生操作
	1、引入驱动
		<dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver</artifactId>
            <version>3.6.4</version>
        </dependency>
	2、使用
		1、创建连接 -> 选库 -> 选集合
			MongoClient client = new MongoClient("localhost", 27017);			// 创建连接，用完记得使用client.close();关闭
			MongoDatabase db= client.getDatabase("test");						// 使用test数据可
			MongoCollection<Document> collection = db.getCollection("user");	// 使用user集合，这个待研究 => MongoCollection<BsonDocument> bsonCollection = db.getCollection(collectionName,BsonDocument.class);

		2、增
			1、Document的创建
				1、正规的
					Document document = new Document();
					document.append("name", "gu").append("gender", "male");
				2、使用Map
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("name", "lifeix");
					map.put("gender", "undefinded");
					Document document = new Document(map);
				3、字符串
					String json ="{'name':'neoyin','gender':'male','avatar':'/444.jpg'}";
					BsonDocument document = BsonDocument.parse(json);
			2、插入
				collection.insertOne(document);
				collection.insertMany(new LinkedList<Document>());

		3、查
			1、遍历结果集的每一篇文档
				FindIterable<Document> iterables = collection.find();	// 或者使用带条件的查找find(filter); filter=Document;
				MongoCursor<Document> cursor = iterables.iterator();
				while (cursor.hasNext()) {
					Document doc = cursor.next();
					System.out.println(doc.toJson());
				}
		4、改
			1、这个才是修改的(没加$set是替换整个文档和原生语句是一样的，参考他就行了)
				Document update = new Document();
				update.append("$set", new Document("gender", "female"));
			2、修改
				UpdateResult result = collection.updateOne(filter, update);		// 修改一条，如果update没有$set会报错
				UpdateResult result = collection.updateMany(filter, update);	// 修改多条，如果update没有$set会报错
				collection.replaceOne(filter, replacement);						// 替换
		5、删
			collection.deleteOne(filter);
			collection.deleteMany(filter);
			





