package com.sistic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class RecurseMenu {
	private static final String TABLE = "electronicsx";
	private static final String DRIVER = "org.hsqldb.jdbcDriver";
	private static final String URL = "jdbc:hsqldb:file:hsqldb/camel;shutdown=true";
	private Set<Integer> itemSet = new HashSet<Integer>();
	private Integer levels = new Integer(1);
	List tempList = new  ArrayList ();
	Statement stmt;
	Connection conn;
	
	
	public RecurseMenu()throws Exception{
		conn = DriverManager.getConnection(URL, "SA", "");
	    stmt = conn.createStatement();
	}
	// private static final String URL="jdbc:hsqldb:mem:camel;shutdown=true";
	public static void main(String[] args)throws Exception {
		RecurseMenu obj = new RecurseMenu();

		obj.init();
		obj.process();
		obj.clean();
	}
	
	private void clean()throws SQLException {
		// TODO Auto-generated method stub
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
	private void init()throws SQLException{
		   drop(stmt);
		   create(stmt);
		   processSQL(stmt);
	}
	
	private Integer incrementLevel(){
		 levels = levels + 1;
		 return levels;
	}
	public void process() {

		try {

			// String sql = createSQL();
			// int result = stmt.executeUpdate(sql);

			List<Item> data = new ArrayList<Item>();

			String qry = getSQL();
			
			ResultSet rs = getStatement().executeQuery(qry);

			while (rs.next()) {

				Integer id = rs.getInt("ID");
				Integer parent = rs.getInt("parent");
				String root = rs.getString("ROOT");
				String node = rs.getString("NODE");
				
//				System.out.println("id:" + id + "\tparent:" + parent + "\troot:" + root + ":" + "\tnode:" + node);
				
				Item item = new RecurseMenu.Item();
				item.setId(id);
				item.setParent(parent);
				item.setRoot(root);
				item.setNode(node);

				data.add(item);
			}

			Map<String, Item> resultMap = new LinkedHashMap<String, Item>();

			for (Item item : data) {
				 Integer id = item.getId();
				 itemSet.add(id);
			}			
			
			recurse( data, itemSet, null,1);
			data.removeAll(tempList);

			for (Item item : data) {
					 Item itm = new RecurseMenu.Item();
					 itm.setRoot(item.getNode());
					 if(item.getChildren().size() > 0){
						 itm.getChildren().addAll(item.getChildren());
					 }
				if(!resultMap.containsKey (item.getRoot())){ 
					  item.getChildren().clear();
					  item.getChildren().add(itm);
					  resultMap.put(item.getRoot(),item);
					  //System.out.println("-------->>>>>>>Added:"+itm.getRoot()+  "#" + item.getChildren().size()); 
				}else{
					 resultMap.get(item.getRoot()).getChildren().add(itm);
					 //System.out.println("-------->>>>>>>Included:"+itm.getRoot()+  "#" + item.getChildren().size()); 
				}
			}
			
			printMap(resultMap.values());
			
			StringBuffer sb = new StringBuffer();
			sb.append("\n<ul class=\"nav\">");
			
			Integer level = incrementLevel();
			
			printData(resultMap.values(),sb,null,level);
			
			sb.append("</ul>");
			
			System.out.println(sb.toString());
			System.out.println("#############################################################");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Statement getStatement()throws SQLException {
		// TODO Auto-generated method stub
		clean();
		conn = DriverManager.getConnection(URL, "SA", "");
		stmt = conn.createStatement();
		
		return stmt;
	}
	public void recurse( List<Item> items, Set<Integer> itemSet,Item newItem, int iterationNo) {
        iterationNo++;
        
		for (Item item : items) {
			
			if((newItem != null)){
				if(newItem.getParent() != item.getId()) continue;
					item.getChildren().add(newItem);
					tempList.add(newItem);
					itemSet.remove(newItem.getId());
			}
			
			if(itemSet.contains(item.getId())){
				//itemSet.remove(item.getId());
				recurse(items,itemSet,item, iterationNo);
			}
 		}
	}


	private String getSQL() {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT  SubMenu.SSN as ID,Menu.ssn as parent, Menu.mssn,Menu.name AS Root, SubMenu.name   AS Node");
		// --GROUP_CONCAT(SUPERVISEE.name ORDER BY SUPERVISEE.name ) AS
		// Children,
		// --COUNT(*)
		sb.append(" FROM " + TABLE + " AS Menu ");
		sb.append(" INNER JOIN " + TABLE + " SubMenu ON  Menu.SSN = SubMenu.MSSN ");
		sb.append(" GROUP BY ID,parent, Root, mssn,Node;");

		return sb.toString();
	}

	private void processSQL(Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
		stmt = getStatement();
		stmt.addBatch(" DELETE FROM " + TABLE + ";  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('2', 'Computers',  '1');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('3', 'Camera',  '1');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('4', 'Notebook',  '2');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('7', 'Desktop',  '2');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('6', 'Tablet','2');  ");
	    stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('5', 'Server',  '2');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('8', 'Handycam','3');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('9', 'Workstation','5');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('10','High End Server12','5');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('11','Sony','4');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('12','Toshiba','4');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('13','Phone','1');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('14','iPhone','13');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('15','Accessories','1');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('16','Hard Disk','15');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('17', 'Dell PC',  '7');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('18', 'Lenovo',  '7');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('19', 'ThinkPad',  '18');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('20','Samsung','13');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('21','HTC','13');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('22','Sony','13');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('23','Sun','10');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('24','Dell','10');  ");
		stmt.addBatch(" INSERT INTO " + TABLE + "  VALUES ('25','HP','10');  ");
		stmt.executeBatch();
	    stmt.executeUpdate("SHUTDOWN;");
	}

	private void create (Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE " + TABLE + " (");
		sb.append("SSN varchar(64) NOT NULL,");
		sb.append("Name varchar(64) DEFAULT NULL,");
		sb.append("MSSN varchar(64) NOT NULL);");
		sb.append("SHUTDOWN;");
		
		getStatement().executeUpdate(sb.toString());

	}
	private   void  drop(Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("DROP TABLE IF   EXISTS " + TABLE + ";");
		sb.append("SHUTDOWN;");

		getStatement().executeUpdate(sb.toString());

	}
	private void printMap(Collection<Item> data){
		 for (Item item : data) {
		System.out.println("\n----->" + item.id + ":" + item.getRoot()+"\t---Children:"+ item.getChildren().size());
		
			for (Item node : item.getChildren()) {
				System.out.print("\n\t\t---:" +node.getRoot() ); 
				for (Item child : node.getChildren()) {
					System.out.print("\tCHILD: " + child.getNode()  + "->"+ child.getId() );  //+ "->" + child.getRoot());
					for (Item child1 : child.getChildren()) 
						 System.out.print("\t grand CHILD: "+ child1.getNode() + "->" + child1.getId() + "->" + child1.getRoot());
				}
			}} 
		}
	
	private void printData(Collection<Item> data, StringBuffer sb,String rootItem,Integer level) {
		
		for (Item item : data) {
			 String menuItem = item.getRoot();
			 String nodeItem = item.getNode();
			 
			 level = incrementLevel();
			 
			  if(item.getChildren().size() > 0){
				  if(rootItem != null && nodeItem!= null)menuItem=nodeItem;
				  level = incrementLevel();
				  sb.append("<li><a href=\"javascript:void(0)\" data-target=\"#sub" +   level  + "\" data-toggle=\"collapse\"  >" + (level+":")+ menuItem  + "</a>\n"); 
				  sb.append( "<ul class=\"nav collapse\" id=\"sub" + level +"\">\n" );
				  
				  printData(new ArrayList(item.getChildren()), sb, menuItem,level);
				  sb.append(  "</ul></li>");
				  
			  }else  if(rootItem != null && nodeItem!= null){
				  sb.append("<li><a href=\"#\" >" + (level +":")+ nodeItem  + "</a></li>\n");
			  }else{
				  sb.append("<li><a href=\"#\" >" + (level +":")+ menuItem  + "</a></li>\n"); 
			  } 
		}
	}
	private class Item {
		private int id;
		private int parent;
		private String node;
		private String root;
		private Set<Item> children = new HashSet<Item>();

		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			result = prime * result + ((node == null) ? 0 : node.hashCode());
			result = prime * result + ((root == null) ? 0 : root.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Item other = (Item) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			if (node == null) {
				if (other.node != null)
					return false;
			} else if (!node.equals(other.node))
				return false;
			if (root == null) {
				if (other.root != null)
					return false;
			} else if (!root.equals(other.root))
				return false;
			return true;
		}

		private Set<Item> getChildren() {
			return children;
		}

		private int getId() {
			return id;
		}

		private void setId(int id) {
			this.id = id;
		}

		private String getNode() {
			return node;
		}

		private void setNode(String node) {
			this.node = node;
		}

		private String getRoot() {
			return root;
		}

		private void setRoot(String root) {
			this.root = root;
		}

		private RecurseMenu getOuterType() {
			return RecurseMenu.this;
		}

		private int getParent() {
			return parent;
		}

		private void setParent(int parent) {
			this.parent = parent;
		}

	}
}
