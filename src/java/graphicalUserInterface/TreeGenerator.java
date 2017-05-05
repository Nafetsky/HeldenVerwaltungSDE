package graphicalUserInterface;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeGenerator {

	private static final String CHARAKTERE = "Charaktere";
	
	 MainFrame mainFrame;

	public JTree getCurrentTree(List<String> charakters, PaneBuilder mainFrame){
		JTree jTree;
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		DefaultMutableTreeNode allCharakters = new DefaultMutableTreeNode(CHARAKTERE);
		
		for(String charakter:charakters){
			DefaultMutableTreeNode nextCharakter = new DefaultMutableTreeNode(charakter);
			allCharakters.add(nextCharakter);
		}
		
		root.add(allCharakters);
		jTree = new JTree(root);
		jTree.addTreeSelectionListener((TreeSelectionEvent e)->{
			DefaultMutableTreeNode currentSelection = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
			mainFrame.switchActiveCharakter((String)currentSelection.getUserObject());
		});
		jTree.setRootVisible(false);
		
		return jTree;
	}
	
}
