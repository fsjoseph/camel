
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.*;

import com.google.gson.Gson;


public class RecurseMenu {

	private Statement stmt;
	private Connection conn;

	private static final String TABLE = "electronics";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/camel";

 	private List<Item> itemsList = new ArrayList<>();
	private Set<Integer> itemSet = new HashSet<Integer>();
 	
 

 public static void main(String[] args)throws Exception {
		
		LocalTime startTime = LocalTime.now();
		
		RecurseMenu obj = new RecurseMenu();
		obj.process();
		
		LocalTime endTime = LocalTime.now();
		
		Duration duration = Duration.between(startTime, endTime);
		
		System.out.println("difference:" + duration.toMillis());
		
	}
	
	private void clean()throws SQLException {
		// TODO Auto-generated method stub
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
 	
 	public void process() {

		try {

 			String qry = getSQL();
			
			ResultSet rs = getStatement().executeQuery(qry);

			while (rs.next()) {

				Integer key = rs.getInt("ID");
				Integer parent = rs.getInt("PARENT");
				String root = rs.getString("NAME");
				String node = rs.getString("NAME");
				
  				Item item = new RecurseMenu.Item();
				item.setKey(key);
				item.setParent(parent);
				item.setRoot(root);
				item.setTitle(root);
				item.setNode(node);

				itemsList.add(item);
			}

			Map<String, Item> resultMap = new LinkedHashMap<String, Item>();

 			
			List<Item> tempList = new ArrayList<>();
			recurseChildren( itemsList, tempList, null);
			filter(itemsList);
			
 			
			printJson(itemsList);
			
			
			/** For LEFTMENU
			StringBuffer sb = new StringBuffer();
			sb.append("\n<ul class=\"nav\">");
			
			Integer level = incrementLevel();
			
			printData(resultMap.values(),sb,null,level);
			
			sb.append("</ul>");
			
			//System.out.println(sb.toString());
			//System.out.println("#############################################################");
			**/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Statement getStatement()throws SQLException {
		// TODO Auto-generated method stub
		conn = DriverManager.getConnection(URL, "camel", "camel");
		stmt = conn.createStatement();
		 
		return stmt;
	}
	
	public static void recurseChildren( List<Item> items, List tempList, Item newItem) {
  
	for (Item item : items) {
		
 			if (newItem != null){
				
				if(newItem.isDeleted()) continue;
				
				if(newItem.getParent() ==0){
					newItem.setNode(null);
					item.setFolder(true);
					continue;
				} 
				
				if(newItem.getParent() == item.getKey()) {
					newItem.setRoot(null);
					item.setNode(null);
					item.getChildren().add(newItem);
					item.setFolder(true);
					newItem.deleted(true);
					
					break;
				} 	
			}
 			
			if(newItem == null ){
			   recurseChildren( items,  tempList, item);
			}
			
		}
	}

	private void filter(List<Item> itemsList){
				
  			for(Iterator<Item> i = itemsList.iterator(); i.hasNext();){
				Item item = i.next();
				
				if(item.isDeleted()){
					i.remove();
				}
			}
	}	

  	private String getSQL() {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ID,NAME,PARENT FROM " + TABLE + " ORDER BY ID");
		
		/*SubMenu.SSN as ID,Menu.ssn as parent, Menu.mssn,Menu.name AS Root, SubMenu.name   AS Node");
		// --GROUP_CONCAT(SUPERVISEE.name ORDER BY SUPERVISEE.name ) AS
		// Children,
		// --COUNT(*)
		sb.append(" FROM " + TABLE + " AS Menu ");
		sb.append(" INNER JOIN " + TABLE + " SubMenu ON  Menu.SSN = SubMenu.MSSN ");
		sb.append(" GROUP BY ID,parent, Root, mssn,Node;");*/

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
	
	private void printJson(Collection<Item> data){
	 
	  try{
		 
		Gson gson = new Gson();
		String result = gson.toJson(data);
		System.out.println(result);
		
		//java.io.FileWriter writer = new java.io.FileWriter("output.txt");
		//writer.write(result);
		//writer.flush();
		
	   }catch(Exception e){
		 e.printStackTrace();
	   }	
	}
	
	private void printMap(Collection<Item> data){
		 for (Item item : data) {
		System.out.println("\n----->" + item.getKey() + ":" + item.getRoot()+"\t---Children:"+ item.getChildren().size());
		
			for (Item node : item.getChildren()) {
				System.out.print("\n\t\t---:" +node.getRoot() ); 
				for (Item child : node.getChildren()) {
					System.out.print("\tCHILD: " + child.getNode()  + "->"+ child.getKey() );  //+ "->" + child.getRoot());
					for (Item child1 : child.getChildren()) 
						 System.out.print("\t grand CHILD: "+ child1.getNode() + "->" + child1.getKey() + "->" + child1.getRoot());
				}
			}} 
		}
	
	private void printData(Collection<Item> data, StringBuffer sb,String rootItem,Integer level) {
		
		for (Item item : data) {
			 String menuItem = item.getRoot();
			 String nodeItem = item.getNode();
			 
 			 
			  if(item.getChildren().size() > 0){
				  if(rootItem != null && nodeItem!= null)menuItem=nodeItem;
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
 		private int parent;
		private int key;
 		private boolean folder;
		private boolean deleted;
 		private String node;
		private String root;
		private String title;
		private Set<Item> children = new HashSet<Item>();

		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + key;
			result = prime * result + parent;
			result = prime * result + ((node == null) ? 0 : node.hashCode());
			result = prime * result + ((root == null) ? 0 : root.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
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
			//if (id != other.id)
				//return false;
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
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;

			return true;
		}

		private Set<Item> getChildren() {
			return children;
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
		
		private String getTitle() {
			return title;
		}

		private void setTitle(String title) {
			this.title = title;
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
		
		private void setFolder(boolean folder){
			this.folder = folder;
		}
		
		private boolean getFolder(){
			return folder;
		}
		
		private int getKey(){
			return key;
		}
		
		private void setKey(int key){
			this.key = key;
		}
		
		private boolean isDeleted(){
			return deleted;
		}
		
		private void deleted(boolean deleted){
			this.deleted = deleted;
		}

	}
}
