package br.ufes.inf.nemo.oled.ui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Classifier;
import RefOntoUML.Comment;
import br.ufes.inf.nemo.oled.DiagramManager;
import br.ufes.inf.nemo.oled.umldraw.structure.ClassElement;
import javax.swing.JLabel;

public class CommentsEditionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private ClassElement classElement;
	private Classifier element;
	private DiagramManager diagramManager;
	@SuppressWarnings("unused")
	private JFrame parent;
	private JTextArea descriptionText;
	private JScrollPane scrollPaneText;
	@SuppressWarnings("rawtypes")
	private JComboBox commentCombo;
	private JButton btnCreate;		
	private JButton btnDelete;
	private JPanel descriptionPanel;	
	private JButton btnSave;
	private JLabel lblSelectedComment;
	
	@SuppressWarnings({ "rawtypes" })
	public CommentsEditionPanel(DiagramManager diagramManager, ClassElement classElement, boolean modal) 
	{
		this.diagramManager = diagramManager;
		this.classElement = classElement;
		this.element = classElement.getClassifier();
		
		descriptionPanel = new JPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		descriptionText = new JTextArea();	
		descriptionText.setToolTipText("Click here to start writing your comment");
		
		scrollPaneText = new JScrollPane();
		scrollPaneText.setToolTipText("Click here to start writing your comment");
		scrollPaneText.setViewportView(descriptionText);
		
		btnCreate = new JButton("");
		btnCreate.setToolTipText("Add a new comment to this class");
		btnCreate.setIcon(new ImageIcon(CommentsEditionPanel.class.getResource("/resources/br/ufes/inf/nemo/oled/ui/cross.png")));
		btnCreate.setFocusable(false);
		btnCreate.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createCommentActionPerformed();				
			}
		});
		
		btnDelete = new JButton("");
		btnDelete.setFocusable(false);
		btnDelete.setToolTipText("Delete seletected comment");
		btnDelete.setIcon(new ImageIcon(CommentsEditionPanel.class.getResource("/resources/br/ufes/inf/nemo/oled/ui/delete.png")));
		btnDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteCommentActionPerformed();
			}
		});
		
		btnSave = new JButton("");
		btnSave.setFocusable(false);
		btnSave.setToolTipText("Save selected comment");
		btnSave.setIcon(new ImageIcon(CommentsEditionPanel.class.getResource("/resources/br/ufes/inf/nemo/oled/ui/save.png")));
		btnSave.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveCommentActionPerformed();
			}
		});
		
		commentCombo = new JComboBox();
		commentCombo.setEditable(false);
		commentCombo.setFocusable(false);
		commentCombo.setPreferredSize(new Dimension(350, 20));
//		((JLabel)commentCombo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
//		commentCombo.addItemListener(new ItemListener(){
//		    @Override
//		    public void itemStateChanged(ItemEvent event) {
//		       if (event.getStateChange() == ItemEvent.SELECTED) {
//		          
//		    	   Object item = event.getItem();
//		          		          
//		           // do something with object
//		       }
//		    }
//		});
				
		commentCombo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				CommentElement ce = (CommentElement) commentCombo.getSelectedItem();
				if(ce!=null) descriptionText.setText(ce.getComment().getBody());
			}
		});
		
		lblSelectedComment = new JLabel("Selected comment:");
		
		GroupLayout gl_descriptionPanel = new GroupLayout(descriptionPanel);
		gl_descriptionPanel.setHorizontalGroup(
			gl_descriptionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_descriptionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_descriptionPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneText, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_descriptionPanel.createSequentialGroup()
							.addComponent(lblSelectedComment, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(commentCombo, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_descriptionPanel.setVerticalGroup(
			gl_descriptionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_descriptionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_descriptionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSave, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCreate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_descriptionPanel.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_descriptionPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(commentCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSelectedComment))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneText, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		descriptionPanel.setLayout(gl_descriptionPanel);
		this.setLayout(groupLayout);		
		
		setInitialData();
		
		setSize(new Dimension(450, 230));
	}
	
	/** Private Class: Comment Element */
	private class CommentElement 
	{
		RefOntoUML.Comment c;
		public CommentElement(RefOntoUML.Comment c){
			this.c = c;
		}
		@Override
		public String toString(){
			String result = new String();
			if(c.getBody().length()>30)
				result += c.getBody().substring(0,30) + " (...)";
			else 
				result += c.getBody();			
			return result;
		}
		public Comment getComment(){
			return c;
		}
	}

	@SuppressWarnings("unchecked")
	public void setInitialData()
	{		
		for(Comment c: element.getOwnedComment()){			
			commentCombo.addItem(new CommentElement(c));			
		}		
		if (commentCombo.getItemCount()>0) {
			commentCombo.setSelectedIndex(0);
			descriptionText.setText(((CommentElement)commentCombo.getSelectedItem()).getComment().getBody()+"\n\n");
		}
	}

	public static String getStereotype(EObject element)
	{
		String type = element.getClass().toString().replaceAll("class RefOntoUML.impl.","");
	    type = type.replaceAll("Impl","");
	    type = Normalizer.normalize(type, Normalizer.Form.NFD);
	    type = type.replace("Association","");
	    return type;
	}

	@SuppressWarnings("unchecked")
	public void createCommentActionPerformed()
	{
		Comment c = diagramManager.getElementFactory().createComment();
		c.setBody("This is an empty comment...");
		CommentElement ce = new CommentElement(c);		
		commentCombo.addItem(ce);
		descriptionText.setText(c.getBody());
		commentCombo.setSelectedIndex(commentCombo.getItemCount()-1);
	}

	public void saveCommentActionPerformed()
	{
		Comment c = ((CommentElement)commentCombo.getSelectedItem()).getComment();
		c.setBody(descriptionText.getText());	
		commentCombo.repaint();
		commentCombo.validate();
	}

	public void deleteCommentActionPerformed()
	{		
		commentCombo.removeItem(commentCombo.getSelectedItem());
		commentCombo.invalidate();	
		descriptionText.setText("");
	}

	public ArrayList<Comment> getComments()
	{
		ArrayList<Comment> result = new ArrayList<Comment>();
		for(int i=0; i<commentCombo.getItemCount();i++){
			CommentElement ce = (CommentElement)commentCombo.getItemAt(i);
			if (ce!=null) result.add(ce.getComment());
		}
		return result;
	}

	public void transferCommentsData()
	{
		// added
		ArrayList<Comment> toBeAdded = new ArrayList<Comment>();
		for(Comment c: getComments()){
			if (!element.getOwnedComment().contains(c)){				
				toBeAdded.add(c);
			}
		}
		for(Comment cmt: toBeAdded) { diagramManager.addComment(cmt, element); } 
			
		//removed
		ArrayList<Comment> toBeDeleted = new ArrayList<Comment>();
		for(Comment c: element.getOwnedComment()){
			if (!getComments().contains(c)){
				toBeDeleted.add(c);
			}
		}
		for(Comment cmt: toBeDeleted) { diagramManager.delete(cmt); }	
	}
}
