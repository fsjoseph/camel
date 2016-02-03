var app = angular.module('myApp', []);
app.controller('FormCtrl', function ($scope, $http) {
 
    $scope.idList = [];
	$scope.data = [];
	$scope.multiRow = 0;
	
	$scope.postData = function( ) {

     //var data = escape(angular.toJson($scope.items));
    
	    //var data =   getTreeData(); 
	    var data =  { menuList: [{key:11,title:5,parent:2},{key:10,title:5,parent:1}] } ;
		//var data =  {name:'francis', menuList:JSON.stringify("[{key:11,title:5,parent:2}]") } ;
		
		$.ajax({
		  type: "POST",
		  url:  'http://localhost/sistic/updateMenu',
		  data:  $.toJSON(data),
		  dataType: "json"
		}).success(function(data, status, headers, config) {
            alert("success:"+ data);
       }).error(function(data, status, headers, config) {
           alert("Error :: "+data);
       });  
		
    /* $http({
          method: 'POST',
          url: 'http://localhost/sistic/updateMenu',
          //data: JSON.stringify(data),
		  data  :    JSON.stringify(data),
		  //dataType:"json",
		  
		  //params : {"name":"francis"},
		  
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		  //contentType: "application/json; charset=utf-8"
       }).success(function(data, status, headers, config) {
            alert("success:"+ data);
       }).error(function(data, status, headers, config) {
           alert("Error :: "+data);
       }); */
	   
	};	
	
	
	$scope.init = function(){
		$scope.data = [{a:"a"},{a:"b"}];
		$scope.idList = {n:"a",x:"b",a:"a1",b:"b",a3:"a",b2:"b",a1:"a",x1:"b"};
	     
	};
	
	
	$scope.init();
 
});

function expandNode(node, dataList, indx) {
    // Expand this node
	
    //node.setExpanded(true);
 
    var key = node.key;
	var title = node.title;
	var parent  = (node.parent)? node.parent.key : null;
	
	if(key == 'root_1') key = 0;
	if(parent == 'root_1') parent = 0;
	
	var arr = {"key":key,"title":title,"parent":parent};
	dataList.push(arr);
	

	if(node.children)
        for (var i = 0; i < node.children.length; i++) {
            // Go recursive on child nodes
            expandNode(node.children[i],dataList,indx);
        }
    //}
}
function getTreeData(){
	//var tree = $("#tree").fancytree("getTree");
	
	var root = $("#tree").fancytree("getRootNode");
	var dataList = [];
	
	expandNode(root,dataList,0);
	
	return "{menuList:" + dataList + "}";
}


var OPT_LIST = [
	{name: "activeVisible", value: true,
	 hint: "Make sure, active nodes are visible (expanded)."},
	{name: "aria", value: false,
	 hint: "Enable WAI-ARIA support."},
	{name: "autoActivate", value: true,
	 hint: "Automatically activate a node when it is focused (using keys)."},
		{name: "autoCollapse", value: false,
		 hint: "Automatically collapse all siblings, when a node is expanded."},
	{name: "autoScroll", value: false,
	 hint: "Automatically scroll nodes into visible area."},
//	{name: "autoFocus", value: true,
//	 hint: "Set focus to first child, when expanding or lazy-loading."},
	{name: "clickFolderMode", value: [{name: "activate", value: 1},
									  {name: "expand", value: 2},
									  {name: "activate and expand", value: 3},
									  {name: "activate (dblclick expands)", value: 4, selected: true}],
	 hint: "1:activate, 2:expand, 3:activate and expand, 4:activate (dblclick expands)"},
	{name: "checkbox", value: false,
	 hint: "Show checkboxes."},
	{name: "debugLevel", value: [{name: "quiet", value: 0},
								 {name: "normal", value: 1},
								 {name: "debug", value: 2}],
	 hint: "0:quiet, 1:normal, 2:debug"},
	{name: "disabled", value: false,
	 hint: "Disable control"},
	{name: "focusOnSelect", value: false,
	 hint: "Set focus when node is checked by a mouse click"},
//	{name: "fx", value: null,
//	 hint: 'Animations, e.g. null or { height: "toggle", duration: 200 }'},
	{name: "generateIds", value: false,
	 hint: "Generate id attributes like <span id='fancytree-id-KEY'>"},
	{name: "idPrefix", value: "ft_",
	 hint: "Used to generate node id´s like <span id='fancytree-id-<key>'>."},
	{name: "icons", value: true,
	 hint: "Display node icons."},
	{name: "keyboard", value: true,
	 hint: "Support keyboard navigation."},
	{name: "keyPathSeparator", value: "/",
	 hint: "Used by node.getKeyPath() and tree.loadKeyPath()."},
	{name: "minExpandLevel", value: 1,
	 hint: "1: root node is not collapsible"},
//    {name: "nolink", value: false,
//     hint: "Use <span> instead of <a> tags for all nodes"},
//	{name: "persist", value: false,
//	 hint: "Persist expand-status to a cookie"},
	{name: "quicksearch", value: false,
	 hint: "Navigate to next node by typing the first letters."},
	{name: "selectMode", value: [{name: "single", value: 1},
								 {name: "multi", value: 2, selected: true},
								 {name: "multi-hier", value: 3}],
	 hint: "1:single, 2:multi, 3:multi-hier"},
//	{name: "strings", value: {loading: "Loading&#8230;",
//							  loadError: "Load error!"},
//	 hint: "Translations"},
	{name: "tabbable", value: true,
	 hint: "Whole tree behaves as one single control"},
	{name: "titlesTabbable", value: false,
	 hint: "Node titles can receive keyboard focus"}
	];
