package br.ufes.inf.nemo.soplpattern.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import RefOntoUML.Classifier;
import RefOntoUML.Generalization;
import RefOntoUML.GeneralizationSet;
import RefOntoUML.Package;
import RefOntoUML.parser.OntoUMLParser;
import br.ufes.inf.nemo.assistant.util.UtilAssistant;
import br.ufes.inf.nemo.common.ontoumlfixer.ClassStereotype;
import br.ufes.inf.nemo.common.ontoumlfixer.Fix;
import br.ufes.inf.nemo.common.ontoumlfixer.OutcomeFixer;
import br.ufes.inf.nemo.soplpattern.dynamic.ui.JanProviderSubgroup;
//import br.ufes.inf.nemo.pattern.dynamic.ui.DynamicWindow;
//import br.ufes.inf.nemo.pattern.ui.manager.DynamicManagerWindow;
//import br.ufes.inf.nemo.patternsopl.dynamic.ui.EP1;
//import br.ufes.inf.nemo.patternsopl.dynamic.ui.janSOffering;


public abstract class SOPLPattern {

	protected  String imagePath;	
	protected  OutcomeFixer outcomeFixer;
	protected  Fix fix = null;

	protected static final int horizontalDistance = 150;
	protected static final int verticalDistance = 330;
	
	protected OntoUMLParser parser;
	protected double x;
	protected double y;
	protected String title;
	protected JanProviderSubgroup janPCsubgroup;
//	protected DiagramManager dm = null;
	//protected janSOffering janSOffering = null;


	protected Classifier createClassifier(String name, String stereotype, double x, double y){
		ClassStereotype enumStereotype = ClassStereotype.valueOf(stereotype.toUpperCase());

		RefOntoUML.Classifier classifier = (Classifier) outcomeFixer.createClass(enumStereotype);
		classifier.setName(name);
		parser.getModel().getPackagedElement().add(classifier);

		fix.includeAdded(classifier,x,y);

		return classifier;
	}



	protected SOPLPattern(OntoUMLParser parser, double x, double y, String imagePath, String title) {
		this.parser = parser;
		this.x = x;
		this.y = y;
		this.imagePath = imagePath;
		this.title = title;
		
		//Instanciar a Janela Principal SOPL aqui !
		
		JanProviderSubgroup janPCsubgroup= new JanProviderSubgroup();
		janPCsubgroup.main(null);
		
	}

	public abstract void runPattern( );	

	public Fix getFix(){
		try{
			Fix fix = getSpecificFix();
			return fix;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	protected abstract Fix getSpecificFix();

}