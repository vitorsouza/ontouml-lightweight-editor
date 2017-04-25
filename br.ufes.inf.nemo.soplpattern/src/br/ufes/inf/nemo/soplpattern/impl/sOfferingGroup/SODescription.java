package br.ufes.inf.nemo.soplpattern.impl.sOfferingGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import RefOntoUML.Association;
import RefOntoUML.Category;
import RefOntoUML.Classifier;
import RefOntoUML.Package;
import RefOntoUML.Relator;
import RefOntoUML.parser.OntoUMLParser;
import br.ufes.inf.nemo.assistant.util.UtilAssistant;
import br.ufes.inf.nemo.common.ontoumlfixer.ClassStereotype;
import br.ufes.inf.nemo.common.ontoumlfixer.Fix;
import br.ufes.inf.nemo.common.ontoumlfixer.OutcomeFixer;
import br.ufes.inf.nemo.common.ontoumlfixer.RelationStereotype;
import br.ufes.inf.nemo.soplpattern.impl.SOPLPattern;

public class SODescription extends SOPLPattern{
	
	
	private Classifier c = null;
	private String elements[][] = new String[4][2];
	//private SOPLPatternTool janSOPLPatternTool;	
	
	//private EP1 janela;
	
	public SODescription(OntoUMLParser parser, double x, double y) {		
		super(parser, x, y, "/resource/SOFFERING.png", "SOFFERING");
	}

	public SODescription(OntoUMLParser parser, Classifier c, double x, double y) {		
		super(parser, x, y, "/resource/SOFFERING.png", "SOFFERING");
		this.c = c;
	}	
	
	
	public void runPattern() {
						
		
	}
	
	public void criar(String[][] tabela){
		this.elements = tabela;		
		
		for(int l =0;l < 4;l++){
			for(int c=0; c < 2;c++){
				System.out.println(elements[l][c]);
			}
		}	
		//janSOPLPatternTool.returnFix();		
		
	}
	
	public Fix getSpecificFix() {			
		
		Package root = parser.getModel();
		outcomeFixer = new OutcomeFixer(root);
		fix = new Fix();
					
//		Classifier roleA = this.createClassifier("Service Offering Description", "Role", x, y);
//		Classifier relatorA = this.createClassifier("Service Offering", "Role", x, y);
		
//		Association formal = null;		
		
//		if(roleA != null && relatorA != null){
//			formal = (Association)outcomeFixer.createAssociationBetween(RelationStereotype.ASSOCIATION, "", roleA, relatorA).getAdded().get(0);
//			fix.includeAdded(formal);
//		}		
			
				
		Classifier roleA = this.createClassifier(this.elements[0][1], "RoleMixin", x, y);
		Classifier collectiveA = this.createClassifier(this.elements[1][1], "Collective", x+(verticalDistance/3), y);
		Classifier relatorA = this.createClassifier(this.elements[2][1], "Relator", x+(verticalDistance/2), y);		
		Classifier roleB = this.createClassifier(this.elements[3][1], "RoleMixin", x+(verticalDistance/3), y+70);

		Association formal = null;		
		
		if(collectiveA != null && roleB != null){
			fix.addAll(outcomeFixer.createGeneralization(roleB, collectiveA));	
		}

		if(roleA != null && relatorA != null){
			formal = (Association)outcomeFixer.createAssociationBetween(RelationStereotype.ASSOCIATION, "", roleA, relatorA).getAdded().get(0);
			fix.includeAdded(formal);
		}
		
		if(relatorA != null && collectiveA != null){
			formal = (Association)outcomeFixer.createAssociationBetween(RelationStereotype.ASSOCIATION, "", relatorA, collectiveA).getAdded().get(0);
			fix.includeAdded(formal);
		}
		
		//dm.updateOLED(fix); //Ja que esta dando erro fazendo isso dentro de SOPLPatterTool, vamos for�ar por aqui....
		return fix;
	}

}