/*	
var SOURCE = 
[{"key":4,"parent":2,"node":"Notebook","root":"Computers","title":"Computers","folder":true,"expanded":false,"children":[{"key":6,"parent":0,"root":"Tablet","title":"Tablet","folder":false,"expanded":false,"children":[]},{"key":7,"parent":0,"root":"Desktop","title":"Desktop","folder":true,"expanded":false,"children":[{"key":17,"parent":7,"node":"Dell PC","root":"Desktop","title":"Dell PC","folder":false,"expanded":false,"children":[]},{"key":18,"parent":7,"node":"Lenovo","root":"Desktop","title":"Lenovo","folder":true,"expanded":false,"children":[{"key":19,"parent":18,"node":"ThinkPad","root":"Lenovo","title":"ThinkPad","folder":false,"expanded":false,"children":[]}]}]},{"key":4,"parent":0,"root":"Notebook","title":"Notebook","folder":true,"expanded":false,"children":[{"key":12,"parent":4,"node":"Toshiba","root":"Notebook","title":"Toshiba","folder":false,"expanded":false,"children":[]},{"key":11,"parent":4,"node":"Sony","root":"Notebook","title":"Sony","folder":false,"expanded":false,"children":[]}]},{"key":5,"parent":0,"root":"Server","title":"Server","folder":true,"expanded":false,"children":[{"key":10,"parent":5,"node":"High End Server12","root":"Server","title":"High End Server12","folder":true,"expanded":false,"children":[{"key":23,"parent":10,"node":"Sun","root":"High End Server12","title":"Sun","folder":false,"expanded":false,"children":[]},{"key":25,"parent":10,"node":"HP","root":"High End Server12","title":"HP","folder":false,"expanded":false,"children":[]},{"key":24,"parent":10,"node":"Dell","root":"High End Server12","title":"Dell","folder":false,"expanded":false,"children":[]}]},{"key":9,"parent":5,"node":"Workstation","root":"Server","title":"Workstation","folder":false,"expanded":false,"children":[]}]}]},{"key":8,"parent":3,"node":"Handycam","root":"Camera","title":"Camera","folder":true,"expanded":false,"children":[{"key":8,"parent":0,"root":"Handycam","title":"Handycam","folder":false,"expanded":false,"children":[]}]},{"key":14,"parent":13,"node":"iPhone","root":"Phone","title":"Phone","folder":true,"expanded":false,"children":[{"key":21,"parent":0,"root":"HTC","title":"HTC","folder":false,"expanded":false,"children":[]},{"key":14,"parent":0,"root":"iPhone","title":"iPhone","folder":false,"expanded":false,"children":[]},{"key":20,"parent":0,"root":"Samsung","title":"Samsung","folder":false,"expanded":false,"children":[]},{"key":22,"parent":0,"root":"Sony","title":"Sony","folder":false,"expanded":false,"children":[]}]},{"key":16,"parent":15,"node":"Hard Disk","root":"Accessories","title":"Accessories","folder":true,"expanded":false,"children":[{"key":16,"parent":0,"root":"Hard Disk","title":"Hard Disk","folder":false,"expanded":false,"children":[]}]}]
*/
/*
var SOURCE = 
[{"key":4,"parent":2,"node":"Notebook","root":"Computers","title":"Computers","folder":true,"expanded":false},
{"key":4,"parent":2,"node":"Notebook","root":"Computers","title":"Computers","folder":true,"expanded":false}]
*/

var SOURCE =
 
[{"id":2,"folder":true,"parent":0,"title":"COMPUTER","children":[{"id":5,"folder":false,"parent":2,"title":"DESKTOP","children":[]},{"id":4,"folder":true,"parent":2,"title":"NOTEBOOK","children":[{"id":6,"folder":false,"parent":4,"title":"DELL","children":[]},{"id":7,"folder":false,"parent":4,"title":"LENOVA","children":[]}]},{"id":10,"folder":true,"parent":2,"title":"SERVER","children":[{"id":11,"folder":true,"parent":10,"title":"HP","children":[{"id":12,"folder":false,"parent":11,"title":"POWEREDGE","children":[]}]}]}]},{"id":3,"folder":true,"parent":0,"title":"PRINTER","children":[{"id":8,"folder":false,"parent":3,"title":"CANON","children":[]},{"id":9,"folder":false,"parent":3,"title":"HP","children":[]}]}]

/*[
    {title: "node 1", folder: true, expanded: true, children: [
      {title: "node 1.1"},
      {title: "node 1.2"}
     ]},
    {title: "node 2", folder: true, expanded: false, children: [
      {title: "node 2.1"},
      {title: "node 2.2"}
     ]}
  ];
*/
  
CLIPBOARD = null;

$(function(){

	// Attach the fancytree widget to an existing <div id="tree"> element
	// and pass the tree options as an argument to the fancytree() function:
	
	$("#tree").fancytree({

	checkbox: true,
    //titlesTabbable: true,     // Add all node titles to TAB chain
    quicksearch: true,
   source: SOURCE,
/* 
	source: $.ajax({
						    url : 'http://wwwendt.de/tech/fancytree/demo/ajax-tree-plain.json' //,
						    //dataType: "json"
						  }),
	 */
    // extensions: ["edit", "table", "gridnav"],
     extensions: ["edit", "dnd", "table", "gridnav"],

    dnd: {
      preventVoidMoves: true,
      preventRecursiveMoves: true,
      autoExpandMS: 400,
      dragStart: function(node, data) {
        return true;
      },
      dragEnter: function(node, data) {
        // return ["before", "after"];
        return true;
      },
      dragDrop: function(node, data) {
        data.otherNode.moveTo(node, data.hitMode);
      }
    },
    edit: {
    },
    table: {
      indentation: 20,
      //nodeColumnIdx: 2,
      //checkboxColumnIdx: 0
    },
    gridnav: {
      autofocusInput: false,
      handleCursorKeys: true
    },

    lazyLoad: function(event, data) {
     // data.result = {url: "../demo/ajax-sub2.json"};
    },
	
	 select: function(event, data) {
        //logEvent(event, data, "current state=" + data.node.isSelected());
        var s = data.tree.getSelectedNodes().join(", ");
        $("#echoSelected").text(s);
     },
	  
	  
    renderColumns: function(event, data) {
      var node = data.node,
        $select = $("<select />"),
        $tdList = $(node.tr).find(">td");

      // (index #0 is rendered by fancytree by adding the checkbox)
      if( node.isFolder() ) {
        // make the title cell span the remaining columns, if it is a folder:
        $tdList.eq(2)
          .prop("colspan", 6)
          .nextAll().remove();
      }

/*      $tdList.eq(1).text(node.getIndexHier()).addClass("alignRight");
      
	  
	  // (index #2 is rendered by fancytree)
      // $tdList.eq(3).text(node.key);
	  
 	  
      $tdList.eq(3).html("<input type='input' value='" + "" + "'>");
      $tdList.eq(4).html("<input type='input' value='" + "" + "'>");
      $tdList.eq(5).html("<input type='checkbox' value='" + "" + "'>");
      $tdList.eq(6).html("<input type='checkbox' value='" + "" + "'>");
      $("<option />", {text: "a", value: "a"}).appendTo($select);
      $("<option />", {text: "b"}).appendTo($select);
      $tdList.eq(7).html($select); */
    }
  }).on("nodeCommand", function(event, data){
    // Custom event handler that is triggered by keydown-handler and
    // context menu:
    var refNode, moveMode,
      tree = $(this).fancytree("getTree"),
      node = tree.getActiveNode();

    switch( data.cmd ) {
    case "moveUp":
      node.moveTo(node.getPrevSibling(), "before");
      node.setActive();
      break;
    case "moveDown":
      node.moveTo(node.getNextSibling(), "after");
      node.setActive();
      break;
    case "indent":
      refNode = node.getPrevSibling();
      node.moveTo(refNode, "child");
      refNode.setExpanded();
      node.setActive();
      break;
    case "outdent":
      node.moveTo(node.getParent(), "after");
      node.setActive();
      break;
    case "rename":
      node.editStart();
      break;
    case "remove":
      node.remove();
      break;
    case "addChild":
      node.editCreateNode("child", "New node");
      // refNode = node.addChildren({
      //   title: "New node",
      //   isNew: true
      // });
      // node.setExpanded();
      // refNode.editStart();
      break;
    case "addSibling":
      node.editCreateNode("after", "New node");
      // refNode = node.getParent().addChildren({
      //   title: "New node",
      //   isNew: true
      // }, node.getNextSibling());
      // refNode.editStart();
      break;
    case "cut":
      CLIPBOARD = {mode: data.cmd, data: node};
      break;
    case "copy":
      CLIPBOARD = {
        mode: data.cmd,
        data: node.toDict(function(n){
          delete n.key;
        })
      };
      break;
    case "clear":
      CLIPBOARD = null;
      break;
    case "paste":
      if( CLIPBOARD.mode === "cut" ) {
        // refNode = node.getPrevSibling();
        CLIPBOARD.data.moveTo(node, "child");
        CLIPBOARD.data.setActive();
      } else if( CLIPBOARD.mode === "copy" ) {
        node.addChildren(CLIPBOARD.data).setActive();
      }
      break;
    default:
      alert("Unhandled command: " + data.cmd);
      return;
    }

  }).on("keydown", function(e){
    var c = String.fromCharCode(e.which),
      cmd = null;

    if( c === "N" && e.ctrlKey && e.shiftKey) {
      cmd = "addChild";
    } else if( c === "C" && e.ctrlKey ) {
      cmd = "copy";
    } else if( c === "V" && e.ctrlKey ) {
      cmd = "paste";
    } else if( c === "X" && e.ctrlKey ) {
      cmd = "cut";
    } else if( c === "N" && e.ctrlKey ) {
      cmd = "addSibling";
    } else if( e.which === $.ui.keyCode.DELETE ) {
      cmd = "remove";
    } else if( e.which === $.ui.keyCode.F2 ) {
      cmd = "rename";
    } else if( e.which === $.ui.keyCode.UP && e.ctrlKey ) {
      cmd = "moveUp";
    } else if( e.which === $.ui.keyCode.DOWN && e.ctrlKey ) {
      cmd = "moveDown";
    } else if( e.which === $.ui.keyCode.RIGHT && e.ctrlKey ) {
      cmd = "indent";
    } else if( e.which === $.ui.keyCode.LEFT && e.ctrlKey ) {
      cmd = "outdent";
    }
    if( cmd ){
      $(this).trigger("nodeCommand", {cmd: cmd});
      return false;
    }
  });


	$("#tree").configurator({
		pluginName: "fancytree",
		optionTarget: "div#options",
		sourceTarget: "pre#sourceCode",
		optionList: OPT_LIST,
		render: function(event, name, value){
//            var w = window.code_google_com_googleprettify;
			$("#sourceCode").removeClass("prettyprinted");
//            alert("change: " + name + "=" + value + "; " + PR.prettyPrint);
			PR.prettyPrint();
		}
	});
	
/*
   * Context menu (https://github.com/mar10/jquery-ui-contextmenu)
   */
  $("#tree").contextmenu({
    delegate: "span.fancytree-node",
    menu: [
      {title: "Edit", cmd: "rename" }, //, uiIcon: "ui-icon-pencil"
      {title: "Delete", cmd: "remove", uiIcon: "ui-icon-trash" },
      {title: "----"},
      {title: "New sibling", cmd: "addSibling", uiIcon: "ui-icon-plus" },
      {title: "New child", cmd: "addChild", uiIcon: "ui-icon-arrowreturn-1-e" },
      {title: "----"},
      {title: "Cut", cmd: "cut", uiIcon: "ui-icon-scissors"},
      {title: "Copy", cmd: "copy", uiIcon: "ui-icon-copy"},
      {title: "Paste as child", cmd: "paste", uiIcon: "ui-icon-clipboard", disabled: true }
      ],
    beforeOpen: function(event, ui) {
      var node = $.ui.fancytree.getNode(ui.target);
      $("#tree").contextmenu("enableEntry", "paste", !!CLIPBOARD);
      node.setActive();
    },
	afterOpen: function(event, ui) {
      var node = $.ui.fancytree.getNode(ui.target);
      $("#tree").contextmenu("enableEntry", "paste", !!CLIPBOARD);
      node.setActive();
    },
    select: function(event, ui) {
      var that = this;
      // delay the event, so the menu can close and the click event does
      // not interfere with the edit control
	  var node = $.ui.fancytree.getNode(ui.target);
	  
      setTimeout(function(){
        $(that).trigger("nodeCommand", {cmd: ui.cmd});
		
      }, 100);
    }
  });
 
	
	$("input[name=showComments]").change(function(event){
		$("#tree").configurator("option", "showComments", $(this).is(":checked"));
	});
	$("input[name=hideDefaults]").change(function(event){
		$("#tree").configurator("option", "hideDefaults", $(this).is(":checked"));
	});

